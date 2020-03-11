package com.example.music.ui.page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.music.R;
import com.example.music.base.BaseFragment;
import com.example.music.bridge.state.MainViewModel;
import com.example.music.databinding.FragmentMainBinding;

public class MainFragment extends BaseFragment {

    private FragmentMainBinding mBinding;
    private MainViewModel mMainViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainViewModel = getFragmentViewModelProvider(this).get(MainViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mBinding = FragmentMainBinding.bind(view);
        mBinding.setClick(new ClickProxy());
        mBinding.setVm(mMainViewModel);
        return view;
    }

    public class ClickProxy {
        public void openMenu() {
            mSharedViewModel.openOrCloseDrawer.setValue(true);
        }

        public void search() {
//            nav().navigate(R.id.ac);
        }
    }
}
