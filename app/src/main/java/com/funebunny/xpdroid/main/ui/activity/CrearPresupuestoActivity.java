package com.funebunny.xpdroid.main.ui.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.funebunny.xpdroid.R;
import com.funebunny.xpdroid.business.presupuesto.model.Presupuesto;
import com.funebunny.xpdroid.business.presupuesto.service.ServicioPresupuestoBusiness;
import com.funebunny.xpdroid.utilities.AppConstants;

public class CrearPresupuestoActivity extends XPDroidActivity {

    private ServicioPresupuestoBusiness servicioPresupuestoBusiness = new ServicioPresupuestoBusiness();
    private Presupuesto presupuesto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_presupuesto);

        Bundle bPresupuesto = getIntent().getExtras();
        if (bPresupuesto != null) {
            presupuesto = (Presupuesto) bPresupuesto.getSerializable(AppConstants.PRESUPUESTO);
            ((EditText) findViewById(R.id.activity_crear_presupuesto_et_importe)).setText(presupuesto.getImporte());
            Spinner sPeriodo = (Spinner) findViewById(R.id.activity_crear_presupuesto_sp_periodo);
            sPeriodo.setSelection(((ArrayAdapter) sPeriodo.getAdapter()).getPosition(presupuesto.getPeriodo()));
            sPeriodo.setEnabled(false);
            setTitle(R.string.title_activity_editar_presupuesto);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_crear_presupuesto, menu);
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


    public void guardarPresupuesto(View view) {

        String periodo = ((Spinner) findViewById(R.id.activity_crear_presupuesto_sp_periodo)).getSelectedItem().toString();
        EditText etImporte = (EditText) findViewById(R.id.activity_crear_presupuesto_et_importe);
        String importe = String.valueOf(etImporte.getText());

        //Validar importe obligatorio
        if (importe.equals("")) {
            etImporte.setError(getResources().getString(R.string.campo_obligatorio));
            return;
        }
        //Validar primer dígito del Importe
        if (!Character.isDigit(String.valueOf(etImporte.getText()).charAt(0))){
            etImporte.setError(getResources().getString(R.string.importe_incorrecto));
            return;
        }

        if (presupuesto == null){
            if (servicioPresupuestoBusiness.tipoPresupuestoExiste(periodo)){
                Resources res = getResources();
                String mensaje = String.format(res.getString(R.string.presupuesto_existente), periodo);
                showMessage(mensaje);
                return;
            }
            servicioPresupuestoBusiness.guardarPresupuesto(periodo, importe);
        }else{
            presupuesto.setImporte(importe);
            presupuesto.setPeriodo(periodo);
            servicioPresupuestoBusiness.actualizarPresupuesto(presupuesto);
        }

        //Mostrar mensaje de presupuesto guardado
        int presupuesto_guardado_mensaje = R.string.presupuesto_guardado_mensaje;
        showMessage(presupuesto_guardado_mensaje);
        this.finish();

    }
}
