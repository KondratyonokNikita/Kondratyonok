package com.kondratyonok.kondratyonok.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.kondratyonok.kondratyonok.R;
import com.kondratyonok.kondratyonok.settings.SettingsActivity;
import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.push.YandexMetricaPush;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        YandexMetrica.reportEvent("Activity", "{\"activity\":\"profile\"}");

        setTheme(SettingsActivity.getApplicationTheme(this));
        setContentView(R.layout.activity_profile);

        if (savedInstanceState != null) {
            YandexMetrica.reportAppOpen(this);
        }

        Intent intent = getIntent();

        handleDeeplink(intent);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.my_name);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.content);
        recyclerView.setHasFixedSize(false);

        recyclerView.setLayoutManager(new LinearLayoutManager(
                this));

        recyclerView.setAdapter(new ProfileAdapter(this));
    }

    /**
     * Deeplink can be extracted from open intent.
     */
    private void handleDeeplink(final Intent intent) {
        Uri uri = intent.getData();
        if (uri != null && !TextUtils.isEmpty(uri.getHost())) {
            YandexMetrica.reportEvent("Open deeplink");
        }
    }

    /**
     * Deeplink push message can contain user defined payload. It can be extracted from intent
     * as {@code String} with {@link YandexMetricaPush#EXTRA_PAYLOAD} constant.
     * This is example method how to do this.
     */
    private void handlePayload(final Intent intent) {
        String payload = intent.getStringExtra(YandexMetricaPush.EXTRA_PAYLOAD);
        if (!TextUtils.isEmpty(payload)) {
            YandexMetrica.reportEvent("Handle payload");
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        YandexMetrica.reportAppOpen(this);
        handleDeeplink(intent);
        handlePayload(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        YandexMetrica.onResumeActivity(this);
    }

    @Override
    protected void onPause() {
        YandexMetrica.onPauseActivity(this);
        super.onPause();
    }

}
