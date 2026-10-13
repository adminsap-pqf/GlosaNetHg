package org.mx.servicio.comun;

import org.jose4j.lang.JoseException;
import org.mx.modelo.comun.bo.UsuarioBO;

public interface LlaveTokenService {
	
	public String generarToken(UsuarioBO usuario) throws JoseException;
	public UsuarioBO validacionToken(String token) throws JoseException;

}
