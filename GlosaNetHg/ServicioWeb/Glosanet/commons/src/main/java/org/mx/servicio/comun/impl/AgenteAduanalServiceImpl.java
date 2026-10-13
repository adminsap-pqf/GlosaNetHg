package org.mx.servicio.comun.impl;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.AgenciaAduanalBO;
import org.mx.modelo.comun.bo.AgenteAduanalBO;
import org.mx.persistencia.comun.AgenciaAduanalDAO;
import org.mx.persistencia.comun.AgenteAduanalDAO;
import org.mx.persistencia.comun.DireccionFiscalDAO;
import org.mx.servicio.comun.AgenteAduanalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;

@Service("agenteAduanalService")
public class AgenteAduanalServiceImpl implements AgenteAduanalService {
    private final static Logger log = Logger.getLogger(AgenteAduanalServiceImpl.class.getName());

    @Autowired
    AgenteAduanalDAO agenteAduanaDAO;

    @Autowired
    DireccionFiscalDAO direccionFiscalDAO;

    @Autowired
    AgenciaAduanalDAO agenciaAduanalDAO;

    @Override
    public boolean actualizarDatosAgenteAduanal(AgenteAduanalBO bo) {
        return agenteAduanaDAO.modificarAgente(bo);
    }

    @Override
    public int obtenerCantidadAgentesSinInformacion(int idCliente) {
        return agenteAduanaDAO.obtenerCantidadAgentesSinInformacion(idCliente);
    }

    @Override
    public List<AgenteAduanalBO> obtenerAgentesYAgencias(int idCliente, boolean informacion) throws GlosaNetException {
        return agenteAduanaDAO.obtenerAgentesYAgencias(idCliente, informacion);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean actualizarAgenteAduanal(AgenteAduanalBO bo) {
        if (bo.getDireccion().getIdDireccion() == 0) {
            bo.getDireccion().setIdDireccion(null);
            bo.setDireccion(direccionFiscalDAO.insertarDireccion(bo.getDireccion()));
            if (bo.getDireccion().getIdDireccion() == null) {
                log.info("@agenteAduanalService -> No se pudo actualizar la dirección del agente");
                return false;
            }
            log.info("@agenteAduanalService -> Se actualizó la nueva dirección del agente");
        } else {
            log.info("@agenteAduanalService -> Se actualizó la calle, ahora es: " + bo.getDireccion().getCalle());
            direccionFiscalDAO.modificarDireccion(bo.getDireccion());
        }
        log.info("@agenteAduanalService -> Se actualizó el nombre del agente por: " + bo.getNombre());
        return agenteAduanaDAO.actualizarAgente(bo);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean agregarAgenciaAduanal(AgenciaAduanalBO bo) {
        if (bo.getDireccion() != null) {
            bo.getDireccion().setIdDireccion(null);
            bo.setDireccion(direccionFiscalDAO.insertarDireccion(bo.getDireccion()));
            if (bo.getDireccion().getIdDireccion() == null) {
                log.info("@agenteAduanalService -> No se pudo agregar la dirección del agente aduanal");
                return false;
            }
        }
        bo.setIdAgenciaAduanal(null);
        return agenciaAduanalDAO.insertarAgenciaAduanal(bo);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean actualizarAgenciaAduanal(AgenciaAduanalBO bo) {
        direccionFiscalDAO.modificarDireccion(bo.getDireccion());
        return agenciaAduanalDAO.actualizarAgenciaAduanal(bo);
    }
}
