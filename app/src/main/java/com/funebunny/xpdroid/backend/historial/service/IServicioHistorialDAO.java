package com.funebunny.xpdroid.backend.historial.service;


import com.funebunny.xpdroid.business.historial.model.Historial;

import java.util.List;

/**
 * Created by I823537 on 05/09/2015.
 */
public interface IServicioHistorialDAO {

    List<Historial> obtenerListaHistorial();

    Long guardarHistorial(Historial historial);

    void eliminarHistorial(Long id);

    void actualizarHistorial(Historial historial);

    Historial obtenerHistorial(int mes, int anio);

}
