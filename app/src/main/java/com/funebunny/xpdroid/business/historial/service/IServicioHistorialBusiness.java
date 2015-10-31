package com.funebunny.xpdroid.business.historial.service;

import com.funebunny.xpdroid.business.gasto.model.Gasto;
import com.funebunny.xpdroid.business.historial.model.Historial;

import java.util.List;

/**
 * Created by I823537 on 05/09/2015.
 */
public interface IServicioHistorialBusiness {

    List<Historial> obtenerListaHistorial();

    void eliminarHistorial(Gasto gasto);

    void guardarHistorial(Gasto gasto);

    void limpiarHistoriales();

}
