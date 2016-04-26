package com.example.cameratest.activities;


import com.example.cameratest.service.impl.BLiveAuthenServiceImpl;

/**
 * @author Mike
 * @project LIDA
 * @date 11/29/15, 5:18 PM
 * @e-mail mike@mikecoder.net
 */
public class BVerifyPersonActivity extends AbstractVerifyPersonActivity {
    @Override
    void setType() {

    }

    @Override
    protected void setAuthenService() {
        this.authenService = new BLiveAuthenServiceImpl(this.mSurfaceHolder);
    }
}
