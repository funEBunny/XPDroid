package com.funebunny.xpdroid.main.ui.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.funebunny.xpdroid.R;
import com.funebunny.xpdroid.business.gasto.model.Gasto;
import com.funebunny.xpdroid.business.gasto.model.GastoFavorito;
import com.funebunny.xpdroid.business.gasto.service.ServicioGastosBusiness;
import com.funebunny.xpdroid.business.presupuesto.model.Presupuesto;
import com.funebunny.xpdroid.business.presupuesto.service.ServicioPresupuestoBusiness;
import com.funebunny.xpdroid.main.ui.adapter.ButtonAdapterGastoFavorito;
import com.funebunny.xpdroid.main.ui.adapter.ExpandableAdapaterAlertaPresupuesto;
import com.funebunny.xpdroid.main.ui.fragment.GastosFavoritosItemFragment;
import com.funebunny.xpdroid.main.ui.fragment.GastosProgramablesItemFragment;
import com.funebunny.xpdroid.main.ui.fragment.HistorialGastosItemFragment;
import com.funebunny.xpdroid.main.ui.fragment.NavigationDrawerFragment;
import com.funebunny.xpdroid.main.ui.fragment.NotificacionesItemFragment;
import com.funebunny.xpdroid.main.ui.fragment.PresupuestoItemFragment;
import com.funebunny.xpdroid.utilities.AppConstants;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;


