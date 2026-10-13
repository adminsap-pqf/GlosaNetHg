package org.mx.controllers.catalogo;

import org.mx.servicio.catalogo.CatalogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@CrossOrigin
@Controller
public class CatalogoController {
    private final static Logger log = Logger.getLogger(CatalogoController.class.getName());
    private final static String URL = "/catalogo";

    @Autowired
    private CatalogoService srv;

    @RequestMapping(
            value = URL + "/getcatalogs",
            method = RequestMethod.GET,
            produces = "application/json"
    )
    @ResponseBody
    public ResponseEntity getCatalogs(
            @RequestParam(defaultValue = "") int[] ids) {
        ResponseEntity responseEntity;
        try {
            responseEntity = new ResponseEntity<>(srv.listarCatalogos(ids), HttpStatus.OK);
        } catch (Exception e) {
            log.severe("Error al obtener el catálogo. " +e.getMessage() );
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
