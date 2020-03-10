package com.example.music.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import com.example.music.App;
import com.example.music.bridge.callback.SharedViewModel;
import com.example.music.data.manager.NetState;
import com.example.music.data.manager.NetworkStateManager;

import fragment.NavHostFragment;

public class BaseFragment extends Fragment {

    private static final Handler HANDLER = new Handler();
    protected AppCompatActivity mActivity;
    protected SharedViewModel mSharedViewModel;
    protected boolean mAnimationEnterLoaded;
    protected boolean mAnimationLoaded;
    protected boolean mInitDataCame;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (AppCompatActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedViewModel = getAppViewModelProvider().get(SharedViewModel.class);
        NetworkStateManager.getInstance().mNetworkStateCallback.observe(this, this::onNetworkStateChanged);
    }

    protected void onNetworkStateChanged(NetState netState) {

    }

    @Nullable
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        HANDLER.postDelayed(() -> {
            mAnimationLoaded = true;
            if (mInitDataCame && !mAnimationEnterLoaded) {
                mAnimationEnterLoaded = true;
                loadInitData();
            }
        }, 280);
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    public void loadInitData() {
    }

    public void showLongToast(String text) {
        Toast.makeText(mActivity.getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }

    public void showShortToast(String text) {
        Toast.makeText(mActivity.getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    public void showLongToast(int stringRes) {
        showLongToast(mActivity.getApplicationContext().getString(stringRes));
    }

    public void showShortToast(int stringRes) {
        showShortToast(mActivity.getApplicationContext().getString(stringRes));
    }

    protected ViewModelProvider getAppViewModelProvider() {
        return ((App) mActivity.getApplicationContext()).getAppViewModelProvider(mActivity);
    }

    protected ViewModelProvider getFragmentViewModelProvider(Fragment fragment) {
        return new ViewModelProvider(fragment, fragment.getDefaultViewModelProviderFactory());
    }

    public SharedViewModel getSharedViewModel(){
        return mSharedViewModel;
    }

    protected NavController nav(){
        return NavHostFragment.findNavController(this);
    }
}
