package org.mx.modelo.comun.bo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "TratadoContenidoBO")
public class TratadoContenidoBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3653200488765718219L;
	private int idTratadoContenido;
	private TratadoBO tratado;
	private int renglon;
	private String column1;
	private String column2;
	private String style;
	
	public int getIdTratadoContenido() {
		return idTratadoContenido;
	}
	public void setIdTratadoContenido(int idTratadoContenido) {
		this.idTratadoContenido = idTratadoContenido;
	}
	public TratadoBO getTratado() {
		return tratado;
	}
	public void setTratado(TratadoBO tratado) {
		this.tratado = tratado;
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
