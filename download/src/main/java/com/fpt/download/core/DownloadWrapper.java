package com.fpt.download.core;

import android.support.annotation.NonNull;

import com.fpt.download.Task;
import com.fpt.download.ISetting;
import com.fpt.download.listener.OnDownloadListener;
import com.fpt.download.listener.OnProgressListener;
import com.fpt.download.listener.OnSpeedListener;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 *   @author  : lucien.feng
 *   e-mail  : fengfei0205@gmail.com
 *   time    : 2019/10/23 10:43
 *   desc    : 下载任务的封装
 * </pre>
 */
public class DownloadWrapper implements Runnable {
    private String fileUrl;
    private String fileName;

    private ISetting iSetting;
    private OnDownloadListener downloadListener;
    private OnSpeedListener speedListener;
    private OnProgressListener progressListener;

    public DownloadWrapper(Task d) {
        this.fileUrl = d.getFileUrl();
        this.fileName = d.getFileName();
        this.iSetting = d.getSetting();

        if (d.getDownloadListener() != null){
            this.downloadListener = d.getDownloadListener();
        }

        if (d.getSpeedListener() != null) {
            this.speedListener = d.getSpeedListener();
        }

        if (d.getProgressListener() != null) {
            this.progressListener = d.getProgressListener();
        }
    }

    @Override
    public void run() {
        startDownload(fileUrl,iSetting,fileName,downloadListener,speedListener,progressListener);
    }

    private void startDownload(String fileUrl, ISetting setting, @NonNull String fileName, OnDownloadListener l0, OnSpeedListener l1, OnProgressListener l2){
        // 获取文件size
        long fileLength = getFileSize(fileUrl);
        if (fileLength == 0){
            // url资源出错
            if (l0 != null) {
                l0.onFailure("error in obtaining URL resources");
            }
            return;
        }
        // 获取文件存储位置
        String filePath = setting.getDownloadFolder()+"/"+fileName;
        File file = new File(filePath);
        if (file.exists()){
            if (file.length() == fileLength) {
                // 已下载过此文件
                System.out.println("this file has been downloaded");
                if (l0 != null) {
                    l0.onSuccess();
                }
                return;
            }else {
                // 不同文件,改名下载
                filePath = setting.getDownloadFolder()+"/"+System.currentTimeMillis()+"_"+fileName;
            }
        }

        // 创建下载过程
        DownloadProcess process = new DownloadProcess(fileLength,l0,l1,l2);
        // 是否需要监听实时下载速度
        if (l1 != null) {
            new Thread(process).start();
        }
        // 创建线程池
        int poolSize = setting.getThreadCount();
        ExecutorService pool = new ThreadPoolExecutor(0, poolSize+1
                , 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
        // 分配线程池
        long slice = fileLength/poolSize;
        for(int i = 0 ;i < poolSize; i++) {
            long start = i * slice;
            long end = (i + 1) * slice - 1;
            if (i == poolSize - 1) {
                start = i * slice;
                end = fileLength;
            }

            // 创建下载类
            DownloadFileTask task = new DownloadFileTask(process,start,end,fileUrl,filePath);
            // 执行线程
            pool.execute(task);
        }
        // 关闭线程池
        pool.shutdown();
    }

    /**
     * 获取下载文件的size;需在子线程调用
     * @param fileUrl
     * @return
     */
    private static long getFileSize(String fileUrl) {
        int fileLength = 0;
        try {
            URL url = new URL(fileUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            fileLength = conn.getContentLength();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileLength;
    }


}
