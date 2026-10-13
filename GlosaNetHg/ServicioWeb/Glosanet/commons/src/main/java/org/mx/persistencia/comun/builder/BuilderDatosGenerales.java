package org.mx.persistencia.comun.builder;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mx.modelo.comun.bo.ClientesBO;
import org.mx.modelo.comun.po.DataStagePO;
import org.mx.modelo.datastage.bo.DataStageBO;
import org.mx.modelo.datastage.bo.DatosGeneralesBO;
import org.mx.modelo.datastage.bo.ResumenBO;
import org.mx.modelo.datastage.po.DatosGeneralesPO;
import org.mx.modelo.util.Util;
import org.mx.persistencia.comun.impl.DataBaseDAOImpl;

public class BuilderDatosGenerales {
	
	public static DatosGeneralesPO builderPO(DatosGeneralesBO bo){

		DatosGeneralesPO po = new DatosGeneralesPO();
		po.setCalleContribuyente(bo.getCalleContribuyente());
		po.setClaveDocumento(bo.getClaveDocumento());
		po.setCpcontribuyente(bo.getCpcontribuyente());
		po.setCurpAgenteA(bo.getCurpAgenteA());
		po.setCurpContribuyente(bo.getCurpContribuyente());
		po.setDestinoMercancia(bo.getDestinoMercancia());
		po.setEntidadFedContribuyente(bo.getEntidadFedContribuyente());
		if (bo.getFechaPagoReal() != null)
			po.setFechaPagoReal(new Timestamp(bo.getFechaPagoReal().getTime()));
		if (bo.getFechaRecepcionPedimento() != null)
			po.setFechaRecepcionPedimento(new Timestamp(bo.getFechaRecepcionPedimento().getTime()));
		po.setMedioTransporteArribo(bo.getMedioTransporteArribo());
		po.setMedioTransporteEntradaSalida(bo.getMedioTransporteEntradaSalida());
		po.setMedioTransporteSalida(bo.getMedioTransporteSalida());
		po.setMunicipioContribuyente(bo.getMunicipioContribuyente());
		po.setNombreContribuyente(bo.getNombreContribuyente());
		po.setNumExteriorContribuyente(bo.getNumExteriorContribuyente());
		po.setNumInteriorContribuyente(bo.getNumInteriorContribuyente());
		po.setPaisContribuyente(bo.getPaisContribuyente());
		po.setPatente(bo.getPatente());
		po.setPedimento(bo.getPedimento());
		po.setPesoBrutoMercancia(bo.getPesoBrutoMercancia());
		po.setPkDatosGenerales(bo.getPkDatosGenerales());
		po.setRfc(bo.getRfc());
		po.setSeccionAduanera(bo.getSeccionAduanera());
		po.setSeccionAduaneraEntrada(bo.getSeccionAduaneraEntrada());
		po.setTipoCambio(bo.getTipoCambio());
		po.setTipoOperacion(bo.getTipoOperacion());
		po.setTipoPedimento(bo.getTipoPedimento());
		po.setTotalDeducibles(bo.getTotalDeducibles());
		po.setTotalEmbalajes(bo.getTotalEmbalajes());
		po.setTotalFletes(bo.getTotalFletes());
		po.setTotalIncrementables(bo.getTotalIncrementables());
		po.setTotalSeguros(bo.getTotalSeguros());

		DataStagePO dataStage = BuilderDataStage.buildPO(DataBaseDAOImpl.dataStageBO);
		po.setDataStage(dataStage);

		return po;
	}
	
