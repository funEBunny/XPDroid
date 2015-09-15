package com.funebunny.xpdroid.backend.gasto.dao;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by schmidt0 on 5/16/2015.
 */
@Table(name = "GastosProgramables")
public class GastoProgramableDAO extends Model {
    public static final int DIARIO = 1;

    public static final int SEMANAL = 2;
    public static final int MENSUAL = 3;
    public static final int ANUAL = 4;

    public GastoProgramableDAO(Integer diaMes, Integer mes, String hora, Integer diaSemana, Integer anio, String descripcion, String importe, String categoria, int repeticion) {
        super();
        this.diaMes = diaMes;
        this.mes = mes;
        this.hora = hora;
        this.diaSemana = diaSemana;
        this.anio = anio;
        this.descripcion = descripcion;
        this.importe = importe;
        this.categoria = categoria;
        this.repeticion = repeticion;
    }

    public GastoProgramableDAO() {
        super();
    }

    @Column(name = "DiaMes")
    private Integer diaMes = null;

    @Column(name = "Mes")
    private Integer mes = null;

    @Column(name = "Hora")
    private String hora = null;

    @Column(name = "DiaSemana")
    private Integer diaSemana = null;

    @Column(name = "Anio")
    private Integer anio = null;

    @Column(name = "Descripcion")
    private String descripcion;

    @Column(name = "Importe")
    private String importe;

    @Column(name = "Categoria")
    private String categoria;

    @Column(name = "Repeticion")
    private int repeticion;

    public Integer getDiaMes() {
        return diaMes;
    }

    public void setDiaMes(Integer diaMes) {
        this.diaMes = diaMes;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(Integer diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getRepeticion() {
        return repeticion;
    }

    public void setRepeticion(int repeticion) {
        this.repeticion = repeticion;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return "GastoProgramableDAO{" +
                "diaMes=" + diaMes +
                ", mes=" + mes +
                ", hora='" + hora + '\'' +
                ", diaSemana=" + diaSemana +
                ", anio=" + anio +
                ", descripcion='" + descripcion + '\'' +
                ", importe='" + importe + '\'' +
                ", categoria='" + categoria + '\'' +
                ", repeticion=" + repeticion +
                "} " + super.toString();
    }
}
