package com.kondratyonok.kondratyonok.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by NKondratyonok on 29.01.18.
 */

public class Settings {
    public static final String SETTINGS_FILE_NAME = "settings";

    public static final String THEME = "THEME";
    public static int theme = Theme.LIGHT;

    public static final String LAYOUT = "LAYOUT";
    public static int layout = Layout.STANDARD;

    public static boolean load(AppCompatActivity activity) {
        SharedPreferences settings;
        settings = activity.getSharedPreferences(SETTINGS_FILE_NAME, Context.MODE_PRIVATE);
        if (settings.contains(THEME)) {
            theme = settings.getInt(THEME, Theme.LIGHT);
        } else {
            return false;
        }
        if (settings.contains(LAYOUT)) {
            layout = settings.getInt(LAYOUT, Layout.STANDARD);
        } else {
            return false;
        }
        return true;
    }

    public static void save(AppCompatActivity activity) {
        SharedPreferences settings;
        settings = activity.getSharedPreferences(SETTINGS_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(THEME, theme);
        editor.putInt(LAYOUT, layout);
        editor.apply();
    }
}
