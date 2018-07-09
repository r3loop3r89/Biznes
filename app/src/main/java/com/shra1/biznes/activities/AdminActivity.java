package com.shra1.biznes.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.shra1.biznes.R;
import com.shra1.biznes.fragments.AdminHomeFragment;

public class AdminActivity extends AppCompatActivity {
    private static AdminActivity INSTANCE = null;
    private Toolbar tbToolbar;
    private DrawerLayout dlAADrawer;
    private LinearLayout llAASlider;
    private ListView lvAADrawerList;
    private LinearLayout llAAMainContent;
    private Context mCtx;
    private FrameLayout flAAFragmentContainer;
    private int iFlAAFragmentContainer;
    private FragmentManager fragmentManager;

    public static AdminActivity getInstance() {
        return INSTANCE;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        mCtx = this;
        INSTANCE = this;
        fragmentManager = getSupportFragmentManager();

        initViews();

        setSupportActionBar(tbToolbar);

        changeFragment(AdminHomeFragment.getInstance(), true);
    }

    public void changeFragment(Fragment fragment, boolean isFirst) {
        if (isFirst) {
            //dont add to backstack
            fragmentManager
                    .beginTransaction()
                    .replace(iFlAAFragmentContainer, fragment)
                    .commit();
        } else {
            //add to backstack
            fragmentManager
                    .beginTransaction()
                    .replace(iFlAAFragmentContainer, fragment)
                    .addToBackStack("yes")
                    .commit();
        }
    }

    private void initViews() {
        tbToolbar = (Toolbar) findViewById(R.id.tbToolbar);
        dlAADrawer = (DrawerLayout) findViewById(R.id.dlAADrawer);
        llAASlider = (LinearLayout) findViewById(R.id.llAASlider);
        lvAADrawerList = (ListView) findViewById(R.id.lvAADrawerList);
        llAAMainContent = (LinearLayout) findViewById(R.id.llAAMainContent);
        iFlAAFragmentContainer = R.id.flAAFragmentContainer;
        flAAFragmentContainer = findViewById(iFlAAFragmentContainer);
    }
}
