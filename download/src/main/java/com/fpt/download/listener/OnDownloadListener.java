package com.fpt.download.listener;

/**
 * <pre>
 *   @author  : lucien.feng
 *   e-mail  : fengfei0205@gmail.com
 *   time    : 2019/10/23 15:53
 *   desc    : 下载监听
 * </pre>
 */
public interface OnDownloadListener {

    /**
     * 下载成功
     */
    void onSuccess();

    /**
     * 下载失败
     * @param eMsg  错误信息
     */
    void onFailure(String eMsg);
}
