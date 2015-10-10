package com.funebunny.xpdroid;

import android.test.ActivityUnitTestCase;

import com.funebunny.xpdroid.main.ui.activity.CrearPresupuestoActivity;

/**
 * Created by I823537 on 10/10/2015.
 */
public class CrearPresupuestoActivityTest extends ActivityUnitTestCase<CrearPresupuestoActivity> {

    private CrearPresupuestoActivity crearPresupuestoActivity;

    public CrearPresupuestoActivityTest(Class<CrearPresupuestoActivity> activityClass) {
        super(activityClass);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        crearPresupuestoActivity = getActivity();
    }

}
