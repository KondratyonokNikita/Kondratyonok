package com.kondratyonok.kondratyonok;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
    }

    public void next(View view) {
        final Intent intent = new Intent();
        intent.setClass(view.getContext(), ThemeActivity.class);
        startActivity(intent);
        finish();
    }
}
