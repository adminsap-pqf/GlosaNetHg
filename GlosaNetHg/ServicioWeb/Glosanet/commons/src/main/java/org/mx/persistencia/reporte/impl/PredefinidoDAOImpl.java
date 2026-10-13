package org.mx.persistencia.reporte.impl;

import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.reportes.ParametrosPartida;
import org.mx.modelo.reportes.bo.DetalleContribucionesBO;
import org.mx.modelo.reportes.bo.DetallePartidasBO;
import org.mx.modelo.reportes.bo.ResumenReporteBO;
import org.mx.modelo.reportes.po.DetalleContribucionesPO;
import org.mx.modelo.reportes.po.DetallePartidasPO;
import org.mx.persistencia.Page;
import org.mx.persistencia.reporte.PredefinidoDAO;
import org.mx.persistencia.reporte.builder.BuilderResumenReporte;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
public class PredefinidoDAOImpl implements PredefinidoDAO {
    private static final Logger log = Logger.getLogger(PredefinidoDAOImpl.class.getName());

    @PersistenceContext
    private EntityManager em;

    @Override
    public ResumenReporteBO save(ResumenReporteBO entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void delete(ResumenReporteBO entity) {
        // TODO Auto-generated method stub

    }

    @Override
    public ResumenReporteBO update(ResumenReporteBO entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResumenReporteBO findById(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ResumenReporteBO> findByProperty(String propertyName,
                                                 Object value, int... rowStartIdxAndCount) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ResumenReporteBO> findAll(int... rowStartIdxAndCount) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean loadDataLocal(File file) {
        // TODO Auto-generated method stub
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ResumenReporteBO> getMontosTotalesPorTipoOperacionClave(
            String restricciones, String empresa, String tipoMoneda)
            throws GlosaNetException {

        try {
            em.clear();
            log.info("Obteniendo monto total por tipo de operación");

            String sql = "" +
                    " SELECT coalesce(ClaveDocumento, 'N/A') Clave, sum(coalesce(" + tipoMoneda + ",0)) Monto " +
                    " FROM Partidas_551 p   " +
                    " WHERE (FK_DataStage, Patente, Pedimento, SeccionAduanera) IN (SELECT FK_DataStage, Patente, Pedimento, SeccionAduanera " +
                    " FROM Datos_Generales_501 DG INNER JOIN GN_DataStage DS on DS.PK_DataStage = DG.FK_DataStage " +
                    " WHERE  " + restricciones + " AND " + empresa +
                    " )GROUP BY coalesce(p.ClaveDocumento, 'N/A') ";

                    log.info("Query: " + sql);

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();
            return BuilderResumenReporte.builderTipoOperacion(lstPO);


        } catch (Exception e) {
            log.severe("Error al obtener el monto total por tipo de operación. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
     * @see mx.com.glosanet.persistencia.dao.reportes.ReportesDAO#getMontosTotalesClaveContribucion(java.lang.String, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ResumenReporteBO> getMontosTotalesClaveContribucion(
            String restricciones, String empresa, String tipoMoneda, String rectificacionFecha)
            throws GlosaNetException {
        log.info("Obteniendo el monto total por clave de contribución");
        try {
            String montoC = "";
            //			if(tipoMoneda.equals("peso") || tipoMoneda.equals("")){
            //				montoC = "t_557.ImportePago";
            //			}else{
            //				montoC = "t_557.ImportePago/DG.TipoCambio"; //
            //			}
			
			
			/*
			 * String sql = " \n SELECT t_557.ClaveContribucion,  sum(" + montoC + ")  MontoContibuciones, sum(coalesce(t_551."+tipoMoneda+",0)) Monto" +
					" \n FROM Datos_Generales_501 AS DG  " +
					" \n LEFT JOIN GN_DataStage AS DS ON DS.PK_DataStage = DG.FK_DataStage" +
					" \n LEFT JOIN (SELECT PK_Partida, Patente, Pedimento, SeccionAduanera, SecuenciaFraccion, SUM(" + tipoMoneda + ") AS " + tipoMoneda + ", FK_DataStage FROM Partidas_551 "
					+ " group by Patente, Pedimento, SeccionAduanera, SecuenciaFraccion, FK_DataStage) AS t_551 "
					+ "  ON t_551.FK_DataStage = DG.FK_DataStage AND t_551.Patente = DG.Patente AND t_551.Pedimento = DG.Pedimento AND t_551.SeccionAduanera = DG.SeccionAduanera " +
					" \n LEFT JOIN (SELECT Patente, Pedimento, SeccionAduanera, SecuenciaFraccion, sum(ImportePago) ImportePago, ClaveContribucion, FK_DataStage FROM Contribuciones_Partida_557 "
					+ " GROUP BY Patente, Pedimento, SeccionAduanera, SecuenciaFraccion, ClaveContribucion, FK_DataStage) AS t_557  "
					+ " ON t_557.FK_DataStage = DG.FK_DataStage AND t_557.Patente = t_551.Patente AND t_557.Pedimento = t_551.Pedimento AND t_557.SeccionAduanera = t_551.SeccionAduanera AND t_557.SecuenciaFraccion = t_551.SecuenciaFraccion  " +
					" \n WHERE " + empresa + " AND t_557.ClaveContribucion IS NOT NULL " +
					" \n GROUP BY t_557.ClaveContribucion" +
					" \n ORDER BY Monto desc";
			 */
            montoC = "t_557.ImportePago";
            empresa = empresa.replace("DS", "DG");
            em.clear();

            String sql = "SELECT FORMAT(ClaveContribucion, 0) ClaveContribucion , sum(ImportePago) MontoContribuciones, 0 Monto FROM V_Contribuciones_Pedimento DG WHERE " + empresa + " GROUP BY DG.ClaveContribucion";
            
            /*String sql = "" +
                    " SELECT t_557.ClaveContribucion, sum(" + montoC + ") MontoContribuciones, sum(coalesce(t_551." + tipoMoneda + ",0)) Monto" +
                    " FROM (SELECT Patente, Pedimento, SeccionAduanera, FechaPagoReal, FK_DataStage FROM Datos_Generales_501 WHERE TipoPedimento = 1 GROUP BY Patente, Pedimento, SeccionAduanera, FechaPagoReal, FK_DataStage) DG " +
                    " INNER JOIN GN_DataStage AS DS ON DS.PK_DataStage = DG.FK_DataStage \n" +
                    " LEFT JOIN (SELECT * FROM Partidas_551 ) AS t_551 ON t_551.FK_DataStage = DG.FK_DataStage AND t_551.Patente = DG.Patente AND t_551.Pedimento = DG.Pedimento AND t_551.SeccionAduanera = DG.SeccionAduanera\n" +
                    " LEFT JOIN (SELECT Patente, Pedimento, SeccionAduanera, SecuenciaFraccion,  ImportePago, ClaveContribucion, FK_DataStage FROM Contribuciones_Partida_557) AS t_557 \n" +
                    " ON t_557.FK_DataStage = DG.FK_DataStage AND t_557.Patente = t_551.Patente AND t_557.Pedimento = t_551.Pedimento AND t_557.SeccionAduanera = t_551.SeccionAduanera AND t_557.SecuenciaFraccion = t_551.SecuenciaFraccion \n" +
                    "  WHERE " + empresa + " AND t_557.ClaveContribucion IS NOT NULL GROUP BY t_557.ClaveContribucion \n" +
                    " UNION ALL\n" +
                    " SELECT CP.ClaveContribucion, sum(CP.ImportePago) MontoContribuciones, 0 Monto \n" +
                    " FROM Datos_Generales_501 DG INNER JOIN GN_DataStage AS DS ON DS.PK_DataStage = DG.FK_DataStage \n" +
                    " LEFT JOIN Contribuciones_Pedimento_510 CP ON CP.Patente = DG.Patente AND CP.Pedimento = DG.Pedimento AND CP.SeccionAduanera = DG.SeccionAduanera AND CP.FK_Datastage = DG.FK_DataStage    \n" +
                    " WHERE DG.TipoPedimento = 1 AND " + empresa +
                    " GROUP BY CP.ClaveContribucion ";*/

            log.info("Query: " + sql);

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            return BuilderResumenReporte.builderMontoTotalContribucionClave(lstPO);

        } catch (Exception e) {
            log.severe("Error al obtener el monto total por clave de contribución. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<ResumenReporteBO> getMontosTotalesImportacion(String restricciones,
                                                              String empresa, String tipoMoneda, String idioma) throws GlosaNetException {
        log.info("Obteniendo el monto total de importación");
        try {

            em.clear();

            String sql = " \n SELECT coalesce(t_551.PaisOrigenDestino, 'N/D') Pais, SUM(coalesce(t_551." + tipoMoneda + ",0)) Monto " +
                    " \n FROM Datos_Generales_501 AS DG  " +
                    " \n LEFT JOIN GN_DataStage AS DS ON DS.PK_DataStage = DG.FK_DataStage" +
                    " \n INNER JOIN (SELECT Patente, Pedimento, SeccionAduanera, PaisOrigenDestino, " + tipoMoneda + ", FK_DataStage FROM Partidas_551 " + restricciones + ") AS t_551 "
                    + " ON t_551.Patente = DG.Patente AND t_551.Pedimento = DG.Pedimento AND t_551.SeccionAduanera = DG.SeccionAduanera AND t_551.FK_DataStage = DG.FK_DataStage " +
                    " \n WHERE DG.TipoOperacion = 1 AND " + empresa +
                    " \n GROUP BY t_551.PaisOrigenDestino ORDER BY Monto desc";

            log.info("Query: " + sql);
            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = (List<Object[]>) query.getResultList();

            return BuilderResumenReporte.builderMontoTotalPais(lstPO, idioma);

        } catch (Exception e) {
            log.severe("Error al obtener el monto total de importación. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ResumenReporteBO> getMontoValorAduanaYContribucionImportacion(String restriccion, String tipoMoneda, String idioma) throws GlosaNetException {
        try {
            log.info("Obteniendo el monto valor de aduana y la contribución de importación");
            em.clear();

            String montoC = "t_557.ImportePago/DG.TipoCambio";
            //t_551.PaisOrigenDestino Pais,
            String sql = " \n SELECT coalesce(t_551.PaisOrigenDestino, 'N/D') Pais, sum(coalesce(t_551." + tipoMoneda + ",0)) Monto, "
                    + " sum(coalesce(" + montoC + ",0) ) MontoContibuciones " +
                    " \n FROM Datos_Generales_501 AS DG  " +
                    " \n LEFT JOIN GN_DataStage AS DS ON DS.PK_DataStage = DG.FK_DataStage" +
                    " \n INNER JOIN (SELECT Patente, Pedimento, SeccionAduanera, SecuenciaFraccion, PaisOrigenDestino, sum(" + tipoMoneda + ") as " + tipoMoneda + ", FK_DataStage "
                    + "				FROM Partidas_551 Group by Patente, Pedimento, SeccionAduanera, SecuenciaFraccion, PaisOrigenDestino, FK_DataStage ) AS t_551 "
                    + " ON t_551.Patente = DG.Patente AND t_551.Pedimento = DG.Pedimento AND t_551.SeccionAduanera = DG.SeccionAduanera  AND t_551.FK_DataStage = DG.FK_DataStage" +
                    " \nLEFT JOIN (SELECT Patente, Pedimento, SeccionAduanera, SecuenciaFraccion, sum(ImportePago) ImportePago FROM Contribuciones_Partida_557 "
                    + " WHERE ClaveContribucion = 6 GROUP BY Patente, Pedimento, SeccionAduanera, SecuenciaFraccion) AS t_557  "
                    + " ON t_557.Patente = t_551.Patente AND t_557.Pedimento = t_551.Pedimento AND t_557.SeccionAduanera = t_551.SeccionAduanera AND t_557.SecuenciaFraccion = t_551.SecuenciaFraccion  " +
                    " \n WHERE DG.TipoOperacion = 1 AND t_551.FK_DataStage IS NOT NULL AND " + restriccion +
                    " \n GROUP BY t_551.PaisOrigenDestino" +
                    " \n ORDER BY Monto desc";


            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            return BuilderResumenReporte.builderMontoTotalContribucionPais(lstPO, idioma);

        } catch (Exception e) {
            log.severe("Error al obtener el monto valor de aduana y contribución de importación. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ResumenReporteBO> getMontoPendienteValorAduanaYContribucionImportacion(
            String restriccion, String tipoMoneda, String idioma) throws GlosaNetException {
        try {
            log.info("Obteniendo el monto pendiente de valor de aduana y la contribución de importación");
            em.clear();

            String sql =
                    " \n SELECT  coalesce(t_551.PaisOrigenDestino, 'N/D') Pais, sum(coalesce(t_551." + tipoMoneda + ",0)) Monto " +
                            " \n FROM Datos_Generales_501 AS DG  " +
                            " \n LEFT JOIN GN_DataStage AS DS ON DS.PK_DataStage = DG.FK_DataStage" +
                            " \n INNER JOIN (SELECT Patente, Pedimento, SeccionAduanera, SecuenciaFraccion, PaisOrigenDestino, sum(" + tipoMoneda + ") as " + tipoMoneda + ", FK_DataStage FROM Partidas_551"
                            + " group by Patente, Pedimento, SeccionAduanera, SecuenciaFraccion, PaisOrigenDestino,  FK_DataStage) AS t_551 "
                            + " ON t_551.Patente = DG.Patente AND t_551.Pedimento = DG.Pedimento AND t_551.SeccionAduanera = DG.SeccionAduanera  AND t_551.FK_DataStage = DG.FK_DataStage" +
                            " \nLEFT JOIN (SELECT Patente, Pedimento, SeccionAduanera, SecuenciaFraccion FROM Contribuciones_Partida_557 "
                            + " WHERE ClaveContribucion = 6 GROUP BY Patente, Pedimento, SeccionAduanera, SecuenciaFraccion) AS t_557  "
                            + " ON t_557.Patente = t_551.Patente AND t_557.Pedimento = t_551.Pedimento AND t_557.SeccionAduanera = t_551.SeccionAduanera AND t_557.SecuenciaFraccion = t_551.SecuenciaFraccion  " +
                            " \n WHERE DG.TipoOperacion = 1 AND t_551.FK_DataStage IS NOT NULL AND " + restriccion +
                            " \n GROUP BY t_551.PaisOrigenDestino" +
                            " \n ORDER BY Monto desc";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            return BuilderResumenReporte.builderMontoTotalPais(lstPO, idioma);

        } catch (Exception e) {
            log.severe("Error al obtener el monto pendiente de valor de aduana y la contribución de importación. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /***
     * Obtiene la lista de pedimentos con el detalle de contribuciones filtrados por un rango de fecha de pago final e identificador de cliente
     * @param filtro
     * @return
     */
    @Override
    public List<DetalleContribucionesBO> getReporteContribuciones(String filtro) {
        log.info("Obteniendo el reporte de  pedimentos con detalle de contribuciones");
        em.clear();

        try {
            String sql = "" +
                    "\n SELECT ROW_NUMBER() OVER (ORDER BY Patente, Pedimento) AS RowIndex, DG.FechaPagoReal, VCP.FK_DataStage IdDatastage, gds.Folio, DG.Patente, DG.Pedimento, DG.SeccionAduanera, " +
                    "\n CASE DG.TipoOperacion WHEN 1 THEN 'Importación' WHEN 2 THEN 'Exportacion' End TipoOperacion, " +
                    "\n CASE DG.TipoPedimento WHEN 1 THEN 'Normal' WHEN 2 THEN 'Rectificación' WHEN 3 THEN 'Consolidado' WHEN 4 THEN 'Tránsito' ELSE DG.TipoPedimento END TipoPedimento, " +
                    "\n CONCAT(DG.ClaveDocumento, ' - ', CASE DG.ClaveDocumento WHEN 'A1' THEN 'Definitiva'" +
                    "\n ELSE (SELECT LOWER(Column2) from GN_Apendice_Contenido gac where FK01_Apendice = 2 AND Column1 = DG.ClaveDocumento) END) ClaveDocumento, " +
                    "\n DTA, PRV, CNT, OTROS, IVA, IGI, RECARGOS, DTA + PRV + CNT + OTROS + IVA + IGI + RECARGOS Total" +
                    "\n FROM (" +
                    "\n SELECT FK_DataStage, FK01_Cliente, Patente, Pedimento, SeccionAduanera," +
                    "\n SUM(CASE ClaveContribucion WHEN 3 THEN ImportePago ELSE 0 END) IVA," +
                    "\n SUM(CASE ClaveContribucion WHEN 6 THEN ImportePago ELSE 0 END) IGI," +
                    "\n SUM(CASE ClaveContribucion WHEN 1 THEN ImportePago ELSE 0 END) DTA," +
                    "\n SUM(CASE ClaveContribucion WHEN 15 THEN ImportePago ELSE 0 END) PRV," +
                    "\n SUM(CASE ClaveContribucion WHEN 21 THEN ImportePago ELSE 0 END) CNT," +
                    "\n SUM(CASE ClaveContribucion WHEN 7 THEN ImportePago ELSE 0 END) RECARGOS," +
                    "\n SUM(CASE WHEN ClaveContribucion NOT IN(3,6,1,15,21,7) THEN ImportePago ELSE 0 END) OTROS" +
                    "\n FROM V_Contribuciones_Pedimento WHERE Pedimento != 0 " +
                    "\n GROUP BY FK_DataStage, FK01_Cliente, Patente, Pedimento, SeccionAduanera) VCP " +
                    "\n INNER JOIN Datos_Generales_501 DG ON DG.FK_DataStage = VCP.FK_DataStage AND DG.Patente = VCP.Patente AND DG.Pedimento = VCP.Pedimento AND DG.SeccionAduanera = VCP.SeccionAduanera" +
                    "\n INNER JOIN GN_DataStage gds ON gds.PK_DataStage = VCP.FK_DataStage " +
                    "\n WHERE " + filtro +
                    "\n ORDER BY Patente, Pedimento";

            Query query = em.createNativeQuery(sql, DetalleContribucionesPO.class);
            List<DetalleContribucionesPO> lstPO = query.getResultList();
            List<DetalleContribucionesBO> lstBO = new ArrayList<>();

            for (DetalleContribucionesPO po : lstPO) {
                lstBO.add(new DetalleContribucionesBO(po));
            }
            return lstBO;
        }catch (Exception ex) {
            log.severe("Error al obtener reporte de pedimentos con detalle de contribuciones. " + ex.getMessage());
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     * @param parametros
     * @return
     */
    @Override
    public List<DetallePartidasBO> getReportePartidas(ParametrosPartida parametros) {
        log.info(String.format("Obteniendo el detalle de partidas del Id_Datastage=%d Patente=%s Pedimento=%s SeccionAduanera=%s para el reporte prediseñado",
                parametros.getIdDatastage(), parametros.getPatente(), parametros.getPedimento(), parametros.getSeccionAduanera()));
        em.clear();

        try {
            String sql = "" +
                    "\n SELECT ROW_NUMBER() OVER (ORDER BY f.FK_DataStage, f.Patente, f.Pedimento, f.SeccionAduanera, p.SecuenciaFraccion) RowIndex, f.FK_DataStage, GDS.Folio, " +
                    "\n f.Patente, f.Pedimento, f.SeccionAduanera, p.SecuenciaFraccion, p.FechaPagoReal, p.PaisCompradorVendedor, f.IndentFiscalProveedor, f.ProveedorMercancia, p.ValorAduana, \n" +
                    "\n SUM(CASE cp.ClaveContribucion WHEN 3 THEN cp.ImportePago ELSE 0 END) IVA, SUM(CASE cp.ClaveContribucion WHEN 6 THEN cp.ImportePago ELSE 0 END) IGI\n" +
                    "\n FROM Partidas_551 p INNER JOIN GN_DataStage GDS on p.FK_DataStage = GDS.PK_DataStage INNER JOIN Contribuciones_Partida_557 cp \n" +
                    "\n ON cp.FK_DataStage = p.FK_DataStage AND cp.Patente = p.Patente AND cp.Pedimento = p.Pedimento AND cp.SeccionAduanera = p.SeccionAduanera AND cp.SecuenciaFraccion = p.SecuenciaFraccion \n" +
                    "\n INNER JOIN Facturas_505 f ON f.FK_DataStage = p.FK_DataStage AND f.Patente = p.Patente AND f.Pedimento = p.Pedimento AND f.SeccionAduanera = p.SeccionAduanera \n" +
                    "\n WHERE p.FK_DataStage = " + parametros.getIdDatastage() + " AND p.Patente = '" + parametros.getPatente() + "' AND p.Pedimento = '" + parametros.getPedimento() + "' " +
                    "\n AND p.SeccionAduanera = '" + parametros.getSeccionAduanera() + "' " +
                    "\n GROUP BY f.FK_DataStage, GDS.Folio, f.Patente, f.Pedimento, f.SeccionAduanera, SecuenciaFraccion, p.FechaPagoReal, p.PaisCompradorVendedor, f.IndentFiscalProveedor, f.ProveedorMercancia, p.ValorAduana \n" +
                    "\n ORDER BY SecuenciaFraccion ";

            Query query = em.createNativeQuery(sql, DetallePartidasPO.class);
            List<DetallePartidasPO> lstPO = query.getResultList();
            List<DetallePartidasBO> lstBO = new ArrayList<>();

            for (DetallePartidasPO po : lstPO) {
                lstBO.add(new DetallePartidasBO(po));
            }
            return lstBO;
        }catch (Exception ex) {
            log.severe("Error al obtener el detalle de partidas de un pedimento para el reporte prediseñado. " + ex.getMessage());
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    @Transactional
    public Page<DetallePartidasBO> getReportePartidas(String filtro, Integer page, Integer size) {
        log.info("Obteniendo el reporte prediseñado con paginación de partidas con los parámetros: " + filtro);
        Page<DetallePartidasBO> result = new Page<>();
        Session session = em.unwrap(Session.class);

        try {
            String sql = "" +
                    "\n SELECT ROW_NUMBER() OVER (ORDER BY f.FK_DataStage, f.Patente, f.Pedimento, f.SeccionAduanera, p.SecuenciaFraccion) RowIndex, " +
                    "\n f.FK_DataStage, GDS.Folio, f.Patente, f.Pedimento, f.SeccionAduanera, p.SecuenciaFraccion, p.FechaPagoReal, p.PaisCompradorVendedor, f.IndentFiscalProveedor, f.ProveedorMercancia, p.ValorAduana, " +
                    "\n SUM(CASE cp.ClaveContribucion WHEN 3 THEN cp.ImportePago ELSE 0 END) IVA, SUM(CASE cp.ClaveContribucion WHEN 6 THEN cp.ImportePago ELSE 0 END) IGI" +
                    "\n FROM Partidas_551 p INNER JOIN GN_DataStage GDS on p.FK_DataStage = GDS.PK_DataStage" +
                    "\n INNER JOIN Contribuciones_Partida_557 cp " +
                    "\n ON cp.FK_DataStage = p.FK_DataStage AND cp.Patente = p.Patente AND cp.Pedimento = p.Pedimento AND cp.SeccionAduanera = p.SeccionAduanera AND cp.SecuenciaFraccion = p.SecuenciaFraccion " +
                    "\n INNER JOIN Facturas_505 f ON f.FK_DataStage = p.FK_DataStage AND f.Patente = p.Patente AND f.Pedimento = p.Pedimento AND f.SeccionAduanera = p.SeccionAduanera " +
                    "\n WHERE " + filtro +
                    "\n GROUP BY f.FK_DataStage, GDS.Folio, f.Patente, f.Pedimento, f.SeccionAduanera, SecuenciaFraccion, p.FechaPagoReal, p.PaisCompradorVendedor, f.IndentFiscalProveedor, f.ProveedorMercancia, p.ValorAduana " +
                    "\n ORDER BY f.FK_DataStage, f.Patente, f.Pedimento, f.SeccionAduanera, p.SecuenciaFraccion ";

            NativeQuery query = session.createNativeQuery(sql, DetallePartidasPO.class);
            ScrollableResults resultScroll = query.scroll(ScrollMode.SCROLL_INSENSITIVE);

            resultScroll.last();
            int totalResults = resultScroll.getRowNumber() + 1;

            resultScroll.first();
            int position = page * size;
            resultScroll.scroll(position < totalResults ? position : 0);

            List<DetallePartidasBO> lstBO = new ArrayList<>();

            int i = 0;
            while (size > i++) {
                Object[] resultEntry = resultScroll.get();
                if (resultEntry != null)
                    lstBO.add(new DetallePartidasBO((DetallePartidasPO) resultEntry[0]));
                if (!resultScroll.next())
                    break;
            }

            result.setResults(lstBO);
            result.setTotal(totalResults);

            return result;
        }catch (Exception ex) {
            log.severe("Error al obtener el detalle de partidas de un pedimento para el reporte prediseñado. " + ex.getMessage());
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    @Transactional
    public List<DetallePartidasBO> getReportePartidas(String filtro) {
        log.info("Obteniendo el reporte prediseñado completo de partidas con los parámetros: " + filtro);
        Page<DetallePartidasBO> result = new Page<>();
        em.clear();

        try {
            String sql = "" +
                    "\n SELECT ROW_NUMBER() OVER (ORDER BY f.FK_DataStage, f.Patente, f.Pedimento, f.SeccionAduanera, p.SecuenciaFraccion) RowIndex, " +
                    "\n f.FK_DataStage, GDS.Folio, f.Patente, f.Pedimento, f.SeccionAduanera, p.SecuenciaFraccion, p.FechaPagoReal, p.PaisCompradorVendedor, f.IndentFiscalProveedor, f.ProveedorMercancia, p.ValorAduana, " +
                    "\n SUM(CASE cp.ClaveContribucion WHEN 3 THEN cp.ImportePago ELSE 0 END) IVA, SUM(CASE cp.ClaveContribucion WHEN 6 THEN cp.ImportePago ELSE 0 END) IGI" +
                    "\n FROM Partidas_551 p INNER JOIN GN_DataStage GDS on p.FK_DataStage = GDS.PK_DataStage" +
                    "\n INNER JOIN Contribuciones_Partida_557 cp " +
                    "\n ON cp.FK_DataStage = p.FK_DataStage AND cp.Patente = p.Patente AND cp.Pedimento = p.Pedimento AND cp.SeccionAduanera = p.SeccionAduanera AND cp.SecuenciaFraccion = p.SecuenciaFraccion " +
                    "\n INNER JOIN Facturas_505 f ON f.FK_DataStage = p.FK_DataStage AND f.Patente = p.Patente AND f.Pedimento = p.Pedimento AND f.SeccionAduanera = p.SeccionAduanera " +
                    "\n WHERE " + filtro +
                    "\n GROUP BY f.FK_DataStage, GDS.Folio, f.Patente, f.Pedimento, f.SeccionAduanera, SecuenciaFraccion, p.FechaPagoReal, p.PaisCompradorVendedor, f.IndentFiscalProveedor, f.ProveedorMercancia, p.ValorAduana " +
                    "\n ORDER BY f.FK_DataStage, f.Patente, f.Pedimento, f.SeccionAduanera, p.SecuenciaFraccion ";

            Query query = em.createNativeQuery(sql, DetallePartidasPO.class);
            List<DetallePartidasPO> lstPO = query.getResultList();
            List<DetallePartidasBO> lstBO = new ArrayList<>();

            for (DetallePartidasPO po: lstPO) {
                lstBO.add(new DetallePartidasBO(po));
            }

            return lstBO;
        }catch (Exception ex) {
            log.severe("Error al obtener el detalle de partidas de un periodo del reporte prediseñado. " + ex.getMessage());
            ex.printStackTrace();
            throw ex;
        }
    }
}
