package org.mx.persistencia.reporte.impl;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.reportes.bo.TablaDataStageBO;
import org.mx.modelo.reportes.po.TablaDataStagePO;
import org.mx.persistencia.reporte.TablaDataStageDAO;
import org.mx.persistencia.reporte.builder.BuilderTablaDataStage;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
public class TablaDataStageDAOImpl implements TablaDataStageDAO {
    private final static Logger log = Logger.getLogger(TablaDataStageDAOImpl.class.getName());

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Override
    public List<TablaDataStageBO> findTablasDataStage(String idioma, String condicion)
            throws GlosaNetException {
        try {
            em.clear();

            List<TablaDataStageBO> lista = new ArrayList<TablaDataStageBO>();
            String sql = " FROM TablaDataStagePO " + condicion + " Order by NivelPartida,Numero";
            Query query = em.createQuery(sql);
            List<TablaDataStagePO> list = query.getResultList();

            if (!list.isEmpty()) {
                for (TablaDataStagePO tablaPO : list) {
                    tablaPO.setIdioma(idioma);
                    lista.add(BuilderTablaDataStage.builderBO(tablaPO));
                }
                return lista;
            }

        } catch (Exception e) {
            //logger.error(e.getMessage());
        }
        return null;
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<String[]> findResultadosDeReportePersonalizado(String sql)
            throws GlosaNetException {
        try {
            em.clear();

            Query query = em.createNativeQuery(sql);

            List<Object[]> result = (List<Object[]>) query.getResultList();
            List<String[]> list = new ArrayList<String[]>();
            System.out.println("termino consulta: " + result.size());
            if (!result.isEmpty()) {
                Object firstResult = result.get(0);
                if (firstResult instanceof String) {
                    for (int i = 0; i < result.size(); i++) {
                        firstResult = result.get(i);
                        String[] String_Array = new String[1];
                        String_Array[0] = firstResult.toString();

                        list.add(String_Array);
                    }
                } else {
                    for (Object[] r : result) {
                        String[] String_Array = new String[r.length];

                        for (int i = 0; i < String_Array.length; i++) {
                            if (r[i] != null) {
                                String_Array[i] = r[i].toString();
                            } else {
                                String_Array[i] = " ";
                            }
                        }

                        list.add(String_Array);
                    }
                }
            }

            return list;
        } catch (Exception e) {
            e.printStackTrace();
            //logger.error(e.getMessage());
            e.getLocalizedMessage();
            e.getStackTrace();
        }

        return null;
    }

    @Override
    public int getCantidadResultadosDeReportePersonalizado(String sql)
            throws GlosaNetException {
        try {
            em.clear();

            Query query = em.createNativeQuery(sql);
            Object r = query.getSingleResult();

            if (r != null && !r.toString().equals("")) {
                return Integer.parseInt(r.toString());
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

}
