package org.mx.modelo.comun.bo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ApendiceContenidoBO")
public class ApendiceContenidoBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2342306685751097023L;
	private int idApendiceContenido;
	private ApendiceBO apendice;
	private int renglon;
	private int orden;
	private String column1;
	private String column2;
	private String column3;
	private String column4;
	private String style;
	
	public int getIdApendiceContenido() {
		return idApendiceContenido;
	}
	public void setIdApendiceContenido(int idApendiceContenido) {
		this.idApendiceContenido = idApendiceContenido;
	}
	public ApendiceBO getApendice() {
		return apendice;
	}
	public void setApendice(ApendiceBO apendice) {
		this.apendice = apendice;
	}
	public int getRenglon() {
		return renglon;
	}
	public void setRenglon(int renglon) {
		this.renglon = renglon;
	}
	public int getOrden() {
		return orden;
	}
	public void setOrden(int orden) {
		this.orden = orden;
	}
	public String getColumn1() {
		return column1;
	}
	public void setColumn1(String column1) {
		this.column1 = column1;
	}
	public String getColumn2() {
		return column2;
	}
	public void setColumn2(String column2) {
		this.column2 = column2;
	}
	public String getColumn3() {
		return column3;
	}
	public void setColumn3(String column3) {
		this.column3 = column3;
	}
	public String getColumn4() {
		return column4;
	}
	public void setColumn4(String column4) {
		this.column4 = column4;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}

}
