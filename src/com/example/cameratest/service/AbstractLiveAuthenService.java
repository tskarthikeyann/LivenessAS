package com.example.cameratest.service;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;

import com.example.cameratest.service.impl.VideoServiceImpl;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * Created by print on 2016/2/24.
 */
public abstract class AbstractLiveAuthenService extends AbstractCameraBaseService implements LiveAuthenService {


    //camera required
    private SurfaceHolder mSurfaceHolder;

    private byte[] snap_picture;//相机图片

    private VideoServiceImpl videoService = null;

    private String fileName;


    protected abstract void setType();

    /**
     * constructor
     */
    public AbstractLiveAuthenService(SurfaceHolder mSurfaceHolder) {
        this.mSurfaceHolder = mSurfaceHolder;
        this.initialize();
        setType();
    }

    /**
     * initialize snap_audio, fileName, & snap_picture
     *
     * @throws Exception
     */
    private void initialize() {
        //assign data to fileName & name
        //获取SD卡根路径(fileName为String类型的)
        fileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        //设置文件名
        String name = "momomomo" + ".3gp";
        //感觉这边File.separator多了一个，不要在意这个细节吗
        fileName += File.separator + File.separator + "xiangji_test"
                + File.separator + name;

        videoService = new VideoServiceImpl(mSurfaceHolder, fileName);
    }


    /**
     * video register's pictures & voice
     */
    @Override
    public void videoTaps() {
        getVideoService().startMediaAndAudioDevice();
    }


    /**
     * read snap picture
     *
     * @return byte[]
     */
    public byte[] readSnapPicture() {
        if (snap_picture != null) {
            return snap_picture;
        }
        Bitmap bm = createVideoPicture();
        try {
            FileOutputStream fout = new FileOutputStream(
                    "/mnt/sdcard/xiangji_test/"
                            + System.currentTimeMillis() + ".jpg");
            BufferedOutputStream bos = new BufferedOutputStream(fout);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();

            ByteArrayOutputStream bom = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, bom);
            snap_picture = bom.toByteArray();
            bom.flush();
            bom.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return snap_picture;
    }


    @SuppressLint({"NewApi", "UseValueOf", "SdCardPath"})
    public Bitmap createVideoPicture() {
        String videoName = "/mnt/sdcard/xiangji_test/momomomo.3gp";
        Bitmap newBitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            Random rdm = new Random(System.currentTimeMillis());
            int t = Math.abs(rdm.nextInt()) % 3 + 2;
            retriever.setDataSource(videoName);
            newBitmap = retriever.getFrameAtTime((long) 1000 * 1000 * t);
        } catch (Exception e) {
            Log.e("PowerMediaProject-ScreenshotManager-createVideoPicture",
                    "fail to videoScreen" + e.getMessage());
        }
        Matrix matrix = new Matrix();
        matrix.postRotate((float) 270.0);
        //matrix.postRotate((float) 0.0);
        assert newBitmap != null;
        return Bitmap.createBitmap(newBitmap, 0, 0,
                newBitmap.getWidth(), newBitmap.getHeight(), matrix, false);
    }

    /**
     * stop media & audio deice
     */
    @Override
    public void stopMediaAndAudioDevice() {
        getVideoService().stopMediaAndAudioDevice();
    }

    @Override
    public VideoService getVideoService() {
        if (videoService == null) {
            //initial camera
            return videoService = new VideoServiceImpl(mSurfaceHolder, fileName);
        } else {
            System.out.println("videoService is not null: " + videoService.toString());
            return videoService;
        }
    }

    /**
     * get initialized camera
     *
     * @return
     */
    @Override
    @Deprecated
    public Camera getCamera() {
        return null;
    }

    /**
     * create a new camera
     */
    @Override
    public Camera createSurface() {
        return videoService.createSurface();
    }

    /**
     * destroy a camera
     */
    @Override
    public void destroySurface() {
        videoService.destroySurface();
    }

    /**
     * initialize camera parameters
     */
    @Override
    public void changeSurface() {
        videoService.changeSurface();
    }


}
