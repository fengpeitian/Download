package com.fpt.download;

/**
 * <pre>
 *   @author  : lucien.feng
 *   e-mail  : fengfei0205@gmail.com
 *   time    : 2019/10/22 09:50
 *   desc    : 通用下载设置
 * </pre>
 */
public interface ISetting {

    /**
     * 设置使用几个线程
     * @return
     */
    int getThreadCount();

    /**
     * 获取文件下载目标文件夹
     * @return
     */
    String getDownloadFolder();

}
