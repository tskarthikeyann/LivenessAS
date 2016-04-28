package com.example.cameratest.service;

import android.graphics.Bitmap;

/**
 * Created by print on 2016/2/23.
 */
public interface BaseService {

	/**
	 * create bitmap according to different devices
	 * 
	 * @param mBitmap
	 */
	public Bitmap createPhotoByDeviceType(Bitmap mBitmap);

	/**
	 * convert pcm file to wav file, and return wav byte array.
	 * 
	 * @param origFile:file
	 *            with suffix .pcm
	 * @param audioFile:
	 *            file with suffix ".wav",
	 * @return wav file bytes
	 */
	public byte[] readSnapAudio(String origFile, String audioFile);

}
