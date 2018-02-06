package com.kondratyonok.kondratyonok.fragment.greeting;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kondratyonok.kondratyonok.R;

/**
 * Created by NKondratyonok on 03.02.18.
 */

public class GreetingFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final int ID = R.layout.fr_greeting;

    public GreetingFragment() {
    }

    public static GreetingFragment newInstance(int sectionNumber) {
        GreetingFragment fragment = new GreetingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(ID, container, false);
    }
}
