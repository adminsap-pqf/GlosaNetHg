package org.mx.persistencia.comun;

import org.mx.modelo.comun.bo.ClientesBO;

public interface ClienteDAO {
	
	public boolean updateCliente(ClientesBO cliente) throws Exception;

}
