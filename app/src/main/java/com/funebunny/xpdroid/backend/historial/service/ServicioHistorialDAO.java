package com.funebunny.xpdroid.backend.historial.service;

import android.util.Log;

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
        Log.d("XPDROID", "Obtener Historial");
        List<Historial> listaHistorial = new ArrayList<>();
        ArrayList<HistorialDAO> listaHistorialDAO = new Select().from(HistorialDAO.class).orderBy("Anio DESC , Mes DESC").execute();

        for (int i = 0; i < listaHistorialDAO.size(); i++) {

            HistorialDAO historialDAO = listaHistorialDAO.get(i);
            Historial historial = new Historial();

            historial.setId(historialDAO.getId());
            historial.setAnio(historialDAO.getAnio());
            historial.setMes(historialDAO.getMes());
            historial.setTotal(historialDAO.getTotal());
            listaHistorial.add(historial);
        }
        Log.d("XPDROID", "Historial: "+listaHistorial);
        return listaHistorial;
    }

    @Override
    public Long guardarHistorial(Historial historial) {
        Log.d("XPDROID", "Guardar Historial: "+historial);
        HistorialDAO historialDAO = new HistorialDAO();
        historialDAO.setTotal(historial.getTotal());
        historialDAO.setMes(historial.getMes());
        historialDAO.setAnio(historial.getAnio());
        historialDAO.save();
        return null;
    }

    @Override
    public void eliminarHistorial(Long id) {
        Log.d("XPDROID", "Eliminar Historial pór id "+id);
        obtenerHistorialPorId(id).delete();
    }

    @Override
    public void actualizarHistorial(Historial historial) {
        Log.d("XPDROID", "Actualizar Historial: "+historial);
        HistorialDAO historialDAO = obtenerHistorialPorId(historial.getId());
        if (historialDAO == null) {
            guardarHistorial(historial);
        } else {
            historialDAO.setTotal(historial.getTotal());
            historialDAO.save();
        }
    }

    public Historial obtenerHistorial(int mes, int anio) {
        Log.d("XPDROID", "Obtener Historial por mes "+mes+ "y anio "+anio);

        ArrayList<HistorialDAO> listaHistorialDAO = new Select().from(HistorialDAO.class).where("Mes = ? AND Anio = ?", mes, anio).execute();

        Historial historial = null;

        if (listaHistorialDAO != null && !listaHistorialDAO.isEmpty()) {

            historial = new Historial();
            historial.setId(listaHistorialDAO.get(0).getId());
            historial.setAnio(listaHistorialDAO.get(0).getAnio());
            historial.setMes(listaHistorialDAO.get(0).getMes());
            historial.setTotal(listaHistorialDAO.get(0).getTotal());
            Log.d("XPDROID", historial.toString());

        }
        return historial;
    }

    private HistorialDAO obtenerHistorialPorId(Long id) {
        Log.d("XPDROID", "Obtener Historial por id "+id);

        ArrayList<HistorialDAO> listaHistorialDAO = new Select().from(HistorialDAO.class).where("Id = ?", id).execute();
        if (listaHistorialDAO!=null&&!listaHistorialDAO.isEmpty()){
            HistorialDAO historialDAO = listaHistorialDAO.get(0);
            Log.d("XPDROID", historialDAO.toString());
            return historialDAO;
        }
        Log.d("XPDROID", "Historial no encontrado");
        return null;
    }
}
