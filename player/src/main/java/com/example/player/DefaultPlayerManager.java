package com.example.player;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.player.bean.DefaultAlbum;
import com.example.player.bean.dto.ChangeMusic;
import com.example.player.bean.dto.PlayingMusic;
import com.example.player.contract.IPlayController;
import com.example.player.contract.IServiceNotifier;

import java.util.List;

public class DefaultPlayerManager implements IPlayController<DefaultAlbum, DefaultAlbum.DefaultMusic> {

    private static DefaultPlayerManager sManager = new DefaultPlayerManager();

    private PlayerController<DefaultAlbum, DefaultAlbum.DefaultMusic> mController;

    private Context mContext;

    private DefaultPlayerManager() {
        mController = new PlayerController<>();
    }

    public static DefaultPlayerManager getInstance() {
        return sManager;
    }


    @Override
    public void init(Context context, IServiceNotifier iServiceNotifier) {
        mContext = context.getApplicationContext();
        mController.init(context, null, iServiceNotifier);
    }

    @Override
    public void loadAlbum(DefaultAlbum musicAlbum) {
        mController.loadAlbum(mContext, musicAlbum);
    }

    @Override
    public void loadAlbum(DefaultAlbum musicAlbum, int playIndex) {
        mController.loadAlbum(mContext, musicAlbum, playIndex);
    }

    @Override
    public void playAudio() {
        mController.playAudio(mContext);
    }

    @Override
    public void playAudio(int albumIndex) {
        mController.playAudio(mContext, albumIndex);
    }

    @Override
    public void playNext() {
        mController.playNext(mContext);
    }

    @Override
    public void playPrevious() {
        mController.playPrevious(mContext);
    }

    @Override
    public void playAgain() {
        mController.playAgain(mContext);
    }

    @Override
    public void togglePlay() {
        mController.togglePlay(mContext);
    }

    @Override
    public void pauseAudio() {
        mController.pauseAudio();
    }

    @Override
    public void resumeAudio() {
        mController.resumeAudio();
    }

    @Override
    public void clear() {
        mController.clear(mContext);
    }

    @Override
    public void changeMode() {
        mController.changeMode();
    }

    @Override
    public boolean isPlaying() {
        return mController.isPlaying();
    }

    @Override
    public boolean isPaused() {
        return mController.isPaused();
    }

    @Override
    public boolean isInited() {
        return mController.isInited();
    }

    @Override
    public void requestLastPlayingInfo() {
        mController.requestLastPlayingInfo();
    }

    @Override
    public void setSeek(int progress) {
        mController.setSeek(progress);
    }

    @Override
    public String getTrackTime(int progress) {
        return mController.getTrackTime(progress);
    }

    @Override
    public DefaultAlbum getAlbum() {
        return mController.getAlbum();
    }

    @Override
    public List<DefaultAlbum.DefaultMusic> getAlbumMusics() {
        return mController.getAlbumMusics();
    }

    @Override
    public void setChangingPlayingMusic(boolean changingPlayingMusic) {
        mController.setChangPlayingMusic(mContext, changingPlayingMusic);
    }

    @Override
    public int getAlbumIndex() {
        return mController.getAlbumIndex();
    }

    @Override
    public MutableLiveData<ChangeMusic> getChangeMusicLiveData() {
        return mController.getChangeMusicLiveData();
    }

    @Override
    public MutableLiveData<PlayingMusic> getPlayingMusicLiveData() {
        return mController.getPlayingMusicLiveData();
    }

    @Override
    public MutableLiveData<Boolean> getPauseLiveData() {
        return mController.getPauseLiveData();
    }

    @Override
    public MutableLiveData<Enum> getPlayModeLiveData() {
        return mController.getPlayModeLiveData();
    }

    @Override
    public Enum getRepeatMode() {
        return mController.getRepeatMode();
    }

    @Override
    public DefaultAlbum.DefaultMusic getCurrentPlayingMusic() {
        return mController.getCurrentPlayingMusic();
    }
}
