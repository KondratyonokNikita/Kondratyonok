package com.kondratyonok.kondratyonok.listener;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.widget.Toast;

import com.kondratyonok.kondratyonok.R;
import com.kondratyonok.kondratyonok.settings.LayoutManagerType;
import com.kondratyonok.kondratyonok.settings.SettingsActivity;

/**
 * Created by NKondratyonok on 04.02.18.
 */

public class OnMenuItemSelectedListener implements NavigationView.OnNavigationItemSelectedListener {
    private final Activity activity;

    public OnMenuItemSelectedListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        final Intent intent;
        switch (item.getItemId()) {
            case R.id.nav_settings:
                intent = new Intent();
                intent.setClass(activity, SettingsActivity.class);
                activity.startActivity(intent);
                break;
            case R.id.nav_list:
                SettingsActivity.setLayoutManagerType(LayoutManagerType.LINEAR, activity);
                activity.recreate();
                break;
            case R.id.nav_grid:
                SettingsActivity.setLayoutManagerType(LayoutManagerType.GRID, activity);
                activity.recreate();
                break;
            default:
                Toast.makeText(activity, "Error!!!", Toast.LENGTH_LONG).show();
                break;
        }
//        activity.mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
