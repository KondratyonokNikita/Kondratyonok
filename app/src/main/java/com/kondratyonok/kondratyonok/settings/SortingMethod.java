package com.kondratyonok.kondratyonok.settings;

import com.kondratyonok.kondratyonok.activity.ApplicationsActivity;

import java.util.Comparator;

/**
 * Created by NKondratyonok on 01.02.18.
 */

public class SortingMethod {
    public static final String NO_SORT = "0";
    public static final String INSTALLATION_DATE = "1";
    public static final String ALPHABETICALLYAZ = "2";
    public static final String ALPHABETICALLYZA = "3";

    public static final String DEFAULT = NO_SORT;

    public static Comparator<Object> getMethod(String  code) {
        switch (code) {
            case SortingMethod.NO_SORT: return new Comparator<Object>() {
                @Override
                public int compare(Object o1, Object o2) {
                    return 0;
                }
            };

            case SortingMethod.INSTALLATION_DATE: return new Comparator<Object>() {
                @Override
                public int compare(Object o1, Object o2) {
                    ApplicationsActivity.Entry first = (ApplicationsActivity.Entry) o1;
                    ApplicationsActivity.Entry second = (ApplicationsActivity.Entry) o2;
                    return first.updateTime.compareTo(second.updateTime);
                }
            };

            case SortingMethod.ALPHABETICALLYAZ: return new Comparator<Object>() {
                @Override
                public int compare(Object o1, Object o2) {
                    ApplicationsActivity.Entry first = (ApplicationsActivity.Entry) o1;
                    ApplicationsActivity.Entry second = (ApplicationsActivity.Entry) o2;
                    return first.name.compareToIgnoreCase(second.name);
                }
            };

            case SortingMethod.ALPHABETICALLYZA: return new Comparator<Object>() {
                @Override
                public int compare(Object o1, Object o2) {
                    ApplicationsActivity.Entry first = (ApplicationsActivity.Entry) o1;
                    ApplicationsActivity.Entry second = (ApplicationsActivity.Entry) o2;
                    return second.name.compareToIgnoreCase(first.name);
                }
            };

            default: return getMethod(SortingMethod.DEFAULT);
        }
    }
}
