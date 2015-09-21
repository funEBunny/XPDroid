package com.funebunny.xpdroid.main.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.funebunny.xpdroid.R;
import com.funebunny.xpdroid.business.gasto.model.Gasto;
import com.funebunny.xpdroid.business.gasto.service.ServicioGastosBusiness;
import com.funebunny.xpdroid.business.historial.model.Historial;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by I823537 on 05/09/2015.
 */
public class ExpandableAdapterHistorialGastos extends BaseExpandableListAdapter {

    private ArrayList<Historial> groups;
    private Map<Integer, ArrayList<Gasto>> itemsMap = new HashMap<>();
    private LayoutInflater inflater;
    private ServicioGastosBusiness servicioGastosBusiness;

    public ExpandableAdapterHistorialGastos(Context context, ArrayList<Historial> listaHistorial) {
        this.groups = listaHistorial;
        this.servicioGastosBusiness = new ServicioGastosBusiness();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        Historial historial = groups.get(groupPosition);
        Calendar fechaMesAnio = Calendar.getInstance();
        fechaMesAnio.set(Calendar.MONTH, historial.getMes());
        fechaMesAnio.set(Calendar.YEAR, historial.getAnio());
        ArrayList<Gasto> gastos;
        gastos = itemsMap.get(groupPosition);
        if (gastos == null) {
            gastos = (ArrayList<Gasto>) servicioGastosBusiness.obtenerGastosMes(fechaMesAnio);
            itemsMap.put(groupPosition, gastos);
        }
        return itemsMap.get(groupPosition).size();

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
            convertView = inflater.inflate(R.layout.historial_gastos_header, null);
        }

        ((TextView) convertView.findViewById(R.id.historial_gastos_header_tv_mes)).setText(Character.toUpperCase(groups.get(groupPosition).getTextoMes().charAt(0)) + groups.get(groupPosition).getTextoMes().substring(1));
        ((TextView) convertView.findViewById(R.id.historial_gastos_header_tv_anio)).setText(String.valueOf(groups.get(groupPosition).getAnio()));
        ((TextView) convertView.findViewById(R.id.historial_gastos_header_tv_total)).setText("$" + groups.get(groupPosition).getTotal());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.historial_gastos_list_item, null);
        }

        ArrayList<Gasto> items = itemsMap.get(groupPosition);
        convertView.setTag(items.get(childPosition));   //Anclar gasto a la vista

        ((TextView) convertView.findViewById(R.id.historial_gastos_lista_categoria)).setText(items.get(childPosition).getCategoria());
        ((TextView) convertView.findViewById(R.id.historial_gastos_lista_descripcion)).setText(items.get(childPosition).getDescripcion());
        ((TextView) convertView.findViewById(R.id.historial_gastos_lista_importe)).setText("$" + items.get(childPosition).getImporte());
        ((TextView) convertView.findViewById(R.id.historial_gastos_lista_fecha)).setText(items.get(childPosition).getFecha());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }

        });

        return convertView; 
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
