package org.mx.persistencia.datastage.impl;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.mx.controllers.datastage.DatastageRequestOptions;
import org.mx.controllers.datastage.VUCEMSolicitudRequestOptions;
import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.datastage.bo.*;
import org.mx.modelo.datastage.po.*;
import org.mx.persistencia.Page;
import org.mx.persistencia.datastage.DatastageDAO;
import org.mx.persistencia.datastage.builder.BuilderDatastage;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository
public class DatastageDAOImpl implements DatastageDAO {

    @PersistenceContext
    private EntityManager em;

    private final static Logger LOGGER = Logger.getLogger(DatastageDAOImpl.class.getName());

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Page<DatosGeneralesCustomBO> getDatosGenerales(DatastageRequestOptions requestOptions)
            throws GlosaNetException {
        LOGGER.info("@getDatosGenerales: Inicio");
        try {
            Session session = em.unwrap(Session.class);

            StringBuilder sqlQuery = new StringBuilder();
            sqlQuery.append("" +
                    "SELECT DG.PK_Datos_Generales, DG.SeccionAduaneraEntrada, DG.SeccionAduanera, DG.Patente, " +
                    "DG.Pedimento, DG.TipoOperacion, DG.ClaveDocumento, DG.TipoPedimento, DG.FechaRecepcionPedimento, " +
                    "DG.FechaPagoReal, O.Observaciones, 0 AS PK_DataStage, (@row_number \\:= @row_number + 1) AS RecordIndex " +
                    "FROM Datos_Generales_501 AS DG " +
                    "CROSS JOIN ( SELECT @row_number \\:= 0 ) as parameters " +
                    "INNER JOIN ( SELECT OP.Pedimento, OP.Patente, OP.SeccionAduanera, GROUP_CONCAT(OP.Observaciones " +
                    "ORDER BY OP.SecuenciaObservacion SEPARATOR '\\r\\n' ) as Observaciones, OP.FK_DataStage FROM Observaciones_Pedimento_511 AS OP " +
                    "GROUP BY OP.FK_DataStage, OP.Patente, OP.Pedimento, OP.SeccionAduanera ) AS O ON DG.FK_Datastage = O.FK_Datastage AND DG.Patente = O.Patente AND DG.Pedimento = O.Pedimento AND DG.SeccionAduanera = O.SeccionAduanera " +
                    "LEFT JOIN (SELECT Patente, Pedimento, SeccionAduanera, GROUP_CONCAT(DISTINCT Fraccion SEPARATOR ', ') Fracciones " +
                    "FROM Partidas_551 WHERE FK_Datastage = :idDatastage GROUP BY Patente, Pedimento, SeccionAduanera) p " +
                    "ON p.Patente = DG.Patente AND p.Pedimento = DG.Pedimento AND p.SeccionAduanera = DG.SeccionAduanera " +
                    "WHERE DG.FK_Datastage = :idDatastage ");

            String filterInitialDate = requestOptions.getFilterInitialDate();
            String filterFinalDate = requestOptions.getFilterFinalDate();
            String tipoOperacion = requestOptions.getFilterTipoOperacion();
            String tipoPedimento = requestOptions.getFilterTipoPedimento();
            String searchTerm = requestOptions.getSearchTerm();


            if (!StringUtils.equals(filterInitialDate, "")) {
                sqlQuery.append("AND DG.FechaRecepcionPedimento >= '").append(filterInitialDate).append("' \n");
            }

            if (!StringUtils.equals(filterFinalDate, "")) {
                sqlQuery.append("AND DG.FechaRecepcionPedimento <= '").append(filterFinalDate).append("' \n");
            }

            if (!StringUtils.equals(tipoOperacion, "")) {
                sqlQuery.append("AND DG.TipoOperacion = '").append(tipoOperacion).append("' \n");
            }

            if (!StringUtils.equals(tipoPedimento, "")) {
                sqlQuery.append("AND DG.TipoPedimento = '").append(tipoPedimento).append("' \n");
            }

            if (!StringUtils.equals(searchTerm, "")) {
                sqlQuery.append("AND (DG.Patente LIKE '%").append(searchTerm)
                        .append("%' OR DG.Pedimento LIKE '%").append(searchTerm).append("%' \n")
                        .append("OR Fracciones LIKE '%").append(searchTerm).append("%') \n");
            }

            sqlQuery.append("ORDER BY DG.Patente, DG.Pedimento;");


            NativeQuery query = session.createNativeQuery(sqlQuery.toString(), DatosGeneralesCustomPO.class);
            query.setParameter("idDatastage", requestOptions.getIdDatastage());

            ScrollableResults resultScroll = query.scroll(ScrollMode.SCROLL_INSENSITIVE);

            resultScroll.last();
            int totalResults = resultScroll.getRowNumber() + 1;

            resultScroll.first();
            int position = requestOptions.getPage() * requestOptions.getSize();
            resultScroll.scroll(position < totalResults ? position : 0);

            List<DatosGeneralesCustomBO> resultPage = new ArrayList<DatosGeneralesCustomBO>();
            int i = 0;
            while (requestOptions.getSize() > i++) {
                Object[] resultEntry = resultScroll.get();
                if (resultEntry != null)
                    resultPage.add(BuilderDatastage.builderDatosGenerales((DatosGeneralesCustomPO) resultEntry[0]));
                if (!resultScroll.next())
                    break;
            }

            Page<DatosGeneralesCustomBO> page = new Page<>();
            page.setResults(resultPage);
            page.setTotal(totalResults);

            return page;

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.severe("@getDatosGenerales: exception -> " + e.toString());
            throw new GlosaNetException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Page<FacturaCustomBO> getFacturas(DatastageRequestOptions requestOptions)
            throws GlosaNetException {
        LOGGER.info("@getFacturas: Inicio");
        try {
            Session session = em.unwrap(Session.class);
            String searchTerm = requestOptions.getSearchTerm();
            StringBuilder sqlQuery = new StringBuilder("SELECT TBL.*, (@row_number \\:= @row_number + 1) AS RecordIndex FROM (SELECT \n");
            sqlQuery.append("PK_Facturas, SeccionAduanera, Patente, Pedimento, FechaFacturacion, NumeroFactura, \n");
            sqlQuery.append("TerminoFacturacion, ProveedorMercancia, CalleProveedor, gvac.errorMessage, \n");
            sqlQuery.append("COALESCE(gvac.status, -1) AS Status, gvac.PK_SolicitudAcuseFactura AS idSolicitudFactura, gvac.path AS Path \n");
            sqlQuery.append("FROM Facturas_505 F \n");
            sqlQuery.append("LEFT JOIN GN_Vucem_AcuseCove gvac ON F.PK_Facturas = gvac.idReceipt \n");
            sqlQuery.append("WHERE Pedimento = :pedimento AND FK_Datastage = :idDatastage \n");
            if (!StringUtils.equals(searchTerm, ""))
                sqlQuery.append("AND (NumeroFactura LIKE '%").append(searchTerm).append("%') \n");
            sqlQuery.append("ORDER BY Pedimento, FechaFacturacion DESC");
            sqlQuery.append(") TBL CROSS JOIN ( SELECT @row_number \\:= 0 ) as parameters; ");

            NativeQuery query = session.createNativeQuery(sqlQuery.toString(), FacturaCustomPO.class);
            query.setParameter("idDatastage", requestOptions.getIdDatastage());
            query.setParameter("pedimento", requestOptions.getPedimento());

            ScrollableResults resultScroll = query.scroll(ScrollMode.SCROLL_INSENSITIVE);

            resultScroll.last();
            int totalResults = resultScroll.getRowNumber() + 1;

            resultScroll.first();
            int position = requestOptions.getPage() * requestOptions.getSize();
            resultScroll.scroll(position < totalResults ? position : 0);

            List<FacturaCustomBO> resultPage = new ArrayList<FacturaCustomBO>();
            int i = 0;
            while (requestOptions.getSize() > i++) {
                Object[] resultEntry = resultScroll.get();
                if (resultEntry != null)
                    resultPage.add(BuilderDatastage.builderFactura((FacturaCustomPO) resultEntry[0]));
                if (!resultScroll.next())
                    break;
            }

            Page<FacturaCustomBO> page = new Page<>();
            page.setResults(resultPage);
            page.setTotal(totalResults);

            return page;

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.severe("@getFacturas: exception -> " + e.toString());
            throw new GlosaNetException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Page<CasosPedimentoCustomBO> getCasosPedimentos(DatastageRequestOptions requestOptions)
            throws GlosaNetException {
        LOGGER.info("@getCasosPedimentos: Inicio");
        try {
            Session session = em.unwrap(Session.class);
            String searchTerm = requestOptions.getSearchTerm();

            StringBuilder sqlQuery = new StringBuilder("" +
                    "SELECT TBL.*, (@row_number \\:= @row_number + 1) AS RecordIndex " +
                    "FROM (SELECT CP.PK_Casos_Pedimento, SeccionAduanera, Patente, Pedimento, " +
                    "gvne.PK_SolicitudEdoc idSolicitudEdoc, gvae.PK_SolicitudAcuseEdoc idSolicitudAcuse, " +
                    "ClaveCaso, IdentificadorCaso, COALESCE(gvne.status, -1) StatusEdoc, gvne.errorMessage ErrorMessageEdoc, gvae.errorMessage ErrorMessageAcuse, " +
                    "COALESCE(gvae.status, -1) StatusAcuse, gvne.path AS PathEdoc, gvae.path AS PathAcuse  FROM Casos_Pedimento_507 CP " +
                    "LEFT JOIN GN_Vucem_Edoc gvne ON gvne.idRequestCase = CP.PK_Casos_Pedimento " +
                    "LEFT JOIN GN_Vucem_AcuseEdoc gvae ON gvae.idRequestCase = CP.PK_Casos_Pedimento " +
                    "WHERE Pedimento = :pedimento AND FK_Datastage = :idDatastage ");
            if (!StringUtils.equals(searchTerm, ""))
                sqlQuery.append("AND (IdentificadorCaso LIKE '%").append(searchTerm).append("%') \n");
            sqlQuery.append("ORDER BY ClaveCaso DESC, IdentificadorCaso) TBL " +
                    "CROSS JOIN ( SELECT @row_number \\:= 0 ) as parameters; ");

            NativeQuery query = session.createNativeQuery(sqlQuery.toString(), CasosPedimentoCustomPO.class);
            query.setParameter("idDatastage", requestOptions.getIdDatastage());
            query.setParameter("pedimento", requestOptions.getPedimento());

            ScrollableResults resultScroll = query.scroll(ScrollMode.SCROLL_INSENSITIVE);

            resultScroll.last();
            int totalResults = resultScroll.getRowNumber() + 1;

            resultScroll.first();
            int position = requestOptions.getPage() * requestOptions.getSize();
            resultScroll.scroll(position < totalResults ? position : 0);

            List<CasosPedimentoCustomBO> resultPage = new ArrayList<>();
            int i = 0;
            while (requestOptions.getSize() > i++) {
                Object[] resultEntry = resultScroll.get();
                if (resultEntry != null)
                    resultPage.add(BuilderDatastage.builderCasosPedimento((CasosPedimentoCustomPO) resultEntry[0]));
                if (!resultScroll.next())
                    break;
            }

            Page<CasosPedimentoCustomBO> page = new Page<>();
            page.setResults(resultPage);
            page.setTotal(totalResults);

            return page;

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.severe("@getCasosPedimentos: exception -> " + e.toString());
            throw new GlosaNetException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Page<PartidaCustomBO> getPartidas(DatastageRequestOptions requestOptions)
            throws GlosaNetException {
        LOGGER.info("@getPartidas: Inicio");
        try {
            Session session = em.unwrap(Session.class);
            String searchTerm = requestOptions.getSearchTerm();

            StringBuilder sqlQuery = new StringBuilder("SELECT \n");
            sqlQuery.append("PK_Partida, SeccionAduanera, Patente, Pedimento, Fraccion, SecuenciaFraccion, SubdivisionFraccion, \n");
            sqlQuery.append("DescripcionMercancia, ValorDolares, PaisOrigenDestino, PaisCompradorVendedor, \n");
            sqlQuery.append("(@row_number \\:= @row_number + 1) AS RecordIndex \n");
            sqlQuery.append("FROM Partidas_551 CROSS JOIN ( SELECT @row_number \\:= 0 ) as parameters \n");
            sqlQuery.append("WHERE Pedimento = :pedimento AND FK_Datastage = :idDatastage \n");

            if (!StringUtils.equals(searchTerm, "")) {
                sqlQuery.append("AND ((DescripcionMercancia LIKE '%").append(searchTerm).append("%') \n");
                sqlQuery.append("OR (Fraccion LIKE '%").append(searchTerm).append("%')) \n");
            }

            sqlQuery.append("ORDER BY Pedimento, SecuenciaFraccion;");

            NativeQuery query = session.createNativeQuery(sqlQuery.toString(), PartidaCustomPO.class);
            query.setParameter("idDatastage", requestOptions.getIdDatastage());
            query.setParameter("pedimento", requestOptions.getPedimento());

            ScrollableResults resultScroll = query.scroll(ScrollMode.SCROLL_INSENSITIVE);

            resultScroll.last();
            int totalResults = resultScroll.getRowNumber() + 1;

            resultScroll.first();
            int position = requestOptions.getPage() * requestOptions.getSize();
            resultScroll.scroll(position < totalResults ? position : 0);

            List<PartidaCustomBO> resultPage = new ArrayList<PartidaCustomBO>();
            int i = 0;
            while (requestOptions.getSize() > i++) {
                Object[] resultEntry = resultScroll.get();
                if (resultEntry != null)
                    resultPage.add(BuilderDatastage.builderPartida((PartidaCustomPO) resultEntry[0]));
                if (!resultScroll.next())
                    break;
            }

            Page<PartidaCustomBO> page = new Page<>();
            page.setResults(resultPage);
            page.setTotal(totalResults);

            return page;

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.severe("@getPartidas: exception -> " + e.toString());
            throw new GlosaNetException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Page<DatosGeneralesCustomBO> getDatosGeneralesPorCliente(DatastageRequestOptions requestOptions)
        throws GlosaNetException {
        LOGGER.info("@getDatosGeneralesPorCliente: Inicio");
        try {
            Session session = em.unwrap(Session.class);

            StringBuilder sqlQuery = new StringBuilder("SELECT CP.*, (@row_num \\:= @row_num + 1) AS RecordIndex \n");
            sqlQuery.append("FROM ( \n");
            sqlQuery.append("SELECT DG.PK_Datos_Generales, DG.SeccionAduaneraEntrada, DG.SeccionAduanera, DG.Patente, DG.Pedimento, \n");
            sqlQuery.append("DG.TipoOperacion, DG.ClaveDocumento, DG.TipoPedimento, DG.FechaRecepcionPedimento, DG.FechaPagoReal, \n");
            sqlQuery.append("O.Observaciones, DS.PK_DataStage \n");
            sqlQuery.append("FROM GN_DataStage AS DS \n");
            sqlQuery.append("LEFT JOIN Datos_Generales_501 AS DG ON DG.FK_DataStage = DS.PK_DataStage \n");
            sqlQuery.append("INNER JOIN ( \n");
            sqlQuery.append("SELECT OP.FK_DataStage, OP.Pedimento, OP.Patente, OP.SeccionAduanera, GROUP_CONCAT(OP.Observaciones  ORDER BY OP.SecuenciaObservacion SEPARATOR '\\r\\n' ) as Observaciones \n");
            sqlQuery.append("FROM Observaciones_Pedimento_511 AS OP GROUP BY OP.FK_DataStage, OP.Pedimento, OP.Patente, OP.SeccionAduanera \n");
            sqlQuery.append(") AS O ON DG.FK_Datastage = O.FK_Datastage AND DG.Patente = O.Patente AND DG.Pedimento = O.Pedimento AND DG.SeccionAduanera = O.SeccionAduanera \n");
            sqlQuery.append("WHERE DS.FK01_Cliente = :idClient \n");

            String filterInitialDate = requestOptions.getFilterInitialDate();
            String filterFinalDate = requestOptions.getFilterFinalDate();
            String pedimento = requestOptions.getPedimento();

            if (!StringUtils.equals(filterInitialDate, ""))
                sqlQuery.append("AND DG.FechaRecepcionPedimento >= '").append(filterInitialDate).append("' \n");

            if (!StringUtils.equals(filterFinalDate, ""))
                sqlQuery.append("AND DG.FechaRecepcionPedimento <= '").append(filterFinalDate).append("' \n");

            if (!StringUtils.equals(pedimento, ""))
                sqlQuery.append("AND DG.Pedimento LIKE '%").append(pedimento).append("%' \n");

            sqlQuery.append("ORDER BY DG.Patente, DG.Pedimento \n");
            sqlQuery.append(") CP CROSS JOIN \n");
            sqlQuery.append("(SELECT @row_num \\:= 0) params; \n");

            NativeQuery query = session.createNativeQuery(sqlQuery.toString(), DatosGeneralesCustomPO.class);
            query.setParameter("idClient", requestOptions.getIdCompany());

            ScrollableResults resultScroll = query.scroll(ScrollMode.SCROLL_INSENSITIVE);

            resultScroll.last();
            int totalResults = resultScroll.getRowNumber() + 1;

            resultScroll.first();
            int position = requestOptions.getPage() * requestOptions.getSize();
            resultScroll.scroll(position < totalResults ? position : 0);

            List<DatosGeneralesCustomBO> resultPage = new ArrayList<DatosGeneralesCustomBO>();
            int i = 0;
            while (requestOptions.getSize() > i++) {
                Object[] resultEntry = resultScroll.get();
                if (resultEntry != null)
                    resultPage.add(BuilderDatastage.builderDatosGenerales((DatosGeneralesCustomPO) resultEntry[0]));
                if (!resultScroll.next())
                    break;
            }

            Page<DatosGeneralesCustomBO> page = new Page<>();
            page.setResults(resultPage);
            page.setTotal(totalResults);

            return page;

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.severe("@getDatosGeneralesPorCliente: exception -> " + e.toString());
            throw new GlosaNetException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Page<FacturaCustomBO> getFacturasPorCliente(DatastageRequestOptions requestOptions)
            throws GlosaNetException {
        LOGGER.info("@getFacturasPorCliente: Inicio");
        try {
            Session session = em.unwrap(Session.class);

            String filterInitialDate = requestOptions.getFilterInitialDate();
            String filterFinalDate = requestOptions.getFilterFinalDate();
            String pedimento = requestOptions.getPedimento();
            String searchTerm = requestOptions.getSearchTerm();

            StringBuilder sqlQuery = new StringBuilder("" +
                    "SELECT F.*, (@row_num \\:= @row_num + 1) AS RecordIndex " +
                    "FROM ( SELECT F.PK_Facturas, F.SeccionAduanera, F.Patente, F.Pedimento, COALESCE(gvac.status, -1) Status, gvac.errorMessage, " +
                    "F.FechaFacturacion, F.NumeroFactura, F.TerminoFacturacion, F.ProveedorMercancia, F.CalleProveedor, " +
                    "gvac.PK_SolicitudAcuseFactura idSolicitudFactura, gvac.path AS Path FROM GN_DataStage AS DS " +
                    "LEFT JOIN Datos_Generales_501 AS DG ON DG.FK_DataStage = DS.PK_DataStage " +
                    "LEFT JOIN Facturas_505 AS F ON F.FK_DataStage = DG.FK_DataStage " +
                    "AND F.Patente = DG.Patente " +
                    "AND F.Pedimento = DG.Pedimento " +
                    "AND F.SeccionAduanera = DG.SeccionAduanera " +
                    "LEFT JOIN GN_Vucem_AcuseCove gvac ON F.PK_Facturas = gvac.idReceipt " +
                    "WHERE F.Pedimento IS NOT NULL AND DS.FK01_Cliente = :idClient \n");

            if (!StringUtils.equals(filterInitialDate, ""))
                sqlQuery.append("AND DG.FechaRecepcionPedimento >= '").append(filterInitialDate).append("' \n");

            if (!StringUtils.equals(filterFinalDate, ""))
                sqlQuery.append("AND DG.FechaRecepcionPedimento <= '").append(filterFinalDate).append("' \n");

            if (!StringUtils.equals(pedimento, ""))
                sqlQuery.append("AND F.Pedimento LIKE '%").append(pedimento).append("%' \n");

            if (!StringUtils.equals(searchTerm, ""))
                sqlQuery.append("AND F.NumeroFactura LIKE '%").append(searchTerm).append("%' \n");

            sqlQuery.append("GROUP BY F.PK_Facturas, gvac.idReceipt, gvac.status, gvac.errorMessage, gvac.PK_SolicitudAcuseFactura " +
                    " ORDER BY F.Pedimento, F.FechaFacturacion DESC \n");

            sqlQuery.append(") F CROSS JOIN \n");
            sqlQuery.append("(SELECT @row_num \\:= 0) params; \n");

            NativeQuery query = session.createNativeQuery(sqlQuery.toString(), FacturaCustomPO.class);
            query.setParameter("idClient", requestOptions.getIdCompany());

            ScrollableResults resultScroll = query.scroll(ScrollMode.SCROLL_INSENSITIVE);
            LOGGER.info("Query: " + query.getQueryString());
            resultScroll.last();
            int totalResults = resultScroll.getRowNumber() + 1;

            resultScroll.first();
            int position = requestOptions.getPage() * requestOptions.getSize();
            resultScroll.scroll(position < totalResults ? position : 0);

            List<FacturaCustomBO> resultPage = new ArrayList<FacturaCustomBO>();
            int i = 0;
            while (requestOptions.getSize() > i++) {
                Object[] resultEntry = resultScroll.get();
                if (resultEntry != null)
                    resultPage.add(BuilderDatastage.builderFactura((FacturaCustomPO) resultEntry[0]));
                if (!resultScroll.next())
                    break;
            }

            Page<FacturaCustomBO> page = new Page<>();
            page.setResults(resultPage);
            page.setTotal(totalResults);

            return page;

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.severe("@getFacturasPorCliente: exception -> " + e.toString());
            throw new GlosaNetException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Page<CasosPedimentoCustomBO> getCasosPedimentosPorCliente(DatastageRequestOptions requestOptions)
            throws GlosaNetException {
        LOGGER.info("@getCasosPedimentosPorCliente: Inicio");
        try {
            Session session = em.unwrap(Session.class);

            StringBuilder sqlQuery = new StringBuilder("SELECT CP.*, (@row_num \\:= @row_num + 1) AS RecordIndex \n");
            sqlQuery.append("FROM ( SELECT \n");
            sqlQuery.append("CP.PK_Casos_Pedimento, CP.SeccionAduanera, CP.Patente, CP.Pedimento, \n");
            sqlQuery.append("CP.ClaveCaso, CP.IdentificadorCaso, COALESCE(gvne.status, -1) StatusEdoc, COALESCE(gvae.status, -1) StatusAcuse, gvne.errorMessage ErrorMessageEdoc, gvae.errorMessage ErrorMessageAcuse, \n");
            sqlQuery.append("gvne.PK_SolicitudEdoc idSolicitudEdoc, gvae.PK_SolicitudAcuseEdoc idSolicitudAcuse, gvne.path AS PathEdoc, gvae.path AS PathAcuse FROM GN_DataStage AS DS \n");
            sqlQuery.append("LEFT JOIN Datos_Generales_501 AS DG ON DG.FK_DataStage = DS.PK_DataStage \n");
            sqlQuery.append("LEFT JOIN Casos_Pedimento_507 AS CP ON CP.FK_DataStage = DG.FK_DataStage \n");
            sqlQuery.append("AND CP.Patente = DG.Patente \n");
            sqlQuery.append("AND CP.Pedimento = DG.Pedimento \n");
            sqlQuery.append("AND CP.SeccionAduanera = DG.SeccionAduanera \n");
            sqlQuery.append("LEFT JOIN GN_Vucem_Edoc gvne ON gvne.idRequestCase = CP.PK_Casos_Pedimento \n");
            sqlQuery.append("LEFT JOIN GN_Vucem_AcuseEdoc gvae ON gvae.idRequestCase = CP.PK_Casos_Pedimento \n");
            sqlQuery.append("WHERE DS.FK01_Cliente = :idClient \n");

            String filterInitialDate = requestOptions.getFilterInitialDate();
            String filterFinalDate = requestOptions.getFilterFinalDate();
            String pedimento = requestOptions.getPedimento();
            String searchTerm = requestOptions.getSearchTerm();

            if (!StringUtils.equals(filterInitialDate, "")) {
                sqlQuery.append("AND DG.FechaRecepcionPedimento >= '").append(filterInitialDate).append("' \n");
            }

            if (!StringUtils.equals(filterFinalDate, "")) {
                sqlQuery.append("AND DG.FechaRecepcionPedimento <= '").append(filterFinalDate).append("' \n");
            }

            if (!StringUtils.equals(pedimento, "")) {
                sqlQuery.append("AND CP.Pedimento LIKE '%").append(pedimento).append("%' \n");
            }

            if (!StringUtils.equals(searchTerm, "")) {
                sqlQuery.append("AND CP.IdentificadorCaso LIKE '%").append(searchTerm).append("%' \n");
            }

            sqlQuery.append("ORDER BY CP.ClaveCaso DESC, CP.IdentificadorCaso \n");

            sqlQuery.append(") CP CROSS JOIN \n");
            sqlQuery.append("(SELECT @row_num \\:= 0) params; \n");

            NativeQuery query = session.createNativeQuery(sqlQuery.toString(), CasosPedimentoCustomPO.class);
            query.setParameter("idClient", requestOptions.getIdCompany());

            ScrollableResults resultScroll = query.scroll(ScrollMode.SCROLL_INSENSITIVE);

            resultScroll.last();
            int totalResults = resultScroll.getRowNumber() + 1;

            resultScroll.first();
            int position = requestOptions.getPage() * requestOptions.getSize();
            resultScroll.scroll(position < totalResults ? position : 0);

            List<CasosPedimentoCustomBO> resultPage = new ArrayList<CasosPedimentoCustomBO>();
            int i = 0;
            while (requestOptions.getSize() > i++) {
                Object[] resultEntry = resultScroll.get();
                if (resultEntry != null)
                    resultPage.add(BuilderDatastage.builderCasosPedimento((CasosPedimentoCustomPO) resultEntry[0]));
                if (!resultScroll.next())
                    break;
            }

            Page<CasosPedimentoCustomBO> page = new Page<>();
            page.setResults(resultPage);
            page.setTotal(totalResults);

            return page;

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.severe("@getCasosPedimentosPorCliente: exception -> " + e.toString());
            throw new GlosaNetException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Page<CasosPedimentoCustomBO> getCasosPedimentosPorDatastage(VUCEMSolicitudRequestOptions requestOptions) throws GlosaNetException {
        LOGGER.info("@getCasosPedimentosPorDatastage: Inicio");
        try {
            if (0 == requestOptions.getIdDatastage()) throw new GlosaNetException("idDatastage cannot be zero");
            if (0 == requestOptions.getSize()) throw new GlosaNetException("size of page cannot be zero");

            Session session = em.unwrap(Session.class);

            StringBuilder sqlQuery = new StringBuilder("" +
                    "SELECT CP.PK_Casos_Pedimento idCasosPedimento, CP.Pedimento, CP.IdentificadorCaso, DS.FK01_Cliente AS idCliente " +
                    "FROM GN_DataStage AS DS " +
                    "LEFT JOIN Datos_Generales_501 AS DG ON DG.FK_DataStage = DS.PK_DataStage " +
                    "LEFT JOIN Casos_Pedimento_507 AS CP ON CP.FK_DataStage = DG.FK_DataStage " +
                    "AND CP.Patente = DG.Patente " +
                    "AND CP.Pedimento = DG.Pedimento " +
                    "AND CP.SeccionAduanera = DG.SeccionAduanera " +
                    "WHERE DS.PK_DataStage = :id and CP.ClaveCaso = 'ED' " +
                    "ORDER BY CP.ClaveCaso DESC, CP.IdentificadorCaso \n");

            NativeQuery query = session.createSQLQuery(sqlQuery.toString());
            query.setParameter("id", requestOptions.getIdDatastage());
            query.setResultTransformer(Transformers.aliasToBean(CasosPedimentoCustomPO.class));

            ScrollableResults resultScroll = query.scroll(ScrollMode.SCROLL_INSENSITIVE);

            resultScroll.last();
            int totalResults = resultScroll.getRowNumber() + 1;

            resultScroll.first();
            int position = requestOptions.getPage() * requestOptions.getSize();
            resultScroll.scroll(position < totalResults ? position : 0);

            List<CasosPedimentoCustomBO> resultPage = new ArrayList<CasosPedimentoCustomBO>();
            int i = 0;
            while (requestOptions.getSize() > i++) {
                Object[] resultEntry = resultScroll.get();
                if (resultEntry != null)
                    resultPage.add(BuilderDatastage.builderCasosPedimento((CasosPedimentoCustomPO) resultEntry[0]));
                if (!resultScroll.next())
                    break;
            }

            Page<CasosPedimentoCustomBO> page = new Page<>();
            page.setResults(resultPage);
            page.setTotal(totalResults);

            return page;

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.severe("@getCasosPedimentosPorCliente: exception -> " + e.toString());
            throw new GlosaNetException(e);
        }
    }

    @Override
    public Integer getTotalCasosPedimentosDeDatastage(Integer idDatastage) throws GlosaNetException {
        LOGGER.info("@getTotalCasosPedimentosDeDatastage: Inicio");

        try {
            //Validaciones datastage
            if (null == idDatastage) throw new GlosaNetException("idDatastage is null");

            StringBuilder sqlQuery = new StringBuilder("SELECT count(CP.PK_Casos_Pedimento) \n");
            sqlQuery.append("FROM GN_DataStage AS DS \n");
            sqlQuery.append("LEFT JOIN Datos_Generales_501 AS DG ON DG.FK_DataStage = DS.PK_DataStage \n");
            sqlQuery.append("LEFT JOIN Casos_Pedimento_507 AS CP ON CP.FK_DataStage = DG.FK_DataStage \n");
            sqlQuery.append("AND CP.Patente = DG.Patente \n");
            sqlQuery.append("AND CP.Pedimento = DG.Pedimento \n");
            sqlQuery.append("AND CP.SeccionAduanera = DG.SeccionAduanera \n");
            sqlQuery.append("WHERE DS.PK_DataStage = :id and CP.ClaveCaso = 'ED' \n");
            sqlQuery.append("ORDER BY CP.ClaveCaso DESC, CP.IdentificadorCaso \n");

            Query query = em.createNativeQuery(sqlQuery.toString());
            query.setParameter("id", idDatastage);

            return ((BigInteger) query
                    .getSingleResult()).intValue();

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.severe("@getTotalFacturasCOVEsDeDatastage: exception -> " + e.toString());
            throw new GlosaNetException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Page<FacturaCoveBO> getFacturasCOVEsDeDatastage(VUCEMSolicitudRequestOptions requestOptions)
            throws GlosaNetException {
        LOGGER.info("@getFacturasCOVEsDeDatastage: Inicio");
        try {

            //Validaciones idDatastage
            if (null == requestOptions.getIdDatastage()) return null;

            Session session = em.unwrap(Session.class);

            StringBuilder sqlQuery = new StringBuilder("" +
                    "SELECT F.PK_Facturas idFactura, F.Pedimento, F.NumeroFactura, DS.FK01_Cliente AS idCliente " +
                    "FROM GN_DataStage AS DS " +
                    "LEFT JOIN Datos_Generales_501 AS DG ON DG.FK_DataStage = DS.PK_DataStage " +
                    "LEFT JOIN Facturas_505 AS F ON F.FK_DataStage = DG.FK_DataStage " +
                    "AND F.Patente = DG.Patente " +
                    "AND F.Pedimento = DG.Pedimento " +
                    "AND F.SeccionAduanera = DG.SeccionAduanera " +
                    "WHERE DS.PK_DataStage = :idDatastage AND F.NumeroFactura LIKE 'COVE%'" +
                    "ORDER BY F.Pedimento, F.NumeroFactura;");

            NativeQuery query = session.createSQLQuery(sqlQuery.toString());
            query.setParameter("idDatastage", requestOptions.getIdDatastage());
            query.setResultTransformer(Transformers.aliasToBean(FacturaCovePO.class));

            ScrollableResults resultScroll =  query.scroll(ScrollMode.SCROLL_INSENSITIVE);

            resultScroll.last();
            int totalResults = resultScroll.getRowNumber() + 1;

            resultScroll.first();
            int position = requestOptions.getPage() * requestOptions.getSize();
            resultScroll.scroll(position < totalResults ? position : 0);

            List<FacturaCoveBO> resultPage = new ArrayList<>();
            int i = 0;
            while (requestOptions.getSize() > i++) {
                Object[] resultEntry = resultScroll.get();
                if (resultEntry != null)
                    resultPage.add(BuilderDatastage.builderFactura((FacturaCovePO)  resultEntry[0]));
                if (!resultScroll.next())
                    break;
            }

            Page<FacturaCoveBO> page = new Page<>();
            page.setResults(resultPage);
            page.setTotal(resultPage.size());

            return page;

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.severe("@getFacturasCOVEsDeDatastage: exception -> " + e.toString());
            throw new GlosaNetException(e);
        }
    }

    @Override
    public Integer getTotalFacturasCOVEsDeDatastage(Integer idDatastage) throws GlosaNetException {
        LOGGER.info("@getTotalFacturasCOVEsDeDatastage: Inicio");

        try {

            //Validaciones datastage
            if (null == idDatastage) throw new GlosaNetException("idDatastage is null");

            StringBuilder sqlQuery = new StringBuilder("SELECT COUNT(F.NumeroFactura) \n");
            sqlQuery.append("FROM GN_DataStage AS DS \n");
            sqlQuery.append("LEFT JOIN Datos_Generales_501 AS DG ON DG.FK_DataStage = DS.PK_DataStage \n");
            sqlQuery.append("LEFT JOIN Facturas_505 AS F ON F.FK_DataStage = DG.FK_DataStage \n");
            sqlQuery.append("AND F.Patente = DG.Patente \n");
            sqlQuery.append("AND F.Pedimento = DG.Pedimento \n");
            sqlQuery.append("AND F.SeccionAduanera = DG.SeccionAduanera \n");
            sqlQuery.append("WHERE DS.PK_DataStage = :idDatastage \n");

            Query query = em.createNativeQuery(sqlQuery.toString());
            query.setParameter("idDatastage", idDatastage);

            return ((BigInteger) query
                    .getSingleResult()).intValue();

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.severe("@getTotalFacturasCOVEsDeDatastage: exception -> " + e.toString());
            throw new GlosaNetException(e);
        }
    }

    @Override
    @Transactional
    public FacturaCoveBO getFactura(Integer idFactura) throws GlosaNetException {
        LOGGER.info("@getFactura: Obteniendo registro único de factura");
        FacturaCovePO factura;
        StringBuilder sqlQuery = new StringBuilder("" +
                "SELECT f.PK_Facturas idFactura, ds.FK01_Cliente idCliente, f.Pedimento, f.NumeroFactura " +
                "FROM Facturas_505 f INNER JOIN GN_DataStage ds ON f.FK_DataStage = ds.PK_DataStage " +
                "WHERE f.PK_Facturas = :id ");

        try {
            em.clear();
            Session session = em.unwrap(Session.class);
            NativeQuery query = session.createSQLQuery(sqlQuery.toString());
            query.setParameter("id", idFactura);
            query.setResultTransformer(Transformers.aliasToBean(FacturaCovePO.class));
            factura = (FacturaCovePO) query.getSingleResult();
        } catch (Exception ex) {
            throw new GlosaNetException("Error: " + ex.getMessage());
        }

        return BuilderDatastage.builderFactura(factura);
    }

    @Override
    @Transactional
    public CasosPedimentoCustomBO getCasoPedimento(Integer idCasoPedimento) throws GlosaNetException {
        LOGGER.info("@getCasoPedimento: Obteniendo registro único de caso pedimento");
        CasosPedimentoCustomPO casoPedimento; //CasosPedimentoIDCasoPO
        StringBuilder sqlQuery = new StringBuilder("" +
                "SELECT CP.PK_Casos_Pedimento AS idCasosPedimento, CP.IdentificadorCaso, CP.Pedimento, DS.FK01_Cliente idCliente \n" +
                "FROM GN_DataStage DS INNER JOIN Casos_Pedimento_507 CP ON CP.FK_DataStage = DS.PK_DataStage \n" +
                "WHERE ClaveCaso = 'ED' AND CP.PK_Casos_Pedimento = :id ");

        try {
            Session session = em.unwrap(Session.class);
            NativeQuery query = session.createSQLQuery(sqlQuery.toString());
            query.setParameter("id", idCasoPedimento);
            query.setResultTransformer(Transformers.aliasToBean(CasosPedimentoCustomPO.class));
            casoPedimento = (CasosPedimentoCustomPO) query.getSingleResult();
        }catch (Exception ex) {
            throw new GlosaNetException("Error: " + ex.getMessage());
        }

        return BuilderDatastage.builderCasosPedimento(casoPedimento);
    }

    @Override
    public boolean existsFactura(Integer idFactura) throws GlosaNetException {
        try {
            Session session = em.unwrap(Session.class);
            return session.find(FacturaCovePO.class, idFactura) != null;
        } catch(Exception ex)
        {
            throw new GlosaNetException(ex.getMessage());
        }
    }

    @Override
    public boolean existsCasoPedimento(Integer idCasoPedimento) throws GlosaNetException {
        try {
            Session session = em.unwrap(Session.class);
            return session.find(CasosPedimentoCustomPO.class, idCasoPedimento) != null;
        } catch(Exception ex)
        {
            throw new GlosaNetException(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public List<StatusAcuseCoveBO> getstatusAcuseCove(int[] ids) throws GlosaNetException {
        try {
            Session session = em.unwrap(Session.class);
            String id = IntStream.of(ids)
                    .mapToObj(Integer::toString)
                    .collect(Collectors.joining(", "));

            NativeQuery query = session.createNativeQuery("" +
                    "SELECT idReceipt, status, errorMessage, path " +
                    "FROM GN_Vucem_AcuseCove WHERE idReceipt in (" + id + ")", StatusAcuseCovePO.class);

            List<StatusAcuseCovePO> queryList = query.list();
            List<StatusAcuseCoveBO> resultList = new ArrayList<>();
            queryList.forEach(po -> resultList.add(new StatusAcuseCoveBO(po)));

            return resultList;
        } catch(Exception ex)
        {
            ex.printStackTrace();
            throw new GlosaNetException(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public List<StatusAcuseEdocBO> getStatusAcuseEdoc(int[] ids) throws GlosaNetException {
        try {
            Session session = em.unwrap(Session.class);
            String id = IntStream.of(ids)
                    .mapToObj(Integer::toString)
                    .collect(Collectors.joining(", "));

            NativeQuery query = session.createNativeQuery("" +
                    "SELECT idRequestCase, path, errorMessage, status " +
                    "FROM GN_Vucem_AcuseEdoc WHERE idRequestCase in (" + id + ")", StatusAcuseEdocPO.class);

            List<StatusAcuseEdocPO> queryList = query.list();
            List<StatusAcuseEdocBO> resultList = new ArrayList<>();
            queryList.forEach(po -> resultList.add(new StatusAcuseEdocBO(po)));

            return resultList;
        } catch(Exception ex)
        {
            ex.printStackTrace();
            throw new GlosaNetException(ex.getMessage());
        }
    }
}
