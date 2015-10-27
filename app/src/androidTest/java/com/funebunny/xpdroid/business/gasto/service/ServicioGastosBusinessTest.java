package com.funebunny.xpdroid.business.gasto.service;

import android.test.AndroidTestCase;

import com.funebunny.xpdroid.backend.gasto.service.ServicioGastosDAO;
import com.funebunny.xpdroid.business.gasto.model.Gasto;
import com.funebunny.xpdroid.business.gasto.model.GastoFavorito;
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
    private List<GastoFavorito> gastoFavoritos;
    private GastoFavorito gastoFavorito;
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

        // LIMPIA TODOS LOS GASTOS MANUALES CARGADOS EN OTROS TESTS!!!
        gastos = servicioGastosBusiness.obtenerGastos();
        for (Gasto g: gastos) {
            servicioGastosBusiness.eliminarGasto(g.getId());
        }
        gastos = servicioGastosBusiness.obtenerGastos();
        assertEquals(0, gastos.size());

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

        // LIMPIA TODOS LOS GASTOS FAVORITOS CARGADOS EN OTROS TESTS!!!
        gastoFavoritos = servicioGastosBusiness.obtenerGastosFavoritos();
        for (GastoFavorito gf: gastoFavoritos) {
            servicioGastosBusiness.eliminarGastoFavorito(gf.getId());
        }
        gastoFavoritos = servicioGastosBusiness.obtenerGastosFavoritos();
        assertEquals(0, gastoFavoritos.size());

        // GUARDAR
        servicioGastosBusiness.guardarGastoFavorito("Gastos1", "100.50", "Varios");
        gastoFavoritos = servicioGastosBusiness.obtenerGastosFavoritos();
        gastoFavorito = gastoFavoritos.get(0);
        assertEquals("Gastos1", gastoFavorito.getDescripcion());
        assertEquals("100.50", gastoFavorito.getImporte());
        assertEquals("Varios", gastoFavorito.getCategoria());

        servicioGastosBusiness.guardarGastoFavorito("Gastos2", "1500", "Supermercado");
        gastoFavoritos = servicioGastosBusiness.obtenerGastosFavoritos();
        assertEquals(2, gastoFavoritos.size());

        gastoFavorito = gastoFavoritos.get(0);
        assertEquals("Gastos1", gastoFavorito.getDescripcion());
        assertEquals("100.50", gastoFavorito.getImporte());
        assertEquals("Varios", gastoFavorito.getCategoria());

        gastoFavorito = gastoFavoritos.get(1);
        assertEquals("Gastos2", gastoFavorito.getDescripcion());
        assertEquals("1500", gastoFavorito.getImporte());
        assertEquals("Supermercado", gastoFavorito.getCategoria());

        // ACTUALIZAR
        gastoFavorito = gastoFavoritos.get(1);
        gastoFavorito.setDescripcion("Gastos2");
        gastoFavorito.setImporte("2000");
        gastoFavorito.setCategoria("Supermercado");
        servicioGastosBusiness.actualizarGastoFavorito(gastoFavorito);
        gastoFavoritos = servicioGastosBusiness.obtenerGastosFavoritos();
        gastoFavorito = gastoFavoritos.get(1);
        assertEquals("Gastos2", gastoFavorito.getDescripcion());
        assertEquals("2000", gastoFavorito.getImporte());
        assertEquals("Supermercado", gastoFavorito.getCategoria());

        // ELIMINAR
        gastoFavoritos = servicioGastosBusiness.obtenerGastosFavoritos();
        for (GastoFavorito gf: gastoFavoritos) {
            servicioGastosBusiness.eliminarGastoFavorito(gf.getId());
        }
        gastoFavoritos = servicioGastosBusiness.obtenerGastosFavoritos();
        assertEquals(0, gastoFavoritos.size());

    }

    public void testGuardarActualizarObtenerEliminarGastoProgramables(){

        // LIMPIA TODOS LOS GASTOS PROGRAMABLES CARGADOS EN OTROS TESTS!!!
        gastoProgramables = servicioGastosBusiness.obtenerGastosProgramables();
        for (GastoProgramable gp: gastoProgramables) {
            servicioGastosBusiness.eliminarGastoProgramable(getContext(), gp.getId());
        }
        gastoProgramables = servicioGastosBusiness.obtenerGastosProgramables();
        assertEquals(0, gastoProgramables.size());

        // GUARDAR
        //public void guardarGastoProgramable(  Context applicationContext, String descripcion, String repeticion, String horario,
        //                                      String importe, String categoria, String diaSemana) {
        servicioGastosBusiness.guardarGastoProgramable(getContext(), "Gastos1", "diario", "10:01", "1500", "Supermercado", null);
        gastoProgramables = servicioGastosBusiness.obtenerGastosProgramables();
        gastoProgramable = gastoProgramables.get(0);
        assertEquals("Gastos1", gastoProgramable.getDescripcion());
        assertEquals("10:01", gastoProgramable.getHora());
        assertEquals("1500", gastoProgramable.getImporte());
        assertEquals("Supermercado", gastoProgramable.getCategoria());

        servicioGastosBusiness.guardarGastoProgramable(getContext(), "Gastos2", "semanal", "15:01", "2500", "Varios", "lunes");
        gastoProgramables = servicioGastosBusiness.obtenerGastosProgramables();
        assertEquals(2, gastoProgramables.size());

        gastoProgramable = gastoProgramables.get(0);
        assertEquals("Gastos1", gastoProgramable.getDescripcion());
        assertEquals("10:01", gastoProgramable.getHora());
        assertEquals("1500", gastoProgramable.getImporte());
        assertEquals("Supermercado", gastoProgramable.getCategoria());

        gastoProgramable = gastoProgramables.get(1);
        assertEquals("Gastos2", gastoProgramable.getDescripcion());
        assertEquals("15:01", gastoProgramable.getHora());
        assertEquals("2500", gastoProgramable.getImporte());
        assertEquals("Varios", gastoProgramable.getCategoria());

        // ACTUALIZAR
        gastoProgramable = gastoProgramables.get(0);
        gastoProgramable.setDescripcion("Gastos1");
        gastoProgramable.setCategoria("Supermercado");
        gastoProgramable.setImporte("1800");
        gastoProgramable.setHora("13:10");
        servicioGastosBusiness.actualizarGastoProgramable(getContext(), gastoProgramable);
        gastoProgramables = servicioGastosBusiness.obtenerGastosProgramables();
        gastoProgramable = gastoProgramables.get(0);
        assertEquals("Gastos1", gastoProgramable.getDescripcion());
        assertEquals("1800", gastoProgramable.getImporte());
        assertEquals("Supermercado", gastoProgramable.getCategoria());
        assertEquals("13:10", gastoProgramable.getHora());

        // ELIMINAR
        gastoProgramables = servicioGastosBusiness.obtenerGastosProgramables();
        for (GastoProgramable gp: gastoProgramables) {
            servicioGastosBusiness.eliminarGastoProgramable(getContext(), gp.getId());
        }
        gastoProgramables = servicioGastosBusiness.obtenerGastosProgramables();
        assertEquals(0, gastoProgramables.size());

    }

    public void testGetDiaSemana() {

    }

}
