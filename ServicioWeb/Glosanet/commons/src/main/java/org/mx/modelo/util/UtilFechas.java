/**
 * 
 */
package org.mx.modelo.util;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Logger;

import org.mx.modelo.comun.bo.Parametros;

/**
 * @author yosimar.mendez
 *
 */
public class UtilFechas {
	private final static Logger log = Logger.getLogger(UtilFechas.class.getName());
	public static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	public static int diasDelMes(int mes, int year)
	{
		Integer dia = mes == 2 ? (year % 4 != 0 ? 28 : (year % 100 != 0 ? 29 : (year % 400 != 0 ? 28 : 29))) : ((mes - 1) % 7 % 2 != 0 ? 30 : 31);
		log.info("El día del mes es: " + dia);
		return dia;
	}
	
	public static String construirFechas(Parametros parametros, String finicio, String ffin)
	{
		UtilFechas util = new UtilFechas();
		String fecha = util.construccionDeFechas(parametros, finicio, ffin);
		log.info("Fecha construida: " + fecha);

		return fecha;
	}

	public  String construccionDeFechas(Parametros parametros, String finicio, String ffin) {
		String campo = "";
		if (parametros.getTipo().equals("Periodo")) {
			int year = parametros.getYear();
			
			if (parametros.getSemestre() != 0) {
				if (parametros.getSemestre() == 1) {
					campo = ffin + " >= '" + year + "-01-01 00:00:00' AND " + finicio + " <= '" + year + "-06-30 23:59:59'";
				}
				else {
					campo = ffin + " >= '" + year + "-07-01 00:00:00' AND " + finicio + " <= '" + year + "-12-31 23:59:59'";
				}
			} else if (parametros.getTrimestre() != 0) {
				switch (parametros.getTrimestre()) {
				case 1:
					campo = ffin + " >= '" + year + "-01-01 00:00:00' AND " + finicio + " <= '" + year + "-03-31 23:59:59'";
					break;
				case 2:
					campo = ffin + " >= '" + year + "-04-01 00:00:00' AND " + finicio + " <= '" + year + "-06-30 23:59:59'";
					break;
				case 3:
					campo = ffin + " >= '" + year + "-07-01 00:00:00' AND " + finicio + " <= '" + year + "-09-30 23:59:59'";
					break;
				case 4:
					campo = ffin + " >= '" + year + "-10-01 00:00:00' AND " + finicio + " <= '" + year + "-12-31 23:59:59'";
					break;
				default:
					campo = ffin + " >= '" + year + "-01-01 00:00:00' AND " + finicio + " <= '" + year + "-12-31 23:59:59'";
					break;
				}
			} else if (!parametros.getMes().equals("")) {
				int mes = Meses.obtenerMes(parametros.getMes().toUpperCase());
				if(mes == 0){
					try{
						mes = Integer.parseInt(parametros.getMes());
					}catch (Exception e) {
						mes = 1;
					}
				}
				campo = ffin + " >= '" + year + "-" + mes + "-01 00:00:00' AND " + finicio + " <= '" + year + "-" + mes + "-" + diasDelMes(mes, year) + " 23:59:59'";
			} else {
				campo = ffin + " >= '" + year + "-01-01 00:00:00' AND " + finicio + " <= '" + year + "-12-31 23:59:59'";
			}
		} else if (parametros.getTipo().equals("Personalizado")) {
			log.info("Construcción de fecha personalizada. Parámetros: " + parametros.getFinicio() + ", " + formatter.format(parametros.getFinicio()));
			if (parametros.getFinicio() != null && parametros.getFfin() != null) {
				campo = ffin + " >= '" + formatter.format(parametros.getFinicio()) + " 00:00:00' AND " + finicio + " <= '" + formatter.format(parametros.getFfin()) + " 23:59:59'"; 
			}
		} else if (parametros.getTipo().equals("Range")) {

			if (parametros.getFinicio() != null && parametros.getFfin() != null) {
				//Conversión de fecha
				Instant instantInicio = parametros.getFinicio().toInstant();
				Instant instantFin = parametros.getFfin().toInstant();
				LocalDate startDate = instantInicio.atZone(ZoneId.of("America/Mexico_City")).toLocalDate();
				LocalDate endDate = instantFin.atZone(ZoneId.of("America/Mexico_City")).toLocalDate();
				campo = ffin + " >= '" + startDate.getYear() + "-" + startDate.getMonthValue() + "-" + startDate.getDayOfMonth() + " 00:00:00' AND " + finicio + " <= '" + endDate.getYear() + "-" + endDate.getMonthValue() + "-" + endDate.getDayOfMonth() + " 23:59:59'";
			} else {
				campo = ffin + " >= '" + formatter.format(new Date()) + " 00:00:00' AND " + finicio + " <= '" + formatter.format(new Date()) + " 23:59:59'";
			}
		} else {
			campo = ffin + " >= '" + formatter.format(new Date()) + " 00:00:00' AND " + finicio + " <= '" + formatter.format(new Date()) + " 23:59:59'";
		}
		log.info("Fecha construida: " + campo);
		return campo;
	}
}
