package com.example.music.ui.page;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music.R;
import com.example.music.base.BaseFragment;
import com.example.music.bridge.request.InfoRequestViewModel;
import com.example.music.bridge.state.DrawerViewModel;
import com.example.music.data.bean.LibraryInfo;
import com.example.music.databinding.AdapterLibraryBinding;
import com.example.music.databinding.FragmentDrawerBinding;
import com.example.music.ui.adapter.SimpleBaseBindingAdapter;

public class DrawerFragment extends BaseFragment {

    private FragmentDrawerBinding mBinding;
    private DrawerViewModel mDrawerViewModel;
    private InfoRequestViewModel mInfoRequestViewModel;
    private SimpleBaseBindingAdapter<LibraryInfo, AdapterLibraryBinding> mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDrawerViewModel = getFragmentViewModelProvider(this).get(DrawerViewModel.class);
        mInfoRequestViewModel = getFragmentViewModelProvider(this).get(InfoRequestViewModel.class);
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getLifecycle().addObserver(mInfoRequestViewModel.getTestUseCase());
        mAdapter = new SimpleBaseBindingAdapter<LibraryInfo, AdapterLibraryBinding>(getContext(), R.layout.adapter_library) {
            @Override
            protected void onSimpleBindItem(AdapterLibraryBinding binding, LibraryInfo item, RecyclerView.ViewHolder holder) {
                binding.setInfo(item);
                binding.getRoot().setOnClickListener(v -> {
                    Uri uri = Uri.parse(item.url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                });
            }
        };
        mBinding.rv.setAdapter(mAdapter);
        mInfoRequestViewModel.getLibraryLiveData().observe(getViewLifecycleOwner(), libraryInfos -> {
            mInitDataCame = true;
            if (mAnimationLoaded && libraryInfos != null) {
                //noinspection unchecked
                mAdapter.setList(libraryInfos);
                mAdapter.notifyDataSetChanged();
            }
        });
        mInfoRequestViewModel.requestLibraryInfo();
        mInfoRequestViewModel.getTestXXX().observe(getViewLifecycleOwner(), s -> {

        });
        mInfoRequestViewModel.requestTestXXX();
    }

    @Override
    public void loadInitData() {
        super.loadInitData();
        if (mInfoRequestViewModel.getLibraryLiveData().getValue() != null) {
            //noinspection unchecked
            mAdapter.setList(mInfoRequestViewModel.getLibraryLiveData().getValue());
            mAdapter.notifyDataSetChanged();
        }
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
