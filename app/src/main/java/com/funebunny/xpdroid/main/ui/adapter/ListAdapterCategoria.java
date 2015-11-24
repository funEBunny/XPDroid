package com.funebunny.xpdroid.main.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.funebunny.xpdroid.R;
import com.funebunny.xpdroid.business.categoria.model.Categoria;
import com.funebunny.xpdroid.business.gasto.model.GastoFavorito;
import com.funebunny.xpdroid.utilities.AppUtilities;

import java.util.List;

/**
 * Created by provirabosch on 15/08/2015.
 */
public class ListAdapterCategoria extends ArrayAdapter<Categoria> {

    public ListAdapterCategoria(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ListAdapterCategoria(Context context, int resource, List<Categoria> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            view = vi.inflate(R.layout.categoria_list_item, null);
        }

        Categoria categoria = getItem(position);

        if (categoria != null) {

            view.setTag(categoria); //Anclar el objeto

            TextView nombre = (TextView) view.findViewById(R.id.categoria_list_item_tv_nombre);

            if (nombre != null) {
                nombre.setText(categoria.getnombre());
            }
        }

        return view;

    }
}
