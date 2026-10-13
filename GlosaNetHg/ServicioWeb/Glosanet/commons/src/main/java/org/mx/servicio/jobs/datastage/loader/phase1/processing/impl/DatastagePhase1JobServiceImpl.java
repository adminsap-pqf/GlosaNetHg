package org.mx.servicio.jobs.datastage.loader.phase1.processing.impl;

import org.apache.commons.lang3.StringUtils;
import org.mx.controllers.jobs.datastage.phase1.DatastagePhase1JobsRequestOptions;
import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.DescargaBO;
import org.mx.modelo.comun.bo.FileZip;
import org.mx.modelo.comun.bo.UsuarioBO;
import org.mx.modelo.util.Util;
import org.mx.persistencia.comun.DescargaDAO;
import org.mx.persistencia.comun.UsuarioDAO;
import org.mx.persistencia.comun.impl.DataStageDAOImpl;
import org.mx.servicio.descomprimir.DescomprimirService;
import org.mx.servicio.jobs.datastage.loader.phase1.processing.DatastagePhase1JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ryndem.mx.glosanet.messagebroker.datastage.loader.phase1.persistence.JobDAO;
import ryndem.mx.glosanet.messagebroker.datastage.loader.phase1.persistence.pages.AllJobsPage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static org.mx.controllers.jobs.datastage.phase1.DatastagePhase1JobsRequestOptions.*;
import static ryndem.mx.glosanet.messagebroker.config.Protocols.PHASE1_JOB_DAO_NAME;

@Service
public class DatastagePhase1JobServiceImpl implements DatastagePhase1JobService {
    private static final Logger log = Logger.getLogger(DatastagePhase1JobServiceImpl.class.getName());

    @Autowired
    UsuarioDAO usuarioDAO;

    @Autowired
    DescargaDAO descargaDAO;

    @Autowired
    DescomprimirService descomprimirService;

    private JobDAO jobDAO;
    @Autowired
    @Qualifier(PHASE1_JOB_DAO_NAME)
    public void setJobDAO(JobDAO jobDAO) {
        this.jobDAO = jobDAO;
    }

    @Value("${environment}")
    private String environment;

    @Override
    public AllJobsPage listPaginatedJobs(DatastagePhase1JobsRequestOptions requestOptions) throws GlosaNetException {

        try {

            StringBuilder sqlQuery = new StringBuilder(
                            "SELECT F.PK_FASE1RMQMensaje, F.GUID, F.status, F.creationDate, ");
            sqlQuery.append("IF((F.stackTrace <> NULL OR F.stackTrace <> ''), 'true', 'false') as stackTrace, ");
            sqlQuery.append("D.Encabezados AS NombreArchivo, D.Error, U.Nombre, U.Apellido, ");
            sqlQuery.append("(@row_number \\:= @row_number + 1) AS RecordIndex ");
            sqlQuery.append("FROM GN_Fase1RMQMensaje AS F ");
            sqlQuery.append("INNER JOIN GN_Descargas AS D ON F.FK01_Descarga = D.PK_Descargas ");
            sqlQuery.append("INNER JOIN GN_Usuarios AS U ON D.FK01_Usuario = U.PK_Usuario ");
            sqlQuery.append("CROSS JOIN ( SELECT @row_number \\:= 0 ) as parameters ");

            boolean sqlWhereConditionalSet = false;

            String filterInitialDate = requestOptions.getFilterInitialDate();
            String filterFinalDate = requestOptions.getFilterFinalDate();
            String filterStatus = requestOptions.getFilterStatus();
            String filterCompany = requestOptions.getFilterCompany();

            //FILTERS
            if (!StringUtils.equals(filterInitialDate, "")) {
                sqlQuery.append("where creationDate >= '" + filterInitialDate + "' ");
                sqlWhereConditionalSet = true;
            }
            if (!StringUtils.equals(filterFinalDate, "")) {
                String condition;
                if (sqlWhereConditionalSet) {
                    condition = "and creationDate <= '" + filterFinalDate + "' ";
                } else {
                    condition = "where creationDate <= '" + filterFinalDate + "' ";
                    sqlWhereConditionalSet = true;
                }
                sqlQuery.append(condition);
            }
            if (!StringUtils.equals(filterStatus, "")) {
                if (sqlWhereConditionalSet)
                    sqlQuery.append("and status = " + filterStatus + " ");
                else {
                    sqlQuery.append("where status = " + filterStatus + " ");
                    sqlWhereConditionalSet = true;
                }
            }
            if (!StringUtils.equals(filterCompany, "")) {
                sqlQuery.append(sqlWhereConditionalSet
                        ? "and FK01_Cliente = " + filterCompany + " "
                        : "where FK01_Cliente = " + filterCompany + " ");
            }

            //ORDER BY VALUE = DATE | STATUS
            String orderByValue = StringUtils.equals(
                    requestOptions.getOrderBy(), DSP1J_ORDERBYVALUE_STATUS)
                    ? DSP1J_ORDERBYVALUE_STATUS : DSP1J_ORDERBYVALUE_CREATIONDATE;
            sqlQuery.append("order by " + orderByValue + " ");

            boolean isDescending = StringUtils.equals(requestOptions.getOrderBySymbol(), DSP1J_ORDERBY_DESC);
            sqlQuery.append(isDescending ? "desc" : "asc");

            return jobDAO.findJobs(sqlQuery.toString(), requestOptions.getPage(), requestOptions.getSize());

        } catch (Exception e) {
            e.printStackTrace();
            throw new GlosaNetException(e);
        }

    }

