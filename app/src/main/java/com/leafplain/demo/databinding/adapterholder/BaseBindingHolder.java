package com.leafplain.demo.databinding.adapterholder;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by kennethyeh on 2017/5/5.
 */

public class BaseBindingHolder extends RecyclerView.ViewHolder {
    private ViewDataBinding binding;

    public BaseBindingHolder(View v) {
        super(v);
        binding = DataBindingUtil.bind(v);
    }

    public ViewDataBinding getBinding() {
        return binding;
    }
}
