package com.kondratyonok.kondratyonok.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.kondratyonok.kondratyonok.model.Entry;

import java.util.List;

/**
 * Created by NKondratyonok on 13.02.18.
 */

@Dao
public interface EntryDao {
    @Insert
    public void insert(Entry calculationResult);

    @Delete
    public void delete(Entry entry);

    @Update
    public int update(Entry entry);

    @Query("Select * from applications where package_name = :packageName")
    public Entry getEntry(String packageName);

    @Query("Select * from applications")
    public List<Entry> loadAll();

    @Query("Select * from applications")
    public LiveData<List<Entry>> loadCalculationResultSync();
}
