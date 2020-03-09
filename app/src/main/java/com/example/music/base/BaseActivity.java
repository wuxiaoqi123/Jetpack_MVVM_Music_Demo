package com.example.music.base;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.music.App;
import com.example.music.bridge.callback.SharedViewModel;
import com.example.music.data.manager.NetworkStateManager;
import com.example.music.utils.AdaptScreenUtils;
import com.example.music.utils.BarUtils;
import com.example.music.utils.ScreenUtils;

public abstract class BaseActivity extends AppCompatActivity {

    protected SharedViewModel mSharedViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BarUtils.setStatusBarColor(this, Color.TRANSPARENT);
        BarUtils.setStatusBarLightMode(this, true);
        mSharedViewModel = getAppViewModelProvider().get(SharedViewModel.class);
        getLifecycle().addObserver(NetworkStateManager.getInstance());
    }

    @Override
    public Resources getResources() {
        if (ScreenUtils.isPortrait()) {
            return AdaptScreenUtils.adaptWidth(super.getResources(), 360);
        } else {
            return AdaptScreenUtils.adaptHeight(super.getResources(), 640);
        }
    }

    protected ViewModelProvider getAppViewModelProvider() {
        return ((App) getApplicationContext()).getAppViewModelProvider(this);
    }

    protected ViewModelProvider getActivityViewModelProvider(AppCompatActivity activity) {
        return new ViewModelProvider(activity, activity.getDefaultViewModelProviderFactory());
    }
}
