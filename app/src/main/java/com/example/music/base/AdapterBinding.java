package com.example.music.base;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.core.view.GravityCompat;
import androidx.databinding.BindingAdapter;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.music.R;
import com.example.music.ui.adapter.CommonViewPagerAdapter;
import com.example.music.utils.ClickUtils;
import com.example.music.utils.Utils;
import com.google.android.material.tabs.TabLayout;

public class AdapterBinding {

    @BindingAdapter(value = {"isOpenDrawer"}, requireAll = false)
    public static void openDrawer(DrawerLayout drawerLayout, boolean isOpenDrawer) {
        if (isOpenDrawer && !drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.openDrawer(GravityCompat.START);
        } else {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @BindingAdapter(value = {"allowDrawerOpen"}, requireAll = false)
    public static void allowDrawerOpen(DrawerLayout drawerLayout, boolean allowDrawerOpen) {
        drawerLayout.setDrawerLockMode(allowDrawerOpen ? DrawerLayout.LOCK_MODE_UNLOCKED :
                DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @BindingAdapter(value = {"onClickWithDebouncing"}, requireAll = false)
    public static void onClickWithDebouncing(View view, View.OnClickListener clickListener) {
        ClickUtils.applySingleDebouncing(view, clickListener);
    }

    @BindingAdapter(value = {"initTabAndPage"}, requireAll = false)
    public static void initTabAndPage(TabLayout tabLayout, boolean initTabAndPage) {
        if (initTabAndPage) {
            int count = tabLayout.getTabCount();
            String[] title = new String[count];
            for (int i = 0; i < count; i++) {
                title[i] = tabLayout.getTabAt(i).getText().toString();
            }
            ViewPager viewPager = (tabLayout.getRootView()).findViewById(R.id.view_pager);
            if (viewPager != null) {
                viewPager.setAdapter(new CommonViewPagerAdapter(count, false, title));
                tabLayout.setupWithViewPager(viewPager);
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @BindingAdapter(value = {"pageAssetPath"}, requireAll = false)
    public static void pageAssetPath(WebView webView, String assetPath) {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Uri uri = request.getUrl();
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
                return true;
            }
        });
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        String url = "file:///android_asset/" + assetPath;
        webView.loadUrl(url);
    }
}
