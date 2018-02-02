package com.kondratyonok.kondratyonok.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.kondratyonok.kondratyonok.Database;
import com.kondratyonok.kondratyonok.Holder;
import com.kondratyonok.kondratyonok.OffsetItemDecoration;
import com.kondratyonok.kondratyonok.R;
import com.kondratyonok.kondratyonok.Utils;
import com.kondratyonok.kondratyonok.settings.SettingsActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ApplicationsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private final List<Entry> data = new ArrayList<>();
    private DrawerLayout mDrawerLayout;
    private ApplicationsAdapter applicationsAdapter;
    private ApplicationsActivity activity = this;
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
            Collections.sort(data, SettingsActivity.getSortingMethod(activity));
            applicationsAdapter.notifyDataSetChanged();
        }

        private void added(Context context, Intent intent) {
            String packageName = Utils.getPackageFromDataString(intent.getDataString());
            try {
                Entry entry = getEntryFromPackageName(packageName);
                data.add(entry);
                Database.insertOrUpdate(entry);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }

        private void removed(final Context context, final Intent intent) {
            for (Entry entry: data) {
                String name = Utils.getPackageFromDataString(intent.getDataString());
                if (name.equals(entry.packageName)) {
                    Database.remove(entry);
                    data.remove(entry);
                    return;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Database.init(this);
        setTheme(SettingsActivity.getApplicationTheme(this));
        setContentView(R.layout.activity_applications);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = findViewById(R.id.menu);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        header.findViewById(R.id.avatar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent();
                intent.setClass(v.getContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        ((ViewGroup) fab.getParent()).removeView(fab);

        createGridLayout();
    }

    @Override
    protected void onStart() {
        super.onStart();
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
        for (Entry entry: data) {
            Database.insertOrUpdate(entry);
        }
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

    private Entry getEntryFromPackageName(String packageName) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = getPackageManager();
        Drawable icon = getPackageManager().getApplicationIcon(packageName);
        String name = (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA));
        long updateTime = packageManager.getPackageInfo(packageName, 0).lastUpdateTime;
        return new Entry(icon, name, packageName, updateTime);
    }

    private void generateData() {
        PackageManager packageManager = getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
//        List<ResolveInfo> appInfo = packageManager.queryIntentActivities(intent, PackageManager.GET_META_DATA);
//        for (ResolveInfo applicationInfo : appInfo) {
//            try {
//                if (!applicationInfo.activityInfo.packageName.equals(getPackageName())) {
//                    Entry entry = getEntryFromPackageName(applicationInfo.activityInfo.packageName);
//                    if (!data.contains(entry)) {
//                        data.add(entry);
//                    }
//                }
//            } catch (PackageManager.NameNotFoundException e) {
//                e.printStackTrace();
//            }
//        }

        List<ApplicationInfo> app = packageManager.getInstalledApplications(0);
        for (ApplicationInfo applicationInfo : app) {
            try {
                if (packageManager.getLaunchIntentForPackage(applicationInfo.packageName) != null) {
                    Entry entry = getEntryFromPackageName(applicationInfo.packageName);
                    if (!data.contains(entry)) {
                        data.add(entry);
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }

        Collections.sort(data, SettingsActivity.getSortingMethod(this));
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
        public String packageName;
        public Long updateTime;
        public Integer launched;

        Entry(Drawable icon, String name, String packageName, long updateTime) {
            this.icon = icon;
            this.name = name;
            this.packageName = packageName;
            this.updateTime = updateTime;

            this.launched = Database.get(packageName);
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

        gridHolder.getWholeView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.get(position).launched++;
                Intent launchIntent = view.getContext().getPackageManager().getLaunchIntentForPackage(mData.get(position).packageName);
                if (launchIntent != null) {
                    view.getContext().startActivity(launchIntent);
                }
                Database.insertOrUpdate(mData.get(position));
            }
        });
        name.setText(mData.get(position).name);
        gridHolder.getWholeView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                PopupMenu popup = new PopupMenu(view.getContext(), view);
                popup.inflate(R.menu.context_menu);
                popup.getMenu().findItem(R.id.nav_times).setTitle("Launched: " + mData.get(position).launched);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.nav_delete: {
                                Intent intent = new Intent(Intent.ACTION_DELETE);
                                intent.setData(Uri.parse("package:" + mData.get(position).packageName));
                                view.getContext().startActivity(intent);
                                break;
                            }
                            case R.id.nav_info: {
                                Snackbar.make(view, mData.get(position).packageName, Snackbar.LENGTH_INDEFINITE).show();
                                break;
                            }
                            default: break;
                        }
                        return false;
                    }
                });
                popup.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
