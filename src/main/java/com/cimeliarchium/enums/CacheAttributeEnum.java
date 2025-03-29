package com.cimeliarchium.enums;

import java.util.LinkedList;
import java.util.List;

public enum CacheAttributeEnum {

	/**
	 * static.images.app-overview - PNG
	 */

	CELESTIAL_BACKGROUND_PREVIEW(
			"celestialBackgroundPreview",
			"/celestial-background-preview.png",
			"image/png"),

	DEFAULT_BACKGROUND(
			"defaultBackground",
			"/default-background.png",
			"image/png"),

	MAP_BACKGROUND_BAGHDAD_PREVIEW(
			"mapBackgroundBaghdadPreview",
			"/map-background-baghdad-preview.png",
			"image/png"),

	MAP_BACKGROUND_CAIRO_PREVIEW(
			"mapBackgroundCairoPreview",
			"/map-background-cairo-preview.png",
			"image/png"),

	MAP_BACKGROUND_DAMASCUS_PREVIEW(
			"mapBackgroundDamascusPreview",
			"/map-background-damascus-preview.png",
			"image/png"),

	MAP_BACKGROUND_DELHI_PREVIEW(
			"mapBackgroundDelhiPreview",
			"/map-background-delhi-preview.png",
			"image/png"),

	MAP_BACKGROUND_ISTANBUL_PREVIEW(
			"mapBackgroundIstanbulPreview",
			"/map-background-istanbul-preview.png",
			"image/png"),

	MAP_BACKGROUND_JERUSALEM_PREVIEW(
			"mapBackgroundJerusalemPreview",
			"/map-background-jerusalem-preview.png",
			"image/png"),

	MAP_BACKGROUND_MOSCOW_PREVIEW(
			"mapBackgroundMoscowPreview",
			"/map-background-moscow-preview.png",
			"image/png"),

	MAP_BACKGROUND_PARIS_PREVIEW(
			"mapBackgroundParisPreview",
			"/map-background-paris-preview.png",
			"image/png"),

	MAP_BACKGROUND_PREVIEW(
			"mapBackgroundPreview",
			"/map-background-preview.png",
			"image/png"),

	MAP_BACKGROUND_ROME_PREVIEW(
			"mapBackgroundRomePreview",
			"/map-background-rome-preview.png",
			"image/png"),

	MAP_BACKGROUND_VENICE_PREVIEW(
			"mapBackgroundVenicePreview",
			"/map-background-venice-preview.png",
			"image/png"),

	/**
	 * static.images.calendar-icons - SVG
	 */

	CALENDAR_ICON_CATHOLIC(
			"calendarIconCatholic",
			"/GRE.svg",
			"image/svg+xml"),

	CALENDAR_ICON_ORTHODOX(
			"calendarIconOrthodox",
			"/JUL.svg",
			"image/svg+xml"),

	CALENDAR_ICON_ISLAMIC(
			"calendarIconIslamic",
			"/HIJ.svg",
			"image/svg+xml"),

	CALENDAR_ICON_JEWISH(
			"calendarIconJewish",
			"/HEB.svg",
			"image/svg+xml"),

	/**
	 * static.images.hour-icons - SVG
	 */

	HOUR_ICON_DAWN(
			"hourIconDawn",
			"/DAWN.svg",
			"image/svg+xml"),

	HOUR_ICON_MORNING(
			"hourIconMorning",
			"/MORN.svg",
			"image/svg+xml"),

	HOUR_ICON_NOON(
			"hourIconNoon",
			"/NOON.svg",
			"image/svg+xml"),

	HOUR_ICON_EVENING(
			"hourIconEvening",
			"/EVEN.svg",
			"image/svg+xml"),

	HOUR_ICON_NIGHT(
			"hourIconNight",
			"/NITE.svg",
			"image/svg+xml"),

	HOUR_ICON_LUNAR_PHASE_WAXING_CRESCENT(
			"hourIconLunarPhaseWaxingCrescent",
			"/LP-WXC.svg",
			"image/svg+xml"),

	HOUR_ICON_LUNAR_PHASE_WAXING_QUARTER(
			"hourIconLunarPhaseWaxingQuarter",
			"/LP-WXQ.svg",
			"image/svg+xml"),

