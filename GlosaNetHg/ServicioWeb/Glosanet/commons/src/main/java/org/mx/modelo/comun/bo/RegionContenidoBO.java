package org.mx.modelo.comun.bo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RegionContenidoBO")
public class RegionContenidoBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4252837440969869322L;
	private int idRegionContenido;
	private RegionesBO region;
	private int renglon;
	private String column1;
	private String column2;
	private String style;
	public int getIdRegionContenido() {
		return idRegionContenido;
	}
	public void setIdRegionContenido(int idRegionContenido) {
		this.idRegionContenido = idRegionContenido;
	}
	public RegionesBO getRegion() {
		return region;
	}
	public void setRegion(RegionesBO region) {
		this.region = region;
	}
	public int getRenglon() {
		return renglon;
	}
	public void setRenglon(int renglon) {
		this.renglon = renglon;
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
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	
	
}
