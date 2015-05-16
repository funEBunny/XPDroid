package com.funebunny.xpdroid.gastos.model;

/**
 * Created by schmidt0 on 5/16/2015.
 */

public abstract class GastoProgramable {

    public static final int DOMINGO = 1;
    public static final int LUNES = 2;
    public static final int MARTES = 3;
    public static final int MIERCOLES = 4;
    public static final int JUEVES = 5;
    public static final int VIERNES = 6;
    public static final int SABADO = 7;

    public static final int ENERO = 0;
    public static final int FEBRERO = 1;
    public static final int MARZO = 2;
    public static final int ABRIL = 3;
    public static final int MAYO = 4;
    public static final int JUNIO = 5;
    public static final int JULIO = 6;
    public static final int AUGOSTO = 7;
    public static final int SEPTIEMBRE = 8;
    public static final int OCTUBRE = 9;
    public static final int NOVIEMBRE = 10;
    public static final int DICIEMBRE = 11;

    private String descripcion;
    private String categoria;
    private String importe;

    private String fechaDeNotificacion;

}