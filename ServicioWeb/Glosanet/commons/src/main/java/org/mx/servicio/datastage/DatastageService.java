package org.mx.servicio.datastage;

import org.mx.controllers.datastage.CustomLoadRequestOptions;
import org.mx.controllers.datastage.DatastageRequestOptions;
import org.mx.controllers.datastage.VUCEMSolicitudRequestOptions;
import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.datastage.bo.*;
import org.mx.persistencia.Page;

import java.util.List;

public interface DatastageService {

    /**
     * Obtener página filtrada y ordenada de Pedimentos
     *
     * @param requestOptions
     * @return
     * @throws GlosaNetException
     */
    public Page<DatosGeneralesCustomBO> listPaginatedDatosGenerales(DatastageRequestOptions requestOptions) throws GlosaNetException;

    /**
     * Obtener página filtrada y ordenada de Facturas
     *
     * @param requestOptions
     * @return
     * @throws GlosaNetException
     */
    public Page<FacturaCustomBO> listPaginatedFacturas(DatastageRequestOptions requestOptions) throws GlosaNetException;

    /**
     * Obtener página filtrada y ordenada de CasosPedimentos
     *
     * @param requestOptions
     * @return
     * @throws GlosaNetException
     */
    public Page<CasosPedimentoCustomBO> listPaginatedCasosPedimentos(DatastageRequestOptions requestOptions) throws GlosaNetException;

    /**
     * Obtener página filtrada y ordenada de Partidas
     *
     * @param requestOptions
     * @return
     * @throws GlosaNetException
     */
    public Page<PartidaCustomBO> listPaginatedPartidas(DatastageRequestOptions requestOptions) throws GlosaNetException;

    /**
     * Obtener página filtrada y ordenada de Coves de facturas
     *
     * @param requestOptions
     * @return
     * @throws GlosaNetException
     */
    public Page<FacturaCoveBO> listPaginatedFacturasCovesDeDatastage(VUCEMSolicitudRequestOptions requestOptions)
            throws GlosaNetException;

    /**
     * @param idDatastage
     * @return
     * @throws GlosaNetException
     */
    public Integer getTotalFacturasDocsDeDatastage(Integer idDatastage) throws GlosaNetException;

    /**
     * Obtener página filtrada y ordenada de identificadores de casos
     *
     * @param requestOptions
     * @return
     * @throws GlosaNetException
     */
    public Page<CasosPedimentoCustomBO> listPaginatedIDCasosDeDatastage(VUCEMSolicitudRequestOptions requestOptions)
            throws GlosaNetException;

    /**
     * @param idDatastage
     * @return
     * @throws GlosaNetException
     */
    public Integer getTotalCasosPedimentosDeDatastage(Integer idDatastage) throws GlosaNetException;

    /**
     * @param options
     * @return
     * @throws GlosaNetException
     */
    public boolean sendCustomMessage(CustomLoadRequestOptions options) throws GlosaNetException;

    List<StatusAcuseCoveBO> getstatusAcuseCove(int[] ids) throws GlosaNetException;
    List<StatusAcuseEdocBO> getStatusAcuseEdoc(int[] ids) throws GlosaNetException;
}
