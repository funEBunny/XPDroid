package com.funebunny.xpdroid.backend.presupuesto.service;

import com.funebunny.xpdroid.backend.presupuesto.dao.TotalesDAO;
import com.funebunny.xpdroid.business.presupuesto.model.Presupuesto;

import java.util.List;

/**
 * Created by provirabosch on 22/08/2015.
 */
public interface IServicioPresupuestoDAO {

    Long guardarPresupuesto(Presupuesto obj);

    void eliminarPresupuesto(Long id);

    void actualizarPresupuesto(Presupuesto obj);

    List<Presupuesto> obtenerPresupuesto();

    Presupuesto obtenerPresupuestoPorPeriodo(String periodo);

    TotalesDAO obtenerTotales();

    void guardarTotalDiario(String total);
    void guardarTotalSemanal(String total);
    void guardarTotalMensual(String total);
    void guardarTotalAnual(String total);

    void inicializarTotales();
}
