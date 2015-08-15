package com.funebunny.xpdroid.gastos.backend.service;

import android.util.Log;

import com.activeandroid.query.Select;
import com.funebunny.xpdroid.gastos.backend.dao.Gasto;
import com.funebunny.xpdroid.gastos.backend.dao.GastoFavoritoDAO;
import com.funebunny.xpdroid.gastos.backend.dao.GastoProgramableDAO;
import com.funebunny.xpdroid.gastos.business.model.GastoFavorito;
import com.funebunny.xpdroid.gastos.business.model.GastoProgAnual;
import com.funebunny.xpdroid.gastos.business.model.GastoProgDiario;
import com.funebunny.xpdroid.gastos.business.model.GastoProgMensual;
import com.funebunny.xpdroid.gastos.business.model.GastoProgSemanal;
import com.funebunny.xpdroid.gastos.business.model.GastoProgramable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by schmidt0 on 4/3/2015.
 */
public class ServicioGastosDAO implements IServicioGastosDAO {

    // @Inject
    public ServicioGastosDAO() {
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
        if (mes.length() == 1) {
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
        gasto.setFecha(fecha);

        Log.d("XPDROID", "Guardando Gasto: " + gasto.toString());
        gasto.save();
    }

    @Override
    public List<GastoProgramable> obtenerGastosProgramables() {


        List<GastoProgramable> gastos = new ArrayList<>();
        ArrayList<GastoProgramableDAO> gastosProgLista = new Select().from(GastoProgramableDAO.class).execute();

        for (int i = 0; i < gastosProgLista.size(); i++) {
            GastoProgramableDAO gastoProgramableDAO = gastosProgLista.get(i);
            GastoProgramable gastoProgramable;
            int repeticion = gastoProgramableDAO.getRepeticion();
            switch (repeticion) {
                case GastoProgramableDAO.SEMANAL: {
                    gastoProgramable = new GastoProgSemanal();
                    ((GastoProgSemanal) gastoProgramable).setDiaSemana(gastoProgramableDAO.getDiaSemana());
                    break;
                }
                case GastoProgramableDAO.MENSUAL: {
                    gastoProgramable = new GastoProgMensual();
                    ((GastoProgMensual) gastoProgramable).setDiaMes(gastoProgramableDAO.getDiaMes());
                    break;
                }
                case GastoProgramableDAO.ANUAL: {
                    gastoProgramable = new GastoProgAnual();
                    break;
                }
                default: {
                    gastoProgramable = new GastoProgDiario();
                    break;
                }
            }

            Integer hora = gastoProgramableDAO.getHora();
            String categoria = gastoProgramableDAO.getCategoria();
            String importe = gastoProgramableDAO.getImporte();
            String descripcion = gastoProgramableDAO.getDescripcion();
            Long id = gastoProgramableDAO.getId();

            gastoProgramable.setImporte(importe);
            gastoProgramable.setCategoria(categoria);
            gastoProgramable.setDescripcion(descripcion);
            gastoProgramable.setHora(hora);
            gastoProgramable.setId(id);
            Log.d("XPDROID", "Obteniendo Gasto: " + gastoProgramable.toString());
            gastos.add(gastoProgramable);
        }
        return gastos;
    }

    @Override
    public GastoProgramable obtenerGastoProgramablePorID(Long id) {


        ArrayList<GastoProgramableDAO> gastosProgLista = new Select().from(GastoProgramableDAO.class).where("Id = ?", id).execute();

        GastoProgramableDAO gastoProgramableDAO = gastosProgLista.get(0);
        GastoProgramable gastoProgramable;
        int repeticion = gastoProgramableDAO.getRepeticion();

        switch (repeticion) {
            case GastoProgramableDAO.SEMANAL: {
                gastoProgramable = new GastoProgSemanal();
                ((GastoProgSemanal) gastoProgramable).setDiaSemana(gastoProgramableDAO.getDiaSemana());
                break;
            }
            case GastoProgramableDAO.MENSUAL: {
                gastoProgramable = new GastoProgMensual();
                ((GastoProgMensual) gastoProgramable).setDiaMes(gastoProgramableDAO.getDiaMes());
                break;
            }
            case GastoProgramableDAO.ANUAL: {
                gastoProgramable = new GastoProgAnual();
                break;
            }
            default: {
                gastoProgramable = new GastoProgDiario();
                break;
            }
        }
        gastoProgramable.setImporte(gastoProgramableDAO.getImporte());
        gastoProgramable.setCategoria(gastoProgramableDAO.getCategoria());
        gastoProgramable.setDescripcion(gastoProgramableDAO.getDescripcion());
        gastoProgramable.setHora(gastoProgramableDAO.getHora());
        gastoProgramable.setId(gastoProgramableDAO.getId());
        Log.d("XPDROID", "Obteniendo Gasto: " + gastoProgramable.toString());
        return gastoProgramable;
    }

    @Override
    public Long guardarGastoProgramable(GastoProgramable gp) {
        Log.d("XPDROID", "Programando Gasto: " + gp.toString());
        GastoProgramableDAO gpd = new GastoProgramableDAO();

        if (gp instanceof GastoProgDiario) {
            gpd.setRepeticion(GastoProgramableDAO.DIARIO);
        } else if (gp instanceof GastoProgSemanal) {
            int diaSemana = ((GastoProgSemanal) gp).getDiaSemana();
            gpd.setDiaSemana(diaSemana);
            gpd.setRepeticion(GastoProgramableDAO.SEMANAL);
        }

        gpd.setCategoria(gp.getCategoria());
        gpd.setDescripcion(gp.getDescripcion());
        gpd.setImporte(gp.getImporte());
        gpd.setHora(gp.getHora());
        gpd.save();
        return gpd.getId();

    }

    @Override
    public void eliminarGastoProgramable(Long id) {
        Log.d("XPDROID", "Eliminando Gasto: " + id);
        GastoProgramableDAO delGP = GastoProgramableDAO.load(GastoProgramableDAO.class, id);
        delGP.delete();
    }

    @Override
    public void eliminarGasto(Long id) {
        obtenerGastoPorId(id).delete();
    }

    @Override
    public void actualizarGasto(Gasto gasto) {
        Gasto gastoDb = obtenerGastoPorId(gasto.getgId());
        gastoDb.setDescripcion(gasto.getDescripcion());
        gastoDb.setImporte(gasto.getImporte());
        gastoDb.setCategoria(gasto.getCategoria());
        gastoDb.setFecha(gasto.getFecha());
        gastoDb.save();
    }

    @Override
    public Gasto obtenerGastoPorId(Long id) {
        ArrayList<Gasto> gastosLista = new Select().from(Gasto.class).where("Id = ?", id).execute();
        return gastosLista.get(0);
    }


    // Gastos Favoritos

    @Override
    public Long guardarGastoFavorito(GastoFavorito gf) {

        GastoFavoritoDAO gfd = new GastoFavoritoDAO();
        gfd.setDescripcion(gf.getDescripcion());
        gfd.setImporte(gf.getImporte());
        gfd.setCategoria(gf.getCategoria());
        gfd.save();
        return gfd.getId();
    }

    @Override
    public void eliminarGastoFavorito(Long id) {
        obtenerGastoFavoritoPorId(id).delete();
    }

    @Override
    public void actualizarGastoFavorito(GastoFavorito gf) {

        GastoFavoritoDAO gfd = obtenerGastoFavoritoPorId(gf.getId());
        gfd.setDescripcion(gf.getDescripcion());
        gfd.setCategoria(gf.getCategoria());
        gfd.setImporte(gf.getImporte());
        gfd.save();
    }

    @Override
    public List<GastoFavorito> obtenerGastosFavoritos() {

        List<GastoFavorito> gastosFav = new ArrayList<>();
        ArrayList<GastoFavoritoDAO> gastosFavDAO = new Select().from(GastoFavoritoDAO.class).execute();

        for (int i = 0; i < gastosFavDAO.size(); i++) {

            GastoFavoritoDAO gfd = gastosFavDAO.get(i);
            GastoFavorito gf = new GastoFavorito();

            gf.setId(gfd.getId());
            gf.setDescripcion(gfd.getDescripcion());
            gf.setImporte(gfd.getImporte());
            gf.setCategoria(gfd.getCategoria());
            gastosFav.add(gf);
        }
        return gastosFav;
    }

    private GastoFavoritoDAO obtenerGastoFavoritoPorId(Long id) {
        ArrayList<GastoFavoritoDAO> gastosFavLista = new Select().from(GastoFavoritoDAO.class).where("Id = ?", id).execute();
        return gastosFavLista.get(0);
    }


}
