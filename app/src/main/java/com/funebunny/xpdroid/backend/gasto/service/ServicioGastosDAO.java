package com.funebunny.xpdroid.backend.gasto.service;

import android.util.Log;

import com.activeandroid.query.Select;
import com.funebunny.xpdroid.backend.gasto.dao.GastoDAO;
import com.funebunny.xpdroid.backend.gasto.dao.GastoFavoritoDAO;
import com.funebunny.xpdroid.backend.gasto.dao.GastoProgramableDAO;
import com.funebunny.xpdroid.business.gasto.model.Gasto;
import com.funebunny.xpdroid.business.gasto.model.GastoFavorito;
import com.funebunny.xpdroid.business.gasto.model.GastoProgAnual;
import com.funebunny.xpdroid.business.gasto.model.GastoProgDiario;
import com.funebunny.xpdroid.business.gasto.model.GastoProgMensual;
import com.funebunny.xpdroid.business.gasto.model.GastoProgSemanal;
import com.funebunny.xpdroid.business.gasto.model.GastoProgramable;

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

        List<Gasto> gastos = new ArrayList<>();
        ArrayList<GastoDAO> gastosDAO = new Select().from(GastoDAO.class).orderBy("Categoria ASC").execute();

        for (int i = 0; i < gastosDAO.size(); i++) {

            GastoDAO gDAO = gastosDAO.get(i);
            Gasto gasto = new Gasto();

            gasto.setId(gDAO.getId());
            gasto.setDescripcion(gDAO.getDescripcion());
            gasto.setImporte(gDAO.getImporte());
            gasto.setCategoria(gDAO.getCategoria());
            gasto.setFecha(gDAO.getFecha());
            gastos.add(gasto);
        }
        Log.d("XPDROID", "Obteniendo Gastos: "+gastos);
        return gastos;
    }

    @Override
    public List<Gasto> obtenerGastosPorCategoria(String categoria) {
        Log.d("XPDROID", "Obteniendo Gastos por categoria: "+categoria);
        List<Gasto> gastos = new ArrayList<>();
        ArrayList<GastoDAO> gastosDAO = new Select().from(GastoDAO.class).where("Categoria = ?", categoria).execute();

        for (int i = 0; i < gastosDAO.size(); i++) {

            GastoDAO gDAO = gastosDAO.get(i);
            Gasto gasto = new Gasto();

            gasto.setId(gDAO.getId());
            gasto.setDescripcion(gDAO.getDescripcion());
            gasto.setImporte(gDAO.getImporte());
            gasto.setCategoria(gDAO.getCategoria());
            gasto.setFecha(gDAO.getFecha());
            gastos.add(gasto);
        }
        Log.d("XPDROID", "Obteniendo Gastos por categoria: "+categoria+", Gastos: " +gastos);
        return gastos;
    }

    @Override
    public List<Gasto> obtenerGastosFechaLike(String fecha) {
        Log.d("XPDROID", "Obteniendo Gastos por fecha: "+fecha);
        String fechaBuscar = "%" + fecha + "%";
        List<Gasto> gastos = new ArrayList<>();
        ArrayList<GastoDAO> gastosDAO = new Select().from(GastoDAO.class).where("Fecha LIKE ?", fechaBuscar).orderBy("Fecha DESC").execute();

        for (int i = 0; i < gastosDAO.size(); i++) {

            GastoDAO gDAO = gastosDAO.get(i);
            Gasto gasto = new Gasto();

            gasto.setId(gDAO.getId());
            gasto.setDescripcion(gDAO.getDescripcion());
            gasto.setImporte(gDAO.getImporte());
            gasto.setCategoria(gDAO.getCategoria());
            gasto.setFecha(gDAO.getFecha());
            gastos.add(gasto);
        }
        Log.d("XPDROID", "Obteniendo Gastos por fecha: "+fecha+" Gastos: "+gastos);
        return gastos;
    }

    @Override
    public List<Gasto> obtenerGastosDesdeHasta(String desde, String hasta) {
        Log.d("XPDROID", "Obteniendo Gastos desde "+desde+" hasta "+hasta);
        ArrayList<GastoDAO> gastosDAO = new Select().from(GastoDAO.class).where("Fecha BETWEEN ? AND ?", desde,hasta).orderBy("Categoria ASC").execute();
        List<Gasto> gastos = new ArrayList<>();
        for (int i = 0; i < gastosDAO.size(); i++) {
            GastoDAO gDAO = gastosDAO.get(i);
            Gasto gasto = new Gasto();
            gasto.setId(gDAO.getId());
            gasto.setDescripcion(gDAO.getDescripcion());
            gasto.setImporte(gDAO.getImporte());
            gasto.setCategoria(gDAO.getCategoria());
            gasto.setFecha(gDAO.getFecha());
            gastos.add(gasto);
        }
        Log.d("XPDROID", "Obteniendo Gastos desde "+desde+" hasta "+hasta+": Gastos:" +gastos);
        return gastos;
    }

    @Override
    public Long guardarGasto(Gasto gasto) {
        Log.d("XPDROID", "Guardando gasto: "+gasto);
        GastoDAO gDAO = new GastoDAO();
        gDAO.setDescripcion(gasto.getDescripcion());
        gDAO.setImporte(gasto.getImporte());
        gDAO.setCategoria(gasto.getCategoria());
        gDAO.setFecha(gasto.getFecha());
        gDAO.save();
        return gDAO.getId();
    }

    @Override
    public void eliminarGasto(Long id) {
        Log.d("XPDROID", "Obteniendo Gastos id "+id);
        obtenerGastoPorId(id).delete();
    }

    @Override
    public void actualizarGasto(Gasto gasto) {
        Log.d("XPDROID", "Actualizando Gasto: "+gasto);
        GastoDAO gDAO = obtenerGastoPorId(gasto.getId());
        gDAO.setDescripcion(gasto.getDescripcion());
        gDAO.setFecha(gasto.getFecha());
        gDAO.setCategoria(gasto.getCategoria());
        gDAO.setImporte(gasto.getImporte());
        gDAO.save();
    }

    @Override
    public List<GastoProgramable> obtenerGastosProgramables() {

        Log.d("XPDROID", "Obteniendo Gastos programables");
        List<GastoProgramable> gastos = new ArrayList<>();
        ArrayList<GastoProgramableDAO> gastosProgLista = new Select().from(GastoProgramableDAO.class).execute();
        Log.d("XPDROID", "Gastos programables DAO: "+gastosProgLista);
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

            String hora = gastoProgramableDAO.getHora();
            String categoria = gastoProgramableDAO.getCategoria();
            String importe = gastoProgramableDAO.getImporte();
            String descripcion = gastoProgramableDAO.getDescripcion();
            Long id = gastoProgramableDAO.getId();

            gastoProgramable.setImporte(importe);
            gastoProgramable.setCategoria(categoria);
            gastoProgramable.setDescripcion(descripcion);
            gastoProgramable.setHora(hora);
            gastoProgramable.setId(id);
            gastos.add(gastoProgramable);
        }
        Log.d("XPDROID", "Gastos Programables BL: " + gastos);
        return gastos;
    }

    @Override
    public GastoProgramable obtenerGastoProgramablePorID(Long id) {

        Log.d("XPDROID", "Obteniendo Gastos Programables por ID "+id);
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
        Log.d("XPDROID", "Obteniendo GastoDAO: " + gastoProgramable.toString());
        return gastoProgramable;
    }

    @Override
    public Long guardarGastoProgramable(GastoProgramable gp) {
        Log.d("XPDROID", "Programando GastoDAO: " + gp.toString());
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
        Log.d("XPDROID", "Eliminando GastoDAO: " + id);
        GastoProgramableDAO delGP = GastoProgramableDAO.load(GastoProgramableDAO.class, id);
        delGP.delete();
    }

    @Override
    public void actualizarGastoProgramable(GastoProgramable gastoProgramable) {
        Log.d("XPDROID", "Actualizando Gasto Programable: " + gastoProgramable);
        GastoProgramableDAO gastoDb = GastoProgramableDAO.load(GastoProgramableDAO.class, gastoProgramable.getId());
        gastoDb.setDescripcion(gastoProgramable.getDescripcion());
        gastoDb.setImporte(gastoProgramable.getImporte());
        gastoDb.setCategoria(gastoProgramable.getCategoria());
        gastoDb.setHora(gastoProgramable.getHora());
        gastoDb.save();
    }

    private GastoDAO obtenerGastoPorId(Long id) {
        ArrayList<GastoDAO> gastosLista = new Select().from(GastoDAO.class).where("Id = ?", id).execute();
        GastoDAO gastoDAO = gastosLista.get(0);
        Log.d("XPDROID", "Obteniendo Gasto Programable por ID "+id+ "Gasto: "+ gastoDAO);
        return gastoDAO;
    }


    // Gastos Favoritos

    @Override
    public Long guardarGastoFavorito(GastoFavorito gf) {
        Log.d("XPDROID", "Guardando Gasto Favorito: " + gf);
        GastoFavoritoDAO gfd = new GastoFavoritoDAO();
        gfd.setDescripcion(gf.getDescripcion());
        gfd.setImporte(gf.getImporte());
        gfd.setCategoria(gf.getCategoria());
        gfd.save();
        return gfd.getId();
    }

    @Override
    public void eliminarGastoFavorito(Long id) {
        Log.d("XPDROID", "Eliminando Gasto Favorito: " + id);
        obtenerGastoFavoritoPorId(id).delete();
    }

    @Override
    public void actualizarGastoFavorito(GastoFavorito gf) {
        Log.d("XPDROID", "Actualizando Gasto Favorito: " + gf);
        GastoFavoritoDAO gfd = obtenerGastoFavoritoPorId(gf.getId());
        gfd.setDescripcion(gf.getDescripcion());
        gfd.setCategoria(gf.getCategoria());
        gfd.setImporte(gf.getImporte());
        gfd.save();
    }

    @Override
    public List<GastoFavorito> obtenerGastosFavoritos() {
        Log.d("XPDROID", "Obtener Gastos Favoritos");
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
        Log.d("XPDROID", "Gastos Favoritos: "+gastosFav);
        return gastosFav;
    }

    private GastoFavoritoDAO obtenerGastoFavoritoPorId(Long id) {
        Log.d("XPDROID", "Obtener Gastos Favoritos por id "+id);
        ArrayList<GastoFavoritoDAO> gastosFavLista = new Select().from(GastoFavoritoDAO.class).where("Id = ?", id).execute();
        return gastosFavLista.get(0);
    }



}
