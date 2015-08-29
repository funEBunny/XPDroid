package com.funebunny.xpdroid.backend.presupuesto.service;

import com.funebunny.xpdroid.business.presupuesto.model.Objetivo;

import java.util.List;

/**
 * Created by provirabosch on 22/08/2015.
 */
public interface IServicioObjetivosDAO {

    Long guardarObjetivo(Objetivo obj);

    void eliminarObjetivo(Long id);

    void actualizarObjetivo(Objetivo obj);

    List<Objetivo> obtenerObjetivos();

    Objetivo obtenerObjetivoPorPeriodo(String periodo);

}