	public static DatosGeneralesBO builderBO(DatosGeneralesPO po)
	{
		DatosGeneralesBO bo = new DatosGeneralesBO();
		bo.setCalleContribuyente(po.getCalleContribuyente());
		bo.setClaveDocumento(po.getClaveDocumento());
		bo.setCpcontribuyente(po.getCpcontribuyente());
		bo.setCurpAgenteA(po.getCurpAgenteA());
		bo.setCurpContribuyente(po.getCurpContribuyente());
		bo.setDestinoMercancia(po.getDestinoMercancia());
		bo.setEntidadFedContribuyente(po.getEntidadFedContribuyente());
		bo.setFechaPagoReal(po.getFechaPagoReal());
		bo.setFechaRecepcionPedimento(po.getFechaRecepcionPedimento());
		bo.setMedioTransporteArribo(po.getMedioTransporteArribo());
		bo.setMedioTransporteEntradaSalida(po.getMedioTransporteEntradaSalida());
		bo.setMedioTransporteSalida(po.getMedioTransporteSalida());
		bo.setMunicipioContribuyente(po.getMunicipioContribuyente());
		bo.setNombreContribuyente(po.getNombreContribuyente());
		bo.setNumExteriorContribuyente(po.getNumExteriorContribuyente());
		bo.setNumInteriorContribuyente(po.getNumInteriorContribuyente());
		bo.setPaisContribuyente(po.getPaisContribuyente());
		bo.setPatente(po.getPatente());
		bo.setPedimento(po.getPedimento());
		bo.setPesoBrutoMercancia(po.getPesoBrutoMercancia());
		bo.setPkDatosGenerales(po.getPkDatosGenerales());
		bo.setRfc(po.getRfc());
		bo.setSeccionAduanera(po.getSeccionAduanera());
		bo.setSeccionAduaneraEntrada(po.getSeccionAduaneraEntrada());
		bo.setTipoCambio(po.getTipoCambio());
		bo.setTipoOperacion(po.getTipoOperacion());
		bo.setTipoPedimento(po.getTipoPedimento());
		bo.setTotalDeducibles(po.getTotalDeducibles());
		bo.setTotalEmbalajes(po.getTotalEmbalajes());
		bo.setTotalFletes(po.getTotalFletes());
		bo.setTotalIncrementables(po.getTotalIncrementables());
		bo.setTotalSeguros(po.getTotalSeguros());

		DataStageBO dataStage = BuilderDataStage.buildBO(po.getDataStage());
		bo.setDataStage(dataStage);
		
		return bo; 
	}
	
	public static List<DatosGeneralesBO> builderObjectMaxMinDateNavegacion(List<Object[]> po)
	{
		List<DatosGeneralesBO> list = new ArrayList<DatosGeneralesBO>();
		for (Object[] objects : po) {
			DatosGeneralesBO bo = new DatosGeneralesBO();
			try {
				bo.setFinicio(Util.formatter.parse(objects[0].toString()));
				bo.setFfin(Util.formatter.parse(objects[1].toString()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			DataStageBO dataStage = new DataStageBO();
			dataStage.setFolio(objects[2].toString());
			dataStage.setFua((Date) objects[4]);
			bo.setDataStage(dataStage);
			
			ClientesBO cliente = new ClientesBO();
			cliente.setNombre(objects[3].toString());
			dataStage.setClientes(cliente);
			
			ResumenBO resumen = new ResumenBO();
			try {
				resumen.setFechaEjecucion(Util.formatter.parse(objects[5].toString()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			bo.setResumen(resumen);
			//FIXME Solo para este caso se reutilizara esta variable para no crear mas y cuando esten seguros de que total(monto) se refieren se colocara la varibale correcta
			if (objects[6] != null && !objects[6].equals("")){
				bo.setTotalDeducibles(Double.parseDouble(objects[6].toString()));
			}else{
				bo.setTotalDeducibles(0.0);
			}
			list.add(bo);
		}
		
		return list;
	}
	
	public static List<DatosGeneralesBO> builderObjectMaxMinDate(List<Object[]> po)
	{
		List<DatosGeneralesBO> list = new ArrayList<DatosGeneralesBO>();
		for (Object[] objects : po) {
			DatosGeneralesBO bo = new DatosGeneralesBO();
			try {
				if(objects[0] != null){
					bo.setFinicio(Util.formatter.parse(objects[0].toString()));					
				}
				if(objects[1] != null){
					bo.setFfin(Util.formatter.parse(objects[1].toString()));					
				}
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
			list.add(bo);
		}
		return list;
	}

}
