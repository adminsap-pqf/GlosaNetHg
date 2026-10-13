package org.mx.persistencia.comun.impl;

import java.math.BigInteger;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.mx.modelo.comun.bo.DireccionFiscalBO;
import org.mx.persistencia.comun.DireccionFiscalDAO;
import org.springframework.stereotype.Component;

@Component
public class DireccionFiscalDAOImpl implements DireccionFiscalDAO {
	private final static Logger log = Logger.getLogger(DireccionFiscalDAOImpl.class.getName());

	@PersistenceContext
	private EntityManager em;

	@Override
	public DireccionFiscalBO insertarDireccion(DireccionFiscalBO bo) {
		try{
			em.clear();
			
			String sbQuery = "insert into GN_Direccion_Fiscal (Calle,No_Exterior,No_Interior,Colonia,Municipio,Estado,Pais,Codigo_Postal) values (:calle, :noext, :noint, :colonia, :municipio, :estado, :pais, :codigo)";
			Query query = em.createNativeQuery(sbQuery);
			query.setParameter("calle",bo.getCalle());
			query.setParameter("noext",bo.getNoExterior());
			query.setParameter("noint",bo.getNoInterior());
			query.setParameter("colonia",bo.getColonia());
			query.setParameter("municipio",bo.getMunicipio());
			query.setParameter("estado",bo.getEstado());
			query.setParameter("pais",bo.getPais());
			query.setParameter("codigo",bo.getCodigoPostal());
			query.executeUpdate();
			Query id = em.createNativeQuery("SELECT LAST_INSERT_ID()");
			Long l = ((BigInteger) id.getSingleResult()).longValue() ;
			Integer r = l != null ? l.intValue() : 0;
			//em.getTransaction().commit();
			DireccionFiscalBO insertado= new DireccionFiscalBO();
			insertado.setIdDireccion(r);
			log.info("@DireccionFiscalDaoImpl -> insertarDireccion() -> Id de la dirección insertado: " + r);
			return insertado;
		}catch(Exception e){
			log.severe("@DireccionFiscalDaoImpl -> insertarDireccion() -> Error al insertar la dirección. " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean modificarDireccion(DireccionFiscalBO bo) {
		
		try {
			em.clear();

			StringBuilder sbQuery = new StringBuilder("UPDATE GN_Direccion_Fiscal SET Calle = :calle, No_exterior = :exterior,");
			sbQuery.append("No_interior = :interior, Colonia = :colonia, Municipio = :municipio, \n");
			sbQuery.append("Estado = :estado, Pais = :pais, Codigo_Postal = :codigo \n");
			sbQuery.append("WHERE PK_Direccion = :id \n");

			Query query = em.createNativeQuery(sbQuery.toString());
			query.setParameter("calle", bo.getCalle());
			query.setParameter("exterior", bo.getNoExterior());
			query.setParameter("interior", bo.getNoInterior());
			query.setParameter("colonia", bo.getColonia());
			query.setParameter("municipio", bo.getMunicipio());
			query.setParameter("estado", bo.getEstado());
			query.setParameter("pais", bo.getPais());
			query.setParameter("codigo", bo.getCodigoPostal());
			query.setParameter("id", bo.getIdDireccion());
			query.executeUpdate();

			log.info("@DireccionFiscalDaoImpl -> modificarDireccion() -> Id de dirección modificada: " + bo.getIdDireccion());
			return true;
		} catch (Exception e) {
			log.severe("@DireccionFiscalDaoImpl -> modificarDireccion() -> Error al modificar la dirección. " + e.getMessage());
			e.printStackTrace();
		}
		return true;
	}
}
