package org.mx.modelo.comun.bo;


import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "HistorialImportacionesBO")
public class HistorialImportacionesBO implements Serializable{
	
		private static final long serialVersionUID = -4575831981437998899L;
		private Integer fk01producto;
		private  double cantidad;
		private Integer fk02partidad;
		private  String fecha;
		private Integer fk03historialPermisos;
		private String observaciones;
		private String numeropermiso;
		private Integer idpermiso;
		private String unidad;
		private String  totalImportaciones;
		private String importacionVigente;
		private boolean flagProductoPartidas;
		
		

		

		public HistorialImportacionesBO() {
			
		}
		
		
		
		
		
		
		
		
		
		public Integer getIdpermiso() {
			return idpermiso;
		}









		public void setIdpermiso(Integer idpermiso) {
			this.idpermiso = idpermiso;
		}









		public boolean isFlagProductoPartidas() {
			return flagProductoPartidas;
		}








		public void setFlagProductoPartidas(boolean flagProductoPartidas) {
			this.flagProductoPartidas = flagProductoPartidas;
		}








		public String getImportacionVigente() {
			return importacionVigente;
		}





		public void setImportacionVigente(String importacionVigente) {
			this.importacionVigente = importacionVigente;
		}





		/**
		 * 
		 * @return
		 */

		public String getTotalImportaciones() {
			return totalImportaciones;
		}


		/**
		 * 
		 * @param totalImportaciones
		 */

		public void setTotalImportaciones(String totalImportaciones) {
			this.totalImportaciones = totalImportaciones;
		}


		/**
		 * 
		 * @return
		 */
		public String getUnidad() {
			return unidad;
		}
		/**
		 * 
		 * @param unidad
		 */
		public void setUnidad(String unidad) {
			this.unidad = unidad;
		}
		
		

		/**
		 * 
		 * @return
		 */
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
		/**
		 * 
		 * @return
		 */
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
		/**
		 * 
		 * @return
		 */
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


		
		/**
		 * 
		 * @return
		 */
		public Integer getFk03historialPermisos() {
			return fk03historialPermisos;
		}

		/**
		 * 
		 * @return
		 */
		public String getFecha() {
			return fecha;
		}

		/**
		 * 
		 * @param fecha
		 */
		public void setFecha(String fecha) {
			this.fecha = fecha;
		}

		/**
		 * 
		 * @param fk03historialPermisos
		 */
		public void setFk03historialPermisos(Integer fk03historialPermisos) {
			this.fk03historialPermisos = fk03historialPermisos;
		}
		/**
		 * 
		 * @return
		 */
		public String getObservaciones() {
			return observaciones;
		}
		/**
		 * 
		 * @param observaciones
		 */
		public void setObservaciones(String observaciones) {
			this.observaciones = observaciones;
		}
		/**
		 * 
		 * @return
		 */
		public String getNumeropermiso() {
			return numeropermiso;
		}
		/**
		 * 
		 * @param numeropermiso
		 */
		public void setNumeropermiso(String numeropermiso) {
			this.numeropermiso = numeropermiso;
		}
		/**
		 * 
		 * @return
		 */

		
		
		
	

}
