package org.mx.persistencia.reporte.builder;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mx.modelo.gaia.Country;
import org.mx.modelo.reportes.ReporteDICIPA_565;
import org.mx.modelo.reportes.bo.ResumenReporteBO;
import org.mx.modelo.reportes.po.ResumenReportePO;

public class BuilderResumenReporte {

	public static ResumenReporteBO builderBO(ResumenReportePO po,String idioma){
		ResumenReporteBO bo = new ResumenReporteBO();
		if(po.getMontoTotal() > 0){
			bo.setMontoTotal(po.getMontoTotal());
		}else{
			bo.setMontoTotal(0.0);
		}

		if (po.getPais() != null &&  !po.getPais().equals("")){
			try {
				bo.setRegion(Country.getInstance(po.getPais()).getContinent().getNombre());
				if(idioma.equalsIgnoreCase("espanol")){
					bo.setPais(Country.getInstance(po.getPais()).getNombre());
				}else{
					bo.setPais(Country.getInstance(po.getPais()).getName());
				}

			} catch (Exception e) {
				//e.printStackTrace();
				bo.setRegion("N/D");
				bo.setPais("N/D");
			}
		}

		if(po.getMontoContibuciones() > 0){
			bo.setMontoContribuciones(po.getMontoContibuciones());
			bo.setPorcentajeContribuciones((po.getMontoContibuciones() * 100)/po.getMontoTotal());
		}else{
			bo.setMontoContribuciones(0.0);
			bo.setPorcentajeContribuciones(0.0);
		}

		return bo;
	}


	public static List<ResumenReporteBO> builderTipoOperacion(List<Object[]> lstPO){
		List<ResumenReporteBO> lstBO = new ArrayList<ResumenReporteBO>();
		for(Object[] po : lstPO){

			ResumenReporteBO bo = new ResumenReporteBO();

			if(po[0] != null && !po[0].equals("")){
				bo.setClave(po[0].toString());
			}else{
				bo.setClave("N/D");
			}

			if(po[1] != null && !po[1].equals("")){
				bo.setMontoTotal(Double.parseDouble(po[1].toString()));
			}else{
				bo.setMontoTotal(0.0);
			}

			lstBO.add(bo);

		}
		return lstBO;

	}

	public static List<ResumenReporteBO> builderMontoTotalContribucionClave(List<Object[]> lstPO){
		List<ResumenReporteBO> lstBO = new ArrayList<ResumenReporteBO>();
		for(Object[] po : lstPO){

			ResumenReporteBO bo = new ResumenReporteBO();

			if(po[0] != null && !po[0].equals("")){
				bo.setClave(po[0].toString());
			}else{
				bo.setClave("N/D");
			}

			if(po[1] != null && !po[1].equals("")){
				bo.setMontoTotal(Double.parseDouble(po[1].toString()));
			}else{
				bo.setMontoTotal(0.0);
			}

			if(po[2] != null && !po[2].equals("")){
				double contribucion = 0.0;
				contribucion = Double.parseDouble(po[2].toString());

				bo.setMontoContribuciones(contribucion);
				bo.setPorcentajeContribuciones((contribucion * 100)/bo.getMontoTotal());
			}else{
				bo.setMontoContribuciones(0.0);
				bo.setPorcentajeContribuciones(0.0);
			}

			lstBO.add(bo);

		}
		return lstBO;

	}

