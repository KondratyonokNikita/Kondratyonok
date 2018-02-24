package com.kondratyonok.kondratyonok.utils;

import android.app.Application;
import android.content.pm.PackageManager;

import com.kondratyonok.kondratyonok.model.Entry;

/**
 * Created by NKondratyonok on 01.02.18.
 */

public class PackageUtils {
    public static String getPackageFromDataString(String uri) {
        return uri.substring("package:".length());
    }

    public static Entry getEntryFromPackageName(String packageName, Application application) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = application.getPackageManager();
        String name = (String) packageManager.getApplicationLabel(
                packageManager.getApplicationInfo(
                        packageName,
                        PackageManager.GET_META_DATA));
        long updateTime = packageManager.getPackageInfo(packageName, 0).lastUpdateTime;
        Entry entry = new Entry();
        entry.name = name;
        entry.packageName = packageName;
        entry.updateTime = updateTime;
        entry.desktopPosition = -1;
        entry.launched = 0;
        return entry;
    }
}
