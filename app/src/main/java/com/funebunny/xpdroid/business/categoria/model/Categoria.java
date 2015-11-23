package com.funebunny.xpdroid.business.categoria.model;

import java.text.DateFormatSymbols;
import java.util.Locale;

/**
 * Created by I823537 on 05/09/2015.
 */
public class Categoria {

    private Long id;
    private String nombre;

    public String getnombre() {
        return nombre;
    }

    public void setnombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", Nombre='" + nombre + '\'' +
                '}';
    }
}
