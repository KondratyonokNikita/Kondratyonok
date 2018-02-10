package com.kondratyonok.kondratyonok.fragment.greeting;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.kondratyonok.kondratyonok.R;
import com.kondratyonok.kondratyonok.settings.Layout;
import com.kondratyonok.kondratyonok.settings.SettingsActivity;
import com.yandex.metrica.YandexMetrica;

public class LayoutFragment extends Fragment {

    private static final int ID = R.layout.fr_layout;
    private static final String ARG_SECTION_NUMBER = "section_number";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        YandexMetrica.reportEvent("Fragment", "{\"fragment\":\"greeting\":\"layout\"}");

        final View mainView = inflater.inflate(ID, container, false);

        View.OnClickListener standard_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton standardRB = mainView.findViewById(R.id.standard_radio_button);
                RadioButton denseRB = mainView.findViewById(R.id.dense_radio_button);
                SettingsActivity.setLayout(Layout.STANDARD, getActivity());
                standardRB.setChecked(true);
                denseRB.setChecked(false);
            }
        };

        View.OnClickListener dense_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton standardRB = mainView.findViewById(R.id.standard_radio_button);
                RadioButton denseRB = mainView.findViewById(R.id.dense_radio_button);
                SettingsActivity.setLayout(Layout.DENSE, getActivity());
                standardRB.setChecked(false);
                denseRB.setChecked(true);
            }
        };

        mainView.findViewById(R.id.standard_layout).setOnClickListener(standard_listener);
        mainView.findViewById(R.id.standard_radio_button).setOnClickListener(standard_listener);
        mainView.findViewById(R.id.dense_layout).setOnClickListener(dense_listener);
        mainView.findViewById(R.id.dense_radio_button).setOnClickListener(dense_listener);

        switch (SettingsActivity.getLayoutColumnsId(getActivity())) {
            case R.integer.span_count:
                ((RadioButton)mainView.findViewById(R.id.standard_radio_button)).setChecked(true);
                ((RadioButton)mainView.findViewById(R.id.dense_radio_button)).setChecked(false);
                break;
            case R.integer.span_count_dense:
                ((RadioButton)mainView.findViewById(R.id.standard_radio_button)).setChecked(false);
                ((RadioButton)mainView.findViewById(R.id.dense_radio_button)).setChecked(true);
                break;
            default: break;
        }
        return mainView;
    }

    public static LayoutFragment newInstance(int sectionNumber) {
        LayoutFragment fragment = new LayoutFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
}
