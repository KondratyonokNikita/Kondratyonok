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
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.GridLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.kondratyonok.kondratyonok.Database;
import com.kondratyonok.kondratyonok.Entry;
import com.kondratyonok.kondratyonok.Holder;
import com.kondratyonok.kondratyonok.OffsetItemDecoration;
import com.kondratyonok.kondratyonok.R;
import com.kondratyonok.kondratyonok.Utils;
import com.kondratyonok.kondratyonok.adapter.GridAdapter;
import com.kondratyonok.kondratyonok.listener.OnMenuItemSelectedListener;
import com.kondratyonok.kondratyonok.settings.SettingsActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ApplicationsActivity extends AppCompatActivity {

    private final List<Entry> data = new ArrayList<>();
    public DrawerLayout mDrawerLayout;
    private RecyclerView.Adapter<RecyclerView.ViewHolder> applicationsAdapter;
    private ApplicationsActivity activity = this;
    private final String TAG = "ApplicationsActivity";

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
            applicationsAdapter.notifyDataSetChanged();
        }

        private void added(Context context, Intent intent) {
            String packageName = Utils.getPackageFromDataString(intent.getDataString());
            try {
                Entry entry = getEntryFromPackageName(packageName);
                data.add(entry);
                Database.insertOrUpdate(entry);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }

        private void removed(final Context context, final Intent intent) {
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
        if (!SettingsActivity.hasAllSettings(this)) {
            final Intent intent = new Intent();
            intent.setClass(this, ApplicationsActivity.class);
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

        NavigationView navigationView = findViewById(R.id.nav_view);
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addDataScheme("package");
        registerReceiver(mMonitor, intentFilter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        createGridLayout();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mMonitor);
        for (Entry entry : data) {
            Database.insertOrUpdate(entry);
        }
    }

    private void createGridLayout() {
        final RecyclerView recyclerView = findViewById(R.id.content);
        recyclerView.setHasFixedSize(false);

        RecyclerView.ItemDecoration itemDecoration = SettingsActivity.getItemDecorator(this);
        if (itemDecoration != null) {
            recyclerView.addItemDecoration(SettingsActivity.getItemDecorator(this));
        }

        recyclerView.setLayoutManager(SettingsActivity.getLayoutManager(this));

        generateData();
        applicationsAdapter = SettingsActivity.getAdapter(this, data);
        recyclerView.setAdapter(applicationsAdapter);
    }

    private Entry getEntryFromPackageName(String packageName) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = getPackageManager();
        Drawable icon = getPackageManager().getApplicationIcon(packageName);
        String name = (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA));
        long updateTime = packageManager.getPackageInfo(packageName, 0).lastUpdateTime;
        return new Entry(icon, name, packageName, updateTime);
    }

    private void generateData() {
        PackageManager packageManager = getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ApplicationInfo> app = packageManager.getInstalledApplications(0);
        for (ApplicationInfo applicationInfo : app) {
            try {
                if (packageManager.getLaunchIntentForPackage(applicationInfo.packageName) != null) {
                    if (!applicationInfo.packageName.equals(getPackageName())) {
                        Entry entry = getEntryFromPackageName(applicationInfo.packageName);
                        if (!data.contains(entry)) {
                            data.add(entry);
                        }
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }

        Collections.sort(data, SettingsActivity.getSortingMethod(this));
    }
}
