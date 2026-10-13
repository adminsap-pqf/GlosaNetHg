package org.mx.persistencia.datastage;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.datastage.bo.VucemTokenBO;

public interface VucemTokenDAO {

    public VucemTokenBO saveNuevoVucemToken(VucemTokenBO token) throws GlosaNetException;

    public VucemTokenBO findVucemTokenPorIdCliente(Long idCliente) throws GlosaNetException;

    public VucemTokenBO updateVucemToken(VucemTokenBO token) throws GlosaNetException;

    public boolean deleteVucemToken(Long idToken) throws GlosaNetException;

}
