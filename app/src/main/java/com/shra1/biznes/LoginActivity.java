package com.shra1.biznes;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.shra1.biznes.activities.AdminActivity;
import com.shra1.biznes.interfaces.GenericResponseCallback;
import com.shra1.biznes.utils.MyAlertDialog;
import com.shra1.biznes.utils.MyProgressDialog;
import com.shra1.biznes.utils.Utils;
import com.shra1.biznes.utils.Validation;
import com.shra1.biznes.webservices.LoginTask;

import static com.shra1.biznes.webservices.LoginTask.RequestDto.AUP_APPLICATION;

public class LoginActivity extends AppCompatActivity {
    private Toolbar tbToolbar;
    private EditText etLAUsername;
    private EditText etLAPassword;
    private Button bLALogin;
    private Button bLACancle;
    private Context mCtx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mCtx = this;
        initViews();

        setSupportActionBar(tbToolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },
                    021
            );
        } else {
            MAIN();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {

            } else {
                Utils.showToast(mCtx, "Please grant all permissions!");
                finish();
            }
        }
        MAIN();
    }

    private void MAIN() {
        bLALogin.setOnClickListener(b -> {
            if (Validation.cantBeEmptyET(etLAUsername)) return;
            if (Validation.cantBeEmptyET(etLAPassword)) return;
            LoginTask.RequestDto requestDto = new LoginTask.RequestDto(
                    etLAUsername.getText().toString().trim(),
                    etLAPassword.getText().toString().trim(),
                    AUP_APPLICATION);

            LoginTask.fire(mCtx, requestDto, new GenericResponseCallback() {

                @Override
                public void onPreExecuteIon() {
                    MyProgressDialog.show(mCtx, "Please wait!", false);
                }

                @Override
                public void onIonCompleted() {
                    MyProgressDialog.dismiss();
                }

                @Override
                public void on200(Object obj) {
                    LoginTask.ResponseDto responseDto = (LoginTask.ResponseDto) obj;
                    if (responseDto.getSuccess() == 1) {

                        if (responseDto.getData().getRole().equals("ROLE_ADMIN")) {
                            startAdminActivity();
                        } else {
                            Utils.showToast(mCtx, "Will start user activity");
                        }

                    } else {
                        //NOT SUCCESSFULL
                        Log.e(Utils.TAG, "on200: NOT SUCCESSFULL", null);
                    }
                }

                @Override
                public void on400(String status) {

                }

                @Override
                public void on500(String status) {

                }

                @Override
                public void on404(String status) {

                }

                @Override
                public void on401(Object obj) {
                    LoginTask.ResponseDto responseDto = (LoginTask.ResponseDto) obj;
                    MyAlertDialog.show(mCtx, "Error", responseDto.getErrorMessage());
                }

                @Override
                public void on419(Object obj) {

                }

                @Override
                public void onNetworkError(Exception e) {

                }

                @Override
                public void onDataError(Exception e) {

                }

                @Override
                public void onNoInternetError() {

                }

                @Override
                public void onUnhandledError(String status) {

                }
            });
            //startAdminActivity();
        });

        bLACancle.setOnClickListener(b -> {
            finish();
        });
    }

    private void startAdminActivity() {
        Intent i = new Intent(mCtx, AdminActivity.class);
        startActivity(i);
    }

    private void initViews() {
        tbToolbar = (Toolbar) findViewById(R.id.tbToolbar);
        etLAUsername = (EditText) findViewById(R.id.etLAUsername);
        etLAPassword = (EditText) findViewById(R.id.etLAPassword);
        bLALogin = (Button) findViewById(R.id.bLALogin);
        bLACancle = (Button) findViewById(R.id.bLACancle);
    }
}
