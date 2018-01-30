package com.kondratyonok.kondratyonok;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kondratyonok.kondratyonok.data.Entry;
import com.kondratyonok.kondratyonok.data.Storage;
import com.kondratyonok.kondratyonok.launcher.LauncherAdapter;
import com.kondratyonok.kondratyonok.launcher.OffsetItemDecoration;
import com.kondratyonok.kondratyonok.settings.Layout;
import com.kondratyonok.kondratyonok.settings.Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LauncherActivity extends AppCompatActivity {

    private final Storage data = new Storage();
    private LauncherAdapter launcherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Settings.getTheme());
        setContentView(R.layout.activity_launcher);
        createGridLayout();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Random rnd = new Random();
                int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                data.addFront(new Entry(color, String.valueOf(color)));
                launcherAdapter.notifyDataSetChanged();
                Snackbar.make(view, "Added color " + String.format("#%06X", 0xFFFFFF & color), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void createGridLayout() {
        final RecyclerView recyclerView = findViewById(R.id.louncher_content);
        recyclerView.setHasFixedSize(false);
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

        data.generateData();
        launcherAdapter = new LauncherAdapter(data);
        recyclerView.setAdapter(launcherAdapter);
    }
}
