package com.kondratyonok.kondratyonok.greeting.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kondratyonok.kondratyonok.R;
import com.kondratyonok.kondratyonok.launcher.ApplicationsActivity;
import com.kondratyonok.kondratyonok.settings.Layout;
import com.kondratyonok.kondratyonok.settings.SettingsActivity;
import com.kondratyonok.kondratyonok.settings.Theme;
import com.kondratyonok.kondratyonok.utils.CommonUtils;
import com.yandex.metrica.YandexMetrica;

import static com.kondratyonok.kondratyonok.utils.CommonUtils.ARG_SECTION_NUMBER;

/**
 * Created by NKondratyonok on 03.02.18.
 */

public class AgreeFragment extends Fragment {
    private static final String TAG = "AgreeFragment";
    private static final int ID = R.layout.fr_agree;

    public static AgreeFragment newInstance(int sectionNumber) {
        CommonUtils.log(TAG, "newInstance");
        AgreeFragment fragment = new AgreeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        CommonUtils.log(TAG, "onCreateView");
        View mainView = inflater.inflate(ID, container, false);

        mainView.findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!SettingsActivity.hasApplicationTheme(getActivity())) {
                    SettingsActivity.setApplicationTheme(Theme.DEFAULT, getActivity());
                }
                if (!SettingsActivity.hasLayout(getActivity())) {
                    SettingsActivity.setLayout(Layout.DEFAULT, getActivity());
                }
                SettingsActivity.setNeedWelcomePage(getActivity(), false);
                startActivity(new Intent(getContext(), ApplicationsActivity.class));
                getActivity().finish();
            }
        });
        return mainView;
    }
}
