package com.example.music.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseBindingAdapter<M, B extends ViewDataBinding> extends RecyclerView.Adapter {

    protected final Context mContext;
    protected List<M> mList = new ArrayList<>();

    public BaseBindingAdapter(Context context) {
        this.mContext = context;
    }

    public List<M> getList() {
        return mList;
    }

    public void setList(List<M> list) {
        this.mList = list;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        B binding = DataBindingUtil.inflate(LayoutInflater.from(this.mContext), getLayoutResId(viewType), parent, false);
        return new BaseBindingViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        B binding = DataBindingUtil.getBinding(holder.itemView);
        onBindItem(binding, mList.get(position), holder);
        if (binding != null) {
            binding.executePendingBindings();
        }
    }

    @LayoutRes
    protected abstract int getLayoutResId(int viewType);

    protected abstract void onBindItem(B binding, M item, RecyclerView.ViewHolder holder);

    public class BaseBindingViewHolder extends RecyclerView.ViewHolder {

        public BaseBindingViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
