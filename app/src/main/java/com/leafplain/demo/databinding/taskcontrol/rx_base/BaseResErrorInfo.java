package com.leafplain.demo.databinding.taskcontrol.rx_base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by kennethyeh on 2017/12/17.
 */

public class BaseResErrorInfo implements Serializable
{

    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("errcode")
    @Expose
    public String errcode;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public String data;
    private final static long serialVersionUID = -246260563124341519L;

}