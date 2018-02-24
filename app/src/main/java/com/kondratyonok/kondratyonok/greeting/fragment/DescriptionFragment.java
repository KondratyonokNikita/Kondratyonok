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

import static com.kondratyonok.kondratyonok.utils.CommonUtils.ARG_SECTION_NUMBER;

public class DescriptionFragment extends Fragment {
    private static final String TAG = "DescriptionFragment";
    private static final int ID = R.layout.fr_description;

    public static DescriptionFragment newInstance(int sectionNumber) {
        CommonUtils.log(TAG, "newInstance");
        DescriptionFragment fragment = new DescriptionFragment();
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
