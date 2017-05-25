package com.leafplain.demo.databinding.base.basecontract;

/**
 * Created by kennethyeh on 2017/5/2.
 */

public interface LoadingContract {

    interface View<T1, T2>{
        void onFinished(T1 result);
        void onFail(T2 fail);
    }

    interface Presenter{
        void start();
        void cancel();
    }
}
