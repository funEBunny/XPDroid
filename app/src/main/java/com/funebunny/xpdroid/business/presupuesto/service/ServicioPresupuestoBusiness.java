package com.funebunny.xpdroid.business.presupuesto.service;

import com.funebunny.xpdroid.backend.presupuesto.service.ServicioPresupuestoDAO;
import com.funebunny.xpdroid.business.presupuesto.model.Presupuesto;
import com.funebunny.xpdroid.utilities.AppConstants;

import java.util.List;

/**
 * Created by provirabosch on 22/08/2015.
 */
public class ServicioPresupuestoBusiness implements IServicioPresupuestoBusiness {

    ServicioPresupuestoDAO servicioPresupuestoDAO = new ServicioPresupuestoDAO();

    public void guardarPresupuesto(String periodo, String importe){

        Presupuesto presupuesto = new Presupuesto();
        presupuesto.setPeriodo(periodo);
        presupuesto.setImporte(importe);
        servicioPresupuestoDAO.guardarPresupuesto(presupuesto);

    }

    public List<Presupuesto> obtenerPresupuesto(){
        return servicioPresupuestoDAO.obtenerPresupuesto();
    }

    public void eliminarPresupuesto(Long id){
        servicioPresupuestoDAO.eliminarPresupuesto(id);
    }

    public void actualizarPresupuesto(Presupuesto obj){
        servicioPresupuestoDAO.actualizarPresupuesto(obj);
    }

    @Override
    public boolean isLimitePresupuestoAlcanzado() {
        return (servicioPresupuestoDAO.obtenerPresupuesto().size() == AppConstants.CANT_MAX_PRESUPUESTO);
    }

    @Override
    public boolean tipoPresupuestoExiste(String periodo) {
        return servicioPresupuestoDAO.obtenerPresupuestoPorPeriodo(periodo) !=null;
    }
}
