package com.funebunny.xpdroid.utilities;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by provirabosch on 01/05/2015.
 */
public class AppUtilities {

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}
