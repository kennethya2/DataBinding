package com.leafplain.demo.databinding.taskcontrol.rx_base;

import android.util.Log;

import rx.Subscriber;

/**
 * Created by kennethyeh on 2017/12/17.
 */

public class HttpResSubscriber<T> extends Subscriber<T> {

    private static String TAG = "HttpResSubscriber";

    private ResCallback mResCallback;
    public interface ResCallback<T>{
        void onResult(T list);
        void onError(HttpResErrorInfo error);
        void tokenExpired();
    }
    public HttpResSubscriber(ResCallback mResCallback){
        this.mResCallback = mResCallback;
    }

    @Override
    public void onCompleted() {
        Log.d(TAG, "onCompleted");
    }

    @Override
    public void onError(Throwable e) {
        Log.d(TAG, "onError");
        Log.d(TAG, "Throwable:"+e.toString());
        // HTTP != 200 or connect error
        HttpResErrorInfo errorInfo = HttpResErrorParser.getInstance().convertError(e);
        if(errorInfo.resInfo!=null && parseResOnErrorType(errorInfo.resInfo) == ResOnErrorType.Type_Token_Expired){
            mResCallback.tokenExpired();
            return;
        }
        mResCallback.onError(errorInfo);

        Log.d(TAG, "----");
    }

    @Override
    public void onNext(T t) {
        mResCallback.onResult(t);
    }


    public interface ResOnErrorType{
        int Type_None           = 0;
        int Type_Token_Expired  = 1;
    }

    public static int parseResOnErrorType(BaseResErrorInfo error){
        int type = ResOnErrorType.Type_None;
        if(error.status == 401 && error.errcode.equalsIgnoreCase("T00001")){
            type = ResOnErrorType.Type_Token_Expired;
        }
        return type;
    }
}
