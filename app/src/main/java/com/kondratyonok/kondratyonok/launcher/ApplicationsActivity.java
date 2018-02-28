package com.kondratyonok.kondratyonok.launcher;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.crashlytics.android.Crashlytics;
import com.kondratyonok.kondratyonok.R;
import com.kondratyonok.kondratyonok.profile.ProfileActivity;
import com.kondratyonok.kondratyonok.launcher.adapter.MainPagerAdapter;
import com.kondratyonok.kondratyonok.model.Entry;
import com.kondratyonok.kondratyonok.database.EntryDbHolder;
import com.kondratyonok.kondratyonok.greeting.GreetingActivity;
import com.kondratyonok.kondratyonok.helper.BackgroundReadyNotifiable;
import com.kondratyonok.kondratyonok.listener.OnMenuItemSelectedListener;
import com.kondratyonok.kondratyonok.listener.OnPageChangeListener;
import com.kondratyonok.kondratyonok.service.ApplicationsLoaderService;
import com.kondratyonok.kondratyonok.service.downloader.BackgroundDownloadService;
import com.kondratyonok.kondratyonok.settings.SettingsActivity;
import com.kondratyonok.kondratyonok.utils.FileUtils;
import com.kondratyonok.kondratyonok.utils.PackageUtils;
import com.yandex.metrica.YandexMetrica;

import net.hockeyapp.android.UpdateManager;

import java.util.Calendar;

import io.fabric.sdk.android.Fabric;

public class ApplicationsActivity extends AppCompatActivity implements BackgroundReadyNotifiable {

    public DrawerLayout mDrawerLayout;
    public Fragment fragment;
    public ViewPager mViewPager;
    private ApplicationsActivity activity = this;
    private final String TAG = "ApplicationsActivity";
    private NavigationView navigationView;
    private final String NEW_DAY = "new_day";

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
                case NEW_DAY:
                    newDay(context, intent);
                    break;
                default:
            }
        }

        private void newDay(Context context, final Intent intent) {
            Log.i("New_DAY", "restartLoader");
            mService.restartLoader();
        }

        private void added(Context context, final Intent intent) {
            (new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        YandexMetrica.reportEvent("Applications", "{\"action\":\"add\"}");
                        String packageName = PackageUtils.getPackageFromDataString(intent.getDataString());
                        Entry entry = PackageUtils.getEntryFromPackageName(packageName, getApplication());
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
                    entry.packageName = PackageUtils.getPackageFromDataString(intent.getDataString());
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

        startService(new Intent(this, BackgroundDownloadService.class));

        AlarmManager alarmMgr;
        PendingIntent alarmIntent;

        alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent1 = new Intent(this, BroadcastReceiver.class);
        intent1.setAction(NEW_DAY);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent1, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.set(Calendar.MINUTE, 45);

        alarmMgr.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(),
                1000 * 60 * 15, alarmIntent);
        this.notifyBackgroundReady();

        UpdateManager.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(mConnection);

    }

    BackgroundDownloadService mService;
    boolean mBound = false;

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            BackgroundDownloadService.LocalBinder binder = (BackgroundDownloadService.LocalBinder) service;
            mService = binder.getService();
            mService.setReadyNotifiable(ApplicationsActivity.this);
            mService.setActivity(ApplicationsActivity.this);
            mService.setTimeout(900000);
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putBoolean("mBound", this.mBound);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.mBound = savedInstanceState.getBoolean("mBound");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent loader = new Intent(this, BackgroundDownloadService.class);
        bindService(loader, mConnection, Context.BIND_AUTO_CREATE);

        if (SettingsActivity.isApplicationSettingsChanged()) {
            recreate();
        }
        Log.i("APPLICATIONS", "onStart");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
        intentFilter.addDataScheme("package");
        registerReceiver(mMonitor, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mMonitor);
        UpdateManager.unregister();
    }

    public NavigationView getNavigationView() {
        return navigationView;
    }

    @Override
    public void notifyBackgroundReady() {
        Bitmap background = FileUtils.ImageSaver.getInstance().loadImage(this, getResources().getString(R.string.background_file));
        if (background != null) {
            findViewById(R.id.main).setBackground(new BitmapDrawable(getResources(), background));
        }
    }
}
