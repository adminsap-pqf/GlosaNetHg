package org.mx.servicio.jobs.datastage.loader.phase1.messaging;

import org.mx.modelo.Exception.GlosaNetException;

public interface DatastagePhase1MsgBrokerService {

    /**
     * @param idCliente
     * @param idUsuario
     * @param rfc
     * @param filePath
     * @param idDownload
     * @throws GlosaNetException
     */
    void sendMessage(Integer idUsuario, Integer idCliente, String rfc, String filePath, Integer idDownload) throws GlosaNetException;
}
