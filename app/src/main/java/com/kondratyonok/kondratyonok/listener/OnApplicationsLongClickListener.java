package com.kondratyonok.kondratyonok.listener;

import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.kondratyonok.kondratyonok.Entry;
import com.kondratyonok.kondratyonok.R;
import com.yandex.metrica.YandexMetrica;

/**
 * Created by NKondratyonok on 04.02.18.
 */

public class OnApplicationsLongClickListener implements View.OnLongClickListener {

    private final Entry data;

    public OnApplicationsLongClickListener(Entry data) {
        this.data = data;
    }

    @Override
    public boolean onLongClick(final View view) {
        YandexMetrica.reportEvent("Popup", "{\"action\":\"opened\"}");
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        popup.inflate(R.menu.context_menu);
        popup.getMenu().findItem(R.id.nav_times).setTitle("Launched: " + data.launched);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_delete: {
                        YandexMetrica.reportEvent("Popup", "{\"action\":\"delete\"}");
                        Intent intent = new Intent(Intent.ACTION_DELETE);
                        intent.setData(Uri.parse("package:" + data.packageName));
                        view.getContext().startActivity(intent);
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
