package com.kondratyonok.kondratyonok.listener;

import android.app.Activity;
import android.support.v4.view.ViewPager;

import com.kondratyonok.kondratyonok.R;
import com.kondratyonok.kondratyonok.launcher.ApplicationsActivity;

/**
 * Created by NKondratyonok on 12.02.18.
 */

public class OnPageChangeListener extends ViewPager.SimpleOnPageChangeListener {
    private final Activity activity;

    public OnPageChangeListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onPageSelected(int position) {
        super.onPageSelected(position);
        ((ApplicationsActivity)activity).getNavigationView().getMenu().findItem(R.id.nav_applications).setChecked(false);
        ((ApplicationsActivity)activity).getNavigationView().getMenu().findItem(R.id.nav_desktop).setChecked(false);
        switch (position) {
            case 0:
                ((ApplicationsActivity)activity).getNavigationView().getMenu().findItem(R.id.nav_desktop).setChecked(true);
                break;
            case 1:
                ((ApplicationsActivity)activity).getNavigationView().getMenu().findItem(R.id.nav_applications).setChecked(true);
                break;
            default:
        }
    }
}
