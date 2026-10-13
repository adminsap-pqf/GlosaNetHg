package org.mx.persistencia.comun.builder;

import org.mx.modelo.comun.bo.AgenciaAduanalBO;
import org.mx.modelo.comun.bo.AgenteAduanalBO;
import org.mx.modelo.comun.bo.DireccionFiscalBO;
import org.mx.modelo.comun.po.AgenciaAduanalPO;
import org.mx.modelo.comun.po.AgenteAduanalPO;
import org.mx.modelo.comun.po.DireccionFiscalPO;

public class BuilderAgenteAduanal {
    public static AgenteAduanalPO builderPO(AgenteAduanalBO bo) {
        AgenteAduanalPO po = new AgenteAduanalPO();
        po.setPkAgente(bo.getIdAgente());
        po.setNumeroPatente(bo.getNumeroPatente());
        po.setNombre(bo.getNombre());
        //System.out.println("Builder Nombre" + po.getNombre());
        po.setApellido(bo.getApellido());
        po.setRfc(bo.getRfc());
        po.setTelefono(bo.getTelefono());
        po.setCorreoElectronico(bo.getCorreoElectronico());
        po.setFkcliente(bo.getIdCliente());

        for (AgenciaAduanalBO agencia : bo.getAgencias()) {
            po.getAgencias().add(BuilderAgencia.builderPO(agencia));
        }

        if (bo.getDireccion() != null) {
            DireccionFiscalPO direccion = BuilderDireccionFiscal.builderPO(bo.getDireccion());
            po.setDireccion(direccion);
        }

        return po;
    }

    public static AgenteAduanalBO builderBO(AgenteAduanalPO po) {
        AgenteAduanalBO bo = new AgenteAduanalBO();
        bo.setIdAgente(po.getPkAgente());
        bo.setNumeroPatente(po.getNumeroPatente());
        bo.setNombre(po.getNombre());
        bo.setApellido(po.getApellido());
        bo.setRfc(po.getRfc());
        bo.setTelefono(po.getTelefono());
        bo.setCorreoElectronico(po.getCorreoElectronico());
        bo.setIdCliente(po.getFkcliente());
        for (AgenciaAduanalPO agencia : po.getAgencias()) {
            bo.getAgencias().add(BuilderAgencia.builderBO(agencia, true));
        }

        if (po.getDireccion() != null) {
            DireccionFiscalBO direccion = BuilderDireccionFiscal.builderBO(po.getDireccion());
            bo.setDireccion(direccion);
        }

        return bo;
    }
}
