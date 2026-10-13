package org.mx.persistencia.resumen;

import java.util.List;

import org.mx.modelo.datastage.bo.ResumenBO;

public interface ResumenDAO {

	boolean guardar(List<ResumenBO> list, int idDatastage);

}
