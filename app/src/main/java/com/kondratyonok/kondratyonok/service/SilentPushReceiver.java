package com.kondratyonok.kondratyonok.service;

/**
 * Created by NKondratyonok on 27.02.18.
 */

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.kondratyonok.kondratyonok.settings.SettingsActivity;
import com.yandex.metrica.push.YandexMetricaPush;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SilentPushReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //Извлечение данных из вашего push-уведомления
        String payload = intent.getStringExtra(YandexMetricaPush.EXTRA_PAYLOAD);
        @SuppressLint("SimpleDateFormat") String currentDateandTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        Log.i("YANDEX", payload);
        SettingsActivity.setSilentPushInfo(context, currentDateandTime + ": " + payload);
    }
}
