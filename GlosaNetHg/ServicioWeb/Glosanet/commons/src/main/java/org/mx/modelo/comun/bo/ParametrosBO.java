/**
 * 
 */
package org.mx.modelo.comun.bo;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Parametros")
public class ParametrosBO {

	private UsuarioBO usuario;
	
	//Fechas
	private int year;
	private int semestre;
	private int trimestre;
	private String mes;
	private Date finicio;
	private Date ffin;
	private String tipo;
	
	//Reportes
	private int reporte;
	private Boolean importacionDefinitiva;
	
	//Direccion
	private String pais;
	private String cp;
	
	
	/**
	 * 
	 */
	public ParametrosBO() {
		super();
		this.reporte = 0;
		this.importacionDefinitiva = true;
		this.semestre = 0;
		this.trimestre = 0;
		this.mes = "";
	}
	
	

	/**
	 * @param cliente
	 * @param usuario
	 * @param year
	 * @param semestre
	 * @param trimestre
	 * @param mes
	 * @param finicio
	 * @param ffin
	 * @param tipo
	 * @param reporte
	 * @param importacionDefinitiva
	 */
	public ParametrosBO( UsuarioBO usuario, int year, int semestre,
			int trimestre, String mes, Date finicio, Date ffin, String tipo,
			int reporte, Boolean importacionDefinitiva) {
		super();
		this.usuario = usuario;
		this.year = year;
		this.semestre = semestre;
		this.trimestre = trimestre;
		this.mes = mes;
		this.finicio = finicio;
		this.ffin = ffin;
		this.tipo = tipo;
		this.reporte = reporte;
		this.importacionDefinitiva = importacionDefinitiva;
	}

	/**
	 * @return the usuario
	 */
	public UsuarioBO getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(UsuarioBO usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * @return the semestre
	 */
	public int getSemestre() {
		return semestre;
	}

	/**
	 * @param semestre the semestre to set
	 */
	public void setSemestre(int semestre) {
		this.semestre = semestre;
	}

	/**
	 * @return the trimestre
	 */
	public int getTrimestre() {
		return trimestre;
	}

	/**
	 * @param trimestre the trimestre to set
	 */
	public void setTrimestre(int trimestre) {
		this.trimestre = trimestre;
	}

	/**
	 * @return the mes
	 */
	public String getMes() {
		return mes;
	}

	/**
	 * @param mes the mes to set
	 */
	public void setMes(String mes) {
		this.mes = mes;
	}

	/**
	 * @return the finicio
	 */
	public Date getFinicio() {
		return finicio;
	}

	/**
	 * @param finicio the finicio to set
	 */
	public void setFinicio(Date finicio) {
		this.finicio = finicio;
	}

	/**
	 * @return the ffin
	 */
	public Date getFfin() {
		return ffin;
	}

	/**
	 * @param ffin the ffin to set
	 */
	public void setFfin(Date ffin) {
		this.ffin = ffin;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the reporte
	 */
	public int getReporte() {
		return reporte;
	}

	/**
	 * @param reporte the reporte to set
	 */
	public void setReporte(int reporte) {
		this.reporte = reporte;
	}

	/**
	 * @return the importacionDefinitiva
	 */
	public Boolean getImportacionDefinitiva() {
		return importacionDefinitiva;
	}

	/**
	 * @param importacionDefinitiva the importacionDefinitiva to set
	 */
	public void setImportacionDefinitiva(Boolean importacionDefinitiva) {
		this.importacionDefinitiva = importacionDefinitiva;
	}



	/**
	 * @return the pais
	 */
	public String getPais() {
		return pais;
	}



	/**
	 * @param pais the pais to set
	 */
	public void setPais(String pais) {
		this.pais = pais;
	}



	/**
	 * @return the cp
	 */
	public String getCp() {
		return cp;
	}



	/**
	 * @param cp the cp to set
	 */
	public void setCp(String cp) {
		this.cp = cp;
	}
	
}
