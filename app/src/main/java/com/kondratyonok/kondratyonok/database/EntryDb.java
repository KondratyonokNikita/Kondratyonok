package com.kondratyonok.kondratyonok.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.kondratyonok.kondratyonok.model.Entry;

/**
 * Created by NKondratyonok on 13.02.18.
 */

@Database(entities = {Entry.class}, version = 1)
public abstract class EntryDb extends RoomDatabase {
    public abstract EntryDao calculationResultDao();
}
