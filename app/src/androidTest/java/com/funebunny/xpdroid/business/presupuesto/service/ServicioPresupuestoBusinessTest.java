package com.funebunny.xpdroid.business.presupuesto.service;

import android.test.AndroidTestCase;

import com.funebunny.xpdroid.business.gasto.model.Gasto;
import com.funebunny.xpdroid.business.gasto.service.ServicioGastosBusiness;
import com.funebunny.xpdroid.business.presupuesto.model.Presupuesto;
import com.funebunny.xpdroid.utilities.AppConstants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by provirabosch on 17/10/2015.
 */
public class ServicioPresupuestoBusinessTest extends AndroidTestCase {

    private ServicioPresupuestoBusiness servicioPresupuestoBusiness;
    private ServicioGastosBusiness servicioGastosBusiness;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        servicioPresupuestoBusiness = new ServicioPresupuestoBusiness();
        servicioGastosBusiness = new ServicioGastosBusiness();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();

        ArrayList<Presupuesto> presupuestosLista = (ArrayList<Presupuesto>) servicioPresupuestoBusiness.obtenerPresupuesto();

        for (int i = 0; i < presupuestosLista.size(); i++) {
            servicioPresupuestoBusiness.eliminarPresupuesto(presupuestosLista.get(i).getId());
        }

        servicioPresupuestoBusiness.limpiarTotales();
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

    public void testTotales() {
        Gasto gasto = crearGastoTest();
        servicioPresupuestoBusiness.calcularTotales(gasto);
        assertEquals(gasto.getImporte(), servicioPresupuestoBusiness.obtenerTotalDiario());
        assertEquals(gasto.getImporte(), servicioPresupuestoBusiness.obtenerTotalSemanal());
        assertEquals(gasto.getImporte(), servicioPresupuestoBusiness.obtenerTotalMensual());
        assertEquals(gasto.getImporte(), servicioPresupuestoBusiness.obtenerTotalAnual());
        servicioPresupuestoBusiness.descontarTotales(gasto);
        assertEquals("0", servicioPresupuestoBusiness.obtenerTotalDiario());
        assertEquals("0", servicioPresupuestoBusiness.obtenerTotalSemanal());
        assertEquals("0", servicioPresupuestoBusiness.obtenerTotalMensual());
        assertEquals("0", servicioPresupuestoBusiness.obtenerTotalAnual());
    }

//    public void testCalcularTotales_cambioDeDia() {
//        Gasto gasto = servicioGastosBusiness.guardarGasto("Test","100","Test", "20/10/2015");
//        servicioPresupuestoBusiness.calcularTotales();
//        assertEquals(gasto.getImporte(),servicioPresupuestoBusiness.obtenerTotalDiario());
//        servicioGastosBusiness.eliminarGasto(gasto.getId());
//    }

    public void testIsPresupuestoDiarioAlcanzado() {
        Gasto gasto = crearGastoTest();
        servicioPresupuestoBusiness.calcularTotales(gasto);
        servicioPresupuestoBusiness.guardarPresupuesto(AppConstants.PERIODO_DIARIO, "99");
        assertTrue(servicioPresupuestoBusiness.isPresupuestoDiarioAlcanzado());
    }

    public void testIsPresupuestoSemanalAlcanzado() {
        Gasto gasto = crearGastoTest();
        servicioPresupuestoBusiness.calcularTotales(gasto);
        servicioPresupuestoBusiness.guardarPresupuesto(AppConstants.PERIODO_SEMANAL, "99");
        assertTrue(servicioPresupuestoBusiness.isPresupuestoSemanalAlcanzado());
    }

    public void testIsPresupuestoMensualAlcanzado() {
        Gasto gasto = crearGastoTest();
        servicioPresupuestoBusiness.calcularTotales(gasto);
        servicioPresupuestoBusiness.guardarPresupuesto(AppConstants.PERIODO_MENSUAL, "99");
        assertTrue(servicioPresupuestoBusiness.isPresupuestoMensualAlcanzado());
    }

    public void testIsPresupuestoAnualAlcanzado() {
        Gasto gasto = crearGastoTest();
        servicioPresupuestoBusiness.calcularTotales(gasto);
        servicioPresupuestoBusiness.guardarPresupuesto(AppConstants.PERIODO_ANUAL, "99");
        assertTrue(servicioPresupuestoBusiness.isPresupuestoAnualAlcanzado());
    }

    public void testTotalPredeterminado() {
        servicioPresupuestoBusiness.actualizarTotalPredeterminado(AppConstants.PERIODO_DIARIO);
        assertEquals(AppConstants.PERIODO_DIARIO, servicioPresupuestoBusiness.obtenerTotalPredeterminado());
    }

    private Gasto crearGastoTest() {
        Gasto gasto = new Gasto();
        SimpleDateFormat formato = new SimpleDateFormat(AppConstants.FECHA_VISTA);
        gasto.setFecha(formato.format(Calendar.getInstance().getTime()));
        gasto.setImporte("100");
        gasto.setCategoria("Test");
        return gasto;
    }
}
