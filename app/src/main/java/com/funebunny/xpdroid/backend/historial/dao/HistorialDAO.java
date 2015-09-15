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
    private int mes;

    @Column(name = "Anio")
    private int anio;

    @Column(name = "Total")
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

    @Override
    public String toString() {
        return "HistorialDAO{" +
                "mes=" + mes +
                ", anio=" + anio +
                ", total='" + total + '\'' +
                "} " + super.toString();
    }
}
