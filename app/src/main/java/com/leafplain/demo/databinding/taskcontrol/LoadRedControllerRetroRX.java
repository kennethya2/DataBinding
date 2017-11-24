package com.leafplain.demo.databinding.taskcontrol;

import android.util.Log;

import com.leafplain.demo.databinding.base.basecontrol.ParsingControllable;
import com.leafplain.demo.databinding.datamodel.info.APIInfo;
import com.leafplain.demo.databinding.datamodel.info.ListItemInfo;
import com.leafplain.demo.databinding.presenter.requestsample.PhotoListPresenter;

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
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by kennethyeh on 2017/11/16.
 */

public class LoadRedControllerRetroRX implements ParsingControllable<PhotoListPresenter.ResponseListener> {

    private static String TAG = "LoadRetroRX";

    private List<ListItemInfo> mList = new ArrayList<>();
    private PhotoListPresenter.ResponseListener mListener=null;
    private String host = "https://raw.githubusercontent.com/kennethya2/";

    public interface PhotoListService{
        @GET("github-resource/master/unsplash/image-list.json")
        Observable<APIInfo.PhotoList> getList();
    }

    @Override
    public void setResponseListener(PhotoListPresenter.ResponseListener listener) {
        mListener = listener;
    }

    @Override
    public void startParsing() {
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
        req1(photoListService);
//        req2(photoListService);
    }

    private void req1(PhotoListService photoListService){
        Log.i(TAG, "req1");
        photoListService.getList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<APIInfo.PhotoList>() {

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
                    public void onNext(APIInfo.PhotoList photoList) {
                        Log.d(TAG, "onNext");
//                        converViewType1(photoList);
                        converViewType2(photoList);
                    }
                });
    }

    private void converViewType1(APIInfo.PhotoList mPhotoList){
        Log.i(TAG, "converViewType1");
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

    private void converViewType2(APIInfo.PhotoList mPhotoList){
        Log.i(TAG, "converViewType2");
        List<APIInfo.PhotoInfo> images = mPhotoList.images;
        Observable.just(mPhotoList)
                .map(new Func1<APIInfo.PhotoList, List<ListItemInfo>>() {
                    @Override
                    public List<ListItemInfo> call(APIInfo.PhotoList photoList) {
                        return getConverViewType(photoList);
                    }
                })
                .subscribe(new Action1<List<ListItemInfo>>() {
                    @Override
                    public void call(List<ListItemInfo> list) {
                        if(mListener!=null){
                            mListener.onResultList(list);
                        }

                    }
                });

    }



    private void req2(PhotoListService photoListService){
        Log.i(TAG, "req2");
        photoListService.getList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<APIInfo.PhotoList, List<ListItemInfo>>() {
                    @Override
                    public List<ListItemInfo> call(APIInfo.PhotoList photoList) {
                        return getConverViewType(photoList);
                    }
                })
                .subscribe(new Subscriber<List<ListItemInfo>>() {
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
                    public void onNext(List<ListItemInfo> list) {
                        if(mListener!=null){
                            mListener.onResultList(list);
                        }
                    }
                });
    }

    private List<ListItemInfo> getConverViewType(APIInfo.PhotoList mPhotoList){
        List<ListItemInfo> list = new ArrayList<>();
        List<APIInfo.PhotoInfo> images = mPhotoList.images;
        for(int i =0 ; i<images.size() ; i++){
            APIInfo.PhotoInfo photo = images.get(i);

            ListItemInfo titleInfo = new ListItemInfo();
            titleInfo.type = ListItemInfo.ListType.TITLE;
            titleInfo.data = photo.title;
            list.add(titleInfo);

            ListItemInfo picInfo = new ListItemInfo();
            picInfo.type = ListItemInfo.ListType.PHOTO_PIC;
            picInfo.data = photo.filePath;
            list.add(picInfo);

            ListItemInfo descInfo = new ListItemInfo();
            descInfo.type=ListItemInfo.ListType.PHOTO;
            descInfo.data=photo.desc;
            list.add(descInfo);
        }
        return list;
    }

    @Override
    public void cancel() {

    }
}
