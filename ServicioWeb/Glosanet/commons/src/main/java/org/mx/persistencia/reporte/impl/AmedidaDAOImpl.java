package org.mx.persistencia.reporte.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.reportes.ReporteDICIPA_565;
import org.mx.persistencia.reporte.AmedidaDAO;
import org.mx.persistencia.reporte.builder.BuilderResumenReporte;
import org.springframework.stereotype.Component;

@Component
public class AmedidaDAOImpl implements AmedidaDAO{
	private final static Logger log = Logger.getLogger(AmedidaDAOImpl.class.getName());

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<ReporteDICIPA_565> findReporteMedidaMensualDICIPA_565(String fecha)
			throws GlosaNetException {
		try{
			String sql = "SELECT DISTINCT DG.Patente, DG.Pedimento, DG.SeccionAduanera, t_503.NumeroGuia, t_503.TipoGuia, t_504.TipoContenedor, "
					+ " t_504.NumContenedor, DG.ClaveDocumento, DG.Rfc, t_505.ProveedorMercancia, t_551.PaisCompradorVendedor, t_551.ValorDolares, "
					+ " DG.TipoCambio, DG.TotalFletes, DG.TotalSeguros, DG.PesoBrutoMercancia, DG.MedioTransporteSalida, DG.MedioTransporteArribo, "
					+ " DG.MedioTransporteEntrada_Salida, DG.DestinoMercancia, DG.TotalEmbalajes, DG.TipoPedimento, "
					+ " COALESCE(t_553.ValorComercialDolares, 0) AS ValorComercialDolares, t_509.TasaContribucion, t_557.ClaveContribucion, t_557.ImportePago, DG.FechaPagoReal "
					+ " FROM Datos_Generales_501 as DG "
					+ " INNER JOIN (select PK_DataStage, FK01_Cliente  from GN_DataStage) as DS on DG.FK_DataStage = DS.PK_DataStage "
					+ " LEFT JOIN (select FK_DataStage, Patente, Pedimento, SeccionAduanera, TipoContenedor, NumContenedor from Contenedores_504) as t_504 on t_504.Patente = DG.Patente AND t_504.Pedimento = DG.Pedimento AND t_504.SeccionAduanera = DG.SeccionAduanera "
					+ " LEFT JOIN (select distinct Patente, Pedimento, SeccionAduanera,  TasaContribucion   from Tasas_Pedimento_509 group by Patente, Pedimento, SeccionAduanera) as t_509 on t_509.Patente = DG.Patente AND t_509.Pedimento = DG.Pedimento AND t_509.SeccionAduanera = DG.SeccionAduanera "
					+ " LEFT JOIN (select Patente, Pedimento, SeccionAduanera, sum(ValorComercialDolares) as ValorComercialDolares from Permisos_Partida_553 group by Patente, Pedimento, SeccionAduanera) as t_553 on t_553.Patente = DG.Patente AND t_553.Pedimento = DG.Pedimento AND t_553.SeccionAduanera = DG.SeccionAduanera "
					+ " LEFT JOIN (select FK_DataStage, Patente, Pedimento, SeccionAduanera, NumeroGuia, TipoGuia from Guias_503 where TipoGuia = 'H') as t_503 on t_503.Patente = DG.Patente AND t_503.Pedimento = DG.Pedimento AND t_503.SeccionAduanera = DG.SeccionAduanera "
					+ " LEFT JOIN (SELECT Patente, Pedimento, SeccionAduanera, PaisCompradorVendedor, sum(ValorDolares) ValorDolares FROM Partidas_551 group by Patente, Pedimento, SeccionAduanera, PaisOrigenDestino) AS t_551 ON t_551.Patente = DG.Patente AND t_551.Pedimento = DG.Pedimento AND t_551.SeccionAduanera = DG.SeccionAduanera "
					+ " LEFT JOIN (select Distinct Patente, Pedimento, SeccionAduanera, ProveedorMercancia from Facturas_505 group by Patente, Pedimento, SeccionAduanera) as t_505 on t_505.Patente = DG.Patente AND t_505.Pedimento = DG.Pedimento AND t_505.SeccionAduanera = DG.SeccionAduanera "
					+ " LEFT JOIN (select DISTINCT Patente, Pedimento, SeccionAduanera, ClaveContribucion, sum(ImportePago) as  ImportePago  from Contribuciones_Partida_557 group by Patente, Pedimento, SeccionAduanera, ClaveContribucion) as t_557 on t_557.Patente = DG.Patente AND t_557.Pedimento = DG.Pedimento AND t_557.SeccionAduanera = DG.SeccionAduanera "
					+ " WHERE DS.FK01_Cliente = 5  AND " + fecha + " Order by DG.Patente, DG.Pedimento, DG.SeccionAduanera";
			System.out.println(sql);
			Query query = em.createNativeQuery(sql);
			List<Object[]> lstPO = query.getResultList();
			List<ReporteDICIPA_565> lst = new ArrayList<ReporteDICIPA_565>();
			for(Object[] r : lstPO){
				ReporteDICIPA_565 bo = new ReporteDICIPA_565();
				bo = BuilderResumenReporte.builderBO_ReporteDICIPA_565_2(r);
				lst.add(bo);
			}

			if (lst.isEmpty())
				log.info("@findReporteMedidaMensualDICIPA_565 -> La generación del reporte mensual DICIPA_565 no arrojó ningún resultado");
			 else
				log.info("@findReporteMedidaMensualDICIPA_565 -> Se generó el reporte mensual DICIPA_565");

			return lst;
		}catch(Exception e){
			log.severe("@findReporteMedidaMensualDICIPA_565 -> Error al generar el reporte mensual: " + e.getMessage());
			e.printStackTrace();
		}
		return new ArrayList<ReporteDICIPA_565>();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ReporteDICIPA_565> findReporteMedidaMensualDICIPA_565_Generales(String fecha)
			throws GlosaNetException {
		try{
			String sql = "SELECT DISTINCT DG.Patente, DG.Pedimento, DG.SeccionAduanera, t_503.NumeroGuia, t_503.TipoGuia, t_504.TipoContenedor, t_504.NumContenedor, "
					+ " DG.ClaveDocumento, DG.Rfc, t_505.ProveedorMercancia, t_551.PaisCompradorVendedor, t_551.ValorDolares, DG.TipoCambio, DG.TotalFletes, DG.TotalSeguros, "
					+ " DG.PesoBrutoMercancia, DG.MedioTransporteSalida, DG.MedioTransporteArribo, DG.MedioTransporteEntrada_Salida, DG.DestinoMercancia, DG.TotalEmbalajes, "
					+ " DG.TipoPedimento, t_509.TasaContribucion, "
					+ " t_557.CCPartida as CCPartida, t_557.ImportePagoPartida as ImportePagoPartida, "
					+ " t_510.CCPedimento as CCPedimento, t_510.ImportePagoPedimento as ImportePagoPedimento, "
					+ " DG.FechaPagoReal"
					+ " FROM Datos_Generales_501 as DG "
					+ " INNER JOIN (select PK_DataStage, FK01_Cliente  from GN_DataStage) as DS on DG.FK_DataStage = DS.PK_DataStage "
					+ " LEFT JOIN (select FK_DataStage, Patente, Pedimento, SeccionAduanera, TipoContenedor, NumContenedor from Contenedores_504) as t_504 on t_504.Patente = DG.Patente AND t_504.Pedimento = DG.Pedimento AND t_504.SeccionAduanera = DG.SeccionAduanera "
					+ " LEFT JOIN (select distinct Patente, Pedimento, SeccionAduanera,  TasaContribucion   from Tasas_Pedimento_509 group by Patente, Pedimento, SeccionAduanera) as t_509 on t_509.Patente = DG.Patente AND t_509.Pedimento = DG.Pedimento AND t_509.SeccionAduanera = DG.SeccionAduanera "
					+ " LEFT JOIN (select FK_DataStage, Patente, Pedimento, SeccionAduanera, NumeroGuia, TipoGuia from Guias_503 where TipoGuia = 'H') as t_503 on t_503.Patente = DG.Patente AND t_503.Pedimento = DG.Pedimento AND t_503.SeccionAduanera = DG.SeccionAduanera "
					+ " LEFT JOIN (SELECT Patente, Pedimento, SeccionAduanera, PaisCompradorVendedor, sum(ValorDolares) ValorDolares FROM Partidas_551 group by Patente, Pedimento, SeccionAduanera, PaisOrigenDestino) AS t_551 ON t_551.Patente = DG.Patente AND t_551.Pedimento = DG.Pedimento AND t_551.SeccionAduanera = DG.SeccionAduanera "
					+ " LEFT JOIN (select Distinct Patente, Pedimento, SeccionAduanera, ProveedorMercancia from Facturas_505 group by Patente, Pedimento, SeccionAduanera) as t_505 on t_505.Patente = DG.Patente AND t_505.Pedimento = DG.Pedimento AND t_505.SeccionAduanera = DG.SeccionAduanera "
					+ " LEFT JOIN (select DISTINCT Patente, Pedimento, SeccionAduanera, ClaveContribucion as CCPartida,  sum(ImportePago) as  ImportePagoPartida  from Contribuciones_Partida_557 group by Patente, Pedimento, SeccionAduanera, CCPartida) as t_557 on t_557.Patente = DG.Patente AND t_557.Pedimento = DG.Pedimento AND t_557.SeccionAduanera = DG.SeccionAduanera "
					+ " LEFT JOIN (select DISTINCT Patente, Pedimento, SeccionAduanera, ClaveContribucion as CCPedimento, sum(ImportePago) as  ImportePagoPedimento  from Contribuciones_Pedimento_510 group by Patente, Pedimento, SeccionAduanera, CCPedimento) as t_510 on t_510.Patente = DG.Patente AND t_510.Pedimento = DG.Pedimento AND t_510.SeccionAduanera = DG.SeccionAduanera  "
					+ " WHERE DS.FK01_Cliente = 5  AND " + fecha + " "
					+ " ORDER BY DG.Patente, DG.Pedimento, DG.SeccionAduanera";
System.out.println("Consulta reporte 565:"+sql);
			Query query = em.createNativeQuery(sql);
			List<Object[]> lstPO = query.getResultList();
			List<ReporteDICIPA_565> lst = new ArrayList<ReporteDICIPA_565>();

			for(Object[] r : lstPO){
				ReporteDICIPA_565 bo = new ReporteDICIPA_565();
				bo = BuilderResumenReporte.builderBO_ReporteDICIPA_565_2(r);
				lst.add(bo);
			}

			if (lst.isEmpty())
				log.info("@findReporteMedidaMensualDICIPA_565_Generales -> La generación del reporte mensual de datos generales DICIPA_565 no arrojó ningún resultado");
			else
				log.info("@findReporteMedidaMensualDICIPA_565_Generales -> Se generó el reporte mensual de datos generales DICIPA_565");

			return lst;
		}catch(Exception e){
			log.severe("@findReporteMedidaMensualDICIPA_565_Generales -> Error al generar el reporte general: " + e.getMessage());
			e.printStackTrace();
		}
		return new ArrayList<ReporteDICIPA_565>();
	}

	@Override
	public ReporteDICIPA_565 save(ReporteDICIPA_565 entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(ReporteDICIPA_565 entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ReporteDICIPA_565 update(ReporteDICIPA_565 entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReporteDICIPA_565 findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReporteDICIPA_565> findByProperty(String propertyName,
			Object value, int... rowStartIdxAndCount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReporteDICIPA_565> findAll(int... rowStartIdxAndCount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean loadDataLocal(File file) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @param clazz
	 */

}
