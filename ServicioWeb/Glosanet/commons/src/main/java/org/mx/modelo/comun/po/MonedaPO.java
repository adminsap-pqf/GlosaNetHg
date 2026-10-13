package org.mx.modelo.comun.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Monedas")
public class MonedaPO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7576997032221266636L;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="PK_Moneda")
	private int idMoneda;
	
	@Column(name="Simbolo")
	private String simbolo;
	
	@Column(name="SimboloNativo")
	private String simboloNativo;
	
	@Column(name="NombreEN")
	private String nombreEn;
	
	@Column(name="NombreES")
	private String nombreEs;
	
	@Column(name="DigitosDecimales")
	private int digitosDecimales;
	
	@Column(name="Redondeo")
	private int redondeo;
	
	@Column(name="Codigo")
	private String codigo;
	
	@Column(name="DineroSeparadorMiles")
	private String dineroSeparadorMiles;
	
	@Column(name="DineroSeparadorDecimales")
	private String dineroSeparadorDecimales;
	
	@Column(name="NumSeparadorMiles")
	private String numSeparadorMiles;
	
	@Column(name="NumSeparadorDecimales")
	private String numSeparadorDecimales;
	
	@Column(name="Activo")
	private boolean activo;
	
	@Column(name="NombrePluralEN")
	private String nombrePluralEn;
	
	@Column(name="NombrePluralES")
	private String nombrePluralEs;
	
	public MonedaPO(){}

	public int getIdMoneda() {
		return idMoneda;
	}

	public void setIdMoneda(int idMoneda) {
		this.idMoneda = idMoneda;
	}

	public String getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

	public String getSimboloNativo() {
		return simboloNativo;
	}

	public void setSimboloNativo(String simboloNativo) {
		this.simboloNativo = simboloNativo;
	}

	public String getNombreEn() {
		return nombreEn;
	}

	public void setNombreEn(String nombreEn) {
		this.nombreEn = nombreEn;
	}

	public String getNombreEs() {
		return nombreEs;
	}

	public void setNombreEs(String nombreEs) {
		this.nombreEs = nombreEs;
	}

	public int getDigitosDecimales() {
		return digitosDecimales;
	}

	public void setDigitosDecimales(int digitosDecimales) {
		this.digitosDecimales = digitosDecimales;
	}

	public int getRedondeo() {
		return redondeo;
	}

	public void setRedondeo(int redondeo) {
		this.redondeo = redondeo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDineroSeparadorMiles() {
		return dineroSeparadorMiles;
	}

	public void setDineroSeparadorMiles(String dineroSeparadorMiles) {
		this.dineroSeparadorMiles = dineroSeparadorMiles;
	}

	public String getDineroSeparadorDecimales() {
		return dineroSeparadorDecimales;
	}

	public void setDineroSeparadorDecimales(String dineroSeparadorDecimales) {
		this.dineroSeparadorDecimales = dineroSeparadorDecimales;
	}

	public String getNumSeparadorMiles() {
		return numSeparadorMiles;
	}

	public void setNumSeparadorMiles(String numSeparadorMiles) {
		this.numSeparadorMiles = numSeparadorMiles;
	}

	public String getNumSeparadorDecimales() {
		return numSeparadorDecimales;
	}

	public void setNumSeparadorDecimales(String numSeparadorDecimales) {
		this.numSeparadorDecimales = numSeparadorDecimales;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public String getNombrePluralEn() {
		return nombrePluralEn;
	}

	public void setNombrePluralEn(String nombrePluralEn) {
		this.nombrePluralEn = nombrePluralEn;
	}

	public String getNombrePluralEs() {
		return nombrePluralEs;
	}

	public void setNombrePluralEs(String nombrePluralEs) {
		this.nombrePluralEs = nombrePluralEs;
	}

}
