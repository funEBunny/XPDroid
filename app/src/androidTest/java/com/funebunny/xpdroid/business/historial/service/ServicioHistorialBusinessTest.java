package com.funebunny.xpdroid.business.historial.service;

import android.test.AndroidTestCase;

import com.funebunny.xpdroid.business.gasto.model.Gasto;
import com.funebunny.xpdroid.business.historial.model.Historial;

import org.junit.Test;

import java.util.List;

/**
 * Created by provirabosch on 17/10/2015.
 */
public class ServicioHistorialBusinessTest extends AndroidTestCase {
    ServicioHistorialBusiness servicioHistorialBusiness;
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        servicioHistorialBusiness = new ServicioHistorialBusiness();

    }

    public void testObtenerListaHistorial(){
        List<Historial> historial = servicioHistorialBusiness.obtenerListaHistorial();

    }

    public void testGuardarNuevoHistorial(){
//        Gasto gasto = new Gasto();
//        gasto.setCategoria("Transporte");
//        gasto.setDescripcion("Gasto Test");
//        gasto.setFecha("23/09/2015");
//        gasto.setImporte("100");
//        servicioHistorialBusiness.guardarHistorial(gasto);
//        List<Historial> historiales = servicioHistorialBusiness.obtenerListaHistorial();
//        Historial historial = historiales.get(0);
//        int anio = historial.getAnio();
//        int mes = historial.getMes();
//        String total = historial.getTotal();
//
//        assertEquals("2015",String.valueOf(anio));
//        assertEquals("8",String.valueOf(mes));
//        assertEquals("100",total);
//
//        gasto = new Gasto();
//        gasto.setCategoria("Transporte");
//        gasto.setDescripcion("Gasto Test2");
//        gasto.setFecha("23/09/2015");
//        gasto.setImporte("100");
//        servicioHistorialBusiness.guardarHistorial(gasto);
//        historiales = servicioHistorialBusiness.obtenerListaHistorial();
//        historial = historiales.get(0);
//        anio = historial.getAnio();
//        mes = historial.getMes();
//        total = historial.getTotal();
//
//        assertEquals("2015", String.valueOf(anio));
//        assertEquals("8", String.valueOf(mes));
//        assertEquals("200", total);
    }
}
