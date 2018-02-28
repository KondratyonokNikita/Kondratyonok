package com.kondratyonok.kondratyonok;

import android.app.Application;
import android.util.Log;

import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.push.YandexMetricaPush;

/**
 * Created by NKondratyonok on 10.02.18.
 */

public class KondrApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("APPLICATION", "onCreate");
        YandexMetrica.activate(getApplicationContext(), getResources().getString(R.string.app_matrica_api));
        YandexMetrica.enableActivityAutoTracking(this);
        YandexMetrica.setLogEnabled();
        YandexMetrica.setCollectInstalledApps(true);
        YandexMetrica.setSessionTimeout(5);
        YandexMetricaPush.init(this);

        final Thread.UncaughtExceptionHandler mAndroidCrashHandler = Thread.getDefaultUncaughtExceptionHandler();

        final Thread.UncaughtExceptionHandler mUncaughtExceptionHandler = new Thread.UncaughtExceptionHandler() {

            @Override
            public void uncaughtException(Thread thread, Throwable exception) {
                try {
                    //Put your logic
                    YandexMetrica.reportError(thread.getName(), exception);
                } finally {
                    // Give to the system
                    if (null != mAndroidCrashHandler) {
                        mAndroidCrashHandler.uncaughtException(thread, exception);
                    }
                }
            }

        };

        Thread.setDefaultUncaughtExceptionHandler(mUncaughtExceptionHandler);
    }
}
