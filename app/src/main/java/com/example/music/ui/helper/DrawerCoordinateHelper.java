package com.example.music.ui.helper;

import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.example.music.base.BaseFragment;
import com.example.music.bridge.callback.SharedViewModel;
import com.example.music.bridge.callback.UnPeekLiveData;

public class DrawerCoordinateHelper implements DefaultLifecycleObserver, View.OnTouchListener {

    private static final DrawerCoordinateHelper S_HELPER = new DrawerCoordinateHelper();
    public final UnPeekLiveData<Boolean> openDrawer = new UnPeekLiveData<>();
    private float downX;
    private float downY;

    private DrawerCoordinateHelper() {
    }

    public static DrawerCoordinateHelper getInstance() {
        return S_HELPER;
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        SharedViewModel.TAG_OF_SECONDRY_PAGES.add(owner.getClass().getSimpleName());
        ((BaseFragment) owner).getSharedViewModel().enableSwipeDrawer
                .setValue(SharedViewModel.TAG_OF_SECONDRY_PAGES.size() == 0);
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        SharedViewModel.TAG_OF_SECONDRY_PAGES.remove(owner.getClass().getSimpleName());
        ((BaseFragment) owner).getSharedViewModel().enableSwipeDrawer
                .setValue(SharedViewModel.TAG_OF_SECONDRY_PAGES.size() == 0);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = x;
                downY = y;
                break;
            case MotionEvent.ACTION_UP:
                float dx = x - downX;
                float dy = y - downY;
                if (Math.abs(dx) > 8 && Math.abs(dy) > 8) {
                    int orientation = getOrientation(dx, dy);
                    switch (orientation) {
                        case 'r':
                            openDrawer.setValue(true);
                            break;
                        case 'l':
                            break;
                        case 'b':
                            break;
                        case 't':
                            break;
                        default:
                            break;
                    }
                }
                break;
            default:
                break;
        }
        return false;
    }

    private int getOrientation(float dx, float dy) {
        if (Math.abs(dx) > Math.abs(dy)) {
            return dx > 0 ? 'r' : 'l';
        } else {
            return dy > 0 ? 'b' : 't';
        }
    }
}
