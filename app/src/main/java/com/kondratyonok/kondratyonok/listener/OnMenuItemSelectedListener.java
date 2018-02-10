package com.kondratyonok.kondratyonok.listener;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.view.MenuItem;
import android.widget.Toast;

import com.kondratyonok.kondratyonok.Entry;
import com.kondratyonok.kondratyonok.R;
import com.kondratyonok.kondratyonok.Utils;
import com.kondratyonok.kondratyonok.activity.ApplicationsActivity;
import com.kondratyonok.kondratyonok.fragment.main.GridFragment;
import com.kondratyonok.kondratyonok.fragment.main.LinearFragment;
import com.kondratyonok.kondratyonok.settings.Layout;
import com.kondratyonok.kondratyonok.settings.LayoutManagerType;
import com.kondratyonok.kondratyonok.settings.SettingsActivity;
import com.yandex.metrica.YandexMetrica;

import java.util.List;

/**
 * Created by NKondratyonok on 04.02.18.
 */

public class OnMenuItemSelectedListener implements NavigationView.OnNavigationItemSelectedListener {
    private final ApplicationsActivity activity;

    public OnMenuItemSelectedListener(ApplicationsActivity activity) {
        this.activity = activity;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        final Intent intent;
        switch (item.getItemId()) {
            case R.id.nav_settings: {
                YandexMetrica.reportEvent("Drawer", "{\"action\":\"settings\"}");
                intent = new Intent();
                intent.setClass(activity, SettingsActivity.class);
                activity.startActivity(intent);
                break;
            }
            case R.id.nav_list: {
                YandexMetrica.reportEvent("Drawer", "{\"action\":\"list\"}");
                LayoutManagerType.setTemp(LayoutManagerType.LINEAR);
                activity.fragment = new LinearFragment();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_content, activity.fragment)
                        .commit();
                break;
            }
            case R.id.nav_grid: {
                YandexMetrica.reportEvent("Drawer", "{\"action\":\"grid\"}");
                LayoutManagerType.setTemp(LayoutManagerType.GRID);
                activity.fragment = new GridFragment();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_content, activity.fragment)
                        .commit();
                break;
            }
            default:
                Toast.makeText(activity, "Error!!!", Toast.LENGTH_LONG).show();
                break;
        }
        activity.mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
