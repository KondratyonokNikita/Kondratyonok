package com.kondratyonok.kondratyonok.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.kondratyonok.kondratyonok.R;
import com.kondratyonok.kondratyonok.adapter.GreetingPagerAdapter;
import com.kondratyonok.kondratyonok.settings.SettingsActivity;
import com.yandex.metrica.YandexMetrica;

import java.util.ArrayList;
import java.util.List;

public class GreetingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        YandexMetrica.reportEvent("Activity", "{\"activity\":\"greeting\"}");
        setTheme(SettingsActivity.getApplicationTheme(this));
        setContentView(R.layout.activity_greeting);

        final List<Integer> data = new ArrayList<Integer>() {{
            add(R.layout.fr_greeting);
            add(R.layout.fr_description);
            add(R.layout.fr_theme);
            add(R.layout.fr_layout);
            add(R.layout.fr_agree);
        }};
        final FragmentManager fragmentManager = getSupportFragmentManager();
        GreetingPagerAdapter mSectionsPagerAdapter = new GreetingPagerAdapter(fragmentManager, data);

        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }
}
