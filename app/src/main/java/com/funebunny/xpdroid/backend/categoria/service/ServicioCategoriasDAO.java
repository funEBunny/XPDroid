package com.funebunny.xpdroid.backend.categoria.service;

import android.util.Log;

import com.activeandroid.query.Select;
import com.funebunny.xpdroid.backend.categoria.dao.CategoriaDAO;
import com.funebunny.xpdroid.business.categoria.model.Categoria;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by schmidt0 on 11/24/2015.
 */
public class ServicioCategoriasDAO implements IServicioCategoriasDAO
{

    @Override
    public Long insertrCategoria(Categoria categoria) {

        CategoriaDAO catDao = new CategoriaDAO();
        catDao.setNombre(categoria.getnombre());
        catDao.save();
        return catDao.getId();

    }

    @Override
    public List<Categoria> obtenerCategorias() {
        Log.d("XPDROID", "Obteniendo Categorias");
        List<Categoria> categorias = new ArrayList<Categoria>();
        ArrayList<CategoriaDAO> categoriaLista = new Select().from(CategoriaDAO.class).execute();
        Log.d("XPDROID", "Categoria DAO: " + categoriaLista);
        for (int i = 0; i < categoriaLista.size(); i++) {
            Categoria categoria = new Categoria();
            categoria.setnombre(categoriaLista.get(i).getNombre());
            categoria.setId(categoriaLista.get(i).getId());
            categorias.add(categoria);
        }

            return categorias;
    }
}
