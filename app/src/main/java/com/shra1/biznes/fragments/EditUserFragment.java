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
import com.shra1.biznes.webservices.GetUserProfileTask;
import com.shra1.biznes.webservices.LoginTask;
import com.shra1.biznes.webservices.RegisterUserTask;

import java.io.File;

import static android.app.Activity.RESULT_OK;

public class EditUserFragment extends Fragment {
    private static final int PICK_IMAGE = 100;
    private static EditUserFragment INSTANCE = null;
    String LOCAL_FILE_PATH;
    String CONTENT_TYPE;
    String FILE_EXTENTION;
    private EditText etFEUUsername;
    private EditText etFEUFirstName;
    private EditText etFEULastName;
    private EditText etFEUEmailAddress;
    private EditText etFEUPassword;
    private EditText etFEUMobileNumber;
    private EditText etFEUAddress;
    private Button bFEUUpdate;
    private Button bFEUDelete;
    private Button bFEUCancle;
    //private RelativeLayout llFEUSelectPhoto;
    private ImageView ivFEUPhoto;
    private Context mCtx;
    private GetUserProfileTask.ResponseDto selectedUser;

    public EditUserFragment() {
    }

    public static EditUserFragment getInstance(GetUserProfileTask.ResponseDto selectedUser) {
        if (INSTANCE == null) {
            INSTANCE = new EditUserFragment();
            INSTANCE.selectedUser = selectedUser;
        }
        return INSTANCE;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_user, container, false);
        mCtx = container.getContext();

        AdminActivity.getInstance().setTitle("Edit User");

        initViews(v);

        /*llFEUSelectPhoto.setOnClickListener(ll -> {
            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            i.setType("image/*");
            startActivityForResult(i, PICK_IMAGE);
        });*/

        etFEUUsername.setText(selectedUser.getData().getUserName());
        etFEUFirstName.setText(selectedUser.getData().getFirstName());
        etFEULastName.setText(selectedUser.getData().getLastName());
        etFEUEmailAddress.setText(selectedUser.getData().getEmailAddress());
        etFEUMobileNumber.setText(selectedUser.getData().getMobileNumber());
        etFEUAddress.setText(selectedUser.getData().getAddress());
        Ion.with(mCtx)
                .load(selectedUser.getData().getPhotoUrl())
                .intoImageView(ivFEUPhoto).withBitmapInfo()
                .setCallback(new FutureCallback<ImageViewBitmapInfo>() {
                    @Override
                    public void onCompleted(Exception e, ImageViewBitmapInfo result) {
                        if (e!=null){
                            e.printStackTrace();
                            return;
                        }
                        ivFEUPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        ivFEUPhoto.setClipToOutline(true);
                    }
                });

        bFEUCancle.setOnClickListener(b -> {
            AdminActivity.getInstance().onBackPressed();
        });

        /*bFEUUpdate.setOnClickListener(b -> {
            if (Validation.cantBeEmptyET(etFEUUsername)) return;
            if (Validation.cantBeEmptyET(etFEUFirstName)) return;
            if (Validation.cantBeEmptyET(etFEULastName)) return;
            if (Validation.cantBeEmptyET(etFEUEmailAddress)) return;
            if (Validation.cantBeEmptyET(etFEUPassword)) return;
            if (Validation.cantBeEmptyET(etFEUMobileNumber)) return;
            if (Validation.cantBeEmptyET(etFEUAddress)) return;

            MyProgressDialog.show(mCtx, "Please wait uploading photo", false);
            Uri file = Uri.fromFile(new File(LOCAL_FILE_PATH));
            FirebaseStorage.getInstance().getReference("BiznessUserPhotos")
                    .child("" + System.currentTimeMillis() + "." + FILE_EXTENTION)
                    .putFile(file)
                    .addOnSuccessListener(taskSnapshot -> {
                        RegisterUserTask.RequestDto u = new RegisterUserTask.RequestDto(
                                etFEUUsername.getText().toString().trim(),
                                etFEUFirstName.getText().toString().trim(),
                                etFEULastName.getText().toString().trim(),
                                etFEUEmailAddress.getText().toString().trim(),
                                etFEUPassword.getText().toString().trim(),
                                etFEUMobileNumber.getText().toString().trim(),
                                etFEUAddress.getText().toString().trim(),
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
                                            AdminActivity.getInstance().onBackPressed();
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
        });*/

        return v;
    }

    private void initViews(View v) {
        etFEUUsername = (EditText) v.findViewById(R.id.etFEUUsername);
        etFEUFirstName = (EditText) v.findViewById(R.id.etFEUFirstName);
        etFEULastName = (EditText) v.findViewById(R.id.etFEULastName);
        etFEUEmailAddress = (EditText) v.findViewById(R.id.etFEUEmailAddress);
        etFEUPassword = (EditText) v.findViewById(R.id.etFEUPassword);
        etFEUMobileNumber = (EditText) v.findViewById(R.id.etFEUMobileNumber);
        etFEUAddress = (EditText) v.findViewById(R.id.etFEUAddress);
        bFEUUpdate = (Button) v.findViewById(R.id.bFEUUpdate);
        bFEUDelete = (Button) v.findViewById(R.id.bFEUDelete);
        bFEUCancle = (Button) v.findViewById(R.id.bFEUCancle);
        //llFEUSelectPhoto = (RelativeLayout) v.findViewById(R.id.llFEUSelectPhoto);
        ivFEUPhoto = (ImageView) v.findViewById(R.id.ivFEUPhoto);
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
                    .intoImageView(ivFEUPhoto)
                    .withBitmapInfo()
                    .setCallback((e, result) -> {
                        if (e != null) {
                            Utils.showToast(mCtx, "Error while retrieving image");
                            e.printStackTrace();
                            return;
                        }
                        ivFEUPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        ivFEUPhoto.setClipToOutline(true);
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
