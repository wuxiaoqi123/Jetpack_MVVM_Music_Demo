package com.example.player;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.danikula.videocache.HttpProxyCacheServer;
import com.example.player.bean.base.BaseAlbumItem;
import com.example.player.bean.base.BaseMusicItem;
import com.example.player.bean.dto.ChangeMusic;
import com.example.player.bean.dto.PlayingMusic;
import com.example.player.contract.IServiceNotifier;
import com.example.player.helper.MediaPlayerHelper;
import com.example.player.helper.PlayerFileNameGenerator;
import com.example.player.utils.NetworkUtils;

import java.util.List;

public class PlayerController<B extends BaseAlbumItem, M extends BaseMusicItem> {

    private PlayingInfoManager<B, M> mPlayingInfoManager = new PlayingInfoManager<>();
    private boolean mIsPaused;
    private boolean mIsChangingPlayingMusic;

    private HttpProxyCacheServer proxy;

    private MutableLiveData<ChangeMusic> changeMusicLiveData = new MutableLiveData<>();
    private MutableLiveData<PlayingMusic> playingMusicLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> pauseLiveData = new MutableLiveData<>();
    private MutableLiveData<Enum> playModeLiveData = new MutableLiveData<>();

    private IServiceNotifier mIServiceNotifier;

    private PlayingMusic mCurrentPlay = new PlayingMusic("00:00", "00:00");
    private ChangeMusic mChangeMusic = new ChangeMusic();

    public void init(Context context, List<String> extraFormatList, IServiceNotifier iServiceNotifier) {
        mPlayingInfoManager.init(context.getApplicationContext());
        proxy = new HttpProxyCacheServer.Builder(context.getApplicationContext())
                .fileNameGenerator(new PlayerFileNameGenerator())
                .maxCacheSize(1024L * 1024 * 1024 * 2)
                .build();
        mIServiceNotifier = iServiceNotifier;
        if (extraFormatList != null) {
            MediaPlayerHelper.getInstance().getFormatList().addAll(extraFormatList);
        }
    }

    public boolean isInited() {
        return mPlayingInfoManager.isInited();
    }

    public void loadAlbum(Context context, B musicAlbum) {
        setAlbum(context, musicAlbum, 0);
    }

    private void setAlbum(Context context, B musicAlbum, int albumIndex) {
        mPlayingInfoManager.setMusicAlbum(musicAlbum);
        mPlayingInfoManager.setAlbumIndex(albumIndex);
        setChangPlayingMusic(context, true);
    }

    public void loadAlbum(Context context, B musicAlbum, int albumIndex) {
        setAlbum(context, musicAlbum, albumIndex);
        playAudio(context);
    }

    public boolean isPlaying() {
        return MediaPlayerHelper.getInstance().getMediaPlayer().isPlaying();
    }

    public boolean isPaused() {
        return mIsPaused;
    }

    public void playAudio(Context context, int albumIndex) {
        if (isPaused() && albumIndex == mPlayingInfoManager.getAlbumIndex()) {
            return;
        }
        mPlayingInfoManager.setAlbumIndex(albumIndex);
        setChangPlayingMusic(context, true);
        playAudio(context);
    }

    public void playAudio(Context context) {
        if (mIsChangingPlayingMusic) {
            MediaPlayerHelper.getInstance().getMediaPlayer().stop();
            getUrlAndPlay(context);
        } else if (mIsPaused) {
            resumeAudio();
        }
    }

    private void getUrlAndPlay(Context context) {
        String url = null;
        M freeMusic = null;
        freeMusic = mPlayingInfoManager.getCurrentPlayingMusic();
        url = freeMusic.url;
        if (TextUtils.isEmpty(url)) {
            pauseAudio();
        } else {
            if (url.contains("http:") || url.contains("ftp:") || url.contains("https:")) {
                if (NetworkUtils.isConnected(context)) {
                    MediaPlayerHelper.getInstance().play(proxy.getProxyUrl(url));
                } else {
                    Toast.makeText(context, R.string.unconnnect, Toast.LENGTH_SHORT).show();
                }
            } else if (url.contains("storage")) {
                MediaPlayerHelper.getInstance().play(url);
            } else {
                MediaPlayerHelper.getInstance().playAsset(context, url);
                afterPlay(context);
            }
        }
    }

    private void afterPlay(Context context) {
        setChangPlayingMusic(context, false);
        bindProgressListener(context);
        mIsPaused = false;
        pauseLiveData.setValue(mIsPaused);
        if (mIServiceNotifier != null) {
            mIServiceNotifier.notifyService(true);
        }
    }

