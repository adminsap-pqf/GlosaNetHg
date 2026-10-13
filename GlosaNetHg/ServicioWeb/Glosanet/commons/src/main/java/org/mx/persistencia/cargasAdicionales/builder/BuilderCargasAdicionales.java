package org.mx.persistencia.cargasAdicionales.builder;

import org.mx.modelo.cargas_adicionales.bo.CargasAdicionalesBO;
import org.mx.modelo.cargas_adicionales.po.CargasAdicionalesPO;

import java.sql.Timestamp;

public class BuilderCargasAdicionales {


    public static CargasAdicionalesBO builderBO(CargasAdicionalesPO po) {
        CargasAdicionalesBO bo = new CargasAdicionalesBO();
        bo.setNombreArchivo(po.getNombreArchivo());
        bo.setFechaCarga(po.getFechaCarga());
        bo.setFUA(po.getFua());
        bo.setDescripcion(po.getDescripcion());
        bo.setIdDocumento(po.getPkDocumento());
        bo.setIdUsuario(po.getFk02Usuario());
        bo.setIdDatosGenerales(po.getFk01DatosGenerales551());

        return bo;

    }

    public static CargasAdicionalesPO builderPO(CargasAdicionalesBO bo) {
        CargasAdicionalesPO po = new CargasAdicionalesPO();

        po.setNombreArchivo(bo.getNombreArchivo());
        if (bo.getFechaCarga() != null) {
            po.setFechaCarga(new Timestamp(bo.getFechaCarga().getTime()));
        }
        if (bo.getFUA() != null) {
            po.setFua(new Timestamp(bo.getFUA().getTime()));
        }
        po.setDescripcion(bo.getDescripcion());
        po.setPkDocumento(bo.getIdDocumento());
        po.setFk02Usuario(bo.getIdUsuario());
        po.setFk01DatosGenerales551(bo.getIdDatosGenerales());
        return po;
    }
}
