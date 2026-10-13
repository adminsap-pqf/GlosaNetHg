package org.mx.persistencia.comun.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.mx.modelo.comun.po.DataStagePO;
import org.mx.modelo.datastage.bo.DataStageBO;
import org.mx.modelo.util.archivos.Archivos;
import org.mx.persistencia.EntityManagerHelper;
import org.mx.persistencia.comun.DataBaseDAO;
import org.springframework.stereotype.Repository;

@Repository
public abstract class DataBaseDAOImpl<BO, PO> implements DataBaseDAO<BO> {
	private final static Logger log = Logger.getLogger(DataBaseDAOImpl.class.getName());
	private Class<BO> model;
	private Class<PO> table;
	
	
	//@PersistenceContext(unitName = "persistenciaNet")
	protected EntityManager em;
	
	//@PersistenceContext(unitName = "persistenciaPro")
	//protected EntityManager emSql;
	
	public static DataStagePO dataStage;
	public static DataStageBO dataStageBO;
	
	public DataBaseDAOImpl(Class<PO> clazz) {
		super();
		em = getEntityManager();
		table = clazz;
	}
	

	public EntityManager getEntityManager() {
		return EntityManagerHelper.getEntityManager();
	}

	/**
	 * Perform an initial save of a previously unsaved ContribucionesPartida557
	 * entity. All subsequent persist actions of this entity should use the
	 * #update() method. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#persist(Object)
	 * EntityManager#persist} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * ContribucionesPartida557DAO.save(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            ContribucionesPartida557 entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */

	@Override
	public BO save(BO entity) {
		try {
			if (!em.getTransaction().isActive())
				em.getTransaction().begin();
			PO po = builderPO(entity);
			em.persist(po);
			 
			em.flush();
			em.getTransaction().commit();
			entity = builderBO(po);
			return entity;
		} catch (RuntimeException re) {
			em.getTransaction().rollback();
			 
			throw re;
		}
	}

