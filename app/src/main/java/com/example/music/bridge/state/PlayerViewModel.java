package com.example.music.bridge.state;

import android.graphics.drawable.Drawable;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.ViewModel;

import com.example.music.R;
import com.example.music.player.PlayerManager;
import com.example.music.utils.Utils;
import com.example.player.PlayingInfoManager;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

public class PlayerViewModel extends ViewModel {

    public final ObservableField<String> title = new ObservableField<>();

    public final ObservableField<String> artist = new ObservableField<>();

    public final ObservableField<String> coverImg = new ObservableField<>();

    public final ObservableField<Drawable> placeHolder = new ObservableField<>();

    public final ObservableInt maxSeekDuration = new ObservableInt();

    public final ObservableInt currentSeekPosition = new ObservableInt();

    public final ObservableBoolean isPlaying = new ObservableBoolean();

    public final ObservableField<MaterialDrawableBuilder.IconValue> playModeIcon = new ObservableField<>();

    {
        title.set(Utils.getApp().getString(R.string.app_name));
        artist.set(Utils.getApp().getString(R.string.app_name));
        placeHolder.set(Utils.getApp().getResources().getDrawable(R.mipmap.bg_album_default));
        Enum repeatMode = PlayerManager.getInstance().getRepeatMode();
        if (repeatMode == PlayingInfoManager.RepeatMode.LIST_LOOP) {
            playModeIcon.set(MaterialDrawableBuilder.IconValue.REPEAT);
        } else if (repeatMode == PlayingInfoManager.RepeatMode.ONE_LOOP) {
            playModeIcon.set(MaterialDrawableBuilder.IconValue.REPEAT_ONCE);
        } else {
            playModeIcon.set(MaterialDrawableBuilder.IconValue.SHUFFLE);
        }
    }
}
