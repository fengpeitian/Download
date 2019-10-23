package com.fpt.download;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.fpt.download.listener.OnProgressListener;
import com.fpt.download.listener.OnSpeedListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        downloadTest();
    }

    private void downloadTest() {
        String url1 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1572327981&di=e17fce5386590a69e95183474a75027a&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.evolife.cn%2F2013-06%2F9ef154a1936372bd.jpg";
        String url2 = "https://media.w3.org/2010/05/sintel/trailer.mp4";
        String url3 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1571748890513&di=2bdb2b402a84094a5e354be147e4f514&imgtype=0&src=http%3A%2F%2Fh.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2F9345d688d43f8794fb05122ed01b0ef41bd53a33.jpg";

        Task download1 = new Task(url1,"timg.jpg");
        download1.setOnProgressListener(new OnProgressListener() {
            @Override
            public void onProgress(int progress) {
                Log.d("MainActivity","download1进度："+progress+"%");
            }
        });
        download1.setOnSpeedListener(new OnSpeedListener() {
            @Override
            public void onSpeed(double speed) {
                Log.d("MainActivity","download1速度："+speed+"kb/s");
            }
        });

        Task download2 = new Task(url2,"trailer.mp4");
        download2.setOnProgressListener(new OnProgressListener() {
            @Override
            public void onProgress(int progress) {
                Log.d("MainActivity","download2进度："+progress+"%");
            }
        });
        download2.setOnSpeedListener(new OnSpeedListener() {
            @Override
            public void onSpeed(double speed) {
                Log.d("MainActivity","download2速度："+speed+"kb/s");
            }
        });

        Task download3 = new Task(url3,"timg(1).jpg");
        download3.setOnProgressListener(new OnProgressListener() {
            @Override
            public void onProgress(int progress) {
                Log.d("MainActivity","download3进度："+progress+"%");
            }
        });
        download3.setOnSpeedListener(new OnSpeedListener() {
            @Override
            public void onSpeed(double speed) {
                Log.d("MainActivity","download3速度："+speed+"kb/s");
            }
        });

        List<Task> list = new ArrayList<>();
        list.add(download1);
        list.add(download2);
        list.add(download3);

        //DownloadUtils.download(list);
        //DownloadUtils.download(download2);
    }

}
