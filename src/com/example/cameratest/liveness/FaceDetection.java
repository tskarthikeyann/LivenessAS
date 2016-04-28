package com.example.cameratest.liveness;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import com.example.cameratest.R;
import com.example.cameratest.activities.MyApplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.FaceDetector;
import android.os.Environment;
import android.util.Log;
/**
*Face detection with OpenCV and Android API
*@author zhao
*/
public class FaceDetection {
	public static CascadeClassifier mCascadeClassifier = null;
	public static File mCascadeFile;
	static {
		try {
			// load cascade file from application resources
			InputStream is = MyApplication.getContext().getResources().openRawResource(R.raw.lbpcascade_frontalface);
			File cascadeDir = MyApplication.getContext().getDir("cascade", Context.MODE_PRIVATE);
			mCascadeFile = new File(cascadeDir, "lbpcascade_frontalface.xml");
			FileOutputStream os = new FileOutputStream(mCascadeFile);

			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = is.read(buffer)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			is.close();
			os.close();

			mCascadeClassifier = new CascadeClassifier(mCascadeFile.getAbsolutePath());
			if (mCascadeClassifier.empty()) {
				Log.e("main", "Failed to load cascade classifier");
				mCascadeClassifier = null;
			} else
				Log.i("main", "Loaded cascade classifier from " + mCascadeFile.getAbsolutePath());

		} catch (IOException e) {
			e.printStackTrace();
			Log.e("main", "Failed to load cascade. Exception thrown: " + e);
		}
	}

	public static Mat faceDetectWithOpenCV(Bitmap image) {

		Mat rgbMat = new Mat();
		Mat grayMat = new Mat();
		Utils.bitmapToMat(image, rgbMat);
		Imgproc.cvtColor(rgbMat, grayMat, Imgproc.COLOR_RGBA2GRAY);

		if (mCascadeClassifier.empty()) {
			Log.d("main", "Cascade can not loaded");
		}

		MatOfRect faces = new MatOfRect();

		mCascadeClassifier.detectMultiScale(grayMat, faces);
		Rect[] rectFaces = faces.toArray();
		//Log.d("main", "OpenCV faces "+rectFaces.length);
		if (rectFaces.length > 0) {
			Mat faceMat=new Mat(grayMat,rectFaces[0]);
			return faceMat;
		} else {
			return null;
		}

	}

	public static boolean faceDetectWithGoogle(Bitmap image) {
		if (image == null) {
			Log.d("main", "image null");
			return false;
		}
		FaceDetector mFaceDetector = new FaceDetector(image.getWidth(), image.getHeight(), 2);
		FaceDetector.Face[] mFaces = new FaceDetector.Face[2];
		int numOfFaces = mFaceDetector.findFaces(image, mFaces);
		// Log.d("main", "face is "+numOfFaces);
		if (numOfFaces > 0) {
			return true;
		} else {
			return false;
		}
	}

}
