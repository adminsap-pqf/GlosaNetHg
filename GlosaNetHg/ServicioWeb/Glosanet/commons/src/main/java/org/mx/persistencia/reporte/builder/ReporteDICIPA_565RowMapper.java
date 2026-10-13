package org.mx.persistencia.reporte.builder;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.mx.modelo.reportes.ReporteDICIPA_565;
import org.springframework.jdbc.core.RowMapper;

public class ReporteDICIPA_565RowMapper implements RowMapper<ReporteDICIPA_565> {

	@Override
	public ReporteDICIPA_565 mapRow(ResultSet rs, int rowNum) throws SQLException {
		ReporteDICIPA_565 bo = new ReporteDICIPA_565();
		
		bo.setPatente(rs.getString("Patente"));
		bo.setSeccionAduanera(rs.getString("SeccionAduanera"));
		bo.setNumeroGuia(rs.getString("NumeroGuia"));
		bo.setTipoGuia(rs.getString("TipoGuia"));
		bo.setTipoContenedor(rs.getString("TipoContenedor"));
		bo.setNumContenedor(rs.getString("NumContenedor"));
		bo.setClaveDocumento(rs.getString("ClaveDocumento"));
		bo.setRfc(rs.getString("Rfc"));
		bo.setProveedorMercancia(rs.getString("ProveedorMercancia"));
		bo.setPaisCompradorVendedor(rs.getString("PaisCompradorVendedor"));
		bo.setValorDolares(rs.getString("ValorDolares"));
		bo.setTipoCambio(rs.getString("TipoCambio"));
		bo.setTotalFletes(rs.getString("TotalFletes"));
		bo.setTotalSeguros(rs.getString("TotalSeguros"));
		bo.setPesoBrutoMercancia(rs.getString("PesoBrutoMercancia"));
		bo.setTotalEmbalajes(rs.getString("TotalEmbalajes"));
		bo.setTipoPedimento(rs.getString("TipoPedimento"));
		bo.setValorComercialDolares(rs.getString(""));
		bo.setTasaContribucion(rs.getString("TasaContribucion"));
		bo.setClaveContribucion(rs.getString("CCPartida"));
		bo.setImportePagoPedimento(rs.getString("ImportePagoPedimento"));
		bo.setFechaPagoReal(rs.getDate("FechaPagoReal"));
		bo.setImportePago(rs.getString("ImportePagoPartida"));
		bo.setClaveContribucionPedimento(rs.getString("CCPedimento"));

		switch(rs.getInt("MedioTransporteSalida")){
		case 1:
			bo.setMedioTransporteSalida(rs.getInt("MedioTransporteSalida") + " - MARITIMO");
			break;
		case 2:
			bo.setMedioTransporteSalida(rs.getInt("MedioTransporteSalida") + " - MARITIFERROVIARIO DE DOBLE ESTIBAMO");
			break;
		case 3:
			bo.setMedioTransporteSalida(rs.getInt("MedioTransporteSalida") + " - CARRETERO-FERROVIARIO");
			break;
		case 4:
			bo.setMedioTransporteSalida(rs.getInt("MedioTransporteSalida") + " - AEREO");
			break;
		case 5:
			bo.setMedioTransporteSalida(rs.getInt("MedioTransporteSalida") + " - POSTAL");
			break;
		case 6:
			bo.setMedioTransporteSalida(rs.getInt("MedioTransporteSalida") + " - FERROVIARIO");
			break;
		case 7:
			bo.setMedioTransporteSalida(rs.getInt("MedioTransporteSalida") + " - CARRETERO");
			break;
		case 8:
			bo.setMedioTransporteSalida(rs.getInt("MedioTransporteSalida")  + " - TUBERIA");
			break;
		case 10:
			bo.setMedioTransporteSalida(rs.getInt("MedioTransporteSalida")  + " - CABLES");
			break;
		case 11:
			bo.setMedioTransporteSalida(rs.getInt("MedioTransporteSalida")  + " - DUCTOS");
			break;
		case 12:
			bo.setMedioTransporteSalida(rs.getInt("MedioTransporteSalida")  + " - PEATONAL");
			break;
		case 98:
			bo.setMedioTransporteSalida(rs.getInt("MedioTransporteSalida")  + " - NO SE DECLARA MEDIO DE TRANSPORTE...");
			break;
		case 99:
			bo.setMedioTransporteSalida(rs.getInt("MedioTransporteSalida")  + " - OTROS");
			break;
		default:
			bo.setMedioTransporteSalida(rs.getInt("MedioTransporteSalida") + "");
			break;
		}



		switch(rs.getInt("MedioTransporteArribo") ){
		case 1:
			bo.setMedioTransporteArribo(rs.getInt("MedioTransporteArribo")  + " - MARITIMO");
			break;
		case 2:
			bo.setMedioTransporteArribo(rs.getInt("MedioTransporteArribo")  + " - MARITIFERROVIARIO DE DOBLE ESTIBAMO");
			break;
		case 3:
			bo.setMedioTransporteArribo(rs.getInt("MedioTransporteArribo")  + " - CARRETERO-FERROVIARIO");
			break;
		case 4:
			bo.setMedioTransporteArribo(rs.getInt("MedioTransporteArribo")  + " - AEREO");
			break;
		case 5:
			bo.setMedioTransporteArribo(rs.getInt("MedioTransporteArribo")  + " - POSTAL");
			break;
		case 6:
			bo.setMedioTransporteArribo(rs.getInt("MedioTransporteArribo")  + " - FERROVIARIO");
			break;
		case 7:
			bo.setMedioTransporteArribo(rs.getInt("MedioTransporteArribo")  + " - CARRETERO");
			break;
		case 8:
			bo.setMedioTransporteArribo(rs.getInt("MedioTransporteArribo")  + " - TUBERIA");
			break;
		case 10:
			bo.setMedioTransporteArribo(rs.getInt("MedioTransporteArribo")  + " - CABLES");
			break;
		case 11:
			bo.setMedioTransporteArribo(rs.getInt("MedioTransporteArribo")  + " - DUCTOS");
			break;
		case 12:
			bo.setMedioTransporteArribo(rs.getInt("MedioTransporteArribo")  + " - PEATONAL");
			break;
		case 98:
			bo.setMedioTransporteArribo(rs.getInt("MedioTransporteArribo")  + " - NO SE DECLARA MEDIO DE TRANSPORTE...");
			break;
		case 99:
			bo.setMedioTransporteArribo(rs.getInt("MedioTransporteArribo")  + " - OTROS");
			break;
		default:
			bo.setMedioTransporteArribo(rs.getInt("MedioTransporteArribo") + "");
			break;
		}



		switch(rs.getInt("MedioTransporteEntrada_Salida") ){
		case 1:
			bo.setMedioTransporteEntrada_Salida(rs.getInt("MedioTransporteEntrada_Salida")  + " - MARITIMO");
			break;
		case 2:
			bo.setMedioTransporteEntrada_Salida(rs.getInt("MedioTransporteEntrada_Salida")  + " - MARITIFERROVIARIO DE DOBLE ESTIBAMO");
			break;
		case 3:
			bo.setMedioTransporteEntrada_Salida(rs.getInt("MedioTransporteEntrada_Salida")  + " - CARRETERO-FERROVIARIO");
			break;
		case 4:
			bo.setMedioTransporteEntrada_Salida(rs.getInt("MedioTransporteEntrada_Salida")  + " - AEREO");
			break;
		case 5:
			bo.setMedioTransporteEntrada_Salida(rs.getInt("MedioTransporteEntrada_Salida")  + " - POSTAL");
			break;
		case 6:
			bo.setMedioTransporteEntrada_Salida(rs.getInt("MedioTransporteEntrada_Salida")  + " - FERROVIARIO");
			break;
		case 7:
			bo.setMedioTransporteEntrada_Salida(rs.getInt("MedioTransporteEntrada_Salida")  + " - CARRETERO");
			break;
		case 8:
			bo.setMedioTransporteEntrada_Salida(rs.getInt("MedioTransporteEntrada_Salida")  + " - TUBERIA");
			break;
		case 10:
			bo.setMedioTransporteEntrada_Salida(rs.getInt("MedioTransporteEntrada_Salida")  + " - CABLES");
			break;
		case 11:
			bo.setMedioTransporteEntrada_Salida(rs.getInt("MedioTransporteEntrada_Salida")  + " - DUCTOS");
			break;
		case 12:
			bo.setMedioTransporteEntrada_Salida(rs.getInt("MedioTransporteEntrada_Salida")  + " - PEATONAL");
			break;
		case 98:
			bo.setMedioTransporteEntrada_Salida(rs.getInt("MedioTransporteEntrada_Salida")  + " - NO SE DECLARA MEDIO DE TRANSPORTE...");
			break;
		case 99:
			bo.setMedioTransporteEntrada_Salida(rs.getInt("MedioTransporteEntrada_Salida")  + " - OTROS");
			break;
		default:
			bo.setMedioTransporteEntrada_Salida(rs.getInt("MedioTransporteEntrada_Salida") + "");
			break;
		}



		switch(rs.getInt("DestinoMercancia") ){
		case 1:
			bo.setDestinoMercancia(rs.getInt("DestinoMercancia")  + " - ESTADO DE BAJA CALIFORNIA Y PARCIAL DE SONORA");
			break;
		case 2:
			bo.setDestinoMercancia(rs.getInt("DestinoMercancia")  + " - ESTADO DE BAJA CALIFORNIA SUR");
			break;
		case 3:
			bo.setDestinoMercancia(rs.getInt("DestinoMercancia")  + " - ESTADO DE QUINTANA ROO");
			break;
		case 5:
			bo.setDestinoMercancia(rs.getInt("DestinoMercancia")  + " - MUNICIPIO DE SALINA CRUZ, OAX");
			break;
		case 6:
			bo.setDestinoMercancia(rs.getInt("DestinoMercancia")  + " - MUNICIPIO DE CANANEA, SON");
			break;
		case 7:
			bo.setDestinoMercancia(rs.getInt("DestinoMercancia")  + " - FRANJA FRONTERIZA NORTE");
			break;
		case 8:
			bo.setDestinoMercancia(rs.getInt("DestinoMercancia")  + " - FRANJA FRONTERIZA SUR, COLINDANTE CON GUATEMALA");
			break;
		case 9:
			bo.setDestinoMercancia(rs.getInt("DestinoMercancia")  + " - INTERIOR DEL PAIS");
			break;
		case 10:
			bo.setDestinoMercancia(rs.getInt("DestinoMercancia")  + " - MUNICIPIO DE CABORCA, SON");
			break;
		default:
			bo.setDestinoMercancia(rs.getInt("DestinoMercancia") + "");
			break;
		}


		return bo;
	}

}
