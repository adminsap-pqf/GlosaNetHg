package org.mx.persistencia.facturas.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.mx.modelo.comun.bo.Parametros;
import org.mx.modelo.datastage.bo.FacturasBO;
import org.mx.modelo.datastage.po.FacturasPO;
import org.mx.modelo.util.UtilFechas;
import org.mx.modelo.util.archivos.Archivos;
import org.mx.persistencia.facturas.FacturasDAO;
import org.mx.persistencia.facturas.builder.BuilderFacturas;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class FacturasDAOImpl implements FacturasDAO{
	private static final Logger log = Logger.getLogger(FacturasDAOImpl.class.getName());
	UtilFechas u;
	
	@PersistenceContext
	private EntityManager em;

	//@Override
	protected FacturasBO builderBO(FacturasPO po) {
		return BuilderFacturas.builderBO(po);
	}

	//@Override
	protected FacturasPO builderPO(FacturasBO bo) {
		return BuilderFacturas.builderPO(bo);
	}

	@Override
	public boolean guardar(List<FacturasBO> list){
		
		for (FacturasBO entity : list) {
			try {
				save(entity);
				log.info("La lista de facturas se ha guardado correctamente. ");
			} catch (Exception e) {
				log.severe("Error al guardar la lista de facturas. " + e.getMessage());
				e.printStackTrace();
			}
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see mx.com.glosanet.persistencia.dao.facturas.FacturasDAO#findFactuasXDataStage(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<FacturasBO> findFactuasXDataStage(String folio) {
		try {
			if (!em.getTransaction().isActive())
				em.getTransaction().begin();

			List<FacturasBO> lista = new ArrayList<FacturasBO>();
			StringBuilder sbQuery = new StringBuilder(" FROM FacturasPO AS F \n");
			sbQuery.append("WHERE F.dataStage.folio = ?");

			Query query = em.createQuery(sbQuery.toString());
			query.setParameter(1, folio);
			List<FacturasPO> list = query.getResultList();

			if (!list.isEmpty()){
				for (FacturasPO facturasPO : list) {
					lista.add(builderBO(facturasPO));
				}
				return lista;
			}
		} catch (Exception e) {
			log.severe("Error al buscar facturas por DataStage. " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	
	/* (non-Javadoc)
	 * @see mx.com.glosanet.persistencia.dao.facturas.FacturasDAO#findFacturas(mx.com.glosanet.modelo.comun.Parametros)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<FacturasBO> findFacturas(Parametros parametros) {
		try {
			u = new UtilFechas();
			if (!em.getTransaction().isActive()){
				em.getTransaction().begin();
			}
			List<FacturasBO> lista = new ArrayList<FacturasBO>();
			String tipoPedimento = " AND TipoPedimento = 1 ";
			
			if(!parametros.getImportacionDefinitiva()){
				tipoPedimento = " AND TipoPedimento <> 1 ";
			}
			
			//Datos_Generales_501 WHERE TipoOperacion 		1 = Importacion 	2 = Exportacion
			//Datos_Generales_501 WHERE TipoPedimento 		1 = Definitiva 		1 <> Temporal? 
			
			String sql = " \n SELECT F.* " +
						 " \n FROM Facturas_505 F " +
						 " \n INNER JOIN (SELECT * FROM GN_DataStage WHERE FK01_Cliente = " + parametros.getUsuario().getCliente().getIdCliente() + ") DS ON DS.PK_DataStage = F.FK_DataStage" +
						 " \n INNER JOIN (SELECT * FROM Datos_Generales_501 WHERE TipoOperacion = '1'   " + tipoPedimento + " AND Rfc = '" + parametros.getUsuario().getCliente().getRfcActual() + "') DG " +
						 "					ON DG.FK_DataStage = DS.PK_DataStage AND DG.Pedimento = F.Pedimento AND DG.Patente = F.Patente AND DG.SeccionAduanera = F.SeccionAduanera" +
						 " \n WHERE " + u.construccionDeFechas(parametros, "F.FechaFacturacion", "F.FechaFacturacion");

			Query query = em.createNativeQuery(sql, FacturasPO.class);
			List<FacturasPO> list = query.getResultList();
			if (!list.isEmpty()){
				for (FacturasPO facturasPO : list) {
					lista.add(builderBO(facturasPO));
				}
				return lista;
			}
			
		} catch (Exception e) {
			log.severe("Error al buscar facturas. " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	public FacturasBO save(FacturasBO entity) {
		try {
			FacturasPO po = builderPO(entity);
			em.persist(po);
			entity = builderBO(po);

			return entity;
		} catch (RuntimeException re) {
			log.severe("Error al guardar una factura. " + re.getMessage());
			em.getTransaction().rollback();
			throw re;
		}
	}

	@Override
	public boolean loadDataLocal(File file){
		try {
			StringBuilder sbQuery = new StringBuilder("LOAD DATA LOCAL INFILE '").append(file.getPath()).append("' \n");
			sbQuery.append("INTO TABLE ").append(Archivos.obtenerTabla(FacturasPO.class.getSimpleName())).append(" \n");
			sbQuery.append("FIELDS TERMINATED BY '|' \n");
			sbQuery.append("LINES TERMINATED BY '\\r\\n' \n");
			sbQuery.append("IGNORE 1 LINES \n");
			log.info("Query: " + sbQuery.toString());

			Query query = em.createNativeQuery(sbQuery.toString());
			int salida = query.executeUpdate();
			log.info("Se ha cargado el archivo a la tabla de la BD. Status: " + salida);
		} catch (Exception e) {
			log.severe("Error al cargar el archivo a la tabla de facturas. " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
}
