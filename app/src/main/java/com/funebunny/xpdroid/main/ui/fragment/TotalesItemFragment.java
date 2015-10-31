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
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.funebunny.xpdroid.R;
import com.funebunny.xpdroid.business.presupuesto.service.ServicioPresupuestoBusiness;
import com.funebunny.xpdroid.main.ui.activity.MainActivity;
import com.funebunny.xpdroid.utilities.AppConstants;
import com.funebunny.xpdroid.utilities.AppUtilities;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link TotalesItemCallbacks}
 * interface.
 */
public class TotalesItemFragment extends Fragment implements AbsListView.OnItemClickListener {

    private ServicioPresupuestoBusiness servicioPresupuestoBusiness = new ServicioPresupuestoBusiness();

    private TotalesItemCallbacks mListener;

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_totalesitem_list, container, false);

        ((RadioButton) view.findViewById(R.id.fragment_totalesitem_list_rb_diario)).setText(formatTextoTotal(getResources().getString(R.string.diario), AppUtilities.formatearImporte(servicioPresupuestoBusiness.obtenerTotalDiario())));
        ((RadioButton) view.findViewById(R.id.fragment_totalesitem_list_rb_semanal)).setText(formatTextoTotal(getResources().getString(R.string.semanal), AppUtilities.formatearImporte(servicioPresupuestoBusiness.obtenerTotalSemanal())));
        ((RadioButton) view.findViewById(R.id.fragment_totalesitem_list_rb_mensual)).setText(formatTextoTotal(getResources().getString(R.string.mensual), AppUtilities.formatearImporte(servicioPresupuestoBusiness.obtenerTotalMensual())));
        ((RadioButton) view.findViewById(R.id.fragment_totalesitem_list_rb_anual)).setText(formatTextoTotal(getResources().getString(R.string.anual), AppUtilities.formatearImporte(servicioPresupuestoBusiness.obtenerTotalAnual())));

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
            mListener = (TotalesItemCallbacks) activity;
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
    public interface TotalesItemCallbacks {
        // TODO: Update argument type and name
        void onTotalesItemSelected(String id);
    }

    private Spanned formatTextoTotal(String texto, String importe) {
        return (Html.fromHtml("<big>" + texto + ":" + "&nbsp;&nbsp;" + "<b>" + "<font color = #006fff>" + "$" + importe + "</font>" + "</b>" + "</big>"));
    }

}
