package com.funebunny.xpdroid.backend.historial.service;

import com.activeandroid.query.Select;
import com.funebunny.xpdroid.backend.historial.dao.HistorialDAO;
import com.funebunny.xpdroid.business.historial.model.Historial;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by I823537 on 05/09/2015.
 */
public class ServicioHistorialDAO implements IServicioHistorialDAO {


    @Override
    public List<Historial> obtenerListaHistorial() {

        List<Historial> listaHistorial = new ArrayList<>();
        ArrayList<HistorialDAO> listaHistorialDAO = new Select().from(HistorialDAO.class).orderBy("Anio DESC").orderBy("Mes DESC").execute();

        for (int i = 0; i < listaHistorialDAO.size(); i++) {

            HistorialDAO historialDAO = listaHistorialDAO.get(i);
            Historial historial = new Historial();

            historial.setId(historialDAO.getId());
            historial.setAnio(historialDAO.getAnio());
            historial.setMes(historialDAO.getMes());
            historial.setTotal(historialDAO.getTotal());
            listaHistorial.add(historial);
        }

        return listaHistorial;
    }

    @Override
    public Long guardarHistorial(Historial historial) {

        HistorialDAO historialDAO = new HistorialDAO();
        historialDAO.setTotal(historial.getTotal());
        historialDAO.setMes(historial.getMes());
        historialDAO.setAnio(historial.getAnio());
        historialDAO.save();
        return null;
    }

    @Override
    public void eliminarHistorial(Long id) {
        obtenerHistorialPorId(id).delete();
    }

    @Override
    public void actualizarHistorial(Historial historial) {
        HistorialDAO historialDAO = obtenerHistorialPorId(historial.getId());
        historialDAO.setTotal(historial.getTotal());
        historialDAO.save();
    }

    public Historial obtenerHistorial(String mes, String anio) {

        ArrayList<HistorialDAO> listaHistorialDAO = new Select().from(HistorialDAO.class).where("Mes = ? AND Anio = ?", mes, anio).execute();

        Historial historial = null;

        if (listaHistorialDAO != null) {

            historial = new Historial();
            historial.setId(listaHistorialDAO.get(0).getId());
            historial.setAnio(listaHistorialDAO.get(0).getAnio());
            historial.setMes(listaHistorialDAO.get(0).getMes());
            historial.setTotal(listaHistorialDAO.get(0).getTotal());

        }

        return historial;
    }

    private HistorialDAO obtenerHistorialPorId(Long id) {

        ArrayList<HistorialDAO> listaHistorialDAO = new Select().from(HistorialDAO.class).where("Id = ?", id).execute();
        return listaHistorialDAO.get(0);
    }
}
