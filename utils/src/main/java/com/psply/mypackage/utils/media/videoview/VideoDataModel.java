package com.psply.mypackage.utils.media.videoview;

/**
 * Data: 2019/1/12
 * Author: shipan
 * Description:
 */
public class VideoDataModel {

    private String mVideoUrl;

    private String mVideoLocalUri;

    private String mVideoSize;

    private long mVideoTotalTime;

    public String getVideoUrl() {
        return mVideoUrl;
    }

    public void setVideoUrl(String mVideoUrl) {
        this.mVideoUrl = mVideoUrl;
    }

    public String getVideoLocalUri() {
        return mVideoLocalUri;
    }

    public void setVideoLocalUri(String mVideoLocalUri) {
        this.mVideoLocalUri = mVideoLocalUri;
    }

    public String getVideoSize() {
        return mVideoSize;
    }

    public void setVideoSize(String mVideoSize) {
        this.mVideoSize = mVideoSize;
    }

    public long getVideoTotalTime() {
        return mVideoTotalTime;
    }

    public void setVideoTotalTime(long mVideoTotalTime) {
        this.mVideoTotalTime = mVideoTotalTime;
    }
}
