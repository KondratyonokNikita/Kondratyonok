package com.kondratyonok.kondratyonok.settings;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.kondratyonok.kondratyonok.R;
import com.yandex.metrica.YandexMetrica;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String KEY_THEME = "theme";
    public static final String KEY_LAYOUT = "layout";
    public static final String KEY_SORTING_METHOD = "sorting_method";
    public static final String KEY_LAYOUT_MANAGER_TYPE = "layout_manager_type";
    public static final String KEY_NEED_WELCOME_PAGE = "need_welcome_page";
    public static final String KEY_NEED_FAVORITES = "need_favorites";
    public static final String KEY_SILENT_PUSH_INFO = "silent_push_info";

    static boolean settingsChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("SETTINGS", "onDestroy");
    }

    public static boolean hasAllSettings(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean good;
        good = preferences.contains(SettingsActivity.KEY_LAYOUT);
        good &= preferences.contains(SettingsActivity.KEY_THEME);
        return good;
    }

    public static Comparator<Object> getSortingMethod(Context activity) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        String code = preferences.getString(SettingsActivity.KEY_SORTING_METHOD, SortingMethod.DEFAULT);
        return SortingMethod.getMethod(code);
    }

    public static int getApplicationTheme(Context activity) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        String code = preferences.getString(SettingsActivity.KEY_THEME, Theme.DEFAULT);
        return Theme.getTheme(code);
    }

    public static void setApplicationTheme(String theme, Context activity) {
        SharedPreferences sPref = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SettingsActivity.KEY_THEME, theme);
        ed.apply();
    }

    public static boolean hasApplicationTheme(Context activity) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        return preferences.contains(SettingsActivity.KEY_THEME);
    }

    public static boolean isApplicationSettingsChanged() {
        if (SettingsActivity.settingsChanged) {
            SettingsActivity.settingsChanged = false;
            return true;
        }
        return false;
    }

    public static int getLayoutColumnsId(Context activity) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        String code = preferences.getString(SettingsActivity.KEY_LAYOUT, Layout.DEFAULT);
        return Layout.getColumnsId(code);
    }

    public static void setLayout(String layout, Context activity) {
        SharedPreferences sPref = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SettingsActivity.KEY_LAYOUT, layout);
        ed.apply();
    }

    public static boolean hasLayout(Context activity) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        return preferences.contains(SettingsActivity.KEY_LAYOUT);
    }


    public static String getSilentPushInfo(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(SettingsActivity.KEY_SILENT_PUSH_INFO, null);
    }

    public static void setSilentPushInfo(Context context, String info) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor ed = preferences.edit();
        ed.putString(SettingsActivity.KEY_SILENT_PUSH_INFO, info);
        ed.apply();
    }

    public static boolean isNeedWelcomePage(Context activity) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        boolean need = preferences.getBoolean(SettingsActivity.KEY_NEED_WELCOME_PAGE, true);
        return need || !SettingsActivity.hasAllSettings(activity);
    }

    public static void setNeedWelcomePage(Context activity, boolean need) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor ed = preferences.edit();
        ed.putBoolean(SettingsActivity.KEY_NEED_WELCOME_PAGE, need);
        ed.apply();
    }

    public static void setSettingsChanged() {
        SettingsActivity.settingsChanged = true;
    }


    public static boolean isNeedFavorites(Context activity) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        return preferences.getBoolean(SettingsActivity.KEY_NEED_FAVORITES, true);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.i("CHANGED", key);
        Map<String, Object> all = new HashMap<>();
        all.put(KEY_LAYOUT_MANAGER_TYPE,
                LayoutManagerType.getName(
                        sharedPreferences.getString(
                                KEY_LAYOUT_MANAGER_TYPE,
                                LayoutManagerType.DEFAULT)));
        all.put(KEY_THEME,
                Theme.getName(
                        sharedPreferences.getString(
                                KEY_THEME,
                                Theme.DEFAULT)));
        all.put(KEY_LAYOUT,
                Layout.getName(
                        sharedPreferences.getString(
                                KEY_LAYOUT,
                                Layout.DEFAULT)));
        all.put(KEY_SORTING_METHOD,
                SortingMethod.getName(
                        sharedPreferences.getString(
                                KEY_SORTING_METHOD,
                                SortingMethod.DEFAULT)));
        YandexMetrica.reportEvent("Settings changed", all);
        SettingsActivity.setSettingsChanged();
    }
}