	/**
	 * Delete a persistent ContribucionesPartida557 entity. This operation must
	 * be performed within the a database transaction context for the entity's
	 * data to be permanently deleted from the persistence store, i.e.,
	 * database. This method uses the
	 * {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * ContribucionesPartida557DAO.delete(entity);
	 * EntityManagerHelper.commit();
	 * entity = null;
	 * </pre>
	 * 
	 * @param entity
	 *            ContribucionesPartida557 entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	@Override
	public void delete(BO entity) {
		try {
			PO po = builderPO(entity);
			getEntityManager().remove(po);
			EntityManagerHelper.log("delete successful", Level.INFO, null);
		} catch (RuntimeException re) {
			EntityManagerHelper.log("delete failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved ContribucionesPartida557 entity and return it
	 * or a copy of it to the sender. A copy of the ContribucionesPartida557
	 * entity parameter is returned when the JPA persistence mechanism has not
	 * previously been tracking the updated entity. This operation must be
	 * performed within the a database transaction context for the entity's data
	 * to be permanently saved to the persistence store, i.e., database. This
	 * method uses the {@link javax.persistence.EntityManager#merge(Object)
	 * EntityManager#merge} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * entity = ContribucionesPartida557DAO.update(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            ContribucionesPartida557 entity to update
	 * @return ContribucionesPartida557 the persisted ContribucionesPartida557
	 *         entity instance, may not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	
	@Override
	public BO update(BO entity) {
		try {
			if (!em.getTransaction().isActive())
				em.getTransaction().begin();

			PO po = builderPO(entity);
			PO resultado = (PO) getEntityManager().merge(po);
			em.flush();
			em.getTransaction().commit();
			BO result = builderBO(resultado);
			EntityManagerHelper.log("update successful", Level.INFO, null);
			return result;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("update failed", Level.SEVERE, re);
			throw re;
		}
	}
	
	@SuppressWarnings("unused")
	public BO update(BO entity,boolean agenteAduanal) {
		try {
			if (!em.getTransaction().isActive())
				em.getTransaction().begin();
			PO po = builderPO(entity);
			PO resultado = (PO) getEntityManager().merge(po);
			em.flush();
			em.getTransaction().commit();
			log.info("Actualizado");
		
			return entity;
		} catch (RuntimeException re) {
		
			throw re;
		}
	}

	
	@Override
	public BO findById(Integer id) {
		try {
			PO result = getEntityManager().find( table, id );
			BO instance = builderBO(result);
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Find all ContribucionesPartida557 entities with a specific property
	 * value.
	 * 
	 * @param propertyName
	 *            the name of the ContribucionesPartida557 property to query
	 * @param value
	 *            the property value to match
	 * @param rowStartIdxAndCount
	 *            Optional int varargs. rowStartIdxAndCount[0] specifies the the
	 *            row index in the query result-set to begin collecting the
	 *            results. rowStartIdxAndCount[1] specifies the the maximum
	 *            number of results to return.
	 * @return List<ContribucionesPartida557> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<BO> findByProperty(String propertyName,
			final Object value, final int... rowStartIdxAndCount) {
		EntityManagerHelper.log(
				"finding ContribucionesPartida557 instance with property: "
						+ propertyName + ", value: " + value, Level.INFO, null);
		try {
			final String queryString = "select model from " + table + " model where model."
					+ propertyName + "= :propertyValue";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("propertyValue", value);
			if (rowStartIdxAndCount != null && rowStartIdxAndCount.length > 0) {
				int rowStartIdx = Math.max(0, rowStartIdxAndCount[0]);
				if (rowStartIdx > 0) {
					query.setFirstResult(rowStartIdx);
				}

				if (rowStartIdxAndCount.length > 1) {
					int rowCount = Math.max(0, rowStartIdxAndCount[1]);
					if (rowCount > 0) {
						query.setMaxResults(rowCount);
					}
				}
			}
			List<PO> lstPO = query.getResultList();
			List<BO> lstBO = new ArrayList<BO>();
			for (PO po : lstPO) {
				lstBO.add(builderBO(po));
			}
			return lstBO;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find by property name failed",
					Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Find all ContribucionesPartida557 entities.
	 * 
	 * @param rowStartIdxAndCount
	 *            Optional int varargs. rowStartIdxAndCount[0] specifies the the
	 *            row index in the query result-set to begin collecting the
	 *            results. rowStartIdxAndCount[1] specifies the the maximum
	 *            count of results to return.
	 * @return List<ContribucionesPartida557> all ContribucionesPartida557
	 *         entities
	 */
	@SuppressWarnings("unchecked")
	public List<BO> findAll(
			final int... rowStartIdxAndCount) {
		EntityManagerHelper.log(
				"finding all ContribucionesPartida557 instances", Level.INFO,
				null);
		try {
			final String queryString = "select model from " + table + " model";
			Query query = getEntityManager().createQuery(queryString);
			if (rowStartIdxAndCount != null && rowStartIdxAndCount.length > 0) {
				int rowStartIdx = Math.max(0, rowStartIdxAndCount[0]);
				if (rowStartIdx > 0) {
					query.setFirstResult(rowStartIdx);
				}

				if (rowStartIdxAndCount.length > 1) {
					int rowCount = Math.max(0, rowStartIdxAndCount[1]);
					if (rowCount > 0) {
						query.setMaxResults(rowCount);
					}
				}
			}
			List<PO> lstPO = query.getResultList();
			List<BO> lstBO = new ArrayList<BO>();
			for (PO po : lstPO) {
				lstBO.add(builderBO(po));
			}
			return lstBO;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find all failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * @return the model
	 */
	public Class<BO> getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(Class<BO> model) {
		this.model = model;
	}

	/**
	 * @return the table
	 */
	public Class<PO> getTable() {
		return table;
	}

	/**
	 * @param table the table to set
	 */
	public void setTable(Class<PO> table) {
		this.table = table;
	}
	
	public boolean loadDataLocal(File file){
		
		try {
			if (!em.getTransaction().isActive()){
				em.getTransaction().begin();
			}

			StringBuilder sbQuery = new StringBuilder("LOAD DATA LOCAL INFILE '").append(file.getPath()).append("' \n");
			sbQuery.append("INTO TABLE ").append(Archivos.obtenerTabla(table.getSimpleName())).append(" \n");
			sbQuery.append("FIELDS TERMINATED BY '|' \n");
			sbQuery.append("LINES TERMINATED BY '\\r\\n' \n");
			sbQuery.append("IGNORE 1 LINES \n");

			log.info("Tabla: " + table.getSimpleName() + "Query: " + sbQuery.toString());
			Query query = em.createNativeQuery(sbQuery.toString());
			query.executeUpdate();

			log.info("La operación se realizó correctamente");
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	protected abstract BO builderBO(PO po);
	protected abstract PO builderPO(BO bo);
}