    @Override
    public Map<String, Object> process(Integer idUsuario, Integer idCliente, String rfc, String filePath, Integer idDescarga) {

        Map<String, Object> mapa = new HashMap<>();
        mapa.put("resultado", -1); // -1 = error del sistema
        mapa.put("fallos", generaMensajeDeError(""));
        mapa.put("consumerProcessResult", 0);

        //0. Obtenemos la referencia de la descarga
        DescargaBO descarga = null;
        try {
            descarga = descargaDAO.findById(idDescarga);
        } catch (Exception e) {
            //error al realizar operación en DAO; no se pudo traer la descarga
            e.printStackTrace();
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String sStackTrace = sw.toString();
            mapa.put("stackTrace", sStackTrace.length() > 500
                    ? sStackTrace.substring(0, 499)
                    : sStackTrace);
            mapa.put("fallos", generaMensajeDeError("Descarga no encontrada"));
        } finally {
            //No se encontró descarga
            if (null == descarga) {
                actualizaEstadoDeError(mapa, idDescarga);
                return mapa;
            }
        }

        //1. Obtenemos el objeto usuario asociado a esta operación
        UsuarioBO usuario = null;
        try {
            usuario = usuarioDAO.findUsuario(idUsuario);
        } catch (Exception e) {
            //error al realizar operación en DAO; no se puedo traer el usuario
            e.printStackTrace();
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String sStackTrace = sw.toString();
            mapa.put("stackTrace", sStackTrace.length() > 500
                    ? sStackTrace.substring(0, 499)
                    : sStackTrace);
            mapa.put("fallos", generaMensajeDeError("Usuario no encontrado"));
        } finally {
            if (null == usuario) {
                actualizaEstadoDeError(mapa, idDescarga);
                return mapa;
            }
        }

        //2. Procesamos datastage
        usuario.getCliente().setRfcActual(rfc);
        log.info("Id de cliente de DataStage: " + usuario.getCliente().getIdCliente());

        File file = new File(filePath);
        if (file.exists() && !file.isDirectory()) {
            FileZip zip = new FileZip();
            zip.setNombre(descarga.getEncabezados());
            Path path = Paths.get(file.getPath());
            try {
                // 2.1 Construimos objeto zip a descomprimir a partir de la ruta provista en el mensaje de rabbitmq
                byte[] data = Files.readAllBytes(path);
                zip.setBytes(data);
            } catch (IOException e) {
                e.printStackTrace();
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String sStackTrace = sw.toString();
                mapa.put("stackTrace", sStackTrace.length() > 500
                        ? sStackTrace.substring(0, 499)
                        : sStackTrace);
                mapa.put("fallos", generaMensajeDeError("Error al intentar leer el archivo"));
                actualizaEstadoDeError(mapa, idDescarga);
                return mapa;
            }
            zip.setExtension("zip");
            try {
                // 2.2 Enviamos objeto zip a descomprimir en ruta temporal
                mapa = descomprimirService.extractFile(zip, usuario);
            } catch (Exception e) {
                e.printStackTrace();
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String sStackTrace = sw.toString();
                mapa.put("stackTrace", sStackTrace.length() > 500
                        ? sStackTrace.substring(0, 499)
                        : sStackTrace);
                mapa.put("fallos", generaMensajeDeError("Error al intentar procesar el datastage"));
                actualizaEstadoDeError(mapa, idDescarga);
                limpiaArchivosDataStage(idCliente, rfc, DataStageDAOImpl.dataStageBO.getFolio());
                return mapa;
            }

            descarga.setDescargado(true);

            try {
                descargaDAO.updateEstadoActivo(descarga);
            } catch (Exception e) {
                //no se puedo actualizar el estado de la descarga
                e.printStackTrace();
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String sStackTrace = sw.toString();
                mapa.put("stackTrace", sStackTrace.length() > 500
                        ? sStackTrace.substring(0, 499)
                        : sStackTrace);
                mapa.put("fallos", generaMensajeDeError("No se puedo actualizar el estado de la descarga"));
                actualizaEstadoDeError(mapa, idDescarga);
                limpiaArchivosDataStage(idCliente, rfc, DataStageDAOImpl.dataStageBO.getFolio());
                return mapa;
            }

        } else {
            //error con el archivo => no existe o es un directorio; se espera un zip
            mapa.put("stackTrace", "");
            mapa.put("fallos", generaMensajeDeError("El archivo no existe o es un directorio"));
            actualizaEstadoDeError(mapa, idDescarga);
            return mapa;
        }

        // resultadoCarga = 0 ? then fallo
        Integer resultadoCarga = (Integer) mapa.get("resultado") != 1 ? 0 : 1;

        if (resultadoCarga == 1) {
            // Proceso exitoso
            // copiamos archivos de carpeta temporal a carpeta en recurso compartido
            // eliminamos archivos de carpeta temporal
            try {
                //Copia de zip original de datastage
                /*if(environment != "production") {
                    Util.copiaArchivoEnRutaDestino(
                            new File(Util.rutaTempDatastages + idCliente + "/" + rfc + "/"
                                    + DataStageDAOImpl.dataStageBO.getFolio() + ".zip"),
                            Util.rutaTestDatastages + idCliente + "/" + rfc + "/",
                            DataStageDAOImpl.dataStageBO.getFolio() + ".zip");
                    limpiaArchivosDataStage(idCliente, rfc, DataStageDAOImpl.dataStageBO.getFolio());
                } else {*/
                    Util.copiaArchivoEnRutaDestino(
                            new File(Util.rutaTempDatastages + idCliente + "/" + rfc + "/"
                                    + DataStageDAOImpl.dataStageBO.getFolio() + ".zip"),
                            Util.rutaDatastages + idCliente + "/" + rfc + "/",
                            DataStageDAOImpl.dataStageBO.getFolio() + ".zip");
                    limpiaArchivosDataStage(idCliente, rfc, DataStageDAOImpl.dataStageBO.getFolio());
                //}

                mapa.put("idDatastage", DataStageDAOImpl.dataStageBO.getIdDataStage() /*getIdDatastage(idCliente, DataStageDAOImpl.dataStageBO.getFolio())*/);

            } catch (IOException e) {
                log.severe("Error en el proceso de phase1 -> " + e.getMessage());
                e.printStackTrace();
            }
        }


        mapa.put("stackTrace", "");
        mapa.put("consumerProcessResult", resultadoCarga);
        actualizaEstadoDeError(mapa, idDescarga);
        mapa.put("folio", descarga.getEncabezados());

        return mapa;
    }


