package com.leafplain.demo.databinding.taskcontrol.rx_base;

import rx.functions.Func1;

/**
 * Created by kennethyeh on 2017/12/17.
 */

public class ResResultFilter<T> implements Func1<BaseResInfo<T>, T> {

    private FilterCallback callback;
    public interface FilterCallback<T>{
        void onSuccess(BaseResInfo errInfo);
        void onNotSuccess(BaseResInfo errInfo);
    }
    public ResResultFilter(FilterCallback callback){
        this.callback = callback;
    }

    @Override
    public T call(BaseResInfo<T> baseResInfo) {
        if(resSuccessValidate(baseResInfo.status, baseResInfo.errcode)){
            callback.onSuccess(baseResInfo);
            return baseResInfo.data;
        }else{
            callback.onNotSuccess(baseResInfo);
            return baseResInfo.data;
        }
    }

    public static boolean resSuccessValidate(int status, String errcode){
        if(status!=200){
            return false;
        }
        if( errcode.trim().equals("") || errcode.trim().equals("0") ){
            return true;
        }else{
            return false;
        }
    }

}
