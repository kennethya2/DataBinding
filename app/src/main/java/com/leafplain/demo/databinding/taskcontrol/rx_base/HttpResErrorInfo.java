package com.leafplain.demo.databinding.taskcontrol.rx_base;

/**
 * Created by kennethyeh on 2017/12/17.
 */

public class HttpResErrorInfo {
    public final static int ERROR_CODE_UN_KNOW = 2073;

    public String errorBody     = "";
    public String errorMessage  = "";
    public int httpStatusCode   = ERROR_CODE_UN_KNOW;
    public Throwable throwable;
    public BaseResErrorInfo resInfo;
}

