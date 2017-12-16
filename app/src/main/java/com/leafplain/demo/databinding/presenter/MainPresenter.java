package com.leafplain.demo.databinding.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.leafplain.demo.databinding.MainActivity;
import com.leafplain.demo.databinding.activities.ex1_personal_profile.PersonalProfileActivity;
import com.leafplain.demo.databinding.activities.ex2_recycler_sample.RecyclerBindingActivity;
import com.leafplain.demo.databinding.activities.ex3_req.PhotoListReqActivity;
import com.leafplain.demo.databinding.base.basecontract.MainContract;

/**
 * Created by kennethyeh on 2017/4/28.
 */

public class MainPresenter implements MainContract.Presenter<Integer>{

    private static final String ACTION_PRE = "com.leafplain.demo.action.";

    private Context context;
    public MainPresenter(Context context){
        this.context = context;
    }
    @Override
    public void onTypeClick(Integer clickInfo) {
        Intent intent = new Intent();
        Bundle extras = new Bundle();
        switch (clickInfo){
            case MainActivity.MainOpenType.Personal_Sample:
                Toast.makeText(context, "Personal_Sample",Toast.LENGTH_SHORT).show();
                intent.setClass(context, PersonalProfileActivity.class);
//                intent.setAction(ACTION_PRE+"PersonalProfileActivity");
                break;
            case MainActivity.MainOpenType.Recycler_Sample:
                Toast.makeText(context, "Recycler_Sample",Toast.LENGTH_SHORT).show();
                intent.setClass(context, RecyclerBindingActivity.class);
//                intent.setAction(ACTION_PRE+"RecyclerBindingActivity");
                break;

            case MainActivity.MainOpenType.REQ_OKHTTP3:
                Toast.makeText(context, "REQ_OKHTTP",Toast.LENGTH_SHORT).show();
                extras.putInt(PhotoListReqActivity.PARAM_REQ_TYPE, MainActivity.MainOpenType.REQ_OKHTTP3);
                intent.putExtras(extras);
                intent.setClass(context, PhotoListReqActivity.class);

                break;
            case MainActivity.MainOpenType.REQ_RETROFIT2:
                Toast.makeText(context, "REQ_RETROFIT2",Toast.LENGTH_SHORT).show();
                extras.putInt(PhotoListReqActivity.PARAM_REQ_TYPE, MainActivity.MainOpenType.REQ_RETROFIT2);
                intent.putExtras(extras);
                intent.setClass(context, PhotoListReqActivity.class);
                break;
            case MainActivity.MainOpenType.REQ_RETROFIT2_RX:
                Toast.makeText(context, "REQ_RETROFIT2_RX",Toast.LENGTH_SHORT).show();
                extras.putInt(PhotoListReqActivity.PARAM_REQ_TYPE, MainActivity.MainOpenType.REQ_RETROFIT2_RX);
                intent.putExtras(extras);
                intent.setClass(context, PhotoListReqActivity.class);
                break;
            case MainActivity.MainOpenType.REQ_RETROFIT2_RX_Encap:
                Toast.makeText(context, "REQ_RETROFIT2_RX_Encap",Toast.LENGTH_SHORT).show();
                extras.putInt(PhotoListReqActivity.PARAM_REQ_TYPE, MainActivity.MainOpenType.REQ_RETROFIT2_RX_Encap);
                intent.putExtras(extras);
                intent.setClass(context, PhotoListReqActivity.class);
                break;
        }
        context.startActivity(intent);
    }
}
