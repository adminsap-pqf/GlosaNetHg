package org.mx.controllers.reportes;

import org.mx.controllers.datastage.DatastageRequestOptions;
import org.mx.modelo.comun.bo.Parametros;
import org.mx.modelo.datastage.bo.DatosGeneralesCustomBO;
import org.mx.modelo.reportes.bo.DetallePartidasBO;
import org.mx.persistencia.Page;
import org.mx.servicio.comun.AccessService;
import org.mx.servicio.comun.LlaveTokenService;
import org.mx.servicio.reporte.PredefinidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@CrossOrigin
@Controller
public class ReportesController {
    private final static Logger log = Logger.getLogger(ReportesController.class.getName());
    private final static String URL = "/reportes";

    @Autowired
    private PredefinidoService repPredefinido;

    @RequestMapping(
            value = URL + "/reportePartidasDetallado",
            method = RequestMethod.POST,
            produces = "application/json"
    )
    @ResponseBody
    public ResponseEntity reportePartidasDetallado(
            @RequestBody Parametros parametros
    ) {
        ResponseEntity responseEntity;

        try {
            Page<DetallePartidasBO> resultPage = repPredefinido.reporteDetallePartidas(parametros);
            responseEntity = new ResponseEntity<>(resultPage, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }
}
