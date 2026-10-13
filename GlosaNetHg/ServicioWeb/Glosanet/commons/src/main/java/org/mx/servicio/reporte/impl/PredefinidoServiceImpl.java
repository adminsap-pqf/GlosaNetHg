package org.mx.servicio.reporte.impl;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.Parametros;
import org.mx.modelo.comun.bo.RegionesBO;
import org.mx.modelo.reportes.ParametrosPartida;
import org.mx.modelo.reportes.bo.DetalleContribucionesBO;
import org.mx.modelo.reportes.bo.DetallePartidasBO;
import org.mx.modelo.reportes.bo.ResumenReporteBO;
import org.mx.modelo.util.Util;
import org.mx.modelo.util.UtilFechas;
import org.mx.persistencia.Page;
import org.mx.persistencia.comun.GlosarioDAO;
import org.mx.persistencia.reporte.PredefinidoDAO;
import org.mx.servicio.comun.impl.AsistentesDataSource;
import org.mx.servicio.reporte.PredefinidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class PredefinidoServiceImpl implements PredefinidoService {

    private final static Logger LOGGER = Logger.getLogger(PredefinidoServiceImpl.class.getName());

    UtilFechas u;

    @Autowired
    PredefinidoDAO predefinidoDAO;

    @Autowired
    GlosarioDAO glosarioDAO;

    @Override
    public List<DetalleContribucionesBO> reporteDetalleContribuciones(Parametros p) {
        u = new UtilFechas();

        String idioma = "espanol";
        String filtro = u.construccionDeFechas(p, "DG.FechaPagoReal", "DG.FechaPagoReal");
        filtro += " AND VCP.FK01_Cliente = " + p.getUsuario().getCliente().getIdCliente();

        if(p.getBusqueda() != null)
            filtro += " AND (DG.Patente LIKE '%" + p.getBusqueda() + "%' " +
                    "OR DG.Pedimento LIKE '%" + p.getBusqueda() + "%' " +
                    "OR DG.SeccionAduanera LIKE '%" + p.getBusqueda() + "%')";

        List<DetalleContribucionesBO> resultado = predefinidoDAO.getReporteContribuciones(filtro);

        return resultado;
    }

    @Override
    public ByteArrayOutputStream reporteContribucionesExcel(Parametros p) throws IOException {
        List<DetalleContribucionesBO> resultado = reporteDetalleContribuciones(p);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        if (resultado.size() == 0) {
            return null;
        }

        Map<String, TemporalAdjuster> ADJUSTERS = new HashMap<>();
        ADJUSTERS.put("month", TemporalAdjusters.firstDayOfMonth());

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = null;

        Map<String, List<DetalleContribucionesBO>> map;

        map = resultado.stream()
                .sorted(Comparator.comparingInt(x -> x.getFechaPagoReal().getMonth()))
                .collect(Collectors.groupingBy(x ->
                        new DateFormatSymbols(new Locale( "es" , "MX" ))
                                .getMonths()[x.getFechaPagoReal().getMonth()])
                );

        for(Map.Entry<String, List<DetalleContribucionesBO>> monthlyList : map.entrySet()) {
            sheet = wb.createSheet(monthlyList.getKey());

            //Crea encabezado
            HSSFFont headerFont = wb.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 14);
            headerFont.setColor(IndexedColors.BLACK.getIndex());

            //Pone nombres de columna
            String[] columns = { "Num", "Fecha Pago Real", "Folio", "Patente", "Pedimento", "Seccion Aduanera", "Tipo Operacion", "Tipo Pedimento", "Clave Documento", "DTA", "PRV", "CNT", "Recargos", "OTROS", "IVA", "IGI",  "Total" };

            int rowId = 0;
            int colId = 0;

            HSSFRow rowHeader = sheet.createRow(rowId++);
            for(String columnName : columns) {
                HSSFCell cell = rowHeader.createCell(colId++);
                cell.setCellValue(columnName);
            }

            //Rellena la hoja
            for(DetalleContribucionesBO row : monthlyList.getValue()) {
                colId = 0;
                HSSFRow rowContent = sheet.createRow(rowId++);
                rowContent.createCell(colId++).setCellValue(row.getRowIndex());
                rowContent.createCell(colId++).setCellValue(sdf.format(row.getFechaPagoReal()));
                rowContent.createCell(colId++).setCellValue(row.getFolio());
                rowContent.createCell(colId++).setCellValue(row.getPatente());
                rowContent.createCell(colId++).setCellValue(row.getPedimento());
                rowContent.createCell(colId++).setCellValue(row.getSeccionAduanera());
                rowContent.createCell(colId++).setCellValue(row.getTipoOperacion());
                rowContent.createCell(colId++).setCellValue(row.getTipoPedimento());
                rowContent.createCell(colId++).setCellValue(row.getClaveDocumento());
                rowContent.createCell(colId++).setCellValue(row.getDta());
                rowContent.createCell(colId++).setCellValue(row.getPrv());
                rowContent.createCell(colId++).setCellValue(row.getCnt());
                rowContent.createCell(colId++).setCellValue(row.getRecargos());
                rowContent.createCell(colId++).setCellValue(row.getOtros_imp());
                rowContent.createCell(colId++).setCellValue(row.getIva());
                rowContent.createCell(colId++).setCellValue(row.getIgi());
                rowContent.createCell(colId++).setCellValue(row.getTotal());
            }
        }

        LOGGER.info("Reporte de pedimentos con detalle de contribuciones del año " + p.getYear() + " del cliente " + p.getUsuario().getNombre() + " se ha generado correctamente.");

        // Genera el archivo de Excel
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        wb.write(fileOut);
        fileOut.close();

        return fileOut;
    }

    @Override
    public ByteArrayOutputStream reportePartidasExcel(Parametros p) throws IOException, GlosaNetException {
        u = new UtilFechas();
        String filtro = u.construccionDeFechas(p, "p.FechaPagoReal", "p.FechaPagoReal");
        filtro += " AND GDS.FK01_Cliente = " + p.getUsuario().getCliente().getIdCliente();

        if(p.getBusqueda() != null)
            filtro += " AND (p.Patente LIKE '%" + p.getBusqueda() + "%' " +
                    "OR p.Pedimento LIKE '%" + p.getBusqueda() + "%' " +
                    "OR p.SeccionAduanera LIKE '%" + p.getBusqueda() + "%')";

        List<DetallePartidasBO> resultado = predefinidoDAO.getReportePartidas(filtro);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        if (resultado.size() == 0) {
            return null;
        }

        Map<String, TemporalAdjuster> ADJUSTERS = new HashMap<>();
        ADJUSTERS.put("month", TemporalAdjusters.firstDayOfMonth());

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = null;

        Map<String, List<DetallePartidasBO>> map;

        map = resultado.stream()
                .sorted(Comparator.comparingInt(x -> x.getFechaPagoReal().getMonth()))
                .collect(Collectors.groupingBy(x ->
                        new DateFormatSymbols(new Locale( "es" , "MX" ))
                                .getMonths()[x.getFechaPagoReal().getMonth()])
                );

        for(Map.Entry<String, List<DetallePartidasBO>> monthlyList : map.entrySet()) {
            sheet = wb.createSheet(monthlyList.getKey());

            //Crea encabezado
            HSSFFont headerFont = wb.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 14);
            headerFont.setColor(IndexedColors.BLACK.getIndex());

            //Pone nombres de columna
            String[] columns = { "Num", "Fecha Pago Real", "Folio", "Patente", "Pedimento", "Seccion Aduanera", "Secuencia Fracción", "Pais Comprador", "Identificación Fiscal", "Proveedor Mercancía", "Valor Aduana", "IVA", "IGI" };

            int rowId = 0;
            int colId = 0;

            HSSFRow rowHeader = sheet.createRow(rowId++);
            for(String columnName : columns) {
                HSSFCell cell = rowHeader.createCell(colId++);
                cell.setCellValue(columnName);
            }

            //Rellena la hoja
            for(DetallePartidasBO row : monthlyList.getValue()) {
                colId = 0;
                HSSFRow rowContent = sheet.createRow(rowId++);
                rowContent.createCell(colId++).setCellValue(row.getRowIndex());
                rowContent.createCell(colId++).setCellValue(sdf.format(row.getFechaPagoReal()));
                rowContent.createCell(colId++).setCellValue(row.getFolio());
                rowContent.createCell(colId++).setCellValue(row.getPatente());
                rowContent.createCell(colId++).setCellValue(row.getPedimento());
                rowContent.createCell(colId++).setCellValue(row.getSeccionAduanera());
                rowContent.createCell(colId++).setCellValue(row.getSecuenciaFraccion());
                rowContent.createCell(colId++).setCellValue(row.getPaisCompradorVendedor());
                rowContent.createCell(colId++).setCellValue(row.getIndentFiscalProveedor());
                rowContent.createCell(colId++).setCellValue(row.getProveedorMercancia());
                rowContent.createCell(colId++).setCellValue(row.getValorAduana());
                rowContent.createCell(colId++).setCellValue(row.getIva());
                rowContent.createCell(colId++).setCellValue(row.getIgi());
            }
        }

        LOGGER.info("Reporte de pedimentos con detalle de partidas9 del año " + p.getYear() + " del cliente " + p.getUsuario().getNombre() + " se ha generado correctamente.");

        // Genera el archivo de Excel
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        wb.write(fileOut);
        fileOut.close();

        return fileOut;
    }

    /**
     * @param parametros
     * @return
     */
    @Override
    public List<DetallePartidasBO> reporteDetallePartidas(ParametrosPartida parametros) throws GlosaNetException {
        List<DetallePartidasBO> resultado = null;

        if(parametros.getIdDatastage() != null && parametros.getPedimento() != null && parametros.getPatente() != null && parametros.getSeccionAduanera() != null)
            resultado = predefinidoDAO.getReportePartidas(parametros);

        if(resultado == null)
            throw new GlosaNetException("No se recibieron todos los parámetros requeridos, consulte el manual del servicio para mayor información.");

        return resultado;
    }

    /**
     * @param p
     * @return
     */
    @Override
    public Page<DetallePartidasBO> reporteDetallePartidas(Parametros p) throws GlosaNetException {
        Page<DetallePartidasBO> resultado = null;
        u = new UtilFechas();

        if(p.getTipo() != null) {

            String filtro = u.construccionDeFechas(p, "p.FechaPagoReal", "p.FechaPagoReal");
            filtro += " AND GDS.FK01_Cliente = " + p.getUsuario().getCliente().getIdCliente();

            if(p.getBusqueda() != null)
                filtro += " AND (p.Patente LIKE '%" + p.getBusqueda() + "%' " +
                        "OR p.Pedimento LIKE '%" + p.getBusqueda() + "%' " +
                        "OR p.SeccionAduanera LIKE '%" + p.getBusqueda() + "%')";

            resultado = predefinidoDAO.getReportePartidas(filtro, p.getPage(), p.getSize());
        }

        if(resultado == null)
            throw new GlosaNetException("No se recibieron todos los parámetros requeridos, consulte el manual del servicio para mayor información.");

        return resultado;
    }

    @Override
    public List<ResumenReporteBO> obtenerResultadosReportesPredefinidos(Parametros p) throws GlosaNetException {
        try {
            u = new UtilFechas();

            String rParticular = "";
            String tipoMoneda;
            String idioma = "espanol";
            tipoMoneda = "ValorAduana";
            String rGeneral = u.construccionDeFechas(p, "DG.FechaPagoReal", "DG.FechaPagoReal");
            rGeneral += /*" AND DG.Rfc = '" + p.getUsuario().getCliente().getRfcActual() +*/ " AND DS.FK01_Cliente = " + p.getUsuario().getCliente().getIdCliente();
            String rectificacionFecha = u.construccionDeFechas(p, "R.FechaPagoReal", "R.FechaPagoReal");

            //Obtiene el tipo de moneda dentro del objeto UsuarioBO
            //valordolares valoraduana
            //tipo de cambio contribuciones

            List<ResumenReporteBO> resultado = new ArrayList<ResumenReporteBO>();
            switch (p.getReporte()) {
                case 0://Resumen de importaciones
                    rParticular = " DG.TipoOperacion = 1 ";
                    resultado = predefinidoDAO.getMontosTotalesPorTipoOperacionClave(rParticular, rGeneral, tipoMoneda);
                    for (ResumenReporteBO re : resultado) {
                        re.setNombreClave(glosarioDAO.getNombreClave("Column2", "Column1", "GN_Apendice_Contenido", 2, re.getClave()));
                    }

                    break;

                case 1://Resumen de exportaciones
                    tipoMoneda = "ValorComercial";
                    rParticular = " DG.TipoOperacion = 2 ";
                    resultado = predefinidoDAO.getMontosTotalesPorTipoOperacionClave(rParticular, rGeneral, tipoMoneda);
                    for (ResumenReporteBO re : resultado) {
                        re.setNombreClave(glosarioDAO.getNombreClave("Column2", "Column1", "GN_Apendice_Contenido", 2, re.getClave()));
                    }
                    break;

                case 2://Resumen de contribuciones
                    resultado = predefinidoDAO.getMontosTotalesClaveContribucion(rParticular, rGeneral, tipoMoneda, rectificacionFecha);
                    for (ResumenReporteBO re : resultado) {
                        re.setNombreClave(glosarioDAO.getNombreClave("Column3", "Column1", "GN_Apendice_Contenido", 12, re.getClave()));
                    }
                    break;

                case 3:    //Importaciones temporales
                    rParticular += " WHERE ClaveDocumento IN ('AD','AJ','BA','BB','BC','BD','BE','BF','BH','BI','BM','BO','BP','BR','H1','H8','I1','F4','F5','AF','IN') ";
                    resultado = predefinidoDAO.getMontosTotalesImportacion(rParticular, rGeneral, tipoMoneda, idioma);
                    break;

                case 4:    //Importaciones definitivas
                    rParticular += " WHERE ClaveDocumento IN ('A1','A3','C1','C2','D1','GC','L1','K1','P1','T1','VF','VU','S2') ";
                    resultado = predefinidoDAO.getMontosTotalesImportacion(rParticular, rGeneral, tipoMoneda, idioma);
                    break;

                case 5://Relaciones entre impuesto de importacion

                    resultado = predefinidoDAO.getMontoValorAduanaYContribucionImportacion(rGeneral, tipoMoneda, idioma);
                    break;

                case 6://Pago pendiente de impuesto de importacion
                    resultado = predefinidoDAO.getMontoPendienteValorAduanaYContribucionImportacion(rGeneral, tipoMoneda, idioma);
                    break;
            }


            return resultado;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private JasperPrint generarPDF(Map<String, Object> arguments, String path,
                                   String index) throws GlosaNetException {

        LOGGER.info("@generarPDF: Inicio");

        JasperPrint jasperPrint;
        try {
            JasperReport jasperReport = (JasperReport) JRLoader
                    .loadObjectFromFile(path); // utiliza los archivos
            // compilados de Jasper
            // (.jasper)
            LOGGER.info("@generarPDF: jasperReport: " + jasperReport);
            jasperPrint = JasperFillManager.fillReport(jasperReport, arguments,
                    (AsistentesDataSource) arguments.get("dataSource"));

        } catch (JRException e) {
            e.printStackTrace();
            LOGGER.severe("@generarPDF: exception -> " + e.toString());
            throw new GlosaNetException(e);
        }
        return jasperPrint;
    }

    @Override
    public byte[] descargarReporte(Map<String, Object> arguments)
            throws GlosaNetException {

        LOGGER.info("@descargarReporte: Inicio");

        byte[] bytes = null; //bytes a regresar
        try {

            List<JasperPrint> list = new ArrayList<>();
            String tipoGrafica = (String) arguments.get("tipoGrafica");
            arguments.put("logo", Util.docroot + arguments.get("logo"));

            /**
             * Lo necesita para pintar el pdf, se manda un Source sin nada
             * para que jasper pinte la pagina.
             */
            AsistentesDataSource datasource = new AsistentesDataSource();
            datasource.addAsistente("");
            arguments.put("dataSource", datasource);

            arguments.put("logo", arguments.get("logo"));
            arguments.put("Logo_Glosanet", Util.docroot + "images/Logo_Glosanet.svg");
            arguments.put("triangulo", Util.docroot + "images/triangulo.svg");

            list.add(generarPDF(arguments, Util.rutaReporte + "Predisenados/ReportesWeb/Portada.jasper", "1"));

            AsistentesDataSource contenido = new AsistentesDataSource();
            contenido.addAsistente("");
            arguments.put("dataSource", contenido);
            List<RegionesBO> reg = new ArrayList<>();
            ArrayList<Map<String, Object>> regiones = (ArrayList<Map<String, Object>>) arguments.get("regiones");
            for (Map<String, Object> r : regiones) {
                RegionesBO re = new RegionesBO();
                re.setNombreRegion(r.get("nombreRegion").toString());
                re.setMontos(r.get("montos").toString());
                LOGGER.info("@descargarReporte: Inicio");
                LOGGER.info(
                        "@descargarReporte: región: " + re.getNombreRegion() + ", montos: " + re.getMontos());
                reg.add(re);
            }

            arguments.put("lstregiones", reg);
            list.add(generaImagenesGraficas(arguments, tipoGrafica, "2"));

            if (arguments.get("continentes") != null) {
                ArrayList<Map<String, Object>> continentes =
                        (ArrayList<Map<String, Object>>) arguments.get("continentes");
                Integer i = 3;
                for (Map<String, Object> con : continentes) {

                    AsistentesDataSource ds = new AsistentesDataSource();
                    ds.addAsistente("");
                    arguments.put("dataSource", ds);

                    if (con.get("montos") != null)
                        arguments.put("montos", con.get("montos"));

                    if (con.get("regiones") != null)
                        arguments.put("regiones", con.get("regiones"));

                    if (con.get("pdf") != null)
                        arguments.put("pdf", con.get("pdf"));

                    if (con.get("total") != null)
                        arguments.put("total", con.get("total").toString());

                    if (con.get("totalPorcentaje") != null)
                        arguments.put("totalPorcentaje", con.get("totalPorcentaje").toString());

                    if (con.get("totalImpuestoImportacion") != null)
                        arguments.put("totalImpuestoImportacion", con.get("totalImpuestoImportacion").toString());

                    if (con.get("region") != null)
                        arguments.put("region", con.get("region").toString());

                    if (con.get("importacionGeneral") != null)
                        arguments.put("importacionGeneral", con.get("importacionGeneral"));

                    if (con.get("precioProsec") != null)
                        arguments.put("precioProsec", con.get("precioProsec"));

                    if (con.get("importeTotal") != null)
                        arguments.put("importeTotal", con.get("importeTotal"));

                    if (con.get("valorAduanas") != null)
                        arguments.put("valorAduanas", con.get("valorAduanas"));

                    if (con.get("impuestoImportacion") != null)
                        arguments.put("impuestoImportacion", con.get("impuestoImportacion"));

                    if (con.get("porcentaje") != null)
                        arguments.put("porcentaje", con.get("porcentaje"));

                    if (con.get("totalImportacionGeneral") != null)
                        arguments.put("totalImportacionGeneral", con.get("totalImportacionGeneral").toString());

                    if (con.get("totalPrecioProsec") != null)
                        arguments.put("totalPrecioProsec", con.get("totalPrecioProsec").toString());

                    if (con.get("totalImporteTotal") != null)
                        arguments.put("totalImporteTotal", con.get("totalImporteTotal").toString());

                    list.add(generaImagenesGraficas(arguments, tipoGrafica, i.toString()));
                    i++;
                }
            }

            File file = new File(Util.rutaReporte + "Predisenados/" + arguments.get("usuario") + "/");
            if (!file.exists()) {
                file.mkdirs();
            }
            File targetFile = new File(
            		Util.rutaReporte + "Predisenados/" + arguments.get("usuario") + "/" + "Reporte.pdf");

            LOGGER.info("@descargarReporte: Ruta a donde se guardara" + targetFile.getAbsolutePath());

            bytes = generateReport2(list);
            OutputStream outStream = new FileOutputStream(targetFile);
            outStream.write(bytes);
            outStream.close();
			LOGGER.info("@descargarReporte: Finalizo");

        } catch (Exception e) {

			e.printStackTrace();
			LOGGER.severe("@descargarReporte: exception -> " + e.toString());
			throw new GlosaNetException(e);
        }

		return bytes;
    }

    private JasperPrint generaImagenesGraficas(Map<String, Object> arguments, String tipoGrafica, String pagina)
			throws GlosaNetException {

		LOGGER.info("@generaImagenesGraficas: Inicio");

        JasperPrint jp = new JasperPrint();
        Path tmp;
        try {
            tmp = Files.createTempDirectory("Dash_");
            if (arguments.get("pdf") != null)
                arguments.put("grafica", Util.createFileTemporal(
                		Base64.decodeBase64(
                				arguments.get("pdf").toString()), "chart" + pagina + ".png", tmp.toString()));

            if (pagina.equals("2")) {
                jp = generarPDF(
                		arguments,
						Util.rutaReporte + "Predisenados/ReportesWeb/Contenido2Columnas.jasper",
						pagina);
            } else {
				LOGGER.info("@generaImagenesGraficas: despues de la segunda");
            }

        } catch (Exception e) {
			e.printStackTrace();
			LOGGER.severe("@generaImagenesGraficas: exception -> " + e.toString());
			throw new GlosaNetException(e);
        }
        return jp;
    }

    public byte[] generateReport2(List<JasperPrint> jasperPrintList)
            throws JRException {

        try {
            // Se crea un solo archivo con una lista de pdf
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            ExporterInput exporterInput = SimpleExporterInput
                    .getInstance(jasperPrintList);
            exporter.setExporterInput(exporterInput);
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(
                    baos));

            SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
            configuration.setCreatingBatchModeBookmarks(true);
            exporter.setConfiguration(configuration);
            exporter.exportReport();

            return baos.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
