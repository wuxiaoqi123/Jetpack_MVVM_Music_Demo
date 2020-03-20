package com.example.music.ui.page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.music.R;
import com.example.music.base.BaseFragment;
import com.example.music.bridge.callback.SharedViewModel;
import com.example.music.bridge.state.PlayerViewModel;
import com.example.music.databinding.FragmentPlayerBinding;
import com.example.music.player.PlayerManager;
import com.example.music.ui.view.PlayerSlideListener;
import com.example.player.PlayingInfoManager;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

public class PlayFragment extends BaseFragment {

    private FragmentPlayerBinding mBinding;
    private PlayerViewModel mPlayerViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPlayerViewModel = getFragmentViewModelProvider(this).get(PlayerViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player, container, false);
        mBinding = FragmentPlayerBinding.bind(view);
        mBinding.setClick(new ClickProxy());
        mBinding.setVm(mPlayerViewModel);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSharedViewModel.timeToAddSlideListener.observe(getViewLifecycleOwner(), aBoolean -> {
            if (view.getParent().getParent() instanceof SlidingUpPanelLayout) {
                SlidingUpPanelLayout sliding = (SlidingUpPanelLayout) view.getParent().getParent();
                sliding.addPanelSlideListener(new PlayerSlideListener(mBinding, sliding));
                sliding.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
                    @Override
                    public void onPanelSlide(View panel, float slideOffset) {

                    }

                    @Override
                    public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                        if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                            SharedViewModel.TAG_OF_SECONDRY_PAGES.add(this.getClass().getSimpleName());
                        } else {
                            SharedViewModel.TAG_OF_SECONDRY_PAGES.remove(this.getClass().getSimpleName());
                        }
                        mSharedViewModel.enableSwipeDrawer.setValue(SharedViewModel.TAG_OF_SECONDRY_PAGES.size() == 0);
                    }
                });
            }
        });
        PlayerManager.getInstance().getChangeMusicLiveData().observe(getViewLifecycleOwner(), changeMusic -> {
            mPlayerViewModel.title.set(changeMusic.title);
            mPlayerViewModel.artist.set(changeMusic.summary);
            mPlayerViewModel.coverImg.set(changeMusic.img);
        });
        PlayerManager.getInstance().getPlayingMusicLiveData().observe(getViewLifecycleOwner(), playingMusic -> {
            mPlayerViewModel.maxSeekDuration.set(playingMusic.duration);
            mPlayerViewModel.currentSeekPosition.set(playingMusic.playerPosition);
        });
        PlayerManager.getInstance().getPauseLiveData().observe(getViewLifecycleOwner(), aBoolean -> {
            mPlayerViewModel.isPlaying.set(!aBoolean);
        });
        PlayerManager.getInstance().getPlayModeLiveData().observe(getViewLifecycleOwner(), anEnum -> {
            int tip;
            if (anEnum == PlayingInfoManager.RepeatMode.LIST_LOOP) {
                mPlayerViewModel.playModeIcon.set(MaterialDrawableBuilder.IconValue.REPEAT);
                tip = R.string.play_repeat;
            } else if (anEnum == PlayingInfoManager.RepeatMode.ONE_LOOP) {
                mPlayerViewModel.playModeIcon.set(MaterialDrawableBuilder.IconValue.REPEAT_ONCE);
                tip = R.string.play_repeat_once;
            } else {
                mPlayerViewModel.playModeIcon.set(MaterialDrawableBuilder.IconValue.SHUFFLE);
                tip = R.string.play_shuffle;
            }
            if (view.getParent().getParent() instanceof SlidingUpPanelLayout) {
                SlidingUpPanelLayout sliding = (SlidingUpPanelLayout) view.getParent().getParent();
                if (sliding.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    showShortToast(tip);
                }
            }
        });
        mSharedViewModel.closeSlidePanelIfExpanded.observe(getViewLifecycleOwner(), aBoolean -> {
            if (view.getParent().getParent() instanceof SlidingUpPanelLayout) {
                SlidingUpPanelLayout sliding = (SlidingUpPanelLayout) view.getParent().getParent();
                if (sliding.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    sliding.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                } else {
                    mSharedViewModel.activityCanBeClosedDirectly.setValue(true);
                }
            } else {
                mSharedViewModel.activityCanBeClosedDirectly.setValue(true);
            }
        });
    }

    public class ClickProxy implements SeekBar.OnSeekBarChangeListener {

        public void playMode() {
            PlayerManager.getInstance().changeMode();
        }

        public void previous() {
            PlayerManager.getInstance().playPrevious();
        }

        public void togglePlay() {
            PlayerManager.getInstance().togglePlay();
        }

        public void next() {
            PlayerManager.getInstance().playNext();
        }

        public void showPlayList() {
            showShortToast(R.string.unfinished);
        }

        public void slideDown() {
            mSharedViewModel.closeSlidePanelIfExpanded.setValue(true);
        }

        public void more() {

        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            PlayerManager.getInstance().setSeek(seekBar.getProgress());
        }
    }
}
