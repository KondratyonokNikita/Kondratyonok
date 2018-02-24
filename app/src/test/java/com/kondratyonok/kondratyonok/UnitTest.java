package com.kondratyonok.kondratyonok;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.kondratyonok.kondratyonok.greeting.GreetingActivity;
import com.kondratyonok.kondratyonok.settings.Layout;
import com.kondratyonok.kondratyonok.settings.SettingsActivity;
import com.kondratyonok.kondratyonok.settings.Theme;
import com.yandex.metrica.YandexMetrica;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static com.kondratyonok.kondratyonok.settings.Layout.DENSE;
import static com.kondratyonok.kondratyonok.settings.Layout.STANDARD;
import static org.junit.Assert.assertEquals;

/**
 * Created by NKondratyonok on 24.02.18.
 */

@RunWith(RobolectricTestRunner.class)
public class UnitTest {
    private final Context context = Robolectric.buildActivity(GreetingActivity.class).get();

    @Test
    public void hasAllSettingsTest() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean good;
        good = preferences.contains(SettingsActivity.KEY_LAYOUT);
        good &= preferences.contains(SettingsActivity.KEY_THEME);

        assertEquals(good, SettingsActivity.hasAllSettings(context));
    }

    @Test
    public void getApplicationThemeTest() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String code = preferences.getString(SettingsActivity.KEY_THEME, Theme.DEFAULT);
        int result = -1;
        switch (code) {
            case "0":
                result = R.style.AppThemeLight;
                break;
            case "1":
                result = R.style.AppThemeDark;
                break;
        }
        assertEquals(result, SettingsActivity.getApplicationTheme(context));
    }

    @Test
    public void setApplicationThemeTest() {
        SettingsActivity.setApplicationTheme(Theme.DEFAULT, context);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        assertEquals(true, preferences.contains(SettingsActivity.KEY_THEME));
        assertEquals(Theme.DEFAULT, preferences.getString(SettingsActivity.KEY_THEME, "default"));
    }

    @Test
    public void hasApplicationThemeTest() {
        SettingsActivity.setApplicationTheme(Theme.DEFAULT, context);
        assertEquals(true, SettingsActivity.hasApplicationTheme(context));
    }

    @Test
    public void getLayoutColumnsIdTest() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String code = preferences.getString(SettingsActivity.KEY_LAYOUT, Layout.DEFAULT);
        int result = -1;
        switch (code) {
            case STANDARD:
                result = R.integer.span_count;
                break;
            case DENSE:
                result = R.integer.span_count_dense;
                break;
        }
        assertEquals(result, SettingsActivity.getLayoutColumnsId(context));
    }

    @Test
    public void setLayoutTest() {
        SettingsActivity.setLayout(Layout.DEFAULT, context);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        assertEquals(true, preferences.contains(SettingsActivity.KEY_LAYOUT));
        assertEquals(Layout.DEFAULT, preferences.getString(SettingsActivity.KEY_LAYOUT, "default"));
    }

    @Test
    public void hasLayoutTest() {
        SettingsActivity.setLayout(Layout.DEFAULT, context);
        assertEquals(true, SettingsActivity.hasLayout(context));
    }

    @Test
    public void isNeedWelcomePageTest() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean need = preferences.getBoolean(SettingsActivity.KEY_NEED_WELCOME_PAGE, true);
        boolean result = need || !SettingsActivity.hasAllSettings(context);
        assertEquals(result, SettingsActivity.isNeedWelcomePage(context));
    }

    @Test
    public void setNeedWelcomePageTest() {
        SettingsActivity.setNeedWelcomePage(context, true);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        assertEquals(true, preferences.contains(SettingsActivity.KEY_NEED_WELCOME_PAGE));
        assertEquals(true, preferences.getBoolean(SettingsActivity.KEY_NEED_WELCOME_PAGE, false));
    }

    @Test
    public void isNeedFavorites() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean result = preferences.getBoolean(SettingsActivity.KEY_NEED_FAVORITES, true);
        assertEquals(result, SettingsActivity.isNeedFavorites(context));
    }

}
