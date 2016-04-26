package com.example.cameratest.utils;

import android.annotation.SuppressLint;
import android.media.MediaRecorder;
import android.util.Log;

@SuppressLint("SdCardPath")
public class MediaUtil {
	//使用录音机的工具类
	private static final String TAG = "MediaUtil";

	//MediaRecorder可以实现录音和录像。
	private MediaRecorder mRecorder;
	//开始的标志
	private boolean mStartedFlg = false;
	//正在录制的标志
	private boolean isRecording = false;


	public void mediaStop() {
		// stop
		if (mStartedFlg) {
			try {
				isRecording = false;
				Log.d(TAG, "Stop recording ...");
				Log.d(TAG, "bf mRecorder.stop(");
				mRecorder.stop();
				Log.d(TAG, "af mRecorder.stop(");
				mRecorder.reset();
									
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		mStartedFlg = false;
	}

	public void audioStop() {
		isRecording = false;
	}
}
