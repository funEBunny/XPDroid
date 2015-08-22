package com.funebunny.xpdroid.main.ui.activity;


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

public class TratarObjetivoActivity extends XPDroidActivity {

    private Objetivo objetivo;
    private ServicioObjetivosBusiness servicioObjetivosBusiness = new ServicioObjetivosBusiness();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tratar_objetivo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bobj = getIntent().getExtras();
        objetivo = (Objetivo) bobj.getSerializable(AppConstants.OBJETIVO);

        if (objetivo != null) {

            ((EditText) findViewById(R.id.activity_tratar_objetivo_et_importe)).setText(objetivo.getImporte());
            Spinner sPeriodo = (Spinner) findViewById(R.id.activity_tratar_objetivo_sp_periodo);
            sPeriodo.setSelection(((ArrayAdapter) sPeriodo.getAdapter()).getPosition(objetivo.getPeriodo()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tratar_objetivo, menu);
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

    public void actualizarObjetivo(View v){

        if (objetivo != null){
            objetivo.setImporte(((EditText) findViewById(R.id.activity_tratar_objetivo_et_importe)).getText().toString());
            objetivo.setPeriodo(((Spinner) findViewById(R.id.activity_tratar_objetivo_sp_periodo)).getSelectedItem().toString());
            servicioObjetivosBusiness.actualizarObjetivo(objetivo);
            //Mostrar mensaje de objetivo actualizado
            int objetivo_actualizado_mensaje = R.string.objetivo_actualizado_mensaje;
            showMessage(objetivo_actualizado_mensaje);
            this.finish();
        }

    }

    public void eliminarObjetivo(View v){

        if (objetivo != null) {
            servicioObjetivosBusiness.eliminarObjetivo(objetivo.getId());
            //Mostrar mensaje de favorito eliminado
            int objetivo_eliminado_mensaje = R.string.objetivo_eliminado_mensaje;
            showMessage(objetivo_eliminado_mensaje);
            this.finish();
        }
    }
}
