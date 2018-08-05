package com.voices.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.voices.R;
import com.voices.dictionary.Dictionary;
import com.voices.model.DictionaryModel;
import com.voices.ui.adapter.DictionaryListAdapter;
import com.voices.ui.base.BaseActivity;
import com.voices.utils.AppUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static com.voices.app.AppConstant.INTENT_KEY_SPEECH_INPUT_RESULT;
import static com.voices.app.AppConstant.REQ_SPEECH_INPUT_RESULT;

public class HomeActivity extends BaseActivity implements View.OnClickListener {


    private LinearLayoutCompat ll_speak;
    private RecyclerView rv_dictionary_list;
    private DictionaryListAdapter dictionaryListAdapter;
    private ArrayList<DictionaryModel> dictionaryArray;
    private long back_pressed;
    private ProgressDialog progressDialog;
    private HashMap<Integer, Integer> updatedPositions;

    private Dictionary dictionary;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void onActivityCreate() {
        initView();
        setDictionaryAdapter();
        initClickListener();
        createDictionary();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setToolbarText(getString(R.string.app_name));
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            super.onBackPressed();
        } else {
            AppUtils.showToast(this, getString(R.string.press_again_to_exit));
        }
        back_pressed = System.currentTimeMillis();

    }

    /**
     * Initialize Views with their IDs
     */
    private void initView() {
        ll_speak = (LinearLayoutCompat) findViewById(R.id.ll_speak);
        rv_dictionary_list = (RecyclerView) findViewById(R.id.rv_dictionary_list);
        rv_dictionary_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    /**
     * Initialize Click Listeners for UI Views
     */
    private void initClickListener() {
        ll_speak.setOnClickListener(this);
    }

    /**
     * Create the dictionary with predefined word set
     */
    private void createDictionary() {
        dictionary = new Dictionary();
        dictionaryArray = dictionary.createDictionary(this.getResources());
        if (dictionaryArray != null)
            dictionaryListAdapter.refreshAdapter(dictionaryArray);
        else
            AppUtils.showToast(this, getString(R.string.error_something_went_wrong));
    }

    /**
     * Set the Dictionary List Adapter
     */
    private void setDictionaryAdapter() {
        dictionaryListAdapter = new DictionaryListAdapter(this);
        rv_dictionary_list.setAdapter(dictionaryListAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_speak:
                if (AppUtils.checkNetworkConnection(this)) {
                    Intent intent = new Intent(this, SpeechActivity.class);
                    startActivityForResult(intent, REQ_SPEECH_INPUT_RESULT);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                } else
                    AppUtils.showToast(this, getString(R.string.error_network_connection));
                break;
        }
    }

    /**
     * Handle Activity result from Speech Activity
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQ_SPEECH_INPUT_RESULT:
                    showProgress();
                    new Handler().postDelayed(new Runnable() { //Used just for showing a UI Progress dialog, in order to better user experience
                        @Override
                        public void run() {
                            if (data != null)
                                checkWordInDictionary(data.getStringExtra(INTENT_KEY_SPEECH_INPUT_RESULT));
                        }
                    }, 1000);
                    break;
            }
        }
    }

    /**
     * Method used to check whether the spoken word(s) is present in the dictionary or not
     *
     * @param speechText
     */
    private void checkWordInDictionary(String speechText) {
        if (dictionaryArray != null) {
            updatedPositions = dictionary.getDictionaryMatchPosition(speechText);

            //Clear All status Update Value in the list
            for (int i = 0; i < dictionaryArray.size(); i++) {
                dictionaryArray.get(i).setUpdated(false);
            }

            //Push the status whether the item is updated or not
            Iterator positionIterator = updatedPositions.keySet().iterator();
            while (positionIterator.hasNext()) {
                int key = (int) positionIterator.next();
                int value = updatedPositions.get(key);
                dictionaryArray.get(value).setUpdated(true);
            }

            dictionaryListAdapter.refreshAdapter(dictionaryArray);
            hideProgress();

            //Check if the word(s) is not present in the dictionary
            if (updatedPositions.isEmpty())
                AppUtils.showToast(this, getString(R.string.error_no_matching_word));
        } else
            AppUtils.showToast(this, getString(R.string.error_something_went_wrong));
    }

    /**
     * To Show Progress Dialog
     */
    private void showProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.speech_progress_msg));
        progressDialog.setCancelable(false);
        if (!isFinishing())
            progressDialog.show();
    }

    /**
     * To hide progress dialog
     */
    private void hideProgress() {
        if (progressDialog != null) {
            if (progressDialog.isShowing())
                if (!isFinishing())
                    progressDialog.hide();
        }
    }
}
