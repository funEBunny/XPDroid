package com.funebunny.xpdroid.main.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.test.ActivityInstrumentationTestCase2;

import com.funebunny.xpdroid.R;
import com.funebunny.xpdroid.business.gasto.model.GastoProgramable;
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
 * Created by provirabosch on 30/10/2015.
 */
public class CrearGastoProgramableActivityTest_edicion extends ActivityInstrumentationTestCase2<CrearGastoProgramableActivity> {

    private CrearGastoProgramableActivity crearGastoProgramableActivity;
    private ServicioGastosBusiness servicioGastosBusiness;

    public CrearGastoProgramableActivityTest_edicion() {
        super(CrearGastoProgramableActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());

        servicioGastosBusiness = new ServicioGastosBusiness();
        GastoProgramable gastoProgramable = servicioGastosBusiness.guardarGastoProgramable(InstrumentationRegistry.getTargetContext(), "Programable Test", "Diario", "23:59", "100", "Varios", "");

        Bundle bGastoProgramable = new Bundle();
        bGastoProgramable.putSerializable(AppConstants.GASTO_PROGRAMABLE, gastoProgramable);
        Intent intent = new Intent();
        intent.putExtras(bGastoProgramable);

        setActivityIntent(intent);
        crearGastoProgramableActivity = getActivity();
    }

    public void testEditarGastoProgramable() {
        String nuevoImporte = "20";
        String nuevaCategoria = "Alimentos";
        String nuevaDescripcion = "Programable Editado";

        onView(withId(R.id.activity_crear_gasto_programable_et_importe)).perform(clearText()).perform(typeText(nuevoImporte), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.activity_crear_gasto_programable_sp_categoria)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(nuevaCategoria))).perform(click());
        onView(withId(R.id.activity_crear_gasto_programable_et_descripcion)).perform(clearText()).perform(typeText(nuevaDescripcion), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.activity_crear_gasto_programable_bt_guardar)).perform(click());
    }
}
