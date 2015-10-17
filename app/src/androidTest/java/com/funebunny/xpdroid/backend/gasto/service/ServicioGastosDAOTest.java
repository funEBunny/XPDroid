package com.funebunny.xpdroid.backend.gasto.service;

import android.test.AndroidTestCase;

import com.funebunny.xpdroid.business.gasto.model.Gasto;
import com.funebunny.xpdroid.business.gasto.model.GastoFavorito;
import com.funebunny.xpdroid.utilities.AppConstants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by provirabosch on 15/10/2015.
 */
public class ServicioGastosDAOTest extends AndroidTestCase {

    private ServicioGastosDAO servicioGastosDAO;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        servicioGastosDAO = new ServicioGastosDAO();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGuardarGasto() {
        Gasto gasto = crearGastoTest();
        assertNotNull(servicioGastosDAO.guardarGasto(gasto));
    }

    public void testObtenerGastos() {
        Gasto gasto = crearGastoTest();
        servicioGastosDAO.guardarGasto(gasto);
        assertNotNull(servicioGastosDAO.obtenerGastos());
    }

    public void testObtenerGastosPorCategoria(){
        Gasto gasto = crearGastoTest();
        servicioGastosDAO.guardarGasto(gasto);
        assertNotNull(servicioGastosDAO.obtenerGastosPorCategoria(gasto.getCategoria()));
    }

    public void testObtenerGastosFechaLike(){
        Gasto gasto = crearGastoTest();
        servicioGastosDAO.guardarGasto(gasto);
        assertNotNull(servicioGastosDAO.obtenerGastosPorCategoria(gasto.getFecha()));
    }

    public void testEliminarGasto(){
        Gasto gasto = crearGastoTest();
        gasto.setId(servicioGastosDAO.guardarGasto(gasto));
        servicioGastosDAO.eliminarGasto(gasto.getId());
        ArrayList<Gasto> gastosLista = (ArrayList<Gasto>) servicioGastosDAO.obtenerGastos();
        assertFalse(gastosLista.contains(gasto));
    }

    public void testActualizarGasto(){
        Gasto gasto = crearGastoTest();
        gasto.setId(servicioGastosDAO.guardarGasto(gasto));
        gasto.setDescripcion("Gasto Test Editado");
        servicioGastosDAO.actualizarGasto(gasto);
        ArrayList<Gasto> gastosLista = (ArrayList<Gasto>) servicioGastosDAO.obtenerGastos();

        int i = 0;
        while (gastosLista.get(i).getId()!= gasto.getId()){
            i++;
        }
        assertNotSame(gasto.getDescripcion(), gastosLista.get(i).getDescripcion());
    }

    public void testGuardarGastoFavorito(){
        GastoFavorito gastoFavorito = crearGastoFavoritoTest();
        assertNotNull(servicioGastosDAO.guardarGastoFavorito(gastoFavorito));
    }

    public void testObtenerGastosFavoritos(){
        GastoFavorito gastoFavorito = crearGastoFavoritoTest();
        servicioGastosDAO.guardarGastoFavorito(gastoFavorito);
        assertNotNull(servicioGastosDAO.obtenerGastosFavoritos());
    }

    public void testEliminarGastoFavorito(){
        GastoFavorito gastoFavorito = crearGastoFavoritoTest();
        gastoFavorito.setId(servicioGastosDAO.guardarGastoFavorito(gastoFavorito));
        servicioGastosDAO.eliminarGastoFavorito(gastoFavorito.getId());
        ArrayList<GastoFavorito> gastosFavoritosLista = (ArrayList<GastoFavorito>) servicioGastosDAO.obtenerGastosFavoritos();
        assertFalse(gastosFavoritosLista.contains(gastoFavorito));
    }

    public void testActualizarGastoFavorito(){
        GastoFavorito gastoFavorito = crearGastoFavoritoTest();
        gastoFavorito.setId(servicioGastosDAO.guardarGastoFavorito(gastoFavorito));
        gastoFavorito.setDescripcion("Gasto Favorito Test Editado");
        servicioGastosDAO.actualizarGastoFavorito(gastoFavorito);
        ArrayList<GastoFavorito> gastosFavoritosLista = (ArrayList<GastoFavorito>) servicioGastosDAO.obtenerGastosFavoritos();

        int i = 0;
        while (gastosFavoritosLista.get(i).getId()!= gastoFavorito.getId()){
            i++;
        }
        assertNotSame(gastoFavorito.getDescripcion(), gastosFavoritosLista.get(i).getDescripcion());
    }

    private Gasto crearGastoTest() {
        Gasto gasto = new Gasto();
        SimpleDateFormat formato = new SimpleDateFormat(AppConstants.FECHA_VISTA);
        gasto.setFecha(formato.format(Calendar.getInstance().getTime()));
        gasto.setImporte("1");
        gasto.setCategoria("Test");
        return gasto;
    }

    private GastoFavorito crearGastoFavoritoTest(){
        GastoFavorito gastoFavorito = new GastoFavorito();
        gastoFavorito.setImporte("1");
        gastoFavorito.setCategoria("Test");
        return gastoFavorito;
    }

}
