package org.mx.persistencia.permisos.builder;

import org.mx.modelo.comun.bo.ProductoBO;
import org.mx.modelo.permisos.org.mx.bo.HistorialPermisosBO;
import org.mx.modelo.permisos.org.mx.po.DupPermisoEventoPO;
import org.mx.modelo.permisos.org.mx.po.HistorialPermisosPO;
import org.mx.persistencia.comun.builder.BuilderProducto;
import org.mx.persistencia.comun.builder.BuilderUsuario;

import java.text.ParseException;
import java.util.Date;



public class BuilderHistorialPermisos {

	
	public static HistorialPermisosBO builderPermisosBO(Object[] obj)throws ParseException{
		HistorialPermisosBO bo = new HistorialPermisosBO();
		bo.setPermiso((String)obj[0]);
		bo.setPkHistorialPermisos(Integer.parseInt(obj[1].toString()));
		return bo;
	}
	
	public static HistorialPermisosBO builderPermisosGeneralesBO(Object obj)throws ParseException{
		HistorialPermisosBO bo = new HistorialPermisosBO();
		bo.setPermiso((String)obj);
		return bo;
	}
	
	
	@SuppressWarnings("deprecation")
	public static  HistorialPermisosBO builderBOSimple(HistorialPermisosPO po){
		HistorialPermisosBO bo = new HistorialPermisosBO();
		bo.setFfin(po.getFechaFin());
		bo.setFinicio(po.getFechaInicio());
		bo.setFolioRegistro(po.getFolioRegistro());
		//		bo.setIdProducto(po.getIdProducto());
		bo.setIdpermiso(bo.getIdpermiso());
		bo.setNumEventos(po.getEventos().size());
		int num = 0;
//		po.getEventos().
		String ev = "";
		Date ed = new Date(3000, 1, 1);
		for(DupPermisoEventoPO x : po.getEventos()){
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
			
			
			if(x.isTerminado() && x.getFechaRealizada().before(ed)){
				num++;
				
			}else{
				ev =x.getNombre();
				bo.getEventos().add(BuilderPermisosEventos.builder2BO(x));
				break;
			}
		}

		bo.setCerrados(num);
		bo.setEventoActual(ev);

		bo.setTramiteCofepris(po.getTramiteCofepris());
		bo.setIdEmpresa(po.getIdEmpresa()); 
		if(po.getUsuario() != null){
			bo.setUsuario(BuilderUsuario.builderBO(po.getUsuario()));
		}
		if(po.getProducto()!=null){
			ProductoBO producto = BuilderProducto.builderBOSimple(po.getProducto());
			bo.setProducto(producto);
		}

		return bo;
	}
}
