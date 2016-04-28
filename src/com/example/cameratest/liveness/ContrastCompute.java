package com.example.cameratest.liveness;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import android.graphics.Bitmap;

/**
*compute the contrast ratio of face image
*@author zhao
*/
public class ContrastCompute {
	public static double computeContrastRatio(Bitmap image){
		Mat rgbMat = new Mat();
		Mat grayMat = new Mat();
		Utils.bitmapToMat(image, rgbMat);
		Imgproc.cvtColor(rgbMat, grayMat, Imgproc.COLOR_RGBA2GRAY);
		
		//获取脸部区域，需要精确调节,或者根据人脸检测结果获取
		Mat face=new Mat(grayMat, new Rect(200,200,200,200));
		
		double s=0;
		for(int i=1;i<grayMat.width()-1;i++){
			for (int j=1;j<grayMat.height()-1;j++) {
				double st=Math.pow((face.get(i,j)[0]-face.get(i-1,j)[0]), 2)+
						Math.pow((face.get(i,j)[0]-face.get(i+1,j)[0]), 2)+
						Math.pow((face.get(i,j)[0]-face.get(i,j-1)[0]), 2)+
						Math.pow((face.get(i,j)[0]-face.get(i,j+1)[0]), 2);
				s+=st;
						
			}
		}
		int k=(grayMat.width()-1)*(grayMat.height()-1);
		
		return s/k;
		
	}
}
