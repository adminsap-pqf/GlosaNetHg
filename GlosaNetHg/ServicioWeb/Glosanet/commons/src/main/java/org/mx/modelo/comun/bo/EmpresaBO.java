/**
 * 
 */
package org.mx.modelo.comun.bo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "EmpresaBO")
public class EmpresaBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4993573491444761618L;
	private Integer idEmpresa;
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
	private Date vigencia;
	private ClientesBO cliente;
	private Integer numDataStage;
	private Date finicioDataStage;
	private Date ffinDataStage;
	private Date vigenciaMatriz;
	private Date vigenciaMatrizFin;
	private Date auditable;
	private String imagenEmpresa;
	private String representanteLegal;
	
	private byte[] img;

	
	/**
	 * 
	 */
	public EmpresaBO() {
		super();
	}

	/**
	 * @param idmpresa
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
	public EmpresaBO(Integer idmpresa, String razonSocial, String rfc,
			String pais, String estado, String calle, String delegacion,
			String municipio, String cp, String empresarial, String email,
			String telefono, boolean mseguridad, Date vigencia,
			ClientesBO cliente) {
		super();
		this.idEmpresa = idmpresa;
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
	}

	/**
	 * @return the idmpresa
	 */
	public Integer getIdEmpresa() {
		return idEmpresa;
	}

	/**
	 * @param idmpresa the idmpresa to set
	 */
	public void setIdEmpresa(Integer idmpresa) {
		this.idEmpresa = idmpresa;
	}

	/**
	 * @return the razonSocial
	 */
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
	 * @return the calle
	 */
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
	 * @return the mseguridad
	 */
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
	 * @return the vigencia
	 */
	public Date getVigencia() {
		return vigencia;
	}

	/**
	 * @param vigencia the vigencia to set
	 */
	public void setVigencia(Date vigencia) {
		this.vigencia = vigencia;
	}

	/**
	 * @return the cliente
	 */
	public ClientesBO getCliente() {
		return cliente;
	}

	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(ClientesBO cliente) {
		this.cliente = cliente;
	}

	/**
	 * @return the numDataStage
	 */
	public Integer getNumDataStage() {
		return numDataStage;
	}

	/**
	 * @param numDataStage the numDataStage to set
	 */
	public void setNumDataStage(Integer numDataStage) {
		this.numDataStage = numDataStage;
	}

	/**
	 * @return the vigenciaMatriz
	 */
	public Date getVigenciaMatriz() {
		return vigenciaMatriz;
	}

	/**
	 * @param vigenciaMatriz the vigenciaMatriz to set
	 */
	public void setVigenciaMatriz(Date vigenciaMatriz) {
		this.vigenciaMatriz = vigenciaMatriz;
	}

	/**
	 * @return the auditable
	 */
	public Date getAuditable() {
		return auditable;
	}

	/**
	 * @param auditable the auditable to set
	 */
	public void setAuditable(Date auditable) {
		this.auditable = auditable;
	}

	/**
	 * @return the finicioDataStage
	 */
	public Date getFinicioDataStage() {
		return finicioDataStage;
	}

	/**
	 * @param finicioDataStage the finicioDataStage to set
	 */
	public void setFinicioDataStage(Date finicioDataStage) {
		this.finicioDataStage = finicioDataStage;
	}

	/**
	 * @return the ffinDataStage
	 */
	public Date getFfinDataStage() {
		return ffinDataStage;
	}

	/**
	 * @param ffinDataStage the ffinDataStage to set
	 */
	public void setFfinDataStage(Date ffinDataStage) {
		this.ffinDataStage = ffinDataStage;
	}

	/**
	 * @return the imagenEmpresa
	 */
	public String getImagenEmpresa() {
		return imagenEmpresa;
	}

	/**
	 * @param imagenEmpresa the imagenEmpresa to set
	 */
	public void setImagenEmpresa(String imagenEmpresa) {
		this.imagenEmpresa = imagenEmpresa;
	}

	/**
	 * @return the representanteLegal
	 */
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
	public Date getVigenciaMatrizFin() {
		return vigenciaMatrizFin;
	}

	/**
	 * @param vigenciaMatrizFin the vigenciaMatrizFin to set
	 */
	public void setVigenciaMatrizFin(Date vigenciaMatrizFin) {
		this.vigenciaMatrizFin = vigenciaMatrizFin;
	}

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}
	
	
}
