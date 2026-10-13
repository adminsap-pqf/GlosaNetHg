package org.mx.persistencia.datastage.builder;

import org.mx.modelo.datastage.bo.*;
import org.mx.modelo.datastage.po.*;

public class BuilderDatastage {

    public static DatosGeneralesCustomBO builderDatosGenerales(DatosGeneralesCustomPO po) {
        DatosGeneralesCustomBO bo = new DatosGeneralesCustomBO();

        bo.setIdDatosGenerales(po.getIdDatosGenerales());
        bo.setPatente(po.getPatente());
        bo.setPedimento(po.getPedimento());
        bo.setSeccionAduanera(po.getSeccionAduanera());
        bo.setTipoOperacion(po.getTipoOperacion());
        bo.setClaveDocumento(po.getClaveDocumento());
        bo.setSeccionAduaneraEntrada(po.getSeccionAduaneraEntrada());
        bo.setTipoPedimento(po.getTipoPedimento());
        bo.setFechaRecepcionPedimento(po.getFechaRecepcionPedimento());
        bo.setFechaPagoReal(po.getFechaPagoReal());
        bo.setObservaciones(po.getObservaciones());
        bo.setIdDatastage(po.getIdDatastage());
        bo.setRecordIndex(po.getRecordIndex());

        return bo;
    }

    public static CasosPedimentoCustomBO builderCasosPedimento(CasosPedimentoCustomPO po) {
        CasosPedimentoCustomBO bo = new CasosPedimentoCustomBO();

        bo.setIdCasosPedimento(po.getIdCasosPedimento());
        bo.setPatente(po.getPatente());
        bo.setPedimento(po.getPedimento());
        bo.setSeccionAduanera(po.getSeccionAduanera());
        bo.setClaveCaso(po.getClaveCaso());
        bo.setIdentificadorCaso(po.getIdentificadorCaso());
        bo.setRecordIndex(po.getRecordIndex());
        bo.setIdCliente(po.getIdCliente());
        bo.setStatusEdoc(po.getStatusEdoc());
        bo.setStatusAcuse(po.getStatusAcuse());
        bo.setErrorMessageEdoc(po.getErrorMessageEdoc());
        bo.setErrorMessageAcuse(po.getErrorMessageAcuse());
        bo.setIdSolicitudEdoc(po.getIdSolicitudEdoc());
        bo.setIdSolicitudAcuse(po.getIdSolicitudAcuse());
        bo.setPathEdoc(po.getPathEdoc());
        bo.setPathAcuse(po.getPathAcuse());

        return bo;
    }

    public static FacturaCustomBO builderFactura(FacturaCustomPO po) {
        FacturaCustomBO bo = new FacturaCustomBO();

        bo.setIdFactura(po.getIdFactura());
        bo.setPatente(po.getPatente());
        bo.setPedimento(po.getPedimento());
        bo.setSeccionAduanera(po.getSeccionAduanera());
        bo.setFechaFacturacion(po.getFechaFacturacion());
        bo.setNumeroFactura(po.getNumeroFactura());
        bo.setTerminoFacturacion(po.getTerminoFacturacion());
        bo.setProveedorMercancia(po.getProveedorMercancia());
        bo.setCalleProveedor(po.getCalleProveedor());
        bo.setStatus(po.getStatus());
        bo.setErrorMessage(po.getErrorMessage());
        bo.setIdSolicitudFactura(po.getIdSolicitudFactura());
        bo.setPath(po.getPath());
        bo.setRecordIndex(po.getRecordIndex());

        return bo;
    }

    public static PartidaCustomBO builderPartida(PartidaCustomPO po) {
        PartidaCustomBO bo = new PartidaCustomBO();

        bo.setIdPartida(po.getIdPartida());
        bo.setPatente(po.getPatente());
        bo.setPedimento(po.getPedimento());
        bo.setFraccion(po.getFraccion());
        bo.setSecuenciaFraccion(po.getSecuenciaFraccion());
        bo.setSubdivisionFraccion(po.getSubdivisionFraccion());
        bo.setSeccionAduanera(po.getSeccionAduanera());
        bo.setDescripcionMercancia(po.getDescripcionMercancia());
        bo.setValorDolares(po.getValorDolares());
        bo.setPaisOrigenDestino(po.getPaisOrigenDestino());
        bo.setPaisCompradorVendedor(po.getPaisCompradorVendedor());
        bo.setRecordIndex(po.getRecordIndex());

        return bo;
    }

    public static FacturaCoveBO builderFactura(FacturaCovePO po) {
        FacturaCoveBO bo = new FacturaCoveBO();

        bo.setIdFactura(po.getIdFactura());
        bo.setPedimento(po.getPedimento());
        bo.setNumeroFactura(po.getNumeroFactura());
        bo.setIdCliente(po.getIdCliente());

        return bo;
    }

}
