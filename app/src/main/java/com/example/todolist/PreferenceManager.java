package com.example.todolist;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class PreferenceManager {
    String prefName = "toDoList";
    String prefStr = " ";
    boolean prefBoolean = false;
    int prefInt = -1;
    long prefLong = -1L;
    float prefFloat = -1F;

    SharedPreferences getPreference(Context context) {
        return context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
    }

    public void setString(Context context, String key, String value) {
        SharedPreferences shared = getPreference(context);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void settingShared(Context context, String key, String value) {
        SharedPreferences shared = getPreference(context);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(key, value);
        // editor.apply() == 쉐어드에 저장을 해주는데 반환 값이 없다.
        editor.apply();
        Log.i("휴지통 settingShared","");
    }

    public String getString(Context context, String key) {
        SharedPreferences shared = getPreference(context);
        String value = shared.getString(key, prefStr);
        return value;
    }

    public void removeKey(Context context, String key) {
        SharedPreferences shared = getPreference(context);
        SharedPreferences.Editor editor = shared.edit();
        editor.remove(key);
        // editor.commit() == 쉐어드에 저장을 성공하면 boolean 타입인 true 값을 반환한다.
        editor.commit();
    }

    public void clear(Context context) {
        SharedPreferences shared = getPreference(context);
        SharedPreferences.Editor editor = shared.edit();
        editor.clear();
        editor.commit();
    }

}
