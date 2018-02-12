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

public class ApplicationsFragment extends Fragment {
    private static final int ID = R.layout.fr_applications;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("ON_CREATE_VIEW_DESKTOP", "CREATE");
        YandexMetrica.reportEvent("Fragment", "{\"fragment\":\"main\":\"grid\"}");
        YandexMetrica.reportEvent("ViewEvent","{\"Layout\":\"grid\"}");
        final View mainView = inflater.inflate(ID, container, false);

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.applications, GridFragment.newInstance()).commit();

        return mainView;
    }

    public static ApplicationsFragment newInstance() {
        return new ApplicationsFragment();
    }

}
