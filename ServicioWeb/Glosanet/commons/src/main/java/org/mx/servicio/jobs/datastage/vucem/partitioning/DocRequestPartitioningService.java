package org.mx.servicio.jobs.datastage.vucem.partitioning;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.datastage.bo.CasosPedimentoCustomBO;
import org.mx.modelo.datastage.bo.FacturaCoveBO;
import org.mx.persistencia.Page;

import java.util.List;

public interface DocRequestPartitioningService {
    List<Page<FacturaCoveBO>> creaParticionesFacturasCOVEsDePedimento(
            Integer idDatastage, Integer itemsPorPagina, Integer totalFacturasCOVEs) throws GlosaNetException;

    List<Page<CasosPedimentoCustomBO>> creaParticionesCasosPedimentos(
            Integer idDatastage, Integer itemsPorPagina, Integer totalCasosPedimentos) throws GlosaNetException;
}
