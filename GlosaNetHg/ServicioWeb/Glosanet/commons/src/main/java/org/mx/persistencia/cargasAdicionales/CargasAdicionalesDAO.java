package org.mx.persistencia.cargasAdicionales;

import org.mx.modelo.cargas_adicionales.bo.CargasAdicionalesBO;
import org.mx.modelo.datastage.bo.PartidaBO;

import java.io.File;
import java.util.List;

public interface CargasAdicionalesDAO {

public List<CargasAdicionalesBO> obtenerDocumentosPorPedimento(int idPedimento);



public boolean loadDataLocal(File file);


public CargasAdicionalesBO saveDocumento(CargasAdicionalesBO cargasAdicionalesBO);

public boolean borrarDocumento(int idDocumento);
}
