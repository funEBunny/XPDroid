package com.funebunny.xpdroid.backend.historial.dao;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by I823537 on 05/09/2015.
 */
@Table(name = "Historial")
public class HistorialDAO extends Model {


    @Column(name = "Mes")
    private String mes;

    @Column(name = "Anio")
    private String anio;

    @Column(name = "Total")
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
}
