package com.example.cameratest.service.impl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.media.MediaRecorder;
import android.util.Log;
import android.view.SurfaceHolder;

import com.example.cameratest.service.AbstractCameraBaseService;
import com.example.cameratest.service.VideoService;
import com.example.cameratest.utils.MediaUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.opencv.android.Utils;
import org.opencv.core.Mat;

/**
 * 录音实现类
 * Created by jfhuang on 2015/5/29.
 */
public class VideoServiceImpl extends AbstractCameraBaseService implements VideoService {

    private Camera            camera;
    //这个MediaRecorder类也可以录音
    private MediaRecorder     recorder;
    private SurfaceHolder     mSurfaceHolder;
    private String            fileName;
    //这个MediaUtil主要用到了AudioRecorder
    private MediaUtil mediaUtil = new MediaUtil();
    //相机参数
    private Camera.Parameters m_p;
    private int               PreviewWidth;
    private int               PreviewHeight;
    private int               VideoWidth;
    private int               VideoHeight;
    private boolean           recording = false;

    /**
     *
     * @param mSurfaceHolder
     * @param fileName: media file absolute path
     */
    public VideoServiceImpl(SurfaceHolder mSurfaceHolder, String fileName) {
        this.mSurfaceHolder = mSurfaceHolder;
        this.fileName = fileName;
    }

    /**
     * video register's pictures & voicestartMediaAndAudioDevice
     */
    public void videoTapes() {
        //如果正在录
        if (!recording) {
            try {
                if (camera != null) {
                    //停止相机预览
                    camera.stopPreview();
                    //释放相机底层的硬件资源
                    camera.release();
                    camera = null;
                }
                //创建MediaRecorder对象
                recorder = new MediaRecorder();
                //创建File，用于存储录制的数据信息
                File myRecVideoFile = new File(fileName);
                if (!myRecVideoFile.exists()) {
                    //如果该文件不存在，就创建这个文件
                    myRecVideoFile.getParentFile().mkdirs();
                    myRecVideoFile.createNewFile();
                }
                //重置recorder对象
                //recorder.reset();
                //找到前置摄像头
                int CammeraIndex = findFrontCamera();
                if (CammeraIndex == -1) {
                    //如果没有前置摄像头，则使用后置摄像头
                    CammeraIndex = findBackCamera();
                }
                //打开一个摄像头
                camera = Camera.open(CammeraIndex);
                //设置摄像头参数
                camera.setParameters(m_p);

                //set display orientation
                //设置摄像头方向
                setCameraDisplayOrientation(camera);
                //打开相机允许另一个进程访问它。一般情况下，镜头与主动相机的进程锁定
                camera.unlock();
                //把摄像头设置给 recorder对象;
                recorder.setCamera(camera);
                // recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                //设置视频源
                recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                //设置输出格式
                recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                // recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                // recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                //设置视频编码类型
                recorder.setVideoEncoder(MediaRecorder.VideoEncoder.H263);
                //设置输出文件
                recorder.setOutputFile(myRecVideoFile.getAbsolutePath());
                //设置预览效果
                recorder.setPreviewDisplay(mSurfaceHolder.getSurface());
                //设置要捕获的视频的宽度和高度
                recorder.setVideoSize(640, 480);//640 480
                //提高清晰度的关键(设置录制的视频编码比特率)
                recorder.setVideoEncodingBitRate(5 * 1024 * 1024);
                //设置最大文件大小（以字节为单位）的录制
                recorder.setMaxFileSize(10 * 1024 * 1024);
                //准备摄像头。
                recorder.prepare();
                //开始摄像。
                recorder.start();
                //正在录制设置为true。
                recording = true;
            } catch (IOException e1) {
                releaseMediaRecorder();
                if (camera != null) {
                    camera.stopPreview();
                    camera.release();
                    camera = null;
                }
            } catch (IllegalStateException e) {
                releaseMediaRecorder();
                if (camera != null) {
                    camera.stopPreview();
                    camera.release();
                    camera = null;
                }
            }
        }
    }

