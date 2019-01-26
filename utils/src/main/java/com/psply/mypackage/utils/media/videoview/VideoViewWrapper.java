package com.psply.mypackage.utils.media.videoview;

import com.psply.mypackage.utils.R;
import com.psply.mypackage.utils.common.TimeUtils;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Data: 2019/1/12
 * Author: shipan
 * Description:
 */
public class VideoViewWrapper {

    private final int UI_UPDATE = 0x01;

    private boolean mVideoPrepared = false;

    private Context mContext;

    private Handler mHandler;

    private CustomVideoView mCustomVideoView;

    private RelativeLayout mVideoViewContainer;

    private View mVideoViewLayout;

    private SeekBar mVideoVolumeProgressSeekBar;

    private SeekBar mVideoProgressSeekBar;

    private ImageView mVideoActionView;

    private ImageView mFullScreenView;

    private TextView mVideoTotalTimeView;

    private TextView mVideoCurrentTimeView;

    private ImageView mLoadingView;

    private ImageView mDefaultBackgroundView;

    private ImageView mDefaultStopView;

    private AudioManager mAudioManager;

    private VideoDataModel mVideoDataModel;

    private ObjectAnimator mRotateAnimator;

    private boolean mIsFullScreen = false;

    private ViewGroup.LayoutParams oriLayoutParams;

    public VideoViewWrapper(ViewGroup rootView, int viewId) {
        initView(rootView,viewId);
    }

    public VideoViewWrapper(Context context, ViewGroup rootView, int viewId) {
        mContext = context;
        initView(rootView,viewId);
        initHandler();
    }

