package com.example.music.bridge.callback;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class SharedViewModel extends ViewModel {

    public static final List<String> TAG_OF_SECONDRY_PAGES = new ArrayList<>();
    public final UnPeekLiveData<Boolean> timeToAddSlideListener = new UnPeekLiveData<>();
    public final UnPeekLiveData<Boolean> closeSlidePanelIfExpanded = new UnPeekLiveData<>();
    public final UnPeekLiveData<Boolean> activityCanBeClosedDirectly = new UnPeekLiveData<>();
    public final UnPeekLiveData<Boolean> openOrCloseDrawer = new UnPeekLiveData<>();
    public final UnPeekLiveData<Boolean> enableSwipeDrawer = new UnPeekLiveData<>();
}
