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
import com.example.music.bridge.state.SearchViewModel;
import com.example.music.databinding.FragmentSearchBinding;
import com.example.music.ui.helper.DrawerCoordinateHelper;

public class SearchFragment extends BaseFragment {

    private FragmentSearchBinding mBinding;
    private SearchViewModel mSearchViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSearchViewModel = getFragmentViewModelProvider(this).get(SearchViewModel.class);
        getLifecycle().addObserver(DrawerCoordinateHelper.getInstance());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        mBinding = FragmentSearchBinding.bind(view);
        mBinding.setClick(new ClickProxy());
        mBinding.setVm(mSearchViewModel);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public class ClickProxy {

        public void back() {
            nav().navigateUp();
        }

        public void testNav() {
            String url = "https://xiaozhuanlan.com/topic/5860149732";
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }

        public void subscribe() {
            String url = "https://xiaozhuanlan.com/kunminx";
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }
}
