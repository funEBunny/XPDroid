package com.funebunny.xpdroid.main.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.test.ActivityInstrumentationTestCase2;

import com.funebunny.xpdroid.R;
import com.funebunny.xpdroid.business.presupuesto.model.Presupuesto;
import com.funebunny.xpdroid.business.presupuesto.service.ServicioPresupuestoBusiness;
import com.funebunny.xpdroid.utilities.AppConstants;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by provirabosch on 21/10/2015.
 */
public class CrearPresupuestoActivityTest_edicion extends ActivityInstrumentationTestCase2<CrearPresupuestoActivity> {

    private CrearPresupuestoActivity crearPresupuestoActivity;
    private ServicioPresupuestoBusiness servicioPresupuestoBusiness;

    public CrearPresupuestoActivityTest_edicion() {
        super(CrearPresupuestoActivity.class);

        servicioPresupuestoBusiness = new ServicioPresupuestoBusiness();
        servicioPresupuestoBusiness.guardarPresupuesto(AppConstants.PERIODO_DIARIO, "100");
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

        injectInstrumentation(InstrumentationRegistry.getInstrumentation());

        Bundle bPresupuesto = new Bundle();
        bPresupuesto.putSerializable(AppConstants.PRESUPUESTO, buscarPresupuesto(AppConstants.PERIODO_DIARIO));
        Intent intent = new Intent();
        intent.putExtras(bPresupuesto);

        setActivityIntent(intent);
        crearPresupuestoActivity = getActivity();
    }

    public void testEditarPresupuestoDiario() {
        String esperado = "200";
        onView(withId(R.id.activity_crear_presupuesto_et_importe)).perform(clearText()).perform(typeText(esperado), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.activity_crear_presupuesto_bt_guardar)).perform(click());
        assertEquals(esperado, buscarPresupuesto(AppConstants.PERIODO_DIARIO).getImporte());
    }

    private Presupuesto buscarPresupuesto(String periodo) {
        Presupuesto presupuestoBuscado = null;
        List<Presupuesto> presupuestoList = servicioPresupuestoBusiness.obtenerPresupuesto();

        for (Presupuesto presupuesto : presupuestoList) {
            String periodoPresupuesto = presupuesto.getPeriodo();
            if (periodo.equals(periodoPresupuesto)) {
                presupuestoBuscado = presupuesto;
            }
        }
        return presupuestoBuscado;
    }
}
