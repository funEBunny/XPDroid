package com.funebunny.xpdroid.gastos.dao;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by schmidt0 on 3/28/2015.
 */
@Table(name = "Gasto")
public class Gasto extends Model {

    @Column(name = "Nombre")
    public String nombre;

    @Column(name = "Monto")
    public String monto;

    @Column(name = "Categoria")
    public Categoria categoria;

    @Override
    public String toString() {
        return "Gasto{" +
                "Nombre = '" + nombre + '\'' +
                "Monto = '" + monto + '\'' +
                ", Categoria = " + categoria.nombre +
                '}';
    }
}
