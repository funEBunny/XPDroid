package com.funebunny.xpdroid.main.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.funebunny.xpdroid.R;

import java.util.ArrayList;

/**
 * Created by I823537 on 01/09/2015.
 */
public class ExpandableAdapaterAlertaPresupuesto extends BaseExpandableListAdapter {

    private LayoutInflater inflater;
    private Activity activity;
    private String header;
    private ArrayList<String> items;


    public ExpandableAdapaterAlertaPresupuesto(String header, ArrayList<String> items) {
        this.header = header;
        this.items = items;
    }

    public void setInflater(LayoutInflater inflater, Activity activity) {
        this.inflater = inflater;
        this.activity = activity;
    }

    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return items.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.alerta_presupuesto_header, null);
        }

        ((TextView) convertView.findViewById(R.id.alerta_presupuesto_header_tv_titulo)).setText(header);

        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {


        if (convertView == null) {
            convertView = inflater.inflate(R.layout.alerta_presupuesto_item, null);
        }

        ((TextView) convertView.findViewById(R.id.alerta_presupuesto_item_tv_detalle)).setText(items.get(childPosition));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }

        });

        return convertView;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
