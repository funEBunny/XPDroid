package com.funebunny.xpdroid.main.ui.activity;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.NoMatchingViewException;
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
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;

/**
 * Created by provirabosch on 17/10/2015.
 */
public class CrearGastoActivityTest extends ActivityInstrumentationTestCase2<CrearGastoActivity> {

    private CrearGastoActivity crearGastoActivity;

    public CrearGastoActivityTest() {
        super(CrearGastoActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();

        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        crearGastoActivity = getActivity();
    }

    public void testCrearGastoVarios() {
        String importeEsperado = "100";
        String categoriaEsperada = "Varios";
        String descripcionEsperada = "Test Gasto";
        onView(withId(R.id.activity_crear_gasto_et_importe)).perform(typeText(importeEsperado), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.activity_crear_gasto_ib_date_picker)).perform(click());

        switch(Locale.getDefault().getLanguage()){
            case "en":
                onView(withText("Set")).perform(click());
            case "es":
                try {
                    onView(withText("Definir")).perform(click());
                } catch (NoMatchingViewException e) {
                    onView(withText("Listo")).perform(click());
                }
        }

        onView(withId(R.id.activity_crear_gasto_sp_categoria)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(categoriaEsperada))).perform(click());
        onView(withId(R.id.activity_crear_gasto_et_descripcion)).perform(typeText(descripcionEsperada), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.activity_crear_gasto_bt_guardar)).perform(click());

    }

    public void testValidarImporteObligatorio() {
        onView(withId(R.id.activity_crear_gasto_bt_guardar)).perform(click());
        onView(withId(R.id.activity_crear_gasto_et_importe)).check(matches(hasErrorText(crearGastoActivity.getResources().getString(R.string.campo_obligatorio))));
    }

    public void testValidarPrimerDigitoImporte() {
        onView(withId(R.id.activity_crear_gasto_et_importe)).perform(typeText("."), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.activity_crear_gasto_bt_guardar)).perform(click());
        onView(withId(R.id.activity_crear_gasto_et_importe)).check(matches(hasErrorText(crearGastoActivity.getResources().getString(R.string.importe_incorrecto))));
    }
}
