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
import org.mx.modelo.util.Util;
import org.mx.servicio.comun.ArchivoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class ArchivoServiceImpl implements ArchivoService {
    private final static Logger log = Logger.getLogger(ArchivoServiceImpl.class.getName());

    @Override
    public byte[] crearArchivoPDF(Map<String, Object> arguments, String tipo)
            throws Exception {
        try {
            log.info("@crearArchivoPDF - Generando archivo PDF");
            AsistentesDataSource archivoPDF = new AsistentesDataSource();
            archivoPDF.addAsistente("");
            arguments.put("dataSource", archivoPDF);
            List<JasperPrint> list = new ArrayList<JasperPrint>();
            List<String> templates = listaJasperPrint(tipo); //aqui obtiene los templates necesarios para hacer el archivo PDF
            Integer pag = 1;

            for (String t : templates) {
                // el set-o de las variables se hace desde el servicio que manda llamar este metodo
                archivoPDF = new AsistentesDataSource();
                archivoPDF.addAsistente("");
                arguments.put("dataSource", archivoPDF);
                list.add(generarPDF(arguments, Util.rutaArchivos + "Template" + t, pag.toString()));
                pag++;
            }

            File file;
            File targetFile;

            //String ahora = new SimpleDateFormat("yyyyMMddhhmm'.pdf'").format(new Date());

            if (tipo.equals("Credito")) {
                String dir = Util.rutaArchivos + "Cliente/" + arguments.get("idCliente") + "/Credito";
                file = new File(dir);
                targetFile = new File(dir + "/" + "Credito.pdf");
            } else if (tipo.equals("Cotizacion")) {
                String dir = Util.rutaArchivos + "Cliente/" + arguments.get("idCliente") + "/Cotizacion";
                file = new File(dir);
                targetFile = new File(dir + "/" + "Cotizacion_" + arguments.get("idCotizacion") + ".pdf");
            } else if (tipo.equals("Pedido")) {
                String dir = Util.rutaArchivos + "Cliente/" + arguments.get("idCliente") + "/Pedido";
                file = new File(dir);
                targetFile = new File(dir + "/" + "Pedido_" + arguments.get("idPedido") + ".pdf");
            } else if (tipo.equals("Producto")) {
                String dir = Util.rutaArchivos + "Producto";
                file = new File(dir);
                targetFile = new File(dir + "/" + arguments.get("folio") + ".pdf");
            } else if (tipo.equals("Produccion")) {
                String dir = Util.rutaArchivos + "Proveedor/" + arguments.get("idProveedor") + "/OrdenesProduccion";
                file = new File(dir);
                targetFile = new File(dir + "/OrdenProduccion_" + arguments.get("idProduccion") + ".pdf");
            } else if (tipo.equals("Remision")) {
                String dir = Util.rutaArchivos + "Cliente/" + arguments.get("idCliente") + "/NotasRemision";
                file = new File(dir);
                targetFile = new File(dir + "/NotaRemision_" + arguments.get("folio") + ".pdf");
            } else {
                file = new File(Util.rutaArchivos);
                targetFile = new File(Util.rutaArchivos + "/" + "archivo.pdf");
            }

            if (!file.exists()) {
                log.info("@crearArchivoPDF -> La ruta no existe: " + file.getPath());
                file.mkdirs();
            }

            log.info("@crearArchivoPDF -> Ruta de guardado de archivo PDF: " + targetFile.getAbsolutePath());
            OutputStream outStream;

            byte[] bytes = generateReport(list);
            outStream = new FileOutputStream(targetFile);
            outStream.write(bytes);
            outStream.close();
            log.info("@crearArchivoPDF -> Se generó correctamente el archivo: " + targetFile.getName());

            return bytes;

        } catch (Exception e) {
            log.severe("@crearArchivoPDF -> Error al crear el archivo PDF. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private List<String> listaJasperPrint(String tipo) {
        List<String> templates = new ArrayList<String>();

        switch (tipo) {
            case "Credito":
                templates.clear();
                templates.add("/credito.jasper");
                break;
            case "Cotizacion":
                templates.clear();
                templates.add("/Cotizacion.jasper");
                break;
            case "Cotizacion2":
                templates.clear();
                templates.add("/Cotizacion2.jasper");
                break;
            case "Pedido":
                templates.clear();
                templates.add("/Pedidos.jasper");
                break;
            case "Producto":
                templates.clear();
                templates.add("/Producto.jasper");
                break;
            case "Produccion":
                templates.clear();
                templates.add("/ordenProduccion.jasper");
                break;
            case "Remision":
                templates.clear();
                templates.add("/Remision.jasper");
                break;
        }

        return templates;
    }

    private JasperPrint generarPDF(Map<String, Object> arguments, String path,
                                   String index) {
        JasperPrint jasperPrint = null;

        try {
            JasperReport jasperReport = (JasperReport) JRLoader
                    .loadObjectFromFile(path); // utiliza los archivos
            // compilados de Jasper
            // (.jasper)

            jasperPrint = JasperFillManager.fillReport(jasperReport, arguments,
                    (AsistentesDataSource) arguments.get("dataSource"));

            return jasperPrint;
        } catch (JRException e) {
            log.severe("@generarPDF -> Error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public byte[] generateReport(List<JasperPrint> jasperPrintList)
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
            log.severe("@generarPDF -> Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public String subirArchivo(File archivo, String nombreArchivo, String path)
            throws Exception {
        try {
            String dir = Util.rutaArchivos + path;
            File file = new File(dir);
            File targetFile = new File(dir + "/" + nombreArchivo + ".pdf");
            if (!file.exists()) {
                file.mkdirs();
            }
            OutputStream outStream;
            outStream = new FileOutputStream(targetFile);
            byte[] array = Files.readAllBytes(archivo.toPath());
            outStream.write(array);
            outStream.close();
            return "Exito";
        } catch (Exception e) {
            log.severe("@subirArchivo -> Error: " + e.getMessage());
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus()
                    .setRollbackOnly();
            throw new Exception();
        }
    }

    @Override
    public String buscarArchivos(String path, String[] archivos) {
        String archivosExistentes = "";

        for (String archivo : archivos) {
            String dir = Util.rutaArchivos + path;
            File file = new File(dir + "/" + archivo + ".pdf");
            if (file.exists()) {
                archivosExistentes += archivo + "|";
            }
        }

        return archivosExistentes;
    }

    @Override
    public ByteArrayOutputStream obtenerrArchivo(String path) throws Exception {
        try {
            String dir = Util.rutaArchivos + path;
            log.info("@obtenerrArchivo -> Directorio: " + dir);

            File file = new File(dir);
            InputStream fileStream = new FileInputStream(file);
            byte[] data = new byte[1024];
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int read = 0;

            while ((read = fileStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, read);
            }

            buffer.flush();
            fileStream.close();

            return buffer;
        } catch (Exception e) {
            log.severe("@obtenerrArchivo -> Error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean buscarArchivo(String path, String archivo) {
        String dir = Util.rutaArchivos + path;
        File file = new File(dir + "/" + archivo + ".pdf");
        return file.exists();
    }
}
