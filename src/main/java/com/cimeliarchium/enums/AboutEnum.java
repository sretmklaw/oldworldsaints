package com.cimeliarchium.enums;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * This enumeration stores lengthy blocks of text that are formatted and displayed within the 'About' view.
 */
public enum AboutEnum {

	VERSION_RELEASE_NOTES(0L,
			"versionReleaseNotes",
			"Version - Release 1.1.0",
			"This website was developed beginning in March 2020, and has been active since 04 May 2021. "
			+ "New user registration has been enabled since 08 April 2024, which happened to coincide with "
			+ "Easter and Eid al-Fitr, as well as the last total solar eclipse in North America until 2044."),

	HEADING_1(1L,
			"what-is-old-world",
			"What is the Old World?"),

	HEADING_2(2L,
			"who-are-saints",
			"Who are the Saints?"),

	HEADING_3(3L,
			"how-to-use",
			"How do I use this site?"),

		HEADING_3_A(31L,
				"how-to-use-registration",
				"Register"),

		HEADING_3_B(32L,
				"how-to-use-main",
				"Main"),

		HEADING_3_C(33L,
				"how-to-use-search",
				"Search"),

	HEADING_4(4L,
			"why-written-this-way",
			"Why are entries written this way?"),

	HEADING_5(5L,
			"what-sources-used",
			"What sources were used?"),

	HEADING_6(6l,
			"how-to-contribute",
			"How can I contribute?"),

	/**
	 * Definition of 'Old World' associated with a map of Nations having Commemorations
	 */
	PARAGRAPH_1_A(101L,
			"paragraph1a",
			null,
			"The term 'Old World' describes the context of time and place in which "
			+ "early European, Asian, and African peoples established vital networks "
			+ "for the mutual exchange of goods and ideas among themselves. "
			+ "In this milieu of trade and proselytization, Jews, Christians, and Muslims "
			+ "- all claiming spiritual descent from a shared lineage of ancient monotheistic Prophets "
			+ "- propagated throughout the known inhabited world a common vocabulary of values and symbols."),

	PARAGRAPH_1_B(102L,
			"paragraph1b",
			null,
			"Hence, an understanding of the 'Age of Faith', extending from Antiquity to the Late Middle Ages, "
			+ "forms the essential foundation for discerning the origins of globalization prior to the "
			+ "the establishment of the modern secular geopolitical order. For the purposes of this study, "
			+ "the Peace of Westphalia (A.D. 1648) has been selected as that end-date, given its establishment "
			+ "of tenets for national sovereignty and international law, apart from religious creed or belief."),

	PARAGRAPH_1_C(103L,
			"paragraph1c",
			"Map Details",
			"When displaying map points, the following icons are used to indicate the location's type and culture:"),

	PARAGRAPH_1_D(104L,
			"paragraph1d",
			null,
			"Additionally, the following icons are tied to places of special cultural importance, "
			+ "which can be viewed as a city-level zoomed inset map when searching by location."),

	/**
	 * Definition of 'Saints' associated with a chart of Commemorations by Nationality and Century
	 */
	PARAGRAPH_2_A(201L,
			"paragraph2a",
			null,
			"Saints are historical or semi-mythical figures whose lives are considered models of virtue "
			+ "worthy of emulation in the Jewish, Christian, and Islamic traditions, either as a result of "
			+ "official sanction by religious authorities, or through the popular acclaim of the faithful. "
			+ "The veneration of saints has persisted from the earliest days down to the present, "
			+ "despite opposition based upon the widespread embrace of Enlightenment values "
			+ "demanding empirical evidence as the basis for belief, as well as the influence of "
			+ "iconoclastic reform movements seeking a return to so-called fundamental religious principles."),

	PARAGRAPH_2_B(202L,
			"paragraph2b",
			null,
			"Nevertheless, confidence in the efficacy of saints' intercession - as well as "
			+ "the supernatural powers which their outstanding holiness is supposed to have conferred upon them - "
			+ "was at one time so widespread that virtually every social group, productive activity, "
			+ "and aspect of human experience became associated with their supposed patronage. "
			+ "For this reason, the institution of sainthood retains equal importance "
			+ "as an aid to the faith of the believer, and as an essential cultural and historical context "
			+ "for the secular-minded student of history."),

	PARAGRAPH_2_C(203L,
			"paragraph2c",
			"Calendar Details",
			"The following icons are used to indicate which calendars and rites observe a specific commemoration:"),

	PARAGRAPH_2_D(204L,
			"paragraph2d",
			"Celestial Map Details",
			"Since time immemorial people have naturally looked to the heavens as the permanent abode of the "
			+ "sanctified dead and of other spiritual beings, sometimes going so far as to identify them with "
			+ "celestial objects or phenomena. Hence, this site provides an interactive star chart with which "
			+ "users can explore traditional myths and legends associated with these heavenly bodies:"),

	PARAGRAPH_2_E(205L,
			"paragraph2e",
			null,
			"When displaying celestial map points, the following icons are used to indicate a star's designations:"),

