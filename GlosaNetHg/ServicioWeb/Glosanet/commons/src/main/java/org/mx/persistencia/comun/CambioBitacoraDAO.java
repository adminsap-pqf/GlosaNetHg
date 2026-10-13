package org.mx.persistencia.comun;

import java.util.List;

import org.mx.modelo.comun.bo.CambioBitacoraBO;

public interface CambioBitacoraDAO {

	public int addCambioBitacora(CambioBitacoraBO cambioBitacora) throws Exception;

	public List<CambioBitacoraBO> findCambiosBitacora();

}
