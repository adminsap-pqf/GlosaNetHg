package org.mx.persistencia.comun.builder;

import org.mx.modelo.comun.bo.EmpresaBO;
import org.mx.modelo.comun.bo.PrevisionesBO;
import org.mx.modelo.comun.bo.UsuarioBO;
import org.mx.modelo.comun.po.PEventoPO;
import org.mx.modelo.comun.po.PrevisionesPO;

public class BuilderPrevisiones {

    public static PrevisionesPO builderPO(PrevisionesBO bo){

        PrevisionesPO po = new PrevisionesPO();
        po.setIdPrevision(bo.getIdPrevision());
        po.setVigencia(bo.getVigencia());
        po.setFechaCreacion(bo.getFechaCreacion());


        return po;
    }

    public static PrevisionesBO builderBO(PrevisionesPO po){

        PrevisionesBO bo = new PrevisionesBO();
        bo.setIdPrevision(po.getIdPrevision());
        bo.setVigencia(po.getVigencia());
        bo.setFechaCreacion(po.getFechaCreacion());
        bo.setCitaCofepris(po.getCitaCofepris());
        bo.setFechaCita(po.getFechaCita());
        if(po.getfK01Empresa() != null){
        	EmpresaBO empresa = BuilderEmpresa.builderBO(po.getfK01Empresa());
        	bo.setEmpresa(empresa);
        }
        if(po.getUsuario() != null){
        	UsuarioBO usuario = BuilderUsuario.builderBO(po.getUsuario());
        	bo.setUsuario(usuario);
        }
        for(PEventoPO ev :po.getPEventos()){
			bo.getEventos().add(BuilderPEventos.builderBO(ev));
		}
//        for(PrevisionProductoPO ev :po.getProductos()){
//			bo.getLstProductos().add(BuilderPrevisionProducto.builderBO(ev));
//		}
        bo.setPublicado(po.getPublicado());
        bo.setTipoPrevision(po.getTipoPrevision());
        bo.setFolioPrevision(po.getFolioPrevision());

        //System.out.println("***********************");
        //System.out.println();

        return bo;
    }
    
    public static PrevisionesBO builderPBO(Object[] po){
		PrevisionesBO bo = new PrevisionesBO();
		bo.setIdPrevision(Integer.parseInt(po[0].toString()));
		bo.setNombre(po[1].toString());
		
		bo.setCantidad(Double.parseDouble(po[2].toString()));
		if(po[3] != null){
			bo.setUnidad(po[3].toString());
			
		}
		bo.setFolioPrevision(po[4].toString());
		bo.setPrevisionProducto(Integer.parseInt(po[5].toString()));
		

		return bo;
	}
    
    public static PrevisionesBO builderPPBO(Object[] po){
		PrevisionesBO bo = new PrevisionesBO();
		bo.setProductos(Integer.parseInt(po[0].toString()));
		bo.setNombre(po[1].toString());

		return bo;
	}
    
    public static PrevisionesBO builderPP(Object[] po){
		PrevisionesBO bo = new PrevisionesBO();
		bo.setIdPrevision(Integer.parseInt(po[0].toString()));
		bo.setNombre(po[1].toString());
		
		bo.setCantidad(Double.parseDouble(po[2].toString()));
		if(po[3] != null){
			bo.setUnidad(po[3].toString());
			
		}
		bo.setFolioPrevision(po[4].toString());
		

		return bo;
	}

	public static PrevisionesBO ProductosPrevision(Object[] obj){
		PrevisionesBO  bo = new PrevisionesBO();
		bo.setCantidad((Double.parseDouble(obj[0].toString())));
		bo.setUnidad(obj[1].toString());
		if(obj[2] != null){
			bo.setDescripcion(obj[2].toString());
		}
		if(obj[3] != null){
			bo.setNombre(obj[3].toString());
		}
		bo.setFkPrevision(Integer.parseInt(obj[4].toString()));
		bo.setVigencia(Integer.parseInt(obj[5].toString()));
		if(obj[6] != null){
			bo.setTotal(Double.parseDouble(obj[6].toString()));
		}else {
			bo.setTotal(0.0);
		}
		bo.setCodigo(obj[7].toString());
		bo.setProductos(Integer.parseInt(obj[8].toString()));
		return bo;
	}


    
}
