package org.mx.modelo.datastage.po;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Partidas551Id entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class PartidasPOId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 3557877286088665714L;
	private String patente;
	private String pedimento;
	private String seccionAduanera;
	private String fraccion;
	private Integer secuenciaFraccion;

	// Constructors

	/** default constructor */
	public PartidasPOId() {
	}

	/** full constructor */
	public PartidasPOId(String patente, String pedimento,
			String seccionAduanera, String fraccion, Integer secuenciaFraccion) {
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
		this.fraccion = fraccion;
		this.secuenciaFraccion = secuenciaFraccion;
	}

	// Property accessors

	@Column(name = "Patente", nullable = false, length = 4)
	public String getPatente() {
		return this.patente;
	}

	public void setPatente(String patente) {
		this.patente = patente;
	}

	@Column(name = "Pedimento", nullable = false, length = 7)
	public String getPedimento() {
		return this.pedimento;
	}

	public void setPedimento(String pedimento) {
		this.pedimento = pedimento;
	}

	@Column(name = "SeccionAduanera", nullable = false, length = 3)
	public String getSeccionAduanera() {
		return this.seccionAduanera;
	}

	public void setSeccionAduanera(String seccionAduanera) {
		this.seccionAduanera = seccionAduanera;
	}

	@Column(name = "Fraccion", nullable = false, length = 8)
	public String getFraccion() {
		return this.fraccion;
	}

	public void setFraccion(String fraccion) {
		this.fraccion = fraccion;
	}

	@Column(name = "SecuenciaFraccion", nullable = false)
	public Integer getSecuenciaFraccion() {
		return this.secuenciaFraccion;
	}

	public void setSecuenciaFraccion(Integer secuenciaFraccion) {
		this.secuenciaFraccion = secuenciaFraccion;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PartidasPOId))
			return false;
		PartidasPOId castOther = (PartidasPOId) other;

		return ((this.getPatente() == castOther.getPatente()) || (this
				.getPatente() != null && castOther.getPatente() != null && this
				.getPatente().equals(castOther.getPatente())))
				&& ((this.getPedimento() == castOther.getPedimento()) || (this
						.getPedimento() != null
						&& castOther.getPedimento() != null && this
						.getPedimento().equals(castOther.getPedimento())))
				&& ((this.getSeccionAduanera() == castOther
						.getSeccionAduanera()) || (this.getSeccionAduanera() != null
						&& castOther.getSeccionAduanera() != null && this
						.getSeccionAduanera().equals(
								castOther.getSeccionAduanera())))
				&& ((this.getFraccion() == castOther.getFraccion()) || (this
						.getFraccion() != null
						&& castOther.getFraccion() != null && this
						.getFraccion().equals(castOther.getFraccion())))
				&& ((this.getSecuenciaFraccion() == castOther
						.getSecuenciaFraccion()) || (this
						.getSecuenciaFraccion() != null
						&& castOther.getSecuenciaFraccion() != null && this
						.getSecuenciaFraccion().equals(
								castOther.getSecuenciaFraccion())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getPatente() == null ? 0 : this.getPatente().hashCode());
		result = 37 * result
				+ (getPedimento() == null ? 0 : this.getPedimento().hashCode());
		result = 37
				* result
				+ (getSeccionAduanera() == null ? 0 : this.getSeccionAduanera()
						.hashCode());
		result = 37 * result
				+ (getFraccion() == null ? 0 : this.getFraccion().hashCode());
		result = 37
				* result
				+ (getSecuenciaFraccion() == null ? 0 : this
						.getSecuenciaFraccion().hashCode());
		return result;
	}

}