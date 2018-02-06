package com.kondratyonok.kondratyonok;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.kondratyonok.kondratyonok.settings.SettingsActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by NKondratyonok on 01.02.18.
 */

public class Utils {
    private static List<Entry> data;

    public static String getPackageFromDataString(String uri) {
        return uri.substring("package:".length());
    }

    public static Entry getEntryFromPackageName(String packageName, Activity activity) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = activity.getPackageManager();
        Drawable icon = activity.getPackageManager().getApplicationIcon(packageName);
        String name = (String) packageManager.getApplicationLabel(
                packageManager.getApplicationInfo(
                        packageName,
                        PackageManager.GET_META_DATA));
        long updateTime = packageManager.getPackageInfo(packageName, 0).lastUpdateTime;
        return new Entry(icon, name, packageName, updateTime);
    }

    public static List<Entry> getEntriesList(Activity activity) {
        if (data == null) {
            List<Entry> new_data = new ArrayList<>();
            PackageManager packageManager = activity.getPackageManager();
            Intent intent = new Intent(Intent.ACTION_MAIN, null);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            List<ApplicationInfo> app = packageManager.getInstalledApplications(0);
            for (ApplicationInfo applicationInfo : app) {
                try {
                    if (packageManager.getLaunchIntentForPackage(applicationInfo.packageName) != null) {
                        if (!applicationInfo.packageName.equals(activity.getPackageName())) {
                            Entry entry = Utils.getEntryFromPackageName(applicationInfo.packageName, activity);
                            if (!new_data.contains(entry)) {
                                new_data.add(entry);
                            }
                        }
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
            Collections.sort(new_data, SettingsActivity.getSortingMethod(activity));
            data = new_data;
        }
        return data;
    }
}
