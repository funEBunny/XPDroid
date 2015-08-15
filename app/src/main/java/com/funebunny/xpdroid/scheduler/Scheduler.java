package com.funebunny.xpdroid.scheduler;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.funebunny.xpdroid.R;
import com.funebunny.xpdroid.gastos.backend.service.ServicioGastosDAO;
import com.funebunny.xpdroid.gastos.business.model.GastoProgramable;
import com.funebunny.xpdroid.main.ui.activity.CrearGastoActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by schmidt0 on 5/9/2015.
 */
public class Scheduler extends Service implements Runnable {

    private Timer temporizador = new Timer();
    private static final long INTERVALO_ACTUALIZACION = 60000; // En ms
    public static final int NOTIFICATION_ID = 1;
    private static NotificationManager NM;
    private static ServicioGastosDAO servicioGastos;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "Servicio creado", Toast.LENGTH_LONG).show();
        Log.d("SERVICEBOOT", "Servicio creado");
        ServicioGastosDAO servicioGastos = new ServicioGastosDAO();
//        iniciarCronometro(servicioGastos);


    }

    private void lauchNotification(ServicioGastosDAO servicioGastos) {
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
                NM = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                NM.notify(NOTIFICATION_ID, notificacion(gastoProgramable));
                servicioGastos.eliminarGastoProgramable(gastoProgramable);
            }

        }
    }


    @Override
    public void onDestroy() {
        Toast.makeText(this, "Servicio destruido", Toast.LENGTH_LONG).show();
        Log.d("SERVICEBOOT", "Servicio destruido");
        pararCronometro();
    }

    private void iniciarCronometro(final ServicioGastosDAO servicioGastos) {
        temporizador.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                lauchNotification(servicioGastos);
            }
        }, 0, INTERVALO_ACTUALIZACION);
    }

    private void pararCronometro() {
        if (temporizador != null)
            temporizador.cancel();
    }



    private Notification notificacion( GastoProgramable gastoProgramable) {
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this);

        nBuilder.setSmallIcon(R.drawable.ic_scheduler);
        nBuilder.setContentTitle(gastoProgramable.getCategoria());
        nBuilder.setContentText(gastoProgramable.getImporte());
        nBuilder.setDefaults(Notification.DEFAULT_ALL);
        nBuilder.setAutoCancel(true);
         /* Increase notification number every time a new notification arrives */
        /*nBuilder.setNumber(++numMessages);*/
        Intent resultIntent = new Intent(this.getApplicationContext(), CrearGastoActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this.getApplicationContext());
        stackBuilder.addParentStack(CrearGastoActivity.class);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        nBuilder.setContentIntent(resultPendingIntent);
        return nBuilder.build();
    }


    @Override
    public void run() {
        lauchNotification(servicioGastos);
    }
}
