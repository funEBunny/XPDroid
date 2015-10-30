package com.funebunny.xpdroid.main.ui.activity;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.test.ActivityInstrumentationTestCase2;

import com.funebunny.xpdroid.R;

import java.util.Locale;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

/**
 * Created by provirabosch on 17/10/2015.
 */
public class CrearGastoProgramableActivityTest extends ActivityInstrumentationTestCase2<CrearGastoProgramableActivity> {

    private CrearGastoProgramableActivity crearGastoProgramableActivity;

    public CrearGastoProgramableActivityTest() {
        super(CrearGastoProgramableActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();

        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        crearGastoProgramableActivity = getActivity();
    }

    public void testCrearGastoDiario(){
        String importeEsperado = "100";
        String categoriaEsperada = "Varios";
        String descripcionEsperada = "Test Programable Diario";
        String repeticionEsperada = "Diario";
        onView(withId(R.id.activity_crear_gasto_programable_et_importe)).perform(typeText(importeEsperado), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.activity_crear_gasto_programable_ib_time_picker)).perform(click());
        if(Locale.getDefault().getLanguage().equals("en")) {
            onView(withText("Set")).perform(click());
        } else {
            onView(withText("Listo")).perform(click());
        }
        onView(withId(R.id.activity_crear_gasto_programable_sp_categoria)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(categoriaEsperada))).perform(click());
        onView(withId(R.id.activity_crear_gasto_programable_sp_repeticion)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(repeticionEsperada))).perform(click());
        onView(withId(R.id.activity_crear_gasto_programable_et_descripcion)).perform(typeText(descripcionEsperada), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.activity_crear_gasto_programable_bt_guardar)).perform(click());
    }

    public void testCrearGastoSemanal(){
        String importeEsperado = "100";
        String categoriaEsperada = "Varios";
        String descripcionEsperada = "Test Programable Semanal";
        String repeticionEsperada = "Semanal";
        String diaEsperado = "Jueves";
        onView(withId(R.id.activity_crear_gasto_programable_et_importe)).perform(typeText(importeEsperado), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.activity_crear_gasto_programable_ib_time_picker)).perform(click());
        if(Locale.getDefault().getLanguage().equals("en")) {
            onView(withText("Set")).perform(click());
        } else {
            onView(withText("Listo")).perform(click());
        }
        onView(withId(R.id.activity_crear_gasto_programable_sp_categoria)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(categoriaEsperada))).perform(click());
        onView(withId(R.id.activity_crear_gasto_programable_sp_repeticion)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(repeticionEsperada))).perform(click());
        onView(withId(R.id.activity_crear_gasto_programable_sp_dias_semana)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(diaEsperado))).perform(click());
        onView(withId(R.id.activity_crear_gasto_programable_et_descripcion)).perform(typeText(descripcionEsperada), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.activity_crear_gasto_programable_bt_guardar)).perform(click());
    }

    public void testValidarImporteObligatorio() {
        onView(withId(R.id.activity_crear_gasto_programable_bt_guardar)).perform(click());
        onView(withId(R.id.activity_crear_gasto_programable_et_importe)).check(matches(hasErrorText(crearGastoProgramableActivity.getResources().getString(R.string.campo_obligatorio))));
    }

    public void testValidarPrimerDigitoImporte() {
        onView(withId(R.id.activity_crear_gasto_programable_et_importe)).perform(typeText("."), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.activity_crear_gasto_programable_bt_guardar)).perform(click());
        onView(withId(R.id.activity_crear_gasto_programable_et_importe)).check(matches(hasErrorText(crearGastoProgramableActivity.getResources().getString(R.string.importe_incorrecto))));
    }
}
