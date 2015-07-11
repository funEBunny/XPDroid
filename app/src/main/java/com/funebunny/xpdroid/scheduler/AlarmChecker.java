package com.funebunny.xpdroid.scheduler;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.funebunny.xpdroid.R;
import com.funebunny.xpdroid.gastos.backend.ServicioGastos;
import com.funebunny.xpdroid.gastos.model.GastoProgramable;
import com.funebunny.xpdroid.main.ui.activity.CrearGastoActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by schmidt0 on 7/11/2015.
 */
public class AlarmChecker extends BroadcastReceiver {

    public int NOTIFICATION_ID = 0;
    private static NotificationManager NM;
    private static ServicioGastos servicioGastos;
    private int notificationNumber=0;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("SERVICEBOOT", "Intent received");
        if (servicioGastos==null){
            servicioGastos = new ServicioGastos();
        }
        lauchNotification(servicioGastos,context);
    }
    private void lauchNotification(ServicioGastos servicioGastos,Context context) {
        Log.d("SERVICEBOOT", "Starting Notification Launcher");
        List<GastoProgramable> gastoProgramables = servicioGastos.obtenerGastosProgramablesDelDia();
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minute = Calendar.getInstance().get(Calendar.MINUTE);
        String horaActual = String.valueOf(hour)+":"+String.valueOf(minute);
        SimpleDateFormat fromUser = new SimpleDateFormat("HH:mm");
        SimpleDateFormat myFormat = new SimpleDateFormat("HHmm");
        int horaFormateada = 0;

        try {
            horaFormateada = Integer.parseInt(myFormat.format(fromUser.parse(horaActual)));
            Log.d("SERVICEBOOT", "Hora Actual = "+horaFormateada);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (int i = 0; i <gastoProgramables.size(); i++) {
            GastoProgramable gastoProgramable = gastoProgramables.get(i);
            int hora = gastoProgramable.getHora();
            //String sHoraGasto = String.valueOf(hora);
            Log.d("SERVICEBOOT", "Hora Gasto = "+hora);
            if (hora<=horaFormateada){
                Log.d("SERVICEBOOT", "Notoficacion lanzada");
                NM = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
                NOTIFICATION_ID = NOTIFICATION_ID +1;
                Log.d("SERVICEBOOT", "NOTIFICATION_ID = "+NOTIFICATION_ID);
                NM.notify(+ NOTIFICATION_ID, notificacion(gastoProgramable,context));
                servicioGastos.eliminarGastoProgramable(gastoProgramable);
            }

        }
    }
    private Notification notificacion( GastoProgramable gastoProgramable,Context context ) {

        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(context);

        nBuilder.setSmallIcon(R.drawable.ic_scheduler);
        nBuilder.setContentTitle(gastoProgramable.getCategoria());
        nBuilder.setContentText(gastoProgramable.getImporte());
        nBuilder.setDefaults(Notification.DEFAULT_ALL);
        nBuilder.setAutoCancel(true);
         /* Increase notification number every time a new notification arrives */
        /*nBuilder.setNumber(++numMessages);*/
        Intent resultIntent = new Intent(context, CrearGastoActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(CrearGastoActivity.class);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        nBuilder.setContentIntent(resultPendingIntent);
        notificationNumber ++;
        Log.d("SERVICEBOOT", "notificationNumber = "+notificationNumber);
        nBuilder.setNumber(notificationNumber);
        return nBuilder.build();
    }

}
