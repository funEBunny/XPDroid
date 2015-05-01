package com.funebunny.xpdroid.main.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.funebunny.xpdroid.R;


public class MainActivity   extends ActionBarActivity
                            implements  NavigationDrawerFragment.NavigationDrawerCallbacks,
                                        GastosFavoritosItemFragment.GastosFavoritosItemCallbacks  {

    // Fragment managing the behaviors, interactions and presentation of the navigation drawer.
    private NavigationDrawerFragment mNavigationDrawerFragment;
    // Used to store the last screen title. For use in {@link #restoreActionBar()}.
    private CharSequence mTitle;

    private String[] navigationDrawerItems;
    private DrawerLayout drawerLayout;
    private ListView drawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationDrawerItems = getResources().getStringArray(R.array.nav_drawer_items);
        //drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);  //No sé para qué es
        drawerList   = (ListView) findViewById(R.id.navigation_drawer);

        drawerList.setAdapter(new ArrayAdapter<String>(this,R.layout.navigation_drawer_list_item, navigationDrawerItems));
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

        switch(position){
            case 0: { //Pagina de Inicio
                fragmentManager.beginTransaction()
                        .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                        .commit();
                break;
            }
            case 1: { //Gastos Favoritos
                fragmentManager.beginTransaction()
                        .replace(R.id.container, GastosFavoritosItemFragment.newInstance(position + 1))
                        .commit();
                break;
            }
        }
    }

    @Override //Implementing method from GastosFavoritosItemFragment.GastosFavoritosItemCallbacks
    public void onGastosFavoritosItemSelected(String id) {
        FragmentManager fragmentManager = getSupportFragmentManager();

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
        //String[] navigationDrawerItems = getResources().getStringArray(R.array.nav_drawer_items);
        for(int i=1;i< navigationDrawerItems.length+1;i++) {
            if (number==i){
                    mTitle = navigationDrawerItems[i-1];
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
            //actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_agregar) {
            Intent intentCrearGasto = new Intent(this, CrearGastoActivity.class);
            startActivity(intentCrearGasto);

            //Mostrar mensaje de agregar gasto
            Toast toast = Toast.makeText(this, R.string.action_crear_gasto, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
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
        private static final String ARG_DRAWER_ITEM_POSITION = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int itemSelected) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_DRAWER_ITEM_POSITION, itemSelected); // item seleccionado del navigation drawer (arranca desde "Inicio" que es posicion 0, "Gastos Favoritos" que es posicion 1, y asi...)
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            Bundle args = this.getArguments();
            int sectionNumber = (int) args.get(ARG_DRAWER_ITEM_POSITION);
            // item seleccionado del navigation drawer (arranca desde "Inicio" que es posicion 0, "Gastos Favoritos" que es posicion 1, y asi...)
            // OJO que desde MainActiviy.onNavigationDrawerItemSelected se manda la posicion + 1

            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_DRAWER_ITEM_POSITION));
        }

    }

}
