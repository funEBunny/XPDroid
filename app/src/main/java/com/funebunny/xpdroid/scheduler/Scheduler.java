package com.funebunny.xpdroid.scheduler;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.funebunny.xpdroid.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by schmidt0 on 5/9/2015.
 */
public class Scheduler extends Service {

    private Timer temporizador = new Timer();
    private static final long INTERVALO_ACTUALIZACION = 10000; // En ms
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager NM;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "Servicio creado", Toast.LENGTH_LONG).show();
        Log.d("SERVICEBOOT", "Servicio creado");
        iniciarCronometro();
    }


    @Override
    public void onDestroy() {
        Toast.makeText(this, "Servicio destruido", Toast.LENGTH_LONG).show();
        Log.d("SERVICEBOOT", "Servicio destruido");
        pararCronometro();
    }

    private void iniciarCronometro() {
        temporizador.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Log.d("SERVICEBOOT", "Notoficacion lanzada");
                NM = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                NM.notify(NOTIFICATION_ID,notificacion());
            }
        }, 0, INTERVALO_ACTUALIZACION);
    }

    private void pararCronometro() {
        if (temporizador != null)
            temporizador.cancel();
    }



    private Notification notificacion() {
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this);

        nBuilder.setSmallIcon(R.drawable.ic_scheduler);
        nBuilder.setContentTitle("App Services");
        nBuilder.setContentText("Servicio Iniciado");
        nBuilder.setDefaults(Notification.DEFAULT_ALL);
        nBuilder.setAutoCancel(true);
        return nBuilder.build();
    }
}
