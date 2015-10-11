package com.funebunny.xpdroid.main.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.funebunny.xpdroid.R;
import com.funebunny.xpdroid.business.presupuesto.model.Presupuesto;
import com.funebunny.xpdroid.utilities.AppUtilities;

import java.text.NumberFormat;
import java.util.List;

/**
 * Created by provirabosch on 21/08/2015.
 */
public class ListAdapterPresupuesto extends ArrayAdapter<Presupuesto> {

    public ListAdapterPresupuesto(Context context, int resource, List<Presupuesto> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            view = vi.inflate(R.layout.presupuesto_list_item, null);
        }

        Presupuesto presupuesto = getItem(position);

        if (presupuesto != null) {

            view.setTag(presupuesto); //Anclar el objeto a la vista

            TextView periodo = (TextView) view.findViewById(R.id.presupuesto_list_item_tv_periodo);
            TextView importe = (TextView) view.findViewById(R.id.presupuesto_list_item_tv_importe);

            if (periodo != null) {
                periodo.setText(presupuesto.getPeriodo());
            }
            if (importe != null) {

                String importe1 = presupuesto.getImporte();
                if (!"".equalsIgnoreCase(importe1)){
                    importe.setText("$" + AppUtilities.formatearImporte(importe1));

                }else{
                    importe.setText("");
                }
            }
        }

        return view;

    }
}
