package org.mx.persistencia.comun.builder;

import org.mx.modelo.datastage.bo.PermisosBO;
import org.mx.modelo.datastage.bo.UsuarioPermisosBO;
import org.mx.modelo.datastage.po.UsuarioPermisosPO;


public class BuilderUsuarioPermisos {

	public static UsuarioPermisosBO builderBO(UsuarioPermisosPO po)
	{
		UsuarioPermisosBO bo = new UsuarioPermisosBO();
		bo.setIdUsuarioPermiso(po.getIdUsuarioPermiso());
		bo.setIdUsuario(po.getIdUsuario());
		bo.setActivo(po.isActivo());
		
		if(po.getPermiso() != null){
			PermisosBO permiso = BuilderPermisos.builderBO(po.getPermiso());
			bo.setPermiso(permiso);
		}else{
			//System.out.println("Esta en null el permiso");
		}
		
		return bo;
	}
	
	public static UsuarioPermisosPO builderPO(UsuarioPermisosBO bo)
	{
		UsuarioPermisosPO po = new UsuarioPermisosPO();
		po.setIdUsuarioPermiso(bo.getIdUsuarioPermiso());
		po.setIdUsuario(bo.getIdUsuario());
		po.setActivo(bo.isActivo());
		
		return po;
	}
}
