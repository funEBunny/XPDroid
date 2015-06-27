package com.funebunny.xpdroid.main.ui.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.funebunny.xpdroid.R;
import com.funebunny.xpdroid.gastos.dao.Gasto;

import java.util.List;

/**
 * Created by Adru on 6/20/2015.
 */
public class ListAdapterGasto extends ArrayAdapter<Gasto> {

    public ListAdapterGasto(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ListAdapterGasto(Context context, int resource, List<Gasto> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            view = vi.inflate(R.layout.historial_gastos_list_item, null);
        }

        // No cargar mas de 10
        if(position <= 10) {
            Gasto gasto = getItem(position);

            if (gasto != null) {
                TextView fecha = (TextView) view.findViewById(R.id.historial_gastos_lista_fecha);
                TextView categoria = (TextView) view.findViewById(R.id.historial_gastos_lista_categoria);
                TextView importe = (TextView) view.findViewById(R.id.historial_gastos_lista_importe);
                TextView descripcion = (TextView) view.findViewById(R.id.historial_gastos_lista_descripcion);

                if (fecha != null) {
                    if(position == 0) {
                        fecha.setText("FECHA");
                    } else {
                        fecha.setText(gasto.getFecha());
                    }
                }
                if (categoria != null) {
                    categoria.setText(gasto.getCategoria());
                }
                if (importe != null) {
                    if(position == 0) {
                        importe.setText(gasto.getImporte());
                    } else {
                        importe.setText("$" + gasto.getImporte());
                    }
                }
                if (descripcion != null) {
                    descripcion.setText(gasto.getDescripcion());
                }
            }
        }

        return view;
    }

}
