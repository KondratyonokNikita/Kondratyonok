package com.kondratyonok.kondratyonok;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.crashlytics.android.Crashlytics;
import com.kondratyonok.kondratyonok.settings.SettingsActivity;

import io.fabric.sdk.android.Fabric;

public class GreetingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        if (SettingsActivity.hasAllSettings(this)) {
            final Intent intent = new Intent();
            intent.setClass(this, LauncherActivity.class);
            startActivity(intent);
            finish();
        } else {
            setContentView(R.layout.activity_greeting);
        }
    }

    public void next(View view) {
        final Intent intent = new Intent();
        intent.setClass(view.getContext(), DescriptionActivity.class);
        startActivity(intent);
        finish();
    }
}
