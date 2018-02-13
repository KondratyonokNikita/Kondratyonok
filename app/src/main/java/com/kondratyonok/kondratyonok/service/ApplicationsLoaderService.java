package com.kondratyonok.kondratyonok.service;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.kondratyonok.kondratyonok.Utils;

import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by NKondratyonok on 13.02.18.
 */

public class ApplicationsLoaderService extends Service {
    private class LoadApplicationsTask implements Runnable {

        @Override
        public void run() {
            try {
                Utils.loadEntriesList(getApplication());
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
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
