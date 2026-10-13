package org.mx.servicio.jobs.datastage.vucem.partitioning.impl;

import org.mx.controllers.datastage.VUCEMSolicitudRequestOptions;
import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.datastage.bo.CasosPedimentoCustomBO;
import org.mx.modelo.datastage.bo.FacturaCoveBO;
import org.mx.persistencia.Page;
import org.mx.servicio.datastage.DatastageService;
import org.mx.servicio.jobs.datastage.vucem.partitioning.DocRequestPartitioningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class DocRequestPartitioningServiceImpl implements DocRequestPartitioningService {

    private final static Logger LOGGER = Logger.getLogger(DocRequestPartitioningServiceImpl.class.getName());

    @Autowired
    DatastageService datastageService;

    @Override
    public List<Page<FacturaCoveBO>> creaParticionesFacturasCOVEsDePedimento(
            Integer idDatastage, Integer itemsPorPagina, Integer totalFacturasCOVEs)
            throws GlosaNetException {
        LOGGER.info("@creaParticionesFacturasCOVEsDePedimento: Inicio");

        //Validaciones
        if (null == idDatastage) return null;
        if (itemsPorPagina < 1) return null;

        //Obtenemos el total de Facturas Cove
        List<Page<FacturaCoveBO>> lista = new ArrayList<>();

        try {
            int paginas = new Double(Math.ceil(
                    Double.valueOf(totalFacturasCOVEs) / Double.valueOf(itemsPorPagina)))
                    .intValue();
            for (int i = 0; i < paginas; i++) {
                VUCEMSolicitudRequestOptions vucemSolicitudRequestOptions =
                        new VUCEMSolicitudRequestOptions.VUCEMSolicitudRequestOptionsBuilder(
                                idDatastage, i, itemsPorPagina).build();
                Page<FacturaCoveBO> pagina = datastageService.listPaginatedFacturasCovesDeDatastage(
                        vucemSolicitudRequestOptions);
                lista.add(pagina);
            }
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.severe("@creaParticionesFacturasCOVEsDePedimento: exception -> " + e.toString());
            throw new GlosaNetException(e);
        }
    }

    @Override
    public List<Page<CasosPedimentoCustomBO>> creaParticionesCasosPedimentos(Integer idDatastage, Integer itemsPerPage, Integer requestCasesCount)
            throws GlosaNetException {
        LOGGER.info("@creaParticionesCasosPedimentos: Inicio");

        //Validaciones
        if (null == idDatastage) throw new GlosaNetException("idDatastage cannot be null");
        if (itemsPerPage < 1) throw new GlosaNetException("items per page cannot be zero");

        //Obtenemos el total de Casos Pedimento
        List<Page<CasosPedimentoCustomBO>> lista = new ArrayList<>();

        try {
            int paginas = new Double(Math.ceil(
                    Double.valueOf(requestCasesCount) / Double.valueOf(itemsPerPage)
            )).intValue();

            for (int i = 0; i < paginas; i++) {
                VUCEMSolicitudRequestOptions vucemSolicitudRequestOptions =
                        new VUCEMSolicitudRequestOptions.VUCEMSolicitudRequestOptionsBuilder(
                                idDatastage, i, itemsPerPage).build();

                Page<CasosPedimentoCustomBO> pagina = datastageService.listPaginatedIDCasosDeDatastage(vucemSolicitudRequestOptions);
                lista.add(pagina);
            }

            return lista;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.severe("@creaParticionesFacturasCOVEsDePedimento: exception -> " + e.toString());
            throw new GlosaNetException(e);
        }
    }
}
