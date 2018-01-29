package com.kondratyonok.kondratyonok;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class LayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
    }

    public void next(View view) {
        final Intent intent = new Intent();
        intent.setClass(view.getContext(), LauncherActivity.class);
        startActivity(intent);
        finish();
    }

    public void setStandardLayout(View view) {
        RadioButton standardRB = findViewById(R.id.standard_radio_button);
        RadioButton denseRB = findViewById(R.id.dense_radio_button);
        boolean isStandardLayout = true;
        standardRB.setChecked(isStandardLayout);
        denseRB.setChecked(!isStandardLayout);
    }

    public void setDenseLayout(View view) {
        RadioButton standardRB = findViewById(R.id.standard_radio_button);
        RadioButton denseRB = findViewById(R.id.dense_radio_button);
        boolean isStandardLayout = false;
        standardRB.setChecked(isStandardLayout);
        denseRB.setChecked(!isStandardLayout);
    }
}
