package com.kondratyonok.kondratyonok;

import android.graphics.drawable.Drawable;

/**
 * Created by NKondratyonok on 04.02.18.
 */

public class Entry {
    public Drawable icon;
    public String name;
    public String packageName;
    public Long updateTime;
    public Integer launched;

    public Entry(Drawable icon, String name, String packageName, long updateTime) {
        this.icon = icon;
        this.name = name;
        this.packageName = packageName;
        this.updateTime = updateTime;

        this.launched = Database.get(packageName);
    }
}
