package com.funebunny.xpdroid.business.gasto.service;

import android.test.AndroidTestCase;

import com.funebunny.xpdroid.backend.gasto.service.ServicioGastosDAO;
import com.funebunny.xpdroid.business.gasto.model.Gasto;
import com.funebunny.xpdroid.business.gasto.model.GastoProgramable;

import java.util.Calendar;
import java.util.List;

/**
 * Created by provirabosch on 17/10/2015.
 */
public class ServicioGastosBusinessTest extends AndroidTestCase{

    private ServicioGastosBusiness servicioGastosBusiness;
    private List<Gasto> gastos;
    private Gasto gasto;
    private List<GastoProgramable> gastoProgramables;
    private GastoProgramable gastoProgramable;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        servicioGastosBusiness = new ServicioGastosBusiness();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGuardarActualizarObtenerEliminarGastos() {

        // GUARDAR
        servicioGastosBusiness.guardarGasto("Gastos1", "100.50", "Varios", "01/01/2014");
        gastos = servicioGastosBusiness.obtenerGastos();
        gasto = gastos.get(0);
        assertEquals("Gastos1", gasto.getDescripcion());
        assertEquals("100.50", gasto.getImporte());
        assertEquals("Varios", gasto.getCategoria());
        assertEquals("01/01/2014", gasto.getFecha());

        servicioGastosBusiness.guardarGasto("Gastos2", "1500", "Supermercado", "20/02/2015");
        gastos = servicioGastosBusiness.obtenerGastos();
        assertEquals(2, gastos.size());

        gasto = gastos.get(0); // obtenerGastos ordena por Categoria solo
        assertEquals("Gastos2", gasto.getDescripcion());
        assertEquals("1500", gasto.getImporte());
        assertEquals("Supermercado", gasto.getCategoria());
        assertEquals("20/02/2015", gasto.getFecha());

        gasto = gastos.get(1);
        assertEquals("Gastos1", gasto.getDescripcion());
        assertEquals("100.50", gasto.getImporte());
        assertEquals("Varios", gasto.getCategoria());
        assertEquals("01/01/2014", gasto.getFecha());

        // ACTUALIZAR
        gasto = gastos.get(0);
        gasto.setDescripcion("Gastos21");
        gasto.setImporte("2000");
        gasto.setCategoria("Supermercado");
        gasto.setFecha("02/02/2015");
        servicioGastosBusiness.actualizarGasto(gasto);
        gastos = servicioGastosBusiness.obtenerGastos();
        gasto = gastos.get(0);
        assertEquals("Gastos21", gasto.getDescripcion());
        assertEquals("2000", gasto.getImporte());
        assertEquals("Supermercado", gasto.getCategoria());
        assertEquals("02/02/2015", gasto.getFecha());

        // OBTENER
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2015);
        gastos = servicioGastosBusiness.obtenerGastosAnio(cal);
        assertEquals(1, gastos.size());
        assertEquals("Gastos21", gastos.get(0).getDescripcion());

        cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.YEAR, 2014);
        gastos = servicioGastosBusiness.obtenerGastosMes(cal);
        assertEquals(1, gastos.size());
        assertEquals("Gastos1", gastos.get(0).getDescripcion());

        cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.YEAR, 2014);
        gastos = servicioGastosBusiness.obtenerGastosSemana(cal);
        assertEquals(1, gastos.size());
        assertEquals("Gastos1", gastos.get(0).getDescripcion());

        cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 2);
        cal.set(Calendar.MONTH, 1);
        cal.set(Calendar.YEAR, 2015);
        gastos = servicioGastosBusiness.obtenerGastosDia(cal);
        assertEquals(1, gastos.size());
        assertEquals("Gastos21", gastos.get(0).getDescripcion());

        gastos = servicioGastosBusiness.obtenerGastosPorCategoria("Supermercado");
        assertEquals(1, gastos.size());
        assertEquals("Gastos21", gastos.get(0).getDescripcion());

        gastos = servicioGastosBusiness.obtenerGastosPorCategoria("Varios");
        assertEquals(1, gastos.size());
        assertEquals("Gastos1", gastos.get(0).getDescripcion());

        // ELIMINAR
        gastos = servicioGastosBusiness.obtenerGastos();
        for (Gasto g: gastos) {
            servicioGastosBusiness.eliminarGasto(g.getId());
        }
        gastos = servicioGastosBusiness.obtenerGastos();
        assertEquals(0, gastos.size());

    }

    public void testGuardarActualizarObtenerEliminarGastoFavoritos(){

        // GUARDAR


        // ACTUALIZAR


        // OBTENER


        // ELIMINAR


    }

    public void testGuardarActualizarObtenerEliminarGastoProgramables(){

        // GUARDAR


        // ACTUALIZAR


        // OBTENER


        // ELIMINAR


    }

}
