package org.mx.servicio.comun.impl;

import java.util.ArrayList;
import java.util.List;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.ApendiceBO;
import org.mx.modelo.comun.bo.ApendiceContenidoBO;
import org.mx.modelo.comun.bo.TratadoBO;
import org.mx.modelo.comun.bo.TratadoContenidoBO;
import org.mx.modelo.comun.bo.RegionesBO;
import org.mx.modelo.comun.bo.RegionContenidoBO;
import org.mx.modelo.gaia.Country;
import org.mx.persistencia.comun.GlosarioDAO;
import org.mx.servicio.comun.GlosarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GlosarioServiceImpl implements GlosarioService{

	@Autowired
	GlosarioDAO glosarioDAO;

	@Override
	public List<ApendiceBO> obtenerApendices() throws GlosaNetException {
		return glosarioDAO.getApendices();
	}

	@Override
	public List<TratadoBO> obtenerTratados() throws GlosaNetException {
		return glosarioDAO.getTratados();
	}

	@Override
	public List<RegionesBO> obtenerRegiones() throws GlosaNetException {
		return glosarioDAO.getRegiones();
	}

	@Override
	public List<ApendiceContenidoBO> obtenerApendiceContenido(int idApendice, int orden) throws GlosaNetException {
		return glosarioDAO.getApendicesContenido(idApendice, orden);
	}

	@Override
	public List<TratadoContenidoBO> obtenerTratadoContenido(int idApendice) throws GlosaNetException {
		return glosarioDAO.getTratadoContenido(idApendice);
	}

	@Override
	public List<RegionContenidoBO> obtenerRegionContenido(int idApendice) throws GlosaNetException {
		return glosarioDAO.getRegionContenido(idApendice);
	}

	@Override
	public List<String> obtenerPaises() throws GlosaNetException {
		List<Country> all = Country.getAll();
		List<String> r = new ArrayList<String>();
		for(Country a : all){
			r.add(a.getIsoAlpha3());
		}
		
		return r;
	}
}
