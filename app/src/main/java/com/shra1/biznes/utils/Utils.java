package com.shra1.biznes.utils;

import android.content.Context;
import android.widget.Toast;

public class Utils {
    public static final String TAG="ShraX";
    public static void showToast(Context mCtx, String text) {
        Toast.makeText(mCtx, text, Toast.LENGTH_SHORT).show();
    }
}
