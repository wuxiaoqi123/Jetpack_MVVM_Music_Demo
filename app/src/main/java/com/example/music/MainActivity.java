package com.example.music;

import android.os.Bundle;

import com.example.music.base.BaseActivity;
import com.example.music.bridge.state.MainActivityViewModel;

public class MainActivity extends BaseActivity {

    private MainActivityViewModel mMainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainActivityViewModel = getActivityViewModelProvider(this).get(MainActivityViewModel.class);
        setContentView(R.layout.activity_main);
    }
}
