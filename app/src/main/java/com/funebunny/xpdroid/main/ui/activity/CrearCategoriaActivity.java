package com.funebunny.xpdroid.main.ui.activity;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.funebunny.xpdroid.R;
import com.funebunny.xpdroid.business.categoria.model.Categoria;
import com.funebunny.xpdroid.business.categoria.service.ServicioCategoriaBusiness;

import java.math.BigDecimal;

public class CrearCategoriaActivity extends XPDroidActivity {

    private ServicioCategoriaBusiness servicioCategorias = new ServicioCategoriaBusiness();
    private Categoria categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_categoria);
        getSupportActionBar().setHomeButtonEnabled(true);   // Botón para volver a actividad anterior

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_solo_logo, menu);
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

    public void guardarCategoria(View view) {

        String descripcion = ((EditText) findViewById(R.id.activity_crear_categoria_et_descripcion)).getText().toString();
        EditText etDescripcion = (EditText) findViewById(R.id.activity_crear_categoria_et_descripcion);

        //Validar importe obligatorio
        if (descripcion.equals("")) {
            etDescripcion.setError(getResources().getString(R.string.campo_obligatorio));
            return;
        }
        servicioCategorias.guardarCategoria(descripcion);

        //Mostrar mensaje de categoria guardada
        int categoria_guardado_mensaje = R.string.categoria_guardado_mensaje;
        showMessage(categoria_guardado_mensaje);
        this.finish();
    }
}
