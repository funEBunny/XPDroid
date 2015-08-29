package com.funebunny.xpdroid.main.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.funebunny.xpdroid.R;
import com.funebunny.xpdroid.business.gasto.model.GastoFavorito;

import java.text.NumberFormat;
import java.util.List;

/**
 * Created by provirabosch on 15/08/2015.
 */
public class ListAdapterGastoFavorito extends ArrayAdapter<GastoFavorito> {

    public ListAdapterGastoFavorito(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ListAdapterGastoFavorito(Context context, int resource, List<GastoFavorito> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            view = vi.inflate(R.layout.gastos_favoritos_list_item, null);
        }

        GastoFavorito gastoFavorito = getItem(position);

        if (gastoFavorito != null) {

            view.setTag(gastoFavorito); //Test PRB - Anclar el objeto

            TextView categoria = (TextView) view.findViewById(R.id.gastos_favoritos_list_item_tv_categoria);
            TextView importe = (TextView) view.findViewById(R.id.gastos_favoritos_list_item_tv_importe);
            TextView descripcion = (TextView) view.findViewById(R.id.gastos_favoritos_list_item_tv_descripcion);

            if (categoria != null) {
                categoria.setText(gastoFavorito.getCategoria());
            }
            if (importe != null) {
                NumberFormat format = NumberFormat.getInstance();
                format.setMaximumFractionDigits(2);
                format.setMaximumIntegerDigits(6);

                importe.setText("$" + format.format(Double.valueOf(gastoFavorito.getImporte())));
            }
            if (descripcion != null) {
                descripcion.setText(gastoFavorito.getDescripcion());
            }
        }

        return view;

    }
}
