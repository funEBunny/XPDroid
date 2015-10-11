package com.funebunny.xpdroid.backend.gasto.service;

import com.funebunny.xpdroid.business.gasto.model.Gasto;
import com.funebunny.xpdroid.business.gasto.model.GastoFavorito;
import com.funebunny.xpdroid.business.gasto.model.GastoProgramable;

import java.util.List;

/**
 * Created by schmidt0 on 4/3/2015.
 */
public interface IServicioGastosDAO {

    // Gastos

    List<Gasto> obtenerGastos();

    List<Gasto> obtenerGastosPorCategoria(String categoria);

    List<Gasto> obtenerGastosFechaLike(String fecha);

    List<Gasto> obtenerGastosDesdeHasta(String desde, String hasta);

    Long guardarGasto(Gasto gasto);

    void eliminarGasto(Long id);

    void actualizarGasto(Gasto gasto);


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
