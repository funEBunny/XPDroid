package com.funebunny.xpdroid.main.ui.activity;

import android.app.DialogFragment;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.funebunny.xpdroid.R;
import com.funebunny.xpdroid.gastos.backend.IServicioGastos;
import com.funebunny.xpdroid.gastos.backend.ServicioGastos;
import com.funebunny.xpdroid.gastos.model.GastoProgDiario;
import com.funebunny.xpdroid.gastos.model.GastoProgSemanal;
import com.funebunny.xpdroid.gastos.model.GastoProgramable;
import com.funebunny.xpdroid.main.ui.fragment.TimePickerFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class CrearGastoProgramableActivity extends XPDroidActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_gasto_programable);
        //Listener para ocultar teclado cuando se toca fuera de un Edit Text
        setupUI(findViewById(R.id.crear_gasto_programable));

        Spinner repeticion = (Spinner) findViewById(R.id.repeticion);

        repeticion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 if (((Spinner) findViewById(R.id.repeticion)).getSelectedItem().toString() == getResources().getString(R.string.semanal)) {
                     findViewById(R.id.dias_semana).setVisibility(View.VISIBLE);
                 } else {
                     findViewById(R.id.dias_semana).setVisibility(View.INVISIBLE);
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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // > Added by PRB

    public void mostrarTimePicker(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void guardarGastoProgramable(View view) {

        String descripcion = ((EditText) findViewById(R.id.descripcion)).getText().toString();
        String repeticion = ((Spinner) findViewById(R.id.repeticion)).getSelectedItem().toString();
        String horario = ((EditText) findViewById(R.id.horario)).getText().toString();
        String importe = ((EditText) findViewById(R.id.importe)).getText().toString();
        String categoria = ((Spinner) findViewById(R.id.categoria)).getSelectedItem().toString();
        String diaSemana = ((Spinner) findViewById(R.id.dias_semana)).getSelectedItem().toString();

        SimpleDateFormat fromUser = new SimpleDateFormat("HH:mm");
        SimpleDateFormat myFormat = new SimpleDateFormat("HHmm");
        String horaFormateada="0";
        try {
            horaFormateada = myFormat.format(fromUser.parse(horario));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        GastoProgramable gp;

        if (repeticion == getResources().getString(R.string.semanal)){
            gp = new GastoProgSemanal();
            ((GastoProgSemanal)gp).setDiaSemana(getDiaSemana(diaSemana));
        }else{
            gp = new GastoProgDiario();
        }

        gp.setHora(Integer.parseInt(horaFormateada));
        gp.setImporte(importe);
        gp.setDescripcion(descripcion);
        gp.setCategoria(categoria);

        ServicioGastos servicioGastos = new ServicioGastos();
        servicioGastos.guardarGastoProgramable(gp);

    }

    private int getDiaSemana(String diaSemana){
        switch (diaSemana){
            case "lunes":return 2;
            case "martes":return 3;
            case "miercoles":return 4;
            case "jueves":return 5;
            case "viernes":return 6;
            case "sabado":return 7;
            default:return 1;
        }
    }
    // < Added by PRB
}
