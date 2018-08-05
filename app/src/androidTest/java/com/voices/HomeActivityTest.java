package com.voices;

import android.support.v7.widget.LinearLayoutCompat;
import android.test.ActivityInstrumentationTestCase2;

import com.voices.ui.activity.HomeActivity;

public class HomeActivityTest extends
        ActivityInstrumentationTestCase2<HomeActivity> {

    private HomeActivity mHomeActivity;
    private LinearLayoutCompat ll_speak;

    public HomeActivityTest() {
        super(HomeActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mHomeActivity = getActivity();
        ll_speak = (LinearLayoutCompat) mHomeActivity.findViewById(R.id.ll_speak);
    }

    public void testPreconditions() {
        assertNotNull("HomeActivity is null", mHomeActivity);
        assertNotNull("Tap to Speak View is null", ll_speak);
    }

    public void testTapToSpeakEvent() {
        mHomeActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ll_speak.performClick();
            }
        });
    }
}
