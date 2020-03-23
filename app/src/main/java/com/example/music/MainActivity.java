package com.example.music;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.example.music.base.BaseActivity;
import com.example.music.bridge.state.MainActivityViewModel;
import com.example.music.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding mBinding;
    private MainActivityViewModel mMainActivityViewModel;
    private boolean isListened = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainActivityViewModel = getActivityViewModelProvider(this).get(MainActivityViewModel.class);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.setLifecycleOwner(this);
        mBinding.setVm(mMainActivityViewModel);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!isListened) {
            mSharedViewModel.timeToAddSlideListener.setValue(true);
            isListened = true;
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        mSharedViewModel.closeSlidePanelIfExpanded.setValue(true);
    }
}
