package com.kondratyonok.kondratyonok.utils;

import android.util.Log;

import com.yandex.metrica.YandexMetrica;

/**
 * Created by NKondratyonok on 24.02.18.
 */

public class CommonUtils {
    public static final String ARG_SECTION_NUMBER = "section_number";

    public static void log(String title, String subtitle) {
        try {
            YandexMetrica.reportEvent(title, subtitle);
            Log.i(title, subtitle);
        } catch (Exception e) {
        }
    }
}
