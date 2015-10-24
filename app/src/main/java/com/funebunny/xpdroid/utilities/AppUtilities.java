package com.funebunny.xpdroid.utilities;

        import android.app.Activity;
        import android.view.inputmethod.InputMethodManager;

        import java.text.DecimalFormat;
        import java.text.NumberFormat;

/**
 * Created by provirabosch on 01/05/2015.
 */
public final class AppUtilities {

    public static String formatearImporte(String importe){

        NumberFormat format = NumberFormat.getInstance();
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(2);
        return format.format(Double.valueOf(importe));
    }
}
