package com.kondratyonok.kondratyonok;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kondratyonok.kondratyonok.launcher.LauncherAdapter;
import com.kondratyonok.kondratyonok.launcher.OffsetItemDecoration;
import com.kondratyonok.kondratyonok.settings.Layout;
import com.kondratyonok.kondratyonok.settings.Settings;
import com.kondratyonok.kondratyonok.settings.Theme;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Settings.getTheme());
        setContentView(R.layout.activity_launcher);
        createGridLayout();
    }

    private void createGridLayout() {
        final RecyclerView recyclerView = findViewById(R.id.louncher_content);
        recyclerView.setHasFixedSize(true);
        final int offset = getResources().getDimensionPixelSize(R.dimen.item_offset);
        recyclerView.addItemDecoration(new OffsetItemDecoration(offset));

        final int spanCount;
        switch (Settings.getLayout()) {
            case Layout.DENSE:
                spanCount = getResources().getInteger(R.integer.span_count_dense);
                break;
            default:
                spanCount = getResources().getInteger(R.integer.span_count);
                break;
        }
        final GridLayoutManager layoutManager = new GridLayoutManager(this, spanCount);
        recyclerView.setLayoutManager(layoutManager);

        final List<Integer> data = generateData();
        final LauncherAdapter launcherAdapter = new LauncherAdapter(data);
        recyclerView.setAdapter(launcherAdapter);
    }

    @NonNull
    private List<Integer> generateData() {
        final List<Integer> colors = new ArrayList<>();
        final Random rnd = new Random();
        for (int i = 0; i < 1000; i++) {
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            colors.add(color);
        }

        return colors;
    }

}
