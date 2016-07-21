package com.example.cameratest.liveness;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import android.graphics.Bitmap;
import android.util.Log;

/**
*compute the contrast ratio of face image
*@author zhao
*/
public class ContrastCompute {
	public static double computeContrastRatio(Mat faceMat){
//		Mat rgbMat = new Mat();
//		Mat grayMat = new Mat();
//		Utils.bitmapToMat(image, rgbMat);
//		Imgproc.cvtColor(rgbMat, grayMat, Imgproc.COLOR_RGBA2GRAY);
//		Mat faceMat=new Mat(grayMat, new Rect(200,200,100,100));	
		//获取脸部区域，需要精确调节,或者根据人脸检测结果获取
	
		double s=0;
		for(int i=1;i<faceMat.height()-1;i++){
			for (int j=1;j<faceMat.width()-1;j++) {
				double st=Math.pow((faceMat.get(i,j)[0]-faceMat.get(i-1,j)[0]), 2)+
						Math.pow((faceMat.get(i,j)[0]-faceMat.get(i+1,j)[0]), 2)+
						Math.pow((faceMat.get(i,j)[0]-faceMat.get(i,j-1)[0]), 2)+
						Math.pow((faceMat.get(i,j)[0]-faceMat.get(i,j+1)[0]), 2);
				s+=st;
						
			}
		}
		int k=4*(faceMat.width()-2)*(faceMat.height()-2);
		
		return s/k;
		
	}
}