	HOUR_ICON_LUNAR_PHASE_WAXING_GIBBOUS(
			"hourIconLunarPhaseWaxingGibbous",
			"/LP-WXG.svg",
			"image/svg+xml"),

	HOUR_ICON_LUNAR_PHASE_FULL(
			"hourIconLunarPhaseFull",
			"/LP-FULL.svg",
			"image/svg+xml"),

	HOUR_ICON_LUNAR_PHASE_WANING_GIBBOUS(
			"hourIconLunarPhaseWaningGibbous",
			"/LP-WNG.svg",
			"image/svg+xml"),

	HOUR_ICON_LUNAR_PHASE_WANING_QUARTER(
			"hourIconLunarPhaseWaningQuarter",
			"/LP-WNQ.svg",
			"image/svg+xml"),

	HOUR_ICON_LUNAR_PHASE_WANING_CRESCENT(
			"hourIconLunarPhaseWaningCrescent",
			"/LP-WNC.svg",
			"image/svg+xml"),

	HOUR_ICON_LUNAR_PHASE_NEW(
			"hourIconLunarPhaseNew",
			"/LP-NEW.svg",
			"image/svg+xml"),

	HOUR_ICON_ZODIAC_SIGN_ARIES(
			"hourIconZodiacSignAries",
			"/ZS-AR.svg",
			"image/svg+xml"),

	HOUR_ICON_ZODIAC_SIGN_TAURUS(
			"hourIconZodiacSignTaurus",
			"/ZS-TR.svg",
			"image/svg+xml"),

	HOUR_ICON_ZODIAC_SIGN_GEMINI(
			"hourIconZodiacSignGemini",
			"/ZS-GM.svg",
			"image/svg+xml"),

	HOUR_ICON_ZODIAC_SIGN_CANCER(
			"hourIconZodiacSignCancer",
			"/ZS-CN.svg",
			"image/svg+xml"),

	HOUR_ICON_ZODIAC_SIGN_LEO(
			"hourIconZodiacSignLeo",
			"/ZS-LE.svg",
			"image/svg+xml"),

	HOUR_ICON_ZODIAC_SIGN_VIRGO(
			"hourIconZodiacSignVirgo",
			"/ZS-VR.svg",
			"image/svg+xml"),

	HOUR_ICON_ZODIAC_SIGN_LIBRA(
			"hourIconZodiacSignLibra",
			"/ZS-LB.svg",
			"image/svg+xml"),

	HOUR_ICON_ZODIAC_SIGN_SCORPIO(
			"hourIconZodiacSignScorpio",
			"/ZS-SC.svg",
			"image/svg+xml"),

	HOUR_ICON_ZODIAC_SIGN_SAGITTARIUS(
			"hourIconZodiacSignSagittarius",
			"/ZS-SG.svg",
			"image/svg+xml"),

	HOUR_ICON_ZODIAC_SIGN_CAPRICORN(
			"hourIconZodiacSignCapricorn",
			"/ZS-CP.svg",
			"image/svg+xml"),

	HOUR_ICON_ZODIAC_SIGN_AQUARIUS(
			"hourIconZodiacSignAquarius",
			"/ZS-AQ.svg",
			"image/svg+xml"),

	HOUR_ICON_ZODIAC_SIGN_PISCES(
			"hourIconZodiacSignPisces",
			"/ZS-PI.svg",
			"image/svg+xml"),

	/**
	 * static.images.location-icons - SVG
	 */

	LOCATION_ICON_GOTHIC_MONUMENT(
			"locationIconGothicMonument",
			"/GOT-MON.svg",
			"image/svg+xml"),

	LOCATION_ICON_GOTHIC_MAJOR(
			"locationIconGothicMajor",
			"/GOT-MAJ.svg",
			"image/svg+xml"),

	LOCATION_ICON_GOTHIC_MINOR(
			"locationIconGothicMinor",
			"/GOT-MIN.svg",
			"image/svg+xml"),

	LOCATION_ICON_LATINATE_MONUMENT(
			"locationIconLatinateMonument",
			"/LAT-MON.svg",
			"image/svg+xml"),

	LOCATION_ICON_LATINATE_MAJOR(
			"locationIconLatinateMajor",
			"/LAT-MAJ.svg",
			"image/svg+xml"),

