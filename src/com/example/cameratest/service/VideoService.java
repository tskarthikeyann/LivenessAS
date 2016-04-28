package com.example.cameratest.service;

/**
 * 视频的服务 Created by print on 2016/2/23.
 */
public interface VideoService {

	/**
	 * video register's pictures & voice
	 */
	public void videoTapes();

	/**
	 * 查找前置摄像头
	 * 
	 * @return
	 */
	public int findFrontCamera();

	/**
	 * 查找后置摄像头
	 * 
	 * @return
	 */
	public int findBackCamera();

	/**
	 * Start Recorder & audio device 开启音频和视频设备
	 */
	public void startMediaAndAudioDevice();

	/**
	 * stop media & audio deice
	 */
	public void stopMediaAndAudioDevice();

	/**
	 * destroy Camera
	 */
	public void destroyCamera();

}
