package com.funebunny.xpdroid.backend.gasto.dao;

import android.test.AndroidTestCase;

import com.funebunny.xpdroid.utilities.AppConstants;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by provirabosch on 14/10/2015.
 */
public class GastoDAOTest extends AndroidTestCase {

    private GastoDAO gastoDao;
    SimpleDateFormat formatoEsperado;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        formatoEsperado = new SimpleDateFormat(AppConstants.FECHA_VISTA);
        gastoDao = new GastoDAO();
    }

    public void testGetFecha_formatoFecha() {
        gastoDao.setFecha(formatoEsperado.format(Calendar.getInstance().getTime()));
        String esperado = formatoEsperado.format(Calendar.getInstance().getTime());
        String real = gastoDao.getFecha();
        assertEquals(esperado, real);
    }
}
