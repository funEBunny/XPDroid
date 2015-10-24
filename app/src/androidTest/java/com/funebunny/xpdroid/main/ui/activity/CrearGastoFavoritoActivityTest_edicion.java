package com.funebunny.xpdroid.main.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.test.ActivityInstrumentationTestCase2;

import com.funebunny.xpdroid.R;
import com.funebunny.xpdroid.business.gasto.model.GastoFavorito;
import com.funebunny.xpdroid.business.gasto.service.ServicioGastosBusiness;
import com.funebunny.xpdroid.utilities.AppConstants;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;

/**
 * Created by provirabosch on 24/10/2015.
 */
public class CrearGastoFavoritoActivityTest_edicion extends ActivityInstrumentationTestCase2<CrearGastoFavoritoActivity> {

    private CrearGastoFavoritoActivity crearGastoFavoritoActivity;
    private ServicioGastosBusiness servicioGastosBusiness;

    public CrearGastoFavoritoActivityTest_edicion() {
        super(CrearGastoFavoritoActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        servicioGastosBusiness = new ServicioGastosBusiness();

        GastoFavorito gastoFavorito = servicioGastosBusiness.guardarGastoFavorito("Favorito", "10", "Varios");

        injectInstrumentation(InstrumentationRegistry.getInstrumentation());

        Bundle bGastoFavorito = new Bundle();
        bGastoFavorito.putSerializable(AppConstants.GASTO_FAVORITO, gastoFavorito);
        Intent intent = new Intent();
        intent.putExtras(bGastoFavorito);

        setActivityIntent(intent);
        crearGastoFavoritoActivity = getActivity();
    }

    public void testEditarGastoFavorito(){
        String nuevoImporte = "20";
        String nuevaCategoria = "Alimentos";
        String nuevaDescripcion = "Favorito Editado";

        onView(withId(R.id.activity_crear_gasto_favorito_et_importe)).perform(clearText()).perform(typeText(nuevoImporte), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.activity_crear_gasto_favorito_sp_categoria)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(nuevaCategoria))).perform(click());
        onView(withId(R.id.activity_crear_gasto_favorito_et_descripcion)).perform(clearText()).perform(typeText(nuevaDescripcion), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.activity_crear_gasto_favorito_bt_guardar)).perform(click());
    }
}

