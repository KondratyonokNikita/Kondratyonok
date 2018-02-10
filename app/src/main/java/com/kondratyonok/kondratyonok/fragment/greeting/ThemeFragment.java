package com.kondratyonok.kondratyonok.fragment.greeting;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.kondratyonok.kondratyonok.R;
import com.kondratyonok.kondratyonok.settings.SettingsActivity;
import com.kondratyonok.kondratyonok.settings.Theme;
import com.yandex.metrica.YandexMetrica;

public class ThemeFragment extends Fragment {

    private static final int ID = R.layout.fr_theme;
    private static final String ARG_SECTION_NUMBER = "section_number";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        YandexMetrica.reportEvent("Fragment", "{\"fragment\":\"greeting\":\"theme\"}");

        final View mainView = inflater.inflate(ID, container, false);

        View.OnClickListener light_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton lightRB = mainView.findViewById(R.id.light_radio_button);
                RadioButton darkRB = mainView.findViewById(R.id.dark_radio_button);
                SettingsActivity.setApplicationTheme(Theme.LIGHT, getActivity());
                lightRB.setChecked(true);
                darkRB.setChecked(false);
                getActivity().recreate();
            }
        };

        View.OnClickListener dark_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton lightRB = mainView.findViewById(R.id.light_radio_button);
                RadioButton darkRB = mainView.findViewById(R.id.dark_radio_button);
                SettingsActivity.setApplicationTheme(Theme.DARK, getActivity());
                lightRB.setChecked(false);
                darkRB.setChecked(true);
                getActivity().recreate();
            }
        };

        mainView.findViewById(R.id.light_theme).setOnClickListener(light_listener);
        mainView.findViewById(R.id.light_radio_button).setOnClickListener(light_listener);
        mainView.findViewById(R.id.dark_theme).setOnClickListener(dark_listener);
        mainView.findViewById(R.id.dark_radio_button).setOnClickListener(dark_listener);

        switch (SettingsActivity.getApplicationTheme(getActivity())) {
            case R.style.AppThemeLight:
                ((RadioButton)mainView.findViewById(R.id.light_radio_button)).setChecked(true);
                ((RadioButton)mainView.findViewById(R.id.dark_radio_button)).setChecked(false);
                break;
            case R.style.AppThemeDark:
                ((RadioButton)mainView.findViewById(R.id.light_radio_button)).setChecked(false);
                ((RadioButton)mainView.findViewById(R.id.dark_radio_button)).setChecked(true);
                break;
            default: break;
        }

        return mainView;
    }

    public static Fragment newInstance(int sectionNumber) {
        ThemeFragment fragment = new ThemeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
}
