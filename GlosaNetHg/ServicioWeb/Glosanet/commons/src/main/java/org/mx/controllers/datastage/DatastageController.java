package org.mx.controllers.datastage;

import org.apache.commons.codec.binary.Base64;
import org.jose4j.lang.JoseException;
import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.cargas_adicionales.bo.CargasAdicionalesBO;
import org.mx.modelo.comun.bo.Parametros;
import org.mx.modelo.comun.bo.StatusMessage;
import org.mx.modelo.comun.bo.UsuarioBO;
import org.mx.modelo.datastage.bo.*;
import org.mx.modelo.util.Util;
import org.mx.persistencia.Page;
import org.mx.servicio.cargasAdicionales.CargasAdicionalesService;
import org.mx.servicio.comun.LlaveTokenService;
import org.mx.servicio.datastage.DatastageService;
import org.mx.servicio.jobs.datastage.vucem.messaging.DocRequestMsgBrokerService;
import org.mx.servicio.jobs.datastage.vucem.partitioning.DocRequestPartitioningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ryndem.mx.glosanet.messagebroker.VucemDocuments;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

@CrossOrigin
@Controller
public class DatastageController {
    private final static Logger log = Logger.getLogger(DatastageController.class.getName());
    private final static String URL = "/datastage";

    @Autowired
    private DatastageService datastageService;

    @Autowired
    private DocRequestPartitioningService docRequestPartitioningService;

    @Autowired
    private DocRequestMsgBrokerService docRequestMsgBrokerService;
    @Autowired
    private CargasAdicionalesService cargasAdicionalesService;
    @Autowired
    LlaveTokenService llaveService;

