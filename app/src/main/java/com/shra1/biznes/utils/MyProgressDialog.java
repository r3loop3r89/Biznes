package com.shra1.biznes.utils;

import android.app.ProgressDialog;
import android.content.Context;

public class MyProgressDialog {
    private static ProgressDialog pd;

    public static void show(Context mCtx, String title, boolean b) {
        prepare(mCtx, title, b);
        pd.show();
    }

    private static void prepare(Context mCtx, String title, boolean b) {
        pd = new ProgressDialog(mCtx);
        pd.setTitle(title);
        pd.setCancelable(b);
    }


    public static void dismiss() {
        if (pd.isShowing()) {
            pd.dismiss();
        }
    }

    public static void showPleaseWait(Context mCtx) {
        prepare(mCtx, "Please wait!", false);
        pd.show();
    }
}
