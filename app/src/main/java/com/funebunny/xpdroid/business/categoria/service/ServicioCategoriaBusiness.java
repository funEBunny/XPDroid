package com.funebunny.xpdroid.business.categoria.service;

import android.content.Context;
import android.util.Log;

import com.funebunny.xpdroid.backend.gasto.service.ServicioGastosDAO;
import com.funebunny.xpdroid.business.categoria.model.Categoria;
import com.funebunny.xpdroid.business.gasto.model.Gasto;
import com.funebunny.xpdroid.business.gasto.model.GastoFavorito;
import com.funebunny.xpdroid.business.gasto.model.GastoProgDiario;
import com.funebunny.xpdroid.business.gasto.model.GastoProgSemanal;
import com.funebunny.xpdroid.business.gasto.model.GastoProgramable;
import com.funebunny.xpdroid.business.gasto.service.IServicioGastosBusiness;
import com.funebunny.xpdroid.business.notificacion.service.ServicioNotificacionesBusiness;
import com.funebunny.xpdroid.utilities.AppConstants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by schmidt0 on 7/25/2015.
 */
public class ServicioCategoriaBusiness implements IServicioCategoriaBusiness {

    //ServicioCategoriasDAO servicioCategoriasDAO = new ServicioCategoriasDAO();

    @Override
    public List<Categoria> obtenerCategorias() {
        Log.d("XPDROID", "Obteniendo Categorias");
        //return servicioCategoriasDAO.obtenerCategorias();

        //FIXME
        return new ArrayList<Categoria>();
    }

    @Override
    public Categoria guardarCategoria(String nombre) {
        Log.d("XPDROID", "Guardando Categorias");
        Categoria c = new Categoria();

        c.setnombre(nombre);
        //FIXME
        //c.setId(servicioCategoriasDAO.guardarGastoProgramable(gp));
        return c;
    }
}
