package com.example.cameratest.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.Calendar;

public class FilePath {

	public String createPath(String username, String type) {
		// TODO Auto-generated method stub

		// Set output file path
		String path = getSDPath();
		// String date = getDate();
		String fullpath = "";
		if (path != null) {
			// sd卡绝对路径+"/xiangji_test"+username+"/"+type;
			path = path + "/xiangji_test/" + username + "/" + type;
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			fullpath = dir + "/";
			// fullpath = dir+"/";
		}
		return fullpath;
	}

	/**
	 * 获取SD path
	 * 
	 * @return
	 */
	public String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); // 判断sd卡是否存在,即判断sd卡是否挂载
		if (sdCardExist)// 如果sd卡已经挂载。
		{
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录(获取sd卡根目录)(得到的是File类型)
			// return sdDir.toString();
		}

		return Environment.getExternalStorageDirectory().getAbsolutePath();// 获取sd卡根目录的绝对路径。
	}

	/**
	 * 获取系统时间
	 * 
	 * @return
	 */
	public static String getDate() {
		Calendar ca = Calendar.getInstance();
		int year = ca.get(Calendar.YEAR); // 获取年份
		int month = ca.get(Calendar.MONTH); // 获取月份
		int day = ca.get(Calendar.DATE); // 获取日
		int minute = ca.get(Calendar.MINUTE); // 分
		int hour = ca.get(Calendar.HOUR_OF_DAY); // 小时
		int second = ca.get(Calendar.SECOND); // 秒

		String date = "" + year + (month + 1) + day + hour + minute + second;
		Log.i("date:", date);

		return date;
	}

}
