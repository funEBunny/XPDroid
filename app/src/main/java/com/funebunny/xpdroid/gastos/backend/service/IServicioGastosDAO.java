package com.funebunny.xpdroid.gastos.backend.service;

import com.funebunny.xpdroid.gastos.backend.dao.GastoDAO;
import com.funebunny.xpdroid.gastos.business.model.GastoFavorito;
import com.funebunny.xpdroid.gastos.business.model.GastoProgramable;

import java.util.List;

/**
 * Created by schmidt0 on 4/3/2015.
 */
public interface IServicioGastosDAO {

    // Gastos

    List<GastoDAO> obtenerGastos();

    List<GastoDAO> obtenerGastosPorCategoria(String categoria);

    List<GastoDAO> obtenerGastosPorFecha(String mes, String anio);

    /**
     *
     * @param descripcion
     * @param importe
     * @param categoria
     * @param fecha
     */
    void guardarGasto(String descripcion, String importe, String categoria, String fecha );

    void eliminarGasto(Long id);

    void actualizarGasto(GastoDAO gasto);

    GastoDAO obtenerGastoPorId(Long id);

    // Gastos Programables

    List<GastoProgramable> obtenerGastosProgramables();

    GastoProgramable obtenerGastoProgramablePorID(Long id);

    Long guardarGastoProgramable(GastoProgramable gp);

    void eliminarGastoProgramable(Long id);

    void actualizarGastoProgramable(GastoProgramable gastoProgramable);

   // Gastos Favoritos

    Long guardarGastoFavorito(GastoFavorito gf);

    void eliminarGastoFavorito(Long id);

    void actualizarGastoFavorito(GastoFavorito gf);

    List<GastoFavorito> obtenerGastosFavoritos();

}
