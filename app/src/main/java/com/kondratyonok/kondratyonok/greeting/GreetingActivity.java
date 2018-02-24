package com.kondratyonok.kondratyonok.greeting;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.kondratyonok.kondratyonok.R;
import com.kondratyonok.kondratyonok.greeting.fragment.AgreeFragment;
import com.kondratyonok.kondratyonok.greeting.fragment.DescriptionFragment;
import com.kondratyonok.kondratyonok.greeting.fragment.GreetingFragment;
import com.kondratyonok.kondratyonok.greeting.fragment.LayoutFragment;
import com.kondratyonok.kondratyonok.greeting.fragment.ThemeFragment;
import com.kondratyonok.kondratyonok.settings.SettingsActivity;
import com.kondratyonok.kondratyonok.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class GreetingActivity extends AppCompatActivity {
    private static final String TAG = "GreetingActivity";

    private final List<Fragment> data = new ArrayList<Fragment>() {{
        add(GreetingFragment.newInstance(R.layout.fr_greeting));
        add(DescriptionFragment.newInstance(R.layout.fr_description));
        add(ThemeFragment.newInstance(R.layout.fr_theme));
        add(LayoutFragment.newInstance(R.layout.fr_layout));
        add(AgreeFragment.newInstance(R.layout.fr_agree));
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonUtils.log(TAG, "onCreate");
        setTheme(SettingsActivity.getApplicationTheme(this));
        setContentView(R.layout.activity_greeting);

        final FragmentManager fragmentManager = getSupportFragmentManager();
        GreetingPagerAdapter mSectionsPagerAdapter = new GreetingPagerAdapter(fragmentManager, data);

        ViewPager mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }
}
