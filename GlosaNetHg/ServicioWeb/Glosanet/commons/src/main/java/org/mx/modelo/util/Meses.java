/**
 * 
 */
package org.mx.modelo.util;

/**
 * @author yosimar.mendez
 *
 */
public enum Meses {

	ENERO (1,"ENERO", "JANUARY"),
	FEBRERO (2, "FEBRERO", "FEBRUARY"),
	MARZO (3, "MARZO", "MARCH"),
	ABRIL (4, "ABRIL", "APRIL"),
	MAYO (5, "MAYO", "MAY"),
	JUNIO (6, "JUNIO", "JUNE"),
	JULIO (7, "JULIO", "JULY"),
	AGOSTO (8, "AGOSTO", "AUGUST"),
	SEPTIEMBRE (9, "SEPTIEMBRE", "SEPTEMBER"),
	OCTUBRE (10, "OCTUBRE", "OCTOBER"),
	NOVIEMBRE (11, "NOVIEMBRE", "NOVEMBER"),
	DICIEMBRE (12, "DICIEMBRE", "DECEMBER");
	
	private final int mes;
	private final String name;
	private final String nameEN;
	

	private Meses(int mes, String name, String nameEN)
	{
		this.mes = mes;
		this.name = name;
		this.nameEN = nameEN;
	}

	/**
	 * @return the mes
	 */
	public int getMes() {
		return mes;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Busca el numero del mes apartir del nombre. 
	 * Si no encuentra el mes retorna un 0
	 * @param name
	 * @return 0
	 */
	public static int obtenerMes(String name)
	{
		for (Meses mes : Meses.values()) {
			if (mes.getName().equals(name) || mes.getNameEN().equals(name))
				return mes.getMes();
		}
		return 0;
	}

	/**
	 * @return the nameEN
	 */
	public String getNameEN() {
		return nameEN;
	}
	
}
