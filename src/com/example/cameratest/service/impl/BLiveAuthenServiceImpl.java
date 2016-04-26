package com.example.cameratest.service.impl;

import android.graphics.Bitmap;
import android.hardware.Camera;
import android.view.SurfaceHolder;

import com.example.cameratest.service.AbstractLiveAuthenService;


/**
 * @author Mike
 * @project LIDA
 * @date 12/1/15, 3:58 PM
 * @e-mail mike@mikecoder.net
 */
public class BLiveAuthenServiceImpl extends AbstractLiveAuthenService {
    /**
     * constructor
     *
     * @param mSurfaceHolder
     */
    public BLiveAuthenServiceImpl(SurfaceHolder mSurfaceHolder) {
        super(mSurfaceHolder);
    }

    @Override
    protected void setType() {

    }

    @Override
    public Bitmap createPhotoByDeviceType(Bitmap mBitmap) {
        Bitmap finalBitmap = createPhoneJpeg(mBitmap);
        return finalBitmap;
    }

    @Override
    public byte[] readSnapAudio(String origFile, String audioFile) {
        return new byte[0];
    }

    @Override
    public void setCameraDisplayOrientation(Camera myCamera) {
        myCamera.setDisplayOrientation(90);
    }
}
