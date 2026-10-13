package org.mx.servicio.comun.impl;

import org.jose4j.lang.JoseException;
import org.mx.modelo.comun.bo.UsuarioBO;
import org.mx.servicio.comun.AccessService;
import org.mx.servicio.comun.LlaveTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.logging.Logger;

@Service("accessService")
public class AccessServiceImpl implements AccessService {

    private final static Logger LOGGER = Logger.getLogger(AccessServiceImpl.class.getName());

    private final static String ACCESO_API_PROHIBIDO = "Acceso al recurso prohibido";
    private final static String ACCESO_API_NOAUTORIZADO = "Recurso no autorizado";

    @Autowired
    private LlaveTokenService llaveService;

    @Override
    public Response validateAccessToAPI(String token) {
        LOGGER.info("@validaAccesoAPI: Inicio");

        if (null == token) {
            LOGGER.info("@validaAccesoAPI: Intento de acceso a API no autorizado");
            return Response.status(Status.UNAUTHORIZED.getStatusCode())
                    .entity(ACCESO_API_NOAUTORIZADO).build();
        }
        try {
            UsuarioBO usuario = llaveService.validacionToken(token);
            LOGGER.info("@validaAccesoAPI: Intento de acceso a API no autorizado");
            if (null == usuario || usuario.getIdUsuario() <= 0) {
                return Response.status(Status.FORBIDDEN.getStatusCode())
                        .entity(ACCESO_API_PROHIBIDO).build();
            }
        } catch (JoseException e) {
            LOGGER.info("@validaAccesoAPI: Error al validar el token " + e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
                    .entity(e).build();
        }

        return null;
    }
}
