package com.shra1.biznes.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shra1.biznes.R;
import com.shra1.biznes.activities.AdminActivity;
import com.shra1.biznes.adapters.rva.MainMenuRVA;
import com.shra1.biznes.dtos.MainMenuItem;

import java.util.ArrayList;
import java.util.List;

public class AdminHomeFragment extends Fragment {
    public static final String MNU_TITLE_ADD_USER = "Add User";
    private static AdminHomeFragment INSTANCE = null;
    private RecyclerView rvFAHMainMenu;
    private Context mCtx;

    public AdminHomeFragment() {
    }

    public static AdminHomeFragment getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AdminHomeFragment();
        }
        return INSTANCE;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_admin_home, container, false);
        mCtx = container.getContext();

        AdminActivity.getInstance().setTitle("Biznes | Admin Home");

        initViews(v);

        List<MainMenuItem> mainMenuItems = new ArrayList();
        mainMenuItems.add(new MainMenuItem(MNU_TITLE_ADD_USER, 0, menuItem -> {
            AdminActivity.getInstance().changeFragment(AddUserFragment.getInstance(), false);
        }, 0));

        mainMenuItems.add(new MainMenuItem("Manage Users", 0, menuItem -> {
            AdminActivity.getInstance().changeFragment(ManageUsersFragment.getInstance(), false);
        }, 0));

        mainMenuItems.add(new MainMenuItem("Settings", 0, menuItem -> {
            AdminActivity.getInstance().changeFragment(SettingsFragment.getInstance(), false);

        }, 2));


        MainMenuRVA mainMenuRVA = new MainMenuRVA(mCtx, mainMenuItems);
        rvFAHMainMenu.setLayoutManager(new GridLayoutManager(mCtx, 2));
        rvFAHMainMenu.setAdapter(mainMenuRVA);

        return v;
    }

    private void initViews(View v) {
        rvFAHMainMenu = v.findViewById(R.id.rvFAHMainMenu);
    }

    @Override
    public void onDetach() {
        INSTANCE = null;
        super.onDetach();
    }
}
