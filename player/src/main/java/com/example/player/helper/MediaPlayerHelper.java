package com.example.player.helper;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MediaPlayerHelper implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnInfoListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnVideoSizeChangedListener, MediaPlayer.OnBufferingUpdateListener, SurfaceHolder.Callback {

    public static final String TAG = "MediaPlayerHelper";

    private String[] ext = {
            ".m4a",
            ".3gp",
            ".mp4",
            ".mp3",
            ".wma",
            ".ogg",
            ".wav",
            ".mid",
    };

    private List<String> formatList = new ArrayList<>();

    public List<String> getFormatList() {
        return formatList;
    }

    {
        formatList.addAll(Arrays.asList(ext));
    }

    public Holder uiHolder;
    private MediaPlayerHelperCallBack mediaPlayerHelperCallBack = null;
    private static MediaPlayerHelper instance;
    private int delaySecondTime = 1000;

    public enum CallBackState {
        PREPARE("MediaPlayer--准备完毕"),
        COMPLETE("MediaPlayer--播放完毕"),
        ERROR("MediaPlayer--播放错误"),
        EXCEPTION("MediaPlayer--播放异常"),
        INFO("MediaPlayer--播放开始"),
        PROGRESS("MediaPlayer--播放进度回调"),
        SEEK_COMPLETE("MediaPlayer--拖动到尾端"),
        VIDEO_SIZE_CHANGE("MeidaPlayer--读取视频大小"),
        BUFFER_UPDATE("MediaPlayer--更新流媒体缓存大小"),
        FORMATE_NOT_SURPORT("MediaPlayer--音视频格式可能不支持"),
        SURFACEVIEW_NULL("SurfaceView--还没初始化"),
        SURFACEVIEW_NOT_ARREADY("SurfaceView--还没准备好"),
        SURFACEVIEW_CHANGE("SurfaceView--Holder改变"),
        SURFACEVIEW_CREATE("SurfaceView--Holder创建"),
        SURFACEVIEW_DESTROY("SurfaceView--Holder销毁");

        private final String state;

        CallBackState(String arg) {
            this.state = arg;
        }

        @Override
        public String toString() {
            return this.state;
        }

    }

    public static MediaPlayerHelper getInstance() {
        if (instance == null) {
            synchronized (MediaPlayerHelper.class) {
                if (instance == null) {
                    instance = new MediaPlayerHelper();
                }
            }
        }
        return instance;
    }

    public MediaPlayerHelper() {
        this.uiHolder = new Holder();
        uiHolder.player = new MediaPlayer();
        uiHolder.player.setOnCompletionListener(this);
        uiHolder.player.setOnErrorListener(this);
        uiHolder.player.setOnInfoListener(this);
        uiHolder.player.setOnPreparedListener(this);
        uiHolder.player.setOnSeekCompleteListener(this);
        uiHolder.player.setOnVideoSizeChangedListener(this);
        uiHolder.player.setOnBufferingUpdateListener(this);
    }

    public MediaPlayerHelper setProgressInterval(int time) {
        delaySecondTime = time;
        return instance;
    }

    public MediaPlayerHelper setSurfaceView(SurfaceView surfaceView) {
        if (surfaceView == null) {
            callBack(CallBackState.SURFACEVIEW_NULL, uiHolder.player);
        } else {
            uiHolder.surfaceView = surfaceView;
            uiHolder.surfaceHolder = surfaceView.getHolder();
            uiHolder.surfaceHolder.addCallback(this);
        }
        return instance;
    }

    public MediaPlayerHelper setMediaPlayerHelperCallBack(MediaPlayerHelperCallBack mediaPlayerHelperCallBack) {
        this.mediaPlayerHelperCallBack = mediaPlayerHelperCallBack;
        return instance;
    }

    public void release() {
        if (uiHolder.player != null) {
            uiHolder.player.release();
            uiHolder.player = null;
        }
        refress_time_handler.removeCallbacks(refress_time_Thread);
    }

    public boolean playAsset(Context context, String assetName) {
        if (!checkAvalable(assetName)) {
            return false;
        }
        AssetManager assetManager = context.getAssets();
        try {
            uiHolder.assetFileDescriptor = assetManager.openFd(assetName);
            uiHolder.player.setDisplay(null);
            uiHolder.player.reset();
            uiHolder.player.setDataSource(uiHolder.assetFileDescriptor.getFileDescriptor());
            uiHolder.player.prepare();
        } catch (Exception e) {
            callBack(CallBackState.ERROR, uiHolder.player);
            return false;
        }
        return true;
    }

    public boolean play(final String localPathOrURl) {
        if (!checkAvalable(localPathOrURl)) {
            return false;
        }
        try {
            uiHolder.player.setDisplay(null);
            uiHolder.player.reset();
            uiHolder.player.setDataSource(localPathOrURl);
            uiHolder.player.prepare();
        } catch (Exception e) {
            callBack(CallBackState.ERROR, uiHolder.player);
            return false;
        }
        return true;
    }

    public MediaPlayer getMediaPlayer() {
        return uiHolder.player;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        callBack(CallBackState.PROGRESS, 100);
        callBack(CallBackState.COMPLETE, mp);
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        callBack(CallBackState.ERROR, mp, what, extra);
        return false;
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        callBack(CallBackState.INFO, mp, what, extra);
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        try {
            if (uiHolder.surfaceView != null) {
                uiHolder.player.setDisplay(uiHolder.surfaceHolder);
            }
            uiHolder.player.start();
            refress_time_handler.postDelayed(refress_time_Thread, delaySecondTime);
        } catch (Exception e) {
            callBack(CallBackState.EXCEPTION, mp);
        }
        callBack(CallBackState.PREPARE, mp);
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {
        callBack(CallBackState.SEEK_COMPLETE, mp);
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        callBack(CallBackState.VIDEO_SIZE_CHANGE, width, height);
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (uiHolder.player != null && holder != null) {
            uiHolder.player.setDisplay(holder);
        }
        callBack(CallBackState.SURFACEVIEW_CREATE, holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        callBack(CallBackState.SURFACEVIEW_CHANGE, format, width, height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        callBack(CallBackState.SURFACEVIEW_DESTROY, holder);
    }

    private boolean checkAvalable(String path) {
        boolean support = false;
        for (int i = 0; i < ext.length; i++) {
            if (path.toLowerCase().endsWith(ext[i])) {
                support = true;
            }
        }
        if (!support) {
            callBack(CallBackState.FORMATE_NOT_SURPORT, uiHolder.player);
        }
        return support;
    }

    Handler refress_time_handler = new Handler();
    Runnable refress_time_Thread = new Runnable() {
        @Override
        public void run() {
            refress_time_handler.removeCallbacks(refress_time_Thread);
            if (uiHolder.player != null && uiHolder.player.isPlaying()) {
                callBack(CallBackState.PROGRESS, 100 * uiHolder.player.getCurrentPosition() / uiHolder.player.getDuration());
            }
            refress_time_handler.postDelayed(refress_time_Thread, delaySecondTime);
        }
    };

    private static final class Holder {
        private SurfaceHolder surfaceHolder;
        private MediaPlayer player;
        private SurfaceView surfaceView;
        private AssetFileDescriptor assetFileDescriptor;
    }

    private void callBack(CallBackState state, Object... args) {
        if (mediaPlayerHelperCallBack != null) {
            mediaPlayerHelperCallBack.onCallBack(state, instance, args);
        }
    }

    public interface MediaPlayerHelperCallBack {

        void onCallBack(CallBackState state, MediaPlayerHelper mediaPlayerHelper, Object... args);
    }
}
