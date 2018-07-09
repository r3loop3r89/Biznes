package com.shra1.biznes.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.firebase.storage.FirebaseStorage;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.ImageViewBitmapInfo;
import com.koushikdutta.ion.Ion;
import com.shra1.biznes.R;
import com.shra1.biznes.activities.AdminActivity;
import com.shra1.biznes.interfaces.GenericResponseCallback;
import com.shra1.biznes.utils.MyAlertDialog;
import com.shra1.biznes.utils.MyProgressDialog;
import com.shra1.biznes.utils.Utils;
import com.shra1.biznes.utils.Validation;
import com.shra1.biznes.webservices.LoginTask;
import com.shra1.biznes.webservices.RegisterUserTask;

import java.io.File;

import static android.app.Activity.RESULT_OK;

public class AddUserFragment extends Fragment {
    private static final int PICK_IMAGE = 100;
    private static AddUserFragment INSTANCE = null;
    String LOCAL_FILE_PATH;
    String CONTENT_TYPE;
    String FILE_EXTENTION;
    private EditText etFAUUsername;
    private EditText etFAUFirstName;
    private EditText etFAULastName;
    private EditText etFAUEmailAddress;
    private EditText etFAUPassword;
    private EditText etFAUMobileNumber;
    private EditText etFAUAddress;
    private Button bFAUSave;
    private Button bFAUCancle;
    private RelativeLayout llFAUSelectPhoto;
    private ImageView ivFAUPhoto;
    private Context mCtx;


    public AddUserFragment() {
    }

    public static AddUserFragment getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AddUserFragment();
        }
        return INSTANCE;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_user, container, false);
        mCtx = container.getContext();

        initViews(v);

        llFAUSelectPhoto.setOnClickListener(ll -> {
            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            i.setType("image/*");
            startActivityForResult(i, PICK_IMAGE);
        });

        bFAUCancle.setOnClickListener(b -> {
            AdminActivity.getInstance().onBackPressed();
        });

        bFAUSave.setOnClickListener(b -> {
            if (Validation.cantBeEmptyET(etFAUUsername)) return;
            if (Validation.cantBeEmptyET(etFAUFirstName)) return;
            if (Validation.cantBeEmptyET(etFAULastName)) return;
            if (Validation.cantBeEmptyET(etFAUEmailAddress)) return;
            if (Validation.cantBeEmptyET(etFAUPassword)) return;
            if (Validation.cantBeEmptyET(etFAUMobileNumber)) return;
            if (Validation.cantBeEmptyET(etFAUAddress)) return;

            MyProgressDialog.show(mCtx, "Please wait uploading photo", false);
            Uri file = Uri.fromFile(new File(LOCAL_FILE_PATH));
            FirebaseStorage.getInstance().getReference("BiznessUserPhotos")
                    .child("" + System.currentTimeMillis() + "." + FILE_EXTENTION)
                    .putFile(file)
                    .addOnSuccessListener(taskSnapshot -> {
                        RegisterUserTask.RequestDto u = new RegisterUserTask.RequestDto(
                                etFAUUsername.getText().toString().trim(),
                                etFAUFirstName.getText().toString().trim(),
                                etFAULastName.getText().toString().trim(),
                                etFAUEmailAddress.getText().toString().trim(),
                                etFAUPassword.getText().toString().trim(),
                                etFAUMobileNumber.getText().toString().trim(),
                                etFAUAddress.getText().toString().trim(),
                                "ROLE_NORMAL_USER",
                                taskSnapshot.getDownloadUrl().toString(),
                                LoginTask.RequestDto.AUP_APPLICATION);
                        MyProgressDialog.dismiss();

                        RegisterUserTask.fire(mCtx, u, new GenericResponseCallback() {
                            @Override
                            public void onPreExecuteIon() {
                                MyProgressDialog.show(mCtx, "Please wait registering user", false);
                            }

                            @Override
                            public void onIonCompleted() {
                                MyProgressDialog.dismiss();
                            }

                            @Override
                            public void on200(Object obj) {
                                RegisterUserTask.ResponseDto
                                        responseDto = (RegisterUserTask.ResponseDto) obj;
                                if (responseDto.getSuccess() == 1) {
                                    MyAlertDialog.show(mCtx, "Successfull", responseDto.getMessage(), "Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                                } else {
                                    MyAlertDialog.show(mCtx, "Failed", "Failed");
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


                    }).addOnFailureListener(e -> {
                Utils.showToast(mCtx, "There was a problem while uploading the image. Please try again later");
                MyProgressDialog.dismiss();
            });
        });

        return v;
    }

    private void initViews(View v) {
        etFAUUsername = (EditText) v.findViewById(R.id.etFAUUsername);
        etFAUFirstName = (EditText) v.findViewById(R.id.etFAUFirstName);
        etFAULastName = (EditText) v.findViewById(R.id.etFAULastName);
        etFAUEmailAddress = (EditText) v.findViewById(R.id.etFAUEmailAddress);
        etFAUPassword = (EditText) v.findViewById(R.id.etFAUPassword);
        etFAUMobileNumber = (EditText) v.findViewById(R.id.etFAUMobileNumber);
        etFAUAddress = (EditText) v.findViewById(R.id.etFAUAddress);
        bFAUSave = (Button) v.findViewById(R.id.bFAUSave);
        bFAUCancle = (Button) v.findViewById(R.id.bFAUCancle);
        llFAUSelectPhoto = (RelativeLayout) v.findViewById(R.id.llFAUSelectPhoto);
        ivFAUPhoto = (ImageView) v.findViewById(R.id.ivFAUPhoto);
    }

    @Override
    public void onDetach() {
        INSTANCE = null;
        super.onDetach();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            //get real path of image from uri
            LOCAL_FILE_PATH = getRealPathFromURI(selectedImageUri);
            File imageFile = new File(LOCAL_FILE_PATH);
            if (imageFile != null) {
                String url = imageFile.getAbsolutePath().replace(" ", "-");
                CONTENT_TYPE = getMimeType(url);
            }

            Ion.with(mCtx)
                    .load(LOCAL_FILE_PATH)
                    .intoImageView(ivFAUPhoto)
                    .withBitmapInfo()
                    .setCallback((e, result) -> {
                        if (e != null) {
                            Utils.showToast(mCtx, "Error while retrieving image");
                            e.printStackTrace();
                            return;
                        }
                        ivFAUPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        ivFAUPhoto.setClipToOutline(true);
                    });
        }
    }

    //get real path from uri
    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = mCtx.getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    //to get MIME type of image url
    private String getMimeType(String url) {
        String type = null;
        FILE_EXTENTION = MimeTypeMap.getFileExtensionFromUrl(url);
        if (FILE_EXTENTION != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(FILE_EXTENTION);
        }
        return type;
    }
}