	/**
	 * Description of site structure associated with screenshots of Main and Search views
	 */

	PARAGRAPH_3_A(301L,
			"paragraph3a",
			null,
			"During registration, users can select to display commemorations from across all available calendars, "
			+ "or they may opt to display only those labels and entries associated with a single denomination. "
			+ "The following information must be provided in order to complete registration:"),

	PARAGRAPH_3_B(302L,
			"paragraph3b",
			null,
			"Following login, the top of the main page will display current astronomical phenomena, including "
			+ "lunar phase, celestial culminations, and position of the rising sun within the tropical zodiac, "
			+ "as outlined in Ptolemy's authoritative cosmology dating from Late Antiquity, the 'Almagest'. "
			+ "Comparative analyses of the symbolic duodecimal groupings in each of the three major traditions "
			+ "- the Twelve Tribes of Israel, the Twelve Apostles of Christ, and the Twelve Imams of Shiism - "
			+ "with the zodiacal mythology of ancient Near Eastern cultures can be viewed in an expander field: "),

	PARAGRAPH_3_C(303L,
			"paragraph3c",
			null,
			"Below the celestial date information, commemorations are shown "
			+ "corresponding to the current day for the selected calendar(s). "
			+ "Finally, a selection from the Psalms of David - the Hebrew 'Tehillim' and Arabic 'Zabur' - "
			+ "will be shown based upon a uniform monthly cycle of readings distributed across five liturgical hours, "
			+ "testifying to the shared structure of daily prayer and veneration of the text across all three faiths:"),

	PARAGRAPH_3_D(304L,
			"paragraph3d",
			null,
			"At the lefthand side of each screen, a search menu is available for registered users "
			+ "to browse commemorations based upon the following defined criteria. "
			+ "Note that at any time, default filtering by rite can be disabled "
			+ "to view search results across all calendars."),

	/**
	 * Explanation of editorial style and general disclaimers
	 */

	PARAGRAPH_4_A(401L,
			"paragraph4a",
			"Note on Translation",
			"Given that the vast majority of source material for the subject at hand is written in languages "
			+ "other than English, the author has endeavored to rely on a number of respected translations and "
			+ "primary source material written by others (see 'Sources'). However, editorial license has been exercised "
			+ "when settling on a standard convention for the transliteration of non-English names and titles. "
			+ "Preference has been given to comprehensibility over technical accuracy, e.g. the Arabic "
			+ "'Ramadhan' and 'Caliphate.' At the same time, however, an attempt has been made to preserve some "
			+ "titles and terminology employed in the context of liturgical observances, such as lunar phases and "
			+ "zodiac constellations."),

	PARAGRAPH_4_B(402L,
			"paragraph4b",
			"Editorial Style",
			"Insofar as possible, entries have been written from a neutral editorial perspective "
			+ "which employs qualifying language - stating for example, that a saint "
			+ "is 'believed to' or 'reported to' have performed some miracle. "

			+ "This has not been done with the intention of disparaging or belittling deeply-held beliefs, "
			+ "but is rather designed to make the site accessible to as broad an audience as possible."),

	PARAGRAPH_4_C(403L,
			"paragraph4c",
			"General Disclaimer",
			"Users who ascribe to a specific religious denomination may object to the inclusion of specific "
			+ "events or individuals which they consider problematic, but which are honored in other calendars. "
			+ "Note that in addition to disclaiming any offense or injury resulting from the "
			+ "perception of insufficient respect paid to the subject of discussion, "
			+ "the author reiterates the stated goal of maintaining a neutral perspective towards historical events, "
			+ "and reserves the right to ban any user at any time, and for any reason."),

	PARAGRAPH_4_D(404L,
			"paragraph4d",
			"Terms of Use",
			"Although every effort has been made to verify the accuracy of information displayed on this site, "
			+ "because the subject matter is by its very nature subject to belief and interpretation, "
			+ "the author neither condones nor advises its use as a primary source for scholarly research."),

	/**
	 * List of references and suggestions for further reading
	 */

	PARAGRAPH_5_A(501L,
			"paragraph5a",
			"Print References",
			"Information contained in the following printed works has been used "
			+ "as primary sources for multiple entries:"),

	PARAGRAPH_5_B(502L,
			"paragraph5b",
			"Web References",
			"The following electronic sources were also used "
			+ "to obtain additional information used in multiple entries:"),

	/**
	 * Instructions for contributing new entries or making a donation to support site maintenance
	 */

	PARAGRAPH_6_A(601L,
			"paragraph6a",
			"Make a Donation",
			"Financial contributions are encouraged, to support the costs of site maintenance and upkeep. "
			+ "The 'Donate Now' link available to registered users offers the option of secure electronic "
			+ "transactions using your PayPal account.");

	private Long rank;

	private String code;

	private String title;

	private String content;

	private AboutEnum(Long rank, String code, String title) {
		this.rank = rank;
		this.code = code;
		this.title = title;
	}

