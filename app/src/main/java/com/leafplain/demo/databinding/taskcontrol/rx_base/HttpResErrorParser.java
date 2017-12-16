package com.leafplain.demo.databinding.taskcontrol.rx_base;

import com.leafplain.demo.databinding.DevLog;

import java.io.IOException;
import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by kennethyeh on 2017/12/17.
 */

public class HttpResErrorParser {
    private static String TAG = "HttpResErrorParser";

    public static HttpResErrorParser getInstance(){
        return new HttpResErrorParser();
    }

    public HttpResErrorInfo convertError(Throwable e){
        HttpResErrorInfo errorInfo = new HttpResErrorInfo();
        errorInfo.throwable = e;
        if (e instanceof HttpException) {
            errorInfo.httpStatusCode    = ((HttpException)e).response().code();
            try {
                String errorBody= ((HttpException)e).response().errorBody().string();
                errorInfo.errorBody     = errorBody;
                errorInfo.errorMessage  = "HttpException";
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            try {
                BaseResErrorInfo error = BaseResErrorParser.getInstance().parsing(errorInfo.errorBody);
                errorInfo.resInfo = error;
            } catch (Exception e2) {
                DevLog.d(TAG,""+e2.toString());
            }

            DevLog.d(TAG, "status:"+errorInfo.httpStatusCode);
            DevLog.d(TAG, "errorBody:"+errorInfo.errorBody);
            DevLog.d(TAG, "errorMessage:"+errorInfo.errorMessage);

        } else if (e instanceof SocketTimeoutException) {
            DevLog.d(TAG, "onTimeout");
            errorInfo.errorMessage= "onTimeout";
            // onTimeout();
        } else if (e instanceof IOException) {
            DevLog.d(TAG, "onNetworkError");
            errorInfo.errorMessage= "onNetworkError";
            // onNetworkError();
        } else {
            // onUnknownError(e.getMessage());
            DevLog.d(TAG, "onUnknownError");
            errorInfo.errorMessage= "onUnknownError";
        }
        return errorInfo;
    }
}

