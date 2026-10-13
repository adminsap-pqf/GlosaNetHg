package org.mx.persistencia.comun.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.mx.modelo.comun.bo.HistorialImportacionesBO;
import org.mx.modelo.comun.bo.Parametros;
import org.mx.modelo.comun.bo.StatusMessage;
import org.mx.modelo.permisos.org.mx.bo.HistorialPermisosBO;
import org.mx.persistencia.comun.HistorialImportacionesDAO;
import org.mx.persistencia.comun.builder.BuilderHistorialImportaciones;
import org.springframework.stereotype.Component;


@Component
public class HistorialImportacionesDAOImpl implements HistorialImportacionesDAO {

	
	@PersistenceContext
	private EntityManager em;
	
	StatusMessage status = new StatusMessage();
	
	@SuppressWarnings("unchecked")
	@Override
	public List<HistorialImportacionesBO> obteneHistorialImportaciones(int idProducto, boolean flagProductoPartida) {
		
		List<HistorialImportacionesBO> listabo = new ArrayList<HistorialImportacionesBO>();	
		
		StringBuilder sqlQuery = new StringBuilder("select h.cantidad, COALESCE ( po.Nombre, pa.DescripcionMercancia) as Observaciones, hp.Permiso AS NumeroPermiso,h.Unidad,h.Fecha ,current_date() fechaActual, (SELECT MAX(HP.Vigencia_Permiso)\n");
		sqlQuery.append("FROM Historial_Permisos HP  \n");
		if( flagProductoPartida){
			sqlQuery.append("WHERE HP.FK01_Producto = h.FK01_Producto) VigenciaMax, \n");
		}else{
			sqlQuery.append("WHERE HP.FK02_Partidas_551 = h.FK02_Partida) VigenciaMax, \n");
		}
		sqlQuery.append("(SELECT COUNT(HI.PK_Importaciones) FROM Historial_Permisos HP \n");
		sqlQuery.append("INNER JOIN ( \n");
		sqlQuery.append("SELECT MAX(HP.Vigencia_Permiso) Vigencia, FK01_Producto\n");				
		sqlQuery.append("FROM Historial_Permisos HP  \n");
		if(flagProductoPartida){
			sqlQuery.append("WHERE HP.FK01_Producto = :idProducto ) HP2 \n");
		}else{
			sqlQuery.append( "WHERE HP.FK02_Partidas_551 = :idProducto ) HP2 \n");
		}
		sqlQuery.append(" ON HP2.Vigencia = HP.Vigencia_Permiso AND HP2.FK01_Producto = HP.FK01_Producto  \n");
		sqlQuery.append("LEFT JOIN  Historial_Importaciones HI ON HP.PK_Historial_Permisos = HI.FK03_Historial_Permisos AND HI.FK01_Producto = HP.FK01_Producto  \n");
		if(flagProductoPartida){
			sqlQuery.append("WHERE HP.FK01_Producto = :idProducto) TotalImportaciones, \n");
		}else{
			sqlQuery.append("WHERE HP.FK02_Partidas_551 = :idProducto) TotalImportaciones, \n");
		}
		sqlQuery.append("(SELECT HP.PErmiso FROM Historial_Permisos HP  \n");
		sqlQuery.append("INNER JOIN ( \n");
		sqlQuery.append("SELECT MAX(HP.Vigencia_Permiso) Vigencia, FK01_Producto  \n");
		sqlQuery.append("FROM Historial_Permisos HP  \n");
		if(flagProductoPartida){
		sqlQuery.append("WHERE HP.FK01_Producto = :idProducto ) HP2 \n");
		}else{
			sqlQuery.append(" WHERE HP.FK02_Partidas_551 = :idProducto) HP2 \n");
		}
		sqlQuery.append("ON HP2.Vigencia = HP.Vigencia_Permiso AND HP2.FK01_Producto = HP.FK01_Producto  \n");
		if(flagProductoPartida){
			sqlQuery.append("WHERE HP.FK01_Producto = :idProducto) PermisoVigente  \n");
		}else{
			sqlQuery.append(" WHERE HP.FK02_Partidas_551 = :idProducto) PermisoVigente  \n");
		}
		sqlQuery.append("from Historial_Importaciones h  \n");
		sqlQuery.append("LEFT join Producto po  on h.FK01_Producto =  po.PK_Producto \n");
		sqlQuery.append("LEFT join Partidas_551 pa on pa.PK_Partida= h.FK02_Partida  \n");
		sqlQuery.append("LEFT join Historial_Permisos hp on hp.PK_Historial_Permisos = h.FK03_Historial_Permisos  \n");
		sqlQuery.append("LEFT JOIN GN_DataStage D ON D.PK_DataStage = pa.FK_DataStage  \n");
		if (flagProductoPartida) {	
			em.clear();
			try {
				sqlQuery.append("where  h.FK01_Producto= :idProducto \n");
				//sqlQuery1.append(idProducto);
				Query query = em.createNativeQuery(sqlQuery.toString());			
				query.setParameter("idProducto",idProducto );
				List <Object[]> listapo = query.getResultList();
				for(Object[] historial:listapo){
					listabo.add(BuilderHistorialImportaciones.builderHistorialBO(historial));/////
				}	
				return listabo;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			try {
				em.clear();		
				sqlQuery.append("WHERE h.FK02_Partida = :idProducto \n");
				sqlQuery.append("UNION \n");
				sqlQuery.append("SELECT  P.CantidadUMComercial, P.DescripcionMercancia, P.NumeroPermiso, \n");
				sqlQuery.append("CASE WHEN P.Unidad = 'KILO' THEN 'Kg'  WHEN P.Unidad = 'PIEZA' THEN 'pza' \n");
				sqlQuery.append("WHEN P.Unidad ='GRAMO' THEN 'g' WHEN P.Unidad ='METRO LINEAL' THEN 'm' \n");
				sqlQuery.append("WHEN P.Unidad ='METRO CUADRADO' THEN 'm²' WHEN P.Unidad ='METRO CUBICO' THEN 'm' \n");
				sqlQuery.append("WHEN P.Unidad ='CABEZA' THEN 'Cabz'  WHEN P.Unidad ='LITRO' THEN 'l' \n");
				sqlQuery.append("WHEN P.Unidad ='PAR' THEN 'l' WHEN P.Unidad ='KILOWATT' THEN 'kW' \n");
				sqlQuery.append("WHEN P.Unidad ='MILLAR' THEN 'mil' WHEN P.Unidad ='JUEGO' THEN 'jgo' \n");
				sqlQuery.append("WHEN P.Unidad ='KILOWATT/HORA' THEN 'kWH' WHEN P.Unidad ='TONELADA' THEN 't' \n");
				sqlQuery.append("WHEN P.Unidad ='BARRIL' THEN 'bbl' WHEN P.Unidad ='GRAMO NETO' THEN 'G net' \n");
				sqlQuery.append("WHEN P.Unidad ='DECENAS' THEN 'dec'  WHEN P.Unidad ='CIENTOS' THEN 'c' \n");
				sqlQuery.append("WHEN P.Unidad ='DOCENAS' THEN 'm'  WHEN P.Unidad ='CAJA' THEN 'caj' \n");
				sqlQuery.append("WHEN P.Unidad ='BOTELLA' THEN 'btll' \n");
				sqlQuery.append("ELSE 'g' END Unidad, P.FechaPagoReal, null, null,null,null \n");
				sqlQuery.append(" FROM ( \n");
				sqlQuery.append("Select  p.CantidadUMComercial, p.DescripcionMercancia, pe.NumeroPermiso, \n"  );
				sqlQuery.append("(SELECT Column2 FROM GN_Prueba.GN_Apendice_Contenido where FK01_Apendice = 7 AND Column1 = p.UnidadMedidaComercial) Unidad, p.UnidadMedidaComercial, p.FechaPagoReal \n" );
				sqlQuery.append("from Partidas_551 p  \n");
				sqlQuery.append("LEFT join Permisos_Partida_553 pe on p.Patente = pe.Patente and p.Pedimento = pe.Pedimento and p.SeccionAduanera = pe.SeccionAduanera and p.Fraccion = pe.Fraccion \n" );
				sqlQuery.append("where p.PK_Partida = :idProducto \n");
				sqlQuery.append(" ) P; \n");
				Query query = em.createNativeQuery(sqlQuery.toString());
				query.setParameter("idProducto",idProducto );
				List <Object[]> listapo = query.getResultList();
				for(Object[] historial: listapo){
					listabo.add(BuilderHistorialImportaciones.builderHistorialBOPartida(historial));
				}	
				return listabo;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		return listabo;
	}			
	
	
	
	
	
	@Override
	public boolean insertarHistorialImportacion(HistorialImportacionesBO  bo) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		boolean flag = bo.isFlagProductoPartidas();
		if(flag){
			try{
				em.clear();
				String sbQuery = "insert into  Historial_Importaciones (FK01_Producto,cantidad,Fecha,Unidad,FK03_Historial_Permisos) value(:producto,:cantidad,:fecha, :unidad, :idpermiso);";
				Query query = em.createNativeQuery(sbQuery);
				query.setParameter("producto", bo.getFk01producto());
				query.setParameter("cantidad", bo.getCantidad());
				query.setParameter("fecha",sdf.parse(bo.getFecha()));
				query.setParameter("unidad",bo.getUnidad());
				query.setParameter("idpermiso", bo.getIdpermiso());
				query.executeUpdate();
				return true;
				
			}catch(Exception e){
				e.printStackTrace();
				return false;	
			}
		}else{
			try{
				em.clear();
				String sbQuery = "insert into  Historial_Importaciones (FK02_Partida,cantidad,Fecha,Unidad,FK03_Historial_Permisos) value (:producto,:cantidad,:fecha, :unidad, :idpermiso);";
				Query query = em.createNativeQuery(sbQuery);
				query.setParameter("producto", bo.getFk02partidad());
				query.setParameter("cantidad", bo.getCantidad());
				query.setParameter("fecha",sdf.parse(bo.getFecha()));
				query.setParameter("unidad",bo.getUnidad());
				query.setParameter("idpermiso", bo.getIdpermiso());
				query.executeUpdate();
				return true;
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
		}
	
	}
	
	
	
	
	@Override
	public List<HistorialPermisosBO> obtenerPermisos(Parametros bo) {
		boolean flag = bo.isFlagProductoPartidas();
		if(flag){
			int id= bo.getIdproducto();
			String sqlQuery = "select Permiso, PK_Historial_Permisos from Historial_Permisos where FK01_Producto = :producto order by Permiso;" ;
				em.clear();
				try {
					List<HistorialPermisosBO> listabo = new ArrayList<HistorialPermisosBO>();	
					Query query = em.createNativeQuery(sqlQuery);
					query.setParameter("producto", id);
//					List <Object[]> listapo = query.getResultList();
//					for(Object[] d:listapo){
//						listabo.add(BuilderHistorialPermisos.builderPermisosBO(d));
//					}
					
					
//					for(Object[] po:listapo){
//						HistorialPermisosBO bo = new HistorialPermisosBO();
//						bo.setPermiso((String) po[0]); 
//						listabo.add(bo);
//					}
					return listabo;

				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
		}else{
			int id= bo.getIdproducto();
			String sqlQuery = "select Permiso, PK_Historial_Permisos from Historial_Permisos where FK02_Partidas_551 = :producto order by Permiso;" ;
				em.clear();
				try {
					List<HistorialPermisosBO> listabo = new ArrayList<HistorialPermisosBO>();	
					Query query = em.createNativeQuery(sqlQuery);
					query.setParameter("producto", id);
//					List <Object[]> listapo = query.getResultList();
//					for(Object[] d:listapo){
//						listabo.add(BuilderHistorialPermisos.builderPermisosBO(d));
//					}
					
					
//					for(Object[] po:listapo){
//						HistorialPermisosBO bo = new HistorialPermisosBO();
//						bo.setPermiso((String) po[0]); 
//						listabo.add(bo);
//					}
					return listabo;

				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
		}

	}
	
	
	
	
}
