package org.mx.modelo.comun.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "Consecutivos")
public class ConsecutivosPO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -768826086216822061L;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="PK_Consecutivos")
	private int idConsecutivos;
	
	@Column(name="FolioRemision") 
	private int folioRemision;

	public int getIdConsecutivos() {
		return idConsecutivos;
	}

	public void setIdConsecutivos(int idConsecutivos) {
		this.idConsecutivos = idConsecutivos;
	}

	public int getFolioRemision() {
		return folioRemision;
	}

	public void setFolioRemision(int folioRemision) {
		this.folioRemision = folioRemision;
	}
	
	

}
