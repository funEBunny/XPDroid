package com.funebunny.xpdroid.main.ui.activity;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.funebunny.xpdroid.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by provirabosch on 10/10/2015.
 */
public class CrearPresupuestoActivityTest extends ActivityInstrumentationTestCase2<CrearPresupuestoActivity> {

    private CrearPresupuestoActivity crearPresupuestoActivity;
    private Spinner spPeriodo;

    public CrearPresupuestoActivityTest() {
        super(CrearPresupuestoActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        crearPresupuestoActivity = getActivity();
        spPeriodo = (Spinner) crearPresupuestoActivity.findViewById(R.id.activity_crear_presupuesto_sp_periodo);

    }

    public void testSpPeriodo_entries() {
        final String[] esperado = crearPresupuestoActivity.getResources().getStringArray(R.array.periodo);
        List<String> esperadoList = new ArrayList<String>(Arrays.asList(esperado));
        List<String> realList = new ArrayList<String>();

        ArrayAdapter spPeriodoAdapter = (ArrayAdapter) spPeriodo.getAdapter();
        for (int i = 0; i < spPeriodoAdapter.getCount(); i++) {
            realList.add((String) spPeriodoAdapter.getItem(i));
        }
        assertEquals(esperadoList, realList);
    }

}
