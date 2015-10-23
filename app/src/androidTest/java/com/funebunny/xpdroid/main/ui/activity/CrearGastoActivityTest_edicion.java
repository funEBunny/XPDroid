package com.funebunny.xpdroid.main.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.test.ActivityInstrumentationTestCase2;

import com.funebunny.xpdroid.R;
import com.funebunny.xpdroid.business.gasto.model.Gasto;
import com.funebunny.xpdroid.business.gasto.service.ServicioGastosBusiness;
import com.funebunny.xpdroid.business.historial.service.ServicioHistorialBusiness;
import com.funebunny.xpdroid.business.presupuesto.service.ServicioPresupuestoBusiness;
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
 * Created by provirabosch on 23/10/2015.
 */
public class CrearGastoActivityTest_edicion extends ActivityInstrumentationTestCase2<CrearGastoActivity> {

    private CrearGastoActivity crearGastoActivity;
    private ServicioGastosBusiness servicioGastosBusiness;
    private ServicioHistorialBusiness servicioHistorialBusiness;
    private ServicioPresupuestoBusiness servicioPresupuestoBusiness;

    public CrearGastoActivityTest_edicion() {
        super(CrearGastoActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        servicioGastosBusiness = new ServicioGastosBusiness();
        servicioHistorialBusiness = new ServicioHistorialBusiness();
        servicioPresupuestoBusiness = new ServicioPresupuestoBusiness();

        Gasto gasto = servicioGastosBusiness.guardarGasto("Test Gasto", "100", "Varios", "20/10/2015");
        servicioHistorialBusiness.guardarHistorial(gasto);
        servicioPresupuestoBusiness.calcularTotales(gasto);

        injectInstrumentation(InstrumentationRegistry.getInstrumentation());

        Bundle bGasto = new Bundle();
        bGasto.putSerializable(AppConstants.GASTO, gasto);
        Intent intent = new Intent();
        intent.putExtras(bGasto);

        setActivityIntent(intent);
        crearGastoActivity = getActivity();
    }

    public void testEditarGasto(){
        String nuevoImporte = "200";
        String nuevaCategoria = "Alimentos";
        String nuevaDescripcion = "Gasto Editado";

        onView(withId(R.id.activity_crear_gasto_et_importe)).perform(clearText()).perform(typeText(nuevoImporte), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.activity_crear_gasto_sp_categoria)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(nuevaCategoria))).perform(click());
        onView(withId(R.id.activity_crear_gasto_et_descripcion)).perform(clearText()).perform(typeText(nuevaDescripcion), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.activity_crear_gasto_bt_guardar)).perform(click());
    }
}
