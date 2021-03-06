package com.funebunny.xpdroid.business.gasto.model;

import java.io.Serializable;
import java.text.NumberFormat;

/**
 * Created by provirabosch on 26/08/2015.
 */
public class Gasto implements Serializable{

    private Long id;
    private String descripcion;
    private String categoria;
    private String importe;
    private String fecha;

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

        NumberFormat format = NumberFormat.getInstance();
        format.setMaximumFractionDigits(2);
        Double.valueOf(importe);
        this.importe = importe;
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
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", categoria='" + categoria + '\'' +
                ", importe='" + importe + '\'' +
                ", fecha='" + fecha + '\'' +
                '}';
    }
}
