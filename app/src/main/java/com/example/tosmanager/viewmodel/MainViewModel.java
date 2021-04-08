package com.example.tosmanager.viewmodel;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tosmanager.R;
import com.example.tosmanager.ui.CalendarFragment;
import com.example.tosmanager.ui.ConfigurationFragment;
import com.example.tosmanager.ui.MyTosFragment;

public class MainViewModel extends ViewModel {
    // state
    private final MutableLiveData<Integer> itemID = new MutableLiveData<>(R.id.navigationMyTos);

    public MutableLiveData<Integer> getItemID() {
        return itemID;
    }

    public Fragment getFragment() {
        switch (itemID.getValue()) {
            case R.id.navigationMyTos:
                return new MyTosFragment();
            case R.id.navigationCalendar:
                return new CalendarFragment();
            case R.id.navigationConfiguration:
                return new ConfigurationFragment();
        }
        return null;
    }

    public Integer getTitleStringName() {
        switch (itemID.getValue()) {
            case R.id.navigationMyTos:
                return R.string.navigation_my_tos;
            case R.id.navigationCalendar:
                return R.string.navigation_calendar;
            case R.id.navigationConfiguration:
                return R.string.navigation_configuration;
        }
        return null;
    }
}
