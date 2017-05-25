package com.leafplain.demo.databinding.presenter.recyclersmaple;

import android.databinding.ViewDataBinding;
import android.util.Log;

import com.leafplain.demo.databinding.base.basecontract.LoadingContract;
import com.leafplain.demo.databinding.databinding.ActivityRecyclerBinding;
import com.leafplain.demo.databinding.datamodel.info.ListItemInfo;
import com.leafplain.demo.databinding.taskcontrol.LoadListController;

import java.util.List;


/**
 * Created by kennethyeh on 2017/5/2.
 */

public class RecyclerSamplePresenter implements LoadingContract.Presenter{

    private String TAG = "RecyclerSamplePresenter";

    private LoadingContract.View view;
    private ActivityRecyclerBinding binding;
    private LoadListController mParsingListTask;

    public RecyclerSamplePresenter(LoadingContract.View view, ViewDataBinding binding){
        this.view       = view;
        this.binding    = (ActivityRecyclerBinding) binding;
    }

    @Override
    public void start() {
        binding.getRecyclerDemoViewModel().isLoaded.set(true);

        mParsingListTask = new LoadListController();
        mParsingListTask.setResponseListener(listener);
        mParsingListTask.startParsing();
    }

    private LoadListController.ResponseListener listener = new LoadListController.ResponseListener(){

        @Override
        public void onResultList(List<ListItemInfo> listResult) {
            binding.getRecyclerDemoViewModel().isLoaded.set(false);
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
        binding.getRecyclerDemoViewModel().isLoaded.set(false);
    }
}
