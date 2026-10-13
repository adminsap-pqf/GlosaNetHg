package org.mx.servicio.comun.impl;

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
import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.DashBoard;
import org.mx.modelo.comun.bo.Parametros;
import org.mx.modelo.util.Util;
import org.mx.modelo.util.UtilFechas;
import org.mx.persistencia.comun.DashBoardDAO;
import org.mx.servicio.comun.DashBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


@Service("dashBoardService")
public class DashBoardServiceImpl implements DashBoardService {

    private final static Logger LOGGER = Logger.getLogger(DashBoardServiceImpl.class.getName());

    @Autowired
    DashBoardDAO dashboardDAO;


    private JasperPrint generaPDF2(Map<String, Object> arguments, String path,
                                   String index) throws GlosaNetException {

        LOGGER.info("@generaPDF2: Inicio");

        JasperPrint jasperPrint;

        try {
            JasperReport jasperReport = (JasperReport) JRLoader
                    .loadObjectFromFile(path); // utiliza los archivos compilados de Jasper (.jasper)
            //System.out.println(jasperReport);
            if (null == jasperReport) {
                LOGGER.info("@generaPDF2: jasperReport es nulo");
            }
            LOGGER.info("@generaPDF2: reporte jasper: " + jasperReport);

            AsistentesDataSource dataSource = (AsistentesDataSource) arguments.get("dataSource");
            if (null == dataSource) {
                LOGGER.info("@generaPDF2: dataSource es nulo");
            }
            LOGGER.info("@generaPDF2: dataSource: " + dataSource);

            jasperPrint = JasperFillManager.fillReport(jasperReport, arguments, dataSource);

            return jasperPrint;
        } catch (JRException e) {

            e.printStackTrace();
            LOGGER.severe("@generaPDF2: excepcion -> " + e.toString());
            throw new GlosaNetException(e);
        }
    }

    private List<String> listaTempTablas(String dashboard) {
        List<String> imagenes = new ArrayList<String>();

        switch (dashboard) {
            case "D501_2":

                imagenes.add("img1_501");
                imagenes.add("img2_501");
                imagenes.add("blank");
                imagenes.add("img3_501");
                imagenes.add("blank");
                imagenes.add("img4_501");
                imagenes.add("blank");
                imagenes.add("img5_501");
                imagenes.add("img6_501");
                imagenes.add("img7_501");
                imagenes.add("blank");
                imagenes.add("img8_501");
                imagenes.add("blank");
                imagenes.add("img9_501");
                imagenes.add("img10_501");
                imagenes.add("blank");
                imagenes.add("img11_501");
                imagenes.add("img12_501");
                imagenes.add("blank");
                imagenes.add("img13_501");

                break;
            case "D502_2":
                imagenes.add("img1_502");
                imagenes.add("img2_502");
                imagenes.add("img3_502");
                break;
            case "D503_2":
                imagenes.add("img1_503");
                imagenes.add("img2_503");
                imagenes.add("img3_503");
                break;
            case "D504_2":
                imagenes.add("img1_504");
                imagenes.add("img2_504");
                imagenes.add("img3_504");
                break;
            case "D505_2":
                imagenes.add("img1_505");
                imagenes.add("img2_505");
                break;
            case "D506_2":
                imagenes.add("img1_506");
                imagenes.add("img2_506");
                break;
            case "D507_2":
                imagenes.add("img1_507");
                imagenes.add("img2_507");
                break;
            case "D509_2":
                imagenes.add("img1_509");
                imagenes.add("img2_509");
                imagenes.add("img3_509");
                imagenes.add("img4_509");
                break;
            case "D510_2":
                imagenes.add("img1_510");
                imagenes.add("img2_510");
                imagenes.add("img3_510");
                imagenes.add("img4_510");
                imagenes.add("img5_510");
                break;
            case "D554_2":
                imagenes.add("img1_554");
                imagenes.add("img2_554");
                imagenes.add("img3_554");
                break;
            case "D556_2":

                imagenes.add("img1_556");
                imagenes.add("img2_556");
                imagenes.add("img3_556");
                imagenes.add("img4_556");
                imagenes.add("img5_556");
                break;
            case "D557_2":

                imagenes.add("img1_557");
                imagenes.add("img2_557");
                imagenes.add("img3_557");
                imagenes.add("img4_557");
                break;
            case "D701_2":

                imagenes.add("img1_701");
                imagenes.add("img2_701");
                imagenes.add("img3_701");
                break;
            case "D702_2":

                imagenes.add("img1_702");
                imagenes.add("img2_702");
                imagenes.add("img3_702");
                break;
        }
        return imagenes;
    }


