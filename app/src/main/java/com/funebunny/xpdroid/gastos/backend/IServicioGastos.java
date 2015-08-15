package com.funebunny.xpdroid.gastos.backend;

import com.funebunny.xpdroid.gastos.dao.Gasto;
import com.funebunny.xpdroid.gastos.model.GastoProgramable;

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

    List<GastoProgramable> obtenerGastosProgramables();

    List<GastoProgramable> obtenerGastosProgramablesDelDia();

    GastoProgramable obtenerGastoProgramablePorID(Long id);

    Long guardarGastoProgramable(GastoProgramable gp);

    void eliminarGastoProgramable(GastoProgramable gp);

    void eliminarGasto(Long id);

    void actualizarGasto(Gasto gasto);

    Gasto obtenerGastoPorId(Long id);

}