    @SuppressLint("HandlerLeak")
    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == UI_UPDATE) {
                    if (mCustomVideoView == null || mVideoProgressSeekBar == null) {
                        return;
                    }
                    int currentPosition = mCustomVideoView.getCurrentPosition();
                    int duration = mCustomVideoView.getDuration();
                    updateVideoTime(duration,currentPosition);
                    mVideoProgressSeekBar.setMax(duration);
                    mVideoProgressSeekBar.setProgress(currentPosition);
                    mHandler.sendEmptyMessageDelayed(UI_UPDATE,500);

                }

            }
        };
    }

    private void initView(ViewGroup rootView, int viewId) {
        mVideoViewContainer = rootView.findViewById(viewId);

        oriLayoutParams = mVideoViewContainer.getLayoutParams();

        if (mVideoViewLayout == null) {
            mVideoViewLayout = View.inflate(mContext, R.layout.layout_video_view,null);
        }

        mCustomVideoView = mVideoViewLayout.findViewById(R.id.video_view);
        mVideoActionView = mVideoViewLayout.findViewById(R.id.video_actions);
        mVideoProgressSeekBar = mVideoViewLayout.findViewById(R.id.video_progress_bar);
        mVideoVolumeProgressSeekBar = mVideoViewLayout.findViewById(R.id.video_volume_progress);
        mFullScreenView = mVideoViewLayout.findViewById(R.id.video_full_screen);
        mVideoCurrentTimeView = mVideoViewLayout.findViewById(R.id.video_time);
        mVideoTotalTimeView = mVideoViewLayout.findViewById(R.id.video_total_time);
        mLoadingView = mVideoViewLayout.findViewById(R.id.loading_view);
        mDefaultBackgroundView = mVideoViewLayout.findViewById(R.id.default_background_image);
        mDefaultStopView = mVideoViewLayout.findViewById(R.id.default_stop_image);

        mVideoViewContainer.addView(mVideoViewLayout);

        initDefaultStopView();
        initVideoProgress();
        initVideoAction();
        initVideoVolume();
        initFullScreen();
        initCustomVideoListener();

    }

    /**
     * 默认图片点击
     */
    private void initDefaultStopView() {
        mDefaultStopView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCustomVideoView == null || mVideoDataModel == null) {
                    return;
                }
                mDefaultStopView.setVisibility(View.GONE);

                if (mVideoPrepared) {
                    startPlay();
                    mVideoActionView.setImageResource(R.drawable.icon_video_start);
                    return;
                }

                if (mVideoDataModel.getVideoSource() == VideoDataModel.VideoSource.OFFLINE) {
                    loadFromOffline();
                } else if (mVideoDataModel.getVideoSource() == VideoDataModel.VideoSource.ONLINE) {
                    loadFromOnline();
                }
            }
        });
    }

    /**
     * 播放器加载进度监听
     */
    private void initCustomVideoListener() {
        mCustomVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //TODO:播放完成
                if (mVideoActionView != null) {
                    mVideoActionView.setImageResource(R.drawable.icon_video_stop);
                }
                mHandler.removeMessages(UI_UPDATE);
            }
        });
        mCustomVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                if (mLoadingView != null) {
                    mLoadingView.setImageResource(R.drawable.icon_video_error);
                }
                return false;
            }
        });
        mCustomVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mVideoPrepared = true;
                mDefaultBackgroundView.setVisibility(View.GONE);
                if (mLoadingView != null) {
                    mRotateAnimator.cancel();
                    mLoadingView.setVisibility(View.GONE);
                }
                //TODO:视频装载完成
                if (mVideoActionView != null) {
                    mVideoActionView.setImageResource(R.drawable.icon_video_start);
                }
                updateVideoTime(mp.getDuration(),mp.getCurrentPosition());
                mHandler.sendEmptyMessageDelayed(UI_UPDATE,500);
            }
        });
    }

    /**
     * 播放进度监听
     */
    private void initVideoProgress() {
        if (mVideoProgressSeekBar == null) {
            return;
        }
        mVideoProgressSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mVideoDataModel != null && mVideoDataModel.getVideoTotalTime() != 0) {
                    updateVideoTime((int)mVideoDataModel.getVideoTotalTime(),progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mHandler.removeMessages(UI_UPDATE);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                if (mCustomVideoView != null) {
                    mCustomVideoView.seekTo(progress);
                    mCustomVideoView.start();
                    if (mVideoActionView != null) {
                        mVideoActionView.setImageResource(R.drawable.icon_video_start);
                    }
                }
                mHandler.sendEmptyMessageDelayed(UI_UPDATE,500);
            }
        });
    }

    /**
     * 暂停/开始
     */
    private void initVideoAction() {
        if (mVideoActionView == null) {
            return;
        }
        mVideoActionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!mVideoPrepared) {
                    if (mCustomVideoView == null || mVideoDataModel == null) {
                        return;
                    }
                    mDefaultStopView.setVisibility(View.GONE);
                    if (mVideoDataModel.getVideoSource() == VideoDataModel.VideoSource.OFFLINE) {
                        loadFromOffline();
                    } else if (mVideoDataModel.getVideoSource() == VideoDataModel.VideoSource.ONLINE) {
                        loadFromOnline();
                    }
                    mVideoActionView.setImageResource(R.drawable.icon_video_start);
                    return;
                }

                if (mCustomVideoView.isPlaying()) {
                    mVideoActionView.setImageResource(R.drawable.icon_video_stop);
                    mDefaultStopView.setVisibility(View.VISIBLE);
                    mCustomVideoView.pause();
                    mHandler.removeMessages(UI_UPDATE);
                } else {
                    mVideoActionView.setImageResource(R.drawable.icon_video_start);
                    mDefaultStopView.setVisibility(View.GONE);
                    mCustomVideoView.start();
                    mHandler.sendEmptyMessageDelayed(UI_UPDATE,500);
                }
                int currentPositon = mCustomVideoView.getCurrentPosition();
                int duration = mCustomVideoView.getDuration();
                updateVideoTime(duration,currentPositon);
                mVideoProgressSeekBar.setMax(duration);
                if (currentPositon >= duration) {
                    mVideoProgressSeekBar.setProgress(0);
                } else {
                    mVideoProgressSeekBar.setProgress(currentPositon);
                }

            }
        });
    }

    /**
     * 播放音量调整
     */
    private void initVideoVolume() {
        if (mVideoVolumeProgressSeekBar == null) {
            return;
        }

        mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        mVideoVolumeProgressSeekBar.setMax(mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        mVideoVolumeProgressSeekBar.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC));

        mVideoVolumeProgressSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    /**
     * 全屏切换
     */
    private void initFullScreen() {
        if (mFullScreenView == null) {
            return;
        }
        mFullScreenView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContext == null) {
                    return;
                }
                if (mIsFullScreen && mContext.getResources().getConfiguration().orientation == Configuration
                        .ORIENTATION_LANDSCAPE) {
                    ((Activity)mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    if (mFullScreenView != null) {
                        mFullScreenView.setImageResource(R.drawable.icon_video_full_screen);
                    }
                } else {
                    ((Activity)mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    if (mFullScreenView != null) {
                        mFullScreenView.setImageResource(R.drawable.icon_video_no_full_screen);
                    }
                }

            }
        });
    }

    /**
     * 播放时间更新
     * @param totalTime 总时长
     * @param currentPosition 当前播放时长
     */
    private void updateVideoTime(int totalTime,int currentPosition) {
        if (mVideoCurrentTimeView == null || mVideoTotalTimeView == null || mVideoDataModel == null) {
            return;
        }
        mVideoTotalTimeView.setText(TimeUtils.formatToHMS(totalTime));
        mVideoCurrentTimeView.setText(TimeUtils.formatToHMS(currentPosition));

    }

    /**
     * loading旋转动画
     */
    private void palyLoadAnimation() {
        mRotateAnimator = ObjectAnimator.ofFloat(mLoadingView,"rotation",0f,360f,0f);
        mRotateAnimator.setDuration(1000);
        mRotateAnimator.setRepeatMode(ValueAnimator.RESTART);
        mRotateAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mRotateAnimator.setInterpolator(new RotateInterpolator());
        mRotateAnimator.start();
    }

    /**
     * 简易旋转动画插值器
     */
    private class RotateInterpolator implements Interpolator{

        @Override
        public float getInterpolation(float input) {
            return input + 30f;
        }
    }

    /**
     * 设置videoData
     * @param videoDataModel 视频文件的相关信息
     */
    public void setVideoDataModel(VideoDataModel videoDataModel) {
        mVideoDataModel = videoDataModel;
    }

    /**
     * 在线加载
     */
    public void loadFromOnline() {
        if (mCustomVideoView == null || mVideoDataModel == null) {
            return;
        }

        mCustomVideoView.setVideoURI(Uri.parse(mVideoDataModel.getVideoUrl()));
        mCustomVideoView.start();
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.VISIBLE);
            palyLoadAnimation();
        }
    }

    /**
     * 本地加载
     */
    public void loadFromOffline() {
        if (mCustomVideoView == null || mVideoDataModel == null) {
            return;
        }
        mCustomVideoView.setVideoPath(mVideoDataModel.getVideoLocalUri());
        mCustomVideoView.start();
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.VISIBLE);
            palyLoadAnimation();
        }

    }

    /**
     * 开始播放
     */
    public void startPlay() {
        mCustomVideoView.start();
    }

    /**
     * 暂停播放
     */
    public void pausePlay() {
        if (mCustomVideoView == null) {
            return;
        }
        mCustomVideoView.pause();
    }

    /**
     * 继续播放
     */
    public void resume() {
        if (mCustomVideoView == null) {
            return;
        }
        mCustomVideoView.resume();
    }

    /**
     * 停止播放
     */
    public void stopPlay() {
        if (mCustomVideoView == null) {
            return;
        }
        mCustomVideoView.stopPlayback();
    }

    public void switchToFullScreen(boolean isFullScreen) {
        if (mVideoViewLayout == null) {
            return;
        }
        mIsFullScreen = isFullScreen;

        if (isFullScreen) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams
                    .MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            mVideoViewContainer.setLayoutParams(layoutParams);
        } else {
            mVideoViewContainer.setLayoutParams(oriLayoutParams);
        }

    }

}
