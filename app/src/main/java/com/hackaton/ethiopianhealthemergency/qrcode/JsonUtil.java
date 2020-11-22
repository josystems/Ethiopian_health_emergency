package com.josystems.mycollageapp.qrcode;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.preference.PreferenceManager;

import com.crashlytics.android.Crashlytics;
import com.josystems.mycollageapp.R;
import com.josystems.mycollageapp.portal.Util;
import com.josystems.mycollageapp.portal.database.StudentDB;
import com.josystems.mycollageapp.portal.model.Course;
import com.josystems.mycollageapp.portal.model.Lesson;
import com.josystems.mycollageapp.ui.Setting.LessonTimeHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JsonUtil {
    private Context ctx;
    private StudentDB db;
    JsonUtil(Context context) {
        ctx = context;
        db = new StudentDB(context);
    }

    public JSONObject exportSettingToJson() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);
        JSONObject json = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        try {
            json.put("ll", pref.getInt("lesson_duration", 60));
            json.put("tl", pref.getInt("total_lesson", 15));
            json.put("lb", pref.getInt("lesson_break", 0));
            JSONArray lst = new JSONArray();
            if (pref.getInt("lst_1", 0) != 0) {
                for (int i = 1; i < 16; i++) {
                    lst.put(pref.getInt("lst_" + i, 60 + 60 * i));
                }
            }
            json.putOpt("lst", lst);
            JSONArray edArray = new JSONArray();
            String[] edl = LessonTimeHelper.getEnabledDay(ctx);
            if (!Arrays.equals(edl, ctx.getResources().getStringArray(R.array.lesson_default_value)))
                for (String s : edl) {
                    edArray.put(Integer.parseInt(s));
                }
            json.put("ed", edArray);
            jsonObject.put("setting",json);
        } catch (JSONException e) {
            Crashlytics.logException(e);
        }
        return jsonObject;
    }

    public boolean importSettingFromJson(String json) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = pref.edit();
        try {
            JSONObject object = new JSONObject(json);
            if (!object.has("setting"))return false;
            JSONObject jsonObject = object.getJSONObject("setting");
            if (jsonObject.has("ll"))
                editor.putInt("lesson_duration", jsonObject.getInt("ll"));
            if (jsonObject.has("tl"))
                editor.putInt("total_lesson", jsonObject.getInt("tl"));
            if (jsonObject.has("lb"))
                editor.putInt("lesson_break", jsonObject.getInt("lb"));
            JSONArray lstArray = jsonObject.getJSONArray("lst");
            for (int i = 0; i < lstArray.length(); i++) {
                editor.putInt("lst_" + (i + 1), lstArray.getInt(i));
            }
            JSONArray dayArray = jsonObject.getJSONArray("ed");
            Set<String> ed = new HashSet<>();
            for (int i = 0; i < dayArray.length(); i++)
                ed.add(Util.intToStr(dayArray.getInt(i)));
            if (ed.size() > 0)
                editor.putStringSet("enabled_day", ed);
            editor.apply();
            Toast.makeText(ctx, "Imported", Toast.LENGTH_SHORT).show();
            return true;
        } catch (JSONException e) {
            Toast.makeText(ctx, "error when import " + e.toString(), Toast.LENGTH_SHORT).show();
            Crashlytics.logException(e);
            return false;
        }
    }

    public  JSONObject exportCourseToJson() {
        JSONObject jsonObject = new JSONObject();
        JSONArray courseArray = new JSONArray();
        try {
            JSONObject object;
            for (Course course : db.getAllCourses()) {
                object = new JSONObject();
                object.put("id", course.getID());
                object.put("n", course.getNAME());
                object.put("c", course.getCOLOR());
                object.put("e", course.getECTS());
                courseArray.put(object);
            }
            jsonObject.put("course", courseArray);
        } catch (Exception e) {
            Crashlytics.logException(e);
        }
        return jsonObject;
    }

    public  boolean importCourseFromJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray;
            if (!jsonObject.has("course")) return false;
            jsonArray = jsonObject.getJSONArray("course");
            db.deleteCourse(-1);
            Course course;
            JSONObject object;
            for (int i = 0; i < jsonArray.length(); i++) {
                course = new Course();
                object = jsonArray.getJSONObject(i);
                course.setID(object.getInt("id"));
                course.setNAME(object.getString("n"));
                course.setCOLOR(object.getInt("c"));
                course.setECTS(object.getInt("e"));
                db.createCourse(course);
            }
            Toast.makeText(ctx, "imported", Toast.LENGTH_SHORT).show();
            return true;
        } catch (JSONException e) {
            Crashlytics.logException(e);
            return false;
        }
    }

    public  boolean importLessonFromJson(String json) {
        try {
            JSONObject object = new JSONObject(json);
            if (!object.has("lesson"))return false;

                db.deleteAllLesson(-1);
                JSONArray jsonArray = object.getJSONArray("lesson");
                JSONObject obj;
                Lesson lesson = new Lesson();
                for (int i = 0; i < jsonArray.length(); i++) {
                    obj = jsonArray.getJSONObject(i);
                    lesson.setID(obj.getInt("l"));
                    lesson.setCOURSE_ID(obj.getInt("c"));
                    db.updateLesson(lesson);
                }
                Toast.makeText(ctx, "Imported", Toast.LENGTH_SHORT).show();
                return true;
        } catch (JSONException e) {
            Crashlytics.logException(e);
            return false;
        }
    }

    public JSONObject exportLessonToJson() {
        List<Lesson> lessonList = db.getLessonOfDay(-1);
        JSONObject jsonObject = new JSONObject();
        JSONArray lessonArray = new JSONArray();
        JSONObject object;
        try {
            for (Lesson lesson : lessonList) {
                object = new JSONObject();
                object.put("c", lesson.getCOURSE().getID());
                object.put("l", lesson.getID());
                lessonArray.put(object);
            }
            jsonObject.put("lesson", lessonArray);
        } catch (JSONException e) {
            Crashlytics.logException(e);
        }
        return jsonObject;
    }
}
