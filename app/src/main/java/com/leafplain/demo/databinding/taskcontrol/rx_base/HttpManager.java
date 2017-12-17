package com.leafplain.demo.databinding.taskcontrol.rx_base;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by kennethyeh on 2017/12/17.
 */

public class HttpManager {

    private static String TAG = "LoadRetroRXEncap";
    private String host = "https://raw.githubusercontent.com/kennethya2/";


    private HttpManager() {}

    private static HttpManager instance = null;
    public static HttpManager getInstance() {
        if (instance == null) {
            // Thread Safe. Might be costly operation in some case
            synchronized (HttpManager.class) {
                if (instance == null) {
                    instance = new HttpManager();
                }
            }
        }
        return instance;
    }

    public void startRequest(HttpParam param){

        Observable observable = param.observable;
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(param.filter)
                .subscribe(param.subscriber);

    }

    public Retrofit getRetorfit(){
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

        return retrofit;
    }
}
