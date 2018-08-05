package com.voices.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;

import com.voices.R;

public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private AppCompatTextView tv_toolbar_text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        setupToolbar();
        onActivityCreate();
    }


    /**
     * Its common use a toolbar within activity, if it exists in the
     * layout this will be configured
     */
    public void setupToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        tv_toolbar_text = (AppCompatTextView) findViewById(R.id.tv_toolbar_text);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }

    protected void setToolbarText(String toolbarTxt) {
        tv_toolbar_text.setText(toolbarTxt);
    }

    @Nullable
    public Toolbar getToolbar() {
        return mToolbar;
    }

    /**
     * @return The layout id that's gonna be the activity view.
     */
    protected abstract int getLayoutId();

    protected abstract void onActivityCreate();
}
