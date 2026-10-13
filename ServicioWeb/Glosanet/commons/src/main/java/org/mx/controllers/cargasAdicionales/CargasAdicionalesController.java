package org.mx.controllers.cargasAdicionales;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.jose4j.lang.JoseException;
import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.cargas_adicionales.bo.CargasAdicionalesBO;
import org.mx.modelo.comun.bo.Parametros;
import org.mx.modelo.comun.bo.StatusMessage;
import org.mx.modelo.comun.bo.UsuarioBO;
import org.mx.modelo.util.Util;
import org.mx.servicio.cargasAdicionales.CargasAdicionalesService;
import org.mx.servicio.comun.LlaveTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@CrossOrigin
@RestController
public class CargasAdicionalesController {

    private final static String URL="/CargasAdicionales";

    @Autowired
    private CargasAdicionalesService cargasAdicionalesService;
    @Autowired
    LlaveTokenService llaveService;

    @RequestMapping(
            value=  URL+"/Archivos",
            method= RequestMethod.GET,
            produces = "application/json"
    )
    @ResponseBody @PermitAll
    public ResponseEntity findFilesByPartida(
            @RequestHeader("x-auth-token") String token,
            @RequestParam int idPartida
            /*@RequestParam int size,
            @RequestParam int page*/
    ){

        int idUsuario = 0;
        try {
            UsuarioBO u = llaveService.validacionToken(token);
            if (u != null) {
                idUsuario = u.getIdUsuario();
            }
            if (idUsuario > 0) {
                List<CargasAdicionalesBO> resultPage = cargasAdicionalesService.obtenerDocumentosPorPedimento(idPartida);
               return  new ResponseEntity(resultPage, HttpStatus.OK);
            }
        } catch (GlosaNetException | JoseException e) {
            return new ResponseEntity(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
   // return responseEntity;
        return new ResponseEntity("Access Denied for this functionality !!!", HttpStatus.FORBIDDEN);
    }

    @RequestMapping(
          value=   URL + "/subirArchivos",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE

    )
    @ResponseBody
    public ResponseEntity  guardarArchivosAdiconales(
            @RequestHeader("x-auth-token") String token,
            @RequestParam("file") MultipartFile file,
            @RequestParam String rfc,
            @RequestParam String descripcion,
            @RequestParam String folioDataStage,
            @RequestParam String idPedimento,
            @RequestParam String idUsuario,
            @RequestParam String idCliente


    ){

        ResponseEntity responseEntity=null;
        int Usuario = 0;

        try {
            UsuarioBO u = llaveService.validacionToken(token);
            if (u != null) {
                Usuario = u.getIdUsuario();
            }
            if (Usuario > 0) {
                System.out.println("Archivo:" + file.getOriginalFilename());
                if (cargasAdicionalesService.cargarArchivo(cargasAdicionalesService.convertMultiPartToFile(file), file.getOriginalFilename(), rfc, idPedimento, folioDataStage, idCliente)) {

                    CargasAdicionalesBO resultPage = cargasAdicionalesService.guardarRegistroCargaAdicional(cargasAdicionalesService.convertMultiPartToFile(file), descripcion, Integer.parseInt(idUsuario), Integer.parseInt(idPedimento));
                    System.out.println("Registra archivo en la BD");//cambiar por log
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
            value=   URL + "CargasAdicionales/eliminarArchivos",
            method = RequestMethod.POST,
            // consumes = MediaType.MULTIPART_FORM_DATA_VALUE
            produces = "application/json"
    )
    @ResponseBody @PermitAll
    public boolean eliminarArchivo(
            @RequestHeader("x-auth-token") String token,
            @RequestBody Parametros parametros

    )

        {
            int Usuario = 0;
            try {
                UsuarioBO u = llaveService.validacionToken(token);
                if (u != null) {
                    Usuario = u.getIdUsuario();
                }
                if (Usuario > 0) {
                cargasAdicionalesService.eliminarDocumento(Integer.parseInt(parametros.getIdDocumento()),String.valueOf(parametros.getIdCliente()),parametros.getRfc(),parametros.getFolioDataStage(),String.valueOf(parametros.getIdPedimento()), parametros.getNombreArchivo());
            }} catch (GlosaNetException e) {
                throw new RuntimeException(e);
            } catch (JoseException e) {
                throw new RuntimeException(e);
            }

            return true;
        }



    
}
