package com.funebunny.xpdroid.main.ui.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import com.funebunny.xpdroid.R;
import com.funebunny.xpdroid.utilities.AppConstants;

import java.text.DateFormatSymbols;
import java.util.Calendar;

/**
 * Created by provirabosch on 11/04/2015.
 */
public class MonthYearPickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

    }

    @Override
    public void onDateSet(DatePicker view, int anio, int mes, int dia) {
//        EditText mesAnioHistorial = (EditText) this.getActivity().findViewById(R.id.mes_anio_historial);
//        String monthString = new DateFormatSymbols().getMonths()[mes];
//        mesAnioHistorial.setText(new StringBuilder().append(monthString).append(" ").append(anio));
    }
}