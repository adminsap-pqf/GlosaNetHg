package org.mx.persistencia.catalogo.impl;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.mx.modelo.catalogo.bo.CatalogoBO;
import org.mx.modelo.catalogo.bo.CatalogoContenidoBO;
import org.mx.modelo.catalogo.po.CatalogoContenidoPO;
import org.mx.modelo.catalogo.po.CatalogoPO;
import org.mx.persistencia.catalogo.CatalogoDAO;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@Transactional
public class CatalogoDAOImpl implements CatalogoDAO {
    private static final Logger log = Logger.getLogger(CatalogoDAOImpl.class.getName());

    @PersistenceContext
    private EntityManager em;

    /**
     * Devuelve la lista de contenidos de catálogo
     *
     * @param catalogoIds Arreglo de ids de catálogo
     * @return Lista de contenidos de catálogos seleccionados
     */
    @Override
    public List<CatalogoBO> listarCatalogos(int[] catalogoIds) {
        Session session = em.unwrap(Session.class);
        String ids = IntStream.of(catalogoIds)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining(", "));

        NativeQuery query = session.createNativeQuery("SELECT * FROM GN_Catalogo WHERE PK_Catalogo in (" + ids + ")", CatalogoPO.class);

        List<CatalogoPO> rsPO = query.list();
        List<CatalogoBO> list = new LinkedList<>();
        rsPO.forEach(catalog -> list.add(build(catalog)));

        return list;
    }

    private CatalogoBO build(CatalogoPO po) {
        return new CatalogoBO(po.getId(), po.getNombreCatalogo(), po.getDescripcion(), build(po.getContenido()));
    }

    private List<CatalogoContenidoBO> build(List<CatalogoContenidoPO> po) {
        List<CatalogoContenidoBO> list = new ArrayList<>();
        po.forEach(content -> list.add(
                new CatalogoContenidoBO(
                        content.getId(),
                        content.getFK_Catalogo(),
                        content.getPosicion(),
                        content.getClave(),
                        content.getClaveReporte(),
                        content.getNombre(),
                        content.getDescripcionBreve(),
                        content.getDescripcionExtensa(),
                        content.getTooltip(),
                        content.isPredeterminado())
                )
        );
        return list;
    }
}
