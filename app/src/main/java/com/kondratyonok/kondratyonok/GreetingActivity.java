package com.kondratyonok.kondratyonok;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.kondratyonok.kondratyonok.settings.Settings;

import io.fabric.sdk.android.Fabric;

public class GreetingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_greeting);
    }

    public void next(View view) {
        final Intent intent = new Intent();
        intent.setClass(view.getContext(), DescriptionActivity.class);
        startActivity(intent);
        finish();
    }
}
