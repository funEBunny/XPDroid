package com.funebunny.xpdroid.business.presupuesto.service;

import android.test.AndroidTestCase;

import com.funebunny.xpdroid.business.presupuesto.model.Presupuesto;
import com.funebunny.xpdroid.utilities.AppConstants;

import java.util.ArrayList;

/**
 * Created by provirabosch on 17/10/2015.
 */
public class ServicioPresupuestoBusinessTest extends AndroidTestCase {

    private ServicioPresupuestoBusiness servicioPresupuestoBusiness;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        servicioPresupuestoBusiness = new ServicioPresupuestoBusiness();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();

        ArrayList<Presupuesto> presupuestosLista = (ArrayList<Presupuesto>) servicioPresupuestoBusiness.obtenerPresupuesto();

        for (int i = 0; i < presupuestosLista.size(); i++) {
            servicioPresupuestoBusiness.eliminarPresupuesto(presupuestosLista.get(i).getId());
        }
    }

    public void testGuardarPresupuesto() {
        servicioPresupuestoBusiness.guardarPresupuesto(AppConstants.PERIODO_DIARIO, "100");
        assertNotNull(servicioPresupuestoBusiness.obtenerPresupuestoDiario());
    }

    public void testEliminarPresupuesto() {
        servicioPresupuestoBusiness.guardarPresupuesto(AppConstants.PERIODO_DIARIO, "100");
        ArrayList<Presupuesto> presupuestosLista = (ArrayList<Presupuesto>) servicioPresupuestoBusiness.obtenerPresupuesto();
        Presupuesto presupuesto = presupuestosLista.get(0);
        servicioPresupuestoBusiness.eliminarPresupuesto(presupuesto.getId());
        presupuestosLista = (ArrayList<Presupuesto>) servicioPresupuestoBusiness.obtenerPresupuesto();
        assertFalse(presupuestosLista.contains(presupuesto));
    }

    public void testActualizarPresupuesto() {
        servicioPresupuestoBusiness.guardarPresupuesto(AppConstants.PERIODO_SEMANAL, "1000");
        ArrayList<Presupuesto> presupuestosLista = (ArrayList<Presupuesto>) servicioPresupuestoBusiness.obtenerPresupuesto();
        Presupuesto presupuesto = presupuestosLista.get(0);
        String esperado = "2000";
        presupuesto.setImporte(esperado);
        servicioPresupuestoBusiness.actualizarPresupuesto(presupuesto);
        assertEquals(esperado, servicioPresupuestoBusiness.obtenerPresupuestoSemanal());
    }

    public void testIsLimitePresupuestoAlcanzado() {
        servicioPresupuestoBusiness.guardarPresupuesto(AppConstants.PERIODO_DIARIO, "100");
        servicioPresupuestoBusiness.guardarPresupuesto(AppConstants.PERIODO_SEMANAL, "1000");
        servicioPresupuestoBusiness.guardarPresupuesto(AppConstants.PERIODO_MENSUAL, "10000");
        servicioPresupuestoBusiness.guardarPresupuesto(AppConstants.PERIODO_ANUAL, "100000");
        assertTrue(servicioPresupuestoBusiness.isLimitePresupuestoAlcanzado());
    }

    public void testTipoPresupuestoExiste() {
        servicioPresupuestoBusiness.guardarPresupuesto(AppConstants.PERIODO_DIARIO, "100");
        assertTrue(servicioPresupuestoBusiness.tipoPresupuestoExiste(AppConstants.PERIODO_DIARIO));
    }
}
