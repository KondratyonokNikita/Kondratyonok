package com.kondratyonok.kondratyonok.settings;

import com.kondratyonok.kondratyonok.model.Entry;

import java.util.Comparator;

/**
 * Created by NKondratyonok on 01.02.18.
 */

public class SortingMethod {
    public static final String NO_SORT = "0";
    public static final String INSTALLATION_DATE = "1";
    public static final String ALPHABETICALLY_AZ = "2";
    public static final String ALPHABETICALLY_ZA = "3";
    public static final String MOST_FREQUENT = "4";

    public static final String DEFAULT = NO_SORT;

    public static Comparator<Object> getMethod(String code) {
        switch (code) {
            case SortingMethod.NO_SORT:
                return new Comparator<Object>() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        return 0;
                    }
                };

            case SortingMethod.INSTALLATION_DATE:
                return new Comparator<Object>() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        Entry first = (Entry) o1;
                        Entry second = (Entry) o2;
                        return second.updateTime.compareTo(first.updateTime);
                    }
                };

            case SortingMethod.ALPHABETICALLY_AZ:
                return new Comparator<Object>() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        Entry first = (Entry) o1;
                        Entry second = (Entry) o2;
                        return first.name.compareToIgnoreCase(second.name);
                    }
                };

            case SortingMethod.ALPHABETICALLY_ZA:
                return new Comparator<Object>() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        Entry first = (Entry) o1;
                        Entry second = (Entry) o2;
                        return second.name.compareToIgnoreCase(first.name);
                    }
                };

            case SortingMethod.MOST_FREQUENT:
                return new Comparator<Object>() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        Entry first = (Entry) o1;
                        Entry second = (Entry) o2;
                        return second.launched.compareTo(first.launched);
                    }
                };

            default:
                return getMethod(SortingMethod.DEFAULT);
        }
    }

    public static String getName(String code) {
        switch (code) {
            case SortingMethod.NO_SORT:
                return "no sort";

            case SortingMethod.INSTALLATION_DATE:
                return "installation date";

            case SortingMethod.ALPHABETICALLY_AZ:
                return "alphabetically az";

            case SortingMethod.ALPHABETICALLY_ZA:
                return "alphabetically za";

            case SortingMethod.MOST_FREQUENT:
                return "most frequent";

            default:
                return getName(SortingMethod.DEFAULT);
        }
    }
}
