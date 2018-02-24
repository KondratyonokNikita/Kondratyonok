package com.kondratyonok.kondratyonok.service;

import android.app.Application;
import android.app.Service;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteConstraintException;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.kondratyonok.kondratyonok.database.EntryDbHolder;
import com.kondratyonok.kondratyonok.model.Entry;
import com.kondratyonok.kondratyonok.utils.PackageUtils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by NKondratyonok on 13.02.18.
 */

public class ApplicationsLoaderService extends Service {
    private class LoadApplicationsTask implements Runnable {

        @Override
        public void run() {
            PackageManager packageManager = getPackageManager();
            Intent intent = new Intent(Intent.ACTION_MAIN, null);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            List<ApplicationInfo> app = packageManager.getInstalledApplications(0);
            for (ApplicationInfo applicationInfo : app) {
                try {
                    if (packageManager.getLaunchIntentForPackage(applicationInfo.packageName) != null) {
                        if (!applicationInfo.packageName.equals(getPackageName())) {
                            Entry entry = PackageUtils.getEntryFromPackageName(applicationInfo.packageName, getApplication());
                            try {
                                EntryDbHolder.getInstance().getDb(getApplicationContext()).calculationResultDao().insert(entry);
                            } catch (SQLiteConstraintException e) {
                                Log.i("EXISTS", "package exists in database");
                            }
                        }
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private ExecutorService mExecutor;

    public ApplicationsLoaderService() {
        mExecutor = Executors.newSingleThreadExecutor();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mExecutor.execute(new LoadApplicationsTask());
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mExecutor.shutdownNow();
    }
}
