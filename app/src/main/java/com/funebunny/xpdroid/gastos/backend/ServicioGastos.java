package com.funebunny.xpdroid.gastos.backend;

import com.activeandroid.query.Select;
import com.funebunny.xpdroid.gastos.dao.Gasto;
import com.funebunny.xpdroid.gastos.dao.GastoProgramableDAO;
import com.funebunny.xpdroid.gastos.model.GastoProgAnual;
import com.funebunny.xpdroid.gastos.model.GastoProgDiario;
import com.funebunny.xpdroid.gastos.model.GastoProgMensual;
import com.funebunny.xpdroid.gastos.model.GastoProgSemanal;
import com.funebunny.xpdroid.gastos.model.GastoProgramable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by schmidt0 on 4/3/2015.
 */
public class ServicioGastos implements IServicioGastos {

   // @Inject
    public ServicioGastos() {
    }

    @Override
    public List<Gasto> obtenerGastos() {
        return new Select().from(Gasto.class).orderBy("Categoria ASC").execute();
    }

    @Override
    public List<Gasto> obtenerGastosPorCategoria(String categoria) {
        return new Select().from(Gasto.class).where("Categoria = ?", categoria).execute();
    }

    @Override
    public List<Gasto> obtenerGastosPorFecha(String mes, String anio) {
        if(mes.length()==1) {
            mes = "0" + mes;
        }
        String fecha = "%" + anio + mes + "%";
        return new Select().from(Gasto.class).where("Fecha LIKE ?", fecha).orderBy("Categoria ASC").execute();
    }

    @Override
    public void guardarGasto(String descripcion, String importe, String categoria, String fecha) {
        Gasto gasto = new Gasto();
        gasto.setImporte(importe);
        gasto.setDescripcion(descripcion);
        gasto.setCategoria(categoria);

        String fechaGasto = "";
        SimpleDateFormat fromUser = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            fechaGasto = myFormat.format(fromUser.parse(fecha));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        gasto.setFecha(fechaGasto);
        gasto.save();
    }

    @Override
    public List<GastoProgramable> obtenerGastosProgramables() {
        return null;
    }

    @Override
    public List<GastoProgramable> obtenerGastosProgramablesDelDia() {


        ArrayList<GastoProgramableDAO> gastosProgLista = new Select().from(GastoProgramableDAO.class).execute();
        for (int i = 0; i < gastosProgLista.size(); i++) {
            GastoProgramableDAO gastoProgramableDAO = gastosProgLista.get(i);
            int repeticion = gastoProgramableDAO.getRepeticion();
            List<GastoProgramable> gastos = new ArrayList<>();
            GastoProgramable gastoProgramable;
            switch (repeticion){
                case GastoProgramableDAO.SEMANAL:{
                    gastoProgramable = new GastoProgSemanal();
                    ((GastoProgSemanal)gastoProgramable).setDiaSemana(gastoProgramableDAO.getDiaSemana());
                    gastos.add(gastoProgramable);
                    break;
                }
                case GastoProgramableDAO.MENSUAL:{
                    gastoProgramable = new GastoProgMensual();
                    ((GastoProgMensual)gastoProgramable).setDiaMes(gastoProgramableDAO.getDiaMes());
                    gastos.add(gastoProgramable);
                    break;
                }
                case GastoProgramableDAO.ANUAL:{
                    gastoProgramable = new GastoProgAnual();
                    gastos.add(gastoProgramable);
                    break;
                }
                default:{
                    gastoProgramable = new GastoProgDiario();
                    gastos.add(gastoProgramable);
                    break;
                }
            }

            Integer hora = gastoProgramableDAO.getHora();
            String categoria = gastoProgramableDAO.getCategoria();
            String importe = gastoProgramableDAO.getImporte();
            String descripcion = gastoProgramableDAO.getDescripcion();

            gastoProgramable.setImporte(importe);
            gastoProgramable.setCategoria(categoria);
            gastoProgramable.setDescripcion(descripcion);
            gastoProgramable.setHora(hora);
        }
        return null;
    }

    @Override
    public void guardarGastoProgramable(GastoProgramable gp) {

        GastoProgramableDAO gpd = new GastoProgramableDAO();

        if (gp instanceof GastoProgDiario) {
            gpd.setRepeticion(GastoProgramableDAO.DIARIO);
        }else if (gp instanceof GastoProgSemanal){
            int diaSemana = ((GastoProgSemanal) gp).getDiaSemana();
            gpd.setDiaSemana(diaSemana);
            gpd.setRepeticion(GastoProgramableDAO.SEMANAL);
        }

        gpd.setCategoria(gp.getCategoria());
        gpd.setDescripcion(gp.getDescripcion());
        gpd.setImporte(gp.getImporte());
        gpd.setHora(gp.getHora());
        gpd.save();
    }

}
