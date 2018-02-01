package com.kondratyonok.kondratyonok.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
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
import com.kondratyonok.kondratyonok.R;
import com.kondratyonok.kondratyonok.OffsetItemDecoration;
import com.kondratyonok.kondratyonok.Utils;
import com.kondratyonok.kondratyonok.settings.SettingsActivity;

import java.util.ArrayList;
import java.util.List;

public class ApplicationsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private final List<Entry> data = new ArrayList<>();
    private DrawerLayout mDrawerLayout;
    private ApplicationsAdapter applicationsAdapter;
    private final String TAG = "ApplicationsActivity";

    private BroadcastReceiver mMonitor = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, final Intent intent) {
            String action = intent.getAction();
            if (action == null) {
                return;
            }
            switch (intent.getAction()) {
                case Intent.ACTION_PACKAGE_REMOVED: removed(context, intent); break;
                case Intent.ACTION_PACKAGE_ADDED: added(context, intent); break;
                default: return;
            }
            applicationsAdapter.notifyDataSetChanged();
        }

        private void added(Context context, Intent intent) {
            String name = Utils.getPackageFromDataString(intent.getDataString());
            try {
                Drawable icon = getPackageManager().getApplicationIcon(name);
                data.add(new Entry(icon, name));
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }

        private void removed(final Context context, final Intent intent) {
            for (Entry entry: data) {
                String name = Utils.getPackageFromDataString(intent.getDataString());
                if (name.equals(entry.name)) {
                    data.remove(entry);
                    return;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(SettingsActivity.getApplicationTheme(this));
        setContentView(R.layout.activity_applications);
        createGridLayout();

        setNavigationView();
        setFloatingActionButton();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addDataScheme("package");
        registerReceiver(mMonitor, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mMonitor);
    }

    private void setFloatingActionButton() {
        FloatingActionButton fab = findViewById(R.id.fab);
        ((ViewGroup) fab.getParent()).removeView(fab);
    }

    private void setNavigationView() {
        mDrawerLayout = findViewById(R.id.menu);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        final ApplicationsActivity activity = this;
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
        applicationsAdapter = new ApplicationsAdapter(data);
        recyclerView.setAdapter(applicationsAdapter);
    }

    private void generateData() {
        PackageManager packageManager = getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> appInfo = packageManager.queryIntentActivities(intent, PackageManager.GET_META_DATA);
        for (ResolveInfo applicationInfo : appInfo) {
            try {
                if (!applicationInfo.activityInfo.packageName.equals(getPackageName())) {
                    Drawable icon = getPackageManager().getApplicationIcon(applicationInfo.activityInfo.packageName);
                    data.add(new Entry(icon, applicationInfo.activityInfo.packageName));
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
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
        public Drawable icon;
        public String name;

        Entry(Drawable icon, String name) {
            this.icon = icon;
            this.name = name;
        }
    }
}

class ApplicationsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static String TAG = "ApplicationsAdapter";

    @NonNull
    private final List<ApplicationsActivity.Entry> mData;

    public ApplicationsAdapter(@NonNull final List<ApplicationsActivity.Entry> data) {
        mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.application_item, parent, false);
        return new Holder.ApplicationsHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        bindGridView((Holder.ApplicationsHolder) holder, position);
    }

    private void bindGridView(@NonNull final Holder.ApplicationsHolder gridHolder, final int position) {
        final View view = gridHolder.getImageView();
        view.setBackground(mData.get(position).icon);
        final TextView name = gridHolder.getTextView();
        name.setText(mData.get(position).name);
        gridHolder.getWholeView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = view.getContext().getPackageManager().getLaunchIntentForPackage(mData.get(position).name);
                if (launchIntent != null) {
                    view.getContext().startActivity(launchIntent);
                }
            }
        });
        gridHolder.getWholeView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                Snackbar snackbar = Snackbar.make(v, "name = " + mData.get(position).name, Snackbar.LENGTH_SHORT)
                        .setDuration(5000)
                        .setAction("Action", null);
                snackbar.addCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        final String description;
                        switch (event) {
                            case Snackbar.Callback.DISMISS_EVENT_ACTION: description = "via an action click."; break;
                            case Snackbar.Callback.DISMISS_EVENT_CONSECUTIVE: description = "from a new Snackbar being shown."; break;
                            case Snackbar.Callback.DISMISS_EVENT_MANUAL: description = "via a call to dismiss()."; break;
                            case Snackbar.Callback.DISMISS_EVENT_SWIPE: description = "via a swipe."; break;
                            case Snackbar.Callback.DISMISS_EVENT_TIMEOUT: description = "via a timeout."; break;
                            default: description = "by god.";
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

