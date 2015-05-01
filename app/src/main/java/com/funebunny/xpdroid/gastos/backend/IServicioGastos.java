package com.funebunny.xpdroid.gastos.backend;

import com.funebunny.xpdroid.gastos.dao.Gasto;

import java.util.List;

/**
 * Created by schmidt0 on 4/3/2015.
 */
public interface IServicioGastos {

    List<Gasto> obtenerGastos();

    List<Gasto> obtenerGastosPorCategoria(String categoria);

    List<Gasto> obtenerGastosPorFecha(String mes, String anio);

    /**
     *
     * @param descripcion
     * @param importe
     * @param categoria
     * @param fecha
     */
   void guardarGasto(String descripcion, String importe, String categoria, String fecha );



}
