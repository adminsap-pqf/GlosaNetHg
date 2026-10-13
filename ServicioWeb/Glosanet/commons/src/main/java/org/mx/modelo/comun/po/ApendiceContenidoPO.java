package org.mx.modelo.comun.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "GN_Apendice_Contenido")
public class ApendiceContenidoPO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7214583703730624230L;
	private int idApendiceContenido;
	private ApendicePO apendice;
	private int renglon;
	private int orden;
	private String column1;
	private String column2;
	private String column3;
	private String column4;
	private String style;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="PK_ApendiceContenido")
	public int getIdApendiceContenido() {
		return idApendiceContenido;
	}
	public void setIdApendiceContenido(int idApendiceContenido) {
		this.idApendiceContenido = idApendiceContenido;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="FK01_Apendice")
	public ApendicePO getApendice() {
		return apendice;
	}
	public void setApendice(ApendicePO apendice) {
		this.apendice = apendice;
	}
	
	@Column(name = "Renglon")
	public int getRenglon() {
		return renglon;
	}
	public void setRenglon(int renglon) {
		this.renglon = renglon;
	}
	
	@Column(name = "Orden")
	public int getOrden() {
		return orden;
	}
	public void setOrden(int orden) {
		this.orden = orden;
	}
	
	@Column(name = "Column1")
	public String getColumn1() {
		return column1;
	}
	public void setColumn1(String column1) {
		this.column1 = column1;
	}
	
	@Column(name = "Column2")
	public String getColumn2() {
		return column2;
	}
	public void setColumn2(String column2) {
		this.column2 = column2;
	}
	
	@Column(name = "Column3")
	public String getColumn3() {
		return column3;
	}
	public void setColumn3(String column3) {
		this.column3 = column3;
	}
	
	@Column(name = "Column4")
	public String getColumn4() {
		return column4;
	}
	public void setColumn4(String column4) {
		this.column4 = column4;
	}
	
	@Column(name = "Style")
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}	
}
