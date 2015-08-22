package com.funebunny.xpdroid.gastos.backend.service;

import com.activeandroid.query.Select;
import com.funebunny.xpdroid.gastos.backend.dao.ObjetivoDAO;
import com.funebunny.xpdroid.gastos.business.model.Objetivo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by provirabosch on 22/08/2015.
 */
public class ServicioObjetivosDAO implements IServicioObjetivosDAO {

    @Override
    public Long guardarObjetivo(Objetivo obj) {

        ObjetivoDAO objDAO = new ObjetivoDAO();
        objDAO.setPeriodo(obj.getPeriodo());
        objDAO.setImporte(obj.getImporte());
        objDAO.save();
        return objDAO.getId();
    }

    @Override
    public void eliminarObjetivo(Long id) {
        obtenerObjetivoPorId(id).delete();
    }

    @Override
    public void actualizarObjetivo(Objetivo obj) {

        ObjetivoDAO objDAO = obtenerObjetivoPorId(obj.getId());
        objDAO.setPeriodo(obj.getPeriodo());
        objDAO.setImporte(obj.getImporte());
        objDAO.save();
    }

    @Override
    public List<Objetivo> obtenerObjetivos() {

        List<Objetivo> objetivos = new ArrayList<>();
        ArrayList<ObjetivoDAO> objetivosDAO = new Select().from(ObjetivoDAO.class).execute();

        for (int i = 0; i < objetivosDAO.size(); i++) {

            ObjetivoDAO objDAO = objetivosDAO.get(i);
            Objetivo obj = new Objetivo();

            obj.setId(objDAO.getId());
            obj.setPeriodo(objDAO.getPeriodo());
            obj.setImporte(objDAO.getImporte());

            objetivos.add(obj);
        }

        return objetivos;
    }


    public Objetivo obtenerObjetivoPorPeriodo(String periodo) {

        Objetivo objetivo = null;

        ArrayList<ObjetivoDAO> objetivosDAO = new Select().from(ObjetivoDAO.class).where("Periodo = ?", periodo).execute();
        ObjetivoDAO objDAO = objetivosDAO.get(0);

        if (objDAO != null) {
            objetivo = new Objetivo();
            objetivo.setId(objDAO.getId());
            objetivo.setPeriodo(objDAO.getPeriodo());
            objetivo.setImporte(objDAO.getImporte());
        }
        return objetivo;
    }

    private ObjetivoDAO obtenerObjetivoPorId(Long id) {
        ArrayList<ObjetivoDAO> objetivosLista = new Select().from(ObjetivoDAO.class).where("Id = ?", id).execute();
        return objetivosLista.get(0);
    }
}
