package com.example.player.bean;

import com.example.player.bean.base.BaseAlbumItem;
import com.example.player.bean.base.BaseArtistItem;
import com.example.player.bean.base.BaseMusicItem;

public class DefaultAlbum extends BaseAlbumItem<DefaultAlbum.DefaultMusic, DefaultAlbum.DefaultArtist> {

    public static class DefaultMusic extends BaseMusicItem<DefaultArtist> {

    }

    public static class DefaultArtist extends BaseArtistItem {

    }
}
