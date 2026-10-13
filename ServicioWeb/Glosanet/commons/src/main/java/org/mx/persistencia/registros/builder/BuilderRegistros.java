package org.mx.persistencia.registros.builder;

import org.mx.modelo.comun.bo.ProductoBO;
import org.mx.modelo.registros.bo.HistorialProductoBO;
import org.mx.modelo.registros.bo.RegistroBO;
import org.mx.modelo.registros.po.EventoPO;
import org.mx.modelo.registros.po.HistorialProductoPO;
import org.mx.modelo.registros.po.RegistroIndexPO;
import org.mx.modelo.registros.po.RegistroPO;
import org.mx.persistencia.comun.builder.BuilderProducto;
import org.mx.persistencia.comun.builder.BuilderUsuario;

public class BuilderRegistros {

    @SuppressWarnings("deprecation")
    public static RegistroBO builderBOSimple(RegistroPO po) {
        RegistroBO bo = new RegistroBO();

        bo.setDescripcion(po.getDescripcion());
        bo.setFfin(po.getFfin());
        bo.setFinicio(po.getFinicio());
        bo.setFolioRegistro(po.getFolioRegistro());
        //		bo.setIdProducto(po.getIdProducto());
        bo.setIdRegistro(po.getIdRegistro());
        bo.setNumEventos(po.getEventos().size());
        bo.setTerminado(po.isTerminado());
        bo.setLastUpdateDate(po.getLastUpdateDate());
        int num = 0;
        //po.getEventos().o
        String ev = "";

        for (EventoPO x : po.getEventos()) {

            //			if(x.isTerminado() && x.getFrealizacion().before(ed)){
            //				num++;
            //
            //				ev =x.getNombre();
            //				bo.getEventos().add(BuilderEventos.builder2BO(x));
            //
            //			}else if(ev.equals("")){
            //				ev =x.getNombre();
            //				bo.getEventos().add(BuilderEventos.builder2BO(x));
            //			}

            if (x.getFrealizacion() == null) {
                num++;
            } else {
                if (x.isTerminado()) {
                    num++;

                } else {
                    ev = x.getNombre();
                    bo.getEventos().add(BuilderEventos.builder2BO(x));
                    break;
                }
            }
        }

        bo.setCerrados(num);
        bo.setEventoActual(ev);

        bo.setProrroga(po.isProrroga());
        bo.setTramiteCofepris(po.getTramiteCofepris());
        bo.setIdEmpresa(po.getIdEmpresa());
        if (po.getUsuario() != null) {
            bo.setUsuario(BuilderUsuario.builderBO(po.getUsuario()));
        }
        if (po.getProducto() != null) {
            ProductoBO producto = BuilderProducto.builderBOSimple(po.getProducto());
            bo.setProducto(producto);
        }
        bo.setFfinProrroga(po.getFfinProrroga());
        bo.setFinicioProrroga(po.getFinicioProrroga());

        return bo;
    }

    public static RegistroBO builderBO(RegistroPO po) {
        RegistroBO bo = new RegistroBO();
        bo.setDescripcion(po.getDescripcion());
        bo.setFfin(po.getFfin());
        bo.setFinicio(po.getFinicio());
        bo.setFolioRegistro(po.getFolioRegistro());
        bo.setIdEmpresa(po.getIdEmpresa());
        //bo.setIdProducto(po.getIdProducto());
        bo.setIdRegistro(po.getIdRegistro());
        for (EventoPO ev : po.getEventos()) {
            bo.getEventos().add(BuilderEventos.builderBO(ev));
        }
        bo.setNumEventos(po.getEventos().size());
        bo.setProrroga(po.isProrroga());
        bo.setTramiteCofepris(po.getTramiteCofepris());

        if (po.getUsuario() != null) {
            bo.setUsuario(BuilderUsuario.builderBO(po.getUsuario()));
        }
        if (po.getProducto() != null) {
            ProductoBO producto = BuilderProducto.builderBOSimple(po.getProducto());
            bo.setProducto(producto);
        }
        bo.setFfinProrroga(po.getFfinProrroga());
        bo.setFinicioProrroga(po.getFinicioProrroga());
        bo.setTerminado(po.isTerminado());
        bo.setLastUpdateDate(po.getLastUpdateDate());
        return bo;
    }

    public static RegistroBO builderIndexBO(RegistroIndexPO po) {
        RegistroBO bo = new RegistroBO();

        bo.setDescripcion(po.getDescripcion());
        bo.setFfin(po.getFfin());
        bo.setFinicio(po.getFinicio());
        bo.setFolioRegistro(po.getFolioRegistro());
        bo.setIdRegistro(po.getIdRegistro());
        bo.setNumEventos(po.getEventos().size());
        bo.setTerminado(po.isTerminado());
        bo.setLastUpdateDate(po.getLastUpdateDate());
        int num = 0;
        String ev = "";

        for (EventoPO x : po.getEventos()) {

            if (x.getFrealizacion() == null) {
                num++;
            } else {
                if (x.isTerminado()) {
                    num++;

                } else {
                    ev = x.getNombre();
                    bo.getEventos().add(BuilderEventos.builder2BO(x));
                    break;
                }
            }
        }

        bo.setCerrados(num);
        bo.setEventoActual(ev);

        bo.setProrroga(po.isProrroga());
        bo.setTramiteCofepris(po.getTramiteCofepris());
        bo.setIdEmpresa(po.getIdEmpresa());
        if (po.getUsuario() != null) {
            bo.setUsuario(BuilderUsuario.builderBO(po.getUsuario()));
        }
        if (po.getProducto() != null) {
            ProductoBO producto = BuilderProducto.builderBOSimple(po.getProducto());
            bo.setProducto(producto);
        }
        bo.setFfinProrroga(po.getFfinProrroga());
        bo.setFinicioProrroga(po.getFinicioProrroga());

        bo.setRecordIndex(po.getRecordIndex());

        return bo;
    }

    public static HistorialProductoBO builderHistorialProducto(HistorialProductoPO po) {
        HistorialProductoBO bo = new HistorialProductoBO();
        bo.setIdHistorialProducto(po.getIdHistorialProducto());
        bo.setIdRegistro(po.getIdRegistro());
        bo.setNombre(po.getNombre());
        bo.setComentarios(po.getComentarios());
        bo.setFecha(po.getFecha());
        return bo;
    }
}
