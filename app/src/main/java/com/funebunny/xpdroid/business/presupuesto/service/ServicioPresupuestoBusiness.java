package com.funebunny.xpdroid.business.presupuesto.service;

import com.funebunny.xpdroid.backend.presupuesto.dao.TotalesDAO;
import com.funebunny.xpdroid.backend.presupuesto.service.ServicioPresupuestoDAO;
import com.funebunny.xpdroid.business.gasto.model.Gasto;
import com.funebunny.xpdroid.business.gasto.service.IServicioGastosBusiness;
import com.funebunny.xpdroid.business.gasto.service.ServicioGastosBusiness;
import com.funebunny.xpdroid.business.presupuesto.model.Presupuesto;
import com.funebunny.xpdroid.utilities.AppConstants;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by provirabosch on 22/08/2015.
 */
public class ServicioPresupuestoBusiness implements IServicioPresupuestoBusiness {

    ServicioPresupuestoDAO servicioPresupuestoDAO = new ServicioPresupuestoDAO();
    ServicioGastosBusiness servicioGastosBusiness = new ServicioGastosBusiness();

    public void guardarPresupuesto(String periodo, String importe) {

        Presupuesto presupuesto = new Presupuesto();
        presupuesto.setPeriodo(periodo);
        presupuesto.setImporte(importe);
        servicioPresupuestoDAO.guardarPresupuesto(presupuesto);

    }

    public List<Presupuesto> obtenerPresupuesto() {
        return servicioPresupuestoDAO.obtenerPresupuesto();
    }

    String obtenerPresupuestoDiario() {
        Presupuesto presupuesto = servicioPresupuestoDAO.obtenerPresupuestoPorPeriodo(AppConstants.PERIODO_DIARIO);
        return presupuesto == null ? "" : presupuesto.getImporte();
    }

    String obtenerPresupuestoSemanal() {
        Presupuesto presupuesto = servicioPresupuestoDAO.obtenerPresupuestoPorPeriodo(AppConstants.PERIODO_SEMANAL);
        return presupuesto == null ? "" : presupuesto.getImporte();
    }

    String obtenerPresupuestoMensual() {
        Presupuesto presupuesto = servicioPresupuestoDAO.obtenerPresupuestoPorPeriodo(AppConstants.PERIODO_MENSUAL);
        return presupuesto == null ? "" : presupuesto.getImporte();
    }

    String obtenerPresupuestoAnual() {
        Presupuesto presupuesto = servicioPresupuestoDAO.obtenerPresupuestoPorPeriodo(AppConstants.PERIODO_ANUAL);
        return presupuesto == null ? "" : presupuesto.getImporte();
    }

    public void eliminarPresupuesto(Long id) {
        servicioPresupuestoDAO.eliminarPresupuesto(id);
    }

    public void actualizarPresupuesto(Presupuesto obj) {
        servicioPresupuestoDAO.actualizarPresupuesto(obj);
    }

    @Override
    public boolean isLimitePresupuestoAlcanzado() {
        return (servicioPresupuestoDAO.obtenerPresupuesto().size() == AppConstants.CANT_MAX_PRESUPUESTO);
    }

    @Override
    public boolean tipoPresupuestoExiste(String periodo) {
        return servicioPresupuestoDAO.obtenerPresupuestoPorPeriodo(periodo) != null;
    }

    boolean isNuevaSemana() {
        Calendar calendar = Calendar.getInstance();
        int semanaActual = calendar.get(Calendar.WEEK_OF_YEAR);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        int semanaAyer = calendar.get(Calendar.WEEK_OF_YEAR);
        return semanaActual != semanaAyer;
    }

    boolean isNuevoMes() {
        Calendar calendar = Calendar.getInstance();
        int mesActual = calendar.get(Calendar.MONTH);
        calendar.add(Calendar.MONTH, -1);
        int mesAyer = calendar.get(Calendar.MONTH);
        return mesActual != mesActual;
    }

    boolean isNuevoAnio() {
        Calendar calendar = Calendar.getInstance();
        int anioActual = calendar.get(Calendar.YEAR);
        calendar.add(Calendar.YEAR, -1);
        int anioAyer = calendar.get(Calendar.YEAR);
        return anioActual != anioAyer;
    }

    boolean isMismaSemana(Calendar fechaGasto) {

        Calendar hoy = Calendar.getInstance();
        int semanaActual = hoy.get(Calendar.WEEK_OF_YEAR);
        int semanaGasto = fechaGasto.get(Calendar.WEEK_OF_YEAR);
        return semanaActual == semanaGasto;
    }