	LOCATION_ICON_LATINATE_MINOR(
			"locationIconLatinateMinor",
			"/LAT-MIN.svg",
			"image/svg+xml"),

	LOCATION_ICON_LEVANTINE_MONUMENT(
			"locationIconLevantineMonument",
			"/LEV-MON.svg",
			"image/svg+xml"),

	LOCATION_ICON_LEVANTINE_MAJOR(
			"locationIconLevantineMajor",
			"/LEV-MAJ.svg",
			"image/svg+xml"),

	LOCATION_ICON_LEVANTINE_MINOR(
			"locationIconLevantineMinor",
			"/LEV-MIN.svg",
			"image/svg+xml"),

	LOCATION_ICON_PERSIANATE_MONUMENT(
			"locationIconPersianateMonument",
			"/PER-MON.svg",
			"image/svg+xml"),

	LOCATION_ICON_PERSIANATE_MAJOR(
			"locationIconPersianateMajor",
			"/PER-MAJ.svg",
			"image/svg+xml"),

	LOCATION_ICON_PERSIANATE_MINOR(
			"locationIconPersianateMinor",
			"/PER-MIN.svg",
			"image/svg+xml"),

	LOCATION_ICON_ORIENTAL_MONUMENT(
			"locationIconOrientalMonument",
			"/ORI-MON.svg",
			"image/svg+xml"),

	LOCATION_ICON_ORIENTAL_MAJOR(
			"locationIconOrientalMajor",
			"/ORI-MAJ.svg",
			"image/svg+xml"),

	LOCATION_ICON_ORIENTAL_MINOR(
			"locationIconOrientalMinor",
			"/ORI-MIN.svg",
			"image/svg+xml"),

	LOCATION_ICON_PARIS(
			"locationIconParis",
			"/GOT-MON-PAR.svg",
			"image/svg+xml"),

	LOCATION_ICON_VATICAN(
			"locationIconVatican",
			"/LAT-MON-VAT.svg",
			"image/svg+xml"),

	LOCATION_ICON_COLOSSEUM(
			"locationIconColosseum",
			"/LAT-MON-COL.svg",
			"image/svg+xml"),

	LOCATION_ICON_VENICE(
			"locationIconVenice",
			"/LAT-MON-VEN.svg",
			"image/svg+xml"),

	LOCATION_ICON_ISTANBUL(
			"locationIconIstanbul",
			"/LEV-MON-IST.svg",
			"image/svg+xml"),

	LOCATION_ICON_MOSCOW(
			"locationIconMoscow",
			"/LEV-MON-MOS.svg",
			"image/svg+xml"),

	LOCATION_ICON_JERUSALEM(
			"locationIconJerusalem",
			"/LEV-MON-JER.svg",
			"image/svg+xml"),

	LOCATION_ICON_DAMASCUS(
			"locationIconDamascus",
			"/LEV-MON-DAM.svg",
			"image/svg+xml"),

	LOCATION_ICON_CAIRO(
			"locationIconCairo",
			"/LEV-MON-CAI.svg",
			"image/svg+xml"),

	LOCATION_ICON_BAGHDAD(
			"locationIconBaghdad",
			"/PER-MON-BAG.svg",
			"image/svg+xml"),

	LOCATION_ICON_MECCA(
			"locationIconMecca",
			"/PER-MON-MEC.svg",
			"image/svg+xml"),

	LOCATION_ICON_DELHI(
			"locationIconDelhi",
			"/PER-MON-DEL.svg",
			"image/svg+xml"),

	/**
	 * static.images.rite-icons - SVG
	 */

	RITE_ICON_NOVUS_ORDO(
			"riteIconCatholicNovusOrdo",
			"/NOV.svg",
			"image/svg+xml"),

	RITE_ICON_TRIDENTINE(
			"riteIconCatholicTridentine",
			"/TRI.svg",
			"image/svg+xml"),

	RITE_ICON_ANGLICAN(
			"riteIconAnglican",
			"/ANG.svg",
			"image/svg+xml"),

	RITE_ICON_LUTHERAN(
			"riteIconLutheran",
			"/LUTH.svg",
			"image/svg+xml"),

	RITE_ICON_MELETIAN(
			"riteIconMeletian",
			"/MEL.svg",
			"image/svg+xml"),

