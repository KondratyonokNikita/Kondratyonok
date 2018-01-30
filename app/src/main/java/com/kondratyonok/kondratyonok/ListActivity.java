package com.kondratyonok.kondratyonok;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kondratyonok.kondratyonok.data.Entry;
import com.kondratyonok.kondratyonok.data.Storage;
import com.kondratyonok.kondratyonok.launcher.LauncherAdapter;
import com.kondratyonok.kondratyonok.launcher.OffsetItemDecoration;
import com.kondratyonok.kondratyonok.list.ListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListActivity extends AppCompatActivity {

    private final Storage data = new Storage();
    private ListAdapter launcherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        createLinearLayout();

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

    private void createLinearLayout() {
        final RecyclerView recyclerView = findViewById(R.id.louncher_content);
        recyclerView.setHasFixedSize(true);
        final int offset = getResources().getDimensionPixelSize(R.dimen.item_offset);
        recyclerView.addItemDecoration(new OffsetItemDecoration(offset));

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        data.generateData();
        launcherAdapter = new ListAdapter(data);
        recyclerView.setAdapter(launcherAdapter);
    }
}
