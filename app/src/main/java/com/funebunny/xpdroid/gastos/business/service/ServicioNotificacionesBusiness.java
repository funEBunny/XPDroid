package com.funebunny.xpdroid.gastos.business.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.funebunny.xpdroid.scheduler.AlarmChecker;

import java.text.ParseException;
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
    public static final long INTERVAL_WEEK = 7*AlarmManager.INTERVAL_DAY;
    public static final long INTERVAL_DAY = AlarmManager.INTERVAL_DAY;

    void activarAlarmaDiaria(Context applicationContext, String time, Long id){
        Calendar calendar = getCalendar(time,0);
        activarAlarma(applicationContext, calendar, id, INTERVAL_DAY);

    }

    void activarAlarmaSemanal(Context applicationContext,int day, String time, Long id){
        Calendar calendar = getCalendar(time,day);
        activarAlarma(applicationContext, calendar, id, INTERVAL_WEEK);

    }

    private void activarAlarma(Context applicationContext, Calendar calendar, Long id, long repeticion) {
        AlarmManager alarm = (AlarmManager) applicationContext.getSystemService(Context.ALARM_SERVICE);
        Intent myIntent = new Intent(applicationContext, AlarmChecker.class).putExtra(NOTIF_ID,id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(applicationContext, id.intValue(), myIntent, 0);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),repeticion, pendingIntent);
    }

    private Calendar getCalendar(String time, int dia) {
        String[] splitTime = time.split(SEPARADOR_HORA);
        String hora = splitTime[HORA];
        String minutos = splitTime[MINUTOS];

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hora));
        calendar.set(Calendar.MINUTE, Integer.parseInt(minutos));
        calendar.set(Calendar.SECOND, ZERO_SEGUNDOS);
        if (dia!=0&&isFechaFutura(time)){
            calendar.add(Calendar.DAY_OF_WEEK,dia);
        }
        return calendar;
    }

    private boolean isFechaFutura(String time) {
        SimpleDateFormat fromUser = new SimpleDateFormat("HH:mm");
        Date today = Calendar.getInstance().getTime();
        try {
            Date date = fromUser.parse(time);
            if (date.after(today)){
                return true;
            }else{
                return false;
            }
        } catch (ParseException e) {
           return false;
        }
    }

}
