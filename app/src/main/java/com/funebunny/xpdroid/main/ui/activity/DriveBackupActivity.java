package com.funebunny.xpdroid.main.ui.activity;

/**
 * Created by provirabosch on 19/11/2015.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentSender;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.funebunny.xpdroid.R;
import com.funebunny.xpdroid.utilities.AppConstants;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi.DriveContentsResult;
import com.google.android.gms.drive.MetadataChangeSet;


public class DriveBackupActivity extends BaseDriveActivity {

    private static final String TAG = "drive-quickstart";
    private static final int REQUEST_CODE_CREATOR = 2;

    @Override
    public void onConnected(Bundle connectionHint) {
        super.onConnected(connectionHint);

        Drive.DriveApi.newDriveContents(getGoogleApiClient())
                .setResultCallback(driveContentsCallback);
    }

    final private ResultCallback<DriveContentsResult> driveContentsCallback =
            new ResultCallback<DriveContentsResult>() {

                @Override
                public void onResult(DriveContentsResult result) {

                    if (!result.getStatus().isSuccess()) {
                        Log.i(TAG, "Failed to create new contents.");
                        return;
                    }
                    // Otherwise, we can write our data to the new contents.
                    Log.i(TAG, "New contents created.");
                    // Get an output stream for the contents.
                    FileOutputStream outputStream = (FileOutputStream) result.getDriveContents().getOutputStream();
                    //Leer archivo DB y generar el nuevo archivo
                    try {
                        FileInputStream fileInputStream = new FileInputStream(getDBFile());

                        FileChannel origenDB = fileInputStream.getChannel();
                        FileChannel destinoDB = outputStream.getChannel();

                        destinoDB.transferFrom(origenDB, 0, origenDB.size());
                        origenDB.close();
                        destinoDB.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    String mimeType = MimeTypeMap.getSingleton().getExtensionFromMimeType("db");
                    MetadataChangeSet metadataChangeSet = new MetadataChangeSet.Builder()
                            .setTitle(AppConstants.DB_NAME)
                            .setMimeType(mimeType)
                            .build();

                    // Create an intent for the file chooser, and start it.
                    IntentSender intentSender = Drive.DriveApi
                            .newCreateFileActivityBuilder()
                            .setInitialMetadata(metadataChangeSet)
                            .setInitialDriveContents(result.getDriveContents())
                            .build(getGoogleApiClient());
                    try {
                        startIntentSenderForResult(
                                intentSender, REQUEST_CODE_CREATOR, null, 0, 0, 0);
                    } catch (SendIntentException e) {
                        Log.i(TAG, "Failed to launch file chooser.");
                    }
                }
            };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUEST_CODE_CREATOR:
                if (resultCode == RESULT_OK) {
                     showMessage(getResources().getString(R.string.backup_exitoso));
                }
                finish();
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    private File getDBFile() {
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        return new File(String.valueOf(contextWrapper.getDatabasePath(AppConstants.DB_NAME)));
    }

}

