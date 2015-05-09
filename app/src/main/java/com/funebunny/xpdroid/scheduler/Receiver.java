package com.funebunny.xpdroid.scheduler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by schmidt0 on 5/9/2015.
 */
public class Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // LANZAR SERVICIO
        Intent serviceIntent = new Intent();
        serviceIntent.setAction("com.funebunny.xpdroid.scheduler.MyScheduler");
        context.startService(serviceIntent);

    }
}
