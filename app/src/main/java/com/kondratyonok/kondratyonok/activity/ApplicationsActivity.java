package com.kondratyonok.kondratyonok.activity;

import android.app.LoaderManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.kondratyonok.kondratyonok.database.Entry;
import com.kondratyonok.kondratyonok.R;
import com.kondratyonok.kondratyonok.Utils;
import com.kondratyonok.kondratyonok.adapter.MainPagerAdapter;
import com.kondratyonok.kondratyonok.database.EntryDbHolder;
import com.kondratyonok.kondratyonok.listener.OnMenuItemSelectedListener;
import com.kondratyonok.kondratyonok.listener.OnPageChangeListener;
import com.kondratyonok.kondratyonok.service.Album;
import com.kondratyonok.kondratyonok.service.ApplicationsLoaderService;
import com.kondratyonok.kondratyonok.service.BackgroundDownloadService;
import com.kondratyonok.kondratyonok.service.PhotoLoader;
import com.kondratyonok.kondratyonok.service.YaDownloader;
import com.kondratyonok.kondratyonok.settings.SettingsActivity;
import com.yandex.metrica.YandexMetrica;

import io.fabric.sdk.android.Fabric;

public class ApplicationsActivity extends AppCompatActivity {

    public DrawerLayout mDrawerLayout;
    public Fragment fragment;
    public ViewPager mViewPager;
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
        }

        private void added(Context context, final Intent intent) {
            (new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        YandexMetrica.reportEvent("Applications", "{\"action\":\"add\"}");
                        String packageName = Utils.getPackageFromDataString(intent.getDataString());
                        Entry entry = Utils.getEntryFromPackageName(packageName, getApplication());
                        EntryDbHolder.getInstance().getDb(getApplicationContext()).calculationResultDao().insert(entry);
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            })).start();
        }

        private void removed(final Context context, final Intent intent) {
            (new Thread(new Runnable() {
                @Override
                public void run() {
                    YandexMetrica.reportEvent("Applications", "{\"action\":\"removed\"}");
                    Entry entry = new Entry();
                    entry.packageName = Utils.getPackageFromDataString(intent.getDataString());
                    EntryDbHolder.getInstance().getDb(getApplicationContext()).calculationResultDao().delete(entry);
                }
            })).start();
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
        setTheme(SettingsActivity.getApplicationTheme(this));
        setContentView(R.layout.activity_main);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new OnMenuItemSelectedListener(this));
        mDrawerLayout = findViewById(R.id.menu);

        View header = navigationView.getHeaderView(0);
        header.findViewById(R.id.avatar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent();
                intent.setClass(v.getContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = new Intent(getApplicationContext(), ApplicationsLoaderService.class);
        startService(intent);

        final FragmentManager fragmentManager = getSupportFragmentManager();
        MainPagerAdapter mSectionsPagerAdapter = new MainPagerAdapter(fragmentManager);

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new OnPageChangeListener(this));

        Intent loader = new Intent(this, BackgroundDownloadService.class);
        bindService(loader, mConnection, Context.BIND_AUTO_CREATE);

        new CountDownTimer(300000000, 900000) {

            @Override
            public void onTick(long millisUntilFinished) {
                if (mBound) {
                    findViewById(R.id.main).setBackground(mService.getDrawable());
                }
            }

            @Override
            public void onFinish() {
            }
        }.start();
    }

    BackgroundDownloadService mService;
    boolean mBound = false;

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            BackgroundDownloadService.LocalBinder binder = (BackgroundDownloadService.LocalBinder) service;
            mService = binder.getService();
            mService.updateBackground(800000);
            mService.setActivity(ApplicationsActivity.this);
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();

        if (SettingsActivity.isApplicationSettingsChanged()) {
            recreate();
        }
        Log.i("APPLICATIONS", "onStart");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addDataScheme("package");
        registerReceiver(mMonitor, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mMonitor);
        unbindService(mConnection);
    }

    public NavigationView getNavigationView() {
        return navigationView;
    }
}
