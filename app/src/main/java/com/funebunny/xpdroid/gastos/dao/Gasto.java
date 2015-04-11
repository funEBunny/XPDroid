package com.funebunny.xpdroid.gastos.dao;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by schmidt0 on 3/28/2015.
 */
@Table(name = "Gasto")
public class Gasto extends Model {

    @Column(name = "Descripcion")
    private String descripcion;

    @Column(name = "Importe")
    private String importe;

    @Column(name = "Categoria")
    private String categoria;

    @Column(name = "Fecha")
    public String fecha;

    private String getDescripcion() {
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

    public String getFecha() {

        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Gasto{" +
                "descripcion='" + descripcion + '\'' +
                ", importe='" + importe + '\'' +
                ", categoria=" + categoria +
                ", fecha='" + fecha + '\'' +
                '}';
    }
}
