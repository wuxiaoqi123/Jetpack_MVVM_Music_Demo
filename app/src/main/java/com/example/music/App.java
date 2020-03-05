package com.example.music;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.music.utils.Utils;

public class App extends Application implements ViewModelStoreOwner {

    private ViewModelStore mAppViewModelStore;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppViewModelStore = new ViewModelStore();
        Utils.init(this);

    }

    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        return null;
    }
}
