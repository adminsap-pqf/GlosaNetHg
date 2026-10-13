package org.mx.modelo.gaia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * VERSION: 0.8
 * 
 * 14/10/06 - separated into own project - gaia
 * 
 * @author Stephen K Samuel 20-Aug-2003 16:49:51
 * 
 * You can use this for free but if your computer explodes I am not responsible.
 * 
 */
@XmlRootElement(name = "Country")
public class Country implements Comparable<Country> {

	private static List<Country>	all;

	public static final Country	Afghanistan				= new Country("Afghanistan","Afganistán", 4, "AF", "AFG", Continent.Asia);

	public static final Country	Albania				= new Country("Albania","Albania", 8, "AL", "ALB", Continent.Europe);
	public static final Country	Algeria				= new Country("Algeria","Argelia", 12, "DZ", "DZA", Continent.Africa);
	public static final Country	AmericanSamoa			= new Country("American Samoa","Samoa Americana", 16, "AS", "ASM", Continent.Oceania);
	public static final Country	Andorra				= new Country("Andorra","Andorra", 20, "AD", "AND", Continent.Europe);
	public static final Country	Angola				= new Country("Angola","Angola", 24, "AO", "AGO", Continent.Africa);
	public static final Country	Anguilla				= new Country("Anguilla","Anguilla", 660, "AI", "AIA", Continent.SouthAmerica);
	public static final Country	Antarctica				= new Country("Antarctica","Antártida", 0, "AQ", "", Continent.Antartica);
	public static final Country	AntiguaBarbuda			= new Country("Antigua And Barbuda","Antigua y Barbuda", 28, "AG", "ATG",
													Continent.SouthAmerica);
	public static final Country	Argentina				= new Country("Argentina","Argentina", 32, "AR", "ARG", Continent.SouthAmerica);
	public static final Country	Armenia				= new Country("Armenia","Armenia", 51, "AM", "ARM", Continent.Asia);
	public static final Country	Aruba					= new Country("Aruba","Aruba", 533, "AW", "ABW", Continent.SouthAmerica);
	public static final Country	Australia				= new Country("Australia","Australia", 36, "AU", "AUS", Continent.Oceania);
	public static final Country	Austria				= new Country("Austria","Austria", 40, "AT", "AUT", Continent.Europe);
	public static final Country	Azerbaijan				= new Country("Azerbaijan","Azerbaiyán", 31, "AZ", "AZE", Continent.Europe);
	public static final Country	Bahamas				= new Country("Bahamas","Bahamas", 44, "BS", "BHS", Continent.SouthAmerica);
	public static final Country	Bahrain				= new Country("Bahrain","Bahrein", 48, "BH", "BHR", Continent.Asia);
	public static final Country	Bangladesh				= new Country("Bangladesh","Bangladesh", 50, "BD", "BGD", Continent.Asia);
	public static final Country	Barbados				= new Country("Barbados","Barbados", 52, "BB", "BRB", Continent.SouthAmerica);
	public static final Country	Belarus				= new Country("Belarus","Bielorrusia", 112, "BY", "BLR", Continent.Europe);
	public static final Country	Belgium				= new Country("Belgium","Bélgica", 56, "BE", "BEL", Continent.Europe);
	public static final Country	Belize				= new Country("Belize","Belice", 84, "BZ", "BLZ", Continent.SouthAmerica);
	public static final Country	Benin					= new Country("Benin","Benín", 204, "BJ", "BEN", Continent.Africa);
	public static final Country	Bermuda				= new Country("Bermuda","Bermudas", 60, "BM", "BMU", Continent.SouthAmerica);
	public static final Country	Bhutan				= new Country("Bhutan","Bután", 64, "BT", "BTN", Continent.Asia);
	public static final Country	Bolivia				= new Country("Bolivia","Bolivia", 68, "BO", "BOL", Continent.SouthAmerica);
	public static final Country	BosniaHerzegovina			= new Country("Bosnia And Herzegovina","Bosnia-Herzegovina", 70, "BA", "BIH", Continent.Europe);
	public static final Country	Botswana				= new Country("Botswana","Botswana", 72, "BW", "BWA", Continent.Africa);
	public static final Country	BouvetIsland			= new Country("Bouvet Island","Isla Bouvet", 0, "BV", "", Continent.Antartica);
	public static final Country	Brazil				= new Country("Brazil","Brasil", 76, "BR", "BRA", Continent.SouthAmerica);
	public static final Country	BritishIndianOceanTerritory	= new Country("British Indian Ocean Territory","Territorio Británico del Océano Índico", 0, "IO", "",
													Continent.Asia);
	public static final Country	BruneiDarussalam			= new Country("Brunei Darussalam","Brunei Darussalam", 96, "BN", "BRN", Continent.Asia);
	public static final Country	Bulgaria				= new Country("Bulgaria","Bulgaria", 100, "BG", "BGR", Continent.Europe);
	public static final Country	BurkinaFaso				= new Country("Burkina Faso","Burkina Faso", 854, "BF", "BFA", Continent.Africa);
	public static final Country	Burundi				= new Country("Burundi","Burundi", 108, "BI", "BDI", Continent.Africa);
	public static final Country	Cambodia				= new Country("Cambodia","Camboya",116, "KH", "KHM", Continent.Asia);
	public static final Country	Cameroon				= new Country("Cameroon","Camerún", 120, "CM", "CMR", Continent.Africa);
	public static final Country	Canada				= new Country("Canada","Canadá", 124, "CA", "CAN", Continent.NorthAmerica, "en");
	public static final Country	CapeVerde				= new Country("Cape Verde","Cabo Verde", 132, "CV", "CPV", Continent.Africa);
	public static final Country	CaymanIslands			= new Country("Cayman Islands","Islas Caimán", 136, "KY", "CYM", Continent.SouthAmerica);
	public static final Country	CentralAfricanRepublic		= new Country("Central African Republic","República Centroafricana", 140, "CF", "CAF",
													Continent.Africa);
	public static final Country	Chad					= new Country("Chad","Chad", 148, "TD", "TCD", Continent.Africa);
	public static final Country	Chile					= new Country("Chile","Chile", 152, "CL", "CHL", Continent.SouthAmerica);
	public static final Country	China					= new Country("China","China", 156, "CN", "CHN", Continent.Asia, "zh");
	public static final Country	ChristmasIsland			= new Country("Christmas Island","Isla De Navidad", 0, "CX", "", Continent.Oceania);
	public static final Country	CocosIslands			= new Country("Cocos Islands","Islas Cocos", 0, "CC", "", Continent.Oceania);
	public static final Country	Colombia				= new Country("Colombia","Colombia", 170, "CO", "COL", Continent.SouthAmerica);
	public static final Country	Comoros				= new Country("Comoros","Comores", 174, "KM", "COM", Continent.Africa);
	public static final Country	Congo					= new Country("Congo","República del Congo", 178, "CG", "COG", Continent.Africa);
	public static final Country	DemocraticRepublicCongo		= new Country("Democratic Republic Of The Congo","República Democrática del Congo", 180, "CD", "COD",
													Continent.Africa);
	public static final Country	CookIslands				= new Country("Cook Islands","Islas Cook", 184, "CK", "COK", Continent.Oceania);
	public static final Country	CostaRica				= new Country("Costa Rica","Costa Rica", 188, "CR", "CRI", Continent.SouthAmerica);
	public static final Country	IvoryCoast				= new Country("Cote D'ivoire","Costa de Marfil", 384, "CI", "CIV", Continent.Africa);
	public static final Country	Croatia				= new Country("Croatia","Croacia", 191, "HR", "HRV", Continent.Europe);
	public static final Country	Cuba					= new Country("Cuba","Cuba", 192, "CU", "CUB", Continent.SouthAmerica);
	public static final Country	Cyprus				= new Country("Cyprus","Chipre", 196, "CY", "CYP", Continent.Europe);
	public static final Country	CzechRepublic			= new Country("Czech Republic","República Checa", 203, "CZ", "CZE", Continent.Europe);
	public static final Country	Denmark				= new Country("Denmark","Dinamarca", 208, "DK", "DNK", Continent.Europe);
	public static final Country	Djibouti				= new Country("Djibouti","Yibuti", 262, "DJ", "DJI", Continent.Africa);
	public static final Country	Dominica				= new Country("Dominica","Dominica", 212, "DM", "DMA", Continent.SouthAmerica);
	public static final Country	DominicanRepublic			= new Country("Dominican Republic","República Dominicana", 214, "DO", "DOM",
													Continent.SouthAmerica);
	public static final Country	Ecuador				= new Country("Ecuador", "Ecuador",218, "EC", "ECU", Continent.SouthAmerica);
	public static final Country	Egypt					= new Country("Egypt", "Egipto",818, "EG", "EGY", Continent.Africa);
	public static final Country	ElSalvador				= new Country("El Salvador", "El Salvador",222, "SV", "SLV", Continent.SouthAmerica);
	public static final Country	EquatorialGuinea			= new Country("Equatorial Guinea", "Guinea Ecuatorial",226, "GQ", "GNQ", Continent.Africa);
	public static final Country	Eritrea				= new Country("Eritrea", "Eritrea",232, "ER", "ERI", Continent.Africa);
	public static final Country	Estonia				= new Country("Estonia","Estonia", 233, "EE", "EST", Continent.Europe);
	public static final Country	Ethiopia				= new Country("Ethiopia", "Etiopía",231, "ET", "ETH", Continent.Africa);
	public static final Country	FalklandIslands			= new Country("Falkland Islands", "Islas Malvinas",238, "FK", "FLK",
													Continent.SouthAmerica);
	public static final Country	FaroeIslands			= new Country("Faroe Islands","Islas Feroe", 234, "FO", "FRO", Continent.Europe);
	public static final Country	Fiji					= new Country("Fiji", "Fiyi",242, "FJ", "FJI", Continent.Oceania);
	public static final Country	Finland				= new Country("Finland", "Finlandia",246, "FI", "FIN", Continent.Europe);
	public static final Country	France				= new Country("France", "Francia",250, "FR", "FRA", Continent.Europe, "fr");
	public static final Country	FrenchGuiana			= new Country("French Guiana", "Guayana Francesa",254, "GF", "GUF", Continent.SouthAmerica,
													"fr");
	public static final Country	FrenchPolynesia			= new Country("French Polynesia", "Polinesia Francesa",258, "PF", "PYF", Continent.Oceania,
													"fr");
	public static final Country	FrenchSouthernTerritories	= new Country("French Southern Territories", "Tierras Australes y Antárticas Francesas",0, "TF", "",
													Continent.Antartica);
	public static final Country	Gabon					= new Country("Gabon","Gabón", 266, "GA", "GAB", Continent.Africa);
	public static final Country	Gambia				= new Country("Gambia", "Gambia",270, "GM", "GMB", Continent.Africa);
	public static final Country	Georgia				= new Country("Georgia","Georgia",268, "GE", "GEO", Continent.Asia);
	public static final Country	Germany				= new Country("Germany", "Alemania",276, "DE", "DEU", Continent.Europe, "de");
	public static final Country	Ghana					= new Country("Ghana","Ghana", 288, "GH", "GHA", Continent.Africa);
	public static final Country	Gibraltar				= new Country("Gibraltar", "Gibraltar",292, "GI", "GIB", Continent.Europe);
	public static final Country	Greece				= new Country("Greece", "Grecia",300, "GR", "GRC", Continent.Europe);
	public static final Country	Greenland				= new Country("Greenland","Groenlandia", 304, "GL", "GRL", Continent.Europe);
	public static final Country	Grenada				= new Country("Grenada","Granada", 308, "GD", "GRD", Continent.SouthAmerica);
	public static final Country	Guadeloupe				= new Country("Guadeloupe","Guadalupe", 312, "GP", "GLP", Continent.SouthAmerica);
	public static final Country	Guam					= new Country("Guam", "Guam",316, "GU", "GUM", Continent.Oceania);
	public static final Country	Guatemala				= new Country("Guatemala", "Guatemala",320, "GT", "GTM", Continent.SouthAmerica);
	public static final Country	Guinea				= new Country("Guinea","República Guinea", 324, "GN", "GIN", Continent.Africa);
	public static final Country	GuineaBissau			= new Country("Guinea-bissau", "Guinea Bissau",624, "GW", "GNB", Continent.Africa);
	public static final Country	Guyana				= new Country("Guyana", "Guyana",328, "GY", "GUY", Continent.SouthAmerica);
	public static final Country	Haiti					= new Country("Haiti","Haiti", 332, "HT", "HTI", Continent.SouthAmerica);
	public static final Country	HeardMcdonald			= new Country("Heard Island And Mcdonald Islands", "Islas Heard y McDonald Islas",0, "HM", "",
													Continent.Antartica);
	public static final Country	Vatican				= new Country("Holy See (Vatican)", "NOMBREPAIS",336, "VA", "VAT", Continent.Europe);
	public static final Country	Honduras				= new Country("Honduras", "Honduras",340, "HN", "HND", Continent.SouthAmerica);
	public static final Country	HongKong				= new Country("Hong Kong", "Hong Kong",344, "HK", "HKG", Continent.Asia);
	public static final Country	Hungary				= new Country("Hungary", "Hungría",348, "HU", "HUN", Continent.Europe);
	public static final Country	Iceland				= new Country("Iceland","Islandia", 352, "IS", "ISL", Continent.Europe);
	public static final Country	India					= new Country("India", "India",356, "IN", "IND", Continent.Asia);
	public static final Country	Indonesia				= new Country("Indonesia","Indonesia", 360, "ID", "IDN", Continent.Asia);
	public static final Country	Iran					= new Country("Iran","Irán", 364, "IR", "IRN", Continent.Asia);
	public static final Country	Iraq					= new Country("Iraq", "Iraq",368, "IQ", "IRQ", Continent.Asia);
	public static final Country	Ireland				= new Country("Ireland", "Irlanda",372, "IE", "IRL", Continent.Europe);
	public static final Country	Israel				= new Country("Israel","Israel", 376, "IL", "ISR", Continent.Asia);
	public static final Country	Italy					= new Country("Italy","Italia", 380, "IT", "ITA", Continent.Europe, "it");
	public static final Country	Jamaica				= new Country("Jamaica", "Jamaica",388, "JM", "JAM", Continent.SouthAmerica);
	public static final Country	Japan					= new Country("Japan", "Japón",392, "JP", "JPN", Continent.Asia, "ja");
	public static final Country	Jordan				= new Country("Jordan","Jordania", 400, "JO", "JOR", Continent.Asia);
	public static final Country	Kazakhstan				= new Country("Kazakhstan","Kazajstán", 398, "KZ", "KAZ", Continent.Asia);
	public static final Country	Kenya					= new Country("Kenya", "Kenia",404, "KE", "KEN", Continent.Africa);
	public static final Country	Kiribati				= new Country("Kiribati", "Kiribati",296, "KI", "KIR", Continent.Oceania);
	public static final Country	NorthKorea				= new Country("North Korea", "Corea del Norte",408, "KP", "PRK", Continent.Asia, "ko");
	public static final Country	SouthKorea				= new Country("South Korea","Corea del Sur", 410, "KR", "KOR", Continent.Asia, "ko");
	public static final Country	Kuwait				= new Country("Kuwait", "Kuwait",414, "KW", "KWT", Continent.Asia);
	public static final Country	Kyrgyzstan				= new Country("Kyrgyzstan", "Kirguistán",417, "KG", "KGZ", Continent.Asia);
	public static final Country	Laos					= new Country("Laos", "Laos",418, "LA", "LAO", Continent.Asia);
	public static final Country	Latvia				= new Country("Latvia","Letonia", 428, "LV", "LVA", Continent.Europe);
	public static final Country	Lebanon				= new Country("Lebanon", "Líbano",422, "LB", "LBN", Continent.Asia);
	public static final Country	Lesotho				= new Country("Lesotho", "Lesotho",426, "LS", "LSO", Continent.Africa);
	public static final Country	Liberia				= new Country("Liberia", "Liberia",430, "LR", "LBR", Continent.Africa);
	public static final Country	Libya					= new Country("Libyan Arab Jamahiriya", "Libia",434, "LY", "LBY",
													Continent.Africa);
	public static final Country	Liechtenstein			= new Country("Liechtenstein","Liechtenstein", 438, "LI", "LIE", Continent.Europe);
	public static final Country	Lithuania				= new Country("Lithuania", "Lituania",440, "LT", "LTU", Continent.Europe);
	public static final Country	Luxembourg				= new Country("Luxembourg", "Luxemburgo",442, "LU", "LUX", Continent.Europe);
	public static final Country	Macao					= new Country("Macao","Macao", 446, "MO", "MAC", Continent.Asia);
	public static final Country	Macedonia				= new Country("Macedonia", "Macedonia",807, "MK", "MKD", Continent.Europe);
	public static final Country	Madagascar				= new Country("Madagascar","Madagascar", 450, "MG", "MDG", Continent.Africa);
	public static final Country	Malawi				    = new Country("Malawi", "Malawi",454, "MW", "MWI", Continent.Africa);
	public static final Country	Malaysia				= new Country("Malaysia", "Malasia",458, "MY", "MYS", Continent.Asia);
	public static final Country	Maldives				= new Country("Maldives", "Maldivas",462, "MV", "MDV", Continent.Asia);
	public static final Country	Mali					= new Country("Mali", "Malí",466, "ML", "MLI", Continent.Africa);
	public static final Country	Malta					= new Country("Malta","Malta", 470, "MT", "MLT", Continent.Europe);
	public static final Country	MarshallIslands			= new Country("Marshall Islands", "Islas Marshall",584, "MH", "MHL", Continent.Oceania);
	public static final Country	Martinique				= new Country("Martinique", "Martinica",474, "MQ", "MTQ", Continent.Oceania);
	public static final Country	Mauritania				= new Country("Mauritania","Mauritania",478, "MR", "MRT", Continent.Africa);
	public static final Country	Mauritius				= new Country("Mauritius","Mauricio", 480, "MU", "MUS", Continent.Africa);
	public static final Country	Mayotte				= new Country("Mayotte", "Mayotte",0, "YT", "", Continent.Africa);
	public static final Country	Mexico				= new Country("Mexico", "México",484, "MX", "MEX", Continent.NorthAmerica);
	public static final Country	Micronesia				= new Country("Micronesia", "Micronesia",583, "FM", "FSM", Continent.Oceania);
	public static final Country	Moldova				= new Country("Moldova, Republic Of","Moldavia", 498, "MD", "MDA", Continent.Europe);
	public static final Country	Monaco				= new Country("Monaco", "Mónaco",492, "MC", "MCO", Continent.Europe);
	public static final Country	Mongolia				= new Country("Mongolia","Mongolia", 496, "MN", "MNG", Continent.Asia);
	public static final Country	Montserrat				= new Country("Montserrat", "Montserrat",500, "MS", "MSR", Continent.SouthAmerica);
	public static final Country	Morocco				= new Country("Morocco", "Marruecos",504, "MA", "MAR", Continent.Africa);
	public static final Country	Mozambique				= new Country("Mozambique","Mozambique", 508, "MZ", "MOZ", Continent.Africa);
	public static final Country	Myanmar				= new Country("Myanmar", "Myanmar",104, "MM", "MMR", Continent.Asia);
	public static final Country	Namibia				= new Country("Namibia", "Namibia",516, "NA", "NAM", Continent.Africa);
	public static final Country	Nauru					= new Country("Nauru","Nauru", 520, "NR", "NRU", Continent.Oceania);
	public static final Country	Nepal					= new Country("Nepal", "Nepal",524, "NP", "NPL", Continent.Asia);
	public static final Country	Netherlands				= new Country("Netherlands","Holanda", 528, "NL", "NLD", Continent.Europe);
	public static final Country	NetherlandsAntilles		= new Country("Netherlands Antilles", "Antillas Holandesas",530, "AN", "ANT",
													Continent.SouthAmerica);
	public static final Country	NewCaledonia			= new Country("New Caledonia", "Nueva Caledonia	",540, "NC", "NCL", Continent.Oceania);
	public static final Country	NewZealand				= new Country("New Zealand","Nueva Zelanda	", 554, "NZ", "NZL", Continent.Oceania);
	public static final Country	Nicaragua				= new Country("Nicaragua","Nicaragua", 558, "NI", "NIC", Continent.SouthAmerica);
	public static final Country	Niger					= new Country("Niger", "Niger",562, "NE", "NER", Continent.Africa);
	public static final Country	Nigeria				= new Country("Nigeria", "Nigeria",566, "NG", "NGA", Continent.Africa);
	public static final Country	Niue					= new Country("Niue", "Niue",570, "NU", "NIU", Continent.Oceania);
	public static final Country	NorfolkIsland			= new Country("Norfolk Island", "NOMBREPAIS",574, "NF", "NFK", Continent.Oceania);
	public static final Country	NorthernMarianaIslands		= new Country("Northern Mariana Islands", "Marianas del Norte",580, "MP", "MNP",
													Continent.Oceania);
	public static final Country	Norway				= new Country("Norway", "Noruega",578, "NO", "NOR", Continent.Europe);
	public static final Country	Oman					= new Country("Oman", "Omán",512, "OM", "OMN", Continent.Asia);
	public static final Country	Pakistan				= new Country("Pakistan","Pakistán", 586, "PK", "PAK", Continent.Asia);
	public static final Country	Palau					= new Country("Palau","Palau", 585, "PW", "PLW", Continent.Oceania);
	public static final Country	PalestinianTerritory		= new Country("Palestinian Territory", "Territorios Palestinos",0, "PS", "", Continent.Asia);
	public static final Country	Panama				= new Country("Panama", "Panamá",591, "PA", "PAN", Continent.SouthAmerica);
	public static final Country	PapuaNewGuinea			= new Country("Papua New Guinea","Papúa-Nueva Guinea", 598, "PG", "PNG", Continent.Oceania);
	public static final Country	Paraguay				= new Country("Paraguay","Paraguay", 600, "PY", "PRY", Continent.SouthAmerica);
	public static final Country	Peru					= new Country("Peru", "Perú",604, "PE", "PER", Continent.SouthAmerica);
	public static final Country	Philippines				= new Country("Philippines","Filipinas", 608, "PH", "PHL", Continent.Asia);
	public static final Country	Pitcairn				= new Country("Pitcairn","Isla Pitcairn", 612, "PN", "PCN", Continent.Oceania);
	public static final Country	Poland				= new Country("Poland", "Polonia",616, "PL", "POL", Continent.Europe);
	public static final Country	Portugal				= new Country("Portugal", "Portugal",620, "PT", "PRT", Continent.Europe);
	public static final Country	PuertoRico				= new Country("Puerto Rico", "Puerto Rico",630, "PR", "PRI", Continent.SouthAmerica);
	public static final Country	Qatar					= new Country("Qatar","Qatar", 634, "QA", "QAT", Continent.Asia);
	public static final Country	Reunion				= new Country("Reunion", "Reunión",638, "RE", "REU", Continent.Africa);
	public static final Country	Romania				= new Country("Romania", "Rumanía",642, "RO", "ROM", Continent.Europe);
	public static final Country	Russia				= new Country("Russian Federation","Federación Rusa", 643, "RU", "RUS", Continent.Europe);
	public static final Country	Rwanda				= new Country("Rwanda", "Ruanda",646, "RW", "RWA", Continent.Africa);
	public static final Country	SaintHelena				= new Country("Saint Helena", "Santa Helena",654, "SH", "SHN", Continent.Africa);
	public static final Country	SaintKittsNevis			= new Country("Saint Kitts And Nevis","San Cristobal y Nevis", 659, "KN", "KNA",
													Continent.SouthAmerica);
	public static final Country	SaintLucia				= new Country("Saint Lucia", "Santa Lucía",662, "LC", "LCA", Continent.SouthAmerica);
	public static final Country	SaintPierreMiquelon		= new Country("Saint Pierre And Miquelon","San Pedro y Miquelón", 666, "PM", "SPM",
													Continent.SouthAmerica);
	public static final Country	SaintVincentGrenadines		= new Country("Saint Vincent And The Grenadines","San Vicente y las Granadinas", 670, "VC", "VCT",
													Continent.SouthAmerica);
	public static final Country	Samoa					= new Country("Samoa", "Samoa",882, "WS", "WSM", Continent.Oceania);
	public static final Country	SanMarino				= new Country("San Marino", "San Marino",674, "SM", "SMR", Continent.Europe);
	public static final Country	SaoTome				= new Country("Sao Tome And Principe", "Santo Tomé y Príncipe",678, "ST", "STP", Continent.Africa);
	public static final Country	SaudiArabia				= new Country("Saudi Arabia", "Arabia Saudita",682, "SA", "SAU", Continent.Asia);
	public static final Country	Senegal				= new Country("Senegal","Senegal", 686, "SN", "SEN", Continent.Africa);
	public static final Country	Seychelles				= new Country("Seychelles", "Seychelles",690, "SC", "SYC", Continent.Africa);
	public static final Country	SierraLeone				= new Country("Sierra Leone", "Sierra Leona",694, "SL", "SLE", Continent.Africa);
	public static final Country	Singapore				= new Country("Singapore", "Singapur",702, "SG", "SGP", Continent.Asia);
	public static final Country	Slovakia				= new Country("Slovakia","Eslovaquia", 703, "SK", "SVK", Continent.Europe);
	public static final Country	Slovenia				= new Country("Slovenia", "Eslovenia",705, "SI", "SVN", Continent.Europe);
	public static final Country	SolomonIslands			= new Country("Solomon Islands", "Islas Salomón",90, "SB", "SLB", Continent.Oceania);
	public static final Country	Somalia				= new Country("Somalia", "Somalia",706, "SO", "SOM", Continent.Africa);
	public static final Country	SouthAfrica				= new Country("South Africa", "Sudáfrica",710, "ZA", "ZAF", Continent.Africa);
	public static final Country	SouthGeorgiaSandwich		= new Country("South Georgia And The South Sandwich Islands","Georgia del Sur y las Islas Sandwich del Sur", 0, "GS",
													"", Continent.Antartica);
	public static final Country	Spain					= new Country("Spain", "España",724, "ES", "ESP", Continent.Europe);
	public static final Country	SriLanka				= new Country("Sri Lanka", "Sri Lanka",144, "LK", "LKA", Continent.Asia);
	public static final Country	Sudan					= new Country("Sudan","Sudán", 736, "SD", "SDN", Continent.Africa);
	public static final Country	Suriname				= new Country("Suriname", "Surinam",740, "SR", "SUR", Continent.SouthAmerica);
	public static final Country	SvalbardJanMayen			= new Country("Svalbard And Jan Mayen", "Svalbard y Jan Mayen",744, "SJ", "SJM",
													Continent.Europe);
	public static final Country	Swaziland				= new Country("Swaziland", "Swazilandia",748, "SZ", "SWZ", Continent.Africa);
	public static final Country	Sweden				= new Country("Sweden", "Suecia",752, "SE", "SWE", Continent.Europe);
	public static final Country	Switzerland				= new Country("Switzerland", "Suiza",756, "CH", "CHE", Continent.Europe);
	public static final Country	Syria					= new Country("Syrian Arab Republic", "Siria",760, "SY", "SYR", Continent.Asia);
	public static final Country	Taiwan				= new Country("Taiwan", "Taiwan",158, "TW", "TWN", Continent.Asia, "zh");
	public static final Country	Tajikistan				= new Country("Tajikistan", "Tadjikistan",762, "TJ", "TJK", Continent.Asia);
	public static final Country	Tanzania				= new Country("Tanzania, United Republic Of", "Tanzania",834, "TZ", "TZA",
													Continent.Africa);
	public static final Country	Thailand				= new Country("Thailand", "Tailandia",764, "TH", "THA", Continent.Asia);
	public static final Country	Timor					= new Country("Timor-leste", "Timor Oriental",0, "TL", "", Continent.Asia);
	public static final Country	Togo					= new Country("Togo", "Togo",768, "TG", "TGO", Continent.Africa);
	public static final Country	Tokelau				= new Country("Tokelau", "Tokelau",772, "TK", "TKL", Continent.Oceania);
	public static final Country	Tonga					= new Country("Tonga", "Tonga",776, "TO", "TON", Continent.Oceania);
	public static final Country	TrinidadTobago			= new Country("Trinidad And Tobago", "Trinidad y Tobago",780, "TT", "TTO",
													Continent.SouthAmerica);
	public static final Country	Tunisia				= new Country("Tunisia", "Túnez",788, "TN", "TUN", Continent.Africa);
	public static final Country	Turkey				= new Country("Turkey", "Turquía",792, "TR", "TUR", Continent.Europe);
	public static final Country	Turkmenistan			= new Country("Turkmenistan","Turkmenistan", 795, "TM", "TKM", Continent.Asia);
	public static final Country	TurksCaicosIslands		= new Country("Turks And Caicos Islands", "Islas Turcas y Caicos",796, "TC", "TCA",
													Continent.SouthAmerica);
	public static final Country	Tuvalu				= new Country("Tuvalu", "Tuvalu",798, "TV", "TUV", Continent.Oceania);
	public static final Country	Uganda				= new Country("Uganda", "Uganda",800, "UG", "UGA", Continent.Africa);
	public static final Country	Ukraine				= new Country("Ukraine", "Ucrania",804, "UA", "UKR", Continent.Europe);
	public static final Country	UAE					= new Country("United Arab Emirates", "Emiratos Árabes Unidos",784, "AE", "ARE", Continent.Asia);
	public static final Country	US					= new Country("United States", "Estados Unidos",840, "US", "USA", Continent.NorthAmerica,
													"en");
	public static final Country	USMinorOutlyingIslands		= new Country("United States Minor Outlying Islands","Estados Unidos Islas Menores Alejadas", 0, "UM", "",
													Continent.SouthAmerica);
	public static final Country	UK					= new Country("United Kingdom", "Reino Unido",826, "GB", "GBR", Continent.Europe, "en");
	public static final Country	Uruguay				= new Country("Uruguay", "Uruguay",858, "UY", "URY", Continent.SouthAmerica);
	public static final Country	Uzbekistan				= new Country("Uzbekistan", "Uzbekistán",860, "UZ", "UZB", Continent.Asia);
	public static final Country	Vanuatu				= new Country("Vanuatu", "Vanuatu",548, "VU", "VUT", Continent.Oceania);
	public static final Country	Venezuela				= new Country("Venezuela", "Venezuela",862, "VE", "VEN", Continent.SouthAmerica);
	public static final Country	Vietnam				= new Country("Vietnam", "Vietnam",704, "VN", "VNM", Continent.Asia);
	public static final Country	BritishVirginIslands		= new Country("British Virgin Islands", "Islas Virgenes Británicas",92, "VG", "VGB",
													Continent.SouthAmerica);
	public static final Country	USVirginIslands			= new Country("US Virgin Islands", "Islas Virgenes Americanas",850, "VI", "VIR",
													Continent.SouthAmerica);
	public static final Country	WallisFutuna			= new Country("Wallis And Futuna", "Wallis y Futuna",876, "WF", "WLF", Continent.Oceania);
	public static final Country	WesternSahara			= new Country("Western Sahara", "Sáhara Occidental",732, "EH", "ESH", Continent.Africa);
	public static final Country	Yemen					= new Country("Yemen","Yemen",887, "YE", "YEM", Continent.Asia);
	public static final Country	Yugoslavia				= new Country("Yugoslavia", "Yugoslavia",891, "YU", "YUG", Continent.Europe);
	public static final Country	Zambia				= new Country("Zambia", "Zambia",894, "ZM", "ZMB", Continent.Africa);
	public static final Country	Zimbabwe				= new Country("Zimbabwe", "Zimbabwe",716, "ZYA", "ZWE", Continent.Africa);
	static {

		all = new ArrayList<Country>();
		all.add(Country.UK);
		all.add(Country.Afghanistan);
		all.add(Country.Albania);
		all.add(Country.Algeria);
		all.add(Country.AmericanSamoa);
		all.add(Country.Andorra);
		all.add(Country.Angola);
		all.add(Country.Anguilla);
		all.add(Country.Antarctica);
		all.add(Country.AntiguaBarbuda);
		all.add(Country.Argentina);
		all.add(Country.Armenia);
		all.add(Country.Aruba);
		all.add(Country.Australia);
		all.add(Country.Austria);
		all.add(Country.Azerbaijan);
		all.add(Country.Bahamas);
		all.add(Country.Bahrain);
		all.add(Country.Bangladesh);
		all.add(Country.Barbados);
		all.add(Country.Belarus);
		all.add(Country.Belgium);
		all.add(Country.Belize);
		all.add(Country.Benin);
		all.add(Country.Bermuda);
		all.add(Country.Bhutan);
		all.add(Country.Bolivia);
		all.add(Country.BosniaHerzegovina);
		all.add(Country.Botswana);
		all.add(Country.BouvetIsland);
		all.add(Country.Brazil);
		all.add(Country.BritishIndianOceanTerritory);
		all.add(Country.BruneiDarussalam);
		all.add(Country.Bulgaria);
		all.add(Country.BurkinaFaso);
		all.add(Country.Burundi);
		all.add(Country.Cambodia);
		all.add(Country.Cameroon);
		all.add(Country.Canada);
		all.add(Country.CapeVerde);
		all.add(Country.CaymanIslands);
		all.add(Country.CentralAfricanRepublic);
		all.add(Country.Chad);
		all.add(Country.Chile);
		all.add(Country.China);
		all.add(Country.ChristmasIsland);
		all.add(Country.CocosIslands);
		all.add(Country.Colombia);
		all.add(Country.Comoros);
		all.add(Country.Congo);
		all.add(Country.DemocraticRepublicCongo);
		all.add(Country.CookIslands);
		all.add(Country.CostaRica);
		all.add(Country.IvoryCoast);
		all.add(Country.Croatia);
		all.add(Country.Cuba);
		all.add(Country.Cyprus);
		all.add(Country.CzechRepublic);
		all.add(Country.Denmark);
		all.add(Country.Djibouti);
		all.add(Country.Dominica);
		all.add(Country.DominicanRepublic);
		all.add(Country.Ecuador);
		all.add(Country.Egypt);
		all.add(Country.ElSalvador);
		all.add(Country.EquatorialGuinea);
		all.add(Country.Eritrea);
		all.add(Country.Estonia);
		all.add(Country.Ethiopia);
		all.add(Country.FalklandIslands);
		all.add(Country.FaroeIslands);
		all.add(Country.Fiji);
		all.add(Country.Finland);
		all.add(Country.France);
		all.add(Country.FrenchGuiana);
		all.add(Country.FrenchPolynesia);
		all.add(Country.FrenchSouthernTerritories);
		all.add(Country.Gabon);
		all.add(Country.Gambia);
		all.add(Country.Georgia);
		all.add(Country.Germany);
		all.add(Country.Ghana);
		all.add(Country.Gibraltar);
		all.add(Country.Greece);
		all.add(Country.Greenland);
		all.add(Country.Grenada);
		all.add(Country.Guadeloupe);
		all.add(Country.Guam);
		all.add(Country.Guatemala);
		all.add(Country.Guinea);
		all.add(Country.GuineaBissau);
		all.add(Country.Guyana);
		all.add(Country.Haiti);
		all.add(Country.HeardMcdonald);
		all.add(Country.Vatican);
		all.add(Country.Honduras);
		all.add(Country.HongKong);
		all.add(Country.Hungary);
		all.add(Country.Iceland);
		all.add(Country.India);
		all.add(Country.Indonesia);
		all.add(Country.Iran);
		all.add(Country.Iraq);
		all.add(Country.Ireland);
		all.add(Country.Israel);
		all.add(Country.Italy);
		all.add(Country.Jamaica);
		all.add(Country.Japan);
		all.add(Country.Jordan);
		all.add(Country.Kazakhstan);
		all.add(Country.Kenya);
		all.add(Country.Kiribati);
		all.add(Country.NorthKorea);
		all.add(Country.SouthKorea);
		all.add(Country.Kuwait);
		all.add(Country.Kyrgyzstan);
		all.add(Country.Laos);
		all.add(Country.Latvia);
		all.add(Country.Lebanon);
		all.add(Country.Lesotho);
		all.add(Country.Liberia);
		all.add(Country.Libya);
		all.add(Country.Liechtenstein);
		all.add(Country.Lithuania);
		all.add(Country.Luxembourg);
		all.add(Country.Macao);
		all.add(Country.Macedonia);
		all.add(Country.Madagascar);
		all.add(Country.Malawi);
		all.add(Country.Malaysia);
		all.add(Country.Maldives);
		all.add(Country.Mali);
		all.add(Country.Malta);
		all.add(Country.MarshallIslands);
		all.add(Country.Martinique);
		all.add(Country.Mauritania);
		all.add(Country.Mauritius);
		all.add(Country.Mayotte);
		all.add(Country.Mexico);
		all.add(Country.Micronesia);
		all.add(Country.Moldova);
		all.add(Country.Monaco);
		all.add(Country.Mongolia);
		all.add(Country.Montserrat);
		all.add(Country.Morocco);
		all.add(Country.Mozambique);
		all.add(Country.Myanmar);
		all.add(Country.Namibia);
		all.add(Country.Nauru);
		all.add(Country.Nepal);
		all.add(Country.Netherlands);
		all.add(Country.NetherlandsAntilles);
		all.add(Country.NewCaledonia);
		all.add(Country.NewZealand);
		all.add(Country.Nicaragua);
		all.add(Country.Niger);
		all.add(Country.Nigeria);
		all.add(Country.Niue);
		all.add(Country.NorfolkIsland);
		all.add(Country.NorthernMarianaIslands);
		all.add(Country.Norway);
		all.add(Country.Oman);
		all.add(Country.Pakistan);
		all.add(Country.Palau);
		all.add(Country.PalestinianTerritory);
		all.add(Country.Panama);
		all.add(Country.PapuaNewGuinea);
		all.add(Country.Paraguay);
		all.add(Country.Peru);
		all.add(Country.Philippines);
		all.add(Country.Pitcairn);
		all.add(Country.Poland);
		all.add(Country.Portugal);
		all.add(Country.PuertoRico);
		all.add(Country.Qatar);
		all.add(Country.Reunion);
		all.add(Country.Romania);
		all.add(Country.Russia);
		all.add(Country.Rwanda);
		all.add(Country.SaintHelena);
		all.add(Country.SaintKittsNevis);
		all.add(Country.SaintLucia);
		all.add(Country.SaintPierreMiquelon);
		all.add(Country.SaintVincentGrenadines);
		all.add(Country.Samoa);
		all.add(Country.SanMarino);
		all.add(Country.SaoTome);
		all.add(Country.SaudiArabia);
		all.add(Country.Senegal);
		all.add(Country.Seychelles);
		all.add(Country.SierraLeone);
		all.add(Country.Singapore);
		all.add(Country.Slovakia);
		all.add(Country.Slovenia);
		all.add(Country.SolomonIslands);
		all.add(Country.Somalia);
		all.add(Country.SouthAfrica);
		all.add(Country.SouthGeorgiaSandwich);
		all.add(Country.Spain);
		all.add(Country.SriLanka);
		all.add(Country.Sudan);
		all.add(Country.Suriname);
		all.add(Country.SvalbardJanMayen);
		all.add(Country.Swaziland);
		all.add(Country.Sweden);
		all.add(Country.Switzerland);
		all.add(Country.Syria);
		all.add(Country.Taiwan);
		all.add(Country.Tajikistan);
		all.add(Country.Tanzania);
		all.add(Country.Thailand);
		all.add(Country.Timor);
		all.add(Country.Togo);
		all.add(Country.Tokelau);
		all.add(Country.Tonga);
		all.add(Country.TrinidadTobago);
		all.add(Country.Tunisia);
		all.add(Country.Turkey);
		all.add(Country.Turkmenistan);
		all.add(Country.TurksCaicosIslands);
		all.add(Country.Tuvalu);
		all.add(Country.Uganda);
		all.add(Country.Ukraine);
		all.add(Country.UAE);
		all.add(Country.US);
		all.add(Country.USMinorOutlyingIslands);
		all.add(Country.Uruguay);
		all.add(Country.Uzbekistan);
		all.add(Country.Vanuatu);
		all.add(Country.Venezuela);
		all.add(Country.Vietnam);
		all.add(Country.BritishVirginIslands);
		all.add(Country.USVirginIslands);
		all.add(Country.WallisFutuna);
		all.add(Country.WesternSahara);
		all.add(Country.Yemen);
		all.add(Country.Yugoslavia);
		all.add(Country.Zambia);
		all.add(Country.Zimbabwe);

		Collections.sort(all);

		all = Collections.unmodifiableList(all);

	}

