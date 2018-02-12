package com.kondratyonok.kondratyonok.fragment.greeting;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kondratyonok.kondratyonok.R;
import com.yandex.metrica.YandexMetrica;

public class DescriptionFragment extends Fragment {

    private static final int ID = R.layout.fr_description;
    private static final String ARG_SECTION_NUMBER = "section_number";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        YandexMetrica.reportEvent("Fragment", "{\"fragment\":\"greeting\":\"description\"}");

        return inflater.inflate(ID, container, false);
    }

    public static DescriptionFragment newInstance(int sectionNumber) {
        DescriptionFragment fragment = new DescriptionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
}
