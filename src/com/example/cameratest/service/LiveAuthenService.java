package com.example.cameratest.service;

/**
 * Created by print on 2016/2/23.
 */
public interface LiveAuthenService {

    /**
     * video register's pictures & voice
     * //注册者的图片和声音
     */
    public void videoTaps();
    /**
     * stop media & audio device
     * //停止视屏和音频设备
     */
    public void stopMediaAndAudioDevice();

    /**
     *得到视屏服务
     * @return
     */
    public VideoService getVideoService();
}
