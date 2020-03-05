package com.example.player.contract;

import androidx.lifecycle.MutableLiveData;

import com.example.player.bean.dto.ChangeMusic;
import com.example.player.bean.dto.PlayingMusic;

public interface ILiveDataNotifier {

    MutableLiveData<ChangeMusic> getChangeMusicLiveData();

    MutableLiveData<PlayingMusic> getPlayingMusicLiveData();

    MutableLiveData<Boolean> getPauseLiveData();

    MutableLiveData<Enum> getPlayModeLiveData();
}
