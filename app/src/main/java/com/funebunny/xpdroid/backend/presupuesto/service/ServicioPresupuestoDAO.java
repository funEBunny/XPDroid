package com.funebunny.xpdroid.backend.presupuesto.service;

import android.util.Log;

import com.activeandroid.query.Select;
import com.funebunny.xpdroid.backend.presupuesto.dao.PresupuestoDAO;
import com.funebunny.xpdroid.backend.presupuesto.dao.TotalesDAO;
import com.funebunny.xpdroid.business.presupuesto.model.Presupuesto;
import com.funebunny.xpdroid.utilities.AppConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by provirabosch on 22/08/2015.
 */
public class ServicioPresupuestoDAO implements IServicioPresupuestoDAO {

    @Override
    public Long guardarPresupuesto(Presupuesto obj) {
        Log.d("XPDROID", "Guardando Presupuesto: "+obj);
        PresupuestoDAO objDAO = new PresupuestoDAO();
        objDAO.setPeriodo(obj.getPeriodo());
        objDAO.setImporte(obj.getImporte());
        objDAO.save();
        return objDAO.getId();
    }

    @Override
    public void eliminarPresupuesto(Long id) {
        Log.d("XPDROID", "Eliminando Presupuesto: "+id);
        obtenerPresupuestoPorId(id).delete();
    }

    @Override
    public void actualizarPresupuesto(Presupuesto obj) {
        Log.d("XPDROID", "Actualizando Presupuesto: "+obj);
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
        Log.d("XPDROID", "Obteniendo Presupuestos: "+presupuesto);
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
        Log.d("XPDROID", "Obteniendo Presupuesto por periodo "+periodo+" : " + presupuesto);
        return presupuesto;
    }

    private PresupuestoDAO obtenerPresupuestoPorId(Long id) {
        ArrayList<PresupuestoDAO> presupuestoLista = new Select().from(PresupuestoDAO.class).where("Id = ?", id).execute();
        PresupuestoDAO presup = presupuestoLista.get(0);
        Log.d("XPDROID", "Obteniendo Presupuesto por ID "+id+" : " + presup);
        return presup;
    }

    @Override
    public TotalesDAO obtenerTotales() {

        TotalesDAO totales;

        ArrayList<TotalesDAO> totalesList = new Select().from(TotalesDAO.class).execute();

        if (!totalesList.isEmpty()) {
            totales = totalesList.get(0);
        } else {
            totales = inicializarTotales();
        }
        Log.d("XPDROID", "Totales: " + totales);
        return totales;
    }

    @Override
    public void guardarTotalDiario(String total) {
        Log.d("XPDROID", "Guardanto Total Diario: " + total);
        TotalesDAO totales = obtenerTotales();
        totales.setTotalDiario(total);
        totales.save();
    }

    @Override
    public void guardarTotalSemanal(String total) {
        Log.d("XPDROID", "Guardanto Total Semanal: " + total);
        TotalesDAO totales = obtenerTotales();
        totales.setTotalSemanal(total);
        totales.save();
    }

    @Override
    public void guardarTotalMensual(String total) {
        Log.d("XPDROID", "Guardanto Total Mensual: " + total);
        TotalesDAO totales = obtenerTotales();
        totales.setTotalMensual(total);
        totales.save();
    }

    @Override
    public void guardarTotalAnual(String total) {
        Log.d("XPDROID", "Guardanto Total Anual: " + total);
        TotalesDAO totales = obtenerTotales();
        totales.setTotalAnual(total);
        totales.save();
    }

    @Override
    public void guardarTotalPredeterminado(String predeterminado) {
        Log.d("XPDROID", "Guardanto Predeterminado: " + predeterminado);
        TotalesDAO totales = obtenerTotales();
        totales.setPredeterminado(predeterminado);
        totales.save();
    }

    private TotalesDAO inicializarTotales() {
        Log.d("XPDROID", "Inicializando Totales");
        TotalesDAO totalesDAO = new TotalesDAO();
        totalesDAO.setTotalDiario("0");
        totalesDAO.setTotalMensual("0");
        totalesDAO.setTotalSemanal("0");
        totalesDAO.setTotalAnual("0");
        totalesDAO.setPredeterminado(AppConstants.PERIODO_MENSUAL);
        totalesDAO.save();
        Log.d("XPDROID", "Totales inicializados" +totalesDAO);
        return totalesDAO;
    }

}
