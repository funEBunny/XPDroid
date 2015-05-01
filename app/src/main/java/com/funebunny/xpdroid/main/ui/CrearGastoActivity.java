package com.funebunny.xpdroid.main.ui;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.funebunny.xpdroid.R;
import com.funebunny.xpdroid.gastos.backend.ServicioGastos;
import com.funebunny.xpdroid.utilities.AppConstants;

import java.util.Calendar;

public class CrearGastoActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_gasto);

        // Botón para volver a actividad anterior
        getSupportActionBar().setHomeButtonEnabled(true);

        // Fijar por defecto la fecha del día
        final Calendar c = Calendar.getInstance();
        EditText fecha = (EditText) findViewById(R.id.fecha);
        fecha.setText(new StringBuilder().append(c.get(Calendar.DAY_OF_MONTH)).append(AppConstants.SEPARADOR_FECHA).append(c.get(Calendar.MONTH) + 1).append(AppConstants.SEPARADOR_FECHA).append(c.get(Calendar.YEAR)));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_crear_gasto, menu);
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

    //>Added by PRB
    public void mostrarDatePicker(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }
    //<Added by PRB

    public void guardarGasto(View view){
        ServicioGastos servicioGastos = new ServicioGastos();

        String descripcion = ((EditText) findViewById(R.id.descripcion)).getText().toString();

        String fecha = ((EditText) findViewById(R.id.fecha)).getText().toString();

        String importe = ((EditText) findViewById(R.id.importe)).getText().toString();

        String categoria = ((Spinner) findViewById(R.id.categoria)).getSelectedItem().toString();

        servicioGastos.guardarGasto(descripcion, importe, categoria, fecha);
        //Mostrar mensaje de agregar gasto
        Toast toast = Toast.makeText(this, R.string.gasto_guardado_mensaje, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
        this.finish();
    }
}
