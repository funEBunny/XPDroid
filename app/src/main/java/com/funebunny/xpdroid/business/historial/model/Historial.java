package com.funebunny.xpdroid.business.historial.model;

import java.text.DateFormatSymbols;
import java.util.Locale;

/**
 * Created by I823537 on 05/09/2015.
 */
public class Historial {

    private Long id;
    private int mes;
    private int anio;
    private String total;

    public String getTextoMes() {
        return new DateFormatSymbols(new Locale("es", "ES")).getMonths()[this.mes];
    }
    public int getMes() {
        return this.mes;
    }
    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return this.anio;
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

    @Override
    public String toString() {
        return "Historial{" +
                "id=" + id +
                ", mes=" + mes +
                ", anio=" + anio +
                ", total='" + total + '\'' +
                '}';
    }
}
