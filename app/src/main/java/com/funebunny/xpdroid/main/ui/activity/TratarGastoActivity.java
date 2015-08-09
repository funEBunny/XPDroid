package com.funebunny.xpdroid.main.ui.activity;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.funebunny.xpdroid.R;
import com.funebunny.xpdroid.main.ui.fragment.DatePickerFragment;

public class TratarGastoActivity extends XPDroidActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tratar_gasto);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle gasto = getIntent().getExtras();

        if (gasto != null) {

            String descripcion = gasto.getString("descripcion");
            String fecha       = gasto.getString("fecha");
            String categoria   = gasto.getString("categoria");
            String importe     = gasto.getString("importe");

            ((EditText) findViewById(R.id.activity_tratar_gasto_et_descripcion)).setText(descripcion);
            ((EditText) findViewById(R.id.activity_tratar_gasto_et_fecha)).setText(fecha);
            ((EditText) findViewById(R.id.activity_tratar_gasto_et_importe)).setText(importe);
            Spinner sCategoria = (Spinner)findViewById(R.id.activity_tratar_gasto_sp_categoria);
            sCategoria.setSelection(((ArrayAdapter) sCategoria.getAdapter()).getPosition(categoria));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_tratar_gasto, menu);
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
    //>Added by PRB
    public void mostrarDatePicker(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }
    //<Added by PRB
}
