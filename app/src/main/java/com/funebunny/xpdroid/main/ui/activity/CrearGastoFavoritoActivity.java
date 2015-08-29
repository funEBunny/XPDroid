package com.funebunny.xpdroid.main.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.funebunny.xpdroid.R;
import com.funebunny.xpdroid.business.gasto.model.GastoFavorito;
import com.funebunny.xpdroid.business.gasto.service.ServicioGastosBusiness;
import com.funebunny.xpdroid.utilities.AppConstants;

public class CrearGastoFavoritoActivity extends XPDroidActivity {

    private ServicioGastosBusiness servicioGastosBusiness = new ServicioGastosBusiness();
    private GastoFavorito gastoFavorito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_gasto_favorito);
        getSupportActionBar().setHomeButtonEnabled(true);   // Botón para volver a actividad anterior

        Bundle bGastoFavorito = getIntent().getExtras();
        if (bGastoFavorito != null) {
            gastoFavorito = (GastoFavorito) bGastoFavorito.getSerializable(AppConstants.GASTO_FAVORITO);
            ((EditText) findViewById(R.id.activity_crear_gasto_favorito_et_descripcion)).setText(gastoFavorito.getDescripcion());
            ((EditText) findViewById(R.id.activity_crear_gasto_favorito_et_importe)).setText(gastoFavorito.getImporte());
            Spinner sCategoria = (Spinner) findViewById(R.id.activity_crear_gasto_favorito_sp_categoria);
            sCategoria.setSelection(((ArrayAdapter) sCategoria.getAdapter()).getPosition(gastoFavorito.getCategoria()));

            setTitle(R.string.title_activity_editar_gasto_favorito);
        }
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

        String descripcion = ((EditText) findViewById(R.id.activity_crear_gasto_favorito_et_descripcion)).getText().toString();
        String importe = ((EditText) findViewById(R.id.activity_crear_gasto_favorito_et_importe)).getText().toString();
        String categoria = ((Spinner) findViewById(R.id.activity_crear_gasto_favorito_sp_categoria)).getSelectedItem().toString();

        if (gastoFavorito == null) {
            servicioGastosBusiness.guardarGastoFavorito(descripcion, importe, categoria);
        } else {
            gastoFavorito.setDescripcion(descripcion);
            gastoFavorito.setImporte(importe);
            gastoFavorito.setCategoria(categoria);
            servicioGastosBusiness.actualizarGastoFavorito(gastoFavorito);
        }
        //Mostrar mensaje de favorito guardado
        int gasto_favorito_guardado_mensaje = R.string.gasto_favorito_guardado_mensaje;
        showMessage(gasto_favorito_guardado_mensaje);
        this.finish();
    }
}
