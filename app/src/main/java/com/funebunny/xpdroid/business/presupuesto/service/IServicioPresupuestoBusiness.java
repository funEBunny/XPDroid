package com.funebunny.xpdroid.business.presupuesto.service;

import com.funebunny.xpdroid.business.gasto.model.Gasto;
import com.funebunny.xpdroid.business.presupuesto.model.Presupuesto;

import java.util.List;

/**
 * Created by provirabosch on 22/08/2015.
 */
public interface IServicioPresupuestoBusiness {

    void guardarPresupuesto(String periodo, String importe);

    List<Presupuesto> obtenerPresupuesto();

    void eliminarPresupuesto(Long id);

    void actualizarPresupuesto(Presupuesto obj);

    boolean isLimitePresupuestoAlcanzado();

    boolean tipoPresupuestoExiste(String periodo);

    void validarPresupuesto();

    void calcularTotales();
    void calcularTotales(Gasto gasto);

}