    @RequestMapping(
            value = URL + "/pedimentos",
            method = RequestMethod.GET,
            produces = "application/json"
    )
    @ResponseBody
    public ResponseEntity findPedimentos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size,
            @RequestParam(defaultValue = "0") int idCompany,
            @RequestParam(defaultValue = "0") int idDatastage,
            @RequestParam(defaultValue = "") String filterInitialDate,
            @RequestParam(defaultValue = "") String filterFinalDate,
            @RequestParam(defaultValue = "") String filterTipoOperacion,
            @RequestParam(defaultValue = "") String filterTipoPedimento,
            @RequestParam(defaultValue = "") String pedimento,
            @RequestParam(defaultValue = "") String searchTerm
    ) {
        // NOTA: Si el idDatastage es 0, quiere decir que las busquedas seran en generales.
        ResponseEntity responseEntity;
        try {
            DatastageRequestOptions datastageRequestOptions = new DatastageRequestOptions
                    .DatastageRequestOptionsBuilder(idCompany, idDatastage, page, size)
                    .setFilterInitialDate(filterInitialDate)
                    .setFilterFinalDate(filterFinalDate)
                    .setFilterTipoOperacion(filterTipoOperacion)
                    .setFilterTipoPedimento(filterTipoPedimento)
                    .setPedimento(pedimento)
                    .setSearchTerm(searchTerm)
                    .build();

            Page<DatosGeneralesCustomBO> resultPage = datastageService.listPaginatedDatosGenerales(datastageRequestOptions);
            responseEntity = new ResponseEntity<>(resultPage, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @RequestMapping(
            value = URL + "/facturas",
            method = RequestMethod.GET,
            produces = "application/json"
    )
    @ResponseBody
    public ResponseEntity findFacturas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size,
            @RequestParam(defaultValue = "0") int idCompany,
            @RequestParam(defaultValue = "0") int idDatastage,
            @RequestParam(defaultValue = "") String filterInitialDate,
            @RequestParam(defaultValue = "") String filterFinalDate,
            @RequestParam(defaultValue = "") String pedimento,
            @RequestParam(defaultValue = "") String searchTerm
    ) {
        ResponseEntity responseEntity;
        try {

            DatastageRequestOptions datastageRequestOptions = new DatastageRequestOptions
                    .DatastageRequestOptionsBuilder(idCompany, idDatastage, page, size)
                    .setFilterInitialDate(filterInitialDate)
                    .setFilterFinalDate(filterFinalDate)
                    .setPedimento(pedimento)
                    .setSearchTerm(searchTerm)
                    .build();

            Page<FacturaCustomBO> resultPage = datastageService.listPaginatedFacturas(datastageRequestOptions);
            responseEntity = new ResponseEntity<>(resultPage, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @RequestMapping(
            value = URL + "/request-factura",
            method = RequestMethod.POST,
            produces = "application/json"
    )
    @ResponseBody
    public ResponseEntity requestFacturas(
            @RequestBody CustomLoadRequestOptions solicitud
    ) {
        ResponseEntity responseEntity;
        try {
            solicitud.setDocumentType(VucemDocuments.AcuseCove);
            if (datastageService.sendCustomMessage(solicitud)) {
                responseEntity = new ResponseEntity<>(true, HttpStatus.OK);

            } else {
                responseEntity = new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity(e,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @RequestMapping(
            value = URL + "/request-datastage",
            method = RequestMethod.GET,
            produces = "application/json"
    )
    @ResponseBody
    @PermitAll
    public ResponseEntity requestDatastage(
            @RequestParam Integer idDatastage,
            @RequestParam Integer size
    ) {
        ResponseEntity responseEntity;
        try {
            // Acuse Cove
            Integer totalFacturasCOVEs = datastageService.getTotalFacturasDocsDeDatastage(idDatastage);
            List<Page<FacturaCoveBO>> listaAcuseCove = docRequestPartitioningService.creaParticionesFacturasCOVEsDePedimento(
                    idDatastage, size, totalFacturasCOVEs);

            // Edoc y Acuse Edoc
            Integer totalCasosPedimentos = datastageService.getTotalCasosPedimentosDeDatastage(idDatastage);
            List<Page<CasosPedimentoCustomBO>> listaCasosPedimento = docRequestPartitioningService.creaParticionesCasosPedimentos(
                    idDatastage, size, totalCasosPedimentos);

            boolean messageSent =
                    docRequestMsgBrokerService.sendMessageToAcuseCoveQueue(listaAcuseCove) &&
                            docRequestMsgBrokerService.sendMessageToAcuseEdocQueue(listaCasosPedimento) &&
                            docRequestMsgBrokerService.sendMessageToEdocQueue(listaCasosPedimento);

            if (messageSent)
                responseEntity = new ResponseEntity<>("ok", HttpStatus.OK);
            else
                responseEntity = new ResponseEntity<>("bad_request", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity(e,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @RequestMapping(
            value = URL + "/request-edoc",
            method = RequestMethod.POST,
            produces = "application/json"
    )
    @ResponseBody
    public ResponseEntity requestEdocs(
            @RequestBody CustomLoadRequestOptions solicitud
    ) {
        ResponseEntity responseEntity;
        try {
            solicitud.setDocumentType(VucemDocuments.Edoc);
            if (datastageService.sendCustomMessage(solicitud)) {
                responseEntity = new ResponseEntity<>(true, HttpStatus.OK);
            } else {
                responseEntity = new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity(e,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @RequestMapping(
            value = URL + "/request-acuseedoc",
            method = RequestMethod.POST,
            produces = "application/json"
    )
    @ResponseBody
    public ResponseEntity requestAcuseEdoc(

            @RequestBody CustomLoadRequestOptions solicitud
    ) {
        ResponseEntity responseEntity;
        try {
            solicitud.setDocumentType(VucemDocuments.AcuseEdoc);
            if (datastageService.sendCustomMessage(solicitud)) {
                responseEntity = new ResponseEntity<>(true, HttpStatus.OK);
            } else {
                responseEntity = new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity(e,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @RequestMapping(
            value = URL + "/eDoc",
            method = RequestMethod.GET,
            produces = "application/json"
    )
    @ResponseBody
    public ResponseEntity findEDocs(

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size,
            @RequestParam(defaultValue = "0") int idCompany,
            @RequestParam(defaultValue = "0") int idDatastage,
            @RequestParam(defaultValue = "") String filterInitialDate,
            @RequestParam(defaultValue = "") String filterFinalDate,
            @RequestParam(defaultValue = "") String pedimento,
            @RequestParam(defaultValue = "") String searchTerm
    ) {
        ResponseEntity responseEntity;
        try {

            DatastageRequestOptions datastageRequestOptions = new DatastageRequestOptions
                    .DatastageRequestOptionsBuilder(idCompany, idDatastage, page, size)
                    .setFilterInitialDate(filterInitialDate)
                    .setFilterFinalDate(filterFinalDate)
                    .setPedimento(pedimento)
                    .setSearchTerm(searchTerm)
                    .build();

            Page<CasosPedimentoCustomBO> resultPage = datastageService.listPaginatedCasosPedimentos(datastageRequestOptions);
            responseEntity = new ResponseEntity<>(resultPage, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity(e,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @RequestMapping(
            value = URL + "/partidas",
            method = RequestMethod.GET,
            produces = "application/json"
    )
    @ResponseBody
    public ResponseEntity findPartidas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size,
            @RequestParam(defaultValue = "0") int idCompany,
            @RequestParam(defaultValue = "0") int idDatastage,
            @RequestParam(defaultValue = "") String pedimento,
            @RequestParam(defaultValue = "") String searchTerm
    ) {
        ResponseEntity responseEntity;
        try {
            DatastageRequestOptions datastageRequestOptions = new DatastageRequestOptions
                    .DatastageRequestOptionsBuilder(idCompany, idDatastage, page, size)
                    .setPedimento(pedimento)
                    .setSearchTerm(searchTerm)
                    .build();

            Page<PartidaCustomBO> resultPage = datastageService.listPaginatedPartidas(datastageRequestOptions);
            responseEntity = new ResponseEntity<>(resultPage, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity(e,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @RequestMapping(
            value = URL + "/acuseCove/getStatus",
            method = RequestMethod.GET,
            produces = "application/json"
    )
    @ResponseBody
    public ResponseEntity getStatusAcuseCove(
            @RequestParam(defaultValue = "0") int[] ids
    ) {
        ResponseEntity responseEntity;
        try {
            List<StatusAcuseCoveBO> result = datastageService.getstatusAcuseCove(ids);
            responseEntity = new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity(e,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @RequestMapping(
            value = URL + "/acuseEdoc/getStatus",
            method = RequestMethod.GET,
            produces = "application/json"
    )
    @ResponseBody
    public ResponseEntity getStatusAcuseEdoc(
            @RequestParam(defaultValue = "0") int[] ids
    ) {
        ResponseEntity responseEntity;
        try {
            List<StatusAcuseEdocBO> result = datastageService.getStatusAcuseEdoc(ids);
            responseEntity = new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity(e,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @RequestMapping(
            value = URL + "/CargasAdicionales/Archivos",
            method = RequestMethod.GET,
            produces = "application/json"
    )
    @ResponseBody
    @PermitAll
    public ResponseEntity findFilesByPartida(
            @RequestHeader("x-auth-token") String token,
            @RequestParam int idPartida
    ) {

        int idUsuario = 0;
        try {
            UsuarioBO u = llaveService.validacionToken(token);
            if (u != null) {
                idUsuario = u.getIdUsuario();
            }
            if (idUsuario > 0) {
                List<CargasAdicionalesBO> resultPage = cargasAdicionalesService.obtenerDocumentosPorPedimento(idPartida);
                return new ResponseEntity(resultPage, HttpStatus.OK);
            }
        } catch (GlosaNetException | JoseException e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // return responseEntity;
        return new ResponseEntity("Access Denied for this functionality !!!", HttpStatus.FORBIDDEN);
    }

    @RequestMapping(
            value = URL + "/CargasAdicionales/subirArchivos",
            method = RequestMethod.POST
            // consumes = MediaType.MULTIPART_FORM_DATA_VALUE
            //produces = "application/json"
    )
    @ResponseBody
    @PermitAll
    public ResponseEntity guardarArchivosAdiconales(
            @RequestHeader("x-auth-token") String token,
            @RequestParam("file") MultipartFile file,
            @RequestParam String rfc,
            @RequestParam String idCliente,
            @RequestParam String idPedimento,
            @RequestParam String folioDataStage,
            @RequestParam String idUsuario,
            @RequestParam String descripcion


    ) {


        ResponseEntity responseEntity = null;
        int Usuario = 0;

        try {
            UsuarioBO u = llaveService.validacionToken(token);
            if (u != null) {
                Usuario = u.getIdUsuario();
            }
            if (Usuario > 0) {

                if (cargasAdicionalesService.cargarArchivo(cargasAdicionalesService.convertMultiPartToFile(file), file.getOriginalFilename(), rfc, idPedimento, folioDataStage, idCliente)) {

                    CargasAdicionalesBO resultPage = cargasAdicionalesService.guardarRegistroCargaAdicional(cargasAdicionalesService.convertMultiPartToFile(file), descripcion, Integer.parseInt(idUsuario), Integer.parseInt(idPedimento));

                    responseEntity = new ResponseEntity<>(resultPage, HttpStatus.OK);

                }
            }
        } catch (GlosaNetException e) {

            return new ResponseEntity(e,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (JoseException e) {
            throw new RuntimeException(e);
        }

        return responseEntity;
    }

    @RequestMapping(
            value = URL + "/CargasAdicionales/eliminarArchivos",
            method = RequestMethod.POST,
            // consumes = MediaType.MULTIPART_FORM_DATA_VALUE
            produces = "application/json"
    )
    @ResponseBody
    @PermitAll
    public boolean eliminarArchivo(
            @RequestHeader("x-auth-token") String token,
            @RequestBody Parametros parametros

    ) {
        int Usuario = 0;
        try {
            UsuarioBO u = llaveService.validacionToken(token);
            if (u != null) {
                Usuario = u.getIdUsuario();
            }
            if (Usuario > 0) {
                cargasAdicionalesService.eliminarDocumento(Integer.parseInt(parametros.getIdDocumento()), String.valueOf(parametros.getIdCliente()), parametros.getRfc(), parametros.getFolioDataStage(), String.valueOf(parametros.getIdPedimento()), parametros.getNombreArchivo());
            }
        } catch (GlosaNetException e) {
            throw new RuntimeException(e);
        } catch (JoseException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

/*
    @RequestMapping(
            value = URL + "/CargasAdicionales/request-adicional",
            method = RequestMethod.POST,
            //produces = "application/json"
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    @ResponseBody
    @PermitAll
    public InputStreamResource requestadicional(
            @RequestHeader("x-auth-token") String token,
            @RequestBody Parametros parametros,
            HttpServletResponse response
    ) throws JoseException {
        int Usuario = 0;
        UsuarioBO u = llaveService.validacionToken(token);
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=" + parametros.getNombreArchivo());

        InputStreamResource resource = null;
        try {

            if (u != null) {
                Usuario = u.getIdUsuario();
            }
            if (Usuario > 0) {
                //resource = new InputStreamResource(new FileInputStream(Util.rutaDatastages+parametros.getIdCliente()+"/"+parametros.getRfc()+"/CargasAdicionales/"+parametros.getFolioDataStage()+"/"+parametros.getIdPedimento()+"/"+parametros.getNombreArchivo()));
                resource = new InputStreamResource(new FileInputStream("C:\\Users\\Fernando Betanzos\\Downloads\\" + parametros.getIdCliente() + "\\" + parametros.getRfc() + "\\CargasAdicionales\\" + parametros.getFolioDataStage() + "\\" + parametros.getIdPedimento() + "\\" + parametros.getNombreArchivo()));


                return resource;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
*/



}