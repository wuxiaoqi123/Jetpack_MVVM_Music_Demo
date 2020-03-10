package com.example.music.ui.page;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.music.R;
import com.example.music.base.BaseFragment;
import com.example.music.bridge.state.DrawerViewModel;
import com.example.music.databinding.FragmentDrawerBinding;

public class DrawerFragment extends BaseFragment {

    private FragmentDrawerBinding mBinding;
    private DrawerViewModel mDrawerViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDrawerViewModel = getFragmentViewModelProvider(this).get(DrawerViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawer, container, false);
        mBinding = FragmentDrawerBinding.bind(view);
        mBinding.setVm(mDrawerViewModel);
        mBinding.setClick(new ClickProxy());
        return view;
    }

    public class ClickProxy {

        public void logoClick() {
            String u = "https://github.com/KunMinX/Jetpack-MVVM-Best-Practice";
            Uri uri = Uri.parse(u);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }
}
