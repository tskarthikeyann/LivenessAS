package com.example.cameratest.utils;

import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.util.Log;

import java.util.List;

public class CamParaUtil {
	private static final String TAG = "CamParaUtil";
	private static CamParaUtil myCamPara = null;
	private CamParaUtil(){

	}
	//单例获取Camera
	public static CamParaUtil getInstance(){
		if(myCamPara == null){
			myCamPara = new CamParaUtil();
			return myCamPara;
		}
		else{
			return myCamPara;
		}
	}





	
	public  void printSupportPreviewSize(Camera.Parameters params){
		List<Size> previewSizes = params.getSupportedPreviewSizes();
		for(int i=0; i< previewSizes.size(); i++){
			Size size = previewSizes.get(i);
			Log.e(TAG, "previewSizes:width = "+size.width+" height = "+size.height);
		}
	
	}

	
	public  void printSupportPictureSize(Camera.Parameters params){
		List<Size> pictureSizes = params.getSupportedPictureSizes();
		for(int i=0; i< pictureSizes.size(); i++){
			Size size = pictureSizes.get(i);
			Log.e(TAG, "pictureSizes:width = "+ size.width +" height = " + size.height);
		}
	}
	
	
	public void printSupportFocusMode(Camera.Parameters params){
		List<String> focusModes = params.getSupportedFocusModes();
		for(String mode : focusModes){
			Log.e(TAG, "focusModes--" + mode);
		}
	}

    
    public static void printCurrentCameraInfo(Camera camera ) {
    	Size csize = camera.getParameters().getPreviewSize();
        Log.e(TAG , "current previewSize:width: " + csize.width + " height: " + csize.height);
        csize = camera.getParameters().getPictureSize();
        Log.e(TAG , "current pictrueSize:width: " + csize.width + " height: " + csize.height);
//        Log.e(TAG , "current previewFormate is "  + camera.getParameters().getPreviewFormat());
//        Log.e(TAG , "current previewFrametate is " + camera.getParameters().getPreviewFrameRate());
    }
}
