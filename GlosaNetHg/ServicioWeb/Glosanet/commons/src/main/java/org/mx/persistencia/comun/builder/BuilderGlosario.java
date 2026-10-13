package org.mx.persistencia.comun.builder;

import org.mx.modelo.comun.bo.ApendiceBO;
import org.mx.modelo.comun.bo.ApendiceContenidoBO;
import org.mx.modelo.comun.bo.RegionContenidoBO;
import org.mx.modelo.comun.bo.RegionesBO;
import org.mx.modelo.comun.bo.TratadoBO;
import org.mx.modelo.comun.bo.TratadoContenidoBO;
import org.mx.modelo.comun.po.ApendicePO;
import org.mx.modelo.comun.po.RegionesPO;
import org.mx.modelo.comun.po.TratadoPO;

public class BuilderGlosario {

	public static ApendiceBO builderBO(ApendicePO po){
		ApendiceBO bo = new ApendiceBO();
		bo.setIdApendice(po.getIdApendice());
		bo.setNumApendice(po.getNumApendice());
		bo.setNombreApendice(po.getNombreApendice());

		return bo;
	}
	
	public static TratadoBO builderBO(TratadoPO po){
		TratadoBO bo = new TratadoBO();
		bo.setIdTratado(po.getIdTratado());
		bo.setNumTratado(po.getNumTratado());
		bo.setNombreTratado(po.getNombreTratado());
		bo.setSubTitulo(po.getSubTitulo());

		return bo;
	}
	
	public static RegionesBO builderBO(RegionesPO po){
		RegionesBO bo = new RegionesBO();
		bo.setIdRegion(po.getIdRegion());
		bo.setNumRegion(po.getNumRegion());
		bo.setNombreRegion(po.getNombreRegion());

		return bo;
	}

	public static ApendiceContenidoBO builderBO(Object[] po){
		ApendiceContenidoBO bo = new ApendiceContenidoBO();
		bo.setIdApendiceContenido(Integer.parseInt(po[0].toString()));
		bo.setRenglon(Integer.parseInt(po[2].toString()));
		if(po[3] != null && !po[3].equals("")){
			bo.setOrden(Integer.parseInt(po[3].toString()));
		}
		if(po[4] != null && !po[4].equals("")){
			bo.setColumn1(po[4].toString());
		}
		if(po[5] != null && !po[5].equals("")){
			bo.setColumn2(po[5].toString());
		}
		if(po[6] != null && !po[6].equals("")){
			bo.setColumn3(po[6].toString());
		}
		if(po[7] != null && !po[7].equals("")){
			bo.setColumn4(po[7].toString());
		}
		if(po[8] != null && !po[8].equals("")){
			bo.setStyle(po[8].toString());
		}

		return bo;
	}
	
	public static TratadoContenidoBO builderTCBO(Object[] po){
		TratadoContenidoBO bo = new TratadoContenidoBO();
		bo.setIdTratadoContenido(Integer.parseInt(po[0].toString()));
		bo.setRenglon(Integer.parseInt(po[2].toString()));
		if(po[3] != null && !po[3].equals("")){
			bo.setColumn1(po[3].toString());
		}
		if(po[4] != null && !po[4].equals("")){
			bo.setColumn2(po[4].toString());
		}
		if(po[5] != null && !po[5].equals("")){
			bo.setStyle(po[5].toString());
		}

		return bo;
	}
	
	public static RegionContenidoBO builderRBO(Object[] po){
		RegionContenidoBO bo = new RegionContenidoBO();
		bo.setIdRegionContenido(Integer.parseInt(po[0].toString()));
		bo.setRenglon(Integer.parseInt(po[2].toString()));
		if(po[3] != null && !po[3].equals("")){
			bo.setColumn1(po[3].toString());
		}
		if(po[4] != null && !po[4].equals("")){
			bo.setColumn2(po[4].toString());
		}
		if(po[5] != null && !po[5].equals("")){
			bo.setStyle(po[5].toString());
		}

		return bo;
	}

}
