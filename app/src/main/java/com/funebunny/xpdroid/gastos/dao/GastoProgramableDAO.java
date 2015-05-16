package com.funebunny.xpdroid.gastos.dao;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by schmidt0 on 5/16/2015.
 */
@Table(name = "GastoProgramable")
public class GastoProgramableDAO extends Model {

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

    public Integer getHora() {
        return hora;
    }

    public void setHora(Integer hora) {
        this.hora = hora;
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

    @Column(name = "diaMes")
    private Integer diaMes = null;

    @Column(name = "mes")
    private Integer mes = null;

    @Column(name = "hora")
    private Integer hora = null;

    @Column(name = "diaSemana")
    private Integer diaSemana = null;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "importe")
    private String importe;

    @Column(name = "categoria")
    private String categoria;

}
