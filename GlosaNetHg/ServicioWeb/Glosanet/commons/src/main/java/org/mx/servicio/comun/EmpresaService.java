package org.mx.servicio.comun;

import java.io.ByteArrayOutputStream;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.EmpresaBO;

public interface EmpresaService {

	public boolean actualizarVigencia(EmpresaBO empresa) throws Exception;
	
	public Boolean guardarMatrizSeguridad(String rcf, String idcliente, byte[] matriz)
			throws GlosaNetException;
	
	public ByteArrayOutputStream descargarMatrizSeguridad(String rfc, String idCliente)
			throws GlosaNetException;
	
	public Boolean guardarImagenEmpresa(String idEmpresa, String idCliente, byte[] matriz)
			throws GlosaNetException;
}
