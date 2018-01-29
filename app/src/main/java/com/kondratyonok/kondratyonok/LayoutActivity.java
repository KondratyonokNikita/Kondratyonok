package com.kondratyonok.kondratyonok;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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
}