	RITE_ICON_SHIITE(
			"riteIconShiite",
			"/SHI.svg",
			"image/svg+xml"),

	RITE_ICON_SUNNI(
			"riteIconSunni",
			"/SNI.svg",
			"image/svg+xml"),

	RITE_ICON_RABBINIC(
			"riteIconRabbinic",
			"/RAB.svg",
			"image/svg+xml"),

	/**
	 * static.images.star-icons - SVG
	 */

	STAR_ICON_MAG1(
			"starIconMag1",
			"/MAG1.svg",
			"image/svg+xml"),

	STAR_ICON_MAG2(
			"starIconMag2",
			"/MAG2.svg",
			"image/svg+xml"),

	STAR_ICON_MAG3(
			"starIconMag3",
			"/MAG3.svg",
			"image/svg+xml"),

	STAR_ICON_BHN(
			"starIconBehenian",
			"/BHN.svg",
			"image/svg+xml"),

	STAR_ICON_BHN_LM(
			"starIconBehenianLunarMansion",
			"/BHN_LM.svg",
			"image/svg+xml"),

	STAR_ICON_LM_AR(
			"starIconManzil",
			"/LM_AR.svg",
			"image/svg+xml"),

	STAR_ICON_LM_IN(
			"starIconNakshatra",
			"/LM_IN.svg",
			"image/svg+xml"),

	STAR_ICON_LM_BOTH(
			"starIconLunarMansion",
			"/LM_BOTH.svg",
			"image/svg+xml"),

	/**
	 * static.images.status-icons - GIF/SVG
	 */

	LOGO_ICON(
			"logoIcon",
			"/LOGO.gif",
			"image/gif"),

	SPINNER_ICON(
			"spinnerIcon",
			"/LOAD.gif",
			"image/gif"),

	SHIMMER_ICON(
			"shimmerIcon",
			"/SHIMMER.gif",
			"image/gif"),

	TITLE_ICON(
			"titleIcon",
			"/TITLE.svg",
			"image/svg+xml"),

	ALERT_ICON(
			"alertIcon",
			"/ALERT.svg",
			"image/svg+xml"),

	REFRESH_ICON(
			"refreshIcon",
			"/REFRESH.svg",
			"image/svg+xml"),

	BULLET_ICON(
			"bulletIcon",
			"/BULLET.svg",
			"image/svg+xml"),

	STATUS_ICON_GREEN(
			"statusIconGreen",
			"/GREEN.svg",
			"image/svg+xml"),

	STATUS_ICON_YELLOW(
			"statusIconYellow",
			"/YELLOW.svg",
			"image/svg+xml"),

	STATUS_ICON_RED(
			"statusIconRed",
			"/RED.svg",
			"image/svg+xml"),

	SEARCH_BUTTON(
			"searchButton",
			"/SEARCHB.svg",
			"image/svg+xml");

	private final String id;

	private final String path;

	private final String fileType;

	private CacheAttributeEnum(String id, String path, String fileType) {
		this.id = id;
		this.path = path;
		this.fileType = fileType;
	}

	public String getId() {
		return id;
	}

	public String getPath() {
		return path;
	}

	public String getFileType() {
		return fileType;
	}

