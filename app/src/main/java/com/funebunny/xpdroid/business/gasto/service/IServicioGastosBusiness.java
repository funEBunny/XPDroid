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

    public List<GastoProgramable> obtenerGastosProgramables();

    public void guardarGastoProgramable(Context applicationContext, String descripcion, String repeticion, String horario, String importe, String categoria, String diaSemana);

    public void eliminarGastoProgramable(Context applicationContext, Long id);

    public void actualizarGastoProgramable(Context applicationContext, GastoProgramable gastoProgramable);

    public int getDiaSemana(String diaSemana);

    public String getDiaSemana(int diaSemana);

    // Gastos Favoritos
    public void guardarGastoFavorito(String descripcion, String importe, String categoria);

    public List<GastoFavorito> obtenerGastosFavoritos();

    public void eliminarGastoFavorito(Long id);

    public void actualizarGastoFavorito(GastoFavorito gf);

    // Gastos
    public void guardarGasto(String descripcion, String importe, String categoria, String fecha);

    public void actualizarGasto(Gasto gasto);

    public void eliminarGasto(Long id);

    public List<Gasto> obtenerGastos();

    public List<Gasto> obtenerGastosPorCategoria(String categoria);

    public List<Gasto> obtenerGastosPorFecha(Calendar fecha);
}
