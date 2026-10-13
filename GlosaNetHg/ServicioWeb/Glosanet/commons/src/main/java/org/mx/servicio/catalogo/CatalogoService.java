package org.mx.servicio.catalogo;

import org.mx.modelo.catalogo.bo.CatalogoBO;

import java.util.List;

public interface CatalogoService {

    /**
     * Devuelve la lista de contenidos de catálogo
     * @param catalogoIds Arreglo de ids de catálogo
     * @return Lista de contenidos de catálogos seleccionados
     */
    public List<CatalogoBO> listarCatalogos(int[] catalogoIds);
}
