package com.hackaton.ethiopianhealthemergency.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.prefs.Preferences;

public class SharedPref {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    public SharedPref(Context c){
        preferences = c.getSharedPreferences(Constant.SHARED_PREFNAME,Context.MODE_PRIVATE);
        editor = preferences.edit();
    }
    public int getInt(String key,int def){
        return preferences.getInt(key,def);
    }
    public void setInt(String key,int value){
        editor.putInt(key,value);
        editor.apply();
    }
    public String getString(String key,String def){
        return preferences.getString(key,def);
    }
    public void setString(String key,String value){
        editor.putString(key,value);
        editor.apply();
    }
    public float getFloat(String key,float def){
        return preferences.getFloat(key,def);
    }
    public void setFloat(String key,float value){
        editor.putFloat(key,value);
        editor.apply();
    }
    public boolean getBool(String key,boolean def){
        return preferences.getBoolean(key,def);
    }
    public void setBool(String key,boolean value){
        editor.putBoolean(key,value);
        editor.apply();
    }
}
