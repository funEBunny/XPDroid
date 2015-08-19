package com.funebunny.xpdroid.gastos.business.model;

import java.io.Serializable;

/**
 * Created by provirabosch on 19/08/2015.
 */
public class Objetivo implements Serializable {

    private Long id;
    private String periodo;
    private String importe;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