	public static List<CacheAttributeEnum> getLoginCacheAttributes() {
		final List<CacheAttributeEnum> resultList = new LinkedList<>();
		resultList.add(CacheAttributeEnum.ALERT_ICON);
		resultList.add(CacheAttributeEnum.BULLET_ICON);
		resultList.add(CacheAttributeEnum.CALENDAR_ICON_CATHOLIC);
		resultList.add(CacheAttributeEnum.CALENDAR_ICON_ISLAMIC);
		resultList.add(CacheAttributeEnum.CALENDAR_ICON_JEWISH);
		resultList.add(CacheAttributeEnum.CALENDAR_ICON_ORTHODOX);
		resultList.add(CacheAttributeEnum.CELESTIAL_BACKGROUND_PREVIEW);
		resultList.add(CacheAttributeEnum.DEFAULT_BACKGROUND);
		resultList.add(CacheAttributeEnum.HOUR_ICON_DAWN);
		resultList.add(CacheAttributeEnum.HOUR_ICON_EVENING);
		resultList.add(CacheAttributeEnum.HOUR_ICON_LUNAR_PHASE_FULL);
		resultList.add(CacheAttributeEnum.HOUR_ICON_LUNAR_PHASE_NEW);
		resultList.add(CacheAttributeEnum.HOUR_ICON_LUNAR_PHASE_WANING_CRESCENT);
		resultList.add(CacheAttributeEnum.HOUR_ICON_LUNAR_PHASE_WANING_GIBBOUS);
		resultList.add(CacheAttributeEnum.HOUR_ICON_LUNAR_PHASE_WANING_QUARTER);
		resultList.add(CacheAttributeEnum.HOUR_ICON_LUNAR_PHASE_WAXING_CRESCENT);
		resultList.add(CacheAttributeEnum.HOUR_ICON_LUNAR_PHASE_WAXING_GIBBOUS);
		resultList.add(CacheAttributeEnum.HOUR_ICON_LUNAR_PHASE_WAXING_QUARTER);
		resultList.add(CacheAttributeEnum.HOUR_ICON_MORNING);
		resultList.add(CacheAttributeEnum.HOUR_ICON_NIGHT);
		resultList.add(CacheAttributeEnum.HOUR_ICON_NOON);
		resultList.add(CacheAttributeEnum.HOUR_ICON_ZODIAC_SIGN_AQUARIUS);
		resultList.add(CacheAttributeEnum.HOUR_ICON_ZODIAC_SIGN_ARIES);
		resultList.add(CacheAttributeEnum.HOUR_ICON_ZODIAC_SIGN_CANCER);
		resultList.add(CacheAttributeEnum.HOUR_ICON_ZODIAC_SIGN_CAPRICORN);
		resultList.add(CacheAttributeEnum.HOUR_ICON_ZODIAC_SIGN_GEMINI);
		resultList.add(CacheAttributeEnum.HOUR_ICON_ZODIAC_SIGN_LEO);
		resultList.add(CacheAttributeEnum.HOUR_ICON_ZODIAC_SIGN_LIBRA);
		resultList.add(CacheAttributeEnum.HOUR_ICON_ZODIAC_SIGN_PISCES);
		resultList.add(CacheAttributeEnum.HOUR_ICON_ZODIAC_SIGN_SAGITTARIUS);
		resultList.add(CacheAttributeEnum.HOUR_ICON_ZODIAC_SIGN_SCORPIO);
		resultList.add(CacheAttributeEnum.HOUR_ICON_ZODIAC_SIGN_TAURUS);
		resultList.add(CacheAttributeEnum.HOUR_ICON_ZODIAC_SIGN_VIRGO);
		resultList.add(CacheAttributeEnum.LOCATION_ICON_BAGHDAD);
		resultList.add(CacheAttributeEnum.LOCATION_ICON_CAIRO);
		resultList.add(CacheAttributeEnum.LOCATION_ICON_COLOSSEUM);
		resultList.add(CacheAttributeEnum.LOCATION_ICON_DAMASCUS);
		resultList.add(CacheAttributeEnum.LOCATION_ICON_DELHI);
		resultList.add(CacheAttributeEnum.LOCATION_ICON_GOTHIC_MAJOR);
		resultList.add(CacheAttributeEnum.LOCATION_ICON_GOTHIC_MINOR);
		resultList.add(CacheAttributeEnum.LOCATION_ICON_GOTHIC_MONUMENT);
		resultList.add(CacheAttributeEnum.LOCATION_ICON_ISTANBUL);
		resultList.add(CacheAttributeEnum.LOCATION_ICON_JERUSALEM);
		resultList.add(CacheAttributeEnum.LOCATION_ICON_LATINATE_MAJOR);
		resultList.add(CacheAttributeEnum.LOCATION_ICON_LATINATE_MINOR);
		resultList.add(CacheAttributeEnum.LOCATION_ICON_LATINATE_MONUMENT);
		resultList.add(CacheAttributeEnum.LOCATION_ICON_LEVANTINE_MAJOR);
		resultList.add(CacheAttributeEnum.LOCATION_ICON_LEVANTINE_MINOR);
		resultList.add(CacheAttributeEnum.LOCATION_ICON_LEVANTINE_MONUMENT);
		resultList.add(CacheAttributeEnum.LOCATION_ICON_MECCA);
		resultList.add(CacheAttributeEnum.LOCATION_ICON_MOSCOW);
		resultList.add(CacheAttributeEnum.LOCATION_ICON_ORIENTAL_MAJOR);
		resultList.add(CacheAttributeEnum.LOCATION_ICON_ORIENTAL_MINOR);
		resultList.add(CacheAttributeEnum.LOCATION_ICON_ORIENTAL_MONUMENT);
		resultList.add(CacheAttributeEnum.LOCATION_ICON_PARIS);
		resultList.add(CacheAttributeEnum.LOCATION_ICON_PERSIANATE_MAJOR);
		resultList.add(CacheAttributeEnum.LOCATION_ICON_PERSIANATE_MINOR);
		resultList.add(CacheAttributeEnum.LOCATION_ICON_PERSIANATE_MONUMENT);
		resultList.add(CacheAttributeEnum.LOCATION_ICON_VATICAN);
		resultList.add(CacheAttributeEnum.LOCATION_ICON_VENICE);
		resultList.add(CacheAttributeEnum.MAP_BACKGROUND_BAGHDAD_PREVIEW);
		resultList.add(CacheAttributeEnum.MAP_BACKGROUND_CAIRO_PREVIEW);
		resultList.add(CacheAttributeEnum.MAP_BACKGROUND_DAMASCUS_PREVIEW);
		resultList.add(CacheAttributeEnum.MAP_BACKGROUND_DELHI_PREVIEW);
		resultList.add(CacheAttributeEnum.MAP_BACKGROUND_ISTANBUL_PREVIEW);
		resultList.add(CacheAttributeEnum.MAP_BACKGROUND_JERUSALEM_PREVIEW);
		resultList.add(CacheAttributeEnum.MAP_BACKGROUND_MOSCOW_PREVIEW);
		resultList.add(CacheAttributeEnum.MAP_BACKGROUND_PARIS_PREVIEW);
		resultList.add(CacheAttributeEnum.MAP_BACKGROUND_PREVIEW);
		resultList.add(CacheAttributeEnum.MAP_BACKGROUND_ROME_PREVIEW);
		resultList.add(CacheAttributeEnum.MAP_BACKGROUND_VENICE_PREVIEW);
		resultList.add(CacheAttributeEnum.REFRESH_ICON);
		resultList.add(CacheAttributeEnum.RITE_ICON_ANGLICAN);
		resultList.add(CacheAttributeEnum.RITE_ICON_LUTHERAN);
		resultList.add(CacheAttributeEnum.RITE_ICON_MELETIAN);
		resultList.add(CacheAttributeEnum.RITE_ICON_NOVUS_ORDO);
		resultList.add(CacheAttributeEnum.RITE_ICON_RABBINIC);
		resultList.add(CacheAttributeEnum.RITE_ICON_SHIITE);
		resultList.add(CacheAttributeEnum.RITE_ICON_SUNNI);
		resultList.add(CacheAttributeEnum.RITE_ICON_TRIDENTINE);
		resultList.add(CacheAttributeEnum.STAR_ICON_MAG1);
		resultList.add(CacheAttributeEnum.STAR_ICON_MAG2);
		resultList.add(CacheAttributeEnum.STAR_ICON_MAG3);
		resultList.add(CacheAttributeEnum.STAR_ICON_BHN);
		resultList.add(CacheAttributeEnum.STAR_ICON_BHN_LM);
		resultList.add(CacheAttributeEnum.STAR_ICON_LM_AR);
		resultList.add(CacheAttributeEnum.STAR_ICON_LM_IN);
		resultList.add(CacheAttributeEnum.STAR_ICON_LM_BOTH);
		resultList.add(CacheAttributeEnum.SEARCH_BUTTON);
		resultList.add(CacheAttributeEnum.SHIMMER_ICON);
		resultList.add(CacheAttributeEnum.SPINNER_ICON);
		resultList.add(CacheAttributeEnum.STATUS_ICON_GREEN);
		resultList.add(CacheAttributeEnum.STATUS_ICON_RED);
		resultList.add(CacheAttributeEnum.STATUS_ICON_YELLOW);
		resultList.add(CacheAttributeEnum.TITLE_ICON);
		return resultList;
	}
}
