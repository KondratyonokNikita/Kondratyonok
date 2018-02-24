package com.kondratyonok.kondratyonok.listener;

import android.app.Application;
import android.content.Intent;
import android.view.View;

import com.kondratyonok.kondratyonok.model.Entry;
import com.kondratyonok.kondratyonok.database.EntryDbHolder;
import com.yandex.metrica.YandexMetrica;

/**
 * Created by NKondratyonok on 04.02.18.
 */

public class OnApplicationClickListener implements View.OnClickListener {
    private String packageName;
    private Application application;

    public OnApplicationClickListener(String packagename, Application application) {
        this.packageName = packagename;
        this.application = application;
    }

    @Override
    public void onClick(View v) {
        YandexMetrica.reportEvent("Application launched");
        (new Thread(new Runnable() {
            @Override
            public void run() {
                Entry entry = EntryDbHolder.getInstance().getDb(application.getApplicationContext()).calculationResultDao().getEntry(packageName);
                entry.launched++;
                EntryDbHolder.getInstance().getDb(application.getApplicationContext()).calculationResultDao().update(entry);
            }
        })).start();
        Intent launchIntent = v.getContext().getPackageManager().getLaunchIntentForPackage(this.packageName);
        if (launchIntent != null) {
            v.getContext().startActivity(launchIntent);
        }
    }
}
