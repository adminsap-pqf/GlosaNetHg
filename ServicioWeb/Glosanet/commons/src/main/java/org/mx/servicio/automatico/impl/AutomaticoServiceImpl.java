package org.mx.servicio.automatico.impl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jumpmind.symmetric.csv.CsvWriter;
import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.CorreoBO;
import org.mx.modelo.comun.bo.DescargaBO;
import org.mx.modelo.comun.bo.FileZip;
import org.mx.modelo.comun.bo.UsuarioBO;
import org.mx.modelo.registros.bo.NotificacionBO;
import org.mx.modelo.reportes.ReporteDICIPA_565;
import org.mx.modelo.util.Util;
import org.mx.persistencia.comun.DescargaDAO;
import org.mx.persistencia.comun.UsuarioDAO;
import org.mx.persistencia.registros.RegistrosDAO;
import org.mx.persistencia.reporte.AmedidaDAO;
import org.mx.persistencia.reporte.TablaDataStageDAO;
import org.mx.servicio.automatico.AutomaticoService;
import org.mx.servicio.descomprimir.DescomprimirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

@Service("automaticoService")
public class AutomaticoServiceImpl implements AutomaticoService {
    private final static Logger log = Logger.getLogger(AutomaticoServiceImpl.class.getName());
    
    DescargaDAO descargaDAO;
    TablaDataStageDAO tablaDataStageDAO;
    AmedidaDAO amedidaDAO;
    UsuarioDAO usuarioDAO;
    DescomprimirService descomprimirService;
    RegistrosDAO registrosDAO;

    private Map<String, Object> fallos;

    @Autowired
    public AutomaticoServiceImpl(DescargaDAO descargaDAO, TablaDataStageDAO tablaDataStageDAO, AmedidaDAO amedidaDAO,
                                 UsuarioDAO usuarioDAO, DescomprimirService descomprimirService,
                                 RegistrosDAO registrosDAO) {
        this.descargaDAO = descargaDAO;
        this.tablaDataStageDAO = tablaDataStageDAO;
        this.amedidaDAO = amedidaDAO;
        this.usuarioDAO = usuarioDAO;
        this.descomprimirService = descomprimirService;
        this.registrosDAO = registrosDAO;
    }

