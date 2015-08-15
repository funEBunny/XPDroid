package com.funebunny.xpdroid.main.ui.activity;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.funebunny.xpdroid.R;
import com.funebunny.xpdroid.gastos.business.service.ServicioGastosBusiness;
import com.funebunny.xpdroid.main.ui.fragment.TimePickerFragment;

public class CrearGastoProgramableActivity extends XPDroidActivity {

    private ServicioGastosBusiness gastosService = new ServicioGastosBusiness();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_gasto_programable);

        getSupportActionBar().setHomeButtonEnabled(true);
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
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void guardarGastoProgramable(View view) {

        String descripcion = ((EditText) findViewById(R.id.activity_crear_gasto_programable_et_descripcion)).getText().toString();
        String repeticion = ((Spinner) findViewById(R.id.activity_crear_gasto_programable_sp_repeticion)).getSelectedItem().toString();
        String horario = ((EditText) findViewById(R.id.activity_crear_gasto_programable_et_horario)).getText().toString();
        String importe = ((EditText) findViewById(R.id.activity_crear_gasto_programable_et_importe)).getText().toString();
        String categoria = ((Spinner) findViewById(R.id.activity_crear_gasto_programable_sp_categoria)).getSelectedItem().toString();
        String diaSemana = ((Spinner) findViewById(R.id.activity_crear_gasto_programable_sp_dias_semana)).getSelectedItem().toString();

        gastosService.guardarGastoProgramable(getApplicationContext(), descripcion, repeticion, horario, importe, categoria, diaSemana);
        //Mostrar mensaje de agregar gasto
        Toast toast = Toast.makeText(this, R.string.gasto_programado_mensaje, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();

        this.finish();
    }

}
