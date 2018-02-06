package com.kondratyonok.kondratyonok.listener;

import android.content.Intent;
import android.view.View;

import com.kondratyonok.kondratyonok.Database;
import com.kondratyonok.kondratyonok.Entry;

import java.util.List;

/**
 * Created by NKondratyonok on 04.02.18.
 */

public class OnApplicationClickListener implements View.OnClickListener {
    private Entry data;

    public OnApplicationClickListener(Entry data) {
        this.data = data;
    }

    @Override
    public void onClick(View v) {
        data.launched++;
        Intent launchIntent = v.getContext().getPackageManager().getLaunchIntentForPackage(data.packageName);
        if (launchIntent != null) {
            v.getContext().startActivity(launchIntent);
        }
        Database.insertOrUpdate(data);
    }
}
