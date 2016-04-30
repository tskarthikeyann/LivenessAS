LOCAL_PATH := $(call my-dir)

#Prebuilt opencv
include $(CLEAR_VARS)
LOCAL_MODULE := opencv_java3
LOCAL_SRC_FILES := prebuild/libopencv_java3.so
include $(PREBUILT_SHARED_LIBRARY)

#Prebuilt dlib
include $(CLEAR_VARS)
LOCAL_MODULE := people_set
LOCAL_SRC_FILES := prebuild/libpeople_det.so
include $(PREBUILT_SHARED_LIBRARY)


#build in this project
include $(CLEAR_VARS)

LOCAL_MODULE := liveness
LOCAL_SRC_FILES := liveness.cpp

LOCAL_SHARED_LIBRARIES := opencv_java3

include $(BUILD_SHARED_LIBRARY)