package com.leafplain.demo.databinding.presenter.profile;

import com.leafplain.demo.databinding.ProfileBinding;
import com.leafplain.demo.databinding.datamodel.view.ObservableProfile;

/**
 * Created by kennethyeh on 2017/4/28.
 */

public class ProfilePresenter {

    private ProfileBinding binding;

    public ProfilePresenter(ProfileBinding binding){
        this.binding = binding;
    }

    String editFirst    = "";
    String editLast     = "";
    public void editProfile(){
        ObservableProfile newProfile = new ObservableProfile();
        editFirst = binding.firstNameET.getText().toString();
        editLast  = binding.lastNameET.getText().toString();
        newProfile.setFirstName(editFirst);
        newProfile.setLastName(editLast);
        binding.setNewProfile(newProfile);
        binding.getProfile().setFirstName("").setLastName("");
    }

}
