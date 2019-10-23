package com.fpt.download.core;

import android.support.annotation.NonNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * <pre>
 *   @author  : lucien.feng
 *   e-mail  : fengfei0205@gmail.com
 *   time    : 2019/10/22 10:29
 *   desc    : 下载文件均分任务
 * </pre>
 */
public class DownloadFileTask implements Runnable {
    private long start;
    private long end;
    private String fileUrl;
    private String downloadPath;
    private DownloadProcess process;

    public DownloadFileTask(@NonNull DownloadProcess process, long start, long end, String fileUrl, String downloadPath) {
        this.start = start;
        this.end = end;
        this.fileUrl = fileUrl;
        this.downloadPath = downloadPath;
        this.process = process;
    }

    @Override
    public void run() {
        try {
            // 获取连接
            URL url = new URL(fileUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            // 设置获取资源范围
            conn.setRequestProperty("Range", "bytes=" + start + "-" + end);

            // 获得文件
            File file = new File(downloadPath);
            RandomAccessFile out = null;
            if (file != null) {
                out = new RandomAccessFile(file, "rw");
            }
            out.seek(start);

            // 获取网络连接的 输入流
            InputStream is = conn.getInputStream();
            byte[] data = new byte[1024];
            int len = 0;
            while ((len = is.read(data)) != -1) {
                out.write(data, 0, len);

                synchronized (DownloadProcess.class) {
                    process.add(len);
                }
            }

            // 关闭连接
            out.close();
            is.close();
        }catch (IOException e){

            synchronized (DownloadProcess.class) {
                process.setError(e.getMessage());
            }
        }
    }

}
