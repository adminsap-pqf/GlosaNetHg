package org.mx.persistencia.comun.builder;


import org.mx.modelo.datastage.po.PermisosPO;
import org.mx.modelo.datastage.bo.PermisosBO;

public class BuilderPermisos {

	public static PermisosBO builderBO(PermisosPO po)
	{
		PermisosBO bo = new PermisosBO();
		bo.setIdPermiso(po.getPkPermiso());
		bo.setPermiso(po.getPermiso());
		
		return bo;
	}
	
	public static PermisosPO builderPO(PermisosBO bo)
	{
		PermisosPO po = new PermisosPO();
		po.setPermiso(bo.getPermiso());
		po.setPkPermiso(bo.getIdPermiso());
		
		return po;
	}
	
}
