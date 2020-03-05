package com.example.player.bean.dto;

import com.example.player.bean.base.BaseAlbumItem;
import com.example.player.bean.base.BaseArtistItem;
import com.example.player.bean.base.BaseMusicItem;

public class ChangeMusic<B extends BaseAlbumItem, M extends BaseMusicItem, A extends BaseArtistItem> {

    public String title = "";
    public String summary = "";
    public String albumId = "";
    public String musicId = "";
    public String img = "";
    public A artist;

    public ChangeMusic() {
    }

    public ChangeMusic(String title, String summary, String albumId, String musicId, String img, A artist) {
        this.title = title;
        this.summary = summary;
        this.albumId = albumId;
        this.musicId = musicId;
        this.img = img;
        this.artist = artist;
    }

    public ChangeMusic(B musicAlbum, int playIndex) {
        this.title = musicAlbum.title;
        this.summary = musicAlbum.summary;
        this.albumId = musicAlbum.albumId;
        this.musicId = ((M) musicAlbum.musics.get(playIndex)).musicId;
        this.img = musicAlbum.coverImg;
        this.artist = (A) musicAlbum.artist;
    }

    public void setBaseInfo(B musicAlbum, M music) {
        //要用当前实际播放的列表，因为不同模式存在不同的播放列表
        this.title = music.title;
        this.summary = musicAlbum.summary;
        this.albumId = musicAlbum.albumId;
        this.musicId = music.musicId;
        this.img = music.coverImg;
        this.artist = (A) music.artist;
    }
}
