package com.funebunny.xpdroid.business.notificacion.service;

import android.content.Context;

import com.funebunny.xpdroid.business.gasto.model.GastoProgramable;

import java.util.Calendar;

/**
 * Created by provirabosch on 29/08/2015.
 */
public interface IServicioNotificacionesBusiness {

    void activarAlarmaDiaria(Context applicationContext, String time, Long id);
    void activarAlarmaSemanal(Context applicationContext, int day, String time, Long id);
    void desactivarAlarma(Context applicationContext,Long id);
    void actualizarAlarma(Context applicationContext, GastoProgramable gastoProgramable);
}
