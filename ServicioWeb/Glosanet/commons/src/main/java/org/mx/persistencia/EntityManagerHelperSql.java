package org.mx.persistencia;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class EntityManagerHelperSql {

	private static final EntityManagerFactory emf2; 
	private static final ThreadLocal<EntityManager> threadLocal2;
	private static final Logger logger2;
	
	static {
		emf2 = Persistence.createEntityManagerFactory("persistenciaPro"); 		
		threadLocal2 = new ThreadLocal<EntityManager>();
		logger2 = Logger.getLogger("persistenciaPro");
		logger2.setLevel(Level.ALL);
	}
		
	public static EntityManager getEntityManager() {
		EntityManager manager = threadLocal2.get();		
		if (manager == null || !manager.isOpen()) {
			manager = emf2.createEntityManager();
			threadLocal2.set(manager);
		}
		return manager;
	}
	
	 public static void closeEntityManager() {
        EntityManager em = threadLocal2.get();
        threadLocal2.set(null);
        if (em != null) em.close();
    }

    public static void beginTransaction() {
    	getEntityManager().getTransaction().begin();
    }
    
    public static void commit() {
    	getEntityManager().getTransaction().commit();
    }  
    
    public static void rollback() {
    	getEntityManager().getTransaction().rollback();
    } 
    
    public static Query createQuery(String query) {
		return getEntityManager().createQuery(query);
	}
	
	public static void log(String info, Level level, Throwable ex) {
    	logger2.log(level, info, ex);
    }
	
}
