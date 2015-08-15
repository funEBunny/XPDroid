package com.funebunny.xpdroid.main.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.funebunny.xpdroid.R;
import com.funebunny.xpdroid.gastos.business.service.ServicioGastosBusiness;

public class CrearGastoFavoritoActivity extends XPDroidActivity {

    ServicioGastosBusiness servicioGastosBusiness = new ServicioGastosBusiness();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_gasto_favorito);
        // Botón para volver a actividad anterior
        getSupportActionBar().setHomeButtonEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_crear_gasto_favorito, menu);
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

    public void guardarGastoFavorito(View view) {

        String descripcion = ((EditText) findViewById(R.id.activity_crear_gasto_programable_et_descripcion)).getText().toString();
        String importe = ((EditText) findViewById(R.id.activity_crear_gasto_programable_et_importe)).getText().toString();
        String categoria = ((Spinner) findViewById(R.id.activity_crear_gasto_programable_sp_categoria)).getSelectedItem().toString();
        servicioGastosBusiness.guardarGastoFavorito(descripcion, importe, categoria);
        //Mostrar mensaje de favorito guardado
        int gasto_favorito_guardado_mensaje = R.string.gasto_favorito_guardado_mensaje;
        showMessage(gasto_favorito_guardado_mensaje);
        this.finish();
    }
}
