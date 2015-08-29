package com.funebunny.xpdroid.backend.presupuesto.dao;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by provirabosch on 19/08/2015.
 */
@Table(name = "Presupuesto")
public class PresupuestoDAO extends Model {

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
