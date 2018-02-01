package com.kondratyonok.kondratyonok;

/**
 * Created by NKondratyonok on 01.02.18.
 */

public class Utils {
    public static String getPackageFromDataString(String uri) {
        return uri.substring("package:".length());
    }
}
