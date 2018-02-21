package com.kondratyonok.kondratyonok.service;

import android.app.Activity;
import android.app.LoaderManager;
import android.app.Service;
import android.arch.persistence.room.PrimaryKey;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.kondratyonok.kondratyonok.R;
import com.kondratyonok.kondratyonok.Utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BackgroundDownloadService extends Service implements LoaderManager.LoaderCallbacks<Bitmap> {
    public class LocalBinder extends Binder {
        public BackgroundDownloadService getService() {
            return BackgroundDownloadService.this;
        }
    }

    private final IBinder mBinder = new LocalBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private ExecutorService mExecutor;
    private Activity activity;

    public BackgroundDownloadService() {
        mExecutor = Executors.newSingleThreadExecutor();
        new Thread(new Runnable() {
            @Override
            public void run() {
                photos = new YaDownloader().fetchAlbum();
            }
        }).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("BackgroundService", "onStartCommand");
        //mExecutor.execute(new BackgroundDownloadService.LoadBackground());
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mExecutor.shutdownNow();
    }

    @Override
    public Loader<Bitmap> onCreateLoader(int id, Bundle args) {
        ++cur;
        while ((photos == null) || (photos.getPhotos().size() < cur)) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return new PhotoLoader(this, photos.getPhotos().get(cur).getImageUrl());
    }

    @Override
    public void onLoadFinished(Loader<Bitmap> loader, Bitmap data) {
        if (data != null) {
            this.background = new BitmapDrawable(getResources(), data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Bitmap> loader) {}

    private Album photos = null;
    private int cur = -1;
    private Drawable background = null;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Drawable getDrawable() {
        return background;
    }

    public void updateBackground(final int timeout) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (true) {
                    activity.getLoaderManager().initLoader(i++, null, BackgroundDownloadService.this);
                    try {
                        Thread.sleep(timeout);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
