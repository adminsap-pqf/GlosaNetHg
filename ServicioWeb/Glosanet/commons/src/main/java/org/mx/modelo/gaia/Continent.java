package org.mx.modelo.gaia;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author sks 14-Sep-2004 16:50:01
 * @modified ymendez 24-Sep-2014 12:02:01
 */
@XmlRootElement(name = "Continent")
public enum Continent implements Comparable<Continent> {

	Africa("Africa","África"),

	Antartica("Antartica","Antártida"),

	Asia("Asia","Asia"),

	Europe("Europe","Europa"),

	NorthAmerica("North America","Norte América"),

	SouthAmerica("South America","Sur América"),

	Oceania("Oceania","Oceania");

	private final String	name;
	private final String nombre;
	private List<Country>	countries;

	private Continent(String name,String nombre) {
		this.name = name;
		this.nombre = nombre;
		countries = new ArrayList<Country>();
	}

	/**
	 * Returns a list of countries that reside in this continent.
	 */
	public List<Country> getCountries() {

		if (countries.isEmpty()) {
			for (Country country : Country.getAll()) {

				if (country.getContinent() == this)
					countries.add(country);
			}
		}
		return countries;
	}

	/**
	 * Regresa el nombre en Ingles
	 * @return the nombre
	 */
	public String getName() {
		return name;
	}

	/**
	 * Regresa el nombre en Espa??ol
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
}