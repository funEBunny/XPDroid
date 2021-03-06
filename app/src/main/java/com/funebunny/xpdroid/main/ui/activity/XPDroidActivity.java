package com.funebunny.xpdroid.main.ui.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spanned;
import android.text.method.DigitsKeyListener;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.funebunny.xpdroid.R;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class XPDroidActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xpdroid);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_xpdroid, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected String getFechaActual() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
        return dateFormat.format(Calendar.getInstance().getTime());

    }

    protected String getHoraActual() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("k:mm");
        return dateFormat.format(Calendar.getInstance().getTime());

    }

    protected void showMessage(int message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

    protected void showMessage(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

    public class DigitosImporteKeyListener extends DigitsKeyListener {

        private int parteEntera;
        private int parteDecimal;
        private Editable text;

        public DigitosImporteKeyListener(int digitosParteEntera, int digitosParteDecimal, Editable etText) {
            super(false, true);
            parteDecimal = digitosParteDecimal;
            parteEntera = digitosParteEntera;
            text = etText;
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            String temp = text + source.toString();

            if (temp.equals(".")) {
                return "0.";
            } else if (temp.toString().indexOf(".") == -1) {

                if (temp.length() > parteEntera) {
                    return "";
                }
            } else {
                temp = temp.substring(temp.indexOf(".") + 1);
                if (temp.length() > parteDecimal) {
                    return "";
                }
            }

            return super.filter(source, start, end, dest, dstart, dend);
        }
    }

}
