package org.mx.servicio.datastage.impl;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.datastage.bo.VucemTokenBO;
import org.mx.persistencia.datastage.VucemTokenDAO;
import org.mx.servicio.datastage.VucemTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.logging.Logger;

@Service("vucemTokenService")
public class VucemTokenServiceImpl implements VucemTokenService {

    private final static Logger LOGGER = Logger.getLogger(VucemTokenServiceImpl.class.getName());

    @Autowired
    VucemTokenDAO vucemTokenDAO;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public VucemTokenBO guardaVucemToken(VucemTokenBO vucemTokenBO) throws GlosaNetException {
        LOGGER.info("@guardaVucemToken: Inicio");

        try {
            return vucemTokenDAO.saveNuevoVucemToken(vucemTokenBO);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.severe("@guardaVucemToken: exception -> " + e.toString());
            TransactionAspectSupport.currentTransactionStatus()
                    .setRollbackOnly();
            throw new GlosaNetException();
        }
    }

    @Override
    public VucemTokenBO obtenerVucemTokenPorIdCliente(Long idCliente) throws GlosaNetException {

        LOGGER.info("@obtenerVucemTokenPorIdCliente: Inicio");

        try {
            return vucemTokenDAO.findVucemTokenPorIdCliente(idCliente);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.severe("@obtenerVucemTokenPorIdCliente: exception -> " + e.toString());
            throw new GlosaNetException();
        }
    }


    public VucemTokenBO actualizarVucemToken(VucemTokenBO token) throws GlosaNetException {

        LOGGER.info("@actualizarVucemToken: Inicio");

        try {
            return vucemTokenDAO.updateVucemToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.severe("@actualizarVucemToken: exception -> " + e.toString());
            throw new GlosaNetException();
        }
    }

    public boolean eliminarVucemToken(Long idToken) throws GlosaNetException {

        LOGGER.info("@eliminarVucemToken: Inicio");

        try {
            return vucemTokenDAO.deleteVucemToken(idToken);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.severe("@eliminarVucemToken: exception -> " + e.toString());
            throw new GlosaNetException();
        }
    }
}
