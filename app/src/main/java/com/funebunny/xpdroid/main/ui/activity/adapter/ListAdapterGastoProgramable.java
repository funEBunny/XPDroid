package com.funebunny.xpdroid.main.ui.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.funebunny.xpdroid.R;
import com.funebunny.xpdroid.gastos.business.model.GastoProgramable;

import java.text.NumberFormat;
import java.util.List;

/**
 * Created by schmidt0 on 8/15/2015.
 */
public class ListAdapterGastoProgramable extends ArrayAdapter<GastoProgramable> {

    public ListAdapterGastoProgramable(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ListAdapterGastoProgramable(Context context, int resource, List<GastoProgramable> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            view = vi.inflate(R.layout.gastos_programables_list_item, null);
        }

        GastoProgramable gastoProgramable = getItem(position);

        if (gastoProgramable != null) {
            TextView fecha = (TextView) view.findViewById(R.id.gastos_programables_lista_fecha);
            TextView categoria = (TextView) view.findViewById(R.id.gastos_programables_lista_categoria);
            TextView importe = (TextView) view.findViewById(R.id.gastos_programables_lista_importe);
            TextView descripcion = (TextView) view.findViewById(R.id.gastos_programables_lista_descripcion);

            if (fecha != null) {
                fecha.setText(String.valueOf(gastoProgramable.getHora()));
            }
            if (categoria != null) {
                categoria.setText(gastoProgramable.getCategoria());
            }
            if (importe != null) {
                NumberFormat format = NumberFormat.getInstance();
                format.setMaximumFractionDigits(2);
                format.setMaximumIntegerDigits(6);

                importe.setText("$" + format.format(Double.valueOf(gastoProgramable.getImporte())));
            }
            if (descripcion != null) {
                descripcion.setText(gastoProgramable.getDescripcion());
            }
        }

        view.setTag(gastoProgramable); //Test PRB - Anclar el objeto GastoDAO a la vista

        return view;

    }


}
