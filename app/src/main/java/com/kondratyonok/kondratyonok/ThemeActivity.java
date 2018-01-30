package com.kondratyonok.kondratyonok;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.kondratyonok.kondratyonok.settings.Settings;
import com.kondratyonok.kondratyonok.settings.Theme;

public class ThemeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
    }

    public void next(View view) {
        final Intent intent = new Intent();
        intent.setClass(view.getContext(), LayoutActivity.class);
        startActivity(intent);
        finish();
    }

    public void setLightTheme(View view) {
        RadioButton lightRB = findViewById(R.id.light_radio_button);
        RadioButton darkRB = findViewById(R.id.dark_radio_button);
        Settings.setTheme(Theme.LIGHT);
        lightRB.setChecked(true);
        darkRB.setChecked(false);
    }

    public void setDarkTheme(View view) {
        RadioButton lightRB = findViewById(R.id.light_radio_button);
        RadioButton darkRB = findViewById(R.id.dark_radio_button);
        Settings.setTheme(Theme.DARK);
        lightRB.setChecked(false);
        darkRB.setChecked(true);
    }
}
