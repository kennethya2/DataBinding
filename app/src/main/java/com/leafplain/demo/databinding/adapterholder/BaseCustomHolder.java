package com.leafplain.demo.databinding.adapterholder;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by kennethyeh on 2017/5/5.
 */

public class BaseCustomHolder extends RecyclerView.ViewHolder {
    private ViewDataBinding binding;
    public BaseCustomHolder(View v) {
        super(v);
    }
    public void setBinding(ViewDataBinding binding){
        this.binding =  binding;
    }
    public ViewDataBinding getBinding() {
        return binding;
    }
}