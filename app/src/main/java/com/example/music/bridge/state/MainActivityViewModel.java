package com.example.music.bridge.state;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {

    public final MutableLiveData<Boolean> openDrawer = new MutableLiveData<>();

    public final MutableLiveData<Boolean> allowDrawerOpen = new MutableLiveData<>();

    {
        allowDrawerOpen.setValue(true);
    }
}
