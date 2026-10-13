package org.mx.modelo.gaia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UsState {

	private static List<UsState>	all;

	public static final UsState	Alabama		= new UsState("Alabama", "AL");
	public static final UsState	Alaska		= new UsState("Alaska", "AK");
	public static final UsState	Arizona		= new UsState("Arizona", "AZ");
	public static final UsState	Arkansas		= new UsState("Arkansas", "AR");
	public static final UsState	California		= new UsState("California", "CA");
	public static final UsState	Colorado		= new UsState("Colorado", "CO");
	public static final UsState	Connecticut		= new UsState("Connecticut", "CT");
	public static final UsState	Delaware		= new UsState("Delaware", "DE");
	public static final UsState	Florida		= new UsState("Florida", "FL");
	public static final UsState	Georgia		= new UsState("Georgia", "GA");
	public static final UsState	Hawaii		= new UsState("Hawaii", "HI");
	public static final UsState	Idaho			= new UsState("Idaho", "ID");
	public static final UsState	Illinois		= new UsState("Illinois", "IL");
	public static final UsState	Indiana		= new UsState("Indiana", "IN");
	public static final UsState	Iowa			= new UsState("Iowa", "IA");
	public static final UsState	Kansas		= new UsState("Kansas", "KS");
	public static final UsState	Kentucky		= new UsState("Kentucky", "KY");
	public static final UsState	Louisiana		= new UsState("Louisiana", "LA");
	public static final UsState	Maine			= new UsState("Maine", "ME");
	public static final UsState	Maryland		= new UsState("Maryland", "MD");
	public static final UsState	Massachusetts	= new UsState("Massachusetts", "MA");
	public static final UsState	Michigan		= new UsState("Michigan", "MI");
	public static final UsState	Minnesota		= new UsState("Minnesota", "MN");
	public static final UsState	Mississippi		= new UsState("Mississippi", "MS");
	public static final UsState	Missouri		= new UsState("Missouri", "MO");
	public static final UsState	Montana		= new UsState("Montana", "MT");
	public static final UsState	Nebraska		= new UsState("Nebraska", "NE");
	public static final UsState	Nevada		= new UsState("Nevada", "NV");
	public static final UsState	NewHampshire	= new UsState("New Hampshire", "NH");
	public static final UsState	NewJersey		= new UsState("New Jersey", "NJ");
	public static final UsState	NewMexico		= new UsState("New Mexico", "NM");
	public static final UsState	NewYork		= new UsState("New York", "NY");
	public static final UsState	NorthCarolina	= new UsState("North Carolina", "NC");
	public static final UsState	NorthDakota		= new UsState("North Dakota", "ND");
	public static final UsState	Ohio			= new UsState("Ohio", "OH");
	public static final UsState	Oklahoma		= new UsState("Oklahoma", "OK");
	public static final UsState	Oregon		= new UsState("Oregon", "OR");
	public static final UsState	Pennsylvania	= new UsState("Pennsylvania", "PA");
	public static final UsState	RhodeIsland		= new UsState("Rhode Island", "RI");
	public static final UsState	SouthCarolina	= new UsState("South Carolina", "SC");
	public static final UsState	SouthDakota		= new UsState("South Dakota", "SD");
	public static final UsState	Tennessee		= new UsState("Tennessee", "TN");
	public static final UsState	Texas			= new UsState("Texas", "TX");
	public static final UsState	Utah			= new UsState("Utah", "UT");
	public static final UsState	Vermont		= new UsState("Vermont", "VT");
	public static final UsState	Virginia		= new UsState("Virginia", "VA");
	public static final UsState	Washington		= new UsState("Washington", "WA");
	public static final UsState	WestVirginia	= new UsState("West Virginia", "WV");
	public static final UsState	Wisconsin		= new UsState("Wisconsin", "WI");
	public static final UsState	Wyoming		= new UsState("Wyoming", "WY");

	static {
		all = new ArrayList<UsState>();

		all.add(UsState.Alabama);
		all.add(UsState.Alaska);
		all.add(UsState.Arizona);
		all.add(UsState.Arkansas);
		all.add(UsState.California);
		all.add(UsState.Colorado);
		all.add(UsState.Connecticut);
		all.add(UsState.Delaware);
		all.add(UsState.Florida);
		all.add(UsState.Georgia);
		all.add(UsState.Hawaii);
		all.add(UsState.Idaho);
		all.add(UsState.Illinois);
		all.add(UsState.Indiana);
		all.add(UsState.Iowa);
		all.add(UsState.Kansas);
		all.add(UsState.Kentucky);
		all.add(UsState.Louisiana);
		all.add(UsState.Maine);
		all.add(UsState.Maryland);
		all.add(UsState.Massachusetts);
		all.add(UsState.Michigan);
		all.add(UsState.Minnesota);
		all.add(UsState.Mississippi);
		all.add(UsState.Missouri);
		all.add(UsState.Montana);
		all.add(UsState.Nebraska);
		all.add(UsState.Nevada);
		all.add(UsState.NewHampshire);
		all.add(UsState.NewJersey);
		all.add(UsState.NewMexico);
		all.add(UsState.NewYork);
		all.add(UsState.NorthCarolina);
		all.add(UsState.NorthDakota);
		all.add(UsState.Ohio);
		all.add(UsState.Oklahoma);
		all.add(UsState.Oregon);
		all.add(UsState.Pennsylvania);
		all.add(UsState.RhodeIsland);
		all.add(UsState.SouthCarolina);
		all.add(UsState.SouthDakota);
		all.add(UsState.Tennessee);
		all.add(UsState.Texas);
		all.add(UsState.Utah);
		all.add(UsState.Vermont);
		all.add(UsState.Virginia);
		all.add(UsState.Washington);
		all.add(UsState.WestVirginia);
		all.add(UsState.Wisconsin);
		all.add(UsState.Wyoming);

		all = Collections.unmodifiableList(all);
	}

	public static List<UsState> getAll() {
		return all;
	}

	private final String	name;
	private final String	iso2;

	public UsState(String name, String iso2) {
		this.name = name;
		this.iso2 = iso2;
	}

	public String getName() {
		return name;
	}

	public String getIso2() {
		return iso2;
	}

	public String getLabel() {
		return getName();
	}

	public String getValue() {
		return getIso2();
	}

	@Override
	public String toString() {
		return getName();
	}

	public static String getStateName(String code) {

		if (code == null || code.length() != 2)
			return null;

		code = code.trim().toUpperCase();

		for (UsState state : all) {
			if (state.getIso2().equals(code))
				return state.getName();
		}

		return null;
	}
}
