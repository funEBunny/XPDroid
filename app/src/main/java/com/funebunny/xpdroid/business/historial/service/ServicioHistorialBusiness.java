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
            Historial historial = servicioHistorialDAO.obtenerHistorial(String.valueOf(mesGasto), String.valueOf(anioGasto));
            String totalHisorial = historial.getTotal();
            BigDecimal bdTotal = new BigDecimal(totalHisorial);
            BigDecimal bdImporteGasto = new BigDecimal(gasto.getImporte());
            bdTotal= bdTotal.subtract(bdImporteGasto);
            historial.setTotal(bdTotal.toPlainString());
            servicioHistorialDAO.guardarHistorial(historial);

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
            Historial historial = servicioHistorialDAO.obtenerHistorial(String.valueOf(mesGasto), String.valueOf(anioGasto));
            String totalHisorial = "0";
            if (historial != null) {
                totalHisorial = historial.getTotal();
            } else {
                historial = new Historial();
                historial.setAnio(Integer.valueOf(anioGasto));
                historial.setMes(Integer.valueOf(mesGasto));
            }
            BigDecimal bdTotal = new BigDecimal(totalHisorial);
            BigDecimal bdImporteGasto = new BigDecimal(gasto.getImporte());
            bdTotal = bdTotal.add(bdImporteGasto);
            historial.setTotal(bdTotal.toPlainString());
            servicioHistorialDAO.guardarHistorial(historial);
        }catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
