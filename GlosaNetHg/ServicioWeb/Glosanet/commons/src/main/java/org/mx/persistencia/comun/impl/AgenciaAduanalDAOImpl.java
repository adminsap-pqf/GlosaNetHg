package org.mx.persistencia.comun.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.mx.modelo.comun.bo.AgenciaAduanalBO;
import org.mx.persistencia.comun.AgenciaAduanalDAO;
import org.springframework.stereotype.Component;

@Component
public class AgenciaAduanalDAOImpl implements AgenciaAduanalDAO {

	@PersistenceContext
	private EntityManager em;
	
//	public AgenciaAduanalDAOImpl() {
//		super(AgenciaAduanalPO.class);
//		// TODO Auto-generated constructor stub
//	}

	@Override
	public boolean insertarAgenciaAduanal(AgenciaAduanalBO bo) {
		try{
//			if(!em.getTransaction().isActive())
//			{
//				em.getTransaction().begin();
//			}
			em.clear();
			String sbQuery = "insert into GN_Agencia_Aduanal (Rfc,Razon_Social,Nombre,Telefono,Correo_Electronico,FK01_Direccion,FK02_Agente) values (:rfc, :razon, :nombre, :telefono, :correo, :iddireccion, :idagente)";
			Query query = em.createNativeQuery(sbQuery);
			query.setParameter("rfc",bo.getRfc());
			query.setParameter("razon",bo.getRazonSocial());
			query.setParameter("nombre",bo.getNombre());
			query.setParameter("telefono",bo.getTelefono());
			query.setParameter("correo",bo.getCorreoElectronico());
			query.setParameter("iddireccion",bo.getDireccion().getIdDireccion());
			query.setParameter("idagente",bo.getAgente().getIdAgente());
			query.executeUpdate();
			//em.getTransaction().commit();
			return true;
			
		}catch(Exception e){
			e.printStackTrace();
			return false;
			
		}
	}

//	@Override
//	protected AgenciaAduanalBO builderBO(AgenciaAduanalPO po) {
//		return BuilderAgencia.builderBO(po);
//	}
//
//	@Override
//	protected AgenciaAduanalPO builderPO(AgenciaAduanalBO bo) {
//		return BuilderAgencia.builderPO(bo);
//	}

	@Override
	public boolean actualizarAgenciaAduanal(AgenciaAduanalBO bo) {
		try {
			em.clear();
//			if(!em.getTransaction().isActive())
//			{
//				em.getTransaction().begin();
//			}
			StringBuilder sbQuery = new StringBuilder("UPDATE GN_Agencia_Aduanal SET Rfc = :rfc, Razon_Social = :razon,");
			sbQuery.append("Nombre = :nombre, Telefono = :telefono, Correo_Electronico = :correo \n");
			sbQuery.append("WHERE PK_Agencia = :id \n");
			Query query = em.createNativeQuery(sbQuery.toString());
			query.setParameter("rfc", bo.getRfc());
			query.setParameter("razon", bo.getRazonSocial());
			query.setParameter("nombre", bo.getNombre());
			query.setParameter("telefono", bo.getTelefono());
			query.setParameter("correo", bo.getCorreoElectronico());
			query.setParameter("id", bo.getIdAgenciaAduanal());
			query.executeUpdate();

			//em.getTransaction().commit();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
}
