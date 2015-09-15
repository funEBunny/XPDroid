package com.funebunny.xpdroid.business.gasto.model;

/**
 * Created by schmidt0 on 5/16/2015.
 */
public class GastoProgAnual extends GastoProgramable {

    private int diaMes;
    private int mes;

    public int getDiaMes() {
        return diaMes;
    }

    public void setDiaMes(int diaMes) {
        this.diaMes = diaMes;
    }

    @Override
    public String toString() {
        return "GastoProgAnual{" +
                "diaMes=" + diaMes +
                ", mes=" + mes +
                "} " + super.toString();
    }
}
