package com.voices.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;

import com.voices.R;
import com.voices.ui.base.BaseActivity;
import com.voices.utils.AppUtils;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseActivity {

    private static final long SPLASH_TIMEOUT = 3000;
    private Timer thread;

    private AppCompatTextView tv_app_version;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onActivityCreate() {
        initView();
        setAppVersion();
        startSplashTimer();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (thread != null) {
            thread.cancel();
            thread.purge();
        }
    }

    /**
     * Initialize Views with their IDs
     */
    private void initView() {
        tv_app_version = (AppCompatTextView) findViewById(R.id.tv_app_version);
    }

    /**
     * To Show the App Version at Splash Screen.
     */
    private void setAppVersion() {
        tv_app_version.setText("V " + AppUtils.getAppVersionName(this));
    }

    /**
     * Start the Splash Timer to wait for few second to move to the new screen
     */
    private void startSplashTimer() {
        thread = new Timer();
        thread.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }, SPLASH_TIMEOUT);
    }
}
