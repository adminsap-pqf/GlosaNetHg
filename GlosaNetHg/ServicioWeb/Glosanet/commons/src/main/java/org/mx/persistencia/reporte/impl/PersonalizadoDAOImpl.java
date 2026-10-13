package org.mx.persistencia.reporte.impl;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.UsuarioBO;
import org.mx.modelo.reportes.bo.PReportesPersonalizadosBO;
import org.mx.modelo.reportes.bo.ReportePersonalizadoBO;
import org.mx.modelo.reportes.bo.TablaDataStageBO;
import org.mx.modelo.reportes.po.PReportesPersonalizadosPO;
import org.mx.modelo.reportes.po.TablaDataStagePO;
import org.mx.persistencia.reporte.PersonalizadoDAO;
import org.mx.persistencia.reporte.builder.BuilderPReportePersonalizado;
import org.mx.persistencia.reporte.builder.BuilderTablaDataStage;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Component
public class PersonalizadoDAOImpl implements PersonalizadoDAO {
	private final static Logger log = Logger.getLogger(PersonalizadoDAOImpl.class.getName());

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Override
    public List<TablaDataStageBO> findTablasDataStage(String idioma, String condicion)
            throws GlosaNetException {
        try {
            List<TablaDataStageBO> lista = new ArrayList<TablaDataStageBO>();

            String sql = " FROM TablaDataStagePO " + condicion + " Order by NivelPartida,Numero";

            Query query = em.createQuery(sql);
            List<TablaDataStagePO> list = query.getResultList();
            if (!list.isEmpty()) {
                for (TablaDataStagePO tablaPO : list) {
                    tablaPO.setIdioma(idioma);
                    lista.add(BuilderTablaDataStage.builderBO(tablaPO)); ////
                }
                return lista;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ReportePersonalizadoBO> getReports(int idAutor) {
        try {
            em.clear();

            String sql = "SELECT * FROM GN_Reportes_Personalizados WHERE FK01_Autor = " + idAutor;
            Query query = em.createNativeQuery(sql);
            List<ReportePersonalizadoBO> lista = new ArrayList<ReportePersonalizadoBO>();
            List<Object[]> list = query.getResultList();
            if (!list.isEmpty()) {
                for (Object[] item : list) {
                    ReportePersonalizadoBO bo = new ReportePersonalizadoBO();
                    bo.setIdReportePersonalizado((Integer) item[0]);
                    bo.setNombre((String) item[1]);
                    bo.setDescripcion((String) item[2]);
                    bo.setFechaCreacion((Date) item[3]);
                    UsuarioBO usuario = new UsuarioBO();
                    usuario.setIdUsuario((Integer) item[4]);
                    bo.setAutor(usuario);

                    lista.add(bo);
                }
                return lista;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ReportePersonalizadoBO getReportById(int id) {
        try {
            em.clear();

            String sql = "SELECT * FROM GN_Reportes_Personalizados WHERE PK_ReportePersonalizado = :id";
            Query query = em.createNativeQuery(sql);
            query.setParameter("id", id);
            ReportePersonalizadoBO bo = new ReportePersonalizadoBO();
            List<Object[]> list = query.getResultList();
            if (!list.isEmpty()) {
                for (Object[] item : list) {
                    bo.setIdReportePersonalizado((Integer) item[0]);
                    bo.setNombre((String) item[1]);
                    bo.setDescripcion((String) item[2]);
                    bo.setFechaCreacion((Date) item[3]);
                    UsuarioBO usuario = new UsuarioBO();
                    usuario.setIdUsuario((Integer) item[4]);
                    bo.setAutor(usuario);

                }
                return bo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PReportesPersonalizadosBO> getPReporstById(int id) {
        try {
            em.clear();

            String sql = "FROM PReportesPersonalizadosPO WHERE reportePersonalizado.idReportePersonalizado = :id";
            Query query = em.createQuery(sql);
            query.setParameter("id", id);
            List<PReportesPersonalizadosBO> lista = new ArrayList<PReportesPersonalizadosBO>();
            List<PReportesPersonalizadosPO> list = query.getResultList();
            if (!list.isEmpty()) {
                for (PReportesPersonalizadosPO item : list) {
                    lista.add(BuilderPReportePersonalizado.builderBO(item));
                }
                return lista;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer saveReport(ReportePersonalizadoBO reportePersonalizado) throws GlosaNetException {
        try {
            em.clear();

            String sql = "INSERT INTO GN_Reportes_Personalizados (Nombre, Descripcion, Fecha_Creacion, FK01_Autor) "
                    + " Values (:nombre, :descripcion, :fechaCreacion, :autor)";

            Query query = em.createNativeQuery(sql);
            query.setParameter("nombre", reportePersonalizado.getNombre());
            query.setParameter("descripcion", reportePersonalizado.getDescripcion());
            query.setParameter("fechaCreacion", reportePersonalizado.getFechaCreacion());
            query.setParameter("autor", reportePersonalizado.getAutor().getIdUsuario());
            query.executeUpdate();

            //SELECT LAST_INSERT_ID();
            Query id = em.createNativeQuery("SELECT LAST_INSERT_ID()");
            Long l = ((BigInteger) id.getSingleResult()).longValue();
            Integer r = l != null ? l.intValue() : 0;

            return r;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Integer editReport(ReportePersonalizadoBO reportePersonalizado) throws GlosaNetException {
        try {
            em.clear();

            String sql = "UPDATE GN_Reportes_Personalizados SET Nombre = :nombre, Descripcion = :descripcion "
                    + " WHERE PK_ReportePersonalizado = :id";

            Query query = em.createNativeQuery(sql);
            query.setParameter("nombre", reportePersonalizado.getNombre());
            query.setParameter("descripcion", reportePersonalizado.getDescripcion());
            query.setParameter("id", reportePersonalizado.getIdReportePersonalizado());
            query.executeUpdate();

            return reportePersonalizado.getIdReportePersonalizado();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean deleteReport(ReportePersonalizadoBO reportePersonalizado) throws GlosaNetException {
        try {
            em.clear();

            String sql = "DELETE FROM GN_Reportes_Personalizados WHERE PK_ReportePersonalizado = :id";

            Query query = em.createNativeQuery(sql);
            query.setParameter("id", reportePersonalizado.getIdReportePersonalizado());
            query.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Integer savePReport(PReportesPersonalizadosBO pReportePersonalizado) throws GlosaNetException {
        try {
            em.clear();

            String sql = "INSERT INTO GN_PReportes_Personalizados (FK01_Columna_TablaDataStage, FK02_Reporte_Personalizado) "
                    + " Values (:columnaTabla, :reportePersonalizado)";

            Query query = em.createNativeQuery(sql);
            query.setParameter("columnaTabla", pReportePersonalizado.getCampoTabla().getIdCampo());
            query.setParameter("reportePersonalizado", pReportePersonalizado.getReportePersonalizado().getIdReportePersonalizado());
            query.executeUpdate();
            //SELECT LAST_INSERT_ID();
            Query id = em.createNativeQuery("SELECT LAST_INSERT_ID()");
            Long l = ((BigInteger) id.getSingleResult()).longValue();
            Integer r = l != null ? l.intValue() : 0;

            return r;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean deletePReports(ReportePersonalizadoBO reportePersonalizado) throws GlosaNetException {
        try {
            em.clear();

            String sql = "DELETE FROM GN_PReportes_Personalizados WHERE FK02_Reporte_Personalizado = :id";

            Query query = em.createNativeQuery(sql);
            query.setParameter("id", reportePersonalizado.getIdReportePersonalizado());
            query.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<String[]> findResultadosDeReportePersonalizado(String sql)
            throws GlosaNetException {
        try {
            Query query = em.createNativeQuery(sql);

            List<Object[]> result = (List<Object[]>) query.getResultList();
            List<String[]> list = new ArrayList<String[]>();

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
                            String_Array[i] = r[i].toString();
                        }

                        list.add(String_Array);
                    }
                }
            }

            return list;
        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
            e.getStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
     * @see mx.com.glosanet.persistencia.dao.reportes.TablaDataStageDAO#getCantidadResultadosDeReportePersonalizado(java.lang.String)
     */
    @Override
    public int getCantidadResultadosDeReportePersonalizado(String sql)
            throws GlosaNetException {
        try {
            //			em = getEntityManager();

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


    @Override
    public TablaDataStageBO save(TablaDataStageBO entity) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public void delete(TablaDataStageBO entity) {
        // TODO Auto-generated method stub

    }


    @Override
    public TablaDataStageBO update(TablaDataStageBO entity) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public TablaDataStageBO findById(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public List<TablaDataStageBO> findByProperty(String propertyName,
                                                 Object value, int... rowStartIdxAndCount) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public List<TablaDataStageBO> findAll(int... rowStartIdxAndCount) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public boolean loadDataLocal(File file) {
        // TODO Auto-generated method stub
        return false;
    }
}
