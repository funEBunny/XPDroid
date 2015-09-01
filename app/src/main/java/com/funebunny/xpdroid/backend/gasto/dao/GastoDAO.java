package com.funebunny.xpdroid.backend.gasto.dao;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.funebunny.xpdroid.utilities.AppConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by schmidt0 on 3/28/2015.
 */
@Table(name = "Gasto")
public class GastoDAO extends Model {

    @Column(name = "Descripcion")
    private String descripcion;

    @Column(name = "Importe")
    private String importe;

    @Column(name = "Categoria")
    private String categoria;

    @Column(name = "Fecha")
    public String fecha;

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

    public String getFecha() {
        SimpleDateFormat viewFormat = new SimpleDateFormat(AppConstants.FECHA_VISTA);
        SimpleDateFormat daoFormat = new SimpleDateFormat(AppConstants.FECHA_DB);
        try {
            return viewFormat.format(daoFormat.parse(fecha));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setFecha(String fecha) {

        String fechaGasto = "";
        SimpleDateFormat fromUser = new SimpleDateFormat(AppConstants.FECHA_VISTA);
        SimpleDateFormat myFormat = new SimpleDateFormat(AppConstants.FECHA_DB);
        try {
            fechaGasto = myFormat.format(fromUser.parse(fecha));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.fecha = fechaGasto;
    }

    @Override
    public String toString() {
        return "GastoDAO{" +
                "descripcion='" + getDescripcion() + '\'' +
                ", importe='" + getImporte() + '\'' +
                ", categoria=" + getCategoria() +
                ", fecha='" + getFecha() + '\'' +
                '}';
    }

}
