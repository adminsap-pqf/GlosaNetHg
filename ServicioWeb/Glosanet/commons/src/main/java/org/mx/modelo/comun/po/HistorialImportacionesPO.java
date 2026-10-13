package org.mx.modelo.comun.po;


import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Historial_Importaciones")
public class HistorialImportacionesPO  implements Serializable{
	

			
	private static final long serialVersionUID = 1297027981026494850L;
			private int pk_importaciones;
			private Integer fk01producto;
			private  double cantidad;
			private Integer fk02partidad;
			private Date fecha;
			private Integer fk03historialPermisos;

			/**
			 * 
			 * @return
			 */
			public HistorialImportacionesPO(int pk_importaciones, Integer fk01producto, double cantidad,
					Integer fk02partidad, Date fecha, Integer fk03historialPermisos, String observaciones,
					String numeropermiso, Set<HistorialImportacionesPO> lista) {
				this.pk_importaciones = pk_importaciones;
				this.fk01producto = fk01producto;
				this.cantidad = cantidad;
				this.fk02partidad = fk02partidad;
				this.fecha = fecha;
				this.fk03historialPermisos = fk03historialPermisos;

			}
			
			public HistorialImportacionesPO() {
			}
			

			@Id 
			@GeneratedValue
			@Column(name = "PK_Importaciones", unique = true, nullable = false)
			public int getPk_importaciones() {
				return pk_importaciones;
			}
			
			/**
			 * 
			 * @param pk_importaciones
			 */
			
			public void setPk_importaciones(int pk_importaciones) {
				this.pk_importaciones = pk_importaciones;
			}
			
			
			@Column(name="FK01_Producto", nullable=false)
			public Integer getFk01producto() {
				return fk01producto;
			}
			
			/**
			 * 
			 * @param fk01producto
			 */
			public void setFk01producto(Integer fk01producto) {
				this.fk01producto = fk01producto;
			}
			@Column(name = "cantidad")
			public double getCantidad() {
				return cantidad;
			}
			/**
			 * 
			 * @param cantidad
			 */
			public void setCantidad(double cantidad) {
				this.cantidad = cantidad;
			}
			
		
			@Column(name="FK02_Partida", nullable=false)
			public Integer getFk02partidad() {
				return fk02partidad;
			}
			/**
			 * 
			 * @param fk02partidad
			 */
			public void setFk02partidad(Integer fk02partidad) {
				this.fk02partidad = fk02partidad;
			}
			
			@Column(name = "Fecha")
			public Date getFecha() {
				return fecha;
			}

			public void setFecha(Date fecha) {
				this.fecha = fecha;
			}
			
		
			@Column(name="FK03_Historial_Permisos", nullable=false)
			public Integer getFk03historialPermisos() {
				return fk03historialPermisos;
			}
		

			/**
			 * 
			 * @param fk03historialPermisos
			 */
			public void setFk03historialPermisos(Integer fk03historialPermisos) {
				this.fk03historialPermisos = fk03historialPermisos;
			}
			
}