    public DescargaDAO getDescargaDAO() {
        return descargaDAO;
    }
    public void setDescargaDAO(DescargaDAO descargaDAO) {
        this.descargaDAO = descargaDAO;
    }
    public TablaDataStageDAO getTablaDataStageDAO() {
        return tablaDataStageDAO;
    }
    public void setTablaDataStageDAO(TablaDataStageDAO tablaDataStageDAO) { this.tablaDataStageDAO = tablaDataStageDAO; }
    public AmedidaDAO getAmedidaDAO() {
        return amedidaDAO;
    }
    public void setAmedidaDAO(AmedidaDAO amedidaDAO) {
        this.amedidaDAO = amedidaDAO;
    }
    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }
    public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }
    public DescomprimirService getDescomprimirService() {
        return descomprimirService;
    }
    public void setDescomprimirService(DescomprimirService descomprimirService) { this.descomprimirService = descomprimirService; }
    public RegistrosDAO getRegistrosDAO() {
        return registrosDAO;
    }
    public void setRegistrosDAO(RegistrosDAO registrosDAO) {
        this.registrosDAO = registrosDAO;
    }

    @Override
    public Boolean borrarArchivosTemporales() throws GlosaNetException {
        File file = new File(Util.rutaTemporal);
        try {
            for (File arch : file.listFiles()) {
                if (arch.isDirectory()) {
                    arch.listFiles();
                }
                log.info("@borrarArchivosTemporales -> Última modificación: " + arch.lastModified());
            }
        } catch (Exception e) {
            log.severe("@borrarArchivosTemporales -> Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Boolean crearReportesDescargas() throws GlosaNetException {
        try {
            log.info("@crearReportesDescargas -> Generando nuevo reporte de descargas");
            List<String[]> consultas = descargaDAO.findOperacionesNoHechas();

            String rutas = "";
            if (!consultas.isEmpty()) {
                for (String[] sql : consultas) {

                    String[] encabezados = sql[2].split(",");
                    String directorio = Util.rutaTemporal + "Reportes/" + sql[1] + "/";

                    if (!new File(directorio).exists()) {
                        new File(directorio).mkdirs();
                    }

                    List<String[]> r = tablaDataStageDAO.findResultadosDeReportePersonalizado(sql[3]);

                    if (r.size() > 0) {
                        boolean alreadyExists = new File(directorio + "Reporte_" + sql[0] + ".csv").exists();

                        if (!new File(directorio).exists()) {
                            new File(directorio).mkdirs();
                            log.info("SE CREO LA CARPETA");
                        }
                        if (alreadyExists) {
                            File ficheroUsuarios = new File(directorio + "Reporte_" + sql[0] + ".csv");
                            ficheroUsuarios.delete();
                        }

                        CsvWriter csvOutput = new CsvWriter(new FileWriter(directorio + "Reporte_" + sql[0] + ".csv", true), ',');
                        csvOutput.writeRecord(encabezados);

                        for (String[] rr : r) {
                            csvOutput.writeRecord(rr);
                        }

                        csvOutput.close();

                        String[] lstFiles = {directorio + "Reporte_" + sql[0] + ".csv"};
                        String[] lstNombres = {"Reporte_" + sql[0] + ".csv"};
                        Util.createZipFileReporte(lstFiles, lstNombres, Util.rutaTemporalVista + "Reportes/" + sql[1]);
                        rutas = "/" + Util.rutaTemporalVista + "Reportes/" + sql[1] + "/Archivos.zip";
                        log.info("@crearReportesDescargas -> Ruta: " + rutas);

                        descargaDAO.updatePeticionDescarga(sql[0], rutas);
                        enviarMensajeVista(sql[0], "PeticionDescarga");
                    } else {
                        descargaDAO.updatePeticionDescarga(sql[0], "No se encontraron resultados");
                        enviarMensajeVista(sql[0], "PeticionDescarga");
                    }
                }
            }
            return true;
        } catch (Exception e) {
            log.severe("@crearReportesDescargas -> Error al generar el reporte: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Boolean enviarMensajeVista(String mensaje, String tipoMensaje) throws GlosaNetException {
        try {
            //			log.info("Mensaje:" + mensaje);
            //			AsyncMessage msg = new AsyncMessage();
            //			msg.setClientId("Java-Based-Producer-For-Messaging");
            //			msg.setTimestamp(new Date().getTime());
            //			msg.setMessageId(tipoMensaje);
            //			msg.setDestination("descargas");
            //			msg.setBody(mensaje);
            //			msg.setHeader("sender", "From the server");
            //
            //			MessageBroker.getMessageBroker(null).routeMessageToService(msg, null);


            return true;
        } catch (Exception e) {
            log.severe("@enviarMensajeVista -> Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Boolean crearReportes_DICIPA() throws GlosaNetException {
        try {
            int indexRow = 0;
            log.info("@crearReportes_DICIPA -> Generando nuevo reporte de DICIPA");
            List<String[]> consultas = descargaDAO.findReportesNoHechas();
            String rutas = "";
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            if (!consultas.isEmpty() && consultas.size() > 0) {
                for (String[] sql : consultas) {
                  String directorio = Util.rutaTemporal + "Reportes/" + sql[1] + "/";
                    //String directorio = "C:\\Users\\Fernando Betanzos\\Downloads\\" + sql[1] + "\\";//Pruebas locales
                    if (!new File(directorio).exists()) {
                        new File(directorio).mkdirs();
                    }

                    log.info(sql[3]);
                    List<ReporteDICIPA_565> generales = amedidaDAO.findReporteMedidaMensualDICIPA_565_Generales(sql[3]);
                    Workbook workbook = new HSSFWorkbook();
//					CreationHelper createHelper = workbook.getCreationHelper();

                    workbook.createSheet("Generales");

                    Sheet sheet = workbook.getSheetAt(0);
                    Row row = sheet.createRow(indexRow);
                    Cell cell = row.createCell(0);
                    cell.setCellValue("Patente");
                    cell = row.createCell(1);
                    cell.setCellValue("Pedimento");
                    cell = row.createCell(2);
                    cell.setCellValue("Seccion Aduanera");
                    cell = row.createCell(3);
                    cell.setCellValue("Numero Guia");
                    cell = row.createCell(4);
                    cell.setCellValue("Tipo Guia");
                    cell = row.createCell(5);
                    cell.setCellValue("Tipo Contenedor");
                    cell = row.createCell(6);
                    cell.setCellValue("Numero Contenedor");
                    cell = row.createCell(7);
                    cell.setCellValue("Clave Documento");
                    cell = row.createCell(8);
                    cell.setCellValue("RFC");
                    cell = row.createCell(9);
                    cell.setCellValue("Proveedor Mercancia");
                    cell = row.createCell(10);
                    cell.setCellValue("Pais Comprador Vendedor");
                    cell = row.createCell(11);
                    cell.setCellValue("Valor Dolares");
                    cell = row.createCell(12);
                    cell.setCellValue("Tipo Cambio");
                    cell = row.createCell(13);
                    cell.setCellValue("Total Fletes");
                    cell = row.createCell(14);
                    cell.setCellValue("Total Seguros");
                    cell = row.createCell(15);
                    cell.setCellValue("Peso Bruto Mercancia");
                    cell = row.createCell(16);
                    cell.setCellValue("Medio Transporte Salida");
                    cell = row.createCell(17);
                    cell.setCellValue("Medio Transporte Arribo");
                    cell = row.createCell(18);
                    cell.setCellValue("Medio Transporte Entrada/Salida");
                    cell = row.createCell(19);
                    cell.setCellValue("Destino Mercancia");
                    cell = row.createCell(20);
                    cell.setCellValue("Total Embalajes");
                    cell = row.createCell(21);
                    cell.setCellValue("Tipo Pedimento");
                    cell = row.createCell(22);
                    cell.setCellValue("Clave Contribucion partida");
                    cell = row.createCell(23);
                    cell.setCellValue("ImportePagoPartida");
                    cell = row.createCell(24);
                    cell.setCellValue("Clave Contribucion Pedimento");
                    cell = row.createCell(25);
                    cell.setCellValue("Tasa contribucion");
                    cell = row.createCell(26);
                    cell.setCellValue("Importe Pago Pedimento");
                    cell = row.createCell(27);
                    cell.setCellValue("Fecha Pago Real");
                    indexRow++;

                    for (ReporteDICIPA_565 item : generales) {
                        row = sheet.createRow(indexRow);
                        cell = row.createCell(0);
                        cell.setCellValue(item.getPatente());
                        cell = row.createCell(1);
                        cell.setCellValue(item.getPedimento());
                        cell = row.createCell(2);
                        cell.setCellValue(item.getSeccionAduanera());
                        cell = row.createCell(3);
                        cell.setCellValue(item.getNumeroGuia());
                        cell = row.createCell(4);
                        cell.setCellValue(item.getTipoGuia());
                        cell = row.createCell(5);
                        cell.setCellValue(item.getTipoContenedor());
                        cell = row.createCell(6);
                        cell.setCellValue(item.getNumContenedor());
                        cell = row.createCell(7);
                        cell.setCellValue(item.getClaveDocumento());
                        cell = row.createCell(8);
                        cell.setCellValue(item.getRfc());
                        cell = row.createCell(9);
                        cell.setCellValue(item.getProveedorMercancia());
                        cell = row.createCell(10);
                        cell.setCellValue(item.getPaisCompradorVendedor());
                        cell = row.createCell(11);
                        cell.setCellValue(item.getValorDolares());
                        cell = row.createCell(12);
                        cell.setCellValue(item.getTipoCambio());
                        cell = row.createCell(13);
                        cell.setCellValue(item.getTotalFletes());
                        cell = row.createCell(14);
                        cell.setCellValue(item.getTotalSeguros());
                        cell = row.createCell(15);
                        cell.setCellValue(item.getPesoBrutoMercancia());
                        cell = row.createCell(16);
                        cell.setCellValue(item.getMedioTransporteSalida());
                        cell = row.createCell(17);
                        cell.setCellValue(item.getMedioTransporteArribo());
                        cell = row.createCell(18);
                        cell.setCellValue(item.getMedioTransporteEntrada_Salida());
                        cell = row.createCell(19);
                        cell.setCellValue(item.getDestinoMercancia());
                        cell = row.createCell(20);
                        cell.setCellValue(item.getTotalEmbalajes());
                        cell = row.createCell(21);
                        cell.setCellValue(item.getTipoPedimento());
                        cell = row.createCell(22);
                        cell.setCellValue(item.getTasaContribucion());
                        cell = row.createCell(23);
                        cell.setCellValue(item.getClaveContribucion());
                        cell = row.createCell(24);
                        cell.setCellValue(item.getImportePago());
                        cell = row.createCell(25);
                        cell.setCellValue(item.getValorComercialDolares());
                        cell = row.createCell(26);
                        cell.setCellValue(item.getImportePagoPedimento());

                        cell = row.createCell(27);
                        //cell.setCellType(CellType.STRING);
                        log.info("\n ******* \n ******* \n Fecha Pago Real String EXCEL" + item.getFechaPagoReal());
                        calendar.setTime(item.getFechaPagoReal());
                        cell.setCellValue(formatter.format(calendar.getTime()));
//						

                        indexRow++;
                    }

                    boolean alreadyExists = new File(directorio + "Reporte_" + sql[0] + ".xls").exists();

                    if (!new File(directorio).exists()) {
                        new File(directorio).mkdirs();
                        log.info("SE CREO LA CARPETA");
                    }
                    if (alreadyExists) {
                        File ficheroUsuarios = new File(directorio + "Reporte_" + sql[0] + ".xls");
                        ficheroUsuarios.delete();
                    }

                    FileOutputStream fileOut = new FileOutputStream(directorio + "Reporte_" + sql[0] + ".xls");
                    workbook.write(fileOut);
                    workbook.close();
                    fileOut.close();


                    rutas = "/" + Util.rutaTemporalVista + "Reportes/" + sql[1] + "/Reporte_" + sql[0] + ".xls";
                   // rutas = "C:\\Users\\Fernando Betanzos\\Downloads\\" + sql[1] + "\\Reporte_" + sql[0] + ".xls";

                    descargaDAO.updatePeticionDescarga(sql[0], rutas);
                    enviarMensajeVista(sql[0], "PeticionDescarga");
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean verificarDataStage() throws Exception {
        log.info("ENTRO A VERIFICAR");
        fallos = new HashMap<String, Object>();
        List<DescargaBO> lstDataStage = descargaDAO.findOperacionesPorTipo("DataStage", false);

        for (DescargaBO descargaBO : lstDataStage) {
            Map<String, Object> map = new HashMap<String, Object>();
            UsuarioBO usuario = usuarioDAO.findUsuario(descargaBO.getIdUsuario());
            usuario.getCliente().setRfcActual(descargaBO.getLocacion());

            log.info("@verificarDataStage -> Id Cliente de datastage: " + usuario.getCliente().getIdCliente());

            File file = new File(descargaBO.getSqlConsulta() + descargaBO.getEncabezados());
            if (file.exists() && !file.isDirectory()) {
                FileZip zip = new FileZip();
                zip.setNombre(descargaBO.getEncabezados());
                Path path = Paths.get(file.getPath());
                try {
                    byte[] data = Files.readAllBytes(path);
                    zip.setBytes(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                zip.setExtension("zip");
                try {
                    map = descomprimirService.extractFile(zip, usuario);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                descargaBO.setDescargado(true);
                descargaDAO.updateEstadoActivo(descargaBO);


                //Agregar nuevos agentes aduanales ------------------------------------------
            } else {
                fallos.put("DataStage", "Se ha detectado un error en el dataStage, intente subirlo nuevamente");
                map.put("fallos", fallos);
            }
            map.put("folio", descargaBO.getEncabezados());
            enviarMensajeVista(map, descargaBO.getIdDescarga());
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Boolean enviarMensajeVista(Map<String, Object> mensaje, int idDescarga) throws GlosaNetException {
        try {
            String mensajeTxt = "";
            if (mensaje.get("resultado") != null && (int) mensaje.get("resultado") != 1) {
                mensajeTxt += mensaje.get("fallos");
            }

            descargaDAO.updateError(idDescarga, mensajeTxt);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean enviarRecordatoriosRegistros() throws GlosaNetException {

        String codigo = "";
        String nombProducto = "";
        String fechaLimite = "";
        String nombEvento = "";
        String fechaRealizacion = "";


        List<NotificacionBO> noti = registrosDAO.findNotificacionesPorEnviar();


        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        String hoy = sdf.format(new Date());


        for (NotificacionBO n : noti) {

            boolean enviar = false;

            String date2 = sdf.format(n.getFinicio());
            if (hoy.equals(date2)) {
                enviar = true;
            } else {
                Date inicio = n.getFinicio();

                Calendar calendar = new GregorianCalendar(inicio.getYear(), inicio.getMonth(), inicio.getDate());

                if (n.getRecurrencia().equals("Cada Mes")) {
                    calendar.add(Calendar.MONTH, 1);
                    date2 = sdf.format(calendar.getTime());
                    if (hoy.equals(date2)) {
                        enviar = true;
                    }
                } else if (n.getRecurrencia().equals("Cada Día")) {
                    enviar = true;
                } else if (n.getRecurrencia().equals("Cada Semana")) {
                    calendar.add(Calendar.DAY_OF_MONTH, 7);
                    date2 = sdf.format(calendar.getTime());
                    if (hoy.equals(date2)) {
                        enviar = true;
                    }
                } else if (n.getRecurrencia().equals("Cada Año")) {
                    calendar.add(Calendar.MONTH, 12);
                    date2 = sdf.format(calendar.getTime());
                    if (hoy.equals(date2)) {
                        enviar = true;
                    }
                }
            }

            if (enviar) {
                /**
                 * Obtener datos del evento y del producto
                 */
                n.getIdEvento();

                String correoHTML = Util.textoMensajeNotificacion(codigo, nombProducto, fechaLimite, nombEvento, fechaRealizacion, "");

                CorreoBO enviarC = new CorreoBO();
                enviarC.setCorreo("");
                enviarC.setAsunto("GlosaNet - Recordatorio");
                enviarC.setCuerpoCorreo(correoHTML);
//				log.info(correoHTML);
                Util.enviarCorreo(enviarC);
            }
        }

        return false;
    }


}
