package org.mx.persistencia.comun.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.mx.modelo.comun.bo.CedulaBO;
import org.mx.persistencia.comun.CedulaDAO;
import org.springframework.stereotype.Component;

@Component
public class CedulaDAOImpl implements CedulaDAO {
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<CedulaBO> totalProductos(int idCliente, String fechaInicio, String fechaFin) throws ParseException {
		try{
			String sql;
			em.clear();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

			sql = "SELECT t_551.Pedimento, t_551.Fraccion, t_551.DescripcionMercancia, t_551.PaisOrigenDestino, "
					+ " t_551.PaisCompradorVendedor, t_551.ValorComercial, t_551.CantidadUMComercial FROM GN_DataStage "
					+ " DS LEFT JOIN  Datos_Generales_501 AS DG ON DG.FK_DataStage = DS.PK_DataStage LEFT JOIN Partidas_551 as  "
					+ " t_551 on t_551.FK_DataStage = DG.FK_DataStage AND t_551.Pedimento = DG.Pedimento WHERE DS.FK01_Cliente = :idCliente "
					+ " AND DG.FechaPagoReal  >= :fInicio AND DG.FechaPagoReal  <= :fFin ";

			Query query = em.createNativeQuery(sql);
			query.setParameter("idCliente", idCliente);
			query.setParameter("fInicio", sdf.parse(fechaInicio));
			query.setParameter("fFin", sdf.parse(fechaFin));
			List<Object[]> lstPO = query.getResultList();
			List<CedulaBO> lstBO = new ArrayList<CedulaBO>();
			for(Object[] po : lstPO){
				CedulaBO r = new CedulaBO();
				r.setPedimento((String)po[0]);
				r.setFraccion((String)po[1]);
				r.setDescripcionMercancia((String)po[2]);
				r.setPaisOrigenDestino((String)po[3]);
				r.setPaisCompradorVendedor((String)po[4]);
				BigDecimal aux = (BigDecimal)po[5];
				r.setValorComercial(aux.doubleValue());
				aux = (BigDecimal)po[6];
				r.setCantidadUMComercial(aux.doubleValue());

				lstBO.add(r);			
			}

			return lstBO;

		}catch (NoResultException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CedulaBO> fraccionOrigen(int idCliente, String fechaInicio, String fechaFin) throws ParseException {
		try{
			String sql;
			em.clear();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

			sql = "SELECT t_551.Fraccion, t_551.PaisOrigenDestino, sum( t_551.ValorComercial) as total "
					+ " FROM GN_DataStage DS LEFT JOIN  Datos_Generales_501 AS DG ON DG.FK_DataStage = DS.PK_DataStage "
					+ " LEFT JOIN Partidas_551 as  t_551 on t_551.FK_DataStage = DG.FK_DataStage AND t_551.Pedimento = DG.Pedimento "
					+ " WHERE DS.FK01_Cliente = :idCliente AND DG.FechaPagoReal  >= :fInicio AND DG.FechaPagoReal  <= "
					+ " :fFin group by  t_551.Fraccion, t_551.PaisOrigenDestino ";

			Query query = em.createNativeQuery(sql);
			query.setParameter("idCliente", idCliente);
			query.setParameter("fInicio", sdf.parse(fechaInicio));
			query.setParameter("fFin", sdf.parse(fechaFin));
			List<Object[]> lstPO = query.getResultList();
			List<CedulaBO> lstBO = new ArrayList<CedulaBO>();
			for(Object[] po : lstPO){
				CedulaBO r = new CedulaBO();
				r.setFraccion((String)po[0]);
				r.setPaisOrigenDestino((String)po[1]);
				BigDecimal aux = (BigDecimal)po[2];
				r.setTotal(aux.doubleValue());

				lstBO.add(r);			
			}

			return lstBO;

		}catch (NoResultException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CedulaBO> totalContribuciones(int idCliente, String fechaInicio, String fechaFin) throws ParseException {
		try{
			String sql;
			em.clear();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

			sql = "SELECT t_510.*, Case when  t_510.TipoPedimento = 1 then 'Importacion'else 'Exportacion' end as Tipo "
					+ " FROM GN_DataStage DS LEFT JOIN  Datos_Generales_501 AS DG ON DG.FK_DataStage = DS.PK_DataStage "
					+ " LEFT JOIN Contribuciones_Pedimento_510 as  t_510 on t_510.FK_DataStage = DG.FK_DataStage AND "
					+ " t_510.Pedimento = DG.Pedimento WHERE DS.FK01_Cliente = :idCliente AND DG.FechaPagoReal  >= "
					+ " :fInicio AND DG.FechaPagoReal  <= :fFin ";

			Query query = em.createNativeQuery(sql);
			query.setParameter("idCliente", idCliente);
			query.setParameter("fInicio", sdf.parse(fechaInicio));
			query.setParameter("fFin", sdf.parse(fechaFin));
			List<Object[]> lstPO = query.getResultList();
			List<CedulaBO> lstBO = new ArrayList<CedulaBO>();
			for(Object[] po : lstPO){
				CedulaBO r = new CedulaBO();
				r.setContribucionesPedimento(Integer.parseInt(po[0]+""));
				r.setPatente(Integer.parseInt(po[1]+""));
				r.setPedimento((String)po[2]);
				r.setSeccionAduanera(Integer.parseInt(po[3]+""));
				r.setClaveContribucion(Integer.parseInt(po[4]+""));
				r.setFormaPago(Integer.parseInt(po[5]+""));
				BigDecimal aux = (BigDecimal)po[6];
				r.setTotal(aux.doubleValue());
				r.setTipoPedimento(Integer.parseInt(po[7]+""));
				r.setFechaPagoReal((Date)po[8]);
				r.setDataStage(Integer.parseInt(po[9]+""));
				r.setTipo((String)po[10]);

				lstBO.add(r);			
			}

			return lstBO;


		}catch (NoResultException e) {
			e.printStackTrace();
		}
		return null;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CedulaBO> resumenContribuciones(int idCliente, String fechaInicio, String fechaFin) throws ParseException {
		try{
			String sql;
			em.clear();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

			sql = "SELECT t_510.ClaveContribucion, t_510.FormaPago, sum(t_510.ImportePago) as total FROM GN_DataStage DS "
					+ " LEFT JOIN  Datos_Generales_501 AS DG ON DG.FK_DataStage = DS.PK_DataStage "
					+ " LEFT JOIN Contribuciones_Pedimento_510 as  t_510 on t_510.FK_DataStage = DG.FK_DataStage "
					+ " AND t_510.Pedimento = DG.Pedimento WHERE DS.FK01_Cliente = :idCliente AND "
					+ " DG.FechaPagoReal  >= :fInicio AND DG.FechaPagoReal  <= :fFin "
					+ " group by t_510.ClaveContribucion, t_510.FormaPago";

			Query query = em.createNativeQuery(sql);
			query.setParameter("idCliente", idCliente);
			query.setParameter("fInicio", sdf.parse(fechaInicio));
			query.setParameter("fFin", sdf.parse(fechaFin));
			List<Object[]> lstPO = query.getResultList();
			List<CedulaBO> lstBO = new ArrayList<CedulaBO>();
			for(Object[] po : lstPO){
				CedulaBO r = new CedulaBO();
				r.setClaveContribucion(Integer.parseInt(po[0]+""));
				r.setFormaPago(Integer.parseInt(po[1]+""));
				BigDecimal aux = (BigDecimal)po[2];
				r.setTotal(aux.doubleValue());

				lstBO.add(r);			
			}

			return lstBO;

		}catch (NoResultException e) {
			e.printStackTrace();
		}
		return null;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CedulaBO> incoterms(int idCliente, String fechaInicio, String fechaFin) throws ParseException {
		try{
			String sql;
			em.clear();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

			sql = "SELECT t_505.ProveedorMercancia, t_505.TerminoFacturacion, t_505.PaisFacturacion, t_505.ValorDolares, t_505.Pedimento "
					+ " FROM GN_DataStage DS LEFT JOIN  Datos_Generales_501 AS DG ON DG.FK_DataStage = DS.PK_DataStage "
					+ " LEFT JOIN Facturas_505 as  t_505 on t_505.FK_DataStage = DG.FK_DataStage AND t_505.Pedimento = DG.Pedimento "
					+ " WHERE DS.FK01_Cliente = :idCliente AND DG.FechaPagoReal  >= :fInicio AND "
					+ " DG.FechaPagoReal  <= :fFin AND t_505.PK_Facturas IS NOT NULL ";

			Query query = em.createNativeQuery(sql);
			query.setParameter("idCliente", idCliente);
			query.setParameter("fInicio", sdf.parse(fechaInicio));
			query.setParameter("fFin", sdf.parse(fechaFin));
			List<Object[]> lstPO = query.getResultList();
			List<CedulaBO> lstBO = new ArrayList<CedulaBO>();
			for(Object[] po : lstPO){
				CedulaBO r = new CedulaBO();
				r.setProveedorMercancia((String)po[0]);
				r.setTerminoFacturacion((String)po[1]);
				r.setPaisFacturacion((String)po[2]);
				BigDecimal aux = (BigDecimal)po[3];
				r.setTotal(aux.doubleValue());
				r.setPedimento((String)po[4]);

				lstBO.add(r);			
			}

			return lstBO;

		}catch (NoResultException e) {
			e.printStackTrace();
		}
		return null;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CedulaBO> facturacionExw(int idCliente, String fechaInicio, String fechaFin) throws ParseException {
		try{
			String sql;
			em.clear();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

			sql = "SELECT  t_505.ProveedorMercancia, t_505.TerminoFacturacion, t_505.PaisFacturacion, t_505.ValorDolares, DG.TotalIncrementables, "
					+ " t_505.Pedimento FROM GN_DataStage DS "
					+ " LEFT JOIN  Datos_Generales_501 AS DG ON DG.FK_DataStage = DS.PK_DataStage "
					+ " LEFT JOIN Facturas_505 as  t_505 on t_505.FK_DataStage = DG.FK_DataStage AND t_505.Pedimento = DG.Pedimento "
					+ " WHERE DS.FK01_Cliente = :idCliente AND DG.FechaPagoReal  >= :fInicio AND DG.FechaPagoReal  <= :fFin "
					+ " AND t_505.TerminoFacturacion = 'EXW' AND DG.TotalIncrementables = 0 ";

			Query query = em.createNativeQuery(sql);
			query.setParameter("idCliente", idCliente);
			query.setParameter("fInicio", sdf.parse(fechaInicio));
			query.setParameter("fFin", sdf.parse(fechaFin));
			List<Object[]> lstPO = query.getResultList();
			List<CedulaBO> lstBO = new ArrayList<CedulaBO>();
			for(Object[] po : lstPO){
				CedulaBO r = new CedulaBO();
				r.setProveedorMercancia((String)po[0]);
				r.setTerminoFacturacion((String)po[1]);
				r.setPaisFacturacion((String)po[2]);
				BigDecimal aux = (BigDecimal)po[3];
				r.setTotal(aux.doubleValue());
				aux = (BigDecimal)po[4];
				r.setTotal(aux.doubleValue());
				r.setPedimento((String)po[5]);

				lstBO.add(r);			
			}

			return lstBO;

		}catch (NoResultException e) {
			e.printStackTrace();
		}
		return null;

	}

}
