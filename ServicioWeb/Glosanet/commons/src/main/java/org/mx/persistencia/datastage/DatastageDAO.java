package org.mx.persistencia.datastage;

import org.mx.controllers.datastage.DatastageRequestOptions;
import org.mx.controllers.datastage.VUCEMSolicitudRequestOptions;
import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.datastage.bo.*;
import org.mx.persistencia.Page;

import java.util.List;

public interface DatastageDAO {

    /***
     *
     * @param requestOptions
     * @return
     */
    public Page<DatosGeneralesCustomBO> getDatosGenerales(DatastageRequestOptions requestOptions) throws GlosaNetException;

    /***
     *
     * @param requestOptions
     * @return
     */
    public Page<FacturaCustomBO> getFacturas(DatastageRequestOptions requestOptions) throws GlosaNetException;

    /***
     *
     * @param requestOptions
     * @return
     */
    public Page<CasosPedimentoCustomBO> getCasosPedimentos(DatastageRequestOptions requestOptions) throws GlosaNetException;

    /***
     *
     * @param requestOptions
     * @return
     */
    public Page<PartidaCustomBO> getPartidas(DatastageRequestOptions requestOptions) throws GlosaNetException;

    /***
     *
     * @param requestOptions
     * @return
     */
    public Page<DatosGeneralesCustomBO> getDatosGeneralesPorCliente(DatastageRequestOptions requestOptions) throws GlosaNetException;

    /***
     *
     * @param requestOptions
     * @return
     */
    public Page<FacturaCustomBO> getFacturasPorCliente(DatastageRequestOptions requestOptions) throws GlosaNetException;

    /***
     *
     * @param requestOptions
     * @return
     */
    public Page<CasosPedimentoCustomBO> getCasosPedimentosPorCliente(DatastageRequestOptions requestOptions) throws GlosaNetException;

    /***
     *
     * @param requestOptions
     * @return
     */
    public Page<CasosPedimentoCustomBO> getCasosPedimentosPorDatastage(VUCEMSolicitudRequestOptions requestOptions) throws GlosaNetException;

    /**
     * @param datastage
     * @return Gets the total count of a Datastage Request Cases.
     * @throws GlosaNetException
     */
    public Integer getTotalCasosPedimentosDeDatastage(Integer datastage) throws GlosaNetException;

    /***
     *
     * @param requestOptions
     * @return
     */
    public Page<FacturaCoveBO> getFacturasCOVEsDeDatastage(VUCEMSolicitudRequestOptions requestOptions)
            throws GlosaNetException;

    /**
     * @param datastage
     * @return
     * @throws GlosaNetException
     */
    public Integer getTotalFacturasCOVEsDeDatastage(Integer datastage) throws GlosaNetException;

    public FacturaCoveBO getFactura(Integer idFactura) throws GlosaNetException;

    public CasosPedimentoCustomBO getCasoPedimento (Integer idCasoPedimento) throws GlosaNetException;

    public boolean existsFactura(Integer idFactura) throws GlosaNetException;

    public boolean existsCasoPedimento(Integer idCasoPedimento) throws GlosaNetException;

    List<StatusAcuseCoveBO> getstatusAcuseCove(int[] ids) throws GlosaNetException;

    List<StatusAcuseEdocBO> getStatusAcuseEdoc(int[] ids) throws GlosaNetException;
}
