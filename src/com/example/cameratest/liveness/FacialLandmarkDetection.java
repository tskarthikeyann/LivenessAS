package com.example.cameratest.liveness;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.opencv.objdetect.CascadeClassifier;

import com.example.cameratest.R;
import com.example.cameratest.activities.MyApplication;
import com.tzutalin.dlib.Constants;
import com.tzutalin.dlib.PeopleDet;
import com.tzutalin.dlib.VisionDetRet;

import android.content.Context;
import android.util.Log;

public class FacialLandmarkDetection {
	
	static{
			// load facial landmark model file from application resources
			InputStream in = MyApplication.getContext().getResources().openRawResource(R.raw.shape_predictor_68_face_landmarks);
	        FileOutputStream out = null;
	        try {
	            out = new FileOutputStream(Constants.getFaceShapeModelPath());
	            byte[] buff = new byte[1024];
	            int read = 0;
	            while ((read = in.read(buff)) > 0) {
	                out.write(buff, 0, read);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (in != null) {
	                    in.close();
	                }
	                if (out != null) {
	                    out.close();
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	}

	public static void facialLandmarkDetectWithDlib(String path){
		
		PeopleDet peopleDet = new PeopleDet();
		List<VisionDetRet> personList = peopleDet.detPerson(path);
		
	}
	
}
