package com.funebunny.xpdroid.business.alarma;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.funebunny.xpdroid.business.gasto.model.GastoProgramable;
import com.funebunny.xpdroid.business.gasto.service.ServicioGastosBusiness;
import com.funebunny.xpdroid.business.notificacion.service.ServicioNotificacionesBusiness;

import java.util.List;

/**
 * Created by schmidt0 on 8/28/2015.
 */
public class DeviceBootReceiver  extends BroadcastReceiver {
    private ServicioGastosBusiness servicioGastosBusiness = new ServicioGastosBusiness();
    private ServicioNotificacionesBusiness notificationsService = new ServicioNotificacionesBusiness();
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Log.d("XPDROID", " BOOT Intent received");
            List<GastoProgramable> gastoProgramables = servicioGastosBusiness.obtenerGastosProgramables();
            for (GastoProgramable gp : gastoProgramables) {
                notificationsService.actualizarAlarma(context,gp);

            }

        }
    }
}