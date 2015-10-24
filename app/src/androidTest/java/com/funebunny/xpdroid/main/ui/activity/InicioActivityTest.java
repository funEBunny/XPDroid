package com.funebunny.xpdroid.main.ui.activity;

import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by provirabosch on 24/10/2015.
 */
public class InicioActivityTest extends ActivityInstrumentationTestCase2<InicioActivity> {

    private InicioActivity inicioActivity;

    public InicioActivityTest() {
        super(InicioActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        inicioActivity = getActivity();
    }

    public void testInicio(){

    }
}
