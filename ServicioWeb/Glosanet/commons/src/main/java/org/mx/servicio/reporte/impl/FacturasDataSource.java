package org.mx.servicio.reporte.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class FacturasDataSource implements JRDataSource {

	private List<String> lista = new ArrayList<String>();
	private int indiceActual = -1;
	

	@Override
	public boolean next() throws JRException {
		return ++indiceActual < lista.size();
	}
	
	public void addClienteBO(String cliente){
		this.lista.add(cliente);
	}
	
	@Override
	public Object getFieldValue(JRField jrf) throws JRException {
		Object valor=null;
		return valor;
	}
	
}
