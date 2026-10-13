package org.mx.servicio.comun.impl;

import java.util.List;

import org.mx.modelo.comun.bo.CedulaBO;
import org.mx.persistencia.comun.CedulaDAO;
import org.mx.servicio.comun.CedulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CedulaServiceImpl implements CedulaService{
	
	@Autowired
	private CedulaDAO cedulaDAO;

	public List<CedulaBO> totalProductos(int idCliente, String fechaInicio, String fechaFin) throws Exception{
		return cedulaDAO.totalProductos(idCliente, fechaInicio, fechaFin);
	}
	
	public List<CedulaBO> fraccionOrigen(int idCliente, String fechaInicio, String fechaFin) throws Exception{
		return cedulaDAO.fraccionOrigen(idCliente, fechaInicio, fechaFin);
	}
	
	public List<CedulaBO> totalContribuciones(int idCliente, String fechaInicio, String fechaFin) throws Exception{
		return cedulaDAO.totalContribuciones(idCliente, fechaInicio, fechaFin);
	}
	
	public List<CedulaBO> resumenContribuciones(int idCliente, String fechaInicio, String fechaFin) throws Exception{
		return cedulaDAO.resumenContribuciones(idCliente, fechaInicio, fechaFin);
	}
	
	public List<CedulaBO> incoterms(int idCliente, String fechaInicio, String fechaFin) throws Exception{
		return cedulaDAO.incoterms(idCliente, fechaInicio, fechaFin);
	}
	
	public List<CedulaBO> facturacionExw(int idCliente, String fechaInicio, String fechaFin) throws Exception{
		return cedulaDAO.facturacionExw(idCliente, fechaInicio, fechaFin);
	}
}
