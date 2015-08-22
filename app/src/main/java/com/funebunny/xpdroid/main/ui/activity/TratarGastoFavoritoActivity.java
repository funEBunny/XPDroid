package com.funebunny.xpdroid.main.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.funebunny.xpdroid.R;
import com.funebunny.xpdroid.gastos.business.model.GastoFavorito;
import com.funebunny.xpdroid.gastos.business.service.ServicioGastosBusiness;
import com.funebunny.xpdroid.utilities.AppConstants;

public class TratarGastoFavoritoActivity extends XPDroidActivity {

    private GastoFavorito gastoFavorito;
    private ServicioGastosBusiness servicioGastosBusiness = new ServicioGastosBusiness();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tratar_gasto_favorito);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bgf = getIntent().getExtras();
        gastoFavorito = (GastoFavorito) bgf.getSerializable(AppConstants.GASTO_FAVORITO);

        if (gastoFavorito != null) {

            ((EditText) findViewById(R.id.activity_tratar_gasto_favorito_et_descripcion)).setText(gastoFavorito.getDescripcion());
            ((EditText) findViewById(R.id.activity_tratar_gasto_favorito_et_importe)).setText(gastoFavorito.getImporte());
            Spinner sCategoria = (Spinner) findViewById(R.id.activity_tratar_gasto_favorito_sp_categoria);
            sCategoria.setSelection(((ArrayAdapter) sCategoria.getAdapter()).getPosition(gastoFavorito.getCategoria()));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tratar_gasto_favorito, menu);
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

    public void actualizarGastoFavorito(View v){

        if (gastoFavorito != null) {
            gastoFavorito.setDescripcion(((EditText) findViewById(R.id.activity_tratar_gasto_favorito_et_descripcion)).getText().toString());
            gastoFavorito.setImporte(((EditText) findViewById(R.id.activity_tratar_gasto_favorito_et_importe)).getText().toString());
            gastoFavorito.setCategoria(((Spinner) findViewById(R.id.activity_tratar_gasto_favorito_sp_categoria)).getSelectedItem().toString());
            servicioGastosBusiness.actualizarGastoFavorito(gastoFavorito);
            //Mostrar mensaje de favorito actualizado
            int favorito_actualizado_mensaje = R.string.favorito_actualizado_mensaje;
            showMessage(favorito_actualizado_mensaje);
            this.finish();
        }
    }

    public void eliminarGastoFavorito(View v){
        if (gastoFavorito != null) {
            servicioGastosBusiness.eliminarGastoFavorito(gastoFavorito.getId());
            //Mostrar mensaje de favorito eliminado
            int favorito_eliminado_mensaje = R.string.favorito_eliminado_mensaje;
            showMessage(favorito_eliminado_mensaje);
            this.finish();
        }
    }

}
