package org.mx.servicio.catalogo.impl;

import org.mx.modelo.catalogo.bo.CatalogoBO;
import org.mx.persistencia.catalogo.CatalogoDAO;
import org.mx.servicio.catalogo.CatalogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service("catalogoService")
public class CatalogoServiceImpl implements CatalogoService  {
    private final static Logger log = Logger.getLogger(CatalogoServiceImpl.class.getName());

    @Autowired
    private CatalogoDAO dao;

    /**
     * Devuelve la lista de contenidos de catálogo
     *
     * @param catalogoIds Arreglo de ids de catálogo
     * @return Lista de contenidos de catálogos seleccionados
     */
    @Override
    public List<CatalogoBO> listarCatalogos(int[] catalogoIds) {
        return dao.listarCatalogos(catalogoIds);
    }
}
