package com.funebunny.xpdroid.business.historial.model;

/**
 * Created by I823537 on 05/09/2015.
 */
public class Historial {

    private Long id;
    private String mes;
    private String anio;
    private String total;

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
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
