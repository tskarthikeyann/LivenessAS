package com.example.cameratest.liveness;
/**
*NDK support for extension
*@author zhao
*/

public class LivenessDetection {

	public static native boolean detectLiveness(int[] image);

}