    @Override
    public byte[] generaReporteDG501PDF(Map<String, Object> arguments)
            throws GlosaNetException {

        LOGGER.info("@generaReporteDG501PDF: Inicio");

        if (arguments.get("dashboard") == null || arguments.get("dashboard").equals("")) {
            arguments.put("dashboard", "D501_2");
        }

        Path tmp;
        byte[] bytes = null; //bytes de contenido a devolver
        try {
            tmp = Files.createTempDirectory("Dash_");
            List<String> tablas = listaTempTablas((String) arguments.get("dashboard"));

            /**
             * Lo necesita para pintar el pdf, se manda un Source sin nada
             * para que jasper pinte la pagina.
             */

            arguments.put("logo", Util.docroot + arguments.get("logo"));
            arguments.put("Logo_Glosanet", Util.docroot + "images/Logo_Glosanet.svg");
            arguments.put("triangulo", Util.docroot + "images/triangulo.svg");


            List<JasperPrint> list = new ArrayList<>();
            Integer pag = 1;

            //hacer la hoja de portada
            String templateGray = "D501_2/OperacionesPeriodo.jasper";
            String templateWhite = "D501_2/OperacionesAduana.jasper";

            list.add(generaPDF2(arguments, Util.rutaReporte + "Dashboard/D501_2/Portada.jasper", pag.toString()));

            LOGGER.info("@generaReporteDG501PDF: Hace portada de reporte");
            //System.out.println("*******************************Hace portada*******************************");
            pag = 2;

            for (String t : tablas) {
                if (!t.equalsIgnoreCase("blank")) {
                    Util.createFileTemporal(Base64.decodeBase64(arguments.get(t).toString()), t + ".png", tmp.toString());
                }
            }

            AsistentesDataSource datasource = new AsistentesDataSource();
            datasource.addAsistente("");
            arguments.put("dataSource", datasource);

            for (String t : tablas) {
                if (!t.equalsIgnoreCase("blank")) {
                    LOGGER.info("@generaReporteDG501PDF: Inicia pagina " + pag + " de reporte");
                    //System.out.println("*******************************Hace pagina " + pag + "*******************************");
                    //arguments.put("grafica", tmp.toString() + t + ".png" );
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("dataSource", datasource);
                    //System.out.println(tmp.toString() + "/" + t + ".png");
                    LOGGER.info("@generaReporteDG501PDF: imagen en: " + tmp.toString() + "/" + t + ".png");
                    map.put("grafica", tmp.toString() + "/" + t + ".png");
                    map.put("logo", arguments.get("logo"));
                    map.put("Logo_Glosanet", Util.docroot + "images/Logo_Glosanet.svg");
                    map.put("triangulo", Util.docroot + "images/triangulo.svg");

                    map.put("autor", arguments.get("usuario"));
                    map.put("nombreCompleto", arguments.get("nombreCompleto"));
                    map.put("email", arguments.get("email"));
                    map.put("fechaReporte", arguments.get("fechaReporte"));
                    map.put("titulo", arguments.get("titulo"));

                    if (pag % 2 == 0) {
                        list.add(generaPDF2(map, Util.rutaReporte + "Dashboard/" + templateGray, pag.toString()));
                    } else {
                        list.add(generaPDF2(map, Util.rutaReporte + "Dashboard/" + templateWhite, pag.toString()));
                    }

                    datasource = new AsistentesDataSource();
                    datasource.addAsistente("");
                    map.put("dataSource", datasource);
                    //System.out.println("*******************************Termina pagina " + pag + "*******************************");
                    LOGGER.info("@generaReporteDG501PDF: Termina pagina " + pag + " de reporte");

                }
                pag++;

            }

            String dashboardFile = Util.rutaReporte + "Dashboard/" + arguments.get("usuario") + "/";
            LOGGER.info("@generaReporteDG501PDF: dashboardFile -> " + dashboardFile);
            File file = new File(dashboardFile);
            if (!file.exists()) {
                LOGGER.info("@generaReporteDG501PDF: dashboardFile no existe; creando");
                file.mkdirs();
            }
            String dashboardReporteFile = Util.rutaReporte + "Dashboard/" + arguments.get("usuario")
                    + "/" + "Reporte.pdf";
            LOGGER.info("@generaReporteDG501PDF: dashboardReporteFile -> " + dashboardReporteFile);
            File targetFile = new File(dashboardReporteFile);
            //System.out.println("Ruta a donde se guardara" + targetFile.getAbsolutePath());
            LOGGER.info("@generaReporteDG501PDF: Ruta a donde se guardara dashboardReporteFile -> " + targetFile.getAbsolutePath());
            OutputStream outStream;
            try {
                //System.out.println("*******************************Genera Archivo con todos los PDF*******************************");
                LOGGER.info("@generaReporteDG501PDF: Genera Archivo con todos los PDF");
                bytes = generateReport2(list);
                outStream = new FileOutputStream(targetFile);
                outStream.write(bytes);
                outStream.close();
                //System.out.println("*******************************Finalizaa Archivo con todos los PDF*******************************");
                LOGGER.info("@generaReporteDG501PDF: Finaliza Archivo con todos los PDF");

            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.severe("@generaReporteDG501PDF: exception -> " + e.toString());
                throw new GlosaNetException(e);
            }

        } catch (Exception e1) {
            e1.printStackTrace();
            LOGGER.severe("@generaReporteDG501PDF: exception -> " + e1.toString());
            throw new GlosaNetException(e1.getMessage(), e1.getCause());
        }

        return bytes;
    }

