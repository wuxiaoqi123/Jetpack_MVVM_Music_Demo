package com.example.music.ui.adapter;

import android.content.Context;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public abstract class SimpleBaseBindingAdapter<M, B extends ViewDataBinding> extends BaseBindingAdapter {

    private final int layout;

    public SimpleBaseBindingAdapter(Context context, int layout) {
        super(context);
        this.layout = layout;
    }

    @Override
    protected int getLayoutResId(int viewType) {
        return layout;
    }

    @Override
    protected void onBindItem(ViewDataBinding binding, Object item, RecyclerView.ViewHolder holder) {
        onSimpleBindItem((B) binding, (M) item, holder);
    }

    protected abstract void onSimpleBindItem(B binding, M item, RecyclerView.ViewHolder holder);
}
