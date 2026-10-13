package org.mx.persistencia.comun;

import java.text.ParseException;
import java.util.List;

import org.mx.modelo.comun.bo.CedulaBO;

public interface CedulaDAO {
	
	/***
	 * 
	 * @return
	 */
	public List<CedulaBO> totalProductos(int idCliente, String fechaInicio, String fechaFin) throws ParseException ;
	
	/***
	 * 
	 * @return
	 */
	public List<CedulaBO> fraccionOrigen(int idCliente, String fechaInicio, String fechaFin) throws ParseException ;
	
	/***
	 * 
	 * @return
	 */
	public List<CedulaBO> totalContribuciones(int idCliente, String fechaInicio, String fechaFin) throws ParseException ;
	
	/***
	 * 
	 * @return
	 */
	public List<CedulaBO> resumenContribuciones(int idCliente, String fechaInicio, String fechaFin) throws ParseException ;
	
	/***
	 * 
	 * @return
	 */
	public List<CedulaBO> incoterms(int idCliente, String fechaInicio, String fechaFin) throws ParseException ;
	
	/***
	 * 
	 * @return
	 */
	public List<CedulaBO> facturacionExw(int idCliente, String fechaInicio, String fechaFin) throws ParseException ;
}
