package com.kondratyonok.kondratyonok.settings;

import com.kondratyonok.kondratyonok.R;

/**
 * Created by NKondratyonok on 30.01.18.
 */

public class Theme {
    public static final String LIGHT = "0";
    public static final String DARK = "1";
    public static final String DEFAULT = LIGHT;

    static int getTheme(String code) {
        switch (code) {
            case "0":
                return R.style.AppThemeLight;
            case "1":
                return R.style.AppThemeDark;
            default:
                return getTheme(Theme.DEFAULT);
        }
    }

}
