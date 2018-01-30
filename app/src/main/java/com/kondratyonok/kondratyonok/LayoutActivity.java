package com.kondratyonok.kondratyonok;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.kondratyonok.kondratyonok.settings.Layout;
import com.kondratyonok.kondratyonok.settings.Settings;
import com.kondratyonok.kondratyonok.settings.Theme;

public class LayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Settings.theme);
        setContentView(R.layout.activity_layout);
    }

    public void next(View view) {
        Settings.save(this);
        final Intent intent = new Intent();
        intent.setClass(view.getContext(), LauncherActivity.class);
        startActivity(intent);
    }

    public void setStandardLayout(View view) {
        RadioButton standardRB = findViewById(R.id.standard_radio_button);
        RadioButton denseRB = findViewById(R.id.dense_radio_button);
        Settings.layout = Layout.STANDARD;
        standardRB.setChecked(true);
        denseRB.setChecked(false);
    }

    public void setDenseLayout(View view) {
        RadioButton standardRB = findViewById(R.id.standard_radio_button);
        RadioButton denseRB = findViewById(R.id.dense_radio_button);
        Settings.layout = Layout.DENSE;
        standardRB.setChecked(false);
        denseRB.setChecked(true);
    }
}
