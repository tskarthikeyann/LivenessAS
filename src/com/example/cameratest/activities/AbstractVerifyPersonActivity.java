package com.example.cameratest.activities;

import com.example.cameratest.R;
import com.example.cameratest.service.AbstractLiveAuthenService;
import com.example.cameratest.service.impl.BLiveAuthenServiceImpl;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


public abstract class AbstractVerifyPersonActivity extends LidaActivity implements SurfaceHolder.Callback {

    static final int MAX_FAIL_NUM = 3;

    TextView      read_text;
    Button        btnStart;
    Button        btnNext;
    SurfaceView   mSurfaceView;
    SurfaceHolder mSurfaceHolder;

    //Identify User
    AbstractLiveAuthenService authenService = null;


    Intent previous     = null;
    String read_content = "";

    private String pictureUrl;//图片地址

    abstract void setType();
    protected void setAuthenService() {
        authenService = new BLiveAuthenServiceImpl(mSurfaceHolder);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Log.d("main", "on create");
        setContentView(R.layout.activity_verifyvideo);
        setType();
        setViews();
    }

    private void setViews() {
        //得到（要读的下面）下面的文字了。(真人活体验证)
        read_text = (TextView) findViewById(R.id.read_content);
        read_text.setText(read_content);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mSurfaceView = (SurfaceView) findViewById(R.id.videoView);
        mSurfaceView.setZOrderOnTop(false);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mSurfaceHolder.setKeepScreenOn(true);

        //initialize Live ID authentication
        setAuthenService();

        btnStart = (Button) findViewById(R.id.btn_video_start);


        btnStart.setOnTouchListener(new StartBtnListener());

    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        authenService.createSurface();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        authenService.changeSurface();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        authenService.destroySurface();
    }

    @Override
    protected void onDestroy() {
        if (authenService != null) {
            authenService.destroySurface();
        }
        super.onDestroy();
    }

    /**
     * 按钮的点击监听1
     */
    private class StartBtnListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            //手指按下则表示录制开始。
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                authenService.videoTaps();
            }
            //手指抬起则录制结束。
            if (event.getAction() == MotionEvent.ACTION_UP) {
                authenService.stopMediaAndAudioDevice();
            }
            return true;
        }
    }
}
