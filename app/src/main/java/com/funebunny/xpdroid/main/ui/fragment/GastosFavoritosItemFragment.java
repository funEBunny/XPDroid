package com.funebunny.xpdroid.main.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.funebunny.xpdroid.R;

import com.funebunny.xpdroid.gastos.business.model.GastoFavorito;
import com.funebunny.xpdroid.gastos.business.service.ServicioGastosBusiness;
import com.funebunny.xpdroid.main.ui.activity.CrearGastoFavoritoActivity;
import com.funebunny.xpdroid.main.ui.activity.MainActivity;
import com.funebunny.xpdroid.main.ui.activity.TratarGastoActivity;
import com.funebunny.xpdroid.main.ui.activity.TratarGastoFavoritoActivity;
import com.funebunny.xpdroid.main.ui.activity.XPDroidActivity;
import com.funebunny.xpdroid.main.ui.activity.adapter.ListAdapterGasto;
import com.funebunny.xpdroid.main.ui.activity.adapter.ListAdapterGastoFavorito;
import com.funebunny.xpdroid.main.ui.dummy.DummyContent;
import com.funebunny.xpdroid.utilities.AppConstants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the
 * {@link GastosFavoritosItemFragment.GastosFavoritosItemCallbacks}
 * interface.
 */
public class GastosFavoritosItemFragment extends Fragment implements AbsListView.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String[] columnas;

    private GastosFavoritosItemCallbacks mListener;
    private List<GastoFavorito> gastosFavoritos = new ArrayList<GastoFavorito>();
    private ServicioGastosBusiness servicioGastos = new ServicioGastosBusiness();

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    // TODO: Rename and change types of parameters
    public static GastosFavoritosItemFragment newInstance(int itemSelected) {
        GastosFavoritosItemFragment fragment = new GastosFavoritosItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, "param1");
        args.putString(ARG_PARAM2, "param2");
        args.putInt(AppConstants.ARG_DRAWER_ITEM_POSITION, itemSelected); // item seleccionado del navigation drawer (arranca desde "Inicio" que es posicion 0, "Gastos Favoritos" que es posicion 1, y asi...)
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public GastosFavoritosItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//
//        columnas = getResources().getStringArray(R.array.gastos_favoritos_columnas);

        gastosFavoritos.addAll(servicioGastos.obtenerGastosFavoritos());

        // TODO: Change Adapter to display your content

        mAdapter = new ListAdapterGastoFavorito(getActivity(), R.layout.gastos_favoritos_list_item, gastosFavoritos);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gastosfavoritositem_list, container, false);

        // Set the adapter
        mListView = (ListView) view.findViewById(R.id.fragment_gastosfavoritositem_list_lv_lista);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);
        // registrar para menu contextual, para mostrar opciones on-long-click
        registerForContextMenu(mListView);

        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_contextual_gasto, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.menu_contextual_gasto_editar:
                Bundle bGastoFavorito = new Bundle();
                bGastoFavorito.putSerializable(AppConstants.GASTO_FAVORITO,gastosFavoritos.get(info.position));
                Intent i = new Intent(getActivity(), CrearGastoFavoritoActivity.class);
                i.putExtras(bGastoFavorito);
                startActivity(i);
                return true;
            case R.id.menu_contextual_gasto_borrar:
                servicioGastos.eliminarGastoFavorito(gastosFavoritos.get(info.position).getId());
                onResume();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


    @Override
    public void onResume() {
        // PRB - Refresh del reporte al volver al fragment
        super.onResume();
        this.gastosFavoritos.clear();
        this.gastosFavoritos.addAll(servicioGastos.obtenerGastosFavoritos());

        mAdapter = new ListAdapterGastoFavorito(getActivity(), R.layout.gastos_favoritos_list_item, gastosFavoritos);
        View view = getView();

        mListView = (ListView) view.findViewById(R.id.fragment_gastosfavoritositem_list_lv_lista);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (GastosFavoritosItemCallbacks) activity;
            ((MainActivity) activity).onSectionAttached(getArguments().getInt(AppConstants.ARG_DRAWER_ITEM_POSITION));
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement GastosFavoritosItemCallbacks.onGastosFavoritosItemSelected");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onGastosFavoritosItemSelected(DummyContent.ITEMS.get(position).id);
        }
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
    public interface GastosFavoritosItemCallbacks {
        // TODO: Update argument type and name
        public void onGastosFavoritosItemSelected(String id);
    }

    // Estos 2 métodos (onActivityCreated y onCreateOptionsMenu) anulan el menu anterior y setean el menu del Fragment seleccionado (actual)
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_crear_gasto_favorito, menu);
    }
}