	/**
	 * Returns a list of all countries
	 * 
	 */
	public static List<Country> getAll() {
		return all;
	}

	/**
	 * Returns a list of countries within the EU vat zone
	 */
	public static List<Country> getEuVatZone() {

		List<Country> eu = new ArrayList<Country>();

		eu.add(Country.UK);
		eu.add(Country.Austria);
		eu.add(Country.Belgium);
		eu.add(Country.Cyprus);
		eu.add(Country.CzechRepublic);
		eu.add(Country.Denmark);
		eu.add(Country.Estonia);
		eu.add(Country.Finland);
		eu.add(Country.France);
		eu.add(Country.Germany);
		eu.add(Country.Greece);
		eu.add(Country.Hungary);
		eu.add(Country.Ireland);
		eu.add(Country.Italy);
		eu.add(Country.Latvia);
		eu.add(Country.Lithuania);
		eu.add(Country.Luxembourg);
		eu.add(Country.Malta);
		eu.add(Country.Netherlands);
		eu.add(Country.Poland);
		eu.add(Country.Portugal);
		eu.add(Country.Slovakia);
		eu.add(Country.Slovenia);
		eu.add(Country.Spain);
		eu.add(Country.Sweden);

		return eu;
	}

	/**
	 *  Returns the country instance matching the Iso number code or null if none do
	 */
	public static Country getInstance(int numberCode) {

		if (numberCode == 0)
			return null;

		for (Country country : all) {
			if (country.getIsoNumber3() == numberCode)
				return country;
		}

		return null;
	}

