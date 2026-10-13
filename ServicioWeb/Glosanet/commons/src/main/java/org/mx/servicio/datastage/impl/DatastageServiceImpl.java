package org.mx.servicio.datastage.impl;

import org.mx.controllers.datastage.CustomLoadRequestOptions;
import org.mx.controllers.datastage.DatastageRequestOptions;
import org.mx.controllers.datastage.VUCEMSolicitudRequestOptions;
import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.datastage.bo.*;
import org.mx.persistencia.Page;
import org.mx.persistencia.datastage.DatastageDAO;
import org.mx.servicio.datastage.DatastageService;
import org.mx.servicio.jobs.datastage.vucem.messaging.DocRequestMsgBrokerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ryndem.mx.glosanet.messagebroker.VucemDocuments;

import java.util.List;
import java.util.logging.Logger;

@Service("datastageService")
public class DatastageServiceImpl implements DatastageService {

    private final static Logger LOGGER = Logger.getLogger(DatastageServiceImpl.class.getName());

    @Autowired
    DatastageDAO dataStageDAO;

    @Autowired
    private DocRequestMsgBrokerService docRequestMsgBrokerService;

    @Override
    public Page<DatosGeneralesCustomBO> listPaginatedDatosGenerales(DatastageRequestOptions requestOptions)
            throws GlosaNetException {
        LOGGER.info("@listPaginatedDatosGenerales: Inicio");
        try {
            Page<DatosGeneralesCustomBO> page;
            if (requestOptions.getIdCompany() == 0) {
                page = dataStageDAO.getDatosGenerales(requestOptions);
            } else {
                page = dataStageDAO.getDatosGeneralesPorCliente(requestOptions);
            }
            return page;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.severe("@listPaginatedDatosGenerales: exception -> " + e.toString());
            throw new GlosaNetException(e);
        }
    }

    @Override
    public Page<FacturaCustomBO> listPaginatedFacturas(DatastageRequestOptions requestOptions)
            throws GlosaNetException {
        LOGGER.info("@listPaginatedFacturas: Inicio");
        try {
            Page<FacturaCustomBO> page;
            if (requestOptions.getIdCompany() == 0) {
                page = dataStageDAO.getFacturas(requestOptions);
            } else {
                page = dataStageDAO.getFacturasPorCliente(requestOptions);
            }
            return page;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.severe("@listPaginatedFacturas: exception -> " + e.toString());
            throw new GlosaNetException(e);
        }
    }

    @Override
    public Page<CasosPedimentoCustomBO> listPaginatedCasosPedimentos(DatastageRequestOptions requestOptions)
            throws GlosaNetException {
        LOGGER.info("@listPaginatedCasosPedimentos: Inicio");
        try {
            Page<CasosPedimentoCustomBO> page;
            if (requestOptions.getIdCompany() == 0) {
                page = dataStageDAO.getCasosPedimentos(requestOptions);
            } else {
                page = dataStageDAO.getCasosPedimentosPorCliente(requestOptions);
            }
            return page;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.severe("@listPaginatedCasosPedimentos: exception -> " + e.toString());
            throw new GlosaNetException(e);
        }
    }

    @Override
    public Page<PartidaCustomBO> listPaginatedPartidas(DatastageRequestOptions requestOptions)
            throws GlosaNetException {
        LOGGER.info("@listPaginatedPartidas: Inicio");
        try {
            Page<PartidaCustomBO> page = dataStageDAO.getPartidas(requestOptions);
            return page;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.severe("@listPaginatedPartidas: exception -> " + e.toString());
            throw new GlosaNetException(e);
        }
    }

    @Override
    public Page<FacturaCoveBO> listPaginatedFacturasCovesDeDatastage(VUCEMSolicitudRequestOptions requestOptions)
            throws GlosaNetException {
        LOGGER.info("@listPaginatedFacturasCovesDeDatastage: Inicio");
        try {
            return dataStageDAO.getFacturasCOVEsDeDatastage(requestOptions);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.severe("@listPaginatedFacturasCovesDeDatastage: Exception -> " + e.toString());
            throw new GlosaNetException(e);
        }
    }

    @Override
    public Integer getTotalFacturasDocsDeDatastage(Integer idDatastage) throws GlosaNetException {
        LOGGER.info("@getTotalFacturasDocsDeDatastage: Inicio");
        try {
            return dataStageDAO.getTotalFacturasCOVEsDeDatastage(idDatastage);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.severe("@getTotalFacturasDocsDeDatastage: exception -> " + e.toString());
            throw new GlosaNetException(e);
        }
    }

    @Override
    public Page<CasosPedimentoCustomBO> listPaginatedIDCasosDeDatastage(VUCEMSolicitudRequestOptions requestOptions)
            throws GlosaNetException {
        LOGGER.info("@listPaginatedIDCasosDeDatastage: Inicio");
        try {
            return dataStageDAO.getCasosPedimentosPorDatastage(requestOptions);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.severe("@listPaginatedIDCasosDeDatastage: exception -> " + e.toString());
            throw new GlosaNetException(e);
        }
    }

    @Override
    public Integer getTotalCasosPedimentosDeDatastage(Integer idDatastage) throws GlosaNetException {
        LOGGER.info("@getCasosPedimentosDeDatastage: Inicio");
        try {
            return dataStageDAO.getTotalCasosPedimentosDeDatastage(idDatastage);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.severe("@getCasosPedimentosDeDatastage: exception -> " + e.toString());
            throw new GlosaNetException(e);
        }
    }

    @Override
    public boolean sendCustomMessage(CustomLoadRequestOptions options) throws GlosaNetException {
        LOGGER.info("@sendCustomMessage: Inicio");

        try {
            if (options.getDocumentType().equals(VucemDocuments.AcuseCove)) {
                if (options.getIdSolicitud() == null && options.getIdFactura() != null) {
                    options.setFacturaPO(dataStageDAO.getFactura(options.getIdFactura()));
                    options.setDocumented(options.getFacturaBO() != null);
                }
            } else {
                if (options.getIdSolicitud() == null && options.getIdCasoPedimento() != null) {
                    options.setCasosPedimentoBO(dataStageDAO.getCasoPedimento(options.getIdCasoPedimento()));
                    options.setDocumented(options.getCasosPedimentoBO() != null);
                }
            }

            return docRequestMsgBrokerService.sendCustomMessage(options);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.severe("@sendCustomMessage: exception -> " + e.toString());
            throw new GlosaNetException(e);
        }
    }

    @Override
    public List<StatusAcuseCoveBO> getstatusAcuseCove(int[] ids)
            throws GlosaNetException {
        try {
            return dataStageDAO.getstatusAcuseCove(ids);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.severe("Error al obtener la lista de estatus de Acuses Cove " + e.toString());
            throw new GlosaNetException(e);
        }
    }

    @Override
    public List<StatusAcuseEdocBO> getStatusAcuseEdoc(int[] ids)
            throws GlosaNetException {
        try {
            return dataStageDAO.getStatusAcuseEdoc(ids);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.severe("Error al obtener la lista de estatus de Acuses Edoc " + e.toString());
            throw new GlosaNetException(e);
        }
    }
}
