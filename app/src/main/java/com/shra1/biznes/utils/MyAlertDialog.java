package com.shra1.biznes.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class MyAlertDialog {
    private static AlertDialog.Builder builder;

    public static void show(Context mCtx, String title, String message) {
        prepare(mCtx, title, message);
        builder.show();
    }

    private static void prepare(Context mCtx, String title, String message) {
        builder = new AlertDialog.Builder(mCtx);
        builder.setTitle(title);
        builder.setMessage(message);
    }

    public static void show(Context mCtx, String title, String message, String positiveButtonText, DialogInterface.OnClickListener positiveButtonClickListener) {
        prepare(mCtx, title, message);
        builder.setPositiveButton(positiveButtonText, positiveButtonClickListener);
        builder.show();
    }

    public static void showErrorDialog(Context mCtx, Exception e, String positiveButtonText, DialogInterface.OnClickListener positiveButtonClickListener) {
        prepare(mCtx, "Error", e.getMessage());
        builder.setPositiveButton(positiveButtonText, positiveButtonClickListener);
        builder.show();
    }

    public static void showNoInternetErrorDialog(Context mCtx) {
        prepare(mCtx, "Error", "No internet");
        builder.show();
    }
}
