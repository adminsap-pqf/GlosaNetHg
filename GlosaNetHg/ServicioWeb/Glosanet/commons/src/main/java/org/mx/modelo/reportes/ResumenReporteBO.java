/**
 * 
 */
package org.mx.modelo.reportes;

/**
 * @author viviana.romero
 *
 */
public class ResumenReporteBO {
	
	private String region;
	private String pais;
	private String estado;
	private String clave;
	private Double montoTotal;
	private Double montoContribuciones;
	private Double porcentajeContribuciones;
	private int reporte;
	
	
	public ResumenReporteBO() {
		super();
	}
	
	

	/**
	 * @param region
	 * @param pais
	 * @param estado
	 * @param montoTotal
	 * @param montoContibuciones
	 * @param porcentajeContribuciones
	 * @param reporte
	 */
	public ResumenReporteBO(String region, String pais, String estado,
			Double montoTotal, Double montoContribuciones,
			Double porcentajeContribuciones, int reporte) {
		super();
		this.region = region;
		this.pais = pais;
		this.estado = estado;
		this.montoTotal = montoTotal;
		this.montoContribuciones = montoContribuciones;
		this.porcentajeContribuciones = porcentajeContribuciones;
		this.reporte = reporte;
	}



	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
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
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}


	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}


	/**
	 * @return the montoTotal
	 */
	public Double getMontoTotal() {
		return montoTotal;
	}

	/**
	 * @param montoTotal the montoTotal to set
	 */
	public void setMontoTotal(Double montoTotal) {
		this.montoTotal = montoTotal;
	}


	/**
	 * @return the montoContibuciones
	 */
	public Double getMontoContribuciones() {
		return montoContribuciones;
	}


	/**
	 * @param montoContibuciones the montoContibuciones to set
	 */
	public void setMontoContribuciones(Double montoContribuciones) {
		this.montoContribuciones = montoContribuciones;
	}


	/**
	 * @return the porcentajeContribuciones
	 */
	public Double getPorcentajeContribuciones() {
		return porcentajeContribuciones;
	}


	/**
	 * @param porcentajeContribuciones the porcentajeContribuciones to set
	 */
	public void setPorcentajeContribuciones(Double porcentajeContribuciones) {
		this.porcentajeContribuciones = porcentajeContribuciones;
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
	 * @return the clave
	 */
	public String getClave() {
		return clave;
	}



	/**
	 * @param clave the clave to set
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}

}
