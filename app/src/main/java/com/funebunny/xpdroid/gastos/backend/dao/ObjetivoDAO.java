package com.funebunny.xpdroid.gastos.backend.dao;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by provirabosch on 19/08/2015.
 */
@Table(name = "Objetivo")
public class ObjetivoDAO extends Model {

    @Column(name = "Periodo")
    private String periodo;

    @Column(name = "Importe")
    private String importe;

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

}
