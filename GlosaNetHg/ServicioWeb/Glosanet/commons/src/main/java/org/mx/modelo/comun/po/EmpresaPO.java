/**
 * 
 */
package org.mx.modelo.comun.po;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author yosimar.mendez
 *
 */
@Entity
@Table(name = "GN_Empresa")
public class EmpresaPO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 849859345079717790L;
	
	private Integer pkEmpresa;
	private String razonSocial;
	private String rfc;
	private String pais;
	private String estado;
	private String calle;
	private String delegacion;
	private String municipio;
	private String cp;
	private String empresarial;
	private String email;
	private String telefono;
	private boolean  mseguridad;
	private Timestamp vigencia;
	private Timestamp vigenciaMatriz;
	private Timestamp vigenciaMatrizFin;
	private Timestamp auditable;
	private ClientesPO cliente;
	private String representanteLegal;
	
	/**
	 * 
	 */
	public EmpresaPO() {
		super();
	}

	/**
	 * @param pkEmpresa
	 * @param razonSocial
	 * @param rfc
	 * @param pais
	 * @param estado
	 * @param calle
	 * @param delegacion
	 * @param municipio
	 * @param cp
	 * @param empresarial
	 * @param email
	 * @param telefono
	 * @param mseguridad
	 * @param vigencia
	 * @param cliente
	 */
	public EmpresaPO(Integer pkEmpresa, String razonSocial, String rfc,
			String pais, String estado, String calle, String delegacion,
			String municipio, String cp, String empresarial, String email,
			String telefono, boolean mseguridad, Timestamp vigencia,
			String representanteLegal, ClientesPO cliente) {
		super();
		this.pkEmpresa = pkEmpresa;
		this.razonSocial = razonSocial;
		this.rfc = rfc;
		this.pais = pais;
		this.estado = estado;
		this.calle = calle;
		this.delegacion = delegacion;
		this.municipio = municipio;
		this.cp = cp;
		this.empresarial = empresarial;
		this.email = email;
		this.telefono = telefono;
		this.mseguridad = mseguridad;
		this.vigencia = vigencia;
		this.cliente = cliente;
		this.representanteLegal = representanteLegal;
	}

	/**
	 * @return the pkEmpresa
	 */
	@Id
	@GeneratedValue
	@Column(name = "PK_Empresa", unique = true, nullable = false)
	public Integer getPkEmpresa() {
		return pkEmpresa;
	}

	/**
	 * @param pkEmpresa the pkEmpresa to set
	 */
	public void setPkEmpresa(Integer pkEmpresa) {
		this.pkEmpresa = pkEmpresa;
	}

	/**
	 * @return the razonSocial
	 */
	@Column(name = "RazonSocial", length = 200)
	public String getRazonSocial() {
		return razonSocial;
	}

	/**
	 * @param razonSocial the razonSocial to set
	 */
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	/**
	 * @return the rfc
	 */
	@Column(name = "RFC", length = 15)
	public String getRfc() {
		return rfc;
	}

	/**
	 * @param rfc the rfc to set
	 */
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	/**
	 * @return the pais
	 */
	@Column(name = "Pais", length = 50)
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
	@Column(name = "Estado", length = 50)
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
	 * @return the calle
	 */
	@Column(name = "Calle", length = 1000)
	public String getCalle() {
		return calle;
	}

	/**
	 * @param calle the calle to set
	 */
	public void setCalle(String calle) {
		this.calle = calle;
	}

	/**
	 * @return the delegacion
	 */
	@Column(name = "Delegacion", length = 80)
	public String getDelegacion() {
		return delegacion;
	}

	/**
	 * @param delegacion the delegacion to set
	 */
	public void setDelegacion(String delegacion) {
		this.delegacion = delegacion;
	}

	/**
	 * @return the municipio
	 */
	@Column(name = "Municipio", length = 80)
	public String getMunicipio() {
		return municipio;
	}

	/**
	 * @param municipio the municipio to set
	 */
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	/**
	 * @return the cp
	 */
	@Column(name = "CP", length = 10)
	public String getCp() {
		return cp;
	}

	/**
	 * @param cp the cp to set
	 */
	public void setCp(String cp) {
		this.cp = cp;
	}

	/**
	 * @return the empresarial
	 */
	@Column(name = "AEmpresarial", length = 100)
	public String getEmpresarial() {
		return empresarial;
	}

	/**
	 * @param empresarial the empresarial to set
	 */
	public void setEmpresarial(String empresarial) {
		this.empresarial = empresarial;
	}

	/**
	 * @return the email
	 */
	@Column(name = "Email", length = 50)
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the telefono
	 */
	@Column(name = "telefono", length = 20)
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return the vigencia
	 */
	@Column(name = "Vigencia", length = 23)
	public Timestamp getVigencia() {
		return vigencia;
	}

	/**
	 * @param vigencia the vigencia to set
	 */
	public void setVigencia(Timestamp vigencia) {
		this.vigencia = vigencia;
	}

	/**
	 * @return the cliente
	 */
	@ManyToOne
	@JoinColumn(name = "FK_Cliente", nullable = false)
	public ClientesPO getCliente() {
		return cliente;
	}

	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(ClientesPO cliente) {
		this.cliente = cliente;
	}

	/**
	 * @return the mseguridad
	 */
	@Column(name = "MSeguridad")
	public boolean isMseguridad() {
		return mseguridad;
	}

	/**
	 * @param mseguridad the mseguridad to set
	 */
	public void setMseguridad(boolean mseguridad) {
		this.mseguridad = mseguridad;
	}

	/**
	 * @return the vigenciaMatriz
	 */
	@Column(name = "VigenciaMatriz", length = 23)
	public Timestamp getVigenciaMatriz() {
		return vigenciaMatriz;
	}

	/**
	 * @param vigenciaMatriz the vigenciaMatriz to set
	 */
	public void setVigenciaMatriz(Timestamp vigenciaMatriz) {
		this.vigenciaMatriz = vigenciaMatriz;
	}

	/**
	 * @return the auditable
	 */
	@Column(name = "Auditable", length = 23)
	public Timestamp getAuditable() {
		return auditable;
	}

	/**
	 * @param auditable the auditable to set
	 */
	public void setAuditable(Timestamp auditable) {
		this.auditable = auditable;
	}

	/**
	 * @return the representanteLegal
	 */
	@Column(name = "Representante", length = 100)
	public String getRepresentanteLegal() {
		return representanteLegal;
	}

	/**
	 * @param representanteLegal the representanteLegal to set
	 */
	public void setRepresentanteLegal(String representanteLegal) {
		this.representanteLegal = representanteLegal;
	}

	/**
	 * @return the vigenciaMatrizFin
	 */
	@Column(name = "VigenciaMatrizFin", length = 23)
	public Timestamp getVigenciaMatrizFin() {
		return vigenciaMatrizFin;
	}

	/**
	 * @param vigenciaMatrizFin the vigenciaMatrizFin to set
	 */
	public void setVigenciaMatrizFin(Timestamp vigenciaMatrizFin) {
		this.vigenciaMatrizFin = vigenciaMatrizFin;
	}

}
