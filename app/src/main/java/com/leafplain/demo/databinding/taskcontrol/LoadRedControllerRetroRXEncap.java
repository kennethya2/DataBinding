package com.leafplain.demo.databinding.taskcontrol;

import android.util.Log;

import com.leafplain.demo.databinding.base.basecontrol.ParsingControllable;
import com.leafplain.demo.databinding.datamodel.info.APIInfo;
import com.leafplain.demo.databinding.datamodel.info.ListItemInfo;
import com.leafplain.demo.databinding.presenter.requestsample.PhotoListPresenter;
import com.leafplain.demo.databinding.taskcontrol.rx_base.ResResultFilter;
import com.leafplain.demo.databinding.taskcontrol.rx_base.BaseResInfo;
import com.leafplain.demo.databinding.taskcontrol.rx_base.HttpResSubscriber;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by kennethyeh on 2017/11/16.
 */

public class LoadRedControllerRetroRXEncap implements ParsingControllable<PhotoListPresenter.ResponseListener> {

    private static String TAG = "LoadRetroRXEncap";

    private List<ListItemInfo> mList = new ArrayList<>();
    private PhotoListPresenter.ResponseListener mListener=null;
    private String host = "https://raw.githubusercontent.com/kennethya2/";

    public interface PhotoListService{
//        @GET("github-resource/master/unsplash/image-list-api.json")
        @GET("github-resource/master/unsplash/image-list-err-api.json")
        Observable<BaseResInfo<List<APIInfo.PhotoInfo>>> getList();
    }

    @Override
    public void setResponseListener(PhotoListPresenter.ResponseListener listener) {
        mListener = listener;
    }

    @Override
    public void startParsing() {
        type2();
    }

    private void type1(){
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        okhttp3.Response response = chain.proceed(request);
//                        Log.d(TAG, "Interceptor res:"+response.body().string());
                        return response;
                    }
                })
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(host)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//新的配置
                .client(client)
                .build();

        PhotoListService photoListService = retrofit.create(PhotoListService.class);
        photoListService.getList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mSubscriber1);
    }

        private Subscriber mSubscriber1 = new Subscriber<BaseResInfo<List<APIInfo.PhotoInfo>>>() {
        @Override
        public void onCompleted() {
            Log.d(TAG, "onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            Log.d(TAG, "onError");
            Log.d(TAG, "Throwable:"+e.toString());

            if (e instanceof HttpException) {
                ResponseBody responseBody = ((HttpException)e).response().errorBody();
                // onUnknownError(getErrorMessage(responseBody));
                Log.d(TAG, "responseBody:"+responseBody.toString());
                Log.d(TAG, "HttpException onUnknownError");
            } else if (e instanceof SocketTimeoutException) {
                Log.d(TAG, "onTimeout");
                // onTimeout();
            } else if (e instanceof IOException) {
                Log.d(TAG, "onNetworkError");
                // onNetworkError();
            } else {
                // onUnknownError(e.getMessage());
                Log.d(TAG, "onUnknownError");
            }
            Log.d(TAG, "----");
        }

        @Override
        public void onNext(BaseResInfo<List<APIInfo.PhotoInfo>> resInfo) {
            Log.d(TAG, "onNext");
            Log.d(TAG, "status:"+resInfo.status);
            Log.d(TAG, "errcode:"+resInfo.errcode);
            Log.d(TAG, "message:"+resInfo.message);

            converViewType(resInfo);
        }
    };

    private void converViewType(BaseResInfo<List<APIInfo.PhotoInfo>> resInfo){
        List<APIInfo.PhotoInfo> images = resInfo.data;
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


    public void type2() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        okhttp3.Response response = chain.proceed(request);
//                        Log.d(TAG, "Interceptor res:"+response.body().string());
                        return response;
                    }
                })
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(host)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//新的配置
                .client(client)
                .build();

        ResResultFilter filter = new ResResultFilter(filterCallback);
        HttpResSubscriber subscriber = new HttpResSubscriber(resultCallback);
        PhotoListService photoListService = retrofit.create(PhotoListService.class);
        photoListService.getList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(filter)
                .subscribe(subscriber);
    }

    private ResResultFilter.FilterCallback filterCallback = new ResResultFilter.FilterCallback<List<APIInfo.PhotoInfo>>() {
        @Override
        public void onResult(BaseResInfo errInfo) {
            Log.d(TAG,"CALL message:"+errInfo.message);
            Log.d(TAG,"CALL errcode:"+errInfo.errcode);
            Log.d(TAG,"CALL status:"+errInfo.status);
        }
    };
    private HttpResSubscriber.ResCallback resultCallback = new HttpResSubscriber.ResCallback<List<APIInfo.PhotoInfo>>(){

        @Override
        public void onResult(List<APIInfo.PhotoInfo> list) {
            if(list==null || list.size()<1){
                return;
            }
            Log.d(TAG,"resultCallback");
            converViewType2(list);
        }
    };

    private void converViewType2(List<APIInfo.PhotoInfo> list){
        for(int i =0 ; i<list.size() ; i++){
            APIInfo.PhotoInfo photo = list.get(i);

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
