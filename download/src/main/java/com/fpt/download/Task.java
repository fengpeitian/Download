package com.fpt.download;

import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;

import com.fpt.download.listener.OnProgressListener;
import com.fpt.download.listener.OnSpeedListener;

/**
 * <pre>
 *   @author  : lucien.feng
 *   e-mail  : fengfei0205@gmail.com
 *   time    : 2019/10/23 13:46
 *   desc    : 下载所需参数
 * </pre>
 */
public class Task implements Parcelable {
    /**
     *  文件url
     */
    private String fileUrl;
    /**
     * 下载分配线程数量
     */
    private int threadCount;
    /**
     * 存放目标文件夹
     */
    private String targetFolder;
    /**
     * 存本地的目标文件名称
     */
    private String fileName;
    /**
     * 通用设置
     */
    private ISetting iSetting;
    /**
     * 下载实时速度的监听
     */
    private OnSpeedListener speedListener;
    /**
     *  下载进度的监听
     */
    private OnProgressListener progressListener;

    public Task(String fileUrl, String fileName) {
        this.fileUrl = fileUrl;
        this.fileName = fileName;

        // 当前设备可用处理器核心数
        int coreCount = Runtime.getRuntime().availableProcessors();
        // 默认分配下载线程数量
        this.threadCount = 2*coreCount+1;
        // 默认文件夹为Download文件夹
        this.targetFolder = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .getPath();
    }

    public OnSpeedListener getSpeedListener() {
        return speedListener;
    }

    public void setOnSpeedListener(OnSpeedListener speedListener) {
        this.speedListener = speedListener;
    }

    public OnProgressListener getProgressListener() {
        return progressListener;
    }

    public void setOnProgressListener(OnProgressListener progressListener) {
        this.progressListener = progressListener;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    public void setTargetFolder(String targetFolder) {
        this.targetFolder = targetFolder;
    }

    public String getFileUrl() {
        return fileUrl == null ? "" : fileUrl;
    }

    public String getFileName() {
        return fileName == null ? "" : fileName;
    }

    public ISetting getSetting() {
        iSetting = new ISetting() {
            @Override
            public int getThreadCount() {
                return threadCount;
            }

            @Override
            public String getDownloadFolder() {
                return targetFolder;
            }
        };
        return iSetting;
    }

    protected Task(Parcel in) {
        fileUrl = in.readString();
        threadCount = in.readInt();
        targetFolder = in.readString();
        fileName = in.readString();
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fileUrl);
        dest.writeInt(threadCount);
        dest.writeString(targetFolder);
        dest.writeString(fileName);
    }

}
