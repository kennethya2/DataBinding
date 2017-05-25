package com.leafplain.demo.databinding.datamodel.info;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kennethyeh on 2017/5/24.
 */

public class APIInfo {

    public static class PhotoList implements Serializable {
        public String summary="";
        public List<PhotoInfo> images = new ArrayList<>();
    }

    public static class PhotoInfo implements Serializable {
        public String title="";
        public String filePath="";
        public String desc="";
    }

}
