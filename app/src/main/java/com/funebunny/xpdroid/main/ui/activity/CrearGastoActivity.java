package com.funebunny.xpdroid.main.ui.activity;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.funebunny.xpdroid.R;
import com.funebunny.xpdroid.business.gasto.model.Gasto;
import com.funebunny.xpdroid.business.gasto.service.ServicioGastosBusiness;
import com.funebunny.xpdroid.business.presupuesto.service.ServicioPresupuestoBusiness;
import com.funebunny.xpdroid.main.ui.fragment.DatePickerFragment;
import com.funebunny.xpdroid.utilities.AppConstants;

import java.util.Calendar;

public class CrearGastoActivity extends XPDroidActivity {

    private ServicioGastosBusiness servicioGastosBusiness = new ServicioGastosBusiness();
    private ServicioPresupuestoBusiness servicioPresupuestoBusiness = new ServicioPresupuestoBusiness();
    private Gasto gasto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_gasto);

        Bundle bGasto = getIntent().getExtras();
        if (bGasto != null) {
            gasto = (Gasto) bGasto.getSerializable(AppConstants.GASTO);
            ((EditText) findViewById(R.id.activity_crear_gasto_et_descripcion)).setText(gasto.getDescripcion());
            ((EditText) findViewById(R.id.activity_crear_gasto_et_importe)).setText(gasto.getImporte());
            Spinner sCategoria = (Spinner) findViewById(R.id.activity_crear_gasto_sp_categoria);
            sCategoria.setSelection(((ArrayAdapter) sCategoria.getAdapter()).getPosition(gasto.getCategoria()));

            setTitle(R.string.title_activity_editar_gasto);
        }

        // Fijar por defecto la fecha del día
        final Calendar c = Calendar.getInstance();
        EditText fecha = (EditText) findViewById(R.id.activity_crear_gasto_et_fecha);
        fecha.setText(new StringBuilder().append(c.get(Calendar.DAY_OF_MONTH)).append(AppConstants.SEPARADOR_FECHA).append(c.get(Calendar.MONTH) + 1).append(AppConstants.SEPARADOR_FECHA).append(c.get(Calendar.YEAR)));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_crear_gasto, menu);
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

    public void mostrarDatePicker(View v) {
        EditText fecha = (EditText) this.findViewById(R.id.activity_crear_gasto_et_fecha);
        DialogFragment newFragment = DatePickerFragment.newInstance(fecha);
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void guardarGasto(View view) {

        String descripcion = ((EditText) findViewById(R.id.activity_crear_gasto_et_descripcion)).getText().toString();
        String fecha = ((EditText) findViewById(R.id.activity_crear_gasto_et_fecha)).getText().toString();
        String importe = ((EditText) findViewById(R.id.activity_crear_gasto_et_importe)).getText().toString();
        String categoria = ((Spinner) findViewById(R.id.activity_crear_gasto_sp_categoria)).getSelectedItem().toString();

        if (gasto == null || gasto.getId()== null){
            gasto = servicioGastosBusiness.guardarGasto(descripcion, importe, categoria, fecha);
        }else{
            gasto.setDescripcion(descripcion);
            gasto.setFecha(fecha);
            gasto.setImporte(importe);
            gasto.setCategoria(categoria);
            servicioGastosBusiness.actualizarGasto(gasto);
        }
        servicioPresupuestoBusiness.calcularTotales(gasto);
        servicioPresupuestoBusiness.validarPresupuesto();

        //Mostrar mensaje de agregar gasto
        int gasto_guardado_mensaje = R.string.gasto_guardado_mensaje;
        showMessage(gasto_guardado_mensaje);
        this.finish();
    }

}
