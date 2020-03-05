package com.example.player.bean.base;

// 音乐实体
public class BaseMusicItem<A extends BaseArtistItem> {

    public String musicId = "";
    public String coverImg = "";
    public String url = "";
    public String title = "";
    public A artist;

    public BaseMusicItem() {
    }

    public BaseMusicItem(String musicId, String coverImg, String url, String title, A artist) {
        this.musicId = musicId;
        this.coverImg = coverImg;
        this.url = url;
        this.title = title;
        this.artist = artist;
    }
}
