package com.example.cameratest.service;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Created by print on 2016/2/23.
 */
public abstract class AbstractBaseService  implements BaseService{



    /**
     * create mobile phone picture
     * @param mBitmap
     */
    public Bitmap createPhoneJpeg(Bitmap mBitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate((float) 90.0);
        Bitmap rotaBitmap = Bitmap.createBitmap(mBitmap, 0, 0,
                mBitmap.getWidth(), mBitmap.getHeight(), matrix, false);

        Bitmap sizeBitmap = Bitmap.createScaledBitmap(rotaBitmap, 960,
                1280, true);
        Bitmap rectBitmap = Bitmap.createBitmap(sizeBitmap, 0, 334, 960,
                505);
        sizeBitmap = null;
        return rectBitmap;
    }

    /**
     * create ipad picture
     * @param mBitmap
     */
    private Bitmap createIpadJpeg(Bitmap mBitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate((float) 0.0);
        int width = (303 * 247) / 72;
        int height = (206 * 247) / 72;
        int startX = mBitmap.getWidth() * (2560 - width) / (2 * 2560);
        int startY = mBitmap.getHeight() * (1600 - height) / (2 * 1600);
        int validWidth = width * mBitmap.getWidth() / 2560;
        int validHeight = height * mBitmap.getHeight() / 1600;
        Bitmap rotaBitmap = Bitmap.createBitmap(mBitmap, startX, startY, validWidth, validHeight);

        return rotaBitmap;
    }



}
