package org.mx.modelo.util;

public enum Archivos {

	Resumen("Resumen","Resumen","ResumenPO"),
	DatosGenerales("501","Datos_Generales_501","DatosGeneralesPO"),
	TransporteMercancias("502","Transporte_Mercancias_502","TransporteMercanciasPO"),
	Guias("503","Guias_503","GuiasPO"),
	Contenedores("504","Contenedores_504","ContenedoresPO"),
	Facturas("505","Facturas_505","FacturasPO"),
	FechasPedimentos("506","Fechas_Pedimento_506","FechasPedimentoPO"),
	CasosPedimento("507","Casos_Pedimento_507","CasosPedimentoPO"),
	CuentasAduanerasGarantiaPedimento("508","Cuentas_Aduaneras_Garantia_Pedimento_508","CuentasAduanerasGarantiaPedimentoPO"),
	TasasPedimento("509","Tasas_Pedimento_509","TasasPedimentoPO"),
	ContribucionPedimento("510","Contribuciones_Pedimento_510","ContribucionesPedimentoPO"),
	ObservacionesPedimento("511","Observaciones_Pedimento_511","ObservacionesPedimentoPO"),
	DescargosMercancia("512","Descargos_Mercancia_512","DescargosMercanciaPO"),
	DestinatariosMercancia("520","Destinatarios_Mercancia_520","DestinatariosMercanciaPO"),
	Partidas("551","Partidas_551","PartidasPO"),
	Mercancias("552","Mercancias_552","MercanciasPO"),
	PermisosPartidas("553","Permisos_Partida_553","PermisosPartidaPO"),
	CasosPartida("554","Casos_Partida_554","CasosPartidaPO"),
	CuentasAduanerasGarantia("555","Cuentas_Aduaneras_Garantia_555","CuentasAduanerasGarantiaPO"),
	TasaContribucionesPartida("556","Tasas_Contribuciones_Partida_556","TasasContribucionesPartidaPO"),
	ContribucionPartida("557","Contribuciones_Partida_557","ContribucionesPartidaPO"),
	ObservacionesPartida("558","Observaciones_Partida_558","ObservacionesPartidaPO"),
	Rectificaciones("701","Rectificaciones_701","RectificacionesPO"),
	DiferenciasContribucionesPedimento("702","Diferencias_Contribuciones_Pedimento_702","DiferenciasContribucionesPedimentoPO"),
	Incidencias("Inci","Incidencias_Reconocimiento_Aduanero","IncidenciasReconocimientoAduaneroPO");
	
	
	private final String name;
	private final String table;
	private final String nameClass;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the table
	 */
	public String getTable() {
		return table;
	}
	
	/**
	 * @return the nameClass
	 */
	public String getNameClass() {
		return nameClass;
	}

	
	/**
	 * @param name
	 * @param table
	 * @param nameClass
	 */
	private Archivos(String name, String table, String nameClass) {
		this.name = name;
		this.table = table;
		this.nameClass = nameClass;
	}
	
	public static String obtenerTabla(String nameClass){
		for (Archivos item : Archivos.values()) {
			if (item.getNameClass().equals(nameClass)){
				//System.out.println(item.getTable());
				return item.getTable();
			}
		}
		return "";
	}
}
