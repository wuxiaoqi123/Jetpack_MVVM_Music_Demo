package com.example.player.bean.base;

import java.util.List;

// 音乐专辑实体
public class BaseAlbumItem<M extends BaseMusicItem, A extends BaseArtistItem> {

    public String albumId = "";
    public String title = "";
    public String summary = "";
    public String coverImg = "";
    public A artist;
    public List<M> musics;

    public BaseAlbumItem() {
    }

    public BaseAlbumItem(String albumId, String title, String summary, String coverImg, A artist, List<M> musics) {
        this.albumId = albumId;
        this.title = title;
        this.summary = summary;
        this.coverImg = coverImg;
        this.artist = artist;
        this.musics = musics;
    }
}
