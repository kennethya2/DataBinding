package com.leafplain.demo.databinding.taskcontrol;

import android.util.Log;

import com.leafplain.demo.databinding.base.basecontrol.ParsingControllable;
import com.leafplain.demo.databinding.datamodel.info.APIInfo;
import com.leafplain.demo.databinding.datamodel.info.ListItemInfo;
import com.leafplain.demo.databinding.presenter.requestsample.PhotoListPresenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;
/**
 * Created by kennethyeh on 2017/5/25.
 */

public class LoadRedControllerRetrofit implements ParsingControllable<PhotoListPresenter.ResponseListener> {

    private static String TAG = "LoadRedControllerRetro";

    private List<ListItemInfo> mList = new ArrayList<>();
    private PhotoListPresenter.ResponseListener mListener=null;

    private String host = "https://raw.githubusercontent.com/kennethya2/";


    public interface PhotoListService{
        @GET("github-resource/master/unsplash/image-list.json")
        Call<APIInfo.PhotoList> getList();
    }

    @Override
    public void setResponseListener(PhotoListPresenter.ResponseListener listener) {
        mListener = listener;
    }

    @Override
    public void startParsing() {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Log.d(TAG, "Interceptor res:"+chain.proceed(request).body().string());
                return chain.proceed(request);
            }
        }).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(host)
                //增加返回值为String的支持
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        PhotoListService photoListService = retrofit.create(PhotoListService.class);
        Call<APIInfo.PhotoList> call=photoListService.getList();
        call.enqueue(new Callback<APIInfo.PhotoList>() {
            @Override
            public void onResponse(Call<APIInfo.PhotoList> call, Response<APIInfo.PhotoList> response) {
                // in main thread
                Log.d(TAG, "onResponse");
                APIInfo.PhotoList mPhotoList = response.body();
                converViewType(mPhotoList);
            }
            @Override
            public void onFailure(Call<APIInfo.PhotoList> call, Throwable t) {
                Log.d(TAG, "onFailure");
            }
        });
    }

    private void converViewType(APIInfo.PhotoList mPhotoList){
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

        if(mListener!=null){
            mListener.onResultList(mList);
        }
    }

    @Override
    public void cancel() {

    }
}
