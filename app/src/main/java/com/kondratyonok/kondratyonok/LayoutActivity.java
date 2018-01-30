package com.kondratyonok.kondratyonok;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

import com.kondratyonok.kondratyonok.settings.Layout;
import com.kondratyonok.kondratyonok.settings.SettingsActivity;

public class LayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(SettingsActivity.getApplicationTheme(this));
        setContentView(R.layout.activity_layout);
    }

    public void next(View view) {
        if (!SettingsActivity.hasLayout(this)) {
            SettingsActivity.setLayout(Layout.DEFAULT, this);
        }
        final Intent intent = new Intent();
        intent.setClass(view.getContext(), LauncherActivity.class);
        startActivity(intent);
        finish();
    }

    public void setStandardLayout(View view) {
        RadioButton standardRB = findViewById(R.id.standard_radio_button);
        RadioButton denseRB = findViewById(R.id.dense_radio_button);
        SettingsActivity.setLayout(Layout.STANDARD, this);
        standardRB.setChecked(true);
        denseRB.setChecked(false);
    }

    public void setDenseLayout(View view) {
        RadioButton standardRB = findViewById(R.id.standard_radio_button);
        RadioButton denseRB = findViewById(R.id.dense_radio_button);
        SettingsActivity.setLayout(Layout.DENSE, this);
        standardRB.setChecked(false);
        denseRB.setChecked(true);
    }
}
