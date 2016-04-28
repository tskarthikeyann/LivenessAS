package com.example.cameratest.service;

import android.hardware.Camera;

/**
 * Created by print on 2016/2/23.
 */
public interface CameraBaseService {

	/**
	 * get initialized camera
	 * 
	 * @return
	 */
	public Camera getCamera();

	/**
	 * create a new camera
	 */
	public Camera createSurface();

	/**
	 * destroy a camera
	 */
	public void destroySurface();

	/**
	 * initialize camera parameters
	 */
	public void changeSurface();

	/**
	 * according to device type, set camera's display orientation
	 * 
	 * @param myCamera
	 */
	public void setCameraDisplayOrientation(Camera myCamera);
}
