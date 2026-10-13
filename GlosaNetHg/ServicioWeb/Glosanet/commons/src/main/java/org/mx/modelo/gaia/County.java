package org.mx.modelo.gaia;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.io.IOUtils;

@XmlRootElement(name = "County")
public class County {

	private static List<County>	UK_COUNTIES;

	public static List<County> getUkCities() {
		if (UK_COUNTIES == null)
			UK_COUNTIES = parse("/org/mx/modelo/gaia/ukcounties.txt");
		return UK_COUNTIES;
	}

	static List<County> parse(String file) {
		InputStream is = City.class.getResourceAsStream(file);
		try {
			List<County> list = new ArrayList<County>();
			List<String> lines = IOUtils.readLines(is);
			for (String line : lines) {
				County c = new County();
				c.setName(line);
				list.add(c);
			}
			list = Collections.unmodifiableList(list);
			return list;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private String	name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
