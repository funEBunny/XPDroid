package com.funebunny.xpdroid.main.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.funebunny.xpdroid.R;

/**
 * Created by I823537 on 15/09/2015.
 */
public class AcercaDeFragment extends Fragment {

    private static final String ARG_DRAWER_ITEM_POSITION = "section_number";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_acerca_de, container, false);
        return view;
    }

    public static AcercaDeFragment newInstance(int itemSelected) {
        AcercaDeFragment acercaDeFragment = new AcercaDeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_DRAWER_ITEM_POSITION, itemSelected);
        acercaDeFragment.setArguments(args);
        return acercaDeFragment;
    }

}