    @Override
    public int findFrontCamera() {
        int cameraCount = 0;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();

        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                return camIdx;
            }
        }
        return -1;
    }

    @Override
    public int findBackCamera() {
        int cameraCount = 0;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();

        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                return camIdx;
            }
        }
        return -1;
    }

    //释放MediaRecorder
    public void releaseMediaRecorder() {
        if (recorder != null) {
            try {
                recorder.setOnErrorListener(null);
                recorder.setPreviewDisplay(null);
                recorder.stop();
                recorder.release();
                recorder = null;
            } catch (Exception e) {
                Log.e("VideoService : ", "Failed to stop Media recorder.");
            }
        }
    }

    /**
     *Start Recorder & audio device
     */
    public void startMediaAndAudioDevice() {
        if (recorder != null) {
            releaseMediaRecorder();
            recording = false;
        }
        videoTapes();
    }

    /**
     * stop media & audio deice
     */
    public void stopMediaAndAudioDevice() {
        if (recorder != null) {
            releaseMediaRecorder();
            recording = false;
        }
        if (camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
        mediaUtil.mediaStop();
        mediaUtil.audioStop();
    }

    /**
     * destroy camera
     */
    public void destroyCamera() {
        camera.stopPreview();
        camera.release();
        camera = null;
    }

    /**
     * get initialized camera
     *
     * @return
     */
    @Override
    public Camera getCamera() {
        if (camera == null) {
            return createSurface();
        } else {
            return camera;
        }
    }

    /**
     * create a new camera
     * 开启前置或者后置摄像头
     */
    @Override
    public Camera createSurface() {
        // 开启相机
        if (camera == null) {
            int CammeraIndex = findFrontCamera();
            if (CammeraIndex == -1) {
                CammeraIndex = findBackCamera();
            }
            camera = Camera.open(CammeraIndex);
            try {
                camera.setPreviewDisplay(mSurfaceHolder);
                //specially, ipad can work well: TODO:
                //camera.setDisplayOrientation(90);
                camera.setDisplayOrientation(90);
            } catch (IOException e) {
                e.printStackTrace();
                camera.release();
            }
        }
        return camera;
    }

    /**
     * destroy a camera
     * 销毁一个摄像头。
     */
    @Override
    public void destroySurface() {
        // 关闭预览并释放资源
        if (camera != null) {
        	
        	/////////
        	///这里是我写的
        	camera.setPreviewCallback(null);
        	////////////
        	
        	
            camera.stopPreview();
            camera.release();
            camera = null;
        }
        releaseMediaRecorder();
    }

    /**
     * initialize camera parameters
     * 初始化相机参数
     */
    @Override
    public void changeSurface() {
        if (null != camera) {
            //获取摄像头参数对象
            m_p = camera.getParameters();
            
            //我写的
            m_p.setPictureFormat(ImageFormat.NV21);
           
            
            
            // 选择合适的预览尺寸
            List<Camera.Size> sizeList = m_p.getSupportedPreviewSizes();
            List<Camera.Size> sizeList2 = m_p.getSupportedVideoSizes();

            if (sizeList.size() > 1) {
                Iterator<Camera.Size> itor = sizeList.iterator();
                boolean bIsInIf = false;
                while (itor.hasNext()) {
                    Camera.Size cur = itor.next();
                    if (((double) cur.width / cur.height == (double) 4 / 3)
                            || ((double) cur.width / cur.height == (double) 16 / 9)
                            || ((double) cur.width / cur.height == (double) 8 / 5)
                    // cur.width==1920&&cur.height==1080
                    // true
                    ) {
                        PreviewWidth = cur.width;
                        PreviewHeight = cur.height;
                        bIsInIf = true;
                        // Toast.makeText(getApplicationContext(),
                        // PreviewWidth + "if " + PreviewHeight,
                        // Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                if (!bIsInIf) {
                    PreviewWidth = sizeList.get(0).width;
                    PreviewHeight = sizeList.get(0).height;
                }
                // 设置Preview(预览)的尺寸
                m_p.setPreviewSize(PreviewWidth, PreviewHeight);
                
                /////////
            	///这里是我写的
                
                //m_p.setPreviewSize(1080, 1920);
                //
                // Toast.makeText(getApplicationContext(),
                // PreviewWidth+""+PreviewHeight,
                // Toast.LENGTH_SHORT).show();
            }
            sizeList2 = (sizeList2 == null ? sizeList : sizeList2);
            if (sizeList2 != null && sizeList2.size() > 1) {
                Iterator<Camera.Size> itor2 = sizeList2.iterator();

                while (itor2.hasNext()) {
                    Camera.Size cur2 = itor2.next();
                    if (((double) cur2.width / cur2.height == (double) 4 / 3)
                            || ((double) cur2.width / cur2.height == (double) 16 / 9)
                            || ((double) cur2.width / cur2.height == (double) 8 / 5)) {
                        VideoWidth = cur2.width;
                        VideoHeight = cur2.height;
                        /* Toast.makeText(getApplicationContext(),
                                "Video	" + VideoWidth + " " + VideoHeight,
                                Toast.LENGTH_LONG).show();*/
                        break;
                    }
                }
            }
        }
        
        ///NV16  JPEG
        
        camera.setParameters(m_p);
        
        /////////
    	///这里是我写的
        camera.setPreviewCallback(new PreviewCallbackInstance());
        //////////////
        
        camera.startPreview();
    }

    @Override
    public void setCameraDisplayOrientation(Camera myCamera) {
        myCamera.setDisplayOrientation(90);
    }

    @Override
    public Bitmap createPhotoByDeviceType(Bitmap mBitmap) {
        return null;
    }

    @Override
    public byte[] readSnapAudio(String origFile, String audioFile) {
        return new byte[0];
    }
    
    /////////
	///这里是我写的
    private class PreviewCallbackInstance implements PreviewCallback{

		@Override
		public void onPreviewFrame(byte[] data, Camera camera) {
			// TODO Auto-generated method stub
			//默认格式是JPEG
			Log.d("main", "preview called"+m_p.getPictureFormat());
			if(m_p.getPictureFormat()==ImageFormat.NV21){
			//	Log.d("main", "Format is NV21");
			}
			
			 Camera.Parameters parameters = camera.getParameters();
			 int width = parameters.getPreviewSize().width;
			 int height = parameters.getPreviewSize().height;

			 YuvImage yuv = new YuvImage(data, parameters.getPreviewFormat(), width, height, null);

			 ByteArrayOutputStream out = new ByteArrayOutputStream();
			 yuv.compressToJpeg(new Rect(0, 0, width, height), 50, out);
//此处可以尝试直接设置ImageFormat为JPEG,会出错
			 byte[] bytes = out.toByteArray();
			 Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
			 			 
			// Log.d("main", "width"+bitmap.getWidth()+"height"+bitmap.getHeight());
			 Mat rgbMat=new Mat();
			 Utils.bitmapToMat(bitmap, rgbMat);
			 Log.d("main", "a"+rgbMat.get(10,10)[0]);
			 //Log.d("mat is ", "width is"+rgbMat.width()+"height is"+rgbMat.height());
		}
		
	}
	
    
}
