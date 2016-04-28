package com.example.cameratest.liveness;
/**
*NDK support for extension
*@author zhao
*/

public class LivenessDetection {

	static {
		System.loadLibrary("liveness");
	}

	public static native boolean detectLiveness(int[] image);

}
