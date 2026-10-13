package org.mx.modelo.comun.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GN_Tratados")
public class TratadoPO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3957328145977691582L;
	private int idTratado;
	private int numTratado;
	private String nombreTratado;
	private int subTitulo;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="PK_Tratado")
	public int getIdTratado() {
		return idTratado;
	}
	public void setIdTratado(int idTratado) {
		this.idTratado = idTratado;
	}
	
	@Column(name = "NumTratado")
	public int getNumTratado() {
		return numTratado;
	}
	public void setNumTratado(int numTratado) {
		this.numTratado = numTratado;
	}
	
	@Column(name = "NombreTratado")
	public String getNombreTratado() {
		return nombreTratado;
	}
	public void setNombreTratado(String nombreTratado) {
		this.nombreTratado = nombreTratado;
	}
	
	@Column(name = "SubTitulo")
	public int getSubTitulo() {
		return subTitulo;
	}
	public void setSubTitulo(int subTitulo) {
		this.subTitulo = subTitulo;
	}

}
