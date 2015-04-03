package com.funebunny.xpdroid.gastos.backend;

import com.activeandroid.query.Select;
import com.funebunny.xpdroid.gastos.dao.Categoria;
import com.funebunny.xpdroid.gastos.dao.Gasto;

import java.util.List;

/**
 * Created by schmidt0 on 4/3/2015.
 */
public class ServicioGastos implements IServicioGastos {


    @Override
    public List<Gasto> obtenerGastos() {
        return new Select().from(Gasto.class).orderBy("Nombre ASC").execute();
    }

    @Override
    public List<Categoria> obtenerCategorias() {
        return new Select().from(Categoria.class).orderBy("Nombre ASC").execute();
    }

    @Override
    public List<Gasto> obtenerGastosPorCategoria(Categoria categoria) {
        return new Select().from(Gasto.class).where("Categoria = ?", categoria.getId()).orderBy("Nombre ASC").execute();
    }

    @Override
    public void guardatGasto(String nombre, String monto, Categoria categoria) {

        Gasto gasto = new Gasto();
        gasto.nombre=nombre;
        gasto.monto=monto;
        gasto.categoria=categoria;
        gasto.save();
    }

    @Override
    public void guardatCategoria(String nombre) {
        Categoria categoria=new Categoria();
        categoria.nombre=nombre;
        categoria.save();
    }
}