    private void bindProgressListener(Context context) {
        MediaPlayerHelper.getInstance().setProgressInterval(1000).setMediaPlayerHelperCallBack(
                (state, mediaPlayerHelper, args) -> {
                    if (state == MediaPlayerHelper.CallBackState.PROGRESS) {
                        int position = mediaPlayerHelper.getMediaPlayer().getCurrentPosition();
                        int duration = mediaPlayerHelper.getMediaPlayer().getDuration();
                        mCurrentPlay.nowTime = calculateTime(position / 1000);
                        mCurrentPlay.allTime = calculateTime(duration / 1000);
                        mCurrentPlay.duration = duration;
                        mCurrentPlay.playerPosition = position;
                        playingMusicLiveData.setValue(mCurrentPlay);
                        if (mCurrentPlay.allTime.equals(mCurrentPlay.nowTime) ||
                                duration / 1000 - position / 1000 < 2) {
                            if (getRepeatMode() == PlayingInfoManager.RepeatMode.ONE_LOOP) {
                                playAgain(context);
                            } else {
                                playNext(context);
                            }
                        }
                    }
                });
    }

    public void requestLastPlayingInfo() {
        playingMusicLiveData.setValue(mCurrentPlay);
        changeMusicLiveData.setValue(mChangeMusic);
        pauseLiveData.setValue(mIsPaused);
    }

    public void setSeek(int progress) {
        MediaPlayerHelper.getInstance().getMediaPlayer().seekTo(progress);
    }

    public String getTrackTime(int progress) {
        return calculateTime(progress / 1000);
    }

    private String calculateTime(int time) {
        int minute;
        int second;
        if (time >= 60) {
            minute = time / 60;
            second = time % 60;
            return (minute < 10 ? "0" + minute : "" + minute) + (second < 10 ? ":0" + second : ":" + second);
        } else {
            second = time;
            if (second < 10) {
                return "00:0" + second;
            }
            return "00:" + second;
        }
    }

    public void playNext(Context context) {
        mPlayingInfoManager.countNextIndex();
        setChangPlayingMusic(context, true);
        playAudio(context);
    }

    public void playPrevious(Context context) {
        mPlayingInfoManager.countPrevoiousIndex();
        setChangPlayingMusic(context, true);
        playAudio(context);
    }

    public void playAgain(Context context) {
        setChangPlayingMusic(context, true);
        playAudio(context);
    }

    public void pauseAudio() {
        MediaPlayerHelper.getInstance().getMediaPlayer().pause();
        mIsPaused = true;
        pauseLiveData.setValue(mIsPaused);
        if (mIServiceNotifier != null) {
            mIServiceNotifier.notifyService(true);
        }
    }

    public void resumeAudio() {
        MediaPlayerHelper.getInstance().getMediaPlayer().start();
        mIsPaused = false;
        pauseLiveData.setValue(mIsPaused);
        if (mIServiceNotifier != null) {
            mIServiceNotifier.notifyService(true);
        }
    }

    public void clear(Context context) {
        MediaPlayerHelper.getInstance().getMediaPlayer().start();
        MediaPlayerHelper.getInstance().getMediaPlayer().reset();
        mPlayingInfoManager.clear(context);
        pauseLiveData.setValue(true);
        resetIsChangingPlayingChapter(context);
        MediaPlayerHelper.getInstance().setProgressInterval(1000).setMediaPlayerHelperCallBack(null);
        if (mIServiceNotifier != null) {
            mIServiceNotifier.notifyService(false);
        }
    }

    public void resetIsChangingPlayingChapter(Context context) {
        mIsChangingPlayingMusic = true;
        setChangPlayingMusic(context, true);
    }

    public void changeMode() {
        playModeLiveData.setValue(mPlayingInfoManager.changeMode());
    }

    public B getAlbum() {
        return mPlayingInfoManager.getMusicAlbum();
    }

    public List<M> getAlbumMusics() {
        return mPlayingInfoManager.getOriginPlayingList();
    }

    public void setChangPlayingMusic(Context context, boolean changingPlayingMusic) {
        mIsChangingPlayingMusic = changingPlayingMusic;
        if (changingPlayingMusic) {
            mChangeMusic.setBaseInfo(mPlayingInfoManager.getMusicAlbum(), getCurrentPlayingMusic());
            changeMusicLiveData.setValue(mChangeMusic);
            mCurrentPlay.setBaseInfo(mPlayingInfoManager.getMusicAlbum(), getCurrentPlayingMusic());
            mPlayingInfoManager.saveRecords(context);
        }
    }

    public int getAlbumIndex() {
        return mPlayingInfoManager.getAlbumIndex();
    }

    public MutableLiveData<ChangeMusic> getChangeMusicLiveData() {
        return changeMusicLiveData;
    }

    public MutableLiveData<PlayingMusic> getPlayingMusicLiveData() {
        return playingMusicLiveData;
    }

    public MutableLiveData<Boolean> getPauseLiveData() {
        return pauseLiveData;
    }

    public MutableLiveData<Enum> getPlayModeLiveData() {
        return playModeLiveData;
    }

    public Enum getRepeatMode() {
        return mPlayingInfoManager.getRepeatMode();
    }

    public void togglePlay(Context context) {
        if (isPlaying()) {
            pauseAudio();
        } else {
            playAudio(context);
        }
    }

    public M getCurrentPlayingMusic() {
        return mPlayingInfoManager.getCurrentPlayingMusic();
    }
}
