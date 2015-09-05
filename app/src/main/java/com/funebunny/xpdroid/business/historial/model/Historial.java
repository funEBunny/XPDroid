package com.funebunny.xpdroid.business.historial.model;

/**
 * Created by I823537 on 05/09/2015.
 */
public class Historial {

    private Long id;
    private int mes;
    private int anio;
    private String total;

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
