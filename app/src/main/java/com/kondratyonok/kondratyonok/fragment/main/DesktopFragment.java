package com.kondratyonok.kondratyonok.fragment.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kondratyonok.kondratyonok.R;
import com.kondratyonok.kondratyonok.activity.ApplicationsActivity;
import com.yandex.metrica.YandexMetrica;

/**
 * Created by NKondratyonok on 11.02.18.
 */

public class DesktopFragment extends Fragment {
    private static final int ID = R.layout.fr_desktop;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("ON_CREATE_VIEW_DESKTOP", "CREATE");
        YandexMetrica.reportEvent("Fragment", "{\"fragment\":\"main\":\"grid\"}");
        YandexMetrica.reportEvent("ViewEvent","{\"Layout\":\"grid\"}");

        return inflater.inflate(ID, container, false);
    }

    public static DesktopFragment newInstance() {
        return new DesktopFragment();
    }

}
