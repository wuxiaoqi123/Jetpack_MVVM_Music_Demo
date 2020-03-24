package com.example.music;

import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.music.base.BaseActivity;
import com.example.music.bridge.state.MainActivityViewModel;
import com.example.music.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding mBinding;
    private MainActivityViewModel mMainActivityViewModel;
    private boolean isListened = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainActivityViewModel = getActivityViewModelProvider(this).get(MainActivityViewModel.class);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.setLifecycleOwner(this);
        mBinding.setVm(mMainActivityViewModel);
        mSharedViewModel.activityCanBeClosedDirectly.observe(this, aBoolean -> {
            NavController nav = Navigation.findNavController(this, R.id.main_fragment_host);
            if (nav.getCurrentDestination() != null && nav.getCurrentDestination().getId() != R.id.mainFragment) {
                nav.navigateUp();
            } else if (mBinding.dl != null && mBinding.dl.isDrawerOpen(GravityCompat.START)) {
                mBinding.dl.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        });
        mSharedViewModel.openOrCloseDrawer.observe(this, aBoolean -> {
            mMainActivityViewModel.openDrawer.setValue(aBoolean);
        });
        mSharedViewModel.enableSwipeDrawer.observe(this, aBoolean -> {
            mMainActivityViewModel.allowDrawerOpen.setValue(aBoolean);
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!isListened) {
            mSharedViewModel.timeToAddSlideListener.setValue(true);
            isListened = true;
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        mSharedViewModel.closeSlidePanelIfExpanded.setValue(true);
    }
}
