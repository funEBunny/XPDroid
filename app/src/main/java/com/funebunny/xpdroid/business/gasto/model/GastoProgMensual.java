package com.funebunny.xpdroid.business.gasto.model;

/**
 * Created by schmidt0 on 5/16/2015.
 */
public class GastoProgMensual extends GastoProgramable {
    private int diaMes;

    public int getDiaMes() {
        return diaMes;
    }

    public void setDiaMes(int diaMes) {
        this.diaMes = diaMes;
    }

    @Override
    public String toString() {
        return "GastoProgMensual{" +
                "diaMes=" + diaMes +
                "} " + super.toString();
    }
}
