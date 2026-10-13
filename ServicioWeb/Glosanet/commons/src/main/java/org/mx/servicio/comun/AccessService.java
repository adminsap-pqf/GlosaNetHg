package org.mx.servicio.comun;

import javax.ws.rs.core.Response;

public interface AccessService {

    Response validateAccessToAPI(String token);
}
