package com.kondratyonok.kondratyonok.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.GridLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.kondratyonok.kondratyonok.Database;
import com.kondratyonok.kondratyonok.Entry;
import com.kondratyonok.kondratyonok.Holder;
import com.kondratyonok.kondratyonok.OffsetItemDecoration;
import com.kondratyonok.kondratyonok.R;
import com.kondratyonok.kondratyonok.Utils;
import com.kondratyonok.kondratyonok.adapter.GridAdapter;
import com.kondratyonok.kondratyonok.listener.OnMenuItemSelectedListener;
import com.kondratyonok.kondratyonok.settings.SettingsActivity;
import com.yandex.metrica.YandexMetrica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.fabric.sdk.android.Fabric;

public class ApplicationsActivity extends AppCompatActivity {

    private List<Entry> data = new ArrayList<>();
    public DrawerLayout mDrawerLayout;
    public Fragment fragment;
    private ApplicationsActivity activity = this;
    private final String TAG = "ApplicationsActivity";
    private NavigationView navigationView;

    private BroadcastReceiver mMonitor = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, final Intent intent) {
            String action = intent.getAction();
            if (action == null) {
                return;
            }
            switch (intent.getAction()) {
                case Intent.ACTION_PACKAGE_REMOVED:
                    removed(context, intent);
                    break;
                case Intent.ACTION_PACKAGE_ADDED:
                    added(context, intent);
                    break;
                default:
                    return;
            }
            Collections.sort(data, SettingsActivity.getSortingMethod(activity));
            fragment.onConfigurationChanged(null);
        }

        private void added(Context context, Intent intent) {
            YandexMetrica.reportEvent("Applications", "{\"action\":\"add\"}");
            String packageName = Utils.getPackageFromDataString(intent.getDataString());
            try {
                Entry entry = Utils.getEntryFromPackageName(packageName, activity);
                data.add(entry);
                Database.insertOrUpdate(entry);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }

        private void removed(final Context context, final Intent intent) {
            YandexMetrica.reportEvent("Applications", "{\"action\":\"removed\"}");
            for (Entry entry : data) {
                String name = Utils.getPackageFromDataString(intent.getDataString());
                if (name.equals(entry.packageName)) {
                    Database.remove(entry);
                    data.remove(entry);
                    return;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        YandexMetrica.reportEvent("Activity", "{\"activity\":\"applications\"}");
        Fabric.with(this, new Crashlytics());
        if (SettingsActivity.isNeedWelcomePage(this)) {
            final Intent intent = new Intent();
            intent.setClass(this, GreetingActivity.class);
            startActivity(intent);
            finish();
        }

        Database.init(this);
        setTheme(SettingsActivity.getApplicationTheme(this));
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = findViewById(R.id.menu);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new OnMenuItemSelectedListener(this));

        View header = navigationView.getHeaderView(0);
        header.findViewById(R.id.avatar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent();
                intent.setClass(v.getContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });
        data = Utils.getEntriesList(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (SettingsActivity.isApplicationThemeChanged()) {
            recreate();
        }
        Log.i("APPLICATIONS", "onStart");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addDataScheme("package");
        registerReceiver(mMonitor, intentFilter);

        fragment = SettingsActivity.getLayoutFragment(this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_content, fragment)
                .commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mMonitor);
        for (Entry entry : data) {
            Database.insertOrUpdate(entry);
        }
    }

    public NavigationView getNavigationView() {
        return navigationView;
    }
}
