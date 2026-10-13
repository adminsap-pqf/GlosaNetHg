
package org.mx.persistencia.comun.builder;


import java.util.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.mx.modelo.comun.bo.HistorialImportacionesBO;


public class BuilderHistorialImportaciones {
	
	public static HistorialImportacionesBO builderHistorialBOPartida(Object[] obj)throws ParseException{
		HistorialImportacionesBO  bo = new HistorialImportacionesBO();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		bo.setCantidad(Double.parseDouble(obj[0].toString()));
		bo.setObservaciones(obj[1].toString());
		if(obj[2] != null){
			bo.setNumeropermiso(obj[2].toString());
		}
		
		if(obj[3] != null){
			bo.setUnidad(obj[3].toString());
		}
		if(obj[4] != null){
			bo.setFecha(sdf.format(obj[4]));
			
		}
		bo.setImportacionVigente("N/A");
		bo.setTotalImportaciones("0");
		
		
		return bo;		
	}

	public static HistorialImportacionesBO builderHistorialBO(Object[] obj) throws ParseException{
		HistorialImportacionesBO  bo = new HistorialImportacionesBO();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		bo.setCantidad(Double.parseDouble(obj[0].toString()));
		bo.setObservaciones(obj[1].toString());
		if(obj[2] != null){
			bo.setNumeropermiso(obj[2].toString());
		}
		
		if(obj[3] != null){
			bo.setUnidad(obj[3].toString());
		}
	
		if(obj[4] != null){
			bo.setFecha(sdf.format(obj[4]));
		}
	
		if(obj[5] != null){
			if(obj[6] != null){
				Date date1, date2;
				date1 = (Date) sdf.parse(sdf.format((Timestamp)obj[6]));
				date2 = (Date) sdf.parse(sdf.format(obj[5]));
				int i = date1.compareTo(date2);
				if (i >= 0){
					bo.setImportacionVigente(obj[8].toString());
				}else{
					bo.setImportacionVigente("N/A");
				}
			}
			
		}
		if(obj[7] != null){
			bo.setTotalImportaciones(obj[7].toString());
		}
		
		return bo;		
	}
	

}
