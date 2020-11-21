package com.hackaton.ethiopianhealthemergency.wizard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class WizardMainViewModel {
    private static MutableLiveData<Integer> setpager;
    public static void setPagerPos(int pos){
        if (setpager==null)
            setpager = new MutableLiveData<>();
        setpager.setValue(pos);
    }
    public static LiveData<Integer> getPagerPos(){
        if (setpager==null){
            setpager = new MutableLiveData<>();
            setpager.setValue(0);
        }
        return setpager;
    }
}
