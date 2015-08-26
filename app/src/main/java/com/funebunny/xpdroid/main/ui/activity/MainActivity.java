package com.funebunny.xpdroid.main.ui.activity;

import android.app.Activity;
import android.app.ActivityOptions;
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
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.funebunny.xpdroid.R;
import com.funebunny.xpdroid.gastos.business.model.Gasto;
import com.funebunny.xpdroid.gastos.business.model.GastoFavorito;
import com.funebunny.xpdroid.gastos.business.model.GastoProgramable;
import com.funebunny.xpdroid.gastos.business.model.Objetivo;
import com.funebunny.xpdroid.gastos.business.service.ServicioGastosBusiness;
import com.funebunny.xpdroid.gastos.business.service.ServicioObjetivosBusiness;
import com.funebunny.xpdroid.main.ui.activity.adapter.ButtonAdapterGastoFavorito;
import com.funebunny.xpdroid.main.ui.fragment.GastosFavoritosItemFragment;
import com.funebunny.xpdroid.main.ui.fragment.GastosProgramablesItemFragment;
import com.funebunny.xpdroid.main.ui.fragment.HistorialGastosItemFragment;
import com.funebunny.xpdroid.main.ui.fragment.NavigationDrawerFragment;
import com.funebunny.xpdroid.main.ui.fragment.NotificacionesItemFragment;
import com.funebunny.xpdroid.main.ui.fragment.ObjetivosItemFragment;
import com.funebunny.xpdroid.utilities.AppConstants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MainActivity extends XPDroidActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        HistorialGastosItemFragment.HistorialGastosItemCallbacks,
        GastosFavoritosItemFragment.GastosFavoritosItemCallbacks,
        GastosProgramablesItemFragment.GastosProgramablesItemCallbacks,
        ObjetivosItemFragment.ObjetivosItemCallbacks,
        NotificacionesItemFragment.NotificacionesItemCallbacks {

    // Fragment managing the behaviors, interactions and presentation of the navigation drawer.
    private NavigationDrawerFragment mNavigationDrawerFragment;
    // Used to store the last screen title. For use in {@link #restoreActionBar()}.
    private CharSequence mTitle;

    private String[] navigationDrawerItems;
    private DrawerLayout drawerLayout;
    private ListView drawerList;

    private ServicioObjetivosBusiness servicioObjetivosBusiness = new ServicioObjetivosBusiness();
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
            case 4: { //Objetivos
                fragmentManager.beginTransaction()
                        .replace(R.id.container, ObjetivosItemFragment.newInstance(position + 1))
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

    public void verHistorial(View view) {
        Intent intentHistorialGastosCompleto = new Intent(this, HistorialGastosCompletoActivity.class);
        //Animacion desde derecha a izquierda
        Bundle animation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.animator.in_right, R.animator.out_left).toBundle();
        startActivity(intentHistorialGastosCompleto, animation);

        //Mostrar mensaje de crear gasto objetivo
        Toast toast = Toast.makeText(this, R.string.action_ver_historial, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

//    public void tratarGasto(View view){
//
//        Gasto gasto = (Gasto) view.findViewById(R.id.historial_gastos_list_item).getTag();
//        Bundle bGasto = new Bundle();
//        bGasto.putSerializable(AppConstants.GASTO, gasto);
//
//        Intent i = new Intent(this, TratarGastoActivity.class);
//        i.putExtras(bGasto);
//        startActivity(i);
//    }
//
//    public void tratarGastoFavorito(View view){
//
//        GastoFavorito gf = (GastoFavorito) view.findViewById(R.id.gastos_favoritos_list_item_ll_main).getTag();
//        Bundle bgf = new Bundle();
//        bgf.putSerializable(AppConstants.GASTO_FAVORITO, gf);
//
//        Intent i = new Intent(this, TratarGastoFavoritoActivity.class);
//        i.putExtras(bgf);
//        startActivity(i);
//    }

    public void crearGastoPorFavorito(View view){
        // Este método es llamado cuando se presiona un botón de gasto favorito en la pantalla principal
        View parentView = (View) view.getParent();
        GastoFavorito gf = (GastoFavorito) parentView.findViewById(R.id.gasto_favorito_button_ll_main).getTag();

        String descripcion = gf.getDescripcion();
        String importe = gf.getImporte();
        String categoria = gf.getCategoria();
        Date fecha = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String fechaFormat = dateFormat.format(fecha);
        servicioGastosBusiness.guardarGasto(descripcion, importe, categoria, fechaFormat);
        //Mostrar mensaje de agregar gasto
        int gasto_guardado_mensaje = R.string.gasto_guardado_mensaje;
        showMessage(gasto_guardado_mensaje);
    }

    public void tratarObjetivo(View view){

        Objetivo obj = (Objetivo) view.findViewById(R.id.objetivos_list_item_ll_main).getTag();
        Bundle bobj = new Bundle();
        bobj.putSerializable(AppConstants.OBJETIVO, obj);
        Intent i = new Intent(this, TratarObjetivoActivity.class);
        i.putExtras(bobj);
        startActivity(i);

    }

    public void tratarGastoProgramable(View view){

        GastoProgramable gasto = (GastoProgramable) view.findViewById(R.id.gastos_programables_list_item).getTag();
        Bundle bGasto = new Bundle();
        bGasto.putSerializable(AppConstants.GASTO_PROGRAMABLE,gasto);
        Intent i = new Intent(this, TratarGastoProgramableActivity.class);
        i.putExtras(bGasto);
        startActivity(i);
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
    public void onObjetivosItemSelected(String id) {

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

        switch (id){
            case R.id.action_settings: { return true; }

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

            case R.id.action_crear_objetivo: {

                if (servicioObjetivosBusiness.isLimiteObjetivosAlcanzado()){
                    //Mostrar mensaje de crear gasto objetivo
                    Toast toast = Toast.makeText(this, R.string.info_limite_objetivos_alcanzado, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                    break;
                }

                Intent intentCrearObjetivo = new Intent(this, CrearObjetivoActivity.class);
                startActivity(intentCrearObjetivo, animation);

                //Mostrar mensaje de crear gasto objetivo
                Toast toast = Toast.makeText(this, R.string.action_crear_objetivo, Toast.LENGTH_SHORT);
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
        private GridView mGridView;
        private ButtonAdapterGastoFavorito mAdapter;

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

            gastosFavoritos.addAll(servicioGastosBusiness.obtenerGastosFavoritos());

            if (gastosFavoritos.size() == 0){
                //Mensaje en pantalla principal que indica que no hay gastos favoritos grabados hasta el momento
                FrameLayout mainLayout = (FrameLayout) rootView.findViewById(R.id.fragment_main_fl_main);
                TextView noHayFavoritos = new TextView(this.getActivity());
                noHayFavoritos.setText(R.string.no_hay_favoritos);
                noHayFavoritos.setGravity(Gravity.CENTER);
                mainLayout.addView(noHayFavoritos);
            }

            mAdapter = new ButtonAdapterGastoFavorito(getActivity(), R.layout.gasto_favorito_button, gastosFavoritos);

            mGridView = (GridView) rootView.findViewById(R.id.fragment_main_gv_favoritos);
            mGridView.setAdapter(mAdapter);

            return rootView;
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
    }

}
