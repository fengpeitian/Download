package com.fpt.download.core;

import com.fpt.download.listener.OnDownloadListener;
import com.fpt.download.listener.OnProgressListener;
import com.fpt.download.listener.OnSpeedListener;

import java.math.BigDecimal;

/**
 * <pre>
 *   @author  : lucien.feng
 *   e-mail  : fengfei0205@gmail.com
 *   time    : 2019/10/22 14:58
 *   desc    : 下载过程
 * </pre>
 */
public class DownloadProcess implements Runnable {
    private long start = 0;
    private long sum;

    private OnDownloadListener listener0;
    private OnSpeedListener listener1;
    private OnProgressListener listener2;

    public DownloadProcess(long sum, OnDownloadListener l0, OnSpeedListener l1, OnProgressListener l2) {
        this.sum = sum;
        this.listener0 = l0;
        this.listener1 = l1;
        this.listener2 = l2;
    }

    @Override
    public void run() {
        calculateSpeed();
    }

    public void add(long len) {
        start += len;

        if (listener2 != null) {
            calculateProgress();
        }
    }

    /**
     * 防重复标记
     */
    private boolean flag = true;
    public void setError(String message) {
        if (listener0 != null && flag){
            flag = false;
            listener0.onFailure(message);
        }
    }

    private void calculateProgress() {
        if (sum > start){
            //当前完成进度
            if (listener2 != null){
                int progress = (int) (start*100 / sum);
                listener2.onProgress(progress);
            }
        }else {
            if (listener2 != null){
                listener2.onProgress(100);
            }
            if (listener0 != null){
                listener0.onSuccess();
            }
        }
    }

    private void calculateSpeed() {
        long old = 0;
        long now = 0;
        while(sum > start) {
            now = start - old;
            old = start;
            //当前下载速度
            if (listener1 != null){
                BigDecimal bd = new BigDecimal((now/0.5)/1024);
                double speed = bd.setScale(2, BigDecimal.ROUND_HALF_UP)
                        .doubleValue();
                listener1.onSpeed(speed);
            }
            //间隔0.5秒
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
