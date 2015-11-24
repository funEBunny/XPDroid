package com.funebunny.xpdroid.business.notificacion.service;

import android.test.AndroidTestCase;

import java.util.Calendar;

/**
 * Created by provirabosch on 17/10/2015.
 */
public class ServicioNotificacionesBusinessTest extends AndroidTestCase {
    ServicioNotificacionesBusiness notificacionesBusiness;

    public ServicioNotificacionesBusinessTest() {
        this.notificacionesBusiness = new ServicioNotificacionesBusiness();
    }

    public void testNotificacion(){
        int hora = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minutos = Calendar.getInstance().get(Calendar.MINUTE);
        notificacionesBusiness.activarAlarmaDiaria(getContext(),hora+":"+minutos,1L);
        notificacionesBusiness.activarAlarmaSemanal(getContext(), 1, hora+":"+minutos,2L);
    }
}
