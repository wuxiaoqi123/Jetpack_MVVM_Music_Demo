package com.example.music.ui.page;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.music.R;
import com.example.music.base.BaseFragment;
import com.example.music.bridge.request.MusicRequestViewModel;
import com.example.music.bridge.state.MainViewModel;
import com.example.music.data.bean.TestAlbum;
import com.example.music.databinding.AdapterPlayItemBinding;
import com.example.music.databinding.FragmentMainBinding;
import com.example.music.player.PlayerManager;
import com.example.music.ui.adapter.SimpleBaseBindingAdapter;

public class MainFragment extends BaseFragment {

    private FragmentMainBinding mBinding;
    private MainViewModel mMainViewModel;
    private MusicRequestViewModel mMusicRequestViewModel;
    private SimpleBaseBindingAdapter<TestAlbum.TestMusic, AdapterPlayItemBinding> mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainViewModel = getFragmentViewModelProvider(this).get(MainViewModel.class);
        mMusicRequestViewModel = getFragmentViewModelProvider(this).get(MusicRequestViewModel.class);
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMainViewModel.initTabAndPage.set(true);
        mMainViewModel.pageAssetPath.set("summary.html");
        mAdapter = new SimpleBaseBindingAdapter<TestAlbum.TestMusic, AdapterPlayItemBinding>(getContext(), R.layout.adapter_play_item) {
            @Override
            protected void onSimpleBindItem(AdapterPlayItemBinding binding, TestAlbum.TestMusic item, RecyclerView.ViewHolder holder) {
                binding.tvTitle.setText(item.title);
                binding.tvArtist.setText(item.artist.name);
                Glide.with(binding.ivCover.getContext()).load(item.coverImg).into(binding.ivCover);
                int currentIndex = PlayerManager.getInstance().getAlbumIndex();
                binding.ivPlayStatus.setColor(currentIndex == holder.getAdapterPosition() ?
                        getResources().getColor(R.color.gray) : Color.TRANSPARENT);
                binding.getRoot().setOnClickListener(v -> {
                    PlayerManager.getInstance().playAudio(holder.getAdapterPosition());
                });
            }
        };
        mBinding.rv.setAdapter(mAdapter);
        PlayerManager.getInstance().getChangeMusicLiveData().observe(getViewLifecycleOwner(), changeMusic -> {
            mAdapter.notifyDataSetChanged();
        });
        mMusicRequestViewModel.getFreeMusicsLiveData().observe(getViewLifecycleOwner(), testAlbum -> {
            if (testAlbum != null && testAlbum.musics != null) {
                //noinspection unchecked
                mAdapter.setList(testAlbum.musics);
                mAdapter.notifyDataSetChanged();
                if (PlayerManager.getInstance().getAlbum() == null ||
                        !PlayerManager.getInstance().getAlbum().albumId.equals(testAlbum.albumId)) {
                    PlayerManager.getInstance().loadAlbum(testAlbum);
                }
            }
        });
        if (PlayerManager.getInstance().getAlbum() == null) {
            mMusicRequestViewModel.requestFreeMusics();
        } else {
            //noinspection unchecked
            mAdapter.setList(PlayerManager.getInstance().getAlbum().musics);
            mAdapter.notifyDataSetChanged();
        }
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
