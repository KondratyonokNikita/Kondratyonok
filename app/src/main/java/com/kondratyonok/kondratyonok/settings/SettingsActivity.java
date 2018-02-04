package com.kondratyonok.kondratyonok.settings;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;

import com.kondratyonok.kondratyonok.Entry;
import com.kondratyonok.kondratyonok.R;

import java.util.Comparator;
import java.util.List;

public class SettingsActivity extends PreferenceActivity {

    public static final String KEY_THEME = "theme";
    public static final String KEY_LAYOUT = "layout";
    public static final String KEY_SORTING_METHOD = "sorting_method";
    public static final String KEY_LAYOUT_MANAGER_TYPE = "layout_manager_type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    public static boolean hasAllSettings(Activity activity) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        boolean good;
        good = preferences.contains(SettingsActivity.KEY_LAYOUT);
        good &= preferences.contains(SettingsActivity.KEY_THEME);
        return good;
    }

    public static RecyclerView.LayoutManager getLayoutManager(Activity activity) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        String code = preferences.getString(SettingsActivity.KEY_LAYOUT_MANAGER_TYPE, LayoutManagerType.DEFAULT);
        return LayoutManagerType.getLayoutManager(code, activity);
    }

    public static RecyclerView.Adapter<RecyclerView.ViewHolder> getAdapter(Activity activity, List<Entry> data) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        String code = preferences.getString(SettingsActivity.KEY_LAYOUT_MANAGER_TYPE, LayoutManagerType.DEFAULT);
        return LayoutManagerType.getAdapter(code, data);
    }

    public static RecyclerView.ItemDecoration getItemDecorator(Activity activity) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        String code = preferences.getString(SettingsActivity.KEY_LAYOUT_MANAGER_TYPE, LayoutManagerType.DEFAULT);
        return LayoutManagerType.getItemDecorator(code, activity);
    }

    public static void setLayoutManagerType(String type, Activity activity) {
        SharedPreferences sPref = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SettingsActivity.KEY_LAYOUT_MANAGER_TYPE, type);
        ed.commit();
    }

    public static Comparator<Object> getSortingMethod(Activity activity) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        String code = preferences.getString(SettingsActivity.KEY_SORTING_METHOD, SortingMethod.DEFAULT);
        return SortingMethod.getMethod(code);
    }

    public static int getApplicationTheme(Activity activity) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        String code = preferences.getString(SettingsActivity.KEY_THEME, Theme.DEFAULT);
        return Theme.getTheme(code);
    }

    public static void setApplicationTheme(String theme, Activity activity) {
        SharedPreferences sPref = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SettingsActivity.KEY_THEME, theme);
        ed.apply();
    }

    public static boolean hasApplicationTheme(Activity activity) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        return preferences.contains(SettingsActivity.KEY_THEME);
    }

    public static int getLayoutColumnsId(Activity activity) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        String code = preferences.getString(SettingsActivity.KEY_LAYOUT, Layout.DEFAULT);
        return Layout.getColumnsId(code);
    }

    public static void setLayout(String layout, Activity activity) {
        SharedPreferences sPref = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SettingsActivity.KEY_LAYOUT, layout);
        ed.apply();
    }

    public static boolean hasLayout(Activity activity) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        return preferences.contains(SettingsActivity.KEY_LAYOUT);
    }
}
