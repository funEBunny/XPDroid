package com.funebunny.xpdroid.main.ui.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.funebunny.xpdroid.R;
import com.funebunny.xpdroid.gastos.business.model.GastoFavorito;
import com.funebunny.xpdroid.gastos.business.model.Objetivo;

import java.text.NumberFormat;
import java.util.List;

/**
 * Created by I823537 on 21/08/2015.
 */
public class ListAdapterObjetivo extends ArrayAdapter<Objetivo> {

    public ListAdapterObjetivo(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ListAdapterObjetivo(Context context, int resource, List<Objetivo> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            view = vi.inflate(R.layout.objetivos_list_item, null);
        }

        Objetivo objetivo = getItem(position);

        if (objetivo != null) {

            TextView periodo = (TextView) view.findViewById(R.id.objetivos_list_item_tv_periodo);
            TextView importe = (TextView) view.findViewById(R.id.objetivos_list_item_tv_importe);

            if (periodo != null) {
                periodo.setText(objetivo.getPeriodo());
            }
            if (importe != null) {
                NumberFormat format = NumberFormat.getInstance();
                format.setMaximumFractionDigits(2);
                format.setMaximumIntegerDigits(6);

                importe.setText("$" + format.format(Double.valueOf(objetivo.getImporte())));
            }
        }

        view.setTag(objetivo); //Test PRB - Anclar el objeto a la vista

        return view;

    }
}