public class MainActivity extends XPDroidActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        HistorialGastosItemFragment.HistorialGastosItemCallbacks,
        GastosFavoritosItemFragment.GastosFavoritosItemCallbacks,
        GastosProgramablesItemFragment.GastosProgramablesItemCallbacks,
        PresupuestoItemFragment.PresupuestoItemCallbacks,
        NotificacionesItemFragment.NotificacionesItemCallbacks {

    // Fragment managing the behaviors, interactions and presentation of the navigation drawer.
    private NavigationDrawerFragment mNavigationDrawerFragment;
    // Used to store the last screen title. For use in {@link #restoreActionBar()}.
    private CharSequence mTitle;

    private String[] navigationDrawerItems;
    private DrawerLayout drawerLayout;
    private ListView drawerList;

    private ServicioPresupuestoBusiness servicioPresupuestoBusiness = new ServicioPresupuestoBusiness();
    private ServicioGastosBusiness servicioGastosBusiness = new ServicioGastosBusiness();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationDrawerItems = getResources().getStringArray(R.array.nav_drawer_items);
        //drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);  //No sé para qué es
        drawerList = (ListView) findViewById(R.id.navigation_drawer);

        drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.navigation_drawer_list_item, navigationDrawerItems));
        //drawerList.setOnClickListener(new DrawerItemClickListener());

        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

        //To allow Up navigation with the app icon in the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override //Implementing method from NavigationDrawerFragment.NavigationDrawerCallbacks
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();

        //TODO: ACA DEBERIA GENERAR INSTANCIAS CADA UNO DE LOS FRAGMENTS DE ACUERDO AL VALOR DE position

        switch (position) {
            case 0: { //Pagina de Inicio
                fragmentManager.beginTransaction()
                        .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                        .commit();
                break;
            }
            case 1: { //Historial de Gastos
                fragmentManager.beginTransaction()
                        .replace(R.id.container, HistorialGastosItemFragment.newInstance(position + 1))
                        .commit();
                break;
            }
            case 2: { //Gastos Favoritos
                fragmentManager.beginTransaction()
                        .replace(R.id.container, GastosFavoritosItemFragment.newInstance(position + 1))
                        .commit();
                break;
            }
            case 3: { //Gastos Gastos Programbables
                fragmentManager.beginTransaction()
                        .replace(R.id.container, GastosProgramablesItemFragment.newInstance(position + 1))
                        .commit();
                break;
            }
            case 4: { //Presupuesto
                fragmentManager.beginTransaction()
                        .replace(R.id.container, PresupuestoItemFragment.newInstance(position + 1))
                        .commit();
                break;
            }
            case 5: { //Recordatorios
//                fragmentManager.beginTransaction()
//                        .replace(R.id.container, GastosFavoritosItemFragment.newInstance(position + 1))
//                        .commit();
//                break;
            }
            case 6: { //Notificaciones
                fragmentManager.beginTransaction()
                        .replace(R.id.container, NotificacionesItemFragment.newInstance(position + 1))
                        .commit();
                break;
            }
            case 7: { //Ayuda
//                fragmentManager.beginTransaction()
//                        .replace(R.id.container, GastosFavoritosItemFragment.newInstance(position + 1))
//                        .commit();
//                break;
            }
        }
    }

    @Override //Implementing method from HistorialGastosItemFragment.HistorialGastosItemCallbacks
    public void onHistorialGastosItemSelected(String id) {
        FragmentManager fragmentManager = getSupportFragmentManager();


    }


    public void crearGastoPorFavorito(View view) {
        // Este método es llamado cuando se presiona un botón de gasto favorito en la pantalla principal
        View parentView = (View) view.getParent();
        GastoFavorito gf = (GastoFavorito) parentView.findViewById(R.id.gasto_favorito_button_ll_main).getTag();

        String descripcion = gf.getDescripcion();
        String importe = gf.getImporte();
        String categoria = gf.getCategoria();
        Date fecha = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String fechaFormat = dateFormat.format(fecha);
        Gasto gasto = servicioGastosBusiness.guardarGasto(descripcion, importe, categoria, fechaFormat);
        servicioPresupuestoBusiness.calcularTotales(gasto);

        // Resfresh del fragment principal
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(1))
                .commit();

        //Mostrar mensaje de agregar gasto
        int gasto_guardado_mensaje = R.string.gasto_guardado_mensaje;
        showMessage(gasto_guardado_mensaje);
    }


    @Override //Implementing method from GastosFavoritosItemFragment.GastosFavoritosItemCallbacks
    public void onGastosFavoritosItemSelected(String id) {
        //FragmentManager fragmentManager = getSupportFragmentManager();

    }

    @Override
    //Implementing method from GastosProgramablesItemFragment.GastosProgramablesItemCallbacks
    public void onGastosProgramablesItemSelected(String id) {
        //FragmentManager fragmentManager = getSupportFragmentManager();

    }

    @Override
    public void onNotificacionesItemSelected(String id) {

    }

    @Override
    public void onPresupuestoItemSelected(String id) {

    }
    /*
    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }
    */

    public void onSectionAttached(int number) {
        String[] navigationDrawerItems = getResources().getStringArray(R.array.nav_drawer_items);
        for (int i = 1; i < navigationDrawerItems.length + 1; i++) {
            if (number == i) {
                mTitle = navigationDrawerItems[i - 1];
                break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen if the drawer is not showing.
            // Otherwise, let the drawer decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            //restoreActionBar();
            ActionBar actionBar = getSupportActionBar();
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(mTitle);

            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

/*        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_agregar_gasto) {
            Intent intentCrearGasto = new Intent(this, CrearGastoActivity.class);
            startActivity(intentCrearGasto);

            //Mostrar mensaje de agregar gasto
            Toast toast = Toast.makeText(this, R.string.action_crear_gasto, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        }*/

        //Animacion desde derecha a izquierda
        Bundle animation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.animator.in_right, R.animator.out_left).toBundle();

        switch (id) {
            case R.id.action_settings: {
                return true;
            }

            case R.id.action_crear_gasto: {
                Intent intentCrearGasto = new Intent(this, CrearGastoActivity.class);
                startActivity(intentCrearGasto, animation);

                //Mostrar mensaje de crear gasto
                Toast toast = Toast.makeText(this, R.string.action_crear_gasto, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
                break;
            }

            case R.id.action_crear_gasto_favorito: {
                Intent intentCrearGastoFavorito = new Intent(this, CrearGastoFavoritoActivity.class);
                startActivity(intentCrearGastoFavorito, animation);

                //Mostrar mensaje de crear gasto favorito
                Toast toast = Toast.makeText(this, R.string.action_crear_gasto_favorito, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
                break;
            }

            case R.id.action_crear_gasto_programable: {
                Intent intentCrearGastoProgramable = new Intent(this, CrearGastoProgramableActivity.class);
                startActivity(intentCrearGastoProgramable, animation);

                //Mostrar mensaje de crear gasto programable
                Toast toast = Toast.makeText(this, R.string.action_crear_gasto_programable, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
                break;
            }

            case R.id.action_crear_presupuesto: {

                if (servicioPresupuestoBusiness.isLimitePresupuestoAlcanzado()) {
                    //Mostrar mensaje de crear gasto presupuesto
                    Toast toast = Toast.makeText(this, R.string.info_limite_presupuesto_alcanzado, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                    break;
                }

                Intent intentCrearPresupuesto = new Intent(this, CrearPresupuestoActivity.class);
                startActivity(intentCrearPresupuesto, animation);

                //Mostrar mensaje de crear gasto presupuesto
                Toast toast = Toast.makeText(this, R.string.action_crear_presupuesto, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }


    //TODO: VA A SER UTILIZADO SOLO PARA LA OPCION DE NAV DRAWER "Inicio"

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        private ServicioGastosBusiness servicioGastosBusiness = new ServicioGastosBusiness();
        private List<GastoFavorito> gastosFavoritos = new ArrayList<GastoFavorito>();


        private ServicioPresupuestoBusiness servicioPresupuestoBusiness = new ServicioPresupuestoBusiness();

        public static PlaceholderFragment newInstance(int itemSelected) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(AppConstants.ARG_DRAWER_ITEM_POSITION, itemSelected); // item seleccionado del navigation drawer (arranca desde "Inicio" que es posicion 0, "Gastos Favoritos" que es posicion 1, y asi...)
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            Bundle args = this.getArguments();
            int sectionNumber = (int) args.get(AppConstants.ARG_DRAWER_ITEM_POSITION);
            // item seleccionado del navigation drawer (arranca desde "Inicio" que es posicion 0, "Gastos Favoritos" que es posicion 1, y asi...)
            // OJO que desde MainActiviy.onNavigationDrawerItemSelected se manda la posicion + 1

            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            //Mes y total acumulado mensual en pantalla de Inicio
            String mes = new DateFormatSymbols(new Locale("es", "ES")).getMonths()[Calendar.getInstance().get(Calendar.MONTH)];
            ((TextView) rootView.findViewById(R.id.fragment_main_tv_mes)).setText(mes.toUpperCase());
            ((TextView) rootView.findViewById(R.id.fragment_main_tv_total_mensual)).setText("$" + servicioPresupuestoBusiness.obtenerTotalMensual());
            List<Presupuesto> presupuestos = servicioPresupuestoBusiness.obtenerPresupuesto();
            RelativeLayout rl = (RelativeLayout) rootView.findViewById(R.id.fragment_totales_rl);

            int id =0;
            for (Presupuesto presupuesto:presupuestos) {
                String periodo = presupuesto.getPeriodo();
                id++;
                if (AppConstants.PERIODO_DIARIO.equalsIgnoreCase(periodo)){
                    TextView tv = new TextView(this.getActivity().getApplicationContext());
                    tv.setText("Total Diario = $" + servicioPresupuestoBusiness.obtenerTotalDiario());
                    tv.setId(id);
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
                    tv.setLayoutParams(lp);
                    rl.addView(tv);
                }
                if (AppConstants.PERIODO_SEMANAL.equalsIgnoreCase(periodo)){
                    TextView tv = new TextView(this.getActivity().getApplicationContext());
                    tv.setText("Total Semanal = $" + servicioPresupuestoBusiness.obtenerTotalSemanal());
                    tv.setId(id);
                    RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
                    if (id>1){
                        lp2.addRule(RelativeLayout.BELOW, id - 1);
                    }
                    tv.setLayoutParams(lp2);
                    rl.addView(tv);
                }
                if (AppConstants.PERIODO_ANUAL.equalsIgnoreCase(periodo)){
                    TextView tv = new TextView(this.getActivity().getApplicationContext());
                    tv.setText("Total Anual = $" + servicioPresupuestoBusiness.obtenerTotalAnual());
                    tv.setId(id);
                    RelativeLayout.LayoutParams lp3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
                    if (id>1){
                        lp3.addRule(RelativeLayout.BELOW, id - 1);
                    }
                    tv.setLayoutParams(lp3);
                    rl.addView(tv);
                }
            }
            //Verificación de presupuesto alcanzado
            verificarPresupuestos(rootView);

            //Botonera de Favoritos en pantalla de Inicio
            gastosFavoritos.addAll(servicioGastosBusiness.obtenerGastosFavoritos());
            if (gastosFavoritos.size() == 0) {
                //Mensaje en pantalla principal que indica que no hay gastos favoritos grabados hasta el momento
                ((TextView) rootView.findViewById(R.id.fragment_main_tv_favoritos)).setText(R.string.no_hay_favoritos);
            } else {
                ButtonAdapterGastoFavorito mAdapter = new ButtonAdapterGastoFavorito(getActivity(), R.layout.gasto_favorito_button, gastosFavoritos);
                ((GridView) rootView.findViewById(R.id.fragment_main_gv_favoritos)).setAdapter(mAdapter);
            }

            return rootView;
        }

        @Override
        public void onResume() {
            super.onResume();

            //Mes y total acumulado mensual en pantalla de Inicio
            String mes = new DateFormatSymbols(new Locale("es", "ES")).getMonths()[Calendar.getInstance().get(Calendar.MONTH)];
            ((TextView) getView().findViewById(R.id.fragment_main_tv_mes)).setText(mes.toUpperCase());
            ((TextView) getView().findViewById(R.id.fragment_main_tv_total_mensual)).setText("$" + servicioPresupuestoBusiness.obtenerTotalMensual());
            //Verificación de presupuesto alcanzado
            verificarPresupuestos(getView());

        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(getArguments().getInt(AppConstants.ARG_DRAWER_ITEM_POSITION));
        }

        // Estos 2 métodos (onActivityCreated y onCreateOptionsMenu) anulan el menu anterior y setean el menu del Fragment seleccionado (actual)
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            setHasOptionsMenu(true);
        }

        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.menu_crear_gasto, menu);
        }

        private void verificarPresupuestos(View view) {

            ArrayList<String> presupuestosAlcanzados = new ArrayList<String>();

            if (servicioPresupuestoBusiness.isPresupuestoDiarioAlcanzado()) {
                presupuestosAlcanzados.add(getResources().getString(R.string.diario));
            }
            if (servicioPresupuestoBusiness.isPresupuestoSemanalAlcanzado()) {
                presupuestosAlcanzados.add(getResources().getString(R.string.semanal));
            }
            if (servicioPresupuestoBusiness.isPresupuestoMensualAlcanzado()) {
                presupuestosAlcanzados.add(getResources().getString(R.string.mensual));
            }
            if (servicioPresupuestoBusiness.isPresupuestoAnualAlcanzado()) {
                presupuestosAlcanzados.add(getResources().getString(R.string.anual));
            }

            if (!presupuestosAlcanzados.isEmpty()) {
                ExpandableListView expandablePresupuesto = (ExpandableListView) view.findViewById(R.id.fragment_main_el_alerta_presupuesto);
                ExpandableAdapaterAlertaPresupuesto expandablePresupuestoAdapter = new ExpandableAdapaterAlertaPresupuesto(getResources().getString(R.string.alerta_presupuesto), presupuestosAlcanzados);
                expandablePresupuestoAdapter.setInflater(LayoutInflater.from(view.getContext()), this.getActivity());
                expandablePresupuesto.setAdapter(expandablePresupuestoAdapter);
            }

        }

    }

}
