package com.example.music.ui.page;

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

    public class ClickProxy {

        public void back() {
            nav().navigateUp();
        }

        public void testNav() {

        }

        public void subscribe() {

        }
    }
}
