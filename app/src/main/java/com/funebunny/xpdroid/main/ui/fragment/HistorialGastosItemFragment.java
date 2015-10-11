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
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.funebunny.xpdroid.R;

import com.funebunny.xpdroid.business.gasto.model.Gasto;
import com.funebunny.xpdroid.business.gasto.service.ServicioGastosBusiness;
import com.funebunny.xpdroid.business.historial.model.Historial;
import com.funebunny.xpdroid.business.historial.service.ServicioHistorialBusiness;
import com.funebunny.xpdroid.business.presupuesto.service.ServicioPresupuestoBusiness;
import com.funebunny.xpdroid.main.ui.activity.CrearGastoActivity;
import com.funebunny.xpdroid.main.ui.adapter.ExpandableAdapterHistorialGastos;
import com.funebunny.xpdroid.main.ui.activity.MainActivity;
import com.funebunny.xpdroid.main.ui.dummy.DummyContent;
import com.funebunny.xpdroid.utilities.AppConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link HistorialGastosItemCallbacks}
 * interface.
 */
public class HistorialGastosItemFragment extends Fragment implements AbsListView.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String ARG_DRAWER_ITEM_POSITION = "section_number";

    private HistorialGastosItemCallbacks mListener;


    // The fragment's ListView/GridView
    ExpandableListView expandableHistorial;
    //The Adapter which will be used to populate the ListView/GridView with Views.
    ExpandableAdapterHistorialGastos expandableAdapterHistorialGastos;

    private Map<Long, Boolean> groupExpandedMap;

    private ServicioGastosBusiness servicioGastosBusiness = new ServicioGastosBusiness();
    private ServicioPresupuestoBusiness servicioPresupuestoBusiness = new ServicioPresupuestoBusiness();
    private ServicioHistorialBusiness servicioHistorialBusiness = new ServicioHistorialBusiness();

    // TODO: Rename and change types of parameters
    public static HistorialGastosItemFragment newInstance(int itemSelected) {
        HistorialGastosItemFragment fragment = new HistorialGastosItemFragment();
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
    public HistorialGastosItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historialgastositem_list, container, false);
        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);

        ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListView.ExpandableListContextMenuInfo) menuInfo;

        if (ExpandableListView.getPackedPositionType(info.packedPosition) != ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
            return; // Menú contextual sólo para child (gastos)
        }
        getActivity().getMenuInflater().inflate(R.menu.menu_contextual_gasto, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListView.ExpandableListContextMenuInfo) item.getMenuInfo();

        Gasto gasto = (Gasto) info.targetView.getTag();

        switch (item.getItemId()) {
            case R.id.menu_contextual_gasto_editar:
                Bundle bGasto = new Bundle();
                bGasto.putSerializable(AppConstants.GASTO, gasto);
                Intent i = new Intent(getActivity(), CrearGastoActivity.class);
                i.putExtras(bGasto);
                startActivity(i);
                return true;
            case R.id.menu_contextual_gasto_borrar:
                servicioGastosBusiness.eliminarGasto(gasto.getId());
                servicioPresupuestoBusiness.descontarTotales(gasto);
                servicioHistorialBusiness.eliminarHistorial(gasto);
                verificarGruposExpandidos();
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
            mListener = (HistorialGastosItemCallbacks) activity;
            ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_DRAWER_ITEM_POSITION));
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement HistorialGastosItemCallbacks.onHistorialGastosItemSelected");
        }
    }

    // PRB - Refresh del reporte al volver al fragment
    @Override
    public void onResume() {
        super.onResume();

        expandableHistorial = (ExpandableListView) getView().findViewById(R.id.fragment_historialgastos_el_gastos);
        ArrayList<Historial> listaHistorial = (ArrayList<Historial>) servicioHistorialBusiness.obtenerListaHistorial();
        expandableAdapterHistorialGastos = new ExpandableAdapterHistorialGastos(getView().getContext(), listaHistorial);
        expandableHistorial.setAdapter(expandableAdapterHistorialGastos);
        registerForContextMenu(expandableHistorial);

        if (groupExpandedMap != null) {
            boolean expandido;

            for (int i = 0; i < listaHistorial.size(); i++) {
                expandido = false;
                try {
                    expandido = groupExpandedMap.get(listaHistorial.get(i).getId());
                } catch (Exception e) {
                }
                if (expandido) expandableHistorial.expandGroup(i);
            }
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        verificarGruposExpandidos();
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
            mListener.onHistorialGastosItemSelected(DummyContent.ITEMS.get(position).id);
        }
    }


    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = expandableHistorial.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
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
    public interface HistorialGastosItemCallbacks {
        // TODO: Update argument type and name
        public void onHistorialGastosItemSelected(String id);
    }

    // Estos 2 métodos (onActivityCreated y onCreateOptionsMenu) anulan el menu anterior y setean el menu del Fragment seleccionado (actual)
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_crear_gasto, menu);
    }

    private void verificarGruposExpandidos() {

        groupExpandedMap = new HashMap<>();
        ArrayList<Historial> groups = expandableAdapterHistorialGastos.getGroups();

        for (int i = 0; i < groups.size(); i++) {
            groupExpandedMap.put(groups.get(i).getId(), expandableHistorial.isGroupExpanded(i));
        }

    }

}
