package org.mx.controllers.vucem_token;


import org.mx.modelo.comun.bo.Parametros;
import org.mx.modelo.datastage.bo.VucemTokenBO;
import org.mx.servicio.datastage.VucemTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.logging.Logger;

@CrossOrigin
@Controller
public class VucemTokenController {
    private final static Logger log = Logger.getLogger(VucemTokenController.class.getName());
    private final static String URL = "/vucem_token";

    @Autowired
    private VucemTokenService vucemTokenService;

    @RequestMapping(
            value = URL + "/addToken",
            method = RequestMethod.POST,
            produces = "application/json"
    )
    @ResponseBody
    public ResponseEntity addToken(
            @RequestBody Parametros parameters
    ) throws Exception {
        ResponseEntity responseEntity;
        try {
            VucemTokenBO result = vucemTokenService.guardaVucemToken(parameters.getToken());
            responseEntity = new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @RequestMapping(
            value = URL + "/getToken",
            method = RequestMethod.POST,
            produces = "application/json"
    )
    @ResponseBody
    public ResponseEntity getToken(
            @RequestBody Parametros parameters
    ) throws Exception {
        ResponseEntity responseEntity;
        try {
            VucemTokenBO result = vucemTokenService.obtenerVucemTokenPorIdCliente(parameters.getIdCliente());
            responseEntity = new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @RequestMapping(
            value = URL + "/updateToken",
            method = RequestMethod.POST,
            produces = "application/json"
    )
    @ResponseBody
    public ResponseEntity updateToken(
            @RequestBody Parametros parameters
    ) throws Exception {
        ResponseEntity responseEntity;
        try {
            VucemTokenBO result = vucemTokenService.actualizarVucemToken(parameters.getToken());
            responseEntity = new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @RequestMapping(
            value = URL + "/deleteToken",
            method = RequestMethod.POST,
            produces = "application/json"
    )
    @ResponseBody
    public ResponseEntity deleteToken(
            @RequestBody Parametros parameters
    ) throws Exception {
        ResponseEntity responseEntity;
        try {
            boolean result = vucemTokenService.eliminarVucemToken(parameters.getIdToken());
            responseEntity = new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity(e,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
