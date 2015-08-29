package com.funebunny.xpdroid.business.presupuesto.service;

import com.funebunny.xpdroid.backend.presupuesto.service.ServicioObjetivosDAO;
import com.funebunny.xpdroid.business.presupuesto.model.Objetivo;
import com.funebunny.xpdroid.business.presupuesto.service.IServicioObjetivosBusiness;
import com.funebunny.xpdroid.utilities.AppConstants;

import java.util.List;

/**
 * Created by provirabosch on 22/08/2015.
 */
public class ServicioObjetivosBusiness implements IServicioObjetivosBusiness {

    ServicioObjetivosDAO servicioObjetivosDAO = new ServicioObjetivosDAO();

    public void guardarObjetivo(String periodo, String importe){

        Objetivo objetivo = new Objetivo();
        objetivo.setPeriodo(periodo);
        objetivo.setImporte(importe);
        servicioObjetivosDAO.guardarObjetivo(objetivo);

    }

    public List<Objetivo> obtenerObjetivos(){
        return servicioObjetivosDAO.obtenerObjetivos();
    }

    public void eliminarObjetivo(Long id){
        servicioObjetivosDAO.eliminarObjetivo(id);
    }

    public void actualizarObjetivo(Objetivo obj){
        servicioObjetivosDAO.actualizarObjetivo(obj);
    }

    @Override
    public boolean isLimiteObjetivosAlcanzado() {
        return (servicioObjetivosDAO.obtenerObjetivos().size() == AppConstants.CANT_MAX_OBJETIVOS);
    }

    @Override
    public boolean tipoObjetivoExiste(String periodo) {
        return servicioObjetivosDAO.obtenerObjetivoPorPeriodo(periodo) !=null;
    }
}
