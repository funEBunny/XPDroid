package com.funebunny.xpdroid.gastos.business.service;

import android.content.Context;

import com.funebunny.xpdroid.gastos.backend.service.ServicioGastosDAO;
import com.funebunny.xpdroid.gastos.business.model.GastoFavorito;
import com.funebunny.xpdroid.gastos.business.model.GastoProgDiario;
import com.funebunny.xpdroid.gastos.business.model.GastoProgSemanal;
import com.funebunny.xpdroid.gastos.business.model.GastoProgramable;
import com.funebunny.xpdroid.gastos.business.model.Objetivo;

import java.util.Calendar;
import java.util.List;

/**
 * Created by schmidt0 on 7/25/2015.
 */
public class ServicioGastosBusiness {

    private static final String SEMANAL = "Semanal";
    private static final String DIARIO = "Diario";

    ServicioNotificacionesBusiness notificationsService = new ServicioNotificacionesBusiness();
    ServicioGastosDAO servicioGastosDAO = new ServicioGastosDAO();

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

    public void actualizarGastoProgramable(Context applicationContext, GastoProgramable gastoProgramable) {
        notificationsService.actualizarAlarma(applicationContext,gastoProgramable);
        servicioGastosDAO.actualizarGastoProgramable(gastoProgramable);

    }

    // Gastos
    public void guardarGasto(String descripcion, String importe, String categoria, String fecha){
        servicioGastosDAO.guardarGasto(descripcion, importe, categoria, fecha);
    }
}
