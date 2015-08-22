package com.funebunny.xpdroid.gastos.backend.service;

import com.funebunny.xpdroid.gastos.backend.dao.ObjetivoDAO;
import com.funebunny.xpdroid.gastos.business.model.Objetivo;

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
