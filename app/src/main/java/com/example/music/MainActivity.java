package com.example.music;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.example.music.base.BaseActivity;
import com.example.music.bridge.state.MainActivityViewModel;
import com.example.music.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding mBinding;
    private MainActivityViewModel mMainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainActivityViewModel = getActivityViewModelProvider(this).get(MainActivityViewModel.class);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.setLifecycleOwner(this);
        mBinding.setVm(mMainActivityViewModel);
    }
}
