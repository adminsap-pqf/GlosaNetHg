package org.mx.servicio.comun;

import org.mx.modelo.comun.bo.ClientesBO;

public interface ClienteService {

	public boolean actualizarCliente(int usuarioCreador, ClientesBO cliente) throws Exception;
}
