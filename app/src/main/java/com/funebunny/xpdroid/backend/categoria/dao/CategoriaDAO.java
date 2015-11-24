package com.funebunny.xpdroid.backend.categoria.dao;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by schmidt0 on 11/24/2015.
 */
@Table(name = "Categoria")
public class CategoriaDAO extends Model {

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Column(name = "Nombre")
    String nombre;
}
