package com.voices;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.voices.ui.activity.SplashActivity;
import com.voices.utils.AppUtils;

public class SplashActivityTest extends
        ActivityInstrumentationTestCase2<SplashActivity> {

    private SplashActivity mActivity;
    private TextView mView, mAppVersionTextView;
    private String appNameResourceString;
    private String appVersionString;

    public SplashActivityTest() {
        super(SplashActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mActivity = this.getActivity();
        mView = (TextView) mActivity.findViewById
                (R.id.tv_app_name);
        mAppVersionTextView = (TextView) mActivity.findViewById(R.id.tv_app_version);
        appNameResourceString = mActivity.getString
                (R.string.app_name);
        appVersionString = "V " + AppUtils.getAppVersionName(mActivity);
    }

    public void testPreconditions() {
        assertNotNull("Activity is null", mActivity);
        assertNotNull("TextView mView is null", mView);
        assertNotNull("AppVersion TextView is null", mAppVersionTextView);
    }

    public void testAppNameText() {
        assertEquals(appNameResourceString, mView.getText().toString());
    }

    public void testAppVersionText() {
        assertEquals(appVersionString, mAppVersionTextView.getText().toString());
    }


}