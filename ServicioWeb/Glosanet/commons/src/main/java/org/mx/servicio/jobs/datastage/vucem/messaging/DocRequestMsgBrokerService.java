package org.mx.servicio.jobs.datastage.vucem.messaging;

import org.mx.controllers.datastage.CustomLoadRequestOptions;
import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.datastage.bo.CasosPedimentoCustomBO;
import org.mx.modelo.datastage.bo.CasosPedimentoIDCasoBO;
import org.mx.modelo.datastage.bo.FacturaCoveBO;
import org.mx.persistencia.Page;

import java.util.List;


public interface DocRequestMsgBrokerService {


    /**
     * @param list
     * @throws GlosaNetException
     */
    public boolean sendMessageToAcuseCoveQueue(List<Page<FacturaCoveBO>> list) throws GlosaNetException;

    /**
     * @param list
     * @throws GlosaNetException
     */
    public boolean sendMessageToEdocQueue(List<Page<CasosPedimentoCustomBO>> list) throws GlosaNetException;

    /**
     * @param list
     * @throws GlosaNetException
     */
    public boolean sendMessageToAcuseEdocQueue(List<Page<CasosPedimentoCustomBO>> list) throws GlosaNetException;

    public boolean sendCustomMessage(CustomLoadRequestOptions options) throws GlosaNetException;
}
