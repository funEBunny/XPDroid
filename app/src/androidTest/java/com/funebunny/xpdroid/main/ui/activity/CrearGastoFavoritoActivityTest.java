package com.funebunny.xpdroid.main.ui.activity;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.test.ActivityInstrumentationTestCase2;

import com.funebunny.xpdroid.R;

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
 * Created by provirabosch on 17/10/2015.
 */
public class CrearGastoFavoritoActivityTest extends ActivityInstrumentationTestCase2<CrearGastoFavoritoActivity> {

    private CrearGastoFavoritoActivity crearGastoFavoritoActivity;

    public CrearGastoFavoritoActivityTest() {
        super(CrearGastoFavoritoActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();

        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        crearGastoFavoritoActivity = getActivity();
    }

    public void testCrearGastoFavoritoVarios() {
        String importeEsperado = "10";
        String categoriaEsperada = "Varios";
        String descripcionEsperada = "Favorito";
        onView(withId(R.id.activity_crear_gasto_favorito_et_importe)).perform(typeText(importeEsperado), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.activity_crear_gasto_favorito_sp_categoria)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(categoriaEsperada))).perform(click());
        onView(withId(R.id.activity_crear_gasto_favorito_et_descripcion)).perform(typeText(descripcionEsperada), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.activity_crear_gasto_favorito_bt_guardar)).perform(click());

    }

    public void testValidarImporteObligatorio() {
        onView(withId(R.id.activity_crear_gasto_favorito_bt_guardar)).perform(click());
        onView(withId(R.id.activity_crear_gasto_favorito_et_importe)).check(matches(hasErrorText(crearGastoFavoritoActivity.getResources().getString(R.string.campo_obligatorio))));
    }

    public void testValidarPrimerDigitoImporte() {
        onView(withId(R.id.activity_crear_gasto_favorito_et_importe)).perform(typeText("."), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.activity_crear_gasto_favorito_bt_guardar)).perform(click());
        onView(withId(R.id.activity_crear_gasto_favorito_et_importe)).check(matches(hasErrorText(crearGastoFavoritoActivity.getResources().getString(R.string.importe_incorrecto))));
    }
}
