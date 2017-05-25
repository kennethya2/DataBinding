package com.leafplain.demo.databinding.datamodel.info;

/**
 * Created by kennethyeh on 2017/5/4.
 */

public class ListItemInfo {

    public interface ListType{
        int TITLE       = 1;
        int PHOTO       = 2;
        int PHOTO_PIC   = 3;
    }

    public int type;
    public Object data;

}
