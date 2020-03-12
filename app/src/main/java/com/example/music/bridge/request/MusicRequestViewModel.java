package com.example.music.bridge.request;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.music.data.bean.TestAlbum;
import com.example.music.data.repository.HttpRequestManager;

public class MusicRequestViewModel extends ViewModel {

    private MutableLiveData<TestAlbum> freeMusicsLiveData;

    public MutableLiveData<TestAlbum> getFreeMusicsLiveData() {
        if (freeMusicsLiveData == null) {
            freeMusicsLiveData = new MutableLiveData<>();
        }
        return freeMusicsLiveData;
    }

    public void requestFreeMusics() {
        HttpRequestManager.getInstance().getFreeMusic(getFreeMusicsLiveData());
    }
}
