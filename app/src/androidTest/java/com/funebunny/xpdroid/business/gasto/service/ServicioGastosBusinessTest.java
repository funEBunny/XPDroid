package com.funebunny.xpdroid.business.gasto.service;

import android.test.AndroidTestCase;

import com.funebunny.xpdroid.backend.gasto.service.ServicioGastosDAO;
import com.funebunny.xpdroid.business.gasto.model.Gasto;

/**
 * Created by provirabosch on 17/10/2015.
 */
public class ServicioGastosBusinessTest extends AndroidTestCase{

    private ServicioGastosBusiness servicioGastosBusiness;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        servicioGastosBusiness = new ServicioGastosBusiness();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGuardarGasto() {

        //servicioGastosBusiness.guardarGasto();

    }



}
