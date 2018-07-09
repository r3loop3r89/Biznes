package com.shra1.biznes.utils;

import android.app.ProgressDialog;
import android.content.Context;

public class MyProgressDialog {
    private static ProgressDialog pd;

    public static void show(Context mCtx, String title, boolean b) {
        pd = new ProgressDialog(mCtx);
        pd.setTitle(title);
        pd.setCancelable(b);
        pd.show();
    }


    public static void dismiss() {
        if (pd.isShowing()) {
            pd.dismiss();
        }
    }
}
