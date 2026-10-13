package org.mx.persistencia.comun.builder;

import org.mx.modelo.comun.bo.ConsecutivosBO;
import org.mx.modelo.comun.po.ConsecutivosPO;

public class BuilderConsecutivos {
	
	public static ConsecutivosBO builderBO(ConsecutivosPO po){
		ConsecutivosBO bo = new ConsecutivosBO();
		bo.setIdConsecutivo(po.getIdConsecutivos());
		bo.setFolioRemision(po.getFolioRemision());
		
		return bo;
		
	}

}
