package org.mx.modelo.comun.bo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ConsecutivosPO")
public class ConsecutivosBO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5883231246292023641L;
	
	private int idConsecutivo;
	private int folioRemision;
	
	public int getIdConsecutivo() {
		return idConsecutivo;
	}
	public void setIdConsecutivo(int idConsecutivo) {
		this.idConsecutivo = idConsecutivo;
	}
	public int getFolioRemision() {
		return folioRemision;
	}
	public void setFolioRemision(int folioRemision) {
		this.folioRemision = folioRemision;
	}
	
	
	

}
