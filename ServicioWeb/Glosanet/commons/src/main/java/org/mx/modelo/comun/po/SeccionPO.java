package org.mx.modelo.comun.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CO_Seccion")
public class SeccionPO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3564800309408827909L;
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="PK_Seccion")
	private int idSeccion;
	@Column(name="Nombre")
	private String nombre;
	
	
	public int getIdSeccion() {
		return idSeccion;
	}
	public void setIdSeccion(int idSeccion) {
		this.idSeccion = idSeccion;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
