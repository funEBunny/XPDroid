package com.funebunny.xpdroid.scheduler;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

    public Long NOTIFICATION_ID = 0L;
    private static NotificationManager NM;
    private static ServicioGastos servicioGastos;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("SERVICEBOOT", "Intent received");
        if (servicioGastos==null){
            servicioGastos = new ServicioGastos();
        }
        Long notifID = intent.getLongExtra("notifID", NOTIFICATION_ID);
        lauchNotification(servicioGastos,context,notifID);
    }
    private void lauchNotification(ServicioGastos servicioGastos,Context context,Long notifID) {
        Log.d("SERVICEBOOT", "Starting Notification Launcher");
        GastoProgramable gastoProgramable = servicioGastos.obtenerGastoProgramablePorID(notifID);
/*
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minute = Calendar.getInstance().get(Calendar.MINUTE);
        String horaActual = String.valueOf(hour)+":"+String.valueOf(minute);
        SimpleDateFormat fromUser = new SimpleDateFormat("HH:mm");
        SimpleDateFormat myFormat = new SimpleDateFormat("HHmm");
        int horaFormateada = 0;
*/

        Log.d("SERVICEBOOT", "Notoficacion lanzada");
        NM = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Log.d("SERVICEBOOT", "NOTIFICATION_ID = " + notifID);
        NM.notify(notifID.intValue(), notificacion(gastoProgramable, context));

/*        try {
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
                Log.d("SERVICEBOOT", "NOTIFICATION_ID = " + notifID);
                NM.notify(notifID, notificacion(gastoProgramable, context, notifID));
                //servicioGastos.eliminarGastoProgramable(gastoProgramable);
            }

        }*/
    }
    private Notification notificacion( GastoProgramable gastoProgramable,Context context ) {

        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(context);

        nBuilder.setDefaults(Notification.DEFAULT_ALL);
        nBuilder.setSmallIcon(R.drawable.ic_xpdroid_notification);
        nBuilder.setContentTitle(gastoProgramable.getCategoria());
        nBuilder.setContentText(gastoProgramable.getDescripcion()+": $"+ gastoProgramable.getImporte());
        nBuilder.setAutoCancel(true);
        Intent resultIntent = new Intent(context, CrearGastoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("categoria",gastoProgramable.getCategoria());
        bundle.putString("descripcion",gastoProgramable.getDescripcion());
        bundle.putString("importe",gastoProgramable.getImporte());
        bundle.putInt("hora",gastoProgramable.getHora());
        bundle.putLong("id",gastoProgramable.getId());
        resultIntent.putExtras(bundle);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(CrearGastoActivity.class);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(gastoProgramable.getId().intValue(), PendingIntent.FLAG_ONE_SHOT);
        nBuilder.setContentIntent(resultPendingIntent);
        Log.d("SERVICEBOOT", "notificationNumber = "+gastoProgramable.getId());
        nBuilder.setNumber(gastoProgramable.getId().intValue());
        return nBuilder.build();
    }

}
