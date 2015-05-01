package com.funebunny.xpdroid.gastos.backend;

import com.activeandroid.query.Select;
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
    public List<Gasto> obtenerGastosPorCategoria(String categoria) {
        return new Select().from(Gasto.class).where("Categoria = ?", categoria).orderBy("Nombre ASC").execute();
    }

}
