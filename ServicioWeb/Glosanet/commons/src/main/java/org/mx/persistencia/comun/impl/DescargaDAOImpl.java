package org.mx.persistencia.comun.impl;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.DescargaBO;
import org.mx.modelo.comun.po.DescargaPO;
import org.mx.persistencia.comun.DescargaDAO;
import org.mx.persistencia.comun.builder.BuilderDescargas;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component("descargaDAO")
@Transactional
public class DescargaDAOImpl implements DescargaDAO {

    private final static Logger LOGGER = Logger.getLogger(DescargaDAOImpl.class.getName());

    @PersistenceContext
    private EntityManager em;

    @Override
    public Integer insertPeticionDescarga(DescargaBO peticion) throws GlosaNetException {
        // TODO Auto-generated method stub
        try {

            String sql = "insert into GN_Descargas (SqlConsulta, FK01_Usuario, Encabezados, Tipo, LocacionTemp ) VALUES(:sqlConsulta,:idUsuario, :encabezados, :tipo, :locacion)";
            LOGGER.info("@insertPeticionDescarga: peticion.getEncabezados() -> " + peticion.getEncabezados());
            Query query = em.createNativeQuery(sql);
            query.setParameter("sqlConsulta", peticion.getSqlConsulta());
            query.setParameter("idUsuario", peticion.getIdUsuario());
            query.setParameter("encabezados", peticion.getEncabezados());
            query.setParameter("tipo", peticion.getTipo());
            query.setParameter("locacion", peticion.getLocacion());
            query.executeUpdate();
            //SELECT LAST_INSERT_ID();
            Query id = em.createNativeQuery("SELECT LAST_INSERT_ID()");
            Long l = ((BigInteger) id.getSingleResult()).longValue();
            Integer r = l != null ? l.intValue() : 0;

            return r;
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());

        }
        return 0;
    }

    @Override
    public String getLigasDescargasReportes(Integer idPeticion)
            throws GlosaNetException {

		LOGGER.info("@getLigasDescargasReportes: Inicio");

		String result = null;
        try {
            em.clear();
            String sql = "SELECT LocacionTemp FROM GN_Descargas WHERE PK_Descargas = " + idPeticion.toString();
            Query query = em.createNativeQuery(sql);
            Object list = query.getSingleResult();
            if (list != null)
                result = list.toString();

        } catch (Exception e) {
			e.printStackTrace();
			LOGGER.severe("@descargarLogo: exception -> " + e.toString());
			throw new GlosaNetException(e);
        }

		return result;
    }

    @Override
    public String getDataStageRespuesta(Integer idPeticion)
            throws GlosaNetException {

		LOGGER.info("@getDataStageRespuesta: Inicio");

		String result = null;
        try {
            em.clear();
            String sql = "SELECT CONCAT(Descargado ,'|',Error) FROM GN_Descargas WHERE PK_Descargas = "
					+ idPeticion.toString();
            Query query = em.createNativeQuery(sql);
            Object list = query.getSingleResult();
            if (list != null)
                result = list.toString();

        } catch (Exception e) {
			e.printStackTrace();
			LOGGER.severe("@getDataStageRespuesta: exception -> " + e.toString());
			throw new GlosaNetException(e);
        }

        return result;
    }

    @Override
    public String getEncabezadosReportes(Integer idPeticion)
            throws GlosaNetException {
        try {
            //em = getEntityManager();
            em.clear();

            String sql = "SELECT Encabezados FROM GN_Descargas WHERE PK_Descargas = " + idPeticion.toString();

            Query query = em.createNativeQuery(sql);

            Object list = query.getSingleResult();
            String r = list.toString();

            return r;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    //	@Override
    //	protected DescargaBO builderBO(DescargaPO po) {
    //		return BuilderDescargas.builderBO(po);
    //	}
    //
    //
    //	@Override
    //	protected DescargaPO builderPO(DescargaBO bo) {
    //		return null;
    //	}


    /* (non-Javadoc)
     * @see mx.com.glosanet.persistencia.dao.descargas.DescargaDAO#findConsultasNoHechas()
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<String[]> findOperacionesNoHechas() throws GlosaNetException {
        try {
            //em = getEntityManager();
            em.clear();

            String sql = "SELECT PK_Descargas, FK01_Usuario, Encabezados, SqlConsulta FROM GN_Descargas WHERE LocacionTemp IS NULL and Tipo = 'Descarga'";

            Query query = em.createNativeQuery(sql);


            List<Object[]> re = query.getResultList();
            List<String[]> r = new ArrayList<String[]>();
            for (Object[] o : re) {
                String sr[] = new String[4];
                sr[0] = o[0].toString();
                sr[1] = o[1].toString();
                sr[2] = o[2].toString();
                sr[3] = o[3].toString();
                r.add(sr);
            }
            return r;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /* (non-Javadoc)
     * @see mx.com.glosanet.persistencia.dao.descargas.DescargaDAO#updatePeticionDescarga(java.lang.String, java.lang.String)
     */
    @Override
    public Boolean updatePeticionDescarga(String idPeticion, String locacionTemp)
            throws GlosaNetException {
        try {
            em.clear();

            String sql = "UPDATE GN_Descargas SET LocacionTemp = :loca  WHERE PK_Descargas = " + idPeticion;
            Query query = em.createNativeQuery(sql);
            query.setParameter("loca", locacionTemp);
            query.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /* (non-Javadoc)
     * @see mx.com.glosanet.persistencia.dao.descargas.DescargaDAO#findOperacionesPorTipo(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<DescargaBO> findOperacionesPorTipo(String tipo, boolean activo)
            throws GlosaNetException {
        try {
            StringBuilder sbQuery = new StringBuilder("FROM DescargaPO po WHERE po.tipo = :tipo AND po.descargado = :activo");
            Query query = em.createQuery(sbQuery.toString());
            query.setParameter("tipo", tipo);
            query.setParameter("activo", activo);
            List<DescargaBO> lista = new ArrayList<DescargaBO>();
            List<DescargaPO> results = query.getResultList();
            for (DescargaPO descargaPO : results) {
                lista.add(BuilderDescargas.builderBO(descargaPO));
            }
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /* (non-Javadoc)
     * @see mx.com.glosanet.persistencia.dao.descargas.DescargaDAO#updateEstadoActivo(mx.com.glosanet.modelo.comun.DescargaBO)
     */
    @Override
    public boolean updateEstadoActivo(DescargaBO operacion)
            throws GlosaNetException {
        try {
//			if (!em.getTransaction().isActive()){
//				em.getTransaction().begin();
//			}
            em.clear();

            StringBuilder sbQuery = new StringBuilder("UPDATE GN_Descargas SET Descargado = :activo WHERE PK_Descargas = :idOperacion");
            Query query = em.createNativeQuery(sbQuery.toString());
            query.setParameter("activo", operacion.getDescargado());
            query.setParameter("idOperacion", operacion.getIdDescarga());
            query.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * @see mx.com.glosanet.persistencia.dao.descargas.DescargaDAO#findOperacionesNoHechas()
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<String[]> findReportesNoHechas() throws GlosaNetException {
        try {
            em.clear();

            String sql = "SELECT PK_Descargas, FK01_Usuario, Encabezados, SqlConsulta FROM GN_Descargas WHERE LocacionTemp IS NULL and Tipo = 'DICIPA'";

            Query query = em.createNativeQuery(sql);


            List<Object[]> re = query.getResultList();
            List<String[]> r = new ArrayList<String[]>();
            for (Object[] o : re) {
                String sr[] = new String[4];
                sr[0] = o[0].toString();
                sr[1] = o[1].toString();
                sr[2] = o[2].toString();
                sr[3] = o[3].toString();
                r.add(sr);
            }
            return r;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateError(int idDescarga, String error)
            throws GlosaNetException {
        try {
            em.clear();

            StringBuilder sbQuery = new StringBuilder("UPDATE GN_Descargas SET Error = :error WHERE PK_Descargas = :idOperacion");
            Query query = em.createNativeQuery(sbQuery.toString());
            query.setParameter("error", error);
            query.setParameter("idOperacion", idDescarga);
            query.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public DescargaBO findById(Integer idDescarga) throws GlosaNetException {
        LOGGER.info("@findById: Inicio");

        try {
            em.clear();
            StringBuilder sbQuery = new StringBuilder("FROM DescargaPO po WHERE po.idDescarga = :idDescarga");
            Query query = em.createQuery(sbQuery.toString());
            query.setParameter("idDescarga", idDescarga);
            DescargaPO descargaPO = (DescargaPO) query.getSingleResult();
            if (descargaPO != null) {
                return BuilderDescargas.builderBO(descargaPO);
            }

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.severe("@findById: exception -> " + e.toString());
            throw new GlosaNetException(e);
        }

        return null;
    }
}
