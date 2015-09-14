package com.funebunny.xpdroid.main.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.funebunny.xpdroid.R;

import com.funebunny.xpdroid.business.presupuesto.service.ServicioPresupuestoBusiness;
import com.funebunny.xpdroid.main.ui.activity.MainActivity;
import com.funebunny.xpdroid.main.ui.dummy.DummyContent;
import com.funebunny.xpdroid.utilities.AppConstants;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link NotificacionesItemCallbacks}
 * interface.
 */
public class TotalesItemFragment extends Fragment implements AbsListView.OnItemClickListener {

    private ServicioPresupuestoBusiness servicioPresupuestoBusiness = new ServicioPresupuestoBusiness();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private NotificacionesItemCallbacks mListener;

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
    public static TotalesItemFragment newInstance(int itemSelected) {
        TotalesItemFragment fragment = new TotalesItemFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, "param1");
//        args.putString(ARG_PARAM2, "param2");
        args.putInt(AppConstants.ARG_DRAWER_ITEM_POSITION, itemSelected); // item seleccionado del navigation drawer (arranca desde "Inicio" que es posicion 0, "Gastos Favoritos" que es posicion 1, y asi...)
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TotalesItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // TODO: Change Adapter to display your content
        mAdapter = new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, DummyContent.ITEMS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_totalesitem_list, container, false);

        ((RadioButton) view.findViewById(R.id.fragment_totalesitem_list_rb_diario)).setText(formatTextoTotal(getResources().getString(R.string.diario), servicioPresupuestoBusiness.obtenerTotalDiario()));
        ((RadioButton) view.findViewById(R.id.fragment_totalesitem_list_rb_semanal)).setText(formatTextoTotal(getResources().getString(R.string.semanal), servicioPresupuestoBusiness.obtenerTotalSemanal()));
        ((RadioButton) view.findViewById(R.id.fragment_totalesitem_list_rb_mensual)).setText(formatTextoTotal(getResources().getString(R.string.mensual), servicioPresupuestoBusiness.obtenerTotalMensual()));
        ((RadioButton) view.findViewById(R.id.fragment_totalesitem_list_rb_anual)).setText(formatTextoTotal(getResources().getString(R.string.anual), servicioPresupuestoBusiness.obtenerTotalAnual()));

        switch (servicioPresupuestoBusiness.obtenerTotalPredeterminado()) {

            case AppConstants.PERIODO_DIARIO: {
                ((RadioButton) view.findViewById(R.id.fragment_totalesitem_list_rb_diario)).setChecked(true);
                break;
            }
            case AppConstants.PERIODO_SEMANAL: {
                ((RadioButton) view.findViewById(R.id.fragment_totalesitem_list_rb_semanal)).setChecked(true);
                break;
            }
            case AppConstants.PERIODO_MENSUAL: {
                ((RadioButton) view.findViewById(R.id.fragment_totalesitem_list_rb_mensual)).setChecked(true);
                break;
            }
            case AppConstants.PERIODO_ANUAL: {
                ((RadioButton) view.findViewById(R.id.fragment_totalesitem_list_rb_anual)).setChecked(true);
                break;
            }
        }

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (NotificacionesItemCallbacks) activity;
            ((MainActivity) activity).onSectionAttached(getArguments().getInt(AppConstants.ARG_DRAWER_ITEM_POSITION));
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement NotificacionesItemCallbacks.onNotificacionesItemSelected");
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
            mListener.onNotificacionesItemSelected(DummyContent.ITEMS.get(position).id);
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
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface NotificacionesItemCallbacks {
        // TODO: Update argument type and name
        public void onNotificacionesItemSelected(String id);
    }

    private Spanned formatTextoTotal(String texto, String importe) {
        return (Html.fromHtml("<big>" + texto + ":" + "&nbsp;&nbsp;$" + "<b>" + importe + "</b>" + "</big>"));
    }

}
