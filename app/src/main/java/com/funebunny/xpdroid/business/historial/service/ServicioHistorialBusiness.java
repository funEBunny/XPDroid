package com.funebunny.xpdroid.business.historial.service;

import com.funebunny.xpdroid.backend.historial.service.ServicioHistorialDAO;
import com.funebunny.xpdroid.business.gasto.model.Gasto;
import com.funebunny.xpdroid.business.historial.model.Historial;
import com.funebunny.xpdroid.utilities.AppConstants;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by I823537 on 05/09/2015.
 */
public class ServicioHistorialBusiness implements IServicioHistorialBusiness {

    ServicioHistorialDAO servicioHistorialDAO = new ServicioHistorialDAO();

    @Override
    public List<Historial> obtenerListaHistorial() {
        return servicioHistorialDAO.obtenerListaHistorial();
    }

    private void guardarHistorial(Historial historial) {

        servicioHistorialDAO.guardarHistorial(historial);
    }

    @Override
    public void eliminarHistorial(Gasto gasto) {
        String fechaGasto = gasto.getFecha();
        String anioGasto = fechaGasto.substring(0,4);
        String mesGasto = fechaGasto.substring(4,6);
        Historial historial = servicioHistorialDAO.obtenerHistorial(mesGasto, anioGasto);
        String totalHisorial = historial.getTotal();
        BigDecimal bdTotal = new BigDecimal(totalHisorial);
        BigDecimal bdImporteGasto = new BigDecimal(gasto.getImporte());
        bdTotal= bdTotal.subtract(bdImporteGasto);
        historial.setTotal(bdTotal.toPlainString());
        servicioHistorialDAO.guardarHistorial(historial);
    }

    @Override
    public void guardarHistorial(Gasto gasto) {
        String fechaGasto = gasto.getFecha();
        String anioGasto = fechaGasto.substring(0,4);
        String mesGasto = fechaGasto.substring(4,6);
        Historial historial = servicioHistorialDAO.obtenerHistorial(mesGasto, anioGasto);
        String totalHisorial = historial.getTotal();
        BigDecimal bdTotal = new BigDecimal(totalHisorial);
        BigDecimal bdImporteGasto = new BigDecimal(gasto.getImporte());
        bdTotal= bdTotal.add(bdImporteGasto);
        historial.setTotal(bdTotal.toPlainString());
        servicioHistorialDAO.guardarHistorial(historial);

    }
}
