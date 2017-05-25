package com.leafplain.demo.databinding.taskcontrol;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.leafplain.demo.databinding.base.basecontrol.ParsingControllable;
import com.leafplain.demo.databinding.datamodel.info.APIInfo;
import com.leafplain.demo.databinding.datamodel.info.ListItemInfo;
import com.leafplain.demo.databinding.presenter.requestsample.PhotoListPresenter;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by kennethyeh on 2017/5/24.
 */

public class LoadReqControllerOKHttp implements ParsingControllable<PhotoListPresenter.ResponseListener> {

    private static String TAG = "LoadReqControllerOKHttp";

    private OkHttpClient client;
    private List<ListItemInfo> mList = new ArrayList<>();
    private PhotoListPresenter.ResponseListener mListener=null;

    private String URL = "https://raw.githubusercontent.com/kennethya2/github-resource/master/unsplash/image-list.json";

    private final int PARSE_RESPONSE =1;
    private Handler requestHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PARSE_RESPONSE:
                    if(mListener!=null){
                        mListener.onResultList(mList);
                    }
                    break;
            }
        }
    };

    @Override
    public void setResponseListener(PhotoListPresenter.ResponseListener listener) {
        mListener = listener;
    }

    @Override
    public void startParsing() {
        client = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder().url(URL);
        //可省略 default GET
        requestBuilder.method("GET",null);
        Request request = requestBuilder.build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // not in main thread
                String json = response.body().string();
                Log.d(TAG, json);
                parseJSON(json);
            }
            @Override
            public void onFailure(Call call, IOException e) {
            }
        });
    }

    private void parseJSON(String json){

            APIInfo.PhotoList mPhotoList = null;
            StringReader jsonSR  = new StringReader(json);
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.create();
            JsonReader reader = new JsonReader(jsonSR);
            reader.setLenient(true);
            mPhotoList = gson.fromJson(reader, APIInfo.PhotoList.class);

            List<APIInfo.PhotoInfo> images = mPhotoList.images;
            for(int i =0 ; i<images.size() ; i++){
                APIInfo.PhotoInfo photo = images.get(i);

                ListItemInfo titleInfo = new ListItemInfo();
                titleInfo.type = ListItemInfo.ListType.TITLE;
                titleInfo.data = photo.title;
                mList.add(titleInfo);

                ListItemInfo picInfo = new ListItemInfo();
                picInfo.type = ListItemInfo.ListType.PHOTO_PIC;
                picInfo.data = photo.filePath;
                mList.add(picInfo);

                ListItemInfo descInfo = new ListItemInfo();
                descInfo.type=ListItemInfo.ListType.PHOTO;
                descInfo.data=photo.desc;
                mList.add(descInfo);
            }

            Message msg = Message.obtain(); // Creates an new Message instance
            msg.what = PARSE_RESPONSE;
            msg.obj = mList;// Put the ArrayList into Message, into "obj" field.
            msg.setTarget(requestHandler); // Set the Handler
            msg.sendToTarget(); //Send the message
    }

    @Override
    public void cancel() {

    }
}
