package org.mx.persistencia.comun.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.mx.modelo.comun.bo.ClientesBO;
import org.mx.persistencia.comun.ClienteDAO;
import org.springframework.stereotype.Component;

@Component
public class ClienteDAOImpl implements ClienteDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public boolean updateCliente(ClientesBO cliente) throws Exception {
		try{
			String sql;

			em.clear();

			

			sql = "UPDATE GN_Cliente SET Representante = :representante , Telefono = :telefono, RazonSocial = :razonSocial, "
					+ "RegimenFiscal = :regimenFiscal, RFC = :rfc, UsoCFDI = :usoCFDI, claveProducto = :claveproducto WHERE PK_Cliente = :idCliente";

			Query query = em.createNativeQuery(sql);
			query.setParameter("representante", cliente.getRepresentante());
			query.setParameter("telefono", cliente.getTelefono());
			query.setParameter("razonSocial", cliente.getRazonSocial());
			query.setParameter("regimenFiscal", cliente.getRegimenFiscal());
			query.setParameter("rfc", cliente.getRfc());
			query.setParameter("usoCFDI", cliente.getUsoCFDI());
			query.setParameter("claveproducto", cliente.getClaveProducto());
			query.setParameter("idCliente", cliente.getIdCliente());

			query.executeUpdate();

			return true;

		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
	}

}
