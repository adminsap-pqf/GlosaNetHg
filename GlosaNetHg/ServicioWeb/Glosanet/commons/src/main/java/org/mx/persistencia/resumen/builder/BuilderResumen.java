package org.mx.persistencia.resumen.builder;

import java.sql.Timestamp;

import org.mx.modelo.comun.po.DataStagePO;
import org.mx.modelo.datastage.bo.ResumenBO;
import org.mx.modelo.datastage.po.ResumenPO;
import org.mx.persistencia.comun.builder.BuilderDataStage;
import org.mx.persistencia.comun.impl.DataBaseDAOImpl;

public class BuilderResumen {

	public static ResumenPO builderResumen(ResumenBO bo){

		ResumenPO po = new ResumenPO();
		if (bo.getFechaEjecucion() != null){
			po.setFechaEjecucion(new Timestamp(bo.getFechaEjecucion().getTime()));
		}
		if (bo.getFechaFinal() != null){
			po.setFechaFinal(new Timestamp(bo.getFechaFinal().getTime()));
		}
		if (bo.getFechaInicial() != null){
			po.setFechaInicial(new Timestamp(bo.getFechaInicial().getTime()));
		}
		po.setFolio(bo.getFolio());
		po.setPkResumen(bo.getIdResumen());
		po.setRfcoPatenteAduanal(bo.getRfcoPatenteAduanal());
		po.setTotalContribuciones(bo.getTotalContribuciones());
		po.setTotalFracciones(bo.getTotalFracciones());

		if(DataBaseDAOImpl.dataStageBO != null){
			DataStagePO dataStage = BuilderDataStage.buildPO(DataBaseDAOImpl.dataStageBO);
			po.setDataStage(dataStage);
		}

		return po;
	}

}
