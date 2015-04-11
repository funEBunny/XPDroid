package com.funebunny.xpdroid.gastos.backend;

import com.activeandroid.query.Select;
import com.funebunny.xpdroid.gastos.dao.Categoria;
import com.funebunny.xpdroid.gastos.dao.Gasto;

import java.util.ArrayList;
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
    public Categoria obtenerCategoria(String categoria) {
        Categoria categoria1=null;
        ArrayList<Categoria> categorias = new Select().from(Categoria.class).orderBy("Nombre ASC").where("Nombre=?", categoria).execute();
        if (!categorias.isEmpty()){
            categoria1 = categorias.get(0);
        }
        return categoria1;
    }
}
