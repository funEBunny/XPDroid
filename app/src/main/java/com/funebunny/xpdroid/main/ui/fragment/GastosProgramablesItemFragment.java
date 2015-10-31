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
import com.funebunny.xpdroid.business.gasto.model.GastoProgramable;
import com.funebunny.xpdroid.business.gasto.service.ServicioGastosBusiness;
import com.funebunny.xpdroid.main.ui.activity.CrearGastoProgramableActivity;
import com.funebunny.xpdroid.main.ui.activity.MainActivity;
import com.funebunny.xpdroid.main.ui.adapter.ListAdapterGastoProgramable;
import com.funebunny.xpdroid.utilities.AppConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link GastosProgramablesItemCallbacks}
 * interface.
 */
public class GastosProgramablesItemFragment extends Fragment implements AbsListView.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String ARG_DRAWER_ITEM_POSITION = "section_number";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private GastosProgramablesItemCallbacks mListener;
    private List<GastoProgramable> gastosProgramables = new ArrayList<GastoProgramable>();
    private ServicioGastosBusiness servicioGastosBusiness = new ServicioGastosBusiness();
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
    public static GastosProgramablesItemFragment newInstance(int itemSelected) {
        GastosProgramablesItemFragment fragment = new GastosProgramablesItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, "param1");
        args.putString(ARG_PARAM2, "param2");
        args.putInt(ARG_DRAWER_ITEM_POSITION, itemSelected); // item seleccionado del navigation drawer (arranca desde "Inicio" que es posicion 0, "Gastos Favoritos" que es posicion 1, y asi...)
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public GastosProgramablesItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }

        this.gastosProgramables.addAll(servicioGastosBusiness.obtenerGastosProgramables());
        mAdapter = new ListAdapterGastoProgramable(getActivity(), R.layout.gastos_programables_list_item, gastosProgramables);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gastosprogramablesitem_list, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(R.id.lista_gastos_programables_lista);
        mListView.setAdapter(mAdapter);

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
                Bundle bGastoProgramable = new Bundle();
                bGastoProgramable.putSerializable(AppConstants.GASTO_PROGRAMABLE, gastosProgramables.get(info.position));
                Intent i = new Intent(getActivity(), CrearGastoProgramableActivity.class);
                i.putExtras(bGastoProgramable);
                startActivity(i);
                return true;
            case R.id.menu_contextual_gasto_borrar:
                servicioGastosBusiness.eliminarGastoProgramable(getActivity().getApplicationContext(), gastosProgramables.get(info.position).getId());
                onResume();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (GastosProgramablesItemCallbacks) activity;
            ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_DRAWER_ITEM_POSITION));
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement GastosProgramablesItemCallbacks.onGastosProgramablesItemSelected");
        }
    }


    // PRB - Refresh del reporte al volver al fragment
    @Override
    public void onResume() {
        super.onResume();
        this.gastosProgramables.clear();
        this.gastosProgramables.addAll(servicioGastosBusiness.obtenerGastosProgramables());
        mAdapter = new ListAdapterGastoProgramable(getActivity(), R.layout.gastos_programables_list_item, gastosProgramables);
        View view = getView();

        mListView = (ListView) view.findViewById(R.id.lista_gastos_programables_lista);
        mListView.setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface GastosProgramablesItemCallbacks {
        // TODO: Update argument type and name
        void onGastosProgramablesItemSelected(String id);
    }

    // Estos 2 métodos (onActivityCreated y onCreateOptionsMenu) anulan el menu anterior y setean el menu del Fragment seleccionado (actual)
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_crear_gasto_programable, menu);
    }

}
