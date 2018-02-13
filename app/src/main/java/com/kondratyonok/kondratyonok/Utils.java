package com.kondratyonok.kondratyonok;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteConstraintException;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.kondratyonok.kondratyonok.database.Entry;
import com.kondratyonok.kondratyonok.database.EntryDbHolder;

import java.util.List;

/**
 * Created by NKondratyonok on 01.02.18.
 */

public class Utils {
    public static String getPackageFromDataString(String uri) {
        return uri.substring("package:".length());
    }

    public static Entry getEntryFromPackageName(String packageName, Application application) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = application.getPackageManager();
        Drawable icon = application.getPackageManager().getApplicationIcon(packageName);
        String name = (String) packageManager.getApplicationLabel(
                packageManager.getApplicationInfo(
                        packageName,
                        PackageManager.GET_META_DATA));
        long updateTime = packageManager.getPackageInfo(packageName, 0).lastUpdateTime;
        Entry entry = new Entry();
        entry.icon = icon;
        entry.name = name;
        entry.packageName = packageName;
        entry.updateTime = updateTime;
        entry.desktopPosition = -1;
        entry.launched = 0;
        return entry;
    }

    public static void loadEntriesList(Application application) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = application.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ApplicationInfo> app = packageManager.getInstalledApplications(0);
        for (ApplicationInfo applicationInfo : app) {
            try {
                if (packageManager.getLaunchIntentForPackage(applicationInfo.packageName) != null) {
                    if (!applicationInfo.packageName.equals(application.getPackageName())) {
                        Entry entry = Utils.getEntryFromPackageName(applicationInfo.packageName, application);
                        try {
                            EntryDbHolder.getInstance().getDb(application.getApplicationContext()).calculationResultDao().insert(entry);
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
