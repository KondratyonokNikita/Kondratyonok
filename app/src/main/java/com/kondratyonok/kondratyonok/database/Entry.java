package com.kondratyonok.kondratyonok.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

/**
 * Created by NKondratyonok on 04.02.18.
 */

@Entity(tableName = "applications")
public class Entry {
    @ColumnInfo(name = "package_name")
    @PrimaryKey
    @NonNull
    public String packageName;

    @Ignore
    public Drawable icon;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "update_time")
    public Long updateTime;

    @ColumnInfo(name = "launched")
    public Integer launched;

    @ColumnInfo(name = "desktop_position")
    public Integer desktopPosition;
}
