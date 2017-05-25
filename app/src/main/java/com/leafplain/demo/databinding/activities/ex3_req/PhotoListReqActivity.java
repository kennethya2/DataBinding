package com.leafplain.demo.databinding.activities.ex3_req;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.leafplain.demo.databinding.MainActivity;
import com.leafplain.demo.databinding.R;
import com.leafplain.demo.databinding.adapterholder.FactoryListBinder;
import com.leafplain.demo.databinding.adapterholder.FactoryListHolder;
import com.leafplain.demo.databinding.base.basecontract.LoadingContract;
import com.leafplain.demo.databinding.databinding.ActivityPhotoListReqBinding;
import com.leafplain.demo.databinding.datamodel.info.ListItemInfo;
import com.leafplain.demo.databinding.datamodel.view.RecyclerBindingViewModel;
import com.leafplain.demo.databinding.presenter.requestsample.PhotoListPresenter;

import java.util.List;

public class PhotoListReqActivity
        extends AppCompatActivity
        implements LoadingContract.View<List<ListItemInfo>, String>{

    private ActivityPhotoListReqBinding binding;
    private PhotoListPresenter mPhotoListPresenter;
    private RecyclerBindingViewModel mViewModel;

    public static final String PARAM_REQ_TYPE = "req_type";
    private int reqType = MainActivity.MainOpenType.REQ_OKHTTP3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new RecyclerBindingViewModel();

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            reqType = extras.getInt(PARAM_REQ_TYPE);
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_photo_list_req);
        binding.setRecyclerDemoViewModel(mViewModel);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPhotoListPresenter = new PhotoListPresenter(PhotoListReqActivity.this, mViewModel, reqType);
        mPhotoListPresenter.start();
    }

    @Override
    public void onFinished(List<ListItemInfo> result) {
        binding.recyclerView.setAdapter(new RecyclerViewAdapter(result));
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
        }
    }
}
