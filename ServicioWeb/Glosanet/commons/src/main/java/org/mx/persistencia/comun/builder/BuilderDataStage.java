package org.mx.persistencia.comun.builder;

import java.sql.Timestamp;
import java.util.Date;

import org.mx.modelo.comun.bo.ClientesBO;
import org.mx.modelo.comun.bo.UsuarioBO;
import org.mx.modelo.comun.po.ClientesPO;
import org.mx.modelo.comun.po.DataStagePO;
import org.mx.modelo.comun.po.UsuariosPO;
import org.mx.modelo.datastage.bo.DataStageBO;

public class BuilderDataStage {

	public static DataStagePO buildPO(DataStageBO bo){
		DataStagePO po = new DataStagePO();
		po.setFolio(bo.getFolio());
		po.setPkDataStage(bo.getIdDataStage());
		po.setFua(new Timestamp(new Date().getTime()));

		ClientesPO clientes = BuilderClientes.builderPO(bo.getClientes());
		po.setClientes(clientes);

		UsuariosPO usuario = BuilderUsuario.builderPO(bo.getUsuario());
		po.setUsuario(usuario);

		return po;
	}

	public static DataStageBO buildBO(DataStagePO po){
		try{
			DataStageBO bo = new DataStageBO();
			bo.setFolio(po.getFolio());
			bo.setIdDataStage(po.getPkDataStage());
			bo.setFua(po.getFua());
			if(po.getClientes() != null){
				ClientesBO clientes = BuilderClientes.builderBO(po.getClientes());
				bo.setClientes(clientes);
			}

			if(po.getUsuario() != null){
				UsuarioBO usuario = BuilderUsuario.builderBO(po.getUsuario());
				bo.setUsuario(usuario);
			}

			return bo;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
