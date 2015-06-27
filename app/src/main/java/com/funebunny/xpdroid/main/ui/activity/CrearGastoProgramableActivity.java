package com.funebunny.xpdroid.main.ui.activity;

import android.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.funebunny.xpdroid.R;
import com.funebunny.xpdroid.main.ui.fragment.TimePickerFragment;

public class CrearGastoProgramableActivity extends XPDroidActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_gasto_programable);
        //Listener para ocultar teclado cuando se toca fuera de un Edit Text
        setupUI(findViewById(R.id.crear_gasto_programable));

        Spinner repeticion = (Spinner) findViewById(R.id.repeticion);

        repeticion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 if (((Spinner) findViewById(R.id.repeticion)).getSelectedItem().toString() == getResources().getString(R.string.semanal)) {
                     findViewById(R.id.dias_semana).setVisibility(View.VISIBLE);
                 } else {
                     findViewById(R.id.dias_semana).setVisibility(View.INVISIBLE);
                 }
             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {

             }
         }

        );

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_crear_gasto_programable, menu);
        return true;
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
        }

        return super.onOptionsItemSelected(item);
    }

    // > Added by PRB

    public void mostrarTimePicker(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void guardarGastoProgramable(View view) {

    }
    // < Added by PRB
}
