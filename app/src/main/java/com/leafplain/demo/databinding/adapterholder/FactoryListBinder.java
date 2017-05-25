package com.leafplain.demo.databinding.adapterholder;

import android.support.v7.widget.RecyclerView;

import com.leafplain.demo.databinding.BR;
import com.leafplain.demo.databinding.MyApplication;
import com.leafplain.demo.databinding.databinding.HolderPhotoPicBinding;
import com.leafplain.demo.databinding.databinding.HolderTitleBinding;
import com.leafplain.demo.databinding.datamodel.info.ListItemInfo;

/**
 * Created by kennethyeh on 2017/5/5.
 */

public class FactoryListBinder {


    public void bindHolder(RecyclerView.ViewHolder viewHolder, ListItemInfo item){
        int type = item.type;
        if(type == ListItemInfo.ListType.TITLE){
            BaseCustomHolder holder = (BaseCustomHolder) viewHolder;
            HolderTitleBinding binding = (HolderTitleBinding)holder.getBinding();
            binding.setTitle((String) item.data);
//            binding.titleTV.setText((String)item.data);
        }

        if(type == ListItemInfo.ListType.PHOTO){
            BaseBindingHolder holder = (BaseBindingHolder) viewHolder;
            holder.getBinding().setVariable(BR.photoUrl, item.data);
            holder.getBinding().executePendingBindings();
        }

        if(type == ListItemInfo.ListType.PHOTO_PIC){
            BaseCustomHolder holder = (BaseCustomHolder) viewHolder;
            HolderPhotoPicBinding mHolderPhotoPicBinding = (HolderPhotoPicBinding) holder.getBinding();
            String url = (String) item.data;
            MyApplication.imageLoader.displayImage(url,
                    mHolderPhotoPicBinding.photoPicIV,
                    MyApplication.options,
                    MyApplication.animateFirstListener);
        }
    }
}
