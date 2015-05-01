package com.funebunny.xpdroid.gastos.backend;

import com.activeandroid.query.Select;
import com.funebunny.xpdroid.gastos.dao.Gasto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        return new Select().from(Gasto.class).where("Categoria = ?", categoria,categoria).orderBy("Nombre ASC").execute();
    }

    @Override
    public List<Gasto> obtenerGastosPorFecha(String mes, String anio) {
        String fecha = anio+mes;
        return new Select().from(Gasto.class).where("Fecha LIKE ?",fecha ).orderBy("Nombre ASC").execute();
    }

    @Override
    public void guardarGasto(String descripcion, String importe, String categoria, String fecha) {
        Gasto gasto = new Gasto();
        gasto.setImporte(importe);
        gasto.setDescripcion(descripcion);
        gasto.setCategoria(categoria);

        String fechaGasto="";
        SimpleDateFormat fromUser = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            fechaGasto = myFormat.format(fromUser.parse(fecha));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        gasto.setFecha(fechaGasto);
        gasto.save();
    }

}
