package com.leafplain.demo.databinding.datamodel.view;


import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.leafplain.demo.databinding.BR;

/**
 * Created by kennethyeh on 2017/4/28.
 */

public class ObservableProfile extends BaseObservable {

    private String firstName;
    private String lastName;

    @Bindable
    public String getFirstName() {
        return this.firstName;
    }
    @Bindable
    public String getLastName() {
        return this.lastName;
    }

    public ObservableProfile setFirstName(String firstName) {
        this.firstName = firstName;
        notifyPropertyChanged(BR.firstName);

        return this;
    }
    public ObservableProfile setLastName(String lastName) {
        this.lastName = lastName;
        notifyPropertyChanged(BR.lastName);

        return this;
    }
}
