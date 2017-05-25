package com.leafplain.demo.databinding.base.basecontract;

/**
 * Created by kennethyeh on 2017/4/28.
 */

public interface MainContract {

    interface Presenter<T>{
        void onTypeClick(T info);
    }
}
