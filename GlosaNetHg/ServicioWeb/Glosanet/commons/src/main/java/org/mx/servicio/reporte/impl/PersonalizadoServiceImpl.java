package org.mx.servicio.reporte.impl;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jumpmind.symmetric.csv.CsvWriter;
import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.DescargaBO;
import org.mx.modelo.comun.bo.Parametros;
import org.mx.modelo.reportes.bo.CampoTablaBO;
import org.mx.modelo.reportes.bo.PReportesPersonalizadosBO;
import org.mx.modelo.reportes.bo.ReportePersonalizadoBO;
import org.mx.modelo.reportes.bo.TablaDataStageBO;
import org.mx.modelo.util.Util;
import org.mx.modelo.util.UtilFechas;
import org.mx.persistencia.comun.DescargaDAO;
import org.mx.persistencia.comun.UsuarioDAO;
import org.mx.persistencia.reporte.PersonalizadoDAO;
import org.mx.servicio.reporte.PersonalizadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonalizadoServiceImpl implements PersonalizadoService {
	private final static Logger log = Logger.getLogger(PersonalizadoServiceImpl.class);

	UtilFechas u;

	@Autowired
	PersonalizadoDAO personalizadoDAO;
	@Autowired
	DescargaDAO descargaDAO;
	@Autowired
	UsuarioDAO usuarioDAO;

	@Override
	public List<TablaDataStageBO> obtenerTablasDataStage(String idioma, String condicion)
			throws GlosaNetException {
		try{
			String cond = "";
			if(condicion.equalsIgnoreCase("tablero")){
				cond = " WHERE DashBoard = 1 ";
			}
			return personalizadoDAO.findTablasDataStage(idioma, cond);
		}catch(Exception e){
			log.error(e.getMessage());
			return null;
		}
	}

	@Override
	public String obtenerLigasDescargasReportes(Integer idPeticion) throws GlosaNetException{
		return descargaDAO.getLigasDescargasReportes(idPeticion);
	}

	@Override
	public List<String> obtenerResultadosDeReportePersonalizado(
			List<CampoTablaBO> campos, Parametros p) throws GlosaNetException {
		try{
			String colSelect = "", leftTablas="", restricciones = "";
			List<String>encabezados = new ArrayList<String>();
			Map<Integer, String> tabJoin = new HashMap<Integer, String>();
			Map<Integer, String> tabNvPartida = new HashMap<Integer, String>();
			Map<Integer, String> colLeft = new HashMap<Integer, String>();
			Map<Integer, String> colJoin= new HashMap<Integer, String>();
			int e = 0;
			Boolean nlPartida = false;

			u = new UtilFechas();
			//			logger.info("Mes: " + p.getMes());
			restricciones = " \n WHERE DG.Rfc = '" + p.getUsuario().getCliente().getRfcActual() + "' "
					+ "AND DS.FK01_Cliente = " + p.getUsuario().getCliente().getIdCliente() + " AND "
					+ u.construccionDeFechas(p, "DG.FechaPagoReal ", "DG.FechaPagoReal ");

			colSelect = "SELECT  ";
			leftTablas = " \n FROM Datos_Generales_501 AS DG  "
					+ " \n LEFT JOIN GN_DataStage AS DS ON DS.PK_DataStage = DG.FK_DataStage ";



			if(campos.size() > 0){

				for(CampoTablaBO c : campos){

					String columnas="";
					e++;

					Integer numTab = c.getTabla().getNumero();
					if(c.getTabla().getNivelPartida()){
						nlPartida = true;
						tabNvPartida.put(c.getTabla().getNumero(), c.getTabla().getNombre());
					}

					if(!colLeft.containsValue(c.getNombre())){

						if(c.getTabla().getNombre().equals("Resumen")){
							colSelect += "COALESCE(Re." + c.getNombre() + ",'') ,";
							numTab = 1;
							if(!tabJoin.containsValue(c.getTabla().getNombre())){
								tabJoin.put(1, c.getTabla().getNombre());
							}
						}else if(c.getTabla().getNombre().equals("Incidencias_Reconocimiento_Aduanero")){
							colSelect += "COALESCE(IRA." + c.getNombre() + ",'') , ";
							numTab = 2;
							if(!tabJoin.containsValue(c.getTabla().getNombre())){
								tabJoin.put(2, c.getTabla().getNombre());
							}
						}else if(c.getTabla().getNombre().equals("Datos_Generales_501")){
							colSelect += "COALESCE(DG." + c.getNombre() + ",'') , ";
						}else{
							colSelect += "COALESCE(t_" + c.getTabla().getNumero().toString() + "." + c.getNombre() + ",'') , ";

							if(!tabJoin.containsValue(c.getTabla().getNombre()) && !c.getTabla().getNombre().equals("Partidas_551")){
								tabJoin.put(c.getTabla().getNumero(), c.getTabla().getNombre());
							}
						}

						encabezados.add(c.getNombre());


						if(colJoin.containsKey(numTab)){
							if(!c.getNombre().equals("Patente") && !c.getNombre().equals("Pedimento") && !c.getNombre().equals("SeccionAduanera") && !c.getNombre().equals("SecuenciaFraccion")){
								columnas = colJoin.get(numTab) + ", " + c.getNombre();
							}else{
								columnas = colJoin.get(numTab);
							}
						}else if(!c.getNombre().equals("Patente") && !c.getNombre().equals("Pedimento") && !c.getNombre().equals("SeccionAduanera") && !c.getNombre().equals("SecuenciaFraccion")){
							columnas = c.getNombre();
						}
						colLeft.put(e, c.getNombre());

						if(!columnas.equals(""))
							colJoin.put(numTab, columnas);

					}

				}
				System.out.print(colSelect);
				colSelect += "1";
				colSelect.replaceAll(" ,1", "");

				if(nlPartida){
					leftTablas += " \n LEFT JOIN (SELECT * FROM Partidas_551) AS t_551 ON AND t_551.Patente = DG.Patente AND t_551.Pedimento = DG.Pedimento AND t_551.SeccionAduanera = DG.SeccionAduanera ";
				}

				for (Integer t : tabJoin.keySet()) {
					String tbl = tabJoin.get(t);
					if(tbl.equals("Resumen")){
						leftTablas += " \n LEFT JOIN (SELECT distinct " + colJoin.get(t) + ", FK_DataStage  FROM Resumen ) AS Re ON Re.FK_DataStage = DG.FK_DataStage  ";
					}else if(tbl.equals("Incidencias_Reconocimiento_Aduanero")){
						leftTablas += " \n LEFT JOIN (SELECT distinct " + colJoin.get(t) + ", Patente, Pedimento, SeccionAduanera, SecuenciaFraccion  FROM Incidencias_Reconocimiento_Aduanero) AS IRA ON "
								+ " IRA.Patente = DG.Patente AND IRA.Pedimento = DG.Pedimento AND IRA.SeccionAduanera = DG.SeccionAduanera AND IRA.SecuenciaFraccion = t_551.SecuenciaFraccion";
					}else {

						if(tabNvPartida.containsKey(t)){
							leftTablas += " \n LEFT JOIN (SELECT distinct " + colJoin.get(t) + ", Patente, Pedimento, SeccionAduanera, SecuenciaFraccion  FROM " + tabJoin.get(t) + ") AS t_" + t.toString() +
									" ON t_" + t.toString() + ".Patente = DG.Patente AND t_" + t.toString() + ".Pedimento = DG.Pedimento AND t_" + t.toString() + ".SeccionAduanera = DG.SeccionAduanera AND t_" + t.toString() + ".SecuenciaFraccion = t_551.SecuenciaFraccion";
						}else{
							leftTablas += " \n LEFT JOIN (SELECT distinct " + colJoin.get(t) + ", Patente, Pedimento, SeccionAduanera  FROM " + tabJoin.get(t) + ") AS t_" + t.toString() +
									" ON t_" + t.toString() + ".Patente = DG.Patente AND t_" + t.toString() + ".Pedimento = DG.Pedimento AND t_" + t.toString() + ".SeccionAduanera = DG.SeccionAduanera ";
						}
					}

				}

			}

			List<String> resultado = new ArrayList<String>() ;

			int i = 0, regresa = 0;
			Integer x = 0;
			String sql = "";
			String directorio = Util.rutaTemporal + "Reportes/" + p.getUsuario().getIdUsuario() + "/";


			do{
				x++;
				String nombreA = p.getUsuario().getCliente().getRfcActual() + "_" + p.getUsuario().getIdUsuario() + "_" + p.getMes().charAt(0) + p.getMes().charAt(1) + p.getMes().charAt(2) + x.toString();
				colSelect= colSelect.substring(0,colSelect.length()-3);
				sql = colSelect + leftTablas + restricciones + " LIMIT " + i + ", 3000";

				List<String[]> r = personalizadoDAO.findResultadosDeReportePersonalizado(sql);
				regresa = r.size();
				//				logger.info(regresa);
				if(r.size() > 0){
					boolean alreadyExists = new File(directorio + nombreA + ".csv").exists();
					if (!new File(directorio).exists()){
						new File(directorio).mkdirs();
						log.info("Se ha creado la carpeta " + directorio);
					}
					if(alreadyExists){
						File ficheroUsuarios = new File(directorio + nombreA+ ".csv");
						ficheroUsuarios.delete();
					}

					CsvWriter csvOutput = new CsvWriter(new FileWriter(directorio + nombreA + ".csv", true), ',');
					for(String w : encabezados){
						csvOutput.write(w);
					}
					csvOutput.endRecord();

					for(String[] rr : r){
						csvOutput.writeRecord(rr);
					}

					csvOutput.close();

					log.info(Util.rutaTemporalVista + "Reportes/"+ p.getUsuario().getIdUsuario() + "/" + nombreA + ".csv");
					resultado.add("/" +Util.rutaTemporalVista + "Reportes/"+ p.getUsuario().getIdUsuario() + "/" + nombreA + ".csv");
				}


				i = i + 3000;

			}while(regresa == 3000);

			return resultado;

		}catch(Exception e){
			log.error(e.getMessage());
			e.printStackTrace();
			return null;
		}

	}

	@Override
	@Transactional(rollbackFor={Exception.class})
	public Integer peticionResultadosDeReportePersonalizado(
			List<CampoTablaBO> campos, Parametros p) throws GlosaNetException {
		try{
			String colSelect = "", leftTablas="", restricciones = "";
			String encabezados = "";
			Map<Integer, String> tabJoin = new HashMap<Integer, String>();
			Map<Integer, String> tabNvPartida = new HashMap<Integer, String>();
			Map<Integer, String> colLeft = new HashMap<Integer, String>();
			Map<Integer, String> colJoin= new HashMap<Integer, String>();
			int e = 0;
			Boolean nlPartida = false;
			u = new UtilFechas();

			log.info("Descarga de reporte personalizado -> " + p.getTipo() + " - " + p.getYear() + " - " + p.getSemestre() + " - " + p.getTrimestre() + " - " + p.getMes()  + ". "+ u.construccionDeFechas(p, "DG.FechaPagoReal ", "DG.FechaPagoReal "));

			restricciones = " \n WHERE DG.Rfc = '" + p.getUsuario().getCliente().getRfcActual() + "' AND "
					+ u.construccionDeFechas(p, "DG.FechaPagoReal ", "DG.FechaPagoReal ");

			colSelect = "SELECT ";
			leftTablas = " \n FROM Datos_Generales_501 AS DG  "
					+ " \n LEFT JOIN GN_DataStage AS DS ON DS.PK_DataStage = DG.FK_DataStage ";

			if(campos.size() > 0){

				// TODO validación para determinar si el reporte consulta la tabla Contribuciones_Partida_557
				Boolean isContribucionesPartida557 = false;
				for(CampoTablaBO c : campos){
					if (c.getTabla().getNumero() == 557){
						isContribucionesPartida557 = true;
					}
				}

				for(CampoTablaBO c : campos){

					String columnas="";
					e++;

					Integer numTab = c.getTabla().getNumero();
					if(c.getTabla().getNumero() == 555){
					}
					if(c.getTabla().getNivelPartida()){
						nlPartida = true;
						tabNvPartida.put(c.getTabla().getNumero(), c.getTabla().getNombre());
					}

					if(!colLeft.containsValue(c.getNombre())){

						if(c.getTabla().getNombre().equals("Resumen")){
							colSelect += "COALESCE(Re." + c.getNombre() + ",'') ,";
							numTab = 1;
							if(!tabJoin.containsValue(c.getTabla().getNombre())){
								tabJoin.put(1, c.getTabla().getNombre());
							}
						}else if(c.getTabla().getNombre().equals("Incidencias_Reconocimiento_Aduanero")){
							colSelect += "COALESCE(IRA." + c.getNombre() + ",'') , ";
							numTab = 2;
							if(!tabJoin.containsValue(c.getTabla().getNombre())){
								tabJoin.put(2, c.getTabla().getNombre());
							}
						}else if(c.getTabla().getNombre().equals("Datos_Generales_501")){
							colSelect += "COALESCE(DG." + c.getNombre() + ",'') , ";
						}else{
							colSelect += "COALESCE(t_" + c.getTabla().getNumero().toString() + "." + c.getNombre() + ",'') , ";

							if(!tabJoin.containsValue(c.getTabla().getNombre()) && !c.getTabla().getNombre().equals("Partidas_551")){
								tabJoin.put(c.getTabla().getNumero(), c.getTabla().getNombre());
							}
						}

						encabezados += c.getNombre() + ", ";


						if(colJoin.containsKey(numTab)){
							if(!c.getNombre().equals("Patente") && !c.getNombre().equals("Pedimento") && !c.getNombre().equals("SeccionAduanera") && !c.getNombre().equals("SecuenciaFraccion")){
								columnas = colJoin.get(numTab) + ", " + c.getNombre();
							}else{
								columnas = colJoin.get(numTab);
							}
						}else if(!c.getNombre().equals("Patente") && !c.getNombre().equals("Pedimento") && !c.getNombre().equals("SeccionAduanera") && !c.getNombre().equals("SecuenciaFraccion")){
							columnas = c.getNombre();
						}
						colLeft.put(e, c.getNombre());

						if(!columnas.equals("")){
							colJoin.put(numTab, columnas);
						}else{
							colJoin.put(numTab, "1");
						}

					}

				}

				colSelect += "1";
				encabezados += "1";

				if(nlPartida) {
					if (isContribucionesPartida557) {
						leftTablas += " \n LEFT JOIN (SELECT * FROM Partidas_551) AS t_551 ON t_551.FK_DataStage = DG.FK_DataStage AND t_551.Patente = DG.Patente AND t_551.Pedimento = DG.Pedimento AND t_551.SeccionAduanera = DG.SeccionAduanera ";
					} else {
						leftTablas += " \n LEFT JOIN (SELECT * FROM Partidas_551) AS t_551 ON t_551.Patente = DG.Patente AND t_551.Pedimento = DG.Pedimento AND t_551.SeccionAduanera = DG.SeccionAduanera ";
					}
				}

				for (Integer t : tabJoin.keySet()) {
					String tbl = tabJoin.get(t);
					if(tbl.equals("Resumen")){
						leftTablas += " \n LEFT JOIN (SELECT distinct " + colJoin.get(t) + ", FK_DataStage  FROM Resumen ) AS Re ON Re.FK_DataStage = DG.FK_DataStage  ";
					}else if(tbl.equals("Incidencias_Reconocimiento_Aduanero")){
						leftTablas += " \n LEFT JOIN (SELECT distinct " + colJoin.get(t) + ", Patente, Pedimento, SeccionAduanera, SecuenciaFraccion, FK_DataStage FROM Incidencias_Reconocimiento_Aduanero) AS IRA ON "
								+ " IRA.FK_DataStage = DG.FK_DataStage AND IRA.Patente = t_551.Patente AND IRA.Pedimento = t_551.Pedimento AND IRA.SeccionAduanera = t_551.SeccionAduanera AND IRA.SecuenciaFraccion = t_551.SecuenciaFraccion";
					}else {

						if(tabNvPartida.containsKey(t)){
							if (t == 557) {
								leftTablas += " \n LEFT JOIN (SELECT distinct " + colJoin.get(t) + ", Patente, Pedimento, SeccionAduanera, SecuenciaFraccion, FK_DataStage  FROM " + tabJoin.get(t) + ") AS t_" + t.toString() +
										" ON t_" + t.toString() + ".FK_DataStage = DG.FK_DataStage AND t_" + t.toString() + ".Patente = t_551.Patente AND t_" + t.toString() + ".Pedimento = t_551.Pedimento AND t_" + t.toString() + ".SeccionAduanera = t_551.SeccionAduanera AND t_" + t.toString() + ".SecuenciaFraccion = t_551.SecuenciaFraccion";
							} else {
								leftTablas += " \n LEFT JOIN (SELECT distinct " + colJoin.get(t) + ", Patente, Pedimento, SeccionAduanera, SecuenciaFraccion FROM " + tabJoin.get(t) + ") AS t_" + t.toString() +
										" ON t_" + t.toString() + ".Patente = t_551.Patente AND t_" + t.toString() + ".Pedimento = t_551.Pedimento AND t_" + t.toString() + ".SeccionAduanera = t_551.SeccionAduanera AND t_" + t.toString() + ".SecuenciaFraccion = t_551.SecuenciaFraccion";
							}

						}else{
							if (t == 557) {
								leftTablas += " \n LEFT JOIN (SELECT distinct " + colJoin.get(t) + ", Patente, Pedimento, SeccionAduanera, FK_DataStage FROM " + tabJoin.get(t) + ") AS t_" + t.toString() +
										" ON t_" + t.toString() + ".FK_DataStage = DG.FK_DataStage AND t_" + t.toString() + ".Patente = DG.Patente AND t_" + t.toString() + ".Pedimento = DG.Pedimento AND t_" + t.toString() + ".SeccionAduanera = DG.SeccionAduanera ";
							} else {
								leftTablas += " \n LEFT JOIN (SELECT distinct " + colJoin.get(t) + ", Patente, Pedimento, SeccionAduanera, FK_DataStage FROM " + tabJoin.get(t) + ") AS t_" + t.toString() +
										" ON t_" + t.toString() + ".Patente = DG.Patente AND t_" + t.toString() + ".Pedimento = DG.Pedimento AND t_" + t.toString() + ".SeccionAduanera = DG.SeccionAduanera AND t_" + t.toString() + ".FK_DataStage = DG.FK_DataStage ";
							}
						}
					}
				}
			}

			colSelect= colSelect.substring(0,colSelect.length()-3);
			encabezados= encabezados.substring(0,encabezados.length()-3);
			String sql = colSelect + leftTablas + restricciones;

			String sqlContarR = "Select count(*) " + leftTablas + restricciones;
			log.info("Query de descarga de reportes: " + sqlContarR);

			int cantResultados = personalizadoDAO.getCantidadResultadosDeReportePersonalizado(sqlContarR);

			if(cantResultados > 0){
				DescargaBO peticion = new DescargaBO(sql, encabezados, p.getUsuario().getIdUsuario(), "Descarga", null);
				return descargaDAO.insertPeticionDescarga(peticion);
			}else{
				return -1;
			}
		}catch(Exception e){
			log.error(e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public List<String> obtenerLigasArchivosReportes(Integer idPeticion)
			throws GlosaNetException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(rollbackFor={Exception.class})
	public Integer guardarReporte(ReportePersonalizadoBO reportePersonalizado) throws GlosaNetException{
		Integer idReporte = 0;
		if(reportePersonalizado.getIdReportePersonalizado() == 0){
			idReporte = personalizadoDAO.saveReport(reportePersonalizado);
			reportePersonalizado.setIdReportePersonalizado(idReporte);
		}else{
			idReporte = personalizadoDAO.editReport(reportePersonalizado);
			personalizadoDAO.deletePReports(reportePersonalizado);
		}
		for(PReportesPersonalizadosBO item: reportePersonalizado.getLstPReportesPersonalizados()){
			item.setReportePersonalizado(reportePersonalizado);
			personalizadoDAO.savePReport(item);
		}
		return idReporte;
	}

	@Override
	@Transactional(rollbackFor={Exception.class})
	public boolean borrarReporte(ReportePersonalizadoBO reportePersonalizado) throws GlosaNetException{
		if(personalizadoDAO.deletePReports(reportePersonalizado)){
			personalizadoDAO.deleteReport(reportePersonalizado);
			return true;
		}
		return false;
	}

	@Override
	public List<ReportePersonalizadoBO> obtenerReportes(int idAutor) throws Exception{
		List<ReportePersonalizadoBO> lstReportes = personalizadoDAO.getReports(idAutor);
		if(lstReportes != null && lstReportes.size() > 0){
			for(int i = 0; i < lstReportes.size() ; i++){
				lstReportes.get(i).setAutor(usuarioDAO.findUsuario(lstReportes.get(i).getAutor().getIdUsuario()));
			}
		}

		return lstReportes;
	}

	@Override
	public ReportePersonalizadoBO obtenerReportePorId(int id) throws Exception{
		ReportePersonalizadoBO reportes = personalizadoDAO.getReportById(id);
		reportes.setLstPReportesPersonalizados(personalizadoDAO.getPReporstById(id));

		return reportes;
	}

}
