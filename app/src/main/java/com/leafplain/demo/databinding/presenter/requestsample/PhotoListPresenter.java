package com.leafplain.demo.databinding.presenter.requestsample;

import android.util.Log;

import com.leafplain.demo.databinding.MainActivity;
import com.leafplain.demo.databinding.base.basecontract.LoadingContract;
import com.leafplain.demo.databinding.base.basecontrol.ParsingControllable;
import com.leafplain.demo.databinding.datamodel.info.ListItemInfo;
import com.leafplain.demo.databinding.datamodel.view.RecyclerBindingViewModel;
import com.leafplain.demo.databinding.taskcontrol.LoadRedControllerRetroRX;
import com.leafplain.demo.databinding.taskcontrol.LoadRedControllerRetrofit;
import com.leafplain.demo.databinding.taskcontrol.LoadReqControllerOKHttp;

import java.util.List;

/**
 * Created by kennethyeh on 2017/5/24.
 */

public class PhotoListPresenter implements LoadingContract.Presenter{

    private String TAG = "PhotoListPresenter";

    private LoadingContract.View view;
    private ParsingControllable requestController;
    private RecyclerBindingViewModel viewModel;
    private int reqType;

    public PhotoListPresenter(LoadingContract.View view, RecyclerBindingViewModel viewModel, int reqType){
        this.view       = view;
        this.viewModel  = viewModel;
        this.reqType = reqType;
    }

    @Override
    public void start() {
        viewModel.isLoaded.set(true);
        switch (reqType){
            case MainActivity.MainOpenType.REQ_OKHTTP3:
                requestController = new LoadReqControllerOKHttp();
                break;
            case MainActivity.MainOpenType.REQ_RETROFIT2:
                requestController = new LoadRedControllerRetrofit();
                break;
            case MainActivity.MainOpenType.REQ_RETROFIT2_RX:
                requestController = new LoadRedControllerRetroRX();
                break;
        }
        requestController.setResponseListener(listener);
        requestController.startParsing();
    }

    public interface ResponseListener{
        void onResultList(List<ListItemInfo> listResult);
    }
    private ResponseListener listener = new ResponseListener(){

        @Override
        public void onResultList(List<ListItemInfo> listResult) {
            viewModel.isLoaded.set(false);
            for(ListItemInfo item: listResult){
                Log.d(TAG,"type:"+item.type);
                Log.d(TAG,"data:"+item.data);
            }
            Log.d(TAG,"------");
            view.onFinished(listResult);
        }
    };

    @Override
    public void cancel() {
        viewModel.isLoaded.set(false);
    }
}
