package com.funebunny.xpdroid.business.gasto.model;

/**
 * Created by schmidt0 on 5/16/2015.
 */
public class GastoProgSemanal extends GastoProgramable{

    public static String SEMANAL = "Semanal";
    private int diaSemana;

    @Override
    public String toString() {
        return "GastoProgSemanal{" +
                "diaSemana=" + diaSemana +
                "} " + super.toString();
    }

    public int getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(int diaSemana) {
        this.diaSemana = diaSemana;
    }
}
