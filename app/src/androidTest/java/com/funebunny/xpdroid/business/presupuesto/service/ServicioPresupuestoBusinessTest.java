package com.funebunny.xpdroid.business.presupuesto.service;

import android.test.AndroidTestCase;

import com.funebunny.xpdroid.business.presupuesto.model.Presupuesto;
import com.funebunny.xpdroid.utilities.AppConstants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

    public void testIsMismaSemana_verdadero() {
        Calendar calendar = Calendar.getInstance();
        assertTrue(servicioPresupuestoBusiness.isMismaSemana(calendar));
    }

    public void testIsMismaSemana_falso() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        assertFalse(servicioPresupuestoBusiness.isMismaSemana(calendar));
    }

    public void testIsMismoMes_verdadero() {
        Calendar calendar = Calendar.getInstance();
        assertTrue(servicioPresupuestoBusiness.isMismoMes(calendar));
    }

    public void testIsMismoMes_falso() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        assertFalse(servicioPresupuestoBusiness.isMismoMes(calendar));
    }

    public void testIsMismoDia_verdadero() {
        Calendar calendar = Calendar.getInstance();
        assertTrue(servicioPresupuestoBusiness.isMismoDia(calendar));
    }

    public void testIsMismoDia_falso() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        assertFalse(servicioPresupuestoBusiness.isMismoDia(calendar));
    }

    public void testIsMismoAnio_verdadero() {
        Calendar calendar = Calendar.getInstance();
        assertTrue(servicioPresupuestoBusiness.isMismoAnio(calendar));
    }

    public void testIsMismoAnio_falso() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 1);
        assertFalse(servicioPresupuestoBusiness.isMismoAnio(calendar));
    }

}
