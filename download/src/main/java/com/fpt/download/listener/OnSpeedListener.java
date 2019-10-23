package com.fpt.download.listener;

/**
 * <pre>
 *   @author  : lucien.feng
 *   e-mail  : fengfei0205@gmail.com
 *   time    : 2019/10/22 14:14
 *   desc    : 下载速度监听
 * </pre>
 */
public interface OnSpeedListener {

    /**
     * 当前速度
     * @param speed 单位：kb/s
     */
    void onSpeed(double speed);

}
