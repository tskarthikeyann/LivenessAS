package com.example.cameratest.liveness;

public class LivenessDetection {

	static {
		System.loadLibrary("liveness");
	}

	public static native boolean detectLiveness(int[] image);

}
