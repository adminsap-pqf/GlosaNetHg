package org.mx.persistencia.comun.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.ApendiceBO;
import org.mx.modelo.comun.bo.ApendiceContenidoBO;
import org.mx.modelo.comun.bo.RegionContenidoBO;
import org.mx.modelo.comun.bo.RegionesBO;
import org.mx.modelo.comun.bo.TratadoBO;
import org.mx.modelo.comun.bo.TratadoContenidoBO;
import org.mx.modelo.comun.po.ApendicePO;
import org.mx.modelo.comun.po.RegionesPO;
import org.mx.modelo.comun.po.TratadoPO;
import org.mx.persistencia.comun.GlosarioDAO;
import org.mx.persistencia.comun.builder.BuilderGlosario;
import org.springframework.stereotype.Component;

@Component
public class GlosarioDAOImpl implements GlosarioDAO{
	
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ApendiceBO> getApendices() throws GlosaNetException {
		try{
			String sql = "FROM ApendicePO ";
			Query query = em.createQuery(sql, ApendicePO.class);
			
			List<ApendiceBO> lista = new ArrayList<ApendiceBO>();
			List<ApendicePO> results = query.getResultList();
			for (ApendicePO apendicePO : results) {
				lista.add(BuilderGlosario.builderBO(apendicePO));
			}			
			return lista;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TratadoBO> getTratados() throws GlosaNetException {
		try{
			String sql = "FROM TratadoPO ";
			Query query = em.createQuery(sql, TratadoPO.class);
			
			List<TratadoBO> lista = new ArrayList<TratadoBO>();
			List<TratadoPO> results = query.getResultList();
			for (TratadoPO tratadoPO : results) {
				lista.add(BuilderGlosario.builderBO(tratadoPO));
			}			
			return lista;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RegionesBO> getRegiones() throws GlosaNetException {
		try{
			String sql = "FROM RegionesPO ";
			Query query = em.createQuery(sql, RegionesPO.class);
			
			List<RegionesBO> lista = new ArrayList<RegionesBO>();
			List<RegionesPO> results = query.getResultList();
			for (RegionesPO regionesPO : results) {
				lista.add(BuilderGlosario.builderBO(regionesPO));
			}			
			return lista;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ApendiceContenidoBO> getApendicesContenido(int idApendice, int orden) throws GlosaNetException {
		try{
			String sql = "SELECT * FROM GN_Apendice_Contenido WHERE FK01_Apendice = " + idApendice ;
			if(orden != 0){
				sql += " AND Orden = " + orden;
			}
			sql += " Order by Orden, Renglon";
			Query query = em.createNativeQuery(sql);
			
			List<ApendiceContenidoBO> lista = new ArrayList<ApendiceContenidoBO>();
			List<Object[]> results = query.getResultList();
			for (Object[] apendiceContenidoPO : results) {
				lista.add(BuilderGlosario.builderBO(apendiceContenidoPO));
			}			
			return lista;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TratadoContenidoBO> getTratadoContenido(int idTratado) throws GlosaNetException {
		try{
			String sql = "SELECT * FROM GN_Tratado_Contenido WHERE FK01_Tratado = " + idTratado ;
			sql += " Order by Renglon";
			Query query = em.createNativeQuery(sql);
			
			List<TratadoContenidoBO> lista = new ArrayList<TratadoContenidoBO>();
			List<Object[]> results = query.getResultList();
			for (Object[] tratadoContenidoPO : results) {
				lista.add(BuilderGlosario.builderTCBO(tratadoContenidoPO));
			}			
			return lista;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RegionContenidoBO> getRegionContenido(int idRegion) throws GlosaNetException {
		try{
			String sql = "SELECT * FROM GN_Region_Contenido WHERE FK01_Region = " + idRegion ;
			sql += " Order by Renglon";
			Query query = em.createNativeQuery(sql);
			
			List<RegionContenidoBO> lista = new ArrayList<RegionContenidoBO>();
			List<Object[]> results = query.getResultList();
			for (Object[] regionContenidoPO : results) {
				lista.add(BuilderGlosario.builderRBO(regionContenidoPO));
			}			
			return lista;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	

	@Override
	public String getNombreClave(String columna, String columnaClave, String tabla, int apendice, String clave) throws GlosaNetException {
		try{
			
			String sql = "SELECT "+ columna +" FROM "+ tabla +" WHERE "+ columnaClave +" = '" + clave + "' AND FK01_Apendice = "+ apendice ;
			
			Query query = em.createNativeQuery(sql);
			
						
			return query.getResultList().get(0).toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
}
