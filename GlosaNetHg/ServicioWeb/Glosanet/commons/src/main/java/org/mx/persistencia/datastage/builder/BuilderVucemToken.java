package org.mx.persistencia.datastage.builder;

import org.mx.modelo.datastage.bo.VucemTokenBO;
import org.mx.modelo.datastage.po.VucemTokenPO;

public class BuilderVucemToken {

    public static VucemTokenPO builderPO(VucemTokenBO bo) {

        VucemTokenPO po = new VucemTokenPO();
        po.setIdCliente(bo.getIdCliente());
        po.setToken(bo.getToken());
        po.setLastUpdateDate(bo.getLastUpdateDate());
        po.setActivo(bo.isActivo());

        return po;
    }

    public static VucemTokenBO builderBO(VucemTokenPO po) {

        VucemTokenBO bo = new VucemTokenBO();
        bo.setPkToken(po.getPkToken());
        bo.setIdCliente(po.getIdCliente());
        bo.setToken(po.getToken());
        bo.setLastDate(po.getLastUpdateDate());
        bo.setActivo(po.isActivo());

        return bo;
    }
}
