package com.funebunny.xpdroid.business.alarma;

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
import com.funebunny.xpdroid.backend.gasto.service.ServicioGastosDAO;
import com.funebunny.xpdroid.business.gasto.model.Gasto;
import com.funebunny.xpdroid.business.gasto.model.GastoProgramable;
import com.funebunny.xpdroid.main.ui.activity.CrearGastoActivity;
import com.funebunny.xpdroid.utilities.AppConstants;

/**
 * Created by schmidt0 on 7/11/2015.
 */
public class AlarmChecker extends BroadcastReceiver {

    public Long NOTIFICATION_ID = 0L;
    private static NotificationManager NM;
    private static ServicioGastosDAO servicioGastos;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("XPDROID", "Alarm Intent received");
        if (servicioGastos==null){
            servicioGastos = new ServicioGastosDAO();
        }
        Long notifID = intent.getLongExtra("notifID", NOTIFICATION_ID);
        lauchNotification(servicioGastos,context,notifID);
    }
    private void lauchNotification(ServicioGastosDAO servicioGastos,Context context,Long notifID) {
        Log.d("XPDROID", "Starting Notification Launcher");
        GastoProgramable gastoProgramable = servicioGastos.obtenerGastoProgramablePorID(notifID);
        Log.d("XPDROID", "Notoficacion lanzada");
        NM = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Log.d("XPDROID", "NOTIFICATION_ID = " + notifID);
        NM.notify(notifID.intValue(), notificacion(gastoProgramable, context));

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

        Gasto gasto = new Gasto();
        gasto.setCategoria(gastoProgramable.getCategoria());
        gasto.setDescripcion(gastoProgramable.getDescripcion());
        gasto.setImporte(gastoProgramable.getImporte());

        bundle.putSerializable(AppConstants.GASTO, gasto);

//        bundle.putString("categoria",gastoProgramable.getCategoria());
//        bundle.putString("descripcion",gastoProgramable.getDescripcion());
//        bundle.putString("importe",gastoProgramable.getImporte());
//        bundle.putString("hora",gastoProgramable.getHora());
//        bundle.putLong("id",gastoProgramable.getId());
        resultIntent.putExtras(bundle);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(CrearGastoActivity.class);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(gastoProgramable.getId().intValue(), PendingIntent.FLAG_ONE_SHOT);
        nBuilder.setContentIntent(resultPendingIntent);
        Log.d("XPDROID", "notificationNumber = "+gastoProgramable.getId());
        nBuilder.setNumber(gastoProgramable.getId().intValue());
        return nBuilder.build();
    }

}