	public static List<ResumenReporteBO> builderMontoTotalPais(List<Object[]> lstPO,String idioma){
		List<ResumenReporteBO> lstBO = new ArrayList<ResumenReporteBO>();
		for(Object[] po : lstPO){

			ResumenReporteBO bo = new ResumenReporteBO();

			if(po[1] != null && !po[1].equals("")){
				bo.setMontoTotal(Double.parseDouble(po[1].toString()));
			}else{
				bo.setMontoTotal(0.0);
			}

			if(po[0] != null && !po[0].equals("")){
				try {
					bo.setRegion(Country.getInstance(po[0].toString()).getContinent().getNombre());

					if(idioma.equalsIgnoreCase("espanol")){
						bo.setPais(Country.getInstance(po[0].toString()).getNombre());
					}else{
						bo.setPais(Country.getInstance(po[0].toString()).getName());
					}

				} catch (Exception e) {
					//e.printStackTrace();
					bo.setRegion("N/D");
					bo.setPais("N/D");
				}	
			}else{
				bo.setRegion("N/D");
				bo.setPais("N/D");
			}
			lstBO.add(bo);
		}
		return lstBO;

	}
	public static List<ResumenReporteBO> builderMontoTotalContribucionPais(List<Object[]> lstPO,String idioma){
		List<ResumenReporteBO> lstBO = new ArrayList<ResumenReporteBO>();
		for(Object[] po : lstPO){

			ResumenReporteBO bo = new ResumenReporteBO();

			if(po[0] != null && !po[0].equals("")){
				try {
					bo.setRegion(Country.getInstance(po[0].toString()).getContinent().getNombre());
					if(idioma.equalsIgnoreCase("espanol")){
						bo.setPais(Country.getInstance(po[0].toString()).getNombre());
					}else{
						bo.setPais(Country.getInstance(po[0].toString()).getName());
					}
				} catch (Exception e) {
					//e.printStackTrace();
					bo.setRegion("N/D");
					bo.setPais("N/D");
				}	
			}else{
				bo.setRegion("N/D");
				bo.setPais("N/D");
			}

			if(po[1] != null && !po[1].equals("")){
				bo.setMontoTotal(Double.parseDouble(po[1].toString()));
			}else{
				bo.setMontoTotal(0.0);
			}

			if(po[2] != null && !po[2].equals("")){
				double contribucion = 0.0;
				contribucion = Double.parseDouble(po[2].toString());

				bo.setMontoContribuciones(contribucion);
				bo.setPorcentajeContribuciones((contribucion * 100)/bo.getMontoTotal());
			}else{
				bo.setMontoContribuciones(0.0);
				bo.setPorcentajeContribuciones(0.0);
			}

			lstBO.add(bo);

		}
		return lstBO;

	}

