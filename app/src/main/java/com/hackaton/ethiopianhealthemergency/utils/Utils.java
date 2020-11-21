package com.hackaton.ethiopianhealthemergency.utils;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Build;

import java.io.IOException;
import java.util.Locale;

public class Utils {
    public static void loadLanguage(Context c,String lang){
        SharedPref sharedPref = new SharedPref(c);
        if (lang == null)
            lang = sharedPref.getString("language", Locale.getDefault().getLanguage());
        else
            new SharedPref(c).setString("language", lang);
        Configuration configuration = c.getResources().getConfiguration();
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        configuration.locale = locale;
        c.getResources().updateConfiguration(configuration, c.getResources().getDisplayMetrics());
    }
    public static void setLockScreenWallpaper(Context context, Bitmap bitmap){
        WallpaperManager manager = (WallpaperManager)context.getSystemService(Context.WALLPAPER_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                manager.setBitmap(bitmap,null,true,WallpaperManager.FLAG_LOCK);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
