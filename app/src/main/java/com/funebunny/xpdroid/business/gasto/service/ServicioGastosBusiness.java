package com.funebunny.xpdroid.business.gasto.service;

import android.content.Context;

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
        return  servicioGastosDAO.obtenerGastosProgramables();
    }

    public void guardarGastoProgramable(Context applicationContext, String descripcion, String repeticion, String horario, String importe, String categoria, String diaSemana) {

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

        Long id = servicioGastosDAO.guardarGastoProgramable(gp);


        if (SEMANAL.equalsIgnoreCase(repeticion)) {
            notificationsService.activarAlarmaSemanal(applicationContext, getDiaSemana(diaSemana), gp.getHora(), id);
        } else {
            notificationsService.activarAlarmaDiaria(applicationContext, gp.getHora(), id);
        }
    }

    public void eliminarGastoProgramable(Context applicationContext, Long id) {

        servicioGastosDAO.eliminarGastoProgramable(id);
        notificationsService.desactivarAlarma(applicationContext, id);
    }

    public int getDiaSemana(String diaSemana) {
        int dia = 0;
        switch (diaSemana) {
            case "Lunes":
                dia = Calendar.MONDAY;
                break;
            case "Martes":
                dia = Calendar.TUESDAY;
                break;
            case "Miercoles":
                dia = Calendar.WEDNESDAY;
                break;
            case "Jueves":
                dia = Calendar.THURSDAY;
                break;
            case "Viernes":
                dia = Calendar.FRIDAY;
                break;
            case "Sabado":
                dia = Calendar.SATURDAY;
                break;
            default:
                dia = Calendar.SATURDAY;
                break;
        }
        return dia;
    }

    public String getDiaSemana(int diaSemana) {
        String dia = "";
        switch (diaSemana) {
            case Calendar.MONDAY:
                dia = "Lunes";
                break;
            case  Calendar.TUESDAY :
                dia ="Martes";
                break;
            case Calendar.WEDNESDAY:
                dia = "Miercoles";
                break;
            case Calendar.THURSDAY:
                dia = "Jueves";
                break;
            case Calendar.FRIDAY:
                dia = "Viernes";
                break;
            case Calendar.SATURDAY:
                dia ="Sabado" ;
                break;
            default:
                dia = "Domingo";
                break;
        }
        return dia;
    }

    public void actualizarGastoProgramable(Context applicationContext, GastoProgramable gastoProgramable) {
        notificationsService.actualizarAlarma(applicationContext,gastoProgramable);
        servicioGastosDAO.actualizarGastoProgramable(gastoProgramable);

    }
    // Gastos Favoritos
    public void guardarGastoFavorito(String descripcion, String importe, String categoria) {

        GastoFavorito gastoFavorito = new GastoFavorito();
        gastoFavorito.setDescripcion(descripcion);
        gastoFavorito.setImporte(importe);
        gastoFavorito.setCategoria(categoria);
        servicioGastosDAO.guardarGastoFavorito(gastoFavorito);

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
    public Gasto guardarGasto(String descripcion, String importe, String categoria, String fecha){

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

    public List<Gasto> obtenerGastosPorCategoria(String categoria){
        return servicioGastosDAO.obtenerGastosPorCategoria(categoria);
    }

    public List<Gasto> obtenerGastosMes(Calendar fecha) {

/*        String mes = String.valueOf(fecha.get(Calendar.MONTH) + 1);
        String anio = String.valueOf(fecha.get(Calendar.YEAR));

        return servicioGastosDAO.obtenerGastosMes(mes, anio);*/
        SimpleDateFormat formatoFecha = new SimpleDateFormat(AppConstants.ANIO_MES);
        String mesanio = formatoFecha.format(fecha.getTime());
        return  servicioGastosDAO.obtenerGastosFechaLike(mesanio);

    }

    public List<Gasto> obtenerGastosSemana(Calendar fecha) {
        SimpleDateFormat formatoFecha = new SimpleDateFormat(AppConstants.FECHA_DB);
        Date fechaDesde = fecha.getTime();
        Calendar calHasta = Calendar.getInstance();
        calHasta.setTime(fechaDesde);
        calHasta.add(Calendar.DAY_OF_YEAR, 6);
        String desde = formatoFecha.format(fechaDesde);
        String hasta = formatoFecha.format(calHasta.getTime());
        return servicioGastosDAO.obtenerGastosDesdeHasta(desde,hasta);

    }

    @Override
    public List<Gasto> obtenerGastosAnio(Calendar fecha) {
        SimpleDateFormat formatoFecha = new SimpleDateFormat(AppConstants.ANIO);
        String anio = formatoFecha.format(fecha.getTime());
        return  servicioGastosDAO.obtenerGastosFechaLike(anio);
    }

}
