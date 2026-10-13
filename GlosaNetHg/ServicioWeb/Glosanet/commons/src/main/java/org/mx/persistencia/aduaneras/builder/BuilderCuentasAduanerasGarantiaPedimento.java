package org.mx.persistencia.aduaneras.builder;

import java.sql.Timestamp;

import org.mx.modelo.comun.po.DataStagePO;
import org.mx.modelo.datastage.bo.CuentasAduanerasGarantiaPedimentoBO;
import org.mx.modelo.datastage.po.CuentasAduanerasGarantiaPedimentoPO;
import org.mx.persistencia.comun.builder.BuilderDataStage;

public class BuilderCuentasAduanerasGarantiaPedimento {

	public static CuentasAduanerasGarantiaPedimentoPO builderPO (CuentasAduanerasGarantiaPedimentoBO bo){

		CuentasAduanerasGarantiaPedimentoPO po = new CuentasAduanerasGarantiaPedimentoPO();
		po.setCantidadUnidades(bo.getCantidadUnidades());
		po.setClaveGarantia(bo.getClaveGarantia());
		if (bo.getFechaConstancia() != null)
			po.setFechaConstancia(new Timestamp(bo.getFechaConstancia().getTime()));
		if (bo.getFechaPagoReal() != null)
			po.setFechaPagoReal(new Timestamp(bo.getFechaPagoReal().getTime()));
		po.setFolioConstancia(bo.getFolioConstancia());
		po.setInstitucionEmisora(bo.getInstitucionEmisora());
		po.setNumeroCuenta(bo.getNumeroCuenta());
		po.setPatente(bo.getPatente());
		po.setPedimento(bo.getPedimento());
		po.setPkCuentasAduanerasGarantiaPedimento(bo.getIdCuentasAduanerasGarantiaPedimento());
		po.setSeccionAduanera(bo.getSeccionAduanera());
		po.setTipoCuenta(bo.getTipoCuenta());
		po.setTitulosAsignados(bo.getTitulosAsignados());
		po.setTotalGarantia(bo.getTotalGarantia());
		po.setValorUnitarioTitulo(bo.getValorUnitarioTitulo());

		DataStagePO dataStage = BuilderDataStage.buildPO(bo.getDataStage());
		po.setDataStage(dataStage);

		return po;
	}
	
}
