package com.kondratyonok.kondratyonok.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.kondratyonok.kondratyonok.R;
import com.kondratyonok.kondratyonok.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class GreetingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_greeting);

        final List<Integer> data = new ArrayList<Integer>() {{
            add(R.layout.fr_greeting);
            add(R.layout.fr_description);
            add(R.layout.fr_theme);
            add(R.layout.fr_layout);
            add(R.layout.fr_agree);
        }};
        final FragmentManager fragmentManager = getSupportFragmentManager();
        ViewPagerAdapter mSectionsPagerAdapter = new ViewPagerAdapter(fragmentManager, data);

        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }
}
