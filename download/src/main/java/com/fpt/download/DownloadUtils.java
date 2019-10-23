package com.fpt.download;

import com.fpt.download.core.DownloadWrapper;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 *   @author  : lucien.feng
 *   e-mail  : fengfei0205@gmail.com
 *   time    : 2019/10/22 17:39
 *   desc    : 下载工具
 * </pre>
 */
public class DownloadUtils {

    /**
     * 批量下载
     * @param downloads
     */
    public static void download(List<Task> downloads){
        if (downloads == null){
            return;
        }
        int size = downloads.size();
        ExecutorService pool = new ThreadPoolExecutor(0, size+1
                , 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
        DownloadWrapper task;
        for (int i = 0; i < size; i++) {
            task = new DownloadWrapper(downloads.get(i));
            // 执行线程
            pool.execute(task);
        }
        // 关闭线程池
        pool.shutdown();
    }

    /**
     * 下载
     * @param download
     */
    public static void download(Task download){
        new Thread(new DownloadWrapper(download)).start();
    }


}
