package com.funebunny.xpdroid.business.gasto.model;

import java.io.Serializable;

/**
 * Created by I823537 on 15/08/2015.
 */
public class GastoFavorito implements Serializable {

    private Long id;
    private String descripcion;
    private String categoria;
    private String importe;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    @Override
    public String toString() {
        return "GastoFavorito{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", categoria='" + categoria + '\'' +
                ", importe='" + importe + '\'' +
                '}';
    }
}