	private AboutEnum(Long rank, String code, String title, String content) {
		this.rank = rank;
		this.code = code;
		this.title = title;
		this.content = content;
	}

	public Long getRank() {
		return rank;
	}

	public String getCode() {
		return code;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public static Map<Long,AboutEnum> getAboutHeadingMap() {
		final Map<Long,AboutEnum> map = new LinkedHashMap<>();
		map.put(AboutEnum.HEADING_1.getRank(), AboutEnum.HEADING_1);
		map.put(AboutEnum.HEADING_2.getRank(), AboutEnum.HEADING_2);
		map.put(AboutEnum.HEADING_3.getRank(), AboutEnum.HEADING_3);
		map.put(AboutEnum.HEADING_4.getRank(), AboutEnum.HEADING_4);
		map.put(AboutEnum.HEADING_5.getRank(), AboutEnum.HEADING_5);
		map.put(AboutEnum.HEADING_6.getRank(), AboutEnum.HEADING_6);
		return map;
	}

	public static Map<Long,AboutEnum> getHowToUseSubHeadingMap() {
		final Map<Long,AboutEnum> map = new LinkedHashMap<>();
		map.put(AboutEnum.HEADING_3_A.getRank(), AboutEnum.HEADING_3_A);
		map.put(AboutEnum.HEADING_3_B.getRank(), AboutEnum.HEADING_3_B);
		map.put(AboutEnum.HEADING_3_C.getRank(), AboutEnum.HEADING_3_C);
		return map;
	}

	public static Long getActiveHowToUseSubHeading(String code) {
		if (code != null) {
			if (code.equals(AboutEnum.HEADING_3_A.getCode())) {
				return AboutEnum.HEADING_3_A.getRank();
			} else if (code.equals(AboutEnum.HEADING_3_B.getCode())) {
				return AboutEnum.HEADING_3_B.getRank();
			} else if (code.equals(AboutEnum.HEADING_3_C.getCode())) {
				return AboutEnum.HEADING_3_C.getRank();
			} else {
				return AboutEnum.HEADING_3_A.getRank();
			}
		} else {
			return AboutEnum.HEADING_3_A.getRank();
		}
	}

	public static Map<Long,AboutEnum> getAboutParagraphMap() {
		final Map<Long,AboutEnum> map = new LinkedHashMap<>();
		map.put(AboutEnum.PARAGRAPH_1_A.getRank(), AboutEnum.PARAGRAPH_1_A);
		map.put(AboutEnum.PARAGRAPH_1_B.getRank(), AboutEnum.PARAGRAPH_1_B);
		map.put(AboutEnum.PARAGRAPH_1_C.getRank(), AboutEnum.PARAGRAPH_1_C);
		map.put(AboutEnum.PARAGRAPH_1_D.getRank(), AboutEnum.PARAGRAPH_1_D);
		map.put(AboutEnum.PARAGRAPH_2_A.getRank(), AboutEnum.PARAGRAPH_2_A);
		map.put(AboutEnum.PARAGRAPH_2_B.getRank(), AboutEnum.PARAGRAPH_2_B);
		map.put(AboutEnum.PARAGRAPH_2_C.getRank(), AboutEnum.PARAGRAPH_2_C);
		map.put(AboutEnum.PARAGRAPH_2_D.getRank(), AboutEnum.PARAGRAPH_2_D);
		map.put(AboutEnum.PARAGRAPH_2_E.getRank(), AboutEnum.PARAGRAPH_2_E);
		map.put(AboutEnum.PARAGRAPH_3_A.getRank(), AboutEnum.PARAGRAPH_3_A);
		map.put(AboutEnum.PARAGRAPH_3_B.getRank(), AboutEnum.PARAGRAPH_3_B);
		map.put(AboutEnum.PARAGRAPH_3_C.getRank(), AboutEnum.PARAGRAPH_3_C);
		map.put(AboutEnum.PARAGRAPH_3_D.getRank(), AboutEnum.PARAGRAPH_3_D);
		map.put(AboutEnum.PARAGRAPH_5_A.getRank(), AboutEnum.PARAGRAPH_5_A);
		map.put(AboutEnum.PARAGRAPH_5_B.getRank(), AboutEnum.PARAGRAPH_5_B);
		return map;
	}

	public static List<AboutEnum> getWhyWrittenThisWayParagraphs() {
		final List<AboutEnum> resultList = new LinkedList<>();
		resultList.add(AboutEnum.PARAGRAPH_4_A);
		resultList.add(AboutEnum.PARAGRAPH_4_B);
		resultList.add(AboutEnum.PARAGRAPH_4_C);
		resultList.add(AboutEnum.PARAGRAPH_4_D);
		return resultList;
	}

	public static List<AboutEnum> getHowtoContributeParagraphs() {
		final List<AboutEnum> resultList = new LinkedList<>();
		resultList.add(AboutEnum.PARAGRAPH_6_A);
		return resultList;
	}
}
