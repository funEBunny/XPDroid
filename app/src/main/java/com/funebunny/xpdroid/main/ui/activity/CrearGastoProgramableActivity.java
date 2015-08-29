package com.funebunny.xpdroid.main.ui.activity;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.funebunny.xpdroid.R;
import com.funebunny.xpdroid.business.gasto.model.GastoProgDiario;
import com.funebunny.xpdroid.business.gasto.model.GastoProgSemanal;
import com.funebunny.xpdroid.business.gasto.model.GastoProgramable;
import com.funebunny.xpdroid.business.gasto.service.ServicioGastosBusiness;
import com.funebunny.xpdroid.main.ui.fragment.TimePickerFragment;
import com.funebunny.xpdroid.utilities.AppConstants;

public class CrearGastoProgramableActivity extends XPDroidActivity {

    private ServicioGastosBusiness servicioGastosBusiness = new ServicioGastosBusiness();
    private GastoProgramable gastoProgramable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_gasto_programable);
        getSupportActionBar().setHomeButtonEnabled(true);

        Bundle bGastoProgramable = getIntent().getExtras();
        if (bGastoProgramable != null) {
            gastoProgramable = (GastoProgramable) bGastoProgramable.getSerializable(AppConstants.GASTO_PROGRAMABLE);
            ((EditText) findViewById(R.id.activity_crear_gasto_programable_et_descripcion)).setText(gastoProgramable.getDescripcion());
            ((EditText) findViewById(R.id.activity_crear_gasto_programable_et_importe)).setText(gastoProgramable.getImporte());
            ((EditText) findViewById(R.id.activity_crear_gasto_programable_et_horario)).setText(String.valueOf(gastoProgramable.getHora()));

            Spinner sCategoria = (Spinner) findViewById(R.id.activity_crear_gasto_programable_sp_categoria);
            sCategoria.setSelection(((ArrayAdapter) sCategoria.getAdapter()).getPosition(gastoProgramable.getCategoria()));

            Spinner sRepeticion = (Spinner) findViewById(R.id.activity_crear_gasto_programable_sp_repeticion);
            if (gastoProgramable instanceof GastoProgSemanal) {
                sRepeticion.setSelection(((ArrayAdapter) sRepeticion.getAdapter()).getPosition(GastoProgSemanal.SEMANAL));

                findViewById(R.id.activity_crear_gasto_programable_sp_dias_semana).setVisibility(View.VISIBLE);
                Spinner sDiaSemana = (Spinner) findViewById(R.id.activity_crear_gasto_programable_sp_dias_semana);
                String diaSemana = servicioGastosBusiness.getDiaSemana(((GastoProgSemanal) gastoProgramable).getDiaSemana());
                sDiaSemana.setSelection(((ArrayAdapter) sDiaSemana.getAdapter()).getPosition(diaSemana));
            } else {
                sRepeticion.setSelection(((ArrayAdapter) sRepeticion.getAdapter()).getPosition(GastoProgDiario.DIARIO));
                findViewById(R.id.activity_crear_gasto_programable_sp_dias_semana).setVisibility(View.INVISIBLE);
            }

            setTitle(R.string.title_activity_editar_gasto_programable);

        } else { //MEJORAR ESTOO!!!!!

            // Lógica para ocultar/mostrar el spinner de días de la semana, según selección del spinner repetición
            Spinner repeticion = (Spinner) findViewById(R.id.activity_crear_gasto_programable_sp_repeticion);

            repeticion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                     @Override
                                                     public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                         if (((Spinner) findViewById(R.id.activity_crear_gasto_programable_sp_repeticion)).getSelectedItem().toString() == getResources().getString(R.string.semanal)) {
                                                             findViewById(R.id.activity_crear_gasto_programable_sp_dias_semana).setVisibility(View.VISIBLE);
                                                         } else {
                                                             findViewById(R.id.activity_crear_gasto_programable_sp_dias_semana).setVisibility(View.INVISIBLE);
                                                         }
                                                     }

                                                     @Override
                                                     public void onNothingSelected(AdapterView<?> parent) {

                                                     }
                                                 }
            );
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_crear_gasto_programable, menu);
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

    //Métodos custom XPDroid
    public void mostrarTimePicker(View v) {
        EditText viewById = (EditText) findViewById(R.id.activity_crear_gasto_programable_et_horario);
        DialogFragment newFragment = TimePickerFragment.newInstance(viewById);
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void guardarGastoProgramable(View view) {

        String descripcion = ((EditText) findViewById(R.id.activity_crear_gasto_programable_et_descripcion)).getText().toString();
        String repeticion = ((Spinner) findViewById(R.id.activity_crear_gasto_programable_sp_repeticion)).getSelectedItem().toString();
        String horario = ((EditText) findViewById(R.id.activity_crear_gasto_programable_et_horario)).getText().toString();
        String importe = ((EditText) findViewById(R.id.activity_crear_gasto_programable_et_importe)).getText().toString();
        String categoria = ((Spinner) findViewById(R.id.activity_crear_gasto_programable_sp_categoria)).getSelectedItem().toString();
        String diaSemana = ((Spinner) findViewById(R.id.activity_crear_gasto_programable_sp_dias_semana)).getSelectedItem().toString();

        if (gastoProgramable == null) {
            servicioGastosBusiness.guardarGastoProgramable(getApplicationContext(), descripcion, repeticion, horario, importe, categoria, diaSemana);
        } else {

            gastoProgramable.setDescripcion(descripcion);
            gastoProgramable.setCategoria(categoria);
            gastoProgramable.setImporte(importe);
            gastoProgramable.setHora(horario);
            servicioGastosBusiness.actualizarGastoProgramable(getApplicationContext(), gastoProgramable);
        }

        //Mostrar mensaje de agregar gasto
        int gasto_guardado_mensaje = R.string.gasto_programado_mensaje;
        showMessage(gasto_guardado_mensaje);
        this.finish();
    }

}
