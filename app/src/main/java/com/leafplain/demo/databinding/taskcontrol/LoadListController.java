package com.leafplain.demo.databinding.taskcontrol;

import android.os.Handler;

import com.leafplain.demo.databinding.base.basecontrol.ParsingControllable;
import com.leafplain.demo.databinding.datamodel.info.ListItemInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kennethyeh on 2017/5/4.
 */

public class LoadListController implements ParsingControllable<LoadListController.ResponseListener> {

    private List<ListItemInfo> mList = new ArrayList<>();
    private ResponseListener mListener=null;

    public interface ResponseListener{
        void onResultList(List<ListItemInfo> listResult);
    }

    @Override
    public void setResponseListener(ResponseListener listener) {
        mListener = listener;
    }

    @Override
    public void startParsing() {
        mHandler.postDelayed(mRunnable, 500);
    }
    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            for(int i =0;i<20;i++){
                ListItemInfo mListItemInfo = new ListItemInfo();
                if((i%5)==0){
                    mListItemInfo.type = ListItemInfo.ListType.TITLE;
                    mListItemInfo.data = "Title: "+(i);
                }else{
                    mListItemInfo.type = ListItemInfo.ListType.PHOTO;
                    String photoURL = "";
                    if((i%5)==1){
                        photoURL = "https://raw.githubusercontent.com/kennethya2/GoogleVR/master/vr-resource/image/island.jpg";
                    }else if((i%5)==2) {
                        photoURL = "https://raw.githubusercontent.com/kennethya2/GoogleVR/master/vr-resource/image/river.jpeg";
                    }else if((i%5)==3){
                        photoURL = "https://raw.githubusercontent.com/kennethya2/GoogleVR/master/vr-resource/image/room.jpg";
                    }else if((i%5)==4){
                        photoURL = "https://raw.githubusercontent.com/kennethya2/GoogleVR/master/vr-resource/image/square.jpg";
                    }
                    mListItemInfo.data = photoURL;

                    ListItemInfo picInfo = new ListItemInfo();
                    picInfo.type=ListItemInfo.ListType.PHOTO_PIC;
                    picInfo.data=photoURL;
                    mList.add(picInfo);

                }
                mList.add(mListItemInfo);
            }
            if(mListener!=null){
                mListener.onResultList(mList);
            }
        }
    };

    @Override
    public void cancel() {

    }

}