    boolean isMismoDia(Calendar fechaGasto) {

        Calendar hoy = Calendar.getInstance();
        int diaHoy = hoy.get(Calendar.DAY_OF_YEAR);
        int diaGasto = fechaGasto.get(Calendar.DAY_OF_YEAR);
        return diaHoy == diaGasto;
    }

    boolean isMismoMes(Calendar fechaGasto) {

        Calendar hoy = Calendar.getInstance();
        int mesActual = hoy.get(Calendar.MONTH);
        int mesGasto = fechaGasto.get(Calendar.MONTH);
        return mesActual == mesGasto;
    }

    boolean isMismoAnio(Calendar fechaGasto) {

        Calendar hoy = Calendar.getInstance();
        int anioActual = hoy.get(Calendar.YEAR);
        int anioGasto = fechaGasto.get(Calendar.YEAR);
        return anioActual == anioGasto;
    }

    boolean isPresupuestoDiarioAlcanzado() {
        String presupuesto = obtenerPresupuestoDiario();
        boolean alcanzado = false;
        if (!presupuesto.isEmpty()) {
            TotalesDAO totalesDAO = servicioPresupuestoDAO.obtenerTotales();
            if (totalesDAO != null) {
                String total = totalesDAO.getTotalDiario();
                alcanzado = isAlcanzado(presupuesto, total);
            }
        }
        return alcanzado;
    }

    private boolean isAlcanzado(String presupuesto, String total) {
        boolean alcanzado;
        BigDecimal bdTotal = new BigDecimal(total);
        BigDecimal bdPresupuesto = new BigDecimal(presupuesto);
        int i = bdTotal.compareTo(bdPresupuesto);
        alcanzado = i != -1;
        return alcanzado;
    }

    @Override
    public void calcularTotales() {
        TotalesDAO totalesDAO = servicioPresupuestoDAO.obtenerTotales();

        totalesDAO.setTotalDiario("0");

        if (isNuevaSemana()) {
            String totalSemana="0";
            totalesDAO.setTotalSemanal("0");
            List<Gasto> gastos = servicioGastosBusiness.obtenerGastosMismaSemana(Calendar.getInstance());
            for (Gasto gasto : gastos) {
                String importeGasto = gasto.getImporte();
               totalSemana = sumar(totalSemana, importeGasto);
            }
        }
        if (isNuevoMes()){
            totalesDAO.setTotalMensual("0");
        }

        if (isNuevoAnio()){
            totalesDAO.setTotalAnual("0");
        }

        String totalDiario = totalesDAO.getTotalDiario();



    }

    @Override
    public void calcularTotales(Gasto gasto) {

        TotalesDAO totalesDAO = servicioPresupuestoDAO.obtenerTotales();

        if (totalesDAO == null) {
            servicioPresupuestoDAO.inicializarTotales();
        }

        if (isMismoDia(obtenerCalendar(gasto.getFecha()))) {
            String totalDiario = sumar(totalesDAO.getTotalDiario(), gasto.getImporte());
            servicioPresupuestoDAO.guardarTotalDiario(totalDiario);
        }

        if (isMismaSemana(obtenerCalendar(gasto.getFecha()))) {
            String totalSemanal = sumar(totalesDAO.getTotalSemanal(), gasto.getImporte());
            servicioPresupuestoDAO.guardarTotalSemanal(totalSemanal);
        }

        if (isMismoMes(obtenerCalendar(gasto.getFecha()))) {
            String totalMensual = sumar(totalesDAO.getTotalMensual(), gasto.getImporte());
            servicioPresupuestoDAO.guardarTotalMensual(totalMensual);
        }

        if (isMismoAnio(obtenerCalendar(gasto.getFecha()))) {
            String totalAnual = sumar(totalesDAO.getTotalAnual(), gasto.getImporte());
            servicioPresupuestoDAO.guardarTotalAnual(totalAnual);
        }

    }

    private String sumar(String total, String importe) {
        BigDecimal bdTotalSemanal = new BigDecimal(total);
        BigDecimal bdImporteGasto = new BigDecimal(importe);
        bdTotalSemanal = bdTotalSemanal.add(bdImporteGasto);
        return bdTotalSemanal.toPlainString();
    }

    private Calendar obtenerCalendar(String fecha) {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date dia = formatoFecha.parse(fecha);
            calendar.setTime(dia);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return calendar;
    }
}
