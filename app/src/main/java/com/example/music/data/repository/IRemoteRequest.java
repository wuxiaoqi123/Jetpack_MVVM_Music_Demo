package com.example.music.data.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.music.data.bean.LibraryInfo;
import com.example.music.data.bean.TestAlbum;

import java.util.List;

public interface IRemoteRequest {

    void getFreeMusic(MutableLiveData<TestAlbum> liveData);

    void getLibraryInfo(MutableLiveData<List<LibraryInfo>> liveData);
}
