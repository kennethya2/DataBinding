package com.leafplain.demo.databinding.base.basecontrol;

/**
 * Created by kennethyeh on 2017/5/4.
 */

public interface ParsingControllable<T> {

    void setResponseListener(T listener);
    void startParsing();
    void cancel();

}
