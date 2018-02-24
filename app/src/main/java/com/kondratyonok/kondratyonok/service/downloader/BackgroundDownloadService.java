package com.kondratyonok.kondratyonok.service.downloader;

import android.app.Activity;
import android.app.LoaderManager;
import android.app.Service;
import android.content.Intent;
import android.content.Loader;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.kondratyonok.kondratyonok.R;
import com.kondratyonok.kondratyonok.helper.BackgroundReadyNotifiable;
import com.kondratyonok.kondratyonok.model.Album;
import com.kondratyonok.kondratyonok.utils.FileUtils;

public class BackgroundDownloadService extends Service implements LoaderManager.LoaderCallbacks<Bitmap> {
    public class LocalBinder extends Binder {
        public BackgroundDownloadService getService() {
            return BackgroundDownloadService.this;
        }
    }

    private final IBinder mBinder = new LocalBinder();
    private Thread loader;
    private int timeout;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LoaderService", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("LoaderService", "onBind");
        return mBinder;
    }

    private Activity activity;
    private BackgroundReadyNotifiable nofiable;

    @Override
    public void onCreate() {
        Log.i("Service", "create");
        restartLoader();
    }

    public void restartLoader() {
        if (this.loader != null) {
            this.loader.interrupt();
        }
        this.loader = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("loader", "start");
                cur = -1;
                photos = new YaDownloader().fetchAlbum();
                int i = 0;
                while (true) {
                    if ((photos == null)||(photos.getSize() == 0)) {
                        photos = new YaDownloader().fetchAlbum();
                        try {
                            Thread.sleep(BackgroundDownloadService.this.timeout);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        continue;
                    }
                    if (activity != null) {
                        activity.getLoaderManager().initLoader(i++, null, BackgroundDownloadService.this);
                    }
                    try {
                        Thread.sleep(BackgroundDownloadService.this.timeout);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        this.loader.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.loader.interrupt();
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
        return new PhotoByURLLoader(this, photos.getPhotos().get(cur).getImageUrl());
    }

    @Override
    public void onLoadFinished(Loader<Bitmap> loader, Bitmap data) {
        if (data != null) {
            FileUtils.ImageSaver.getInstance().saveImage(getApplicationContext(), data, getResources().getString(R.string.background_file));
            if (this.nofiable != null) {
                this.nofiable.notifyBackgroundReady();
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Bitmap> loader) {}

    private Album photos = null;
    private int cur = -1;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setReadyNotifiable(BackgroundReadyNotifiable notifiable) {
        this.nofiable = notifiable;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
