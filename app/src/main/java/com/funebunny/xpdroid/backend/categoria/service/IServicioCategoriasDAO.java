package com.funebunny.xpdroid.backend.categoria.service;

import com.funebunny.xpdroid.business.categoria.model.Categoria;

import java.util.List;

/**
 * Created by schmidt0 on 11/24/2015.
 */
public interface IServicioCategoriasDAO {

    Long insertrCategoria(Categoria categoria);

    List<Categoria> obtenerCategorias();
}
