package com.example.player.contract;

import android.content.Context;

import com.example.player.bean.base.BaseAlbumItem;
import com.example.player.bean.base.BaseMusicItem;

public interface IPlayController<B extends BaseAlbumItem, M extends BaseMusicItem> extends ILiveDataNotifier, IPlayInfoManager {

    void init(Context context, IServiceNotifier iServiceNotifier);

    void loadAlbum(B musicAlbum);

    void loadAlbum(B musicAlbum, int playIndex);

    void playAudio();

    void playAudio(int albumIndex);

    void playNext();

    void playPrevious();

    void playAgain();

    void togglePlay();

    void pauseAudio();

    void resumeAudio();

    void clear();

    void changeMode();

    boolean isPlaying();

    boolean isPaused();

    boolean isInited();

    void setSeek(int progress);

    String getTrackTime(int progress);
}
