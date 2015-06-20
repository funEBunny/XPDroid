package com.funebunny.xpdroid.main.ui.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.EditText;
import android.widget.TimePicker;

import com.funebunny.xpdroid.R;

import java.util.Calendar;

/**
 * Created by provirabosch on 11/04/2015.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hora, int minutos) {

        EditText horario = (EditText) this.getActivity().findViewById(R.id.horario);
        horario.setText(new StringBuilder().append(hora).append(":").append(minutos));

    }
}
