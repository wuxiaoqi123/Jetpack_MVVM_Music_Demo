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
import com.example.music.bridge.state.PlayerViewModel;
import com.example.music.databinding.FragmentPlayerBinding;

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

    public class ClickProxy implements SeekBar.OnSeekBarChangeListener {

        public void playMode() {

        }

        public void previous() {

        }

        public void togglePlay() {

        }

        public void next() {

        }

        public void showPlayList() {

        }

        public void slideDown() {

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

        }
    }
}
