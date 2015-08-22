package com.funebunny.xpdroid.gastos.business.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by schmidt0 on 5/16/2015.
 */

public abstract class GastoProgramable implements Serializable {

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

    private Long id;
    private String descripcion;
    private String categoria;
    private String importe;
    private int hora;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "GastoProgramable{" +
                "descripcion='" + descripcion + '\'' +
                ", categoria='" + categoria + '\'' +
                ", importe='" + importe + '\'' +
                ", hora=" + hora +
                '}';
    }

    public String getHora() {
        SimpleDateFormat toUser = new SimpleDateFormat("HH:mm");
        SimpleDateFormat fromDB = new SimpleDateFormat("HHmm");
        try {
            String sHora = String.valueOf(hora);
            Date from = fromDB.parse(sHora);
            String to = toUser.format(from);
            return to;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public void setHora(String hora) {
        SimpleDateFormat fromUser = new SimpleDateFormat("HH:mm");
        SimpleDateFormat toDB = new SimpleDateFormat("HHmm");
        try {
            this.hora = Integer.valueOf(toDB.format(fromUser.parse(hora)));
        } catch (ParseException e) {
           this.hora =0;
        }
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

}
