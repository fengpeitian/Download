package com.fpt.download.listener;

/**
 * <pre>
 *   @author  : lucien.feng
 *   e-mail  : fengfei0205@gmail.com
 *   time    : 2019/10/22 14:10
 *   desc    : 进度监听
 * </pre>
 */
public interface OnProgressListener {

    /**
     * 当前进度
     * @param progress
     */
    void onProgress(int progress);
}
