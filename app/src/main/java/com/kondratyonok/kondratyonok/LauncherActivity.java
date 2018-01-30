package com.kondratyonok.kondratyonok;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.kondratyonok.kondratyonok.data.Entry;
import com.kondratyonok.kondratyonok.data.Storage;
import com.kondratyonok.kondratyonok.launcher.LauncherAdapter;
import com.kondratyonok.kondratyonok.launcher.OffsetItemDecoration;
import com.kondratyonok.kondratyonok.settings.Layout;
import com.kondratyonok.kondratyonok.settings.Settings;

import java.util.Random;

public class LauncherActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private final Storage data = new Storage();
    private LauncherAdapter launcherAdapter;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle aToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Settings.getTheme());
        setContentView(R.layout.activity_launcher);
        createGridLayout();

        mDrawerLayout = findViewById(R.id.menu);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Random rnd = new Random();
                int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                data.addFront(new Entry(color, String.valueOf(color)));
                launcherAdapter.notifyDataSetChanged();
                Snackbar.make(view, "Added color " + String.format("#%06X", 0xFFFFFF & color), Snackbar.LENGTH_SHORT)
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_settings:
                Toast.makeText(this, "No settings. Sorry", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_list:
                final Intent intent = new Intent();
                intent.setClass(this, ListActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.nav_launcher:
                Toast.makeText(this, "You are here dude", Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(this, "What a fuck!!!", Toast.LENGTH_LONG).show();
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
