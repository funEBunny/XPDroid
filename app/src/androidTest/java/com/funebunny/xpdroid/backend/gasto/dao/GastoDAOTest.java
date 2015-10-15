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
    SimpleDateFormat formatoEsperado = new SimpleDateFormat(AppConstants.FECHA_VISTA);

    @Override
    public void setUp() throws Exception {
        super.setUp();
        gastoDao = new GastoDAO();
        gastoDao.setFecha(formatoEsperado.format(Calendar.getInstance().getTime()));
    }

    public void testGetFecha_formatoFecha() {

        String esperado = formatoEsperado.format(Calendar.getInstance().getTime());
        String real = gastoDao.getFecha();

        assertEquals(esperado, real);

    }
}
