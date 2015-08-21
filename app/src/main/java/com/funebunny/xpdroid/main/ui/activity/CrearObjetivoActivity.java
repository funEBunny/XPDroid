package com.funebunny.xpdroid.main.ui.activity;

import android.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.funebunny.xpdroid.R;
import com.funebunny.xpdroid.gastos.business.service.ServicioGastosBusiness;
import com.funebunny.xpdroid.main.ui.fragment.DatePickerFragment;
import com.funebunny.xpdroid.utilities.AppConstants;

import java.util.Calendar;

public class CrearObjetivoActivity extends XPDroidActivity {

    ServicioGastosBusiness servicioGastosBusiness = new ServicioGastosBusiness();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_objetivo);
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
        servicioGastosBusiness.guardarObjetivo(periodo, importe);
        //Mostrar mensaje de objetivo guardado
        int objetivo_guardado_mensaje = R.string.objetivo_guardado_mensaje;
        showMessage(objetivo_guardado_mensaje);
        this.finish();

    }
}