	/**
	 * Returns the country that matches the 2 or 3 character alpha code passed in  or null if no country matches.
	 *
	 */
	public static Country getInstance(String code) {

		if (code == null || code.length() < 2 || code.length() > 3)
			return null;

		code = code.trim().toUpperCase();

		for (Country country : all) {
			if (country.getIsoAlpha2().equals(code) || country.getIsoAlpha3().equals(code))
				return country;
		}

		return null;
	}

	private final Continent	continent;
	private final int		isoNumber3;
	private final String	name, isoAlpha2, isoAlpha3,nombre;
	private String		language;

	protected Country(String name,String nombre, int number, String iso2, String iso3, Continent continent) {

		this.name = name;
		this.isoNumber3 = number;
		this.isoAlpha2 = iso2;
		this.isoAlpha3 = iso3;
		this.continent = continent;
		this.language = "en";
		this.nombre=nombre;
	}

	protected Country(String name,String nombre, int number, String iso2, String iso3, Continent continent, String language) {

		this(name,nombre, number, iso2, iso3, continent);
		this.language = language;
	}
	
	public static Country obtenerAbreviacionPais(String pais)
	{
		for (Country country : Country.getAll()) {

			if (country.getName().equalsIgnoreCase(pais))
			{
				return country;
			}
		}
		
		return null;
	}

