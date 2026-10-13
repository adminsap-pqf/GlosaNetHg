package org.mx.persistencia.comun.builder;

import java.sql.Timestamp;

import org.mx.modelo.comun.bo.ClientesBO;
import org.mx.modelo.comun.bo.UsuarioBO;
import org.mx.modelo.comun.po.ClientesPO;
import org.mx.modelo.comun.po.UsuariosPO;
import org.mx.modelo.datastage.po.UsuarioPermisosPO;

public class BuilderUsuario {

	public static UsuarioBO builderBO(UsuariosPO po, boolean oneToMany){
		UsuarioBO bo = new UsuarioBO();
		bo.setIdUsuario(po.getPkUsuario());
		bo.setUsuario(po.getUsuario());
		bo.setPassword(po.getPassword());
		bo.setNombre(po.getNombre());
		bo.setApellido(po.getApellido());
		bo.setEmail(po.getEmail());
		if (po.getFcreacion() != null)
			bo.setFcreacion(new Timestamp(po.getFcreacion().getTime()));
		
		bo.setTelefono(po.getTelefono());
		bo.setHabilitado(po.isHabilitado());
		bo.setConsola(po.isConsola());
		bo.setPermiso(po.getPermisos());
		return bo;
	}

	public static UsuarioBO builderBO(UsuariosPO po)
	{
		UsuarioBO bo = new UsuarioBO();
		bo.setIdUsuario(po.getPkUsuario());
		bo.setPassword(po.getPassword());
		bo.setUsuario(po.getUsuario());
		bo.setHabilitado(po.isHabilitado());
		bo.setApellido(po.getApellido());
		bo.setEmail(po.getEmail());
		bo.setExt(po.getExt());
		bo.setNombre(po.getNombre());
		bo.setTelefono(po.getTelefono());
		bo.setConsola(po.isConsola());
		
		bo.setPermiso(po.getPermisos());
		
		if (po.getLstPermisos() != null && po.getLstPermisos().size() > 0)
		{
			for (UsuarioPermisosPO permiso : po.getLstPermisos()) {
				bo.getPermisos().add(BuilderUsuarioPermisos.builderBO(permiso));
			}
		}

		if (po.getFcreacion() != null)
			bo.setFcreacion(new Timestamp(po.getFcreacion().getTime()));


		if(po.getCliente() != null){
			ClientesBO cliente = BuilderClientes.builderBO(po.getCliente());	
			bo.setCliente(cliente);
		}

		return bo;
	}

	public static UsuariosPO builderPO(UsuarioBO bo)
	{
		UsuariosPO po = new UsuariosPO();
		po.setHabilitado(bo.isHabilitado());
		po.setConsola(bo.isConsola());
		po.setPassword(bo.getPassword());
		po.setPkUsuario(bo.getIdUsuario());
		po.setUsuario(bo.getUsuario());

		po.setApellido(bo.getApellido());
		po.setEmail(bo.getEmail());
		po.setExt(bo.getExt());
		po.setNombre(bo.getNombre());
		po.setTelefono(bo.getTelefono());

		bo.setPermiso(po.getPermisos());

	

		if (bo.getFcreacion() != null)
			po.setFcreacion(new Timestamp(bo.getFcreacion().getTime()));

		//System.out.println(bo.getCliente());
		ClientesPO cliente = BuilderClientes.builderPO(bo.getCliente());
		//System.out.println(bo.getPermiso());

//		PermisosPO permisos = BuilderPermisos.builderPO(bo.getPermiso());
//		if (bo.getPermisos() != null)
//		{
//			po.setLstPermisos(new ArrayList<UsuarioPermisosPO>());
//			for (UsuarioPermisosBO permiso : bo.getPermisos()) {
//				po.getLstPermisos().add(BuilderUsuarioPermisos.builderPO(permiso));
//			}
//		}

		po.setCliente(cliente);
//		po.setPermisos(permisos);

		return po;
	}




}