    private void actualizaEstadoDeError(Map<String, Object> mapa, Integer idDescarga) {

        try {
            String mensajeTxt = "";
            if (mapa.get("resultado") != null && (int) mapa.get("resultado") != 1) {
                mensajeTxt += mapa.get("fallos");
            }
            descargaDAO.updateError(idDescarga, mensajeTxt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Map<String, Object> generaMensajeDeError(String cause) {
        Map<String, Object> failure = new HashMap<>();
        failure.put("DataStage", cause);
        return failure;
    }

    private void limpiaArchivosDataStage(Integer idCliente, String rfc, String encabezado) {
        // Eliminación de carpeta descomprimida de datastage
        try {
            Util.eliminaRecursoEnRutaDestino(Util.rutaTempDatastages
                    + idCliente + "/" + rfc + "/" + "Archivos");
            //Eliminación de zip de datastage
            Util.eliminaRecursoEnRutaDestino(Util.rutaTempDatastages
                    + idCliente + "/" + rfc + "/" + encabezado + ".zip");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String findStacktraceOfGUID(String GUID) throws GlosaNetException {
        try {

            return jobDAO.findJobStacktraceByGUID(GUID);

        } catch (Exception e) {
            e.printStackTrace();
            throw new GlosaNetException(e);
        }
    }

    @Override
    public Integer getIdDatastage(Integer idCliente, String folio) throws GlosaNetException {
        return jobDAO.getIdDatastage(idCliente, folio);
    }
}
