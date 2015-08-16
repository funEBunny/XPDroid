package com.funebunny.xpdroid.gastos.business.service;

import android.content.Context;

import com.funebunny.xpdroid.gastos.backend.service.ServicioGastosDAO;
import com.funebunny.xpdroid.gastos.business.model.GastoFavorito;
import com.funebunny.xpdroid.gastos.business.model.GastoProgDiario;
import com.funebunny.xpdroid.gastos.business.model.GastoProgSemanal;
import com.funebunny.xpdroid.gastos.business.model.GastoProgramable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public void guardarGastoProgramable(Context applicationContext, String descripcion, String repeticion, String horario, String importe, String categoria, String diaSemana){

        SimpleDateFormat fromUser = new SimpleDateFormat("HH:mm");
        SimpleDateFormat myFormat = new SimpleDateFormat("HHmm");
        String horaFormateada="0";
        try {
            horaFormateada = myFormat.format(fromUser.parse(horario));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        GastoProgramable gp;

        if (SEMANAL.equalsIgnoreCase(repeticion)){
            gp = new GastoProgSemanal();
            ((GastoProgSemanal)gp).setDiaSemana(getDiaSemana(diaSemana));
        }else{
            gp = new GastoProgDiario();
        }

        gp.setHora(Integer.parseInt(horaFormateada));
        gp.setImporte(importe);
        gp.setDescripcion(descripcion);
        gp.setCategoria(categoria);

        Long id = servicioGastosDAO.guardarGastoProgramable(gp);

        String formatTime = null;
        try {
            formatTime = fromUser.format(fromUser.parse(horario));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (SEMANAL.equalsIgnoreCase(repeticion)){
            notificationsService.activarAlarmaSemanal(applicationContext, getDiaSemana(diaSemana), formatTime, id);
        }else{
            notificationsService.activarAlarmaDiaria(applicationContext, formatTime, id);
        }
    }

    private int getDiaSemana(String diaSemana){
        int dia=0;
        switch (diaSemana){
            case "Lunes":dia= Calendar.MONDAY;break;
            case "Martes":dia=  Calendar.TUESDAY;break;
            case "Miercoles":dia=  Calendar.WEDNESDAY;break;
            case "Jueves":dia=  Calendar.THURSDAY;break;
            case "Viernes":dia=  Calendar.FRIDAY;break;
            case "Sabado":dia=  Calendar.SATURDAY;break;
            default:dia=  Calendar.SATURDAY;break;
        }
        return dia;
    }

    public void guardarGastoFavorito(String descripcion, String importe, String categoria){

        GastoFavorito gastoFavorito = new GastoFavorito();
        gastoFavorito.setDescripcion(descripcion);
        gastoFavorito.setImporte(importe);
        gastoFavorito.setCategoria(categoria);
        servicioGastosDAO.guardarGastoFavorito(gastoFavorito);

    }

    public List<GastoFavorito> obtenerGastosFavoritos(){
        return servicioGastosDAO.obtenerGastosFavoritos();
    }

    public void eliminarGastoFavorito(Long id){
        servicioGastosDAO.eliminarGastoFavorito(id);
    }

    public void actualizarGastoFavorito(GastoFavorito gf){
        servicioGastosDAO.actualizarGastoFavorito(gf);
    }

    public void eliminarGastoProgramable(Context applicationContext, Long id){

        servicioGastosDAO.eliminarGastoProgramable(id);
        notificationsService.desactivarAlarma(applicationContext, id);
    }

}