	public static ReporteDICIPA_565 builderBO_ReporteDICIPA_565(Object[] po){
		int i= 0;
		ReporteDICIPA_565 bo = new ReporteDICIPA_565();

		if(po[i] != null && !po[i].equals("")){
			bo.setPatente(po[i].toString());
		}
		i++;
		if(po[i] != null && !po[i].equals("")){
			bo.setPedimento(po[i].toString());
		}
		i++;
		if(po[i] != null && !po[i].equals("")){
			bo.setSeccionAduanera(po[i].toString());
		}
		i++;
		if(po[i] != null && !po[i].equals("")){
			bo.setClaveDocumento(po[i].toString());
		}
		i++;
		if(po[i] != null && !po[i].equals("")){
			bo.setRfc(po[i].toString());
		}
		i++;
		if(po[i] != null && !po[i].equals("")){
			bo.setTipoCambio(po[i].toString());
		}
		i++;
		if(po[i] != null && !po[i].equals("")){
			bo.setTotalFletes(po[i].toString());
		}
		i++;
		if(po[i] != null && !po[i].equals("")){
			bo.setTotalSeguros(po[i].toString());
		}
		i++;
		if(po[i] != null && !po[i].equals("")){
			bo.setPesoBrutoMercancia(po[i].toString());
		}
		i++;
		if(po[i] != null && !po[i].equals("")){
			switch(po[i].toString()){
			case "1":
				bo.setMedioTransporteSalida(po[i].toString() + " - MARITIMO");
				break;
			case "2":
				bo.setMedioTransporteSalida(po[i].toString() + " - MARITIFERROVIARIO DE DOBLE ESTIBAMO");
				break;
			case "3":
				bo.setMedioTransporteSalida(po[i].toString() + " - CARRETERO-FERROVIARIO");
				break;
			case "4":
				bo.setMedioTransporteSalida(po[i].toString() + " - AEREO");
				break;
			case "5":
				bo.setMedioTransporteSalida(po[i].toString() + " - POSTAL");
				break;
			case "6":
				bo.setMedioTransporteSalida(po[i].toString() + " - FERROVIARIO");
				break;
			case "7":
				bo.setMedioTransporteSalida(po[i].toString() + " - CARRETERO");
				break;
			case "8":
				bo.setMedioTransporteSalida(po[i].toString() + " - TUBERIA");
				break;
			case "10":
				bo.setMedioTransporteSalida(po[i].toString() + " - CABLES");
				break;
			case "11":
				bo.setMedioTransporteSalida(po[i].toString() + " - DUCTOS");
				break;
			case "12":
				bo.setMedioTransporteSalida(po[i].toString() + " - PEATONAL");
				break;
			case "98":
				bo.setMedioTransporteSalida(po[i].toString() + " - NO SE DECLARA MEDIO DE TRANSPORTE...");
				break;
			case "99":
				bo.setMedioTransporteSalida(po[i].toString() + " - OTROS");
				break;
			default:
				bo.setMedioTransporteSalida(po[i].toString());
				break;
			}
		}
		i++;
		if(po[i] != null && !po[i].equals("")){
			switch(po[i].toString()){
			case "1":
				bo.setMedioTransporteArribo(po[i].toString() + " - MARITIMO");
				break;
			case "2":
				bo.setMedioTransporteArribo(po[i].toString() + " - MARITIFERROVIARIO DE DOBLE ESTIBAMO");
				break;
			case "3":
				bo.setMedioTransporteArribo(po[i].toString() + " - CARRETERO-FERROVIARIO");
				break;
			case "4":
				bo.setMedioTransporteArribo(po[i].toString() + " - AEREO");
				break;
			case "5":
				bo.setMedioTransporteArribo(po[i].toString() + " - POSTAL");
				break;
			case "6":
				bo.setMedioTransporteArribo(po[i].toString() + " - FERROVIARIO");
				break;
			case "7":
				bo.setMedioTransporteArribo(po[i].toString() + " - CARRETERO");
				break;
			case "8":
				bo.setMedioTransporteArribo(po[i].toString() + " - TUBERIA");
				break;
			case "10":
				bo.setMedioTransporteArribo(po[i].toString() + " - CABLES");
				break;
			case "11":
				bo.setMedioTransporteArribo(po[i].toString() + " - DUCTOS");
				break;
			case "12":
				bo.setMedioTransporteArribo(po[i].toString() + " - PEATONAL");
				break;
			case "98":
				bo.setMedioTransporteArribo(po[i].toString() + " - NO SE DECLARA MEDIO DE TRANSPORTE...");
				break;
			case "99":
				bo.setMedioTransporteArribo(po[i].toString() + " - OTROS");
				break;
			default:
				bo.setMedioTransporteArribo(po[i].toString());
				break;
			}
		}
		i++;
		if(po[i] != null && !po[i].equals("")){
			switch(po[i].toString()){
			case "1":
				bo.setMedioTransporteEntrada_Salida(po[i].toString() + " - MARITIMO");
				break;
			case "2":
				bo.setMedioTransporteEntrada_Salida(po[i].toString() + " - MARITIFERROVIARIO DE DOBLE ESTIBAMO");
				break;
			case "3":
				bo.setMedioTransporteEntrada_Salida(po[i].toString() + " - CARRETERO-FERROVIARIO");
				break;
			case "4":
				bo.setMedioTransporteEntrada_Salida(po[i].toString() + " - AEREO");
				break;
			case "5":
				bo.setMedioTransporteEntrada_Salida(po[i].toString() + " - POSTAL");
				break;
			case "6":
				bo.setMedioTransporteEntrada_Salida(po[i].toString() + " - FERROVIARIO");
				break;
			case "7":
				bo.setMedioTransporteEntrada_Salida(po[i].toString() + " - CARRETERO");
				break;
			case "8":
				bo.setMedioTransporteEntrada_Salida(po[i].toString() + " - TUBERIA");
				break;
			case "10":
				bo.setMedioTransporteEntrada_Salida(po[i].toString() + " - CABLES");
				break;
			case "11":
				bo.setMedioTransporteEntrada_Salida(po[i].toString() + " - DUCTOS");
				break;
			case "12":
				bo.setMedioTransporteEntrada_Salida(po[i].toString() + " - PEATONAL");
				break;
			case "98":
				bo.setMedioTransporteEntrada_Salida(po[i].toString() + " - NO SE DECLARA MEDIO DE TRANSPORTE...");
				break;
			case "99":
				bo.setMedioTransporteEntrada_Salida(po[i].toString() + " - OTROS");
				break;
			default:
				bo.setMedioTransporteEntrada_Salida(po[i].toString());
				break;
			}
		}
		i++;
		if(po[i] != null && !po[i].equals("")){
			switch(po[i].toString()){
			case "1":
				bo.setDestinoMercancia(po[i].toString() + " - ESTADO DE BAJA CALIFORNIA Y PARCIAL DE SONORA");
				break;
			case "2":
				bo.setDestinoMercancia(po[i].toString() + " - ESTADO DE BAJA CALIFORNIA SUR");
				break;
			case "3":
				bo.setDestinoMercancia(po[i].toString() + " - ESTADO DE QUINTANA ROO");
				break;
			case "5":
				bo.setDestinoMercancia(po[i].toString() + " - MUNICIPIO DE SALINA CRUZ, OAX");
				break;
			case "6":
				bo.setDestinoMercancia(po[i].toString() + " - MUNICIPIO DE CANANEA, SON");
				break;
			case "7":
				bo.setDestinoMercancia(po[i].toString() + " - FRANJA FRONTERIZA NORTE");
				break;
			case "8":
				bo.setDestinoMercancia(po[i].toString() + " - FRANJA FRONTERIZA SUR, COLINDANTE CON GUATEMALA");
				break;
			case "9":
				bo.setDestinoMercancia(po[i].toString() + " - INTERIOR DEL PAIS");
				break;
			case "10":
				bo.setDestinoMercancia(po[i].toString() + " - MUNICIPIO DE CABORCA, SON");
				break;
			default:
				bo.setDestinoMercancia(po[i].toString());
				break;
			}
		}
		i++;
		if(po[i] != null && !po[i].equals("")){
			bo.setTotalEmbalajes(po[i].toString());
		}
		i++;
		if(po[i] != null && !po[i].equals("")){
			bo.setTipoPedimento(po[i].toString());
		}
		i++;


		return bo;
	}

