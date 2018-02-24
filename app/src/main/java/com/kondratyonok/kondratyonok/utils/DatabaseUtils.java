package com.kondratyonok.kondratyonok.utils;

import android.app.Application;
import android.util.SparseArray;

import com.kondratyonok.kondratyonok.database.EntryDbHolder;
import com.kondratyonok.kondratyonok.model.Entry;

/**
 * Created by NKondratyonok on 23.02.18.
 */

public class DatabaseUtils {
    public static void saveSparseArray(SparseArray<Entry> data, Application application) {
        for (int i = 0; i < data.size(); ++i) {
            int key = data.keyAt(i);
            Entry entry = EntryDbHolder.getInstance().getDb(application.getApplicationContext()).calculationResultDao().getEntry(data.get(key).packageName);
            entry.desktopPosition = data.get(key).desktopPosition;
            EntryDbHolder.getInstance().getDb(application.getApplicationContext()).calculationResultDao().update(entry);
        }
    }
}
