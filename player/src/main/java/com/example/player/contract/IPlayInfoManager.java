package com.example.player.contract;

import com.example.player.bean.base.BaseAlbumItem;
import com.example.player.bean.base.BaseMusicItem;

import java.util.List;

public interface IPlayInfoManager<B extends BaseAlbumItem, M extends BaseMusicItem> {

    B getAlbum();

    List<M> getAlbumMusics();

    void setChangingPlayingMusic(boolean changingPlayingMusic);

    int getAlbumIndex();

    Enum getRepeatMode();

    M getCurrentPlayingMusic();

    void requestLastPlayingInfo();
}
