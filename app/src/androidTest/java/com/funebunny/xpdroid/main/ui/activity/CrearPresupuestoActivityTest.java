package com.funebunny.xpdroid.main.ui.activity;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.test.ActivityInstrumentationTestCase2;

import com.funebunny.xpdroid.R;
import com.funebunny.xpdroid.business.presupuesto.model.Presupuesto;
import com.funebunny.xpdroid.business.presupuesto.service.ServicioPresupuestoBusiness;
import com.funebunny.xpdroid.utilities.AppConstants;

import java.util.List;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;


/**
 * Created by provirabosch on 10/10/2015.
 */
public class CrearPresupuestoActivityTest extends ActivityInstrumentationTestCase2<CrearPresupuestoActivity> {

    private CrearPresupuestoActivity crearPresupuestoActivity;
    private ServicioPresupuestoBusiness servicioPresupuestoBusiness;

    public CrearPresupuestoActivityTest() {
        super(CrearPresupuestoActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        crearPresupuestoActivity = getActivity();
        servicioPresupuestoBusiness = new ServicioPresupuestoBusiness();
    }

    public void testCrearPresupuestoSemanal() {
        String periodoSemanal = AppConstants.PERIODO_SEMANAL;
        String esperado = "1000";
        onView(withId(R.id.activity_crear_presupuesto_et_importe)).perform(typeText(esperado), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.activity_crear_presupuesto_sp_periodo)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(periodoSemanal))).perform(click());
        onView(withId(R.id.activity_crear_presupuesto_bt_guardar)).perform(click());
        assertEquals(esperado, buscarPresupuesto(periodoSemanal).getImporte());
    }

    public void testValidarImporteObligatorio() {
        onView(withId(R.id.activity_crear_presupuesto_bt_guardar)).perform(click());
        onView(withId(R.id.activity_crear_presupuesto_et_importe)).check(matches(hasErrorText(crearPresupuestoActivity.getResources().getString(R.string.campo_obligatorio))));
    }

    public void testValidarPrimerDigitoImporte() {
        onView(withId(R.id.activity_crear_presupuesto_et_importe)).perform(typeText("."), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.activity_crear_presupuesto_bt_guardar)).perform(click());
        onView(withId(R.id.activity_crear_presupuesto_et_importe)).check(matches(hasErrorText(crearPresupuestoActivity.getResources().getString(R.string.importe_incorrecto))));
    }

    public void testPresupuestoExistente() {
        String periodoAnual = AppConstants.PERIODO_ANUAL;
        String importe = "100000";
        servicioPresupuestoBusiness.guardarPresupuesto(periodoAnual, importe);

        onView(withId(R.id.activity_crear_presupuesto_et_importe)).perform(typeText(importe), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.activity_crear_presupuesto_sp_periodo)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(periodoAnual))).perform(click());
        onView(withId(R.id.activity_crear_presupuesto_bt_guardar)).perform(click());
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