    public byte[] generateReport2(List<JasperPrint> jasperPrintList)
            throws GlosaNetException {

        LOGGER.info("@generateReport2: Inicio");

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
            LOGGER.severe("@generateReport2: exception -> " + e.toString());
            throw new GlosaNetException(e);
        }
    }


    private String t501_ObtenerRestricciones(Parametros parametros) {
        String restricciones = "";
        String fechas = UtilFechas.construirFechas(parametros, "DG.FechaPagoReal", "DG.FechaPagoReal");

        restricciones += " WHERE DG.RFC = '" + parametros.getUsuario().getCliente().getRfcActual() + "' ";
        restricciones += " AND DS.FK01_Cliente = " + parametros.getUsuario().getCliente().getIdCliente();
        restricciones += " AND " + fechas;

        return restricciones;
    }

    private String t503_ObtenerRestricciones(Parametros parametros) {
        String restricciones = "";
        String fechas = UtilFechas.construirFechas(parametros, "Guias_503.FechaPagoReal", "Guias_503.FechaPagoReal");

        restricciones += " WHERE DG.RFC = '" + parametros.getUsuario().getCliente().getRfcActual() + "' ";
        restricciones += " AND DS.FK01_Cliente = " + parametros.getUsuario().getCliente().getIdCliente();
        restricciones += " AND " + fechas;

        return restricciones;
    }

    private String t504_ObtenerRestricciones(Parametros parametros) {
        String restricciones = "";
        String fechas = UtilFechas.construirFechas(parametros, "Contenedores_504.FechaPagoReal", "Contenedores_504.FechaPagoReal");

        restricciones += " WHERE DG.RFC = '" + parametros.getUsuario().getCliente().getRfcActual() + "' ";
        restricciones += " AND DS.FK01_Cliente = " + parametros.getUsuario().getCliente().getIdCliente();
        restricciones += " AND " + fechas;

        return restricciones;
    }

    private String t505_ObtenerRestricciones(Parametros parametros) {
        String restricciones = "";
        String fechas = UtilFechas.construirFechas(parametros, "Facturas_505.FechaPagoReal", "Facturas_505.FechaPagoReal");

        restricciones += " WHERE DG.RFC = '" + parametros.getUsuario().getCliente().getRfcActual() + "' ";
        restricciones += " AND DS.FK01_Cliente = " + parametros.getUsuario().getCliente().getIdCliente();
        restricciones += " AND " + fechas;

        return restricciones;
    }

    private String t506_ObtenerRestricciones(Parametros parametros) {
        String restricciones = "";
        String fechas = UtilFechas.construirFechas(parametros, "Fechas_Pedimento_506.FechaValidacionPagoR", "Fechas_Pedimento_506.FechaValidacionPagoR");

        restricciones += " WHERE DG.RFC = '" + parametros.getUsuario().getCliente().getRfcActual() + "' ";
        restricciones += " AND DS.FK01_Cliente = " + parametros.getUsuario().getCliente().getIdCliente();
        restricciones += " AND " + fechas;

        return restricciones;
    }

    private String t507_ObtenerRestricciones(Parametros parametros) {
        String restricciones = "";
        String fechas = UtilFechas.construirFechas(parametros, "Casos_Pedimento_507.FechaValidacionPagoR", "Casos_Pedimento_507.FechaValidacionPagoR");

        restricciones += " WHERE DG.RFC = '" + parametros.getUsuario().getCliente().getRfcActual() + "' ";
        restricciones += " AND DS.FK01_Cliente = " + parametros.getUsuario().getCliente().getIdCliente();
        restricciones += " AND " + fechas;

        return restricciones;
    }

    private String t509_ObtenerRestricciones(Parametros parametros) {
        String restricciones = "";
        String fechas = UtilFechas.construirFechas(parametros, "Tasas_Pedimento_509.FechaPagoReal", "Tasas_Pedimento_509.FechaPagoReal");

        restricciones += " WHERE DG.RFC = '" + parametros.getUsuario().getCliente().getRfcActual() + "' ";
        restricciones += " AND DS.FK01_Cliente = " + parametros.getUsuario().getCliente().getIdCliente();
        restricciones += " AND " + fechas;

        return restricciones;
    }

    private String t502_ObtenerRestricciones(Parametros parametros) {
        String restricciones = "";
        String fechas = UtilFechas.construirFechas(parametros, "T502.FechaPagoReal", "T502.FechaPagoReal");

        restricciones += " WHERE DG.RFC = '" + parametros.getUsuario().getCliente().getRfcActual() + "' ";
        restricciones += " AND DS.FK01_Cliente = " + parametros.getUsuario().getCliente().getIdCliente();
        restricciones += " AND " + fechas;

        return restricciones;
    }

    private String t557_ObtenerRestricciones(Parametros parametros) {
        String restricciones = "";
        String fechas = UtilFechas.construirFechas(parametros, "t557.FechaPagoReal", "t557.FechaPagoReal");

        restricciones += " WHERE DG.RFC = '" + parametros.getUsuario().getCliente().getRfcActual() + "' ";
        restricciones += " AND DS.FK01_Cliente = " + parametros.getUsuario().getCliente().getIdCliente();
        restricciones += " AND " + fechas;

        return restricciones;
    }

    private String t510_ObtenerRestricciones(Parametros parametros) {
        String restricciones = "";
        String fechas = UtilFechas.construirFechas(parametros, "Contribuciones_Pedimento_510.FechaPagoReal", "Contribuciones_Pedimento_510.FechaPagoReal");

        restricciones += " WHERE DG.RFC = '" + parametros.getUsuario().getCliente().getRfcActual() + "' ";
        restricciones += " AND DS.FK01_Cliente = " + parametros.getUsuario().getCliente().getIdCliente();
        restricciones += " AND " + fechas;

        return restricciones;
    }

    private String t554_ObtenerRestricciones(Parametros parametros) {
        String restricciones = "";
        String fechas = UtilFechas.construirFechas(parametros, "Casos_Partida_554.FechaPagoReal", "Casos_Partida_554.FechaPagoReal");

        restricciones += " WHERE DG.RFC = '" + parametros.getUsuario().getCliente().getRfcActual() + "' ";
        restricciones += " AND DS.FK01_Cliente = " + parametros.getUsuario().getCliente().getIdCliente();
        restricciones += " AND " + fechas;

        return restricciones;
    }

    private String t556_ObtenerRestricciones(Parametros parametros) {
        String restricciones = "";
        String fechas = UtilFechas.construirFechas(parametros, "Tasas_Contribuciones_Partida_556.FechaPagoReal", "Tasas_Contribuciones_Partida_556.FechaPagoReal");

        restricciones += " WHERE DG.RFC = '" + parametros.getUsuario().getCliente().getRfcActual() + "' ";
        restricciones += " AND DS.FK01_Cliente = " + parametros.getUsuario().getCliente().getIdCliente();
        restricciones += " AND " + fechas;

        return restricciones;
    }

    private String t701_ObtenerRestricciones(Parametros parametros) {
        String restricciones = "";
        String fechas = UtilFechas.construirFechas(parametros, "Rectificaciones_701.FechaPagoReal", "Rectificaciones_701.FechaPagoReal");

        restricciones += " WHERE DG.RFC = '" + parametros.getUsuario().getCliente().getRfcActual() + "' ";
        restricciones += " AND DS.FK01_Cliente = " + parametros.getUsuario().getCliente().getIdCliente();
        restricciones += " AND " + fechas;

        return restricciones;
    }

    private String t702_ObtenerRestricciones(Parametros parametros) {
        String restricciones = "";
        String fechas = UtilFechas.construirFechas(parametros, "Diferencias_Contribuciones_Pedimento_702.FechaPagoReal", "Diferencias_Contribuciones_Pedimento_702.FechaPagoReal");

        restricciones += " WHERE DG.RFC = '" + parametros.getUsuario().getCliente().getRfcActual() + "' ";
        restricciones += " AND DS.FK01_Cliente = " + parametros.getUsuario().getCliente().getIdCliente();
        restricciones += " AND " + fechas;

        return restricciones;
    }

    @Override
    public List<DashBoard> t702_ObtenerTotalPorFormaPago(Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t702_ObtenerTotalPorFormaPago(t702_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t702_ObtenerOperacionesPorAduana(Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t702_ObtenerOperacionesPorAduana(t702_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t702_ObtenerOperacionesPorPatente(Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t702_ObtenerOperacionesPorPatente(t702_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t701_ObtenerOperacionesPorAduana(Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t701_ObtenerOperacionesPorAduana(t701_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t701_ObtenerOperacionesPorPatente(Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t701_ObtenerOperacionesPorPatente(t701_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t701_ObtenerOperacionesPorClaveDocumento(Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t701_ObtenerOperacionesPorClaveDocumento(t701_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t556_ObtenerOperacionesTipoTasa(Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t556_ObtenerOperacionesTipoTasa(t556_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t556_ObtenerOperacionesPorAgente(Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t556_ObtenerOperacionesPorAgente(t556_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t556_ObtenerOperacionesPorAduana(Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t556_ObtenerOperacionesPorAduana(t556_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t556_ObtenerOperacionesTasaContribucion(Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t556_ObtenerOperacionesTasaContribucion(t556_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t556_ObtenerOperacionesPorClave(Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t556_ObtenerOperacionesPorClave(t556_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t554_ObtenerOperacionesPorAgente(Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t554_ObtenerOperacionesPorAgente(t554_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t554_ObtenerOperacionesPorClave(Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t554_ObtenerOperacionesPorClave(t554_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t554_ObtenerOperacionesPorAduana(Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t554_ObtenerOperacionesPorAduana(t554_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t510_ObtenerOperacionesPorTipoPedimento(Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t510_ObtenerOperacionesPorTipoPedimento(t510_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t510_ObtenerOperacionesPorAgente(Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t510_ObtenerOperacionesPorAgente(t510_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t510_ObtenerOperacionesPorClave(Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t510_ObtenerOperacionesPorClave(t510_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t510_ObtenerOperacionesPorContribucion(Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t510_ObtenerOperacionesPorContribucion(t510_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t510_ObtenerOperacionesPorAduana(Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t510_ObtenerOperacionesPorAduana(t510_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t509_ObtenerPedimentoPorImporteTasa(Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t509_ObtenerPedimentoPorImporteTasa(t509_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t509_ObtenerPedimentoPorAgente(Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t509_ObtenerPedimentoPorAgente(t509_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t509_ObtenerPedimentoPorTipoTasa(Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t509_ObtenerPedimentoPorTipoTasa(t509_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t509_ObtenerPedimentoPorAduana(Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t509_ObtenerPedimentoPorAduana(t509_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t507_ObtenerPedimentoPorClave(Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t507_ObtenerPedimentoPorClave(t507_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t507_ObtenerPedimentoPorAduana(Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t507_ObtenerPedimentoPorAduana(t507_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t506_ObtenerFechaPorAgente(Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t506_ObtenerFechaPorAgente(t506_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t506_ObtenerFechaPorAduana(Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t506_ObtenerFechaPorAduana(t506_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t505_ObtenerFacturaPorMoneda(Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t505_getFacturaPorMoneda(t505_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t505_ObtenerFacturaPorPais(Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t505_getFacturaPorPais(t505_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t504_ObtenerContenedorPorAgente(Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t504_getContenedorPorAgente(t504_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t504_ObtenerContenedorPorTipo(Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t504_getContenedorPorTipo(t504_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t504_ObtenerContenedorPorAduana(Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t504_getContenedorPorAduana(t504_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t503_ObtenerGuiasPorAgente(Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t503_getGuiasPorAgente(t503_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t503_ObtenerGuiasPorAduana(Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t503_getGuiasPorAduana(t503_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t503_ObtenerGuiasPorTipo(Parametros parametros)
            throws GlosaNetException {
        try {
            return dashboardDAO.t503_getGuiasPorTipo(t503_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t501_obtenerOperacionesDelPeriodo(Parametros parametros)
            throws GlosaNetException {
        try {
            return dashboardDAO.t501_findOperacionesDelPeriodo(t501_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t501_obtenerOperacionesPorAduana(Parametros parametros)
            throws GlosaNetException {
        try {

            return dashboardDAO.t501_findOperacionesPorAduana(t501_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<DashBoard> t501_obtenerTipoCambioPeriodo(Parametros parametros)
            throws GlosaNetException {
        try {
            return dashboardDAO.t501_findTipoCambioPeriodo(t501_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t501_obtenerOperacionesPorMedioTransporteArribo(
            Parametros parametros) throws GlosaNetException {
        try {

            return dashboardDAO.t501_findOperacionesPorMedioTransporteArribo(t501_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t501_obtenerPesoBrutoPorMedioTransporteArribo(
            Parametros parametros) throws GlosaNetException {
        try {

            return dashboardDAO.t501_findPesoBrutoPorMedioTransporteArribo(t501_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t501_obtenerOperacionesPorTipoPedimento(
            Parametros parametros) throws GlosaNetException {
        try {

            return dashboardDAO.t501_getOperacionesPorTipoPedimento(t501_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t501_obtenerOperacionesPorClavePedimento(
            Parametros parametros) throws GlosaNetException {
        try {

            return dashboardDAO.t501_getOperacionesPorClavePedimento(t501_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t502_obtenerOperacionesPorAduana(
            Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t502_findOperacionesPorAduana(t502_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t502_obtenerOperacionesPorAgenteAduanal(
            Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t502_findOperacionesPorAgenteAduanal(t502_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t502_obtenerOperacionesPorPais(Parametros parametros)
            throws GlosaNetException {
        try {
            return dashboardDAO.t502_findOperacionesPorPais(t502_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t557_obtenerOperacionesPorFormaDePago(
            Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t557_findOperacionesPorFormaDePago(t557_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t557_obtenerImportePagoPorTipoContribucion(
            Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t557_findImportePagoPorTipoContribucion(t557_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t557_obtenerOperacionesPorAgenteAduanal(
            Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t557_findOperacionesPorAgenteAduanal(t557_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t557_obtenerOperacionesPorAduana(
            Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t557_findOperacionesPorAduana(t557_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DashBoard> t501_ObtenerOperacionesMedioTransporteSalida(
            Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t501_ObtenerOperacionesMedioTransporteSalida(t501_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DashBoard> t501_ObtenerOperacionesMedioTransporteArriboSalida(
            Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t501_ObtenerOperacionesMedioTransporteArriboSalida(t501_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DashBoard> t501_ObtenerOperacionesPorIncrementablesYDeducibles(
            Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t501_ObtenerOperacionesPorIncrementablesYDeducibles(t501_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DashBoard> t501_ObtenerOperacionesDestinoMercancia(
            Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t501_ObtenerOperacionesDestinoMercancia(t501_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DashBoard> t501_ObtenerOperacionesPorPatente(
            Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t501_ObtenerOperacionesPorPatente(t501_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DashBoard> t501_ObtenerOperacionesPorTipoPedimento(
            Parametros parametros) throws GlosaNetException {
        try {
            return dashboardDAO.t501_ObtenerOperacionesPorTipoPedimento(t501_ObtenerRestricciones(parametros));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
