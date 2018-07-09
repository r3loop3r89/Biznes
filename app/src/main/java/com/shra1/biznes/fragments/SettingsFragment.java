package com.shra1.biznes.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shra1.biznes.R;

public class SettingsFragment extends Fragment {
    TextView tvTEST;
    String data;

    private static SettingsFragment INSTANCE = null;

    public static SettingsFragment getInstance(String data) {
        if (INSTANCE == null) {
            INSTANCE = new SettingsFragment();
            INSTANCE.data = data;
        }
        return INSTANCE;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        tvTEST = v.findViewById(R.id.tvTEST);

        tvTEST.setText(data);
        return v;
    }
}
