package com.example.music.bridge.state;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    public final ObservableBoolean initTabAndPage = new ObservableBoolean();
    public final ObservableField<String> pageAssetPath = new ObservableField<>();
}
