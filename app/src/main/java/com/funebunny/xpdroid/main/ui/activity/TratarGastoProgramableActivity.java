package com.funebunny.xpdroid.main.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.funebunny.xpdroid.R;
import com.funebunny.xpdroid.gastos.business.model.GastoProgDiario;
import com.funebunny.xpdroid.gastos.business.model.GastoProgSemanal;
import com.funebunny.xpdroid.gastos.business.model.GastoProgramable;
import com.funebunny.xpdroid.gastos.business.service.ServicioGastosBusiness;
import com.funebunny.xpdroid.main.ui.fragment.TimePickerFragment;
import com.funebunny.xpdroid.utilities.AppConstants;

public class TratarGastoProgramableActivity extends XPDroidActivity {

    private GastoProgramable gastoProgramable;
    private ServicioGastosBusiness servicioGastosBusiness = new ServicioGastosBusiness();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tratar_gasto_programable);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bgf = getIntent().getExtras();
        gastoProgramable = (GastoProgramable)bgf.getSerializable(AppConstants.GASTO_PROGRAMABLE);
        if (gastoProgramable!=null){

            ((EditText) findViewById(R.id.activity_tratar_gasto_programable_et_descripcion)).setText(gastoProgramable.getDescripcion());
            ((EditText) findViewById(R.id.activity_tratar_gasto_programable_et_importe)).setText(gastoProgramable.getImporte());
            ((EditText) findViewById(R.id.activity_tratar_gasto_programable_et_horario)).setText(String.valueOf(gastoProgramable.getHora()));

            Spinner sCategoria = (Spinner) findViewById(R.id.activity_tratar_gasto_programable_sp_categoria);
            sCategoria.setSelection(((ArrayAdapter) sCategoria.getAdapter()).getPosition(gastoProgramable.getCategoria()));

            Spinner sRepeticion = (Spinner) findViewById(R.id.activity_tratar_gasto_programable_sp_repeticion);


            if (gastoProgramable instanceof GastoProgSemanal){
                sRepeticion.setSelection(((ArrayAdapter) sRepeticion.getAdapter()).getPosition(GastoProgSemanal.SEMANAL));

                findViewById(R.id.activity_tratar_gasto_programable_sp_dias_semana).setVisibility(View.VISIBLE);
                Spinner sDiaSemana = (Spinner) findViewById(R.id.activity_tratar_gasto_programable_sp_dias_semana);
                String diaSemana = servicioGastosBusiness.getDiaSemana(((GastoProgSemanal) gastoProgramable).getDiaSemana());
                sDiaSemana.setSelection(((ArrayAdapter) sDiaSemana.getAdapter()).getPosition(diaSemana));
            }else{
                sRepeticion.setSelection(((ArrayAdapter) sRepeticion.getAdapter()).getPosition(GastoProgDiario.DIARIO));
                findViewById(R.id.activity_tratar_gasto_programable_sp_dias_semana).setVisibility(View.INVISIBLE);
            }
        }

    }

    //Métodos custom XPDroid
    public void mostrarTimePicker(View v) {
        EditText time = (EditText) findViewById(R.id.activity_tratar_gasto_programable_et_horario);
        TimePickerFragment newFragment = TimePickerFragment.newInstance(time);
        newFragment.show(getFragmentManager(), "timePicker");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tratar_gasto_programable, menu);
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

    public void actualizarGastoProgramable(View v){

        if (gastoProgramable != null) {
            gastoProgramable.setDescripcion(((EditText) findViewById(R.id.activity_tratar_gasto_programable_et_descripcion)).getText().toString());
            gastoProgramable.setImporte(((EditText) findViewById(R.id.activity_tratar_gasto_programable_et_importe)).getText().toString());
            gastoProgramable.setHora(((EditText) findViewById(R.id.activity_tratar_gasto_programable_et_horario)).getText().toString());
            gastoProgramable.setCategoria(((Spinner) findViewById(R.id.activity_tratar_gasto_programable_sp_categoria)).getSelectedItem().toString());
            servicioGastosBusiness.actualizarGastoProgramable(this.getApplicationContext(),gastoProgramable);
            //Mostrar mensaje de programado actualizado
            int programado_actualizado_mensaje = R.string.programado_actualizado_mensaje;
            showMessage(programado_actualizado_mensaje);
            this.finish();
        }
    }

    public void eliminarGastoProgramable(View v){
        if (gastoProgramable != null) {
            servicioGastosBusiness.eliminarGastoProgramable(this.getApplicationContext(), gastoProgramable.getId());
            //Mostrar mensaje de favorito eliminado
            int programado_eliminado_mensaje = R.string.programado_eliminado_mensaje;
            showMessage(programado_eliminado_mensaje);
            this.finish();
        }
    }
}
