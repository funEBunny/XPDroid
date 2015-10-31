package com.funebunny.xpdroid.main.ui.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.funebunny.xpdroid.R;
import com.funebunny.xpdroid.main.ui.activity.MainActivity;

/**
 * Created by I823537 on 15/09/2015.
 */
public class AcercaDeFragment extends Fragment implements AbsListView.OnItemClickListener {

    private static final String ARG_DRAWER_ITEM_POSITION = "section_number";
    private AcercaDeCallbacks mListener;

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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (AcercaDeCallbacks) activity;
            ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_DRAWER_ITEM_POSITION));
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement AcercaDeCallbacks.onAcercaDeSelected");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public interface AcercaDeCallbacks {
        // TODO: Update argument type and name
        void onAcercaDeSelected(String id);
    }
}