	/*
	 * @see
	 * java.lang.Comparable#compareTo(com.liferay.util.util.world.Country)
	 */
	@Override
	public int compareTo(Country arg0) {

		if (this == arg0)
			return 0;

		else if (this == UK)
			return -1;

		else if (arg0 == UK)
			return 1;

		return getName().compareTo(arg0.getName());
	}

	@Override
	public boolean equals(Object obj) {
		return obj == this;
	}

	/**
	 * Returns the Continent enum that is applicable for this country.
	 */
	public Continent getContinent() {
		return continent;
	}

	/**
	 * Returns the 3 letter ISO currency code for this country
	 */
	public Currency getCurrency() {
		return Currency.getInstance(isoAlpha3);
	}

	/**
	 * Returns the ISO 2 character alpha code for this country
	 * 
	 * @return
	 */
	public String getIsoAlpha2() {
		return isoAlpha2;
	}

	/**
	 * Returns the ISO 3 character alpha code for this country
	 * 
	 * @return
	 */
	public String getIsoAlpha3() {
		return isoAlpha3;
	}

	/**
	 * Returns the ISO 3 digit number code for this country
	 * 
	 * @return
	 */
	public int getIsoNumber3() {
		return isoNumber3;
	}

	public String getLabel() {
		return getName();
	}

	/**
	 * Returns the Language code for this country
	 *
	 * @return language code
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Returns the name for this country
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	public String getNombre(){
		return nombre;
	}

	public String getValue() {
		return getIsoAlpha3();
	}

	@Override
	public String toString() {
		return name;
	}
}