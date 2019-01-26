package com.psply.mypackage.utils;

import com.bumptech.glide.Glide;
import com.psply.mypackage.utils.media.videoview.VideoDataModel;
import com.psply.mypackage.utils.media.videoview.VideoViewWrapper;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import org.greenrobot.eventbus.EventBus;

/**
 * Data: 2018/12/15
 * Author: shipan
 * Description:
 */
public class TestActivity extends Activity {

    VideoViewWrapper videoViewWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);

        ImageView imageView = findViewById(R.id.sample_image);

        RelativeLayout videoContainer = findViewById(R.id.root_view);

        videoViewWrapper = new VideoViewWrapper(this,videoContainer,R.id.video_view_container);
        VideoDataModel videoDataModel = new VideoDataModel();
        videoDataModel.setVideoSource(VideoDataModel.VideoSource.ONLINE);
        videoDataModel.setVideoUrl("https://vdse.bdstatic.com//e509104a380c00fa8d2cb13d2fa2c1ec?authorization=bce-auth-v1%2F40f207e648424f47b2e3dfbb1014b1a5%2F2017-05-11T09%3A02%3A31Z%2F-1%2F%2F9f71c27b020218c3dc417156e4b6dc243373f6c7c1caf189ffacc83a580ca57a");
        videoViewWrapper.setVideoDataModel(videoDataModel);

//        Glide.with(this).load("https://www.baidu.com/img/bd_logo1.png?where=super").into(imageView);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            videoViewWrapper.switchToFullScreen(true);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            videoViewWrapper.switchToFullScreen(false);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
