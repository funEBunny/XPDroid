package com.funebunny.xpdroid.backend.presupuesto.service;

import com.activeandroid.query.Select;
import com.funebunny.xpdroid.backend.presupuesto.dao.PresupuestoDAO;
import com.funebunny.xpdroid.business.presupuesto.model.Presupuesto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by provirabosch on 22/08/2015.
 */
public class ServicioPresupuestoDAO implements IServicioPresupuestoDAO {

    @Override
    public Long guardarPresupuesto(Presupuesto obj) {

        PresupuestoDAO objDAO = new PresupuestoDAO();
        objDAO.setPeriodo(obj.getPeriodo());
        objDAO.setImporte(obj.getImporte());
        objDAO.save();
        return objDAO.getId();
    }

    @Override
    public void eliminarPresupuesto(Long id) {
        obtenerPresupuestoPorId(id).delete();
    }

    @Override
    public void actualizarPresupuesto(Presupuesto obj) {

        PresupuestoDAO objDAO = obtenerPresupuestoPorId(obj.getId());
        objDAO.setPeriodo(obj.getPeriodo());
        objDAO.setImporte(obj.getImporte());
        objDAO.save();
    }

    @Override
    public List<Presupuesto> obtenerPresupuesto() {

        List<Presupuesto> presupuesto = new ArrayList<>();
        ArrayList<PresupuestoDAO> presupuestoDAO = new Select().from(PresupuestoDAO.class).execute();

        for (int i = 0; i < presupuestoDAO.size(); i++) {

            PresupuestoDAO objDAO = presupuestoDAO.get(i);
            Presupuesto obj = new Presupuesto();

            obj.setId(objDAO.getId());
            obj.setPeriodo(objDAO.getPeriodo());
            obj.setImporte(objDAO.getImporte());

            presupuesto.add(obj);
        }

        return presupuesto;
    }


    public Presupuesto obtenerPresupuestoPorPeriodo(String periodo) {

        Presupuesto presupuesto = null;

        ArrayList<PresupuestoDAO> presupuestoDAO = new Select().from(PresupuestoDAO.class).where("Periodo = ?", periodo).execute();

        if (!presupuestoDAO.isEmpty()) {

            PresupuestoDAO objDAO = presupuestoDAO.get(0);
            presupuesto = new Presupuesto();
            presupuesto.setId(objDAO.getId());
            presupuesto.setPeriodo(objDAO.getPeriodo());
            presupuesto.setImporte(objDAO.getImporte());

        }
        return presupuesto;
    }

    private PresupuestoDAO obtenerPresupuestoPorId(Long id) {
        ArrayList<PresupuestoDAO> presupuestoLista = new Select().from(PresupuestoDAO.class).where("Id = ?", id).execute();
        return presupuestoLista.get(0);
    }
}
