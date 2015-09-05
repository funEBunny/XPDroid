package com.funebunny.xpdroid.business.historial.model;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by I823537 on 05/09/2015.
 */
public class Historial {

    private Long id;
    private int mes;
    private int anio;
    private String total;

    public String getMes() {
        return new DateFormatSymbols(new Locale("es", "ES")).getMonths()[this.mes+1];
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public String getAnio() {
        return String.valueOf(this.anio);
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
