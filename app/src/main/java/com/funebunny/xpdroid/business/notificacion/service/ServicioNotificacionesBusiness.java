package com.funebunny.xpdroid.business.notificacion.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.funebunny.xpdroid.business.gasto.model.GastoProgSemanal;
import com.funebunny.xpdroid.business.gasto.model.GastoProgramable;
import com.funebunny.xpdroid.business.alarma.AlarmChecker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by schmidt0 on 7/25/2015.
 */
public class ServicioNotificacionesBusiness {

    public static final int ZERO_SEGUNDOS = 0;
    public static final int HORA = 0;
    public static final int MINUTOS = 1;
    public static final String SEPARADOR_HORA = ":";
    public static final String NOTIF_ID = "notifID";
    public static final long INTERVAL_WEEK = 7 * AlarmManager.INTERVAL_DAY;
    public static final long INTERVAL_DAY = AlarmManager.INTERVAL_DAY;

   public void activarAlarmaDiaria(Context applicationContext, String time, Long id) {
        Log.d("XPDROID", "Activando alarma diaria");
        Calendar calendar = getCalendar(time, 0);
        activarAlarma(applicationContext, calendar, id, INTERVAL_DAY);

    }

    public void activarAlarmaSemanal(Context applicationContext, int day, String time, Long id) {
        Log.d("XPDROID", "Activando alarma semanal");
        Calendar calendar = getCalendar(time, day);
        activarAlarma(applicationContext, calendar, id, INTERVAL_WEEK);

    }

    private void activarAlarma(Context applicationContext, Calendar calendar, Long id, long repeticion) {
        AlarmManager alarm = (AlarmManager) applicationContext.getSystemService(Context.ALARM_SERVICE);
        Intent myIntent = new Intent(applicationContext, AlarmChecker.class).putExtra(NOTIF_ID, id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(applicationContext, id.intValue(), myIntent, 0);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), repeticion, pendingIntent);
    }

    public void desactivarAlarma(Context applicationContext,Long id){
        Log.d("XPDROID", "Desactivando alarma");
        AlarmManager alarm = (AlarmManager) applicationContext.getSystemService(Context.ALARM_SERVICE);
        Intent myIntent = new Intent(applicationContext, AlarmChecker.class).putExtra(NOTIF_ID, id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(applicationContext, id.intValue(), myIntent, 0);
        alarm.cancel(pendingIntent);
    }

    private Calendar getCalendar(String time, int dia) {
        String[] splitTime = time.split(SEPARADOR_HORA);
        String hora = splitTime[HORA];
        String minutos = splitTime[MINUTOS];

        Calendar calendar = Calendar.getInstance();
        int todayDOW = calendar.get(Calendar.DAY_OF_WEEK);
        if (dia!=0){
            if (todayDOW==dia){
                if (esHoraPasada(time)){
                    calendar.add(Calendar.DAY_OF_WEEK, dia);
                }
             }else{
                calendar.add(Calendar.DAY_OF_WEEK, dia);
            }
        }else{
            if (esHoraPasada(time)){
                calendar.add(Calendar.DATE,1);
            }
        }
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hora));
        calendar.set(Calendar.MINUTE, Integer.parseInt(minutos));
        calendar.set(Calendar.SECOND, ZERO_SEGUNDOS);
        return calendar;
    }

    private boolean esHoraPasada(String time) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmm");
        Date today = Calendar.getInstance().getTime();
        String todayTime = timeFormat.format(today);
        String timeToCompare = time.replace(":", "");

        if (Integer.valueOf(todayTime)>Integer.valueOf(timeToCompare)) {
            return true;
        } else {
            return false;
        }
    }

    public void actualizarAlarma(Context applicationContext, GastoProgramable gastoProgramable) {
        Log.d("XPDROID", "Actualizando alarma");
        desactivarAlarma(applicationContext, gastoProgramable.getId());
        if (gastoProgramable instanceof GastoProgSemanal){
            activarAlarmaSemanal(applicationContext, ((GastoProgSemanal) gastoProgramable).getDiaSemana(), gastoProgramable.getHora(), gastoProgramable.getId());
        }else{
            activarAlarmaDiaria(applicationContext, gastoProgramable.getHora(), gastoProgramable.getId());
        }
    }

}
