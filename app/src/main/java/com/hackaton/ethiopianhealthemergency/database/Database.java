package com.hackaton.ethiopianhealthemergency.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.hackaton.ethiopianhealthemergency.model.Alerts;
import com.hackaton.ethiopianhealthemergency.model.Emergency;
import com.hackaton.ethiopianhealthemergency.model.HealthCenter;
import com.hackaton.ethiopianhealthemergency.model.User;
import com.hackaton.ethiopianhealthemergency.utils.Constant;
import com.hackaton.ethiopianhealthemergency.utils.Utils;

import java.util.LinkedList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    private static String TABLE_USER = "user";
    private static String TABLE_EMERGENCY_USER = "emergency";
    private static String TABLE_ALERT = "alert";
    private static String TABLE_HEALTH_CENTER = "health_center";

    public Database(@Nullable Context context, int version) {
        super(context, Constant.DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_USER + "(id TEXT Unique, fullName TEXT ,phoneNumber TEXT,sex TEXT,address TEXT,weight TEXT,height TEXT,bloodType TEXT,birthDate TEXT,allergy TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_EMERGENCY_USER + "(name TEXT ,phoneNumber TEXT unique,relation TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_ALERT + "(cause TEXT ,location TEXT,timeStamp INTEGER)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_HEALTH_CENTER + "(name TEXT Unique ,region TEXT,zone TEXT,woreda,city TEXT,longitude TEXT,latitude,phoneNumbers TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public void saveUserInfo(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", 1);
        values.put("fullName", user.getFullName());
        values.put("phoneNumber", user.getPhoneNumber());
        values.put("sex", user.getSex());
        values.put("address", user.getAddress());
        values.put("weight", user.getWeight());
        values.put("height", user.getHeight());
        values.put("bloodType", user.getBloodType());
        values.put("birthDate", user.getBirthDate());
        values.put("allergy", user.getAllergy());
        if (getUserInfo() == null)
            db.insert(TABLE_USER, null, values);
        else
            db.update(TABLE_USER, values, "id=?", new String[]{"1"});
    }

    public User getUserInfo() {
        SQLiteDatabase db = getReadableDatabase();
        User user = null;
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER, null);
        if (cursor.moveToFirst()) {
            user = new User();
            user.setFullName(cursor.getString(1));
            user.setPhoneNumber(cursor.getString(2));
            user.setSex(cursor.getString(3));
            user.setAddress(cursor.getString(4));
            user.setWeight(cursor.getString(5));
            user.setHeight(cursor.getString(6));
            user.setBloodType(cursor.getString(7));
            user.setBirthDate(cursor.getString(8));
            user.setAllergy(cursor.getString(1));
        }
        return user;
    }

    public List<Emergency> getEmergencyUser() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_EMERGENCY_USER, null);
        List<Emergency> emergencies = new LinkedList<>();
        Emergency emergency;
        if (cursor != null)
            if (cursor.moveToFirst()) {
                do {
                    emergency = new Emergency();
                    emergency.setName(cursor.getString(0));
                    emergency.setNumber(cursor.getString(1));
                    emergency.setRelation(cursor.getString(2));
                    emergencies.add(emergency);
                } while (cursor.moveToNext());
            }
        return emergencies;
    }

    public void addEmergencyUser(Emergency emergency) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        try {
            contentValues.put("name", emergency.getName());
            contentValues.put("phoneNumber", emergency.getNumber());
            contentValues.put("relation", emergency.getRelation());
            db.insert(TABLE_EMERGENCY_USER, null, contentValues);
        } catch (Exception e) {
            Log.e("add Emergency ", e.toString());
        }
    }

    public void addAlert(Alerts alerts) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        try {
            contentValues.put("cause", alerts.getCause());
            contentValues.put("timeStamp", alerts.getTimeStamp());
            contentValues.put("location", alerts.getLocation());
            db.insert(TABLE_ALERT, null, contentValues);
        } catch (Exception e) {
            Log.e("add Alert ", e.toString());
        }
    }

    public List<Alerts> getAlerts() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ALERT + "ORDERBY timestamp", null);
        List<Alerts> alerts = new LinkedList<>();
        Alerts alert;
        if (cursor != null)
            if (cursor.moveToFirst()) {
                do {
                    alert = new Alerts();
                    alert.setCause(cursor.getString(0));
                    alert.setLocation(cursor.getString(1));
                    alert.setTimeStamp(cursor.getLong(2));
                    alerts.add(alert);
                } while (cursor.moveToNext());
            }
        return alerts;
    }

    public void saveHealthCenter(HealthCenter healthCenter) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", healthCenter.getName());
        values.put("type", healthCenter.getType());
        values.put("region", healthCenter.getRegion());
        values.put("zone", healthCenter.getZone());
        values.put("woreda", healthCenter.getWoreda());
        values.put("city", healthCenter.getCity());
        values.put("longitude", healthCenter.getLongitude());
        values.put("latitude", healthCenter.getLatitude());
        values.put("phoneNumbers", Utils.ListToString(healthCenter.getPhoneNumbers()));
        if (!isHealthExists(healthCenter.getName()))
            db.insert(TABLE_HEALTH_CENTER, null, values);
        else
            db.update(TABLE_HEALTH_CENTER, values, "name=?", new String[]{healthCenter.getName()});
    }

    public boolean isHealthExists(String name) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name from " + TABLE_HEALTH_CENTER + " WHERE name=? ", new String[]{name});
        if (cursor.moveToFirst())
            return true;
        return false;
    }

    public List<HealthCenter> getHealthCenter(String filter, String value) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor;
        if (filter == null || value == null)
            cursor = db.rawQuery("SELECT * FROM " + TABLE_HEALTH_CENTER, null);
        else
            cursor = db.rawQuery("SELECT * FROM " + TABLE_HEALTH_CENTER+" WHERE "+filter+"=? ", new String[]{value});
        List<HealthCenter> healthCenters = new LinkedList<>();
        HealthCenter healthCenter;
        if (cursor != null)
            if (cursor.moveToFirst()) {
                do {
                    healthCenter = new HealthCenter();
                    healthCenter.setName(cursor.getString(0));
                    healthCenter.setType(cursor.getString(1));
                    healthCenter.setRegion(cursor.getString(2));
                    healthCenter.setZone(cursor.getString(3));
                    healthCenter.setWoreda(cursor.getString(4));
                    healthCenter.setCity(cursor.getString(5));
                    healthCenter.setLongitude(cursor.getString(6));
                    healthCenter.setLatitude(cursor.getString(7));
                    healthCenter.setPhoneNumbers(cursor.getString(8));
                    healthCenters.add(healthCenter);
                } while (cursor.moveToNext());
            }
        return healthCenters;
    }

}

