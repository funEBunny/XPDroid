package com.funebunny.xpdroid.main.ui.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.funebunny.xpdroid.R;
import com.funebunny.xpdroid.gastos.business.model.Objetivo;
import com.funebunny.xpdroid.gastos.business.service.ServicioObjetivosBusiness;
import com.funebunny.xpdroid.utilities.AppConstants;

public class CrearObjetivoActivity extends XPDroidActivity {

    private ServicioObjetivosBusiness servicioObjetivosBusiness = new ServicioObjetivosBusiness();
    private Objetivo objetivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_objetivo);

        Bundle bObjetivo = getIntent().getExtras();
        if (bObjetivo != null) {
            objetivo = (Objetivo) bObjetivo.getSerializable(AppConstants.OBJETIVO);
            ((EditText) findViewById(R.id.activity_crear_objetivo_et_importe)).setText(objetivo.getImporte());
            Spinner sPeriodo = (Spinner) findViewById(R.id.activity_crear_objetivo_sp_periodo);
            sPeriodo.setSelection(((ArrayAdapter) sPeriodo.getAdapter()).getPosition(objetivo.getPeriodo()));
            sPeriodo.setEnabled(false);
            setTitle(R.string.title_activity_editar_objetivo);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_crear_objetivo, menu);
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

// Custom methods - PRB

    public void guardarObjetivo(View view) {

        String periodo = ((Spinner) findViewById(R.id.activity_crear_objetivo_sp_periodo)).getSelectedItem().toString();
        String importe = ((EditText) findViewById(R.id.activity_crear_objetivo_et_importe)).getText().toString();



        if (objetivo == null){
            if (servicioObjetivosBusiness.tipoObjetivoExiste(periodo)){
                Resources res = getResources();
                String mensaje = String.format(res.getString(R.string.objetivo_existente), periodo);
                showMessage(mensaje);
                return;
            }
            servicioObjetivosBusiness.guardarObjetivo(periodo, importe);
        }else{
            objetivo.setImporte(importe);
            objetivo.setPeriodo(periodo);
            servicioObjetivosBusiness.actualizarObjetivo(objetivo);
        }

        //Mostrar mensaje de objetivo guardado
        int objetivo_guardado_mensaje = R.string.objetivo_guardado_mensaje;
        showMessage(objetivo_guardado_mensaje);
        this.finish();

    }
}
