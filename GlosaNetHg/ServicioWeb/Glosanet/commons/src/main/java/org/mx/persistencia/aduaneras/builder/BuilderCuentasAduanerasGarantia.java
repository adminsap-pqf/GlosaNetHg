package org.mx.persistencia.aduaneras.builder;

import java.sql.Timestamp;

import org.mx.modelo.comun.po.DataStagePO;
import org.mx.modelo.datastage.bo.CuentasAduanerasGarantiaBO;
import org.mx.modelo.datastage.po.CuentasAduanerasGarantiaPO;
import org.mx.persistencia.comun.builder.BuilderDataStage;

public class BuilderCuentasAduanerasGarantia {

	public static CuentasAduanerasGarantiaPO builderPO(CuentasAduanerasGarantiaBO bo){

		CuentasAduanerasGarantiaPO po = new CuentasAduanerasGarantiaPO();
		po.setCantidadUnidadesMedida(bo.getCantidadUnidadesMedida());
		po.setClaveGarantia(bo.getClaveGarantia());
		if (bo.getFechaConstancia() != null)
			po.setFechaConstancia(new Timestamp(bo.getFechaConstancia().getTime()));
		if (bo.getFechaPagoReal() != null)
			po.setFechaPagoReal(new Timestamp(bo.getFechaPagoReal().getTime()));
		po.setFolioConstancia(bo.getFolioConstancia());
		po.setFraccion(bo.getFraccion());
		po.setInstitucionEmisora(bo.getInstitucionEmisora());
		po.setNumeroCuenta(bo.getNumeroCuenta());
		po.setPatente(bo.getPatente());
		po.setPedimento(bo.getPedimento());
		po.setPkCuentasAduanerasGarantia(bo.getIdCuentasAduanerasGarantia());
		po.setSeccionAduanera(bo.getSeccionAduanera());
		po.setSecuenciaFraccion(bo.getSecuenciaFraccion());
		po.setTitulosAsignados(bo.getTitulosAsignados());
		po.setTotalGarantia(bo.getTotalGarantia());
		po.setValorUnitarioTitulo(bo.getValorUnitarioTitulo());

		DataStagePO dataStage = BuilderDataStage.buildPO(bo.getDataStage());
		po.setDataStage(dataStage);

		return po;
	}
	
}
