package com.leafplain.demo.databinding.activities.ex1_personal_profile;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.leafplain.demo.databinding.ProfileBinding;
import com.leafplain.demo.databinding.R;
import com.leafplain.demo.databinding.presenter.profile.ProfilePresenter;
import com.leafplain.demo.databinding.datamodel.view.ObservableProfile;

public class PersonalProfileActivity extends AppCompatActivity {

    private ProfileBinding mDemoBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_personal_profile);
        mDemoBinding = DataBindingUtil.setContentView(this, R.layout.activity_personal_profile);
        ProfilePresenter presenter = new ProfilePresenter(mDemoBinding);
        mDemoBinding.setPresenter(presenter);

        ObservableProfile profile = new ObservableProfile();
        profile.setFirstName("");
        profile.setLastName("");
        mDemoBinding.setProfile(profile);
    }
}
