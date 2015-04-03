package com.funebunny.xpdroid.gastos.backend;

import android.support.v7.internal.widget.ListViewCompat;

import com.funebunny.xpdroid.gastos.dao.Categoria;
import com.funebunny.xpdroid.gastos.dao.Gasto;

import java.util.List;

/**
 * Created by schmidt0 on 4/3/2015.
 */
public interface IServicioGastos {

    List<Gasto> obtenerGastos();

    List<Categoria> obtenerCategorias();

    List<Gasto> obtenerGastosPorCategoria(Categoria categoria);

    void guardatGasto(String nombre, String monto, Categoria categoria);

    void guardatCategoria(String nombre);




}
