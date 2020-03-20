package com.example.music.ui.view;

import android.animation.ArgbEvaluator;
import android.animation.FloatEvaluator;
import android.animation.IntEvaluator;
import android.graphics.Color;
import android.view.View;

import com.example.music.databinding.FragmentPlayerBinding;
import com.example.music.utils.ScreenUtils;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class PlayerSlideListener implements SlidingUpPanelLayout.PanelSlideListener {

    private FragmentPlayerBinding mBinding;
    private SlidingUpPanelLayout mSlidingUpPanelLayout;
    private int screenWidth;
    private int screenHeight;
    private IntEvaluator intEvaluator = new IntEvaluator();
    private FloatEvaluator floatEvaluator = new FloatEvaluator();
    private ArgbEvaluator colorEvaluator = new ArgbEvaluator();
    private int nowPlayingCardColor;
    private int playPauseDrawableColor;

    public PlayerSlideListener(FragmentPlayerBinding binding, SlidingUpPanelLayout slidingUpPanelLayout) {
        mBinding = binding;
        mSlidingUpPanelLayout = slidingUpPanelLayout;
        screenWidth = ScreenUtils.getScreenWidth();
        screenHeight = ScreenUtils.getScreenHeight();
        playPauseDrawableColor = Color.BLACK;
        nowPlayingCardColor = Color.WHITE;


        mBinding.playPause.setDrawableColor(playPauseDrawableColor);
    }

    @Override
    public void onPanelSlide(View panel, float slideOffset) {

    }

    @Override
    public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {

    }
}
