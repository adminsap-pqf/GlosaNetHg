package org.mx.persistencia.comun;

import java.io.File;
import java.util.List;

public interface DataBaseDAO<BO> {
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
	 * IContribucionesPartida557DAO.save(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            ContribucionesPartida557 entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public BO save(BO entity);

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
	 * IContribucionesPartida557DAO.delete(entity);
	 * EntityManagerHelper.commit();
	 * entity = null;
	 * </pre>
	 * 
	 * @param entity
	 *            ContribucionesPartida557 entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(BO entity);

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
	 * entity = IContribucionesPartida557DAO.update(entity);
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
	public BO update(BO entity);

	/***
	 * 
	 * @param id
	 * @return
	 */
	public BO findById(Integer id);

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
	 *            count of results to return.
	 * @return List<ContribucionesPartida557> found by query
	 */
	public List<BO> findByProperty(String propertyName,
			Object value, int... rowStartIdxAndCount);

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
	public List<BO> findAll(int... rowStartIdxAndCount);

	/***
	 * 
	 * @param file
	 * @return
	 */
	public boolean loadDataLocal(File file);
}
