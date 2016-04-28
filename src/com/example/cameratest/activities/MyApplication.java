package com.example.cameratest.activities;

import android.app.Application;
import android.content.Context;

/**
*This class is used to get global context
*@author zhao
*/

public class MyApplication extends Application {
	private static Context context;

	@Override
	public void onCreate() {
		context = getApplicationContext();
	}

	public static Context getContext() {
		return context;
	}
}
