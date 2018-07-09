package com.shra1.biznes.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceStorage {
    private static SharedPreferenceStorage INSTANCE = null;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private SharedPreferenceStorage(Context c) {
        sharedPreferences = c.getSharedPreferences(
                c.getPackageName() + "." + this.getClass().getName(),
                Context.MODE_PRIVATE
        );
        editor = sharedPreferences.edit();
    }

    public static SharedPreferenceStorage getInstance(Context c) {
        if (INSTANCE == null) {
            INSTANCE = new SharedPreferenceStorage(c);
        }
        return INSTANCE;
    }

    public void setJSESSIONID(String JSESSIONID){
        editor.putString("JSESSIONID", JSESSIONID);
        editor.commit();
    }

    public String getJSESSIONID(){
        return sharedPreferences.getString("JSESSIONID", "JSESSIONID");
    }
}
