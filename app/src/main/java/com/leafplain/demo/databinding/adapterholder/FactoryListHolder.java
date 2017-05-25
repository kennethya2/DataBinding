package com.leafplain.demo.databinding.adapterholder;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leafplain.demo.databinding.R;
import com.leafplain.demo.databinding.databinding.HolderPhotoPicBinding;
import com.leafplain.demo.databinding.databinding.HolderTitleBinding;
import com.leafplain.demo.databinding.datamodel.info.ListItemInfo;

/**
 * Created by kennethyeh on 2017/5/5.
 */

public class FactoryListHolder {

    public RecyclerView.ViewHolder getHolder(ViewGroup parent, int viewType){
        View v = null;
        RecyclerView.ViewHolder holder = null ;

        switch (viewType){
            case ListItemInfo.ListType.TITLE:
                v       = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_title, parent, false);
                holder  = new BaseCustomHolder(v);
                HolderTitleBinding mHolderTitleBinding = DataBindingUtil.bind(v);
                ((BaseCustomHolder)holder).setBinding(mHolderTitleBinding);
                break;
            case ListItemInfo.ListType.PHOTO:
                v       = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_photo, parent, false);
                holder  = new BaseBindingHolder(v);
                break;
            case ListItemInfo.ListType.PHOTO_PIC:
                v       = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_photo_pic, parent, false);
                holder  = new BaseCustomHolder(v);
                HolderPhotoPicBinding mHolderPhotoPicBinding = DataBindingUtil.bind(v);
                ((BaseCustomHolder)holder).setBinding(mHolderPhotoPicBinding);
                break;
        }
        return holder;
    }
}
