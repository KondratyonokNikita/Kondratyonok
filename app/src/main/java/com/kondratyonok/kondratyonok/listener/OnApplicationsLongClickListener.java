package com.kondratyonok.kondratyonok.listener;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.kondratyonok.kondratyonok.database.Entry;
import com.kondratyonok.kondratyonok.R;
import com.kondratyonok.kondratyonok.database.EntryDbHolder;
import com.yandex.metrica.YandexMetrica;

import java.util.List;

/**
 * Created by NKondratyonok on 04.02.18.
 */

public class OnApplicationsLongClickListener implements View.OnLongClickListener {

    private final Entry data;
    private final Activity activity;

    public OnApplicationsLongClickListener(Entry data, Activity activity) {
        this.data = data;
        this.activity = activity;
    }

    @Override
    public boolean onLongClick(final View view) {
        YandexMetrica.reportEvent("Popup", "{\"action\":\"opened\"}");
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        popup.inflate(R.menu.context_menu);
        popup.getMenu().findItem(R.id.nav_times).setTitle("Launched: " + data.launched);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_delete: {
                        YandexMetrica.reportEvent("Popup", "{\"action\":\"delete\"}");
                        Intent intent = new Intent(Intent.ACTION_DELETE);
                        intent.setData(Uri.parse("package:" + data.packageName));
                        view.getContext().startActivity(intent);
                        break;
                    }
                    case R.id.nav_to_desktop: {
                        YandexMetrica.reportEvent("Popup", "{\"action\":\"to_desktop\"}");
                        (new Thread(new Runnable() {
                            @Override
                            public void run() {
                                List<Entry> entries = EntryDbHolder.getInstance().getDb(activity.getApplicationContext()).calculationResultDao().loadAll();
                                boolean free[] = new boolean[20];
                                for (int i = 0; i < 20; ++i) {
                                    free[i] = true;
                                }
                                for (Entry entry: entries) {
                                    if (entry.desktopPosition != -1) {
                                        free[entry.desktopPosition] = false;
                                    }
                                }
                                int place = -1;
                                for (int i = 0; i < 20; ++i) {
                                    if (free[i]) {
                                        place = i;
                                        break;
                                    }
                                }
                                Entry entry = EntryDbHolder.getInstance().getDb(activity.getApplicationContext()).calculationResultDao().getEntry(data.packageName);
                                entry.desktopPosition = place;
                                EntryDbHolder.getInstance().getDb(activity.getApplicationContext()).calculationResultDao().update(entry);                            }
                        })).start();
                        break;
                    }
                    case R.id.nav_info: {
                        YandexMetrica.reportEvent("Popup", "{\"action\":\"info\"}");
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", data.packageName, null);
                        intent.setData(uri);
                        view.getContext().startActivity(intent);
                        break;
                    }
                    default:
                        break;
                }
                return false;
            }
        });
        popup.show();
        return false;
    }
}
