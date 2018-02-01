package com.kondratyonok.kondratyonok.settings;

import com.kondratyonok.kondratyonok.R;

/**
 * Created by NKondratyonok on 30.01.18.
 */

public class Layout {
    public static final String STANDARD = "0";
    public static final String DENSE = "1";
    public static final String DEFAULT = STANDARD;

    static int getColumnsId(String code) {
        switch (code) {
            case STANDARD: return R.integer.span_count;
            case DENSE: return R.integer.span_count_dense;
            default: return getColumnsId(Layout.DEFAULT);
        }
    }
}
