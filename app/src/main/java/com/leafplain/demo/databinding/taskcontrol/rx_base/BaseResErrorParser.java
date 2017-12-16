package com.leafplain.demo.databinding.taskcontrol.rx_base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import org.json.JSONException;

import java.io.StringReader;

/**
 * Created by kennethyeh on 2017/12/17.
 */

public class BaseResErrorParser {

    public static BaseResErrorParser getInstance(){
        return new BaseResErrorParser();
    }

    private BaseResErrorParser(){}

    public BaseResErrorInfo parsing(String value) throws JSONException {

        BaseResErrorInfo info = null;

        StringReader jsonSR  = new StringReader(value);
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();

        JsonReader reader = new JsonReader(jsonSR);
        reader.setLenient(true);
        info = gson.fromJson(reader, BaseResErrorInfo.class);

        return info;
    }
}
