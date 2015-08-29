package com.funebunny.xpdroid.backend.gasto.dao;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by schmidt0 on 3/28/2015.
 */
@Table(name = "GastoFavorito")
public class GastoFavoritoDAO extends Model {

    @Column(name = "Descripcion")
    private String descripcion;

    @Column(name = "Importe")
    private String importe;

    @Column(name = "Categoria")
    private String categoria;


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }


    @Override
    public String toString() {
        return "GastoDAO{" +
                "descripcion='" + getDescripcion() + '\'' +
                ", importe='" + getImporte() + '\'' +
                ", categoria=" + getCategoria() +
                '}';
    }
}
