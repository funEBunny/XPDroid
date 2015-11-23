package com.funebunny.xpdroid.main.ui.activity;

/**
 * Created by provirabosch on 20/11/2015.
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;

import com.funebunny.xpdroid.business.presupuesto.service.ServicioPresupuestoBusiness;
import com.funebunny.xpdroid.utilities.AppConstants;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi.DriveContentsResult;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.OpenFileActivityBuilder;


public class DriveRestoreActivity extends BaseDriveActivity {

    private static final String TAG = "drive-quickstart";
    private static final int REQUEST_CODE_OPENER = 2;
    private DriveId archivoSeleccionadoDriveId;

    @Override
    public void onConnected(Bundle connectionHint) {
        super.onConnected(connectionHint);

        if (archivoSeleccionadoDriveId != null) {
            restaurarDB();
            return;
        }

        IntentSender intentSender = Drive.DriveApi.newOpenFileActivityBuilder().build(getGoogleApiClient());

        try {
            startIntentSenderForResult(
                    intentSender, REQUEST_CODE_OPENER, null, 0, 0, 0);
        } catch (IntentSender.SendIntentException e) {
            Log.i(TAG, "Failed to launch file chooser.");
        }

    }

    final private ResultCallback<DriveContentsResult> driveContentsCallback =
            new ResultCallback<DriveContentsResult>() {

                @Override
                public void onResult(DriveContentsResult result) {
                    // If the operation was not successful, we cannot do anything
                    // and must
                    // fail.
                    if (!result.getStatus().isSuccess()) {
                        Log.i(TAG, "Failed to create new contents.");
                        return;
                    }
                    // Otherwise, we can write our data to the new contents.
                    Log.i(TAG, "New contents created.");

                    IntentSender intentSender = Drive.DriveApi.newOpenFileActivityBuilder().build(getGoogleApiClient());

                    try {
                        startIntentSenderForResult(
                                intentSender, REQUEST_CODE_OPENER, null, 0, 0, 0);
                    } catch (IntentSender.SendIntentException e) {
                        Log.i(TAG, "Failed to launch file chooser.");
                    }

                }
            };

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {

        switch (requestCode) {
            case REQUEST_CODE_OPENER:
                if (resultCode == RESULT_OK) {
                    archivoSeleccionadoDriveId = data.getParcelableExtra(
                            OpenFileActivityBuilder.EXTRA_RESPONSE_DRIVE_ID);
                } else if (resultCode == RESULT_CANCELED) {
                    finish();
                    return;
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void restaurarDB() {
        DriveFile driveFile = archivoSeleccionadoDriveId.asDriveFile();
        driveFile.open(getGoogleApiClient(), DriveFile.MODE_READ_ONLY, null).setResultCallback(contentsOpenedCallback);
        archivoSeleccionadoDriveId = null;
    }

    private ResultCallback<DriveContentsResult> contentsOpenedCallback = new ResultCallback<DriveContentsResult>() {
        @Override
        public void onResult(DriveContentsResult driveContentsResult) {

            try {

                FileInputStream fileInputStream = (FileInputStream) driveContentsResult.getDriveContents().getInputStream();
                FileOutputStream fileOutputStream = new FileOutputStream(getApplicationContext().getDatabasePath(AppConstants.DB_NAME));

                FileChannel origenDB = fileInputStream.getChannel();
                FileChannel destinoDB = fileOutputStream.getChannel();

                destinoDB.transferFrom(origenDB, 0, origenDB.size());
                origenDB.close();
                destinoDB.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            ServicioPresupuestoBusiness servicioPresupuestoBusiness = new ServicioPresupuestoBusiness();
            servicioPresupuestoBusiness.calcularTotalesRestore();
            finish();
        }
    };
}