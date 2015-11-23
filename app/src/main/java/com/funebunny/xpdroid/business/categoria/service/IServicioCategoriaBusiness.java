package com.funebunny.xpdroid.business.categoria.service;

import android.content.Context;

import com.funebunny.xpdroid.business.categoria.model.Categoria;

import java.util.List;

/**
 * Created by provirabosch on 26/08/2015.
 */
public interface IServicioCategoriaBusiness {

    List<Categoria> obtenerCategorias();

    Categoria guardarCategoria(String nombre);

}
