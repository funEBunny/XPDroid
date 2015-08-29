package com.funebunny.xpdroid.backend.presupuesto.dao;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by provirabosch on 29/08/2015.
 */
@Table(name = "Totales")
public class TotalesDAO extends Model {

    @Column(name = "Diario")
    private String totalDiario;
    @Column(name = "Semanal")
    private String totalSemanal;
    @Column(name = "Mensual")
    private String totalMensual;
    @Column(name = "Anual")
    private String totalAnual;


    public String getTotalDiario() {
        return totalDiario;
    }

    public void setTotalDiario(String totalDiario) {
        this.totalDiario = totalDiario;
    }

    public String getTotalSemanal() {
        return totalSemanal;
    }

    public void setTotalSemanal(String totalSemanal) {
        this.totalSemanal = totalSemanal;
    }

    public String getTotalMensual() {
        return totalMensual;
    }

    public void setTotalMensual(String totalMensual) {
        this.totalMensual = totalMensual;
    }

    public String getTotalAnual() {
        return totalAnual;
    }

    public void setTotalAnual(String totalAnual) {
        this.totalAnual = totalAnual;
    }



}
