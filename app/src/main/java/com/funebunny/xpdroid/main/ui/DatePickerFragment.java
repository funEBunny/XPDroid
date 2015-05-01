package com.funebunny.xpdroid.main.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import com.funebunny.xpdroid.R;

import java.util.Calendar;

/**
 * Created by provirabosch on 11/04/2015.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

    }

    @Override
    public void onDateSet(DatePicker view, int anio, int mes, int dia) {

        EditText fecha = (EditText) this.getActivity().findViewById(R.id.fecha);
        fecha.setText(new StringBuilder().append(dia).append("/").append(mes+1).append("/").append(anio));

    }
}