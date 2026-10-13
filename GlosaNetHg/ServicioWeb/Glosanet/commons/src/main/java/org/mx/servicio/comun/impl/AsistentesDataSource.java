package org.mx.servicio.comun.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;


public class AsistentesDataSource implements JRDataSource {
	
	
	private List<String> listaAsistentes = new ArrayList<String>();
	private int indiceParticipanteActual = -1;

	@Override
	public boolean next() throws JRException {
		return ++indiceParticipanteActual < listaAsistentes.size();
	}

	public void addAsistente(String Asistente){

		this.listaAsistentes.add(Asistente);

	}

	@Override
	public Object getFieldValue(JRField jrf) throws JRException {
		Object valor = null;
		return valor;
	}

}
