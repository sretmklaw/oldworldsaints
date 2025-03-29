package com.cimeliarchium.enums;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * This enumeration provides static copies of rows stored in the database-backed 'reference' table.
 * Its purpose is to allow unauthenticated visitors to view a static listing of References 
 * from the 'About' view, without having to perform extraneous calls to the database.
 */
public enum ReferenceTitleEnum {

	/**
	 * Print References follow
	 */

	GIBB_ET_AL_ENCYCLOPAEDIA_OF_ISLAM(
			"'The Encyclopaedia of Islam,' Hamilton A. R. Gibb et al., E. J. Brill (1986)", 
			null),

	SINGER_ET_AL_JEWISH_ENCYCLOPEDIA(
			"'The Jewish Encyclopedia,' Isidore Singer et al., Funk & Wagnalls (1901)", 
			null),

	BUTLER_LIVES_OF_THE_FATHERS(
			"'The Lives of the Fathers,' Alban Butler, James Duffy (1866)", 
			null),

	ALLEN_STAR_NAMES(
			"'Star Names and Their Meanings,' Richard Hinckley Allen, G. E. Stechert (1899)", 
			null),

	/**
	 * Web References follow
	 */

	CELTIC_OLD_ENGLISH_SAINTS(
			"Celtic and Old English Saints", 
			"https://web.archive.org/web/20220315143410/https://celticsaints.org/"),

	DENIS_MOSKOWITZ(
			"Denis M. Moskowitz: New Constellation Symbols",
			"https://web.archive.org/web/20231002181936/https://suberic.net/~dmm/astro/constellations.html"),

	FOUR_WEEK_PSALTER(
			"The Four Week Psalter",
			"https://web.archive.org/web/20211114130949/https://catholic-resources.org/LoH/Psalter-Hours.html"),

	GCATHOLIC_SAINTS(
			"GCatholic: Saints and Blesseds", 
			"https://web.archive.org/web/20220202174853/https://www.gcatholic.org/saints/index.htm"),

	HABIBUR_HIJRI_CALENDAR(
			"Habibur: Islamic Hijri Calendar For 1400 Years", 
			"https://web.archive.org/web/20220202113214/https://www.habibur.com/hijri/"),

	HEBCAL_JEWISH_HOLIDAY_CALENDAR(
			"Jewish Holiday Calendars & Hebrew Date Converter",
			"https://web.archive.org/web/20220209201356/https://www.hebcal.com/"),

	MEDIEVAL_TRADE_NETWORKS_MAP(
			"Martin Mansson: Medieval Trade Networks Map",
			"https://web.archive.org/web/20210801052935/https://imgur.com/MsXaOdV"),
	
	MUQAWWIM_NEAR_EAST_CALENDAR_CONVERTER(
			"Muqawwim.com: Calendar Converter for Near East Historians",
			"https://web.archive.org/web/20220311021843/https://www.muqawwim.com/"),

	POETICON_ASTRONOMICON(
			"Gaius Julius Hyginus: Poeticon Astronomicon",
			"https://archive.org/details/ap_20200203"),

	SUNNAH_HADITH_OF_MUHAMMAD(
			"Sunnah.com: The Hadith of the Prophet Muhammad", 
			"https://web.archive.org/web/20220309070205/https://sunnah.com/"),

	UNIVERSITY_OF_MICHIGAN_KJV(
			"University of Michigan Library: King James Bible", 
			"https://web.archive.org/web/20220204175928/https://quod.lib.umich.edu/k/kjv/browse.html"),

	UNIVERSITY_OF_MICHIGAN_KORAN(
			"University of Michigan Library: The Koran", 
			"https://web.archive.org/web/20220129080612/https://quod.lib.umich.edu/k/koran/browse.html"),

	WORLD_MAP_IN_THE_SKY(
			"World Map in the Sky: The Zenith Map",
			"https://zenithmap.netlify.app/?lon=45");

	private final String title;

	private final String hyperlink;

	ReferenceTitleEnum(String title, String hyperlink) {
		this.title = title;
		this.hyperlink = hyperlink;
	}

	public String getTitle() {
		return this.title;
	}

	public String getHyperlink() {
		return this.hyperlink;
	}

	public static List<String> getPrintReferenceTitles() {
		final List<String> resultList = new LinkedList<>();
		resultList.add(GIBB_ET_AL_ENCYCLOPAEDIA_OF_ISLAM.getTitle());
		resultList.add(SINGER_ET_AL_JEWISH_ENCYCLOPEDIA.getTitle());
		resultList.add(BUTLER_LIVES_OF_THE_FATHERS.getTitle());
		resultList.add(ALLEN_STAR_NAMES.getTitle());
		return resultList;
	}

	public static Map<String,String> getWebReferences() {
		final HashMap<String,String> map = new LinkedHashMap<>();
		map.put(CELTIC_OLD_ENGLISH_SAINTS.getTitle(), CELTIC_OLD_ENGLISH_SAINTS.getHyperlink());
		map.put(DENIS_MOSKOWITZ.getTitle(), DENIS_MOSKOWITZ.getHyperlink());
		map.put(FOUR_WEEK_PSALTER.getTitle(), FOUR_WEEK_PSALTER.getHyperlink());
		map.put(GCATHOLIC_SAINTS.getTitle(), GCATHOLIC_SAINTS.getHyperlink());
		map.put(HABIBUR_HIJRI_CALENDAR.getTitle(), HABIBUR_HIJRI_CALENDAR.getHyperlink());
		map.put(HEBCAL_JEWISH_HOLIDAY_CALENDAR.getTitle(), HEBCAL_JEWISH_HOLIDAY_CALENDAR.getHyperlink());
		map.put(MEDIEVAL_TRADE_NETWORKS_MAP.getTitle(), MEDIEVAL_TRADE_NETWORKS_MAP.getHyperlink());
		map.put(MUQAWWIM_NEAR_EAST_CALENDAR_CONVERTER.getTitle(), MUQAWWIM_NEAR_EAST_CALENDAR_CONVERTER.getHyperlink());
		map.put(POETICON_ASTRONOMICON.getTitle(), POETICON_ASTRONOMICON.getHyperlink());
		map.put(SUNNAH_HADITH_OF_MUHAMMAD.getTitle(), SUNNAH_HADITH_OF_MUHAMMAD.getHyperlink());
		map.put(UNIVERSITY_OF_MICHIGAN_KJV.getTitle(), UNIVERSITY_OF_MICHIGAN_KJV.getHyperlink());
		map.put(UNIVERSITY_OF_MICHIGAN_KORAN.getTitle(), UNIVERSITY_OF_MICHIGAN_KORAN.getHyperlink());
		map.put(WORLD_MAP_IN_THE_SKY.getTitle(), WORLD_MAP_IN_THE_SKY.getHyperlink());
		return map;
	}
}
