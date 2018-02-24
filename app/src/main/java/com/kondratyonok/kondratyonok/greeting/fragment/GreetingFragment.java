package com.kondratyonok.kondratyonok.greeting.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kondratyonok.kondratyonok.R;
import com.kondratyonok.kondratyonok.utils.CommonUtils;
import com.yandex.metrica.YandexMetrica;

/**
 * Created by NKondratyonok on 03.02.18.
 */

public class GreetingFragment extends Fragment {
    private static final String TAG = "GreetingFragment";
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final int ID = R.layout.fr_greeting;

    public static GreetingFragment newInstance(int sectionNumber) {
        CommonUtils.log(TAG, "newInstance");
        GreetingFragment fragment = new GreetingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        CommonUtils.log(TAG, "onCreateView");
        return inflater.inflate(ID, container, false);
    }
}
