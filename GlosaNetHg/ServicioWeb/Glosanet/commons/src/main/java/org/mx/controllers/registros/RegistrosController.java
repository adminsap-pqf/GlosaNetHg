package org.mx.controllers.registros;

import org.jose4j.lang.JoseException;
import org.mx.modelo.comun.bo.Parametros;
import org.mx.modelo.comun.bo.ProductoBO;
import org.mx.modelo.comun.bo.Responsables;
import org.mx.modelo.comun.bo.UsuarioBO;
import org.mx.modelo.registros.bo.HistorialProductoBO;
import org.mx.modelo.registros.bo.RegistroBO;
import org.mx.persistencia.Page;
import org.mx.servicio.comun.AccessService;
import org.mx.servicio.comun.LlaveTokenService;
import org.mx.servicio.comun.RegistrosService;
import org.mx.servicio.registros.RegistroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.logging.Logger;

@CrossOrigin
@Controller
public class RegistrosController {
    private final static Logger LOGGER = Logger.getLogger(RegistrosController.class.getName());
    private final static String URL = "/registros";

    @Autowired
    LlaveTokenService llaveService;

    @Autowired
    RegistroService registroService;
    @Autowired
   RegistrosService registrosService;

    @Autowired
    private AccessService accessService;

    @RequestMapping(
            value = URL + "/obtenerRegistrosEmpresa",
            method = RequestMethod.GET,
            produces = "application/json"
    )
    @ResponseBody
    public ResponseEntity listRecords(
            @RequestHeader("x-auth-token") String token,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size,
            @RequestParam(defaultValue = "0") int idCompany,
            @RequestParam(defaultValue = "") String tab,
            @RequestParam(defaultValue = "") String manager,
            @RequestParam(defaultValue = "") String managerEvent,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "false") boolean withFolio,
            @RequestParam(defaultValue = "false") boolean withoutFolio,
            @RequestParam(defaultValue = "") String statusCofepris,
            @RequestParam(defaultValue = "") String filterEventName,
            @RequestParam(defaultValue = "") String filterEventDate) throws Exception {
        LOGGER.info("method: \"listRecords\"");
        int idUsuario = 0;
        try {
            UsuarioBO u = llaveService.validacionToken(token);
            if (u != null) {
                idUsuario = u.getIdUsuario();
            }
            if (idUsuario > 0) {
                RegistrosRequestOptions registrosRequestOptions = new RegistrosRequestOptions
                        .RegistrosRequestOptionsBuilder(idCompany, page, size)
                        .setTab(tab)
                        .setManager(manager)
                        .setManagerEvent(managerEvent)
                        .setKeyword(keyword)
                        .setWithFolio(withFolio)
                        .setWithoutFolio(withoutFolio)
                        .setStatusCofepris(statusCofepris)
                        .setFilterEventName(filterEventName)
                        .setFilterEventDate(filterEventDate)
                        .build();

                Page<RegistroBO> results = registroService.listPaginatedRecords(registrosRequestOptions);
                return new ResponseEntity<Page>(results, HttpStatus.OK);
            }
        } catch (JoseException e) {
            e.printStackTrace();
        }
        return new ResponseEntity("Access Denied for this functionality !!!", HttpStatus.FORBIDDEN);
    }

    @PostMapping(URL + "/getManagers")
    public ResponseEntity getManagers(@RequestHeader("x-auth-token") String token, @RequestBody Parametros parameters) throws Exception {
        LOGGER.info("method: \"getManagers\"");

        int idUsuario = 0;
        try {
            UsuarioBO u = llaveService.validacionToken(token);
            if (u != null) {
                idUsuario = u.getIdUsuario();
            }
            if (idUsuario > 0) {
                int idEmpresa = parameters.getId();
                Responsables results = registroService.getManager(idEmpresa);
                return new ResponseEntity<Responsables>(results, HttpStatus.OK);
            }
        } catch (JoseException e) {
            e.printStackTrace();
        }
        return new ResponseEntity("Access Denied for this functionality !!!", HttpStatus.FORBIDDEN);
    }

    @RequestMapping(
            value = URL + "/guardarHistorialProducto",
            method = RequestMethod.POST,
            produces = "application/json"
    )
    @ResponseBody
    public ResponseEntity guardarHistorialProducto(
            @RequestHeader("x-auth-token") String token,
            @RequestBody Parametros parameters
            ) throws Exception {
        LOGGER.info("@guardarHistorialProducto: Inicio");

        ResponseEntity responseEntity;
        try {
            List<HistorialProductoBO> result = registroService.saveHistorialProducto(parameters.getHistorialProducto());
            responseEntity = new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @RequestMapping(
            value = URL + "/obtenerProductosDataStagePorEmpresa",
            method = RequestMethod.POST,
            produces = "application/json"
    )
    @ResponseBody
    public ResponseEntity obtenerProductosDataStagePorEmpresa(
            @RequestBody Parametros p
    ) throws Exception {
        LOGGER.info("@obtenerProductosDataStagePorEmpresa: Inicio");

        ResponseEntity responseEntity;
        try {
            Page<ProductoBO> result = registroService.obtenerProductosDataStagePorEmpresa(
                    p.getCondicion(),
                    p.getId(),
                    p.getPage(),
                    p.getSize(),
                    p.getOrden(),
                    p.getBusqueda(),
                    p.getCondicion());

            responseEntity = new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }


}
