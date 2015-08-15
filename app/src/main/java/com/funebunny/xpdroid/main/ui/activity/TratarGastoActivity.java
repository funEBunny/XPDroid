package com.funebunny.xpdroid.main.ui.activity;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.funebunny.xpdroid.R;
import com.funebunny.xpdroid.gastos.dao.Gasto;
import com.funebunny.xpdroid.gastos.backend.ServicioGastos;
import com.funebunny.xpdroid.main.ui.fragment.DatePickerFragment;
import com.funebunny.xpdroid.utilities.AppConstants;

public class TratarGastoActivity extends XPDroidActivity {

    private Gasto gasto;
    private ServicioGastos servicioGastos = new ServicioGastos();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tratar_gasto);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bGasto = getIntent().getExtras();
        gasto = (Gasto) bGasto.getSerializable(AppConstants.GASTO);

        if (gasto != null) {

            ((EditText) findViewById(R.id.activity_tratar_gasto_et_descripcion)).setText(gasto.getDescripcion());
            ((EditText) findViewById(R.id.activity_tratar_gasto_et_fecha)).setText(gasto.getFecha());
            ((EditText) findViewById(R.id.activity_tratar_gasto_et_importe)).setText(gasto.getImporte());
            Spinner sCategoria = (Spinner) findViewById(R.id.activity_tratar_gasto_sp_categoria);
            sCategoria.setSelection(((ArrayAdapter) sCategoria.getAdapter()).getPosition(gasto.getCategoria()));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_tratar_gasto, menu);
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

    //>Added by PRB
    public void mostrarDatePicker(View v) {
        EditText fecha = (EditText) this.findViewById(R.id.activity_tratar_gasto_et_fecha);
        DialogFragment newFragment = DatePickerFragment.newInstance(fecha);
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void actualizarGasto(View v) {
        if (gasto != null) {
            gasto.setDescripcion(((EditText) findViewById(R.id.activity_tratar_gasto_et_descripcion)).getText().toString());
            gasto.setImporte(((EditText) findViewById(R.id.activity_tratar_gasto_et_importe)).getText().toString());
            gasto.setFecha(((EditText) findViewById(R.id.activity_tratar_gasto_et_fecha)).getText().toString());
            gasto.setCategoria(((Spinner) findViewById(R.id.activity_tratar_gasto_sp_categoria)).getSelectedItem().toString());
            servicioGastos.actualizarGasto(gasto);
            //Mostrar mensaje de gasto actualizado
            int gasto_actualizado_mensaje = R.string.gasto_actualizado_mensaje;
            showMessage(gasto_actualizado_mensaje);
            this.finish();
        }
    }

    public void eliminarGasto(View v) {
        if (gasto != null) {
            servicioGastos.eliminarGasto(gasto.getgId());
            //Mostrar mensaje de gasto eliminado
            int gasto_eliminado_mensaje = R.string.gasto_eliminado_mensaje;
            showMessage(gasto_eliminado_mensaje);
            this.finish();
        }
    }
    //<Added by PRB
}
