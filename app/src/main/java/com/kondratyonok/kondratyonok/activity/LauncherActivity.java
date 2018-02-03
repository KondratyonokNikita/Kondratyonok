package com.kondratyonok.kondratyonok.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kondratyonok.kondratyonok.Holder;
import com.kondratyonok.kondratyonok.OffsetItemDecoration;
import com.kondratyonok.kondratyonok.R;
import com.kondratyonok.kondratyonok.settings.SettingsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LauncherActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private final List<Entry> data = new ArrayList<>();
    private LauncherAdapter launcherAdapter;
    private DrawerLayout mDrawerLayout;
    private final String TAG = "LauncherActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(SettingsActivity.getApplicationTheme(this));
        setContentView(R.layout.activity_launcher);
        createGridLayout();

        setNavigationView();
        setFloatingActionButton();
    }

    private void setFloatingActionButton() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Random rnd = new Random();
                int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                data.add(0, new Entry(color, String.valueOf(color)));
                launcherAdapter.notifyDataSetChanged();
                if (Log.isLoggable(TAG, Log.INFO)) {
                    Log.i(TAG, "Added color " + String.format("#%06X", 0xFFFFFF & color));
                }
            }
        });
    }

    private void setNavigationView() {
        mDrawerLayout = findViewById(R.id.menu);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        final LauncherActivity activity = this;
        header.findViewById(R.id.avatar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent();
                intent.setClass(activity, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    private void createGridLayout() {
        final RecyclerView recyclerView = findViewById(R.id.louncher_content);
        recyclerView.setHasFixedSize(false);
        final int offset = getResources().getDimensionPixelSize(R.dimen.item_offset);
        recyclerView.addItemDecoration(new OffsetItemDecoration(offset));

        final int columnCountId = SettingsActivity.getLayoutColumnsId(this);
        final int spanCount = getResources().getInteger(columnCountId);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, spanCount);
        recyclerView.setLayoutManager(layoutManager);

        generateData();
        launcherAdapter = new LauncherAdapter(data);
        recyclerView.setAdapter(launcherAdapter);
    }

    public void generateData() {
        if (data.size() != 0) {
            return;
        }
        final Random rnd = new Random();
        for (int i = 0; i < 1000; i++) {
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            data.add(new Entry(color, String.valueOf(color)));
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        final Intent intent;
        switch (item.getItemId()) {
            case R.id.nav_settings:
                intent = new Intent();
                intent.setClass(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_list:
                intent = new Intent();
                intent.setClass(this, ListActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.nav_launcher:
                intent = new Intent();
                intent.setClass(this, LauncherActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.nav_applications:
                intent = new Intent();
                intent.setClass(this, ApplicationsActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                Toast.makeText(this, "What a fuck!!!", Toast.LENGTH_LONG).show();
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public static class Entry {
        public Integer color;
        public String text;

        public Entry(Integer color, String text) {
            this.color = color;
            this.text = text;
        }
    }
}

class LauncherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static String TAG = "LauncherAdapter";

    @NonNull
    private final List<LauncherActivity.Entry> mData;

    public LauncherAdapter(@NonNull final List<LauncherActivity.Entry> data) {
        mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new Holder.GridHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        bindGridView((Holder.GridHolder) holder, position);
    }

    private void bindGridView(@NonNull final Holder.GridHolder gridHolder, final int position) {
        final View view = gridHolder.getImageView();
        view.setBackgroundColor(mData.get(position).color);
        final TextView color = gridHolder.getTextView();
        final String colorRRGGBB = String.format("#%06X", 0xFFFFFF & mData.get(position).color);
        color.setText(colorRRGGBB);
        final LauncherAdapter adapter = this;
        gridHolder.getWholeView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                Snackbar snackbar = Snackbar.make(v, "color = " + colorRRGGBB + ", text = " + mData.get(position).text, Snackbar.LENGTH_SHORT)
                        .setDuration(5000)
                        .setAction("DELETE", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mData.remove(position);
                                if (Log.isLoggable(TAG, Log.INFO)) {
                                    Log.i(TAG, "Deleted from position " + position + " with color " + colorRRGGBB);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        });
                snackbar.addCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        final String description;
                        switch (event) {
                            case Snackbar.Callback.DISMISS_EVENT_ACTION:
                                description = "via an action click.";
                                break;
                            case Snackbar.Callback.DISMISS_EVENT_CONSECUTIVE:
                                description = "from a new Snackbar being shown.";
                                break;
                            case Snackbar.Callback.DISMISS_EVENT_MANUAL:
                                description = "via a call to dismiss().";
                                break;
                            case Snackbar.Callback.DISMISS_EVENT_SWIPE:
                                description = "via a swipe.";
                                break;
                            case Snackbar.Callback.DISMISS_EVENT_TIMEOUT:
                                description = "via a timeout.";
                                break;
                            default:
                                description = "by god.";
                        }
                        if (Log.isLoggable(TAG, Log.INFO)) {
                            Log.i(TAG, "SnackBar dismissed " + description);
                        }
                    }
                });
                snackbar.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
