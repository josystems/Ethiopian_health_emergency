package com.hackaton.ethiopianhealthemergency.qrcode;

import android.content.Context;
import android.util.Log;

import com.hackaton.ethiopianhealthemergency.database.Database;
import com.hackaton.ethiopianhealthemergency.model.Emergency;
import com.hackaton.ethiopianhealthemergency.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class JsonUtil {
    private Context ctx;
    private Database db;

    JsonUtil(Context context) {
        ctx = context;
        db = new Database(context, 1);
    }

    public JSONObject exportUserInfo() {
        JSONObject json = new JSONObject();
        JSONObject basicInfo = new JSONObject();
        JSONObject emergencyObject = new JSONObject();
        JSONArray emergencyArray = new JSONArray();
        User userInfo = db.getUserInfo();
        List<Emergency> emergencies = db.getEmergencyUser();
        try {
            json.put("fullName", userInfo.getFullName());
            json.put("phoneNumber", userInfo.getPhoneNumber());
            json.put("sex", userInfo.getSex());
            json.put("address", userInfo.getAddress());
            json.put("weight", userInfo.getWeight());
            json.put("height", userInfo.getHeight());
            json.put("bloodType", userInfo.getBloodType());
            json.put("birthDate", userInfo.getBirthDate());
            json.put("allergy", userInfo.getAllergy());
            basicInfo.put("basic_info", json);
            //INFO user emergency number
            for (Emergency emergency : emergencies) {
                emergencyObject.put("name", emergency.getName());
                emergencyObject.put("phoneNumber", emergency.getNumber());
                emergencyObject.put("relation", emergency.getRelation());
                emergencyArray.put(emergencyObject);
            }
            basicInfo.put("emergency", emergencyArray);
        } catch (JSONException e) {
            Log.e("JSON EXPORT ", e.toString());
        }
        return basicInfo;
    }

    public User readUserInfoFromJson(String jsonData) {
        User user = new User();

        try {
            JSONObject object = new JSONObject(jsonData);
            if (object.has("basic_info")) {
                JSONObject basicInfo = object.getJSONObject("basic_info");
                user.setFullName(basicInfo.getString("fullName"));
                user.setPhoneNumber(basicInfo.getString("phoneNumber"));
                user.setSex(basicInfo.getString("sex"));
                user.setAddress(basicInfo.getString("address"));
                user.setWeight(basicInfo.getString("weight"));
                user.setHeight(basicInfo.getString("height"));
                user.setBloodType(basicInfo.getString("bloodType"));
                user.setBirthDate(basicInfo.getString("birthDate"));
                user.setAllergy(basicInfo.getString("allergy"));
            }
            if (object.has("emergency")) {
                List<Emergency> emergencyList = new LinkedList<>();
                Emergency emergency;
                JSONArray emergencies = object.getJSONArray("emergency");
                for (int i = 0; i < emergencies.length(); i++) {
                    JSONObject obj = emergencies.getJSONObject(i);
                    emergency = new Emergency();
                    emergency.setName(obj.getString("name"));
                    emergency.setNumber(obj.getString("phoneNumber"));
                    emergency.setRelation(obj.getString("relation"));
                    emergencyList.add(emergency);
                }
                user.setEmergencies(emergencyList);
            }

        } catch (Exception e) {
            user = null;
            Log.e("READ JSON ", e.toString());
        }
        return user;
    }
}
