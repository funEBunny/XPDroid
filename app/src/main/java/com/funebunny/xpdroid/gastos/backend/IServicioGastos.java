package com.funebunny.xpdroid.gastos.backend;

import com.funebunny.xpdroid.gastos.dao.Gasto;

import java.util.List;

/**
 * Created by schmidt0 on 4/3/2015.
 */
public interface IServicioGastos {

    List<Gasto> obtenerGastos();

    List<Gasto> obtenerGastosPorCategoria(String categoria);


}
