package com.funebunny.xpdroid.main.ui.activity;

import android.app.DialogFragment;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.funebunny.xpdroid.R;
import com.funebunny.xpdroid.business.gasto.model.GastoProgDiario;
import com.funebunny.xpdroid.business.gasto.model.GastoProgSemanal;
import com.funebunny.xpdroid.business.gasto.model.GastoProgramable;
import com.funebunny.xpdroid.business.gasto.service.ServicioGastosBusiness;
import com.funebunny.xpdroid.main.ui.fragment.TimePickerFragment;
import com.funebunny.xpdroid.utilities.AppConstants;

import java.math.BigDecimal;

public class CrearGastoProgramableActivity extends XPDroidActivity {

    private ServicioGastosBusiness servicioGastosBusiness = new ServicioGastosBusiness();
    private GastoProgramable gastoProgramable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_gasto_programable);
        getSupportActionBar().setHomeButtonEnabled(true);

        EditText etImporte = (EditText) findViewById(R.id.activity_crear_gasto_programable_et_importe);
        Bundle bGastoProgramable = getIntent().getExtras();
        if (bGastoProgramable != null) {

            gastoProgramable = (GastoProgramable) bGastoProgramable.getSerializable(AppConstants.GASTO_PROGRAMABLE);
            etImporte.setText(gastoProgramable.getImporte());
            ((EditText) findViewById(R.id.activity_crear_gasto_programable_et_descripcion)).setText(gastoProgramable.getDescripcion());
            ((EditText) findViewById(R.id.activity_crear_gasto_programable_et_horario)).setText(String.valueOf(gastoProgramable.getHora()));
            Spinner sCategoria = (Spinner) findViewById(R.id.activity_crear_gasto_programable_sp_categoria);
            sCategoria.setSelection(((ArrayAdapter) sCategoria.getAdapter()).getPosition(gastoProgramable.getCategoria()));

            Spinner sRepeticion = (Spinner) findViewById(R.id.activity_crear_gasto_programable_sp_repeticion);
            sRepeticion.setEnabled(false);

            if (gastoProgramable instanceof GastoProgSemanal) {

                sRepeticion.setSelection(((ArrayAdapter) sRepeticion.getAdapter()).getPosition(GastoProgSemanal.SEMANAL));
                Spinner sDiasSemana = (Spinner) findViewById(R.id.activity_crear_gasto_programable_sp_dias_semana);
                String diaSemana = servicioGastosBusiness.getDiaSemana(((GastoProgSemanal) gastoProgramable).getDiaSemana());
                sDiasSemana.setSelection(((ArrayAdapter) sDiasSemana.getAdapter()).getPosition(diaSemana));
                sDiasSemana.setEnabled(true);
            } else {
                sRepeticion.setSelection(((ArrayAdapter) sRepeticion.getAdapter()).getPosition(GastoProgDiario.DIARIO));
            }

            setTitle(R.string.title_activity_editar_gasto_programable);

        } else {

            ((EditText) findViewById(R.id.activity_crear_gasto_programable_et_horario)).setText(getHoraActual());

        }
        //Limitador de dígitos enteros y decimales para campo Importe
        etImporte.setFilters(new InputFilter[]{new DigitosImporteKeyListener(AppConstants.CANTIDAD_ENTEROS, AppConstants.CANTIDAD_DECIMALES, etImporte.getText())});
        verificarSpinnerRepeticion();
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
        String categoria = ((Spinner) findViewById(R.id.activity_crear_gasto_programable_sp_categoria)).getSelectedItem().toString();
        String diaSemana = ((Spinner) findViewById(R.id.activity_crear_gasto_programable_sp_dias_semana)).getSelectedItem().toString();
        EditText etImporte = (EditText) findViewById(R.id.activity_crear_gasto_programable_et_importe);
        String importe = String.valueOf(etImporte.getText());

        //Validar importe obligatorio
        if (importe.equals("")) {
            etImporte.setError(getResources().getString(R.string.campo_obligatorio));
            return;
        }
        //Validar que el importe no sea CERO
        if (new BigDecimal(importe).compareTo(BigDecimal.ZERO) == 0) {
            etImporte.setError(getResources().getString(R.string.importe_incorrecto));
            return;
        }

        if (gastoProgramable == null) {
            servicioGastosBusiness.guardarGastoProgramable(getApplicationContext(), descripcion, repeticion, horario, importe, categoria, diaSemana);
        } else {

            gastoProgramable.setDescripcion(descripcion);
            gastoProgramable.setCategoria(categoria);
            gastoProgramable.setImporte(importe);
            gastoProgramable.setHora(horario);
            if (gastoProgramable instanceof GastoProgSemanal)
                ((GastoProgSemanal) gastoProgramable).setDiaSemana(servicioGastosBusiness.getDiaSemana(diaSemana));
            servicioGastosBusiness.actualizarGastoProgramable(getApplicationContext(), gastoProgramable);
        }

        //Mostrar mensaje de agregar gasto
        int gasto_guardado_mensaje = R.string.gasto_programado_mensaje;
        showMessage(gasto_guardado_mensaje);
        this.finish();
    }

    private void verificarSpinnerRepeticion() {
        // Lógica para ocultar/mostrar el spinner de días de la semana, según selección del spinner repetición
        Spinner repeticion = (Spinner) findViewById(R.id.activity_crear_gasto_programable_sp_repeticion);

        repeticion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                 @Override
                                                 public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                     if (((Spinner) findViewById(R.id.activity_crear_gasto_programable_sp_repeticion)).getSelectedItem().toString() == getResources().getString(R.string.semanal)) {
                                                         Spinner spDiasSemana = (Spinner) findViewById(R.id.activity_crear_gasto_programable_sp_dias_semana);
                                                         spDiasSemana.setVisibility(View.VISIBLE);
                                                         LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
                                                         layoutParams.gravity = Gravity.CENTER_VERTICAL;
                                                         spDiasSemana.setLayoutParams(layoutParams);

                                                     } else {
                                                         findViewById(R.id.activity_crear_gasto_programable_sp_dias_semana).setVisibility(View.GONE);

                                                     }
                                                 }

                                                 @Override
                                                 public void onNothingSelected(AdapterView<?> parent) {

                                                 }
                                             }
        );
    }

}
