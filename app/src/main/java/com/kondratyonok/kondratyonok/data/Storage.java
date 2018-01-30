package com.kondratyonok.kondratyonok.data;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by NKondratyonok on 30.01.18.
 */

public class Storage {
    private List<Entry> storage;

    public Storage() {
        storage = new ArrayList<>();
    }

    public void add(Entry value) {
        storage.add(value);
    }

    public void addFront(Entry value) {
        storage.add(0, value);
    }

    public Entry get(int position) {
        return storage.get(position);
    }

    public void remove(int position) {
        storage.remove(position);
    }

    public int size() {
        return storage.size();
    }

    public void generateData() {
        if (storage.size() != 0) {
            return;
        }
        final Random rnd = new Random();
        for (int i = 0; i < 1000; i++) {
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            storage.add(new Entry(color, String.valueOf(color)));
        }
    }
}
