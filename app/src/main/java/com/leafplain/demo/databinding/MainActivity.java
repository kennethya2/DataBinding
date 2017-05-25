package com.leafplain.demo.databinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.leafplain.demo.databinding.databinding.ActivityMainBinding;
import com.leafplain.demo.databinding.presenter.MainPresenter;

public class MainActivity extends AppCompatActivity {


    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        presenter = new MainPresenter(this);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setMainPresenter(presenter);
    }

    public static class MainOpenType{
        public static final int Personal_Sample = 1;
        public static final int Recycler_Sample = 2;
        public static final int REQ_OKHTTP3     = 3;
        public static final int REQ_RETROFIT2   = 4;
    }
}
