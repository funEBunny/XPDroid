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
import com.funebunny.xpdroid.business.historial.service.ServicioHistorialBusiness;
import com.funebunny.xpdroid.business.presupuesto.service.ServicioPresupuestoBusiness;
import com.funebunny.xpdroid.main.ui.fragment.DatePickerFragment;
import com.funebunny.xpdroid.utilities.AppConstants;


public class CrearGastoActivity extends XPDroidActivity {

    private ServicioGastosBusiness servicioGastosBusiness = new ServicioGastosBusiness();
    private ServicioPresupuestoBusiness servicioPresupuestoBusiness = new ServicioPresupuestoBusiness();
    private ServicioHistorialBusiness servicioHistorialBusiness = new ServicioHistorialBusiness();
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

            if (gasto.getId() != null) {    //Se está editando un gasto, entonces tomo la fecha del gasto
                ((EditText) findViewById(R.id.activity_crear_gasto_et_fecha)).setText(gasto.getFecha());
                setTitle(R.string.title_activity_editar_gasto);
            } else {    //Se trata de un gasto programable, entonces uso fecha del día
                ((EditText) findViewById(R.id.activity_crear_gasto_et_fecha)).setText(getFechaActual());
            }

        } else {
            // Fijar por defecto la fecha del día cuando se trata de un gasto nuevo
            ((EditText) findViewById(R.id.activity_crear_gasto_et_fecha)).setText(getFechaActual());
        }
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
        String categoria = ((Spinner) findViewById(R.id.activity_crear_gasto_sp_categoria)).getSelectedItem().toString();
        EditText etImporte = (EditText) findViewById(R.id.activity_crear_gasto_et_importe);
        String importe = String.valueOf(etImporte.getText());

        //Validar importe obligatorio
        if (importe.equals("")) {
            etImporte.setError(getResources().getString(R.string.campo_obligatorio));
            return;
        }
        //Validar primer dígito del Importe
        if (!Character.isDigit(String.valueOf(etImporte.getText()).charAt(0))) {
            etImporte.setError(getResources().getString(R.string.importe_incorrecto));
            return;
        }


        if (gasto == null || gasto.getId() == null) {
            gasto = servicioGastosBusiness.guardarGasto(descripcion, importe, categoria, fecha);
        } else {
            servicioPresupuestoBusiness.descontarTotales(gasto);
            servicioHistorialBusiness.eliminarHistorial(gasto);
            gasto.setDescripcion(descripcion);
            gasto.setFecha(fecha);
            gasto.setImporte(importe);
            gasto.setCategoria(categoria);
            servicioGastosBusiness.actualizarGasto(gasto);
        }
        servicioPresupuestoBusiness.calcularTotales(gasto);
        servicioHistorialBusiness.guardarHistorial(gasto);


        //Mostrar mensaje de agregar gasto
        int gasto_guardado_mensaje = R.string.gasto_guardado_mensaje;
        showMessage(gasto_guardado_mensaje);
        this.finish();

    }

}
