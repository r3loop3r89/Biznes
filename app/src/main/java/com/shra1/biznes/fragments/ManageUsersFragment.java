package com.shra1.biznes.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.shra1.biznes.R;
import com.shra1.biznes.activities.AdminActivity;
import com.shra1.biznes.adapters.UsersListAdapter;
import com.shra1.biznes.interfaces.GenericResponseCallback;
import com.shra1.biznes.utils.MyAlertDialog;
import com.shra1.biznes.utils.MyProgressDialog;
import com.shra1.biznes.webservices.GetUserProfileTask;
import com.shra1.biznes.webservices.ListOfAllUsersTask;

import java.util.List;

public class ManageUsersFragment extends Fragment {
    private static ManageUsersFragment INSTANCE = null;
    private Context mCtx;
    private ListView lvFMUList;

    public static ManageUsersFragment getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ManageUsersFragment();
        }
        return INSTANCE;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_manage_users, container, false);
        mCtx = container.getContext();
        AdminActivity.getInstance().setTitle("Manage Users");

        initViews(v);

        ListOfAllUsersTask.fire(mCtx, new GenericResponseCallback() {
            @Override
            public void onPreExecuteIon() {
                MyProgressDialog.showPleaseWait(mCtx);
            }

            @Override
            public void onIonCompleted() {
                MyProgressDialog.dismiss();
            }

            @Override
            public void on200(Object obj) {
                ListOfAllUsersTask.ResponseDto responseDto = (ListOfAllUsersTask.ResponseDto) obj;
                if (responseDto.getSuccess().equals("1")) {
                    showListViewContent(responseDto.getData());
                } else {

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
                MyAlertDialog.showNoInternetErrorDialog(mCtx);
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

        return v;
    }

    private void showListViewContent(List<ListOfAllUsersTask.ResponseDto.Details> data) {
        UsersListAdapter adapter = new UsersListAdapter(mCtx, data, clickedItem -> {
            GetUserProfileTask.fire(mCtx, clickedItem, new GenericResponseCallback() {
                @Override
                public void onPreExecuteIon() {
                    MyProgressDialog.showPleaseWait(mCtx);
                }

                @Override
                public void onIonCompleted() {
                    MyProgressDialog.dismiss();
                }

                @Override
                public void on200(Object obj) {
                    GetUserProfileTask.ResponseDto selectedUser = (GetUserProfileTask.ResponseDto) obj;
                    if (selectedUser.getSuccess()==1){
                        AdminActivity.getInstance().changeFragment(EditUserFragment.getInstance(selectedUser), false);
                    }else{

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
        });
        lvFMUList.setAdapter(adapter);
    }

    private void initViews(View v) {
        lvFMUList = v.findViewById(R.id.lvFMUList);
    }
}
