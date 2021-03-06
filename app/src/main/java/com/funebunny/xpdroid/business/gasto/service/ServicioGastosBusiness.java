package com.funebunny.xpdroid.business.gasto.service;

import android.content.Context;
import android.util.Log;

import com.funebunny.xpdroid.backend.gasto.service.ServicioGastosDAO;
import com.funebunny.xpdroid.business.gasto.model.Gasto;
import com.funebunny.xpdroid.business.gasto.model.GastoFavorito;
import com.funebunny.xpdroid.business.gasto.model.GastoProgDiario;
import com.funebunny.xpdroid.business.gasto.model.GastoProgSemanal;
import com.funebunny.xpdroid.business.gasto.model.GastoProgramable;
import com.funebunny.xpdroid.business.notificacion.service.ServicioNotificacionesBusiness;
import com.funebunny.xpdroid.utilities.AppConstants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by schmidt0 on 7/25/2015.
 */
public class ServicioGastosBusiness implements IServicioGastosBusiness {

    private static final String SEMANAL = "Semanal";
    private static final String DIARIO = "Diario";

    ServicioNotificacionesBusiness notificationsService = new ServicioNotificacionesBusiness();
    ServicioGastosDAO servicioGastosDAO = new ServicioGastosDAO();

    @Override
    public List<GastoProgramable> obtenerGastosProgramables() {
        Log.d("XPDROID", "Obteniendo Gastos Programables");
        return servicioGastosDAO.obtenerGastosProgramables();
    }

    public GastoProgramable guardarGastoProgramable(Context applicationContext, String descripcion, String repeticion, String horario, String importe, String categoria, String diaSemana) {
        Log.d("XPDROID", "Guardando Gastos Programables");
        GastoProgramable gp;

        if (SEMANAL.equalsIgnoreCase(repeticion)) {
            gp = new GastoProgSemanal();
            ((GastoProgSemanal) gp).setDiaSemana(getDiaSemana(diaSemana));
        } else {
            gp = new GastoProgDiario();
        }

        gp.setHora(horario);
        gp.setImporte(importe);
        gp.setDescripcion(descripcion);
        gp.setCategoria(categoria);
        gp.setId(servicioGastosDAO.guardarGastoProgramable(gp));

        if (SEMANAL.equalsIgnoreCase(repeticion)) {
            notificationsService.activarAlarmaSemanal(applicationContext, getDiaSemana(diaSemana), gp.getHora(), gp.getId());
        } else {
            notificationsService.activarAlarmaDiaria(applicationContext, gp.getHora(), gp.getId());
        }

        return gp;
    }

    public void eliminarGastoProgramable(Context applicationContext, Long id) {
        Log.d("XPDROID", "Eliminando Gastos Programables");
        servicioGastosDAO.eliminarGastoProgramable(id);
        notificationsService.desactivarAlarma(applicationContext, id);
    }

    public int getDiaSemana(String diaSemana) {
        Log.d("XPDROID", "Obtener dia semana: " + diaSemana);
        int dia = 0;

        switch (diaSemana) {
            case "Lunes":
                dia = Calendar.MONDAY;
                break;
            case "Martes":
                dia = Calendar.TUESDAY;
                break;
            case "Miércoles":
                dia = Calendar.WEDNESDAY;
                break;
            case "Jueves":
                dia = Calendar.THURSDAY;
                break;
            case "Viernes":
                dia = Calendar.FRIDAY;
                break;
            case "Sábado":
                dia = Calendar.SATURDAY;
                break;
            default:
                dia = Calendar.SUNDAY;
                break;
        }
        return dia;
    }

    public String getDiaSemana(int diaSemana) {
        Log.d("XPDROID", "Obtener dia semana: " + diaSemana);
        String dia = "";
        switch (diaSemana) {
            case Calendar.MONDAY:
                dia = "Lunes";
                break;
            case Calendar.TUESDAY:
                dia = "Martes";
                break;
            case Calendar.WEDNESDAY:
                dia = "Miércoles";
                break;
            case Calendar.THURSDAY:
                dia = "Jueves";
                break;
            case Calendar.FRIDAY:
                dia = "Viernes";
                break;
            case Calendar.SATURDAY:
                dia = "Sábado";
                break;
            default:
                dia = "Domingo";
                break;
        }
        return dia;
    }

