package com.funebunny.xpdroid.gastos.business;

import android.content.Context;

import com.funebunny.xpdroid.gastos.backend.ServicioGastos;
import com.funebunny.xpdroid.gastos.model.GastoProgDiario;
import com.funebunny.xpdroid.gastos.model.GastoProgSemanal;
import com.funebunny.xpdroid.gastos.model.GastoProgramable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by schmidt0 on 7/25/2015.
 */
public class GastosService {

    private static final String SEMANAL = "Semanal";
    private static final String DIARIO = "Diario";

    NotificationsService notificationsService = new NotificationsService();

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
        ServicioGastos servicioGastos = new ServicioGastos();
        Long id = servicioGastos.guardarGastoProgramable(gp);

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
}
