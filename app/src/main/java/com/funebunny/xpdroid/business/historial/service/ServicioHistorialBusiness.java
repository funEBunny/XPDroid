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
 * Created by provirabosch on 05/09/2015.
 */
public class ServicioHistorialBusiness implements IServicioHistorialBusiness {

    ServicioHistorialDAO servicioHistorialDAO = new ServicioHistorialDAO();

    @Override
    public List<Historial> obtenerListaHistorial() {
        return servicioHistorialDAO.obtenerListaHistorial();
    }

    @Override
    public void eliminarHistorial(Gasto gasto) {

        String fechaGasto = gasto.getFecha();
        SimpleDateFormat dateFormat = new SimpleDateFormat(AppConstants.FECHA_VISTA);
        try {
            Date dia = dateFormat.parse(fechaGasto);
            Calendar instance = Calendar.getInstance();
            instance.setTime(dia);
            int anioGasto = instance.get(Calendar.YEAR);
            int mesGasto = instance.get(Calendar.MONTH);
            Historial historial = servicioHistorialDAO.obtenerHistorial(mesGasto, anioGasto);
            String total = restar(historial.getTotal(), gasto.getImporte());
            if (new BigDecimal(total).compareTo(BigDecimal.ZERO) == 0) {
                servicioHistorialDAO.eliminarHistorial(historial.getId());
            } else {
                historial.setTotal(total);
                servicioHistorialDAO.actualizarHistorial(historial);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void guardarHistorial(Gasto gasto) {
        String fechaGasto = gasto.getFecha();
        SimpleDateFormat dateFormat = new SimpleDateFormat(AppConstants.FECHA_VISTA);
        try {
            Date dia = dateFormat.parse(fechaGasto);
            Calendar instance = Calendar.getInstance();
            instance.setTime(dia);
            int anioGasto = instance.get(Calendar.YEAR);
            int mesGasto = instance.get(Calendar.MONTH);
            Historial historial = servicioHistorialDAO.obtenerHistorial(mesGasto, anioGasto);
            String importeGasto = gasto.getImporte();
            if (historial != null) {
                String totalHisorial = historial.getTotal();
                historial.setTotal(sumar(totalHisorial, importeGasto));
                servicioHistorialDAO.actualizarHistorial(historial);
            } else {
                historial = new Historial();
                historial.setAnio(Integer.valueOf(anioGasto));
                historial.setMes(Integer.valueOf(mesGasto));
                historial.setTotal(importeGasto);
                servicioHistorialDAO.guardarHistorial(historial);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    private String restar(String total, String importe) {

        BigDecimal bdTotal = new BigDecimal(total);
        BigDecimal bdImporteGasto = new BigDecimal(importe);
        bdTotal = bdTotal.subtract(bdImporteGasto);
        return bdTotal.toPlainString();

    }

    private String sumar(String total, String importe) {
        BigDecimal bdTotal = new BigDecimal(total);
        BigDecimal bdImporteGasto = new BigDecimal(importe);
        bdTotal = bdTotal.add(bdImporteGasto);
        return bdTotal.toPlainString();
    }

}