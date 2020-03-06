package com.example.music.data.bean;

import com.example.player.bean.base.BaseAlbumItem;
import com.example.player.bean.base.BaseArtistItem;
import com.example.player.bean.base.BaseMusicItem;

public class TestAlbum extends BaseAlbumItem<TestAlbum.TestMusic, TestAlbum.TestArtist> {

    public String albumMid = "";

    public static class TestMusic extends BaseMusicItem<TestArtist> implements BaseBean {

        public String songMid = "";
    }

    public static class TestArtist extends BaseArtistItem implements BaseBean {

        public String birthday = "";
    }
}
