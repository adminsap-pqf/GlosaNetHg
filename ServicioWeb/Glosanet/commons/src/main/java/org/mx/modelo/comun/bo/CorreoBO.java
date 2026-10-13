package org.mx.modelo.comun.bo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CorreoBO")
public class CorreoBO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1154888471568744574L;
	private Long idCorreo;
	private Date fechaInicio;
	private String origen;
	private Long destino;
	private String medio;
	private String FolioDocumento;
	private String correo;
	private String ccorreo;
	private String cocorreo;
	private String cuerpoCorreo;
	private String comentariosAdicionales;
	private String asunto;
	private String archivoAdjunto;
	private String tipo;
	private String nombreDestino;
	private String facturadaPor;
	private Long idContacto;
	private Long idPendiente;
	private Long idEmpleado;
	private String paisDestino;
	private String nombreOrigen;
	private Integer conFormato = 0;
	private String idEmpleadoString;
	public Long getIdCorreo() {
		return idCorreo;
	}
	public void setIdCorreo(Long idCorreo) {
		this.idCorreo = idCorreo;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public String getOrigen() {
		return origen;
	}
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	public Long getDestino() {
		return destino;
	}
	public void setDestino(Long destino) {
		this.destino = destino;
	}
	public String getMedio() {
		return medio;
	}
	public void setMedio(String medio) {
		this.medio = medio;
	}
	public String getFolioDocumento() {
		return FolioDocumento;
	}
	public void setFolioDocumento(String folioDocumento) {
		FolioDocumento = folioDocumento;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getCcorreo() {
		return ccorreo;
	}
	public void setCcorreo(String ccorreo) {
		this.ccorreo = ccorreo;
	}
	public String getCocorreo() {
		return cocorreo;
	}
	public void setCocorreo(String cocorreo) {
		this.cocorreo = cocorreo;
	}
	public String getCuerpoCorreo() {
		return cuerpoCorreo;
	}
	public void setCuerpoCorreo(String cuerpoCorreo) {
		this.cuerpoCorreo = cuerpoCorreo;
	}
	public String getComentariosAdicionales() {
		return comentariosAdicionales;
	}
	public void setComentariosAdicionales(String comentariosAdicionales) {
		this.comentariosAdicionales = comentariosAdicionales;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getArchivoAdjunto() {
		return archivoAdjunto;
	}
	public void setArchivoAdjunto(String archivoAdjunto) {
		this.archivoAdjunto = archivoAdjunto;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getNombreDestino() {
		return nombreDestino;
	}
	public void setNombreDestino(String nombreDestino) {
		this.nombreDestino = nombreDestino;
	}
	public String getFacturadaPor() {
		return facturadaPor;
	}
	public void setFacturadaPor(String facturadaPor) {
		this.facturadaPor = facturadaPor;
	}
	public Long getIdContacto() {
		return idContacto;
	}
	public void setIdContacto(Long idContacto) {
		this.idContacto = idContacto;
	}
	public Long getIdPendiente() {
		return idPendiente;
	}
	public void setIdPendiente(Long idPendiente) {
		this.idPendiente = idPendiente;
	}
	public Long getIdEmpleado() {
		return idEmpleado;
	}
	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}
	public String getPaisDestino() {
		return paisDestino;
	}
	public void setPaisDestino(String paisDestino) {
		this.paisDestino = paisDestino;
	}
	public String getNombreOrigen() {
		return nombreOrigen;
	}
	public void setNombreOrigen(String nombreOrigen) {
		this.nombreOrigen = nombreOrigen;
	}
	public Integer getConFormato() {
		return conFormato;
	}
	public void setConFormato(Integer conFormato) {
		this.conFormato = conFormato;
	}
	public String getIdEmpleadoString() {
		return idEmpleadoString;
	}
	public void setIdEmpleadoString(String idEmpleadoString) {
		this.idEmpleadoString = idEmpleadoString;
	}

}