    public void actualizarGastoProgramable(Context applicationContext, GastoProgramable gastoProgramable) {
        Log.d("XPDROID", "Actualizando Gasto Programabl: " + gastoProgramable);
        notificationsService.actualizarAlarma(applicationContext, gastoProgramable);
        servicioGastosDAO.actualizarGastoProgramable(gastoProgramable);

    }

    // Gastos Favoritos
    public GastoFavorito guardarGastoFavorito(String descripcion, String importe, String categoria) {
        Log.d("XPDROID", "Guardar Gasto Favorito");
        GastoFavorito gastoFavorito = new GastoFavorito();
        gastoFavorito.setDescripcion(descripcion);
        gastoFavorito.setImporte(importe);
        gastoFavorito.setCategoria(categoria);
        gastoFavorito.setId(servicioGastosDAO.guardarGastoFavorito(gastoFavorito));
        return gastoFavorito;
    }

    public List<GastoFavorito> obtenerGastosFavoritos() {
        return servicioGastosDAO.obtenerGastosFavoritos();
    }

    public void eliminarGastoFavorito(Long id) {
        servicioGastosDAO.eliminarGastoFavorito(id);
    }

    public void actualizarGastoFavorito(GastoFavorito gf) {
        servicioGastosDAO.actualizarGastoFavorito(gf);
    }

    // Gastos
    public Gasto guardarGasto(String descripcion, String importe, String categoria, String fecha) {

        Gasto gasto = new Gasto();
        gasto.setCategoria(categoria);
        gasto.setFecha(fecha);
        gasto.setImporte(importe);
        gasto.setDescripcion(descripcion);
        gasto.setId(servicioGastosDAO.guardarGasto(gasto));
        return gasto;
    }

    public void actualizarGasto(Gasto gasto) {
        servicioGastosDAO.actualizarGasto(gasto);
    }

    public void eliminarGasto(Long id) {
        servicioGastosDAO.eliminarGasto(id);
    }

    public List<Gasto> obtenerGastos() {
        return servicioGastosDAO.obtenerGastos();
    }

    public List<Gasto> obtenerGastosPorCategoria(String categoria) {
        return servicioGastosDAO.obtenerGastosPorCategoria(categoria);
    }

    public List<Gasto> obtenerGastosMes(Calendar fecha) {
        Log.d("XPDROID", "Obtener Gasto Mes:" + fecha.getTime().toString());
        SimpleDateFormat formatoFecha = new SimpleDateFormat(AppConstants.ANIO_MES);
        String mesanio = formatoFecha.format(fecha.getTime());
        return servicioGastosDAO.obtenerGastosFechaLike(mesanio);

    }

    public List<Gasto> obtenerGastosSemana(Calendar fecha) {
        Log.d("XPDROID", "Obtener Gasto Semana:" + fecha.getTime().toString());
        SimpleDateFormat formatoFecha = new SimpleDateFormat(AppConstants.FECHA_DB);
        Date fechaDesde = fecha.getTime();
        Calendar calHasta = Calendar.getInstance();
        calHasta.setTime(fechaDesde);
        calHasta.add(Calendar.DAY_OF_YEAR, 6);
        String desde = formatoFecha.format(fechaDesde);
        String hasta = formatoFecha.format(calHasta.getTime());
        return servicioGastosDAO.obtenerGastosDesdeHasta(desde, hasta);

    }

    @Override
    public List<Gasto> obtenerGastosAnio(Calendar fecha) {
        Log.d("XPDROID", "Obtener Gasto Anio:" + fecha.getTime().toString());
        SimpleDateFormat formatoFecha = new SimpleDateFormat(AppConstants.ANIO);
        String anio = formatoFecha.format(fecha.getTime());
        return servicioGastosDAO.obtenerGastosFechaLike(anio);
    }

    @Override
    public List<Gasto> obtenerGastosDia(Calendar fecha) {
        Log.d("XPDROID", "Obtener Gasto Dia:" + fecha.getTime().toString());
        SimpleDateFormat formatoFecha = new SimpleDateFormat(AppConstants.FECHA_DB);
        String dia = formatoFecha.format(fecha.getTime());
        return servicioGastosDAO.obtenerGastosFechaLike(dia);
    }

}
