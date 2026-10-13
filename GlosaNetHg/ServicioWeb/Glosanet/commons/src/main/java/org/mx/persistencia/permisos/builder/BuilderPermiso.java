package org.mx.persistencia.permisos.builder;

import org.mx.modelo.comun.bo.ProductoBO;
import org.mx.modelo.permisos.org.mx.bo.PermisoBO;
import org.mx.modelo.permisos.org.mx.po.DupPermisoEventoPO;
import org.mx.modelo.permisos.org.mx.po.PermisoPO;
import org.mx.persistencia.comun.builder.BuilderPrevisiones;
import org.mx.persistencia.comun.builder.BuilderProducto;
import org.mx.persistencia.comun.builder.BuilderUsuario;

import java.text.ParseException;
import java.util.Date;


public class BuilderPermiso {

    public static PermisoBO builderPermisosBO(Object[] obj) throws ParseException {
        PermisoBO bo = new PermisoBO();
        bo.setPermiso((String) obj[0]);
        bo.setPkHistorialPermisos(Integer.parseInt(obj[1].toString()));
        return bo;
    }

    public static PermisoBO builderPermisosGeneralesBO(Object obj) throws ParseException {
        PermisoBO bo = new PermisoBO();
        bo.setPermiso((String) obj);
        return bo;
    }

    @SuppressWarnings("deprecation")
    public static PermisoBO builderBOSimple(PermisoPO po) {
        PermisoBO bo = new PermisoBO();
        bo.setFfin(po.getFechaFin());
        bo.setFinicio(po.getFechaInicio());
        bo.setFolioRegistro(po.getFolioRegistro());
        //bo.setIdProducto(po.getIdProducto());
        bo.setIdpermiso(po.getIdPermisos());
        bo.setNumEventos(po.getEventos().size());
        bo.setFecha(po.getFecha());
        int num = 0;
		//po.getEventos().
        String ev = "";
        Date ed = new Date(3000, 1, 1);

        for (DupPermisoEventoPO x : po.getEventos()) {
//			
////			if(x.isTerminado() && x.getFrealizacion().before(ed)){
////				num++;
////				
////				ev =x.getNombre();
////				bo.getEventos().add(BuilderEventos.builder2BO(x));
////				
////			}else if(ev.equals("")){
////				ev =x.getNombre();
////				bo.getEventos().add(BuilderEventos.builder2BO(x));
////			}

            if (!x.getNombre().equals("Folio de Permiso")) {
                if (x.isTerminado() && x.getFechaRealizada().before(ed)) {
                    num++;
                } else {
                    ev = x.getNombre();
                    //System.out.println("Evento*************************************************************");
                    //System.out.println(x);
                    bo.getEventos().add(BuilderPermisosEventos.builder2BO(x));
                    break;
                }
            }
        }

        bo.setCerrados(num);
        bo.setEventoActual(ev);

        bo.setTramiteCofepris(po.getTramiteCofepris());
        bo.setIdEmpresa(po.getIdEmpresa());
        if (po.getUsuario() != null) {
            bo.setUsuario(BuilderUsuario.builderBO(po.getUsuario()));
        }
        if (po.getProducto() != null) {
            ProductoBO producto = BuilderProducto.builderBOSimple(po.getProducto());
            bo.setProducto(producto);
        }

        return bo;
    }


    public static PermisoBO builderBO(PermisoPO po) {
        PermisoBO bo = new PermisoBO();
        bo.setFolioRegistro(po.getFolioRegistro());
        bo.setIdEmpresa(po.getIdEmpresa());
        //bo.setVigenciaPermiso(po.getViegnciaPermiso());
        bo.setCantidad(po.getCantidad().toString());
        bo.setUnidad(po.getUnidad());
        bo.setFfin(po.getFechaFin());
        bo.setFinicio(po.getFechaInicio());
        bo.setIdpermiso(po.getIdPermisos());
        bo.setCitaCofepris(po.getCitaCofepris());
        bo.setFechaCita(po.getFechaCita());
        bo.setFecha(po.getFecha());
        bo.setTerminado(po.isTerminado());
        if (po.getPrevision() != null) {
            bo.setPrevision(BuilderPrevisiones.builderBO(po.getPrevision()));
        }
        for (DupPermisoEventoPO ev : po.getEventos()) {
            bo.getEventos().add(BuilderPermisosEventos.builder2BO(ev));
        }
        bo.setNumEventos(po.getEventos().size());
        bo.setTramiteCofepris(po.getTramiteCofepris());

        if (po.getUsuario() != null) {
            bo.setUsuario(BuilderUsuario.builderBO(po.getUsuario()));
        }
        if (po.getProducto() != null) {
            ProductoBO producto = BuilderProducto.builderBOSimple(po.getProducto());
            bo.setProducto(producto);
        }
        return bo;
    }
}
