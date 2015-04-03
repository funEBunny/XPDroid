package com.funebunny.xpdroid.gastos.dao;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by schmidt0 on 3/28/2015.
 */
@Table(name = "Categoria")
public class Categoria extends Model{
    @Column(name = "Nombre")
    public String nombre;
}
