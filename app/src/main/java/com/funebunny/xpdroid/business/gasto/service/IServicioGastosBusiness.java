package com.funebunny.xpdroid.business.gasto.service;

import android.content.Context;

import com.funebunny.xpdroid.business.gasto.model.Gasto;
import com.funebunny.xpdroid.business.gasto.model.GastoFavorito;
import com.funebunny.xpdroid.business.gasto.model.GastoProgramable;

import java.util.Calendar;
import java.util.List;

/**
 * Created by provirabosch on 26/08/2015.
 */
public interface IServicioGastosBusiness {

    List<GastoProgramable> obtenerGastosProgramables();

    GastoProgramable guardarGastoProgramable(Context applicationContext, String descripcion, String repeticion, String horario, String importe, String categoria, String diaSemana);

    void eliminarGastoProgramable(Context applicationContext, Long id);

    void actualizarGastoProgramable(Context applicationContext, GastoProgramable gastoProgramable);

    int getDiaSemana(String diaSemana);

    String getDiaSemana(int diaSemana);

    // Gastos Favoritos
    GastoFavorito guardarGastoFavorito(String descripcion, String importe, String categoria);

    List<GastoFavorito> obtenerGastosFavoritos();

    void eliminarGastoFavorito(Long id);

    void actualizarGastoFavorito(GastoFavorito gf);

    // Gastos
    Gasto guardarGasto(String descripcion, String importe, String categoria, String fecha);

    void actualizarGasto(Gasto gasto);

    void eliminarGasto(Long id);

    List<Gasto> obtenerGastos();

    List<Gasto> obtenerGastosPorCategoria(String categoria);

    List<Gasto> obtenerGastosMes(Calendar fecha);

    List<Gasto> obtenerGastosSemana(Calendar fecha);

    List<Gasto> obtenerGastosAnio(Calendar instance);

    List<Gasto> obtenerGastosDia(Calendar instance);
}
