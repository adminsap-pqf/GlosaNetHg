package org.mx.servicio.datastage;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.datastage.bo.VucemTokenBO;

import java.util.List;

public interface VucemTokenService {

    public VucemTokenBO guardaVucemToken(VucemTokenBO vucemTokenBO) throws GlosaNetException;
    public VucemTokenBO obtenerVucemTokenPorIdCliente(Long idCliente) throws GlosaNetException;
    public VucemTokenBO actualizarVucemToken(VucemTokenBO token) throws GlosaNetException;
    public boolean eliminarVucemToken(Long idToken) throws GlosaNetException;
}
