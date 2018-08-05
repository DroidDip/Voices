package com.voices.ui.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;

import com.voices.R;
import com.voices.ui.base.BaseActivity;
import com.voices.utils.AppUtils;

import java.util.ArrayList;
import java.util.Locale;

import static com.voices.app.AppConstant.INTENT_KEY_SPEECH_INPUT_RESULT;
import static com.voices.app.AppConstant.REQ_CODE_SPEECH_INPUT;
import static com.voices.app.AppConstant.REQ_SPEECH_INPUT_RESULT;

public class SpeechActivity extends BaseActivity {

    private AppCompatTextView tv_user_speaks_data;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_speech;
    }

    @Override
    protected void onActivityCreate() {
        initView();
        startVoiceInput();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setToolbarText(getString(R.string.app_name));
    }

    /**
     * Initialize Views with their IDs
     */
    private void initView() {
        tv_user_speaks_data = (AppCompatTextView) findViewById(R.id.tv_user_speaks_data);
    }

    /**
     * Start the voice input process
     */
    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            a.printStackTrace();
            AppUtils.showToast(this, getString(R.string.error_speech_input));
        }
    }

    /**
     * Handle the result after Speech being detected
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    final ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    final String speechInput = result.get(0);
                    tv_user_speaks_data.setText(speechInput);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra(INTENT_KEY_SPEECH_INPUT_RESULT, speechInput);
                            setResult(RESULT_OK, resultIntent);
                            finish();
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        }
                    }, 1200);
                }
                break;
            }

        }
    }
}
