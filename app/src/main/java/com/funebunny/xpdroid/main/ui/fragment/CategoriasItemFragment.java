package com.funebunny.xpdroid.main.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.funebunny.xpdroid.R;
import com.funebunny.xpdroid.business.categoria.model.Categoria;
import com.funebunny.xpdroid.business.categoria.service.ServicioCategoriaBusiness;
import com.funebunny.xpdroid.main.ui.activity.MainActivity;
import com.funebunny.xpdroid.main.ui.adapter.ListAdapterCategoria;
import com.funebunny.xpdroid.utilities.AppConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the
 * {@link CategoriasItemFragment.CategoriasItemCallbacks}
 * interface.
 */
public class CategoriasItemFragment extends Fragment implements AbsListView.OnItemClickListener {

    private String[] columnas;

    private CategoriasItemCallbacks mListener;
    private List<Categoria> categorias = new ArrayList<Categoria>();
    private ServicioCategoriaBusiness servicioCategorias = new ServicioCategoriaBusiness();

    //The fragment's ListView/GridView.
    private AbsListView mListView;

    //The Adapter which will be used to populate the ListView/GridView with Views.
    private ListAdapter mAdapter;

    // TODO: Rename and change types of parameters
    public static CategoriasItemFragment newInstance(int itemSelected) {
        CategoriasItemFragment fragment = new CategoriasItemFragment();
        Bundle args = new Bundle();
        args.putInt(AppConstants.ARG_DRAWER_ITEM_POSITION, itemSelected); // item seleccionado del navigation drawer (arranca desde "Inicio" que es posicion 0, "Gastos Favoritos" que es posicion 1, y asi...)
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CategoriasItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        categorias.addAll(servicioCategorias.obtenerCategorias());
        mAdapter = new ListAdapterCategoria(getActivity(), R.layout.categoria_list_item, categorias);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categoriaitem_list, container, false);

        // Set the adapter
        mListView = (ListView) view.findViewById(R.id.fragment_categoriaitem_list_lv_lista);
        mListView.setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);
        // registrar para menu contextual, para mostrar opciones on-long-click
        registerForContextMenu(mListView);

        return view;
    }

    @Override
    public void onResume() {
        // PRB - Refresh del reporte al volver al fragment
        super.onResume();
        this.categorias.clear();
        this.categorias.addAll(servicioCategorias.obtenerCategorias());

        if (categorias.isEmpty()) mostrarNoData();
        else ocultarNoData();

        mAdapter = new ListAdapterCategoria(getActivity(), R.layout.categoria_list_item, categorias);
        View view = getView();

        mListView = (ListView) view.findViewById(R.id.fragment_categoriaitem_list_lv_lista);
        mListView.setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (CategoriasItemCallbacks) activity;
            ((MainActivity) activity).onSectionAttached(getArguments().getInt(AppConstants.ARG_DRAWER_ITEM_POSITION));
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement CategoriasItemCallbacks.onCategoriasItemSelected");
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

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    /**
     * This interface must be implemented by activities that contain this fragment to allow an interaction in this fragment
     * to be communicated to the activity and potentially other fragments contained in that activity.
     * <p/> See the Android Training lesson <a href= "http://developer.android.com/training/basics/fragments/communicating.html">Communicating with Other Fragments</a> for more information.
     */
    public interface CategoriasItemCallbacks {
        // TODO: Update argument type and name
        void onCategoriasItemSelected(String id);
    }

    // Estos 2 métodos (onActivityCreated y onCreateOptionsMenu) anulan el menu anterior y setean el menu del Fragment seleccionado (actual)
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_crear_categoria, menu);
    }

    private void mostrarNoData() {
        //Mensaje en pantalla principal que indica que no hay categorias grabadas
        getView().findViewById(R.id.fragment_categoria_tv_nodata).setVisibility(View.VISIBLE);
    }

    private void ocultarNoData() {
        (getView().findViewById(R.id.fragment_categoria_tv_nodata)).setVisibility(View.GONE);
    }
}
