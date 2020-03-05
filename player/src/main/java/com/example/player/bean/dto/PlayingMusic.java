package com.example.player.bean.dto;

import com.example.player.bean.base.BaseAlbumItem;
import com.example.player.bean.base.BaseArtistItem;

public class PlayingMusic<A extends BaseArtistItem, B extends BaseAlbumItem> extends ChangeMusic {

    public String nowTime = "";
    public String allTime = "";
    public int duration;
    public int playerPosition;

    public PlayingMusic(String nowTime, String allTime) {
        this.nowTime = nowTime;
        this.allTime = allTime;
    }

    public PlayingMusic(String title, String summary, String bookId, String chapterId, String nowTime, String allTime, String img, A artist) {
        super(title, summary, bookId, chapterId, img, artist);
        this.nowTime = nowTime;
        this.allTime = allTime;
    }

    public PlayingMusic(B baseAlbumItem, int playIndex, String nowTime, String allTime) {
        super(baseAlbumItem, playIndex);
        this.nowTime = nowTime;
        this.allTime = allTime;
    }
}
