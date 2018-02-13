package com.kondratyonok.kondratyonok.database;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by NKondratyonok on 13.02.18.
 */

public class EntryDbHolder {

    private static final EntryDbHolder ourInstance = new EntryDbHolder();

    public static EntryDbHolder getInstance() {
        return ourInstance;
    }

    private static final String DATABASE_NAME = "main";

    private volatile EntryDb mEntryDb;

    private EntryDbHolder() {
    }

    public EntryDb getDb(@NonNull Context context) {
        if (mEntryDb == null) {
            synchronized (this) {
                if (mEntryDb == null) {
                    mEntryDb =
                            Room.databaseBuilder(context, EntryDb.class, DATABASE_NAME).build();
                }
            }
        }
        return mEntryDb;
    }
}
