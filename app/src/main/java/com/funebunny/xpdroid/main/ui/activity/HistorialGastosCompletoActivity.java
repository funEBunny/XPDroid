package com.funebunny.xpdroid.main.ui.activity;

import android.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.funebunny.xpdroid.R;
import com.funebunny.xpdroid.main.ui.fragment.DatePickerFragment;
import com.funebunny.xpdroid.utilities.AppConstants;

import java.util.Calendar;

public class HistorialGastosCompletoActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_gastos_completo);

        // Fijar por defecto la fecha del día
        final Calendar c = Calendar.getInstance();
        EditText fecha = (EditText) findViewById(R.id.activity_historial_fecha_desde);
        fecha.setText(new StringBuilder().append(c.get(Calendar.DAY_OF_MONTH)).append(AppConstants.SEPARADOR_FECHA).append(c.get(Calendar.MONTH) + 1).append(AppConstants.SEPARADOR_FECHA).append(c.get(Calendar.YEAR)));
        fecha = (EditText) findViewById(R.id.activity_historial_fecha_hasta);
        fecha.setText(new StringBuilder().append(c.get(Calendar.DAY_OF_MONTH) - 1).append(AppConstants.SEPARADOR_FECHA).append(c.get(Calendar.MONTH) + 1).append(AppConstants.SEPARADOR_FECHA).append(c.get(Calendar.YEAR)));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_historial_gastos_completo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                overridePendingTransition(R.animator.in_left, R.animator.out_right);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void mostrarDatePickerFechaDesde(View v) {
        EditText fecha = (EditText) this.findViewById(R.id.activity_historial_fecha_desde);
        DialogFragment newFragment = DatePickerFragment.newInstance(fecha);
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void mostrarDatePickerFechaHasta(View v) {
        EditText fecha = (EditText) this.findViewById(R.id.activity_historial_fecha_hasta);
        DialogFragment newFragment = DatePickerFragment.newInstance(fecha);
        newFragment.show(getFragmentManager(), "datePicker");
    }

}
