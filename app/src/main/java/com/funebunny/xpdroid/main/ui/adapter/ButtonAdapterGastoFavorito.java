package com.funebunny.xpdroid.main.ui.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.funebunny.xpdroid.R;
import com.funebunny.xpdroid.business.gasto.model.GastoFavorito;
import com.funebunny.xpdroid.utilities.AppUtilities;

import java.util.List;

/**
 * Created by provirabosch on 22/08/2015.
 */
public class ButtonAdapterGastoFavorito extends ArrayAdapter<GastoFavorito> {

    public ButtonAdapterGastoFavorito(Context context, int resource, List<GastoFavorito> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            view = vi.inflate(R.layout.gasto_favorito_button, null);
        }

        GastoFavorito gastoFavorito = getItem(position);

        if (gastoFavorito != null) {

            view.setTag(gastoFavorito); //Anclar objeto a la vista

            Button bGastoFavorito = (Button) view.findViewById(R.id.gasto_favorito_button_bt_gasto);

            if (bGastoFavorito != null) {

                bGastoFavorito.setText(Html.fromHtml("<b><big>" + gastoFavorito.getDescripcion() +
                        "&nbsp;&nbsp;$" + AppUtilities.formatearImporte(gastoFavorito.getImporte()) + "</big></b>" + "<br />" +
                        "<small>" + gastoFavorito.getCategoria() + "</small>"));

            }

        }

        return view;
    }
}
