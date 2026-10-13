package org.mx.persistencia.comun.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.mx.modelo.comun.bo.AgenteAduanalBO;
import org.mx.modelo.comun.po.AgenteAduanalPO;
import org.mx.persistencia.comun.AgenteAduanalDAO;
import org.mx.persistencia.comun.builder.BuilderAgenteAduanal;
import org.springframework.stereotype.Component;

@Component
public class AgenteAduanalDAOImpl implements AgenteAduanalDAO {

//	public AgenteAduanalDAOImpl() {
//		super(AgenteAduanalPO.class);
//		// TODO Auto-generated constructor stub
//	}
	
	@PersistenceContext
	private EntityManager em;

	/* (non-Javadoc)
	 * @see mx.com.glosanet.persistencia.dao.comun.AgenteAduanalDAO#modificar(mx.com.glosanet.modelo.bo.AgenteAduanalBO)
	 */
	@Override
	public boolean modificarAgente(AgenteAduanalBO bo){
		//super.update(bo);////
		return true;
	}

	/* (non-Javadoc)
	 * @see mx.com.glosanet.persistencia.dao.comun.DataBaseDAOImpl#builderBO(java.lang.Object)
	 */
//	@Override
//	protected AgenteAduanalBO builderBO(AgenteAduanalPO po) {
//		return BuilderAgenteAduanal.builderBO(po);
//	}

	/* (non-Javadoc)
	 * @see mx.com.glosanet.persistencia.dao.comun.DataBaseDAOImpl#builderPO(java.lang.Object)
	 */
//	@Override
//	protected AgenteAduanalPO builderPO(AgenteAduanalBO bo) {
//		return BuilderAgenteAduanal.builderPO(bo);
//	}

	/* (non-Javadoc)
	 * @see mx.com.glosanet.persistencia.dao.comun.DataBaseDAO#loadDataLocal(java.io.File)
	 */


	/* (non-Javadoc)
	 * @see mx.com.glosanet.persistencia.dao.comun.AgenteAduanalDAO#obtenerCantidadAgentesSinInformacion()
	 */
	@Override
	public int obtenerCantidadAgentesSinInformacion(int idCliente) {
		try{
			//em = getEntityManager();
			em.clear();
			
			String sql=""
					+ " \n SELECT *"
					+ " \n FROM  GN_Agente_Aduanal"
					+ " \n where  Nombre is null and FK02_Cliente = :cliente";
			
			
			Query query = em.createNativeQuery(sql);
			query.setParameter("cliente",idCliente);
			return query.getResultList().size();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<AgenteAduanalBO> obtenerAgentesYAgencias(int idCliente, boolean informacion) {
		try{ 
			
			String sql;
//			if (!em.getTransaction().isActive())
//			{
//				//em.getTransaction().begin();
//			}
			em.clear();
			//em = getEntityManager(); esto ocaciona que aveces lleguen valores nulos
			
			
			//if(informacion){
			//	sql= "FROM AgenteAduanalPO WHERE fkcliente = :cliente and nombre is not null ";
			//}else{
			//	sql= "FROM AgenteAduanalPO WHERE fkcliente = :cliente and nombre is null";
				
			//}
			sql= "FROM AgenteAduanalPO WHERE fkcliente = :cliente";
			
			Query query = em.createQuery(sql);
			query.setParameter("cliente", idCliente);
			List<AgenteAduanalPO> listapo=query.getResultList();
			
			
			List<AgenteAduanalBO> listabo = new ArrayList<AgenteAduanalBO>();
			for(AgenteAduanalPO agente: listapo){
				listabo.add(BuilderAgenteAduanal.builderBO(agente));/////
			}

			
			return listabo;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean actualizarAgente(AgenteAduanalBO bo) {
		try {
			//em = getEntityManager();
//			if(!em.getTransaction().isActive())
//			{
//				em.getTransaction().begin();
//			}
			
			em.clear();
			
			StringBuilder sbQuery = new StringBuilder("UPDATE GN_Agente_Aduanal SET Nombre = :nombre, Apellido = :apellido,");
			sbQuery.append("Rfc = :rfc, Telefono = :telefono, Correo_Electronico = :correo, FK01_Direccion = :idDireccion \n");
			sbQuery.append("WHERE PK_Agente = :id \n");
			Query query = em.createNativeQuery(sbQuery.toString());
			query.setParameter("nombre", bo.getNombre());
			query.setParameter("apellido", bo.getApellido());
			query.setParameter("rfc", bo.getRfc());
			query.setParameter("telefono", bo.getTelefono());
			query.setParameter("correo", bo.getCorreoElectronico());
			query.setParameter("idDireccion", bo.getDireccion().getIdDireccion());
			query.setParameter("id", bo.getIdAgente());
			query.executeUpdate();

			//em.getTransaction().commit();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
}
