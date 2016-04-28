package com.example.cameratest.activities;

import org.opencv.android.OpenCVLoader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

public class LidaActivity extends Activity {

	public static LidaActivity lidaActivity;

	public static void myToastText(String msg) {
		Toast.makeText(getMyContext(), msg, Toast.LENGTH_SHORT).show();
	}

	public static Context getMyContext() {
		return lidaActivity;
	}

	/**
	 * 进度条提示框
	 */
	public static ProgressDialog progressDialog;

	/// load so file
	static {
		OpenCVLoader.initDebug();
		System.loadLibrary("liveness");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		progressDialog = new ProgressDialog(this);
		lidaActivity = this;
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 竖屏
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 没有标题
	}

}
