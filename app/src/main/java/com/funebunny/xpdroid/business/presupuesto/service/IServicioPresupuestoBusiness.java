package com.funebunny.xpdroid.business.presupuesto.service;

import com.funebunny.xpdroid.business.presupuesto.model.Presupuesto;

import java.util.List;

/**
 * Created by provirabosch on 22/08/2015.
 */
public interface IServicioPresupuestoBusiness {

    public void guardarPresupuesto(String periodo, String importe);

    public List<Presupuesto> obtenerPresupuesto();

    public void eliminarPresupuesto(Long id);

    public void actualizarPresupuesto(Presupuesto obj);

    boolean isLimitePresupuestoAlcanzado();

    boolean tipoPresupuestoExiste(String periodo);

}
