package com.leafplain.demo.databinding.activities.ex2_recycler_sample;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.leafplain.demo.databinding.R;
import com.leafplain.demo.databinding.adapterholder.FactoryListBinder;
import com.leafplain.demo.databinding.adapterholder.FactoryListHolder;
import com.leafplain.demo.databinding.base.basecontract.LoadingContract;
import com.leafplain.demo.databinding.databinding.ActivityRecyclerBinding;
import com.leafplain.demo.databinding.datamodel.info.ListItemInfo;
import com.leafplain.demo.databinding.datamodel.view.RecyclerBindingViewModel;
import com.leafplain.demo.databinding.presenter.recyclersmaple.RecyclerSamplePresenter;

import java.util.List;

public class RecyclerBindingActivity
        extends AppCompatActivity
        implements LoadingContract.View<List<ListItemInfo>, String>{

    private ActivityRecyclerBinding mRecyclerBinding;
    private RecyclerSamplePresenter mRecyclerSamplePresenter;
    private RecyclerBindingViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_recycler);
        mRecyclerBinding = DataBindingUtil.setContentView(this, R.layout.activity_recycler);

        mViewModel = new RecyclerBindingViewModel();
        mRecyclerBinding.setRecyclerDemoViewModel(mViewModel);
//
//
        mRecyclerSamplePresenter = new RecyclerSamplePresenter(RecyclerBindingActivity.this, mRecyclerBinding);
        mRecyclerBinding.setPresenter(mRecyclerSamplePresenter);

        mRecyclerBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onFinished(List<ListItemInfo> result) {
        mRecyclerBinding.recyclerView.setAdapter(new RecyclerViewAdapter(result));
    }

    @Override
    public void onFail(String fail) {

    }


    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<ListItemInfo> result;
        private FactoryListHolder listHolderFactory = new FactoryListHolder();
        private FactoryListBinder listBinderFactory = new FactoryListBinder();

        public RecyclerViewAdapter(List<ListItemInfo> result){
            this.result=result;
        }

        @Override
        public int getItemCount() {
            return result.size();
        }

        @Override
        public int getItemViewType(int position) {
            return result.get(position).type;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return listHolderFactory.getHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
            listBinderFactory.bindHolder(viewHolder, result.get(position));
//            mBVNewsContent.setViewHolder(viewHolder, position);
        }


    }
}
