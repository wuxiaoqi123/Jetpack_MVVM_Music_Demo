package com.example.player;

import android.content.Context;

import com.example.player.bean.base.BaseAlbumItem;
import com.example.player.bean.base.BaseMusicItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayingInfoManager<B extends BaseAlbumItem, M extends BaseMusicItem> {

    private static final String SP_NAME = "Music";
    private static final String REPEAT_MODE = "REPEAT_MODE";
    private static final String LAST_CHAPTER_INDEX = "LAST_CHAPTER_INDEX";
    private static final String LAST_BOOK_DETAIL = "LAST_BOOK_DETAIL";

    private int mPlayIndex = 0;
    private int mAlbumIndex = 0;

    private Enum mRepeatMode;

    public enum RepeatMode {
        ONE_LOOP, LIST_LOOP, RANDOM
    }

    private List<M> mOriginPlayingList = new ArrayList<>();
    private List<M> mShufflePlayingList = new ArrayList<>();
    private B mMusicAlbum;

    public void init(Context context) {

    }

    public boolean isInited() {
        return mMusicAlbum != null;
    }

    private void fitShuffle() {
        mShufflePlayingList.clear();
        mShufflePlayingList.addAll(mOriginPlayingList);
        Collections.shuffle(mShufflePlayingList);
    }

    public Enum changeMode() {
        if (mRepeatMode == RepeatMode.LIST_LOOP) {
            mRepeatMode = RepeatMode.ONE_LOOP;
        } else if (mRepeatMode == RepeatMode.ONE_LOOP) {
            mRepeatMode = RepeatMode.RANDOM;
        } else {
            mRepeatMode = RepeatMode.LIST_LOOP;
        }
        return mRepeatMode;
    }

    public B getMusicAlbum() {
        return mMusicAlbum;
    }

    public void setMusicAlbum(B musicAlbum) {
        this.mMusicAlbum = musicAlbum;
        mOriginPlayingList.clear();
        mOriginPlayingList.addAll(mMusicAlbum.musics);
        fitShuffle();
    }

    public List<M> getPlayingList() {
        if (mRepeatMode == RepeatMode.RANDOM) {
            return mShufflePlayingList;
        }
        return mOriginPlayingList;
    }

    public List<M> getOriginPlayingList() {
        return mOriginPlayingList;
    }

    public M getCurrentPlayingMusic() {
        return getPlayingList().get(mPlayIndex);
    }

    public Enum getRepeatMode() {
        return mRepeatMode;
    }

    public void countPrevoiousIndex() {
        if (mPlayIndex == 0) {
            mPlayIndex = (getPlayingList().size() - 1);
        } else {
            --mPlayIndex;
        }
        mAlbumIndex = mOriginPlayingList.indexOf(getCurrentPlayingMusic());
    }

    public void countNextIndex() {
        if (mPlayIndex == (getPlayingList().size() - 1)) {
            mPlayIndex = 0;
        } else {
            ++mPlayIndex;
        }
        mAlbumIndex = mOriginPlayingList.indexOf(getCurrentPlayingMusic());
    }

    public int getAlbumIndex() {
        return mAlbumIndex;
    }

    public void setAlbumIndex(int albumIndex) {
        mAlbumIndex = albumIndex;
        mPlayIndex = getPlayingList().indexOf(mOriginPlayingList.get(albumIndex));
    }

    public void clear(Context context) {
        saveRecords(context);
    }

    public void saveRecords(Context context) {

    }
}
