package com.funebunny.xpdroid.business.historial.service;

import com.funebunny.xpdroid.backend.historial.service.ServicioHistorialDAO;
import com.funebunny.xpdroid.business.historial.model.Historial;

import java.util.List;

/**
 * Created by I823537 on 05/09/2015.
 */
public class ServicioHistorialBusiness implements IServicioHistorialBusiness {

    ServicioHistorialDAO servicioHistorialDAO = new ServicioHistorialDAO();

    @Override
    public List<Historial> obtenerListaHistorial() {
        return servicioHistorialDAO.obtenerListaHistorial();
    }

    @Override
    public void guardarHistorial(Historial historial) {
        servicioHistorialDAO.guardarHistorial(historial);
    }

    @Override
    public void eliminarHistorial(Long id) {

    }

    @Override
    public void actualizarHistorial(Historial historial) {

    }
}