	public static ReporteDICIPA_565 builderBO_ReporteDICIPA_565_2(Object[] po){
		int i= 0;
		ReporteDICIPA_565 bo = new ReporteDICIPA_565();

		if(po[i] != null && !po[i].equals("")){ //DG.Patente,Patente
			bo.setPatente(po[i].toString());
		}
		i++;
		if(po[i] != null && !po[i].equals("")){ //DG.Pedimento//Pedimento
			bo.setPedimento(po[i].toString());
		}
		i++;
		if(po[i] != null && !po[i].equals("")){//DG.SeccionAduanera//Seccion Aduanera
			bo.setSeccionAduanera(po[i].toString());
		}
		i++;
		if(po[i] != null && !po[i].equals("")){//t_503.NumeroGuia//Numero Guia
			bo.setNumeroGuia(po[i].toString());
		}
		i++;
		if(po[i] != null && !po[i].equals("")){//t_503.TipoGuia//Tipo Guia
			bo.setTipoGuia(po[i].toString());
		}
		i++;
		if(po[i] != null && !po[i].equals("")){//t_504.TipoContenedor//Tipo Contenedor
			bo.setTipoContenedor(po[i].toString());
		}
		i++;
		if(po[i] != null && !po[i].equals("")){//t_504.NumContenedor//Numero Contenedor
			bo.setNumContenedor(po[i].toString());
		}
		i++;
		if(po[i] != null && !po[i].equals("")){//DG.ClaveDocumento//Clave Documento
			bo.setClaveDocumento(po[i].toString());
		}
		i++;
		if(po[i] != null && !po[i].equals("")){//DG.Rfc//RFC
			bo.setRfc(po[i].toString());
		}
		i++;
		if(po[i] != null && !po[i].equals("")){//t_505.ProveedorMercancia//Proveedor Mercancia
			bo.setProveedorMercancia(po[i].toString());
		}
		i++;
		if(po[i] != null && !po[i].equals("")){//t_551.PaisCompradorVendedor//Pais Comprador Vendedor
			bo.setPaisCompradorVendedor(po[i].toString());
		}
		i++;

		if(po[i] != null && !po[i].equals("")){// t_551.ValorDolares//Valor Dolares
			bo.setValorDolares(po[i].toString());
		}
		i++;
		if(po[i] != null && !po[i].equals("")){//DG.TipoCambio//Tipo Cambio
			bo.setTipoCambio(po[i].toString());
		}
		i++;
		if(po[i] != null && !po[i].equals("")){// DG.TotalFletes//Total Fletes
			bo.setTotalFletes(po[i].toString());
		}
		i++;
		if(po[i] != null && !po[i].equals("")){// DG.TotalSeguros//Total Seguros
			bo.setTotalSeguros(po[i].toString());
		}
		i++;
		if(po[i] != null && !po[i].equals("")){//DG.PesoBrutoMercancia//Peso Bruto Mercancia
			bo.setPesoBrutoMercancia(po[i].toString());
		}
		i++;
		if(po[i] != null && !po[i].equals("")){// DG.MedioTransporteSalida//Medio Transporte Salida
			switch(po[i].toString()){
			case "1":
				bo.setMedioTransporteSalida(po[i].toString() + " - MARITIMO");
				break;
			case "2":
				bo.setMedioTransporteSalida(po[i].toString() + " - MARITIFERROVIARIO DE DOBLE ESTIBAMO");
				break;
			case "3":
				bo.setMedioTransporteSalida(po[i].toString() + " - CARRETERO-FERROVIARIO");
				break;
			case "4":
				bo.setMedioTransporteSalida(po[i].toString() + " - AEREO");
				break;
			case "5":
				bo.setMedioTransporteSalida(po[i].toString() + " - POSTAL");
				break;
			case "6":
				bo.setMedioTransporteSalida(po[i].toString() + " - FERROVIARIO");
				break;
			case "7":
				bo.setMedioTransporteSalida(po[i].toString() + " - CARRETERO");
				break;
			case "8":
				bo.setMedioTransporteSalida(po[i].toString() + " - TUBERIA");
				break;
			case "10":
				bo.setMedioTransporteSalida(po[i].toString() + " - CABLES");
				break;
			case "11":
				bo.setMedioTransporteSalida(po[i].toString() + " - DUCTOS");
				break;
			case "12":
				bo.setMedioTransporteSalida(po[i].toString() + " - PEATONAL");
				break;
			case "98":
				bo.setMedioTransporteSalida(po[i].toString() + " - NO SE DECLARA MEDIO DE TRANSPORTE...");
				break;
			case "99":
				bo.setMedioTransporteSalida(po[i].toString() + " - OTROS");
				break;
			default:
				bo.setMedioTransporteSalida(po[i].toString());
				break;
			}
		}
		i++;

		if(po[i] != null && !po[i].equals("")){//DG.MedioTransporteArribo//Medio Transporte Arribo
			switch(po[i].toString()){
			case "1":
				bo.setMedioTransporteArribo(po[i].toString() + " - MARITIMO");
				break;
			case "2":
				bo.setMedioTransporteArribo(po[i].toString() + " - MARITIFERROVIARIO DE DOBLE ESTIBAMO");
				break;
			case "3":
				bo.setMedioTransporteArribo(po[i].toString() + " - CARRETERO-FERROVIARIO");
				break;
			case "4":
				bo.setMedioTransporteArribo(po[i].toString() + " - AEREO");
				break;
			case "5":
				bo.setMedioTransporteArribo(po[i].toString() + " - POSTAL");
				break;
			case "6":
				bo.setMedioTransporteArribo(po[i].toString() + " - FERROVIARIO");
				break;
			case "7":
				bo.setMedioTransporteArribo(po[i].toString() + " - CARRETERO");
				break;
			case "8":
				bo.setMedioTransporteArribo(po[i].toString() + " - TUBERIA");
				break;
			case "10":
				bo.setMedioTransporteArribo(po[i].toString() + " - CABLES");
				break;
			case "11":
				bo.setMedioTransporteArribo(po[i].toString() + " - DUCTOS");
				break;
			case "12":
				bo.setMedioTransporteArribo(po[i].toString() + " - PEATONAL");
				break;
			case "98":
				bo.setMedioTransporteArribo(po[i].toString() + " - NO SE DECLARA MEDIO DE TRANSPORTE...");
				break;
			case "99":
				bo.setMedioTransporteArribo(po[i].toString() + " - OTROS");
				break;
			default:
				bo.setMedioTransporteArribo(po[i].toString());
				break;
			}
		}
		i++;

		if(po[i] != null && !po[i].equals("")){//DG.MedioTransporteEntrada_Salida//Medio Transporte Entrada/Salida
			switch(po[i].toString()){
			case "1":
				bo.setMedioTransporteEntrada_Salida(po[i].toString() + " - MARITIMO");
				break;
			case "2":
				bo.setMedioTransporteEntrada_Salida(po[i].toString() + " - MARITIFERROVIARIO DE DOBLE ESTIBAMO");
				break;
			case "3":
				bo.setMedioTransporteEntrada_Salida(po[i].toString() + " - CARRETERO-FERROVIARIO");
				break;
			case "4":
				bo.setMedioTransporteEntrada_Salida(po[i].toString() + " - AEREO");
				break;
			case "5":
				bo.setMedioTransporteEntrada_Salida(po[i].toString() + " - POSTAL");
				break;
			case "6":
				bo.setMedioTransporteEntrada_Salida(po[i].toString() + " - FERROVIARIO");
				break;
			case "7":
				bo.setMedioTransporteEntrada_Salida(po[i].toString() + " - CARRETERO");
				break;
			case "8":
				bo.setMedioTransporteEntrada_Salida(po[i].toString() + " - TUBERIA");
				break;
			case "10":
				bo.setMedioTransporteEntrada_Salida(po[i].toString() + " - CABLES");
				break;
			case "11":
				bo.setMedioTransporteEntrada_Salida(po[i].toString() + " - DUCTOS");
				break;
			case "12":
				bo.setMedioTransporteEntrada_Salida(po[i].toString() + " - PEATONAL");
				break;
			case "98":
				bo.setMedioTransporteEntrada_Salida(po[i].toString() + " - NO SE DECLARA MEDIO DE TRANSPORTE...");
				break;
			case "99":
				bo.setMedioTransporteEntrada_Salida(po[i].toString() + " - OTROS");
				break;
			default:
				bo.setMedioTransporteEntrada_Salida(po[i].toString());
				break;
			}
		}
		i++;

		if(po[i] != null && !po[i].equals("")){//DG.DestinoMercancia//Destino Mercancia
			switch(po[i].toString()){
			case "1":
				bo.setDestinoMercancia(po[i].toString() + " - ESTADO DE BAJA CALIFORNIA Y PARCIAL DE SONORA");
				break;
			case "2":
				bo.setDestinoMercancia(po[i].toString() + " - ESTADO DE BAJA CALIFORNIA SUR");
				break;
			case "3":
				bo.setDestinoMercancia(po[i].toString() + " - ESTADO DE QUINTANA ROO");
				break;
			case "5":
				bo.setDestinoMercancia(po[i].toString() + " - MUNICIPIO DE SALINA CRUZ, OAX");
				break;
			case "6":
				bo.setDestinoMercancia(po[i].toString() + " - MUNICIPIO DE CANANEA, SON");
				break;
			case "7":
				bo.setDestinoMercancia(po[i].toString() + " - FRANJA FRONTERIZA NORTE");
				break;
			case "8":
				bo.setDestinoMercancia(po[i].toString() + " - FRANJA FRONTERIZA SUR, COLINDANTE CON GUATEMALA");
				break;
			case "9":
				bo.setDestinoMercancia(po[i].toString() + " - INTERIOR DEL PAIS");
				break;
			case "10":
				bo.setDestinoMercancia(po[i].toString() + " - MUNICIPIO DE CABORCA, SON");
				break;
			default:
				bo.setDestinoMercancia(po[i].toString());
				break;
			}
		}									
		i++;
		if(po[i] != null && !po[i].equals("")){//DG.TotalEmbalajes//Total Embalajes
			bo.setTotalEmbalajes(po[i].toString());
		}
		i++;
		if(po[i] != null && !po[i].equals("")){//DG.TipoPedimento//Tipo Pedimento
			bo.setTipoPedimento(po[i].toString());
		}
		i++;
		if(po[i] != null && !po[i].equals("")){//t_509.TasaContribucion//Tasa Contribucion
			//bo.setTasaContribucion("prueba3");
			bo.setValorComercialDolares(po[i].toString());
			//bo.setImportePago(po[i].toString());
		}
		i++;
		if(po[i] != null && !po[i].equals("")){//
			//bo.setClaveContribucion(po[i].toString());
			//bo.setClaveContribucionPedimento(po[i].toString());
			//bo.setValorComercialDolares(po[i].toString());
			//bo.setValorComercialDolares(po[i].toString());

			bo.setTasaContribucion(po[i].toString());
		}
		i++;
		if(po[i] != null && !po[i].equals("")){		//t_510.CCPedimento as CCPedimento//Clave Contribucion Pedimento
			//bo.setImportePago(po[i].toString());
			//
			bo.setClaveContribucion(po[i].toString());
		}
		i++;
		if(po[i] != null && !po[i].equals("")){//t_557.ImportePagoPartida as ImportePagoPartida//Importe Pago Partida

			bo.setImportePago(po[i].toString());
			//bo.setClaveContribucion("prueba2");
			//bo.setClaveContribucionPedimento(po[i].toString());
		}
		i++;
		if(po[i] != null && !po[i].equals("")){//t_510.ImportePagoPedimento as ImportePagoPedimento//Importe Pago Pedimento
			//bo.setValorComercialDolares("prueba");
			//bo.setImportePagoPedimento(po[i].toString());
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date=null;

			if (po.length==27){
			try {
				date = dateFormat.parse(po[i].toString());
				bo.setFechaPagoReal(date);
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
			bo.setFechaPagoReal(date);
		}else{
				try {
					date = dateFormat.parse(po[27].toString());
					bo.setFechaPagoReal(date);
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
				bo.setFechaPagoReal(date);
			}


		}
		if (po.length==28){
		i++;

		if(po[i] != null && !po[i].equals("")){//DG.FechaPagoReal//Fecha Pago Real
			bo.setImportePagoPedimento(po[26].toString());


		}
		}
		//i++;

		
		
		return bo;
	}


}
