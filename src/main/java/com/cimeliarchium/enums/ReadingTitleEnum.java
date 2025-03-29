package com.cimeliarchium.enums;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public enum ReadingTitleEnum {

/**
 * Islamic Reading titles begin here
 */

	READING_SURAH_ALFATIHAH(3001L,
			"Reading from the Surah Al-Fatihah",
			"https://web.archive.org/web/20210508063312/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?byte=774&type=DIV0"),

	READING_SURAH_ALBAQARAH(3002L,
			"Reading from the Surah Al-Baqarah",
			"https://web.archive.org/web/20220125203915/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=1320"),

	READING_SURAH_ALIIMRAN(3003L,
			"Reading from the Surah Ali Imran",
			"https://web.archive.org/web/20220128220340/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=72808"),

	READING_SURAH_ANNISA(3004L,
			"Reading from the Surah An-Nisa",
			"https://web.archive.org/web/20210317001354/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=114839"),

	READING_SURAH_ALMAIDAH(3005L,
			"Reading from the Surah Al-Maidah",
			"https://web.archive.org/web/20210224180120/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=158021"),

	READING_SURAH_ALANAM(3006L,
			"Reading from the Surah Al-Anam",
			"https://web.archive.org/web/20210126032632/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=190943"),

	READING_SURAH_ALARAF(3007L,
			"Reading from the Surah Al-Araf",
			"https://web.archive.org/web/20220224015850/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=227087"),

	READING_SURAH_ALANFAL(3008L,
			"Reading from the Surah Al-Anfal",
			"https://web.archive.org/web/20210811104950/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=267454"),

	READING_SURAH_ATTAWBAH(3009L,
			"Reading from the Surah At-Tawbah",
			"https://web.archive.org/web/20220129012354/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=282392"),

	READING_SURAH_YUNUS(3010L,
			"Reading from the Surah Yunus",
			"https://web.archive.org/web/20210223134933/http://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=312617"),

	READING_SURAH_HUD(3011L,
			"Reading from the Surah Hud",
			"https://web.archive.org/web/20220128231029/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=333828"),

	READING_SURAH_YUSUF(3012L,
			"Reading from the Surah Yusuf",
			"https://web.archive.org/web/20210422062826/http://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=356419"),

	READING_SURAH_ARRAD(3013L,
			"Reading from the Surah Ar-Rad",
			"https://web.archive.org/web/20170720175328/http://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=377182"),

	READING_SURAH_IBRAHIM(3014L,
			"Reading from the Surah Ibrahim",
			"https://web.archive.org/web/20220128221017/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=387432"),

	READING_SURAH_ALHIJR(3015L,
			"Reading from the Surah Al-Hijr",
			"https://web.archive.org/web/20210505194756/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=397431"),

	READING_SURAH_ANNAHL(3016L,
			"Reading from the Surah An-Nahl",
			"https://web.archive.org/web/20220130211534/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=406676"),

	READING_SURAH_ALISRA(3017L,
			"Reading from the Surah Al-Isra",
			"https://web.archive.org/web/20211226065009/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=429259"),

	READING_SURAH_ALKAHF(3018L,
			"Reading from the Surah Al-Kahf",
			"https://web.archive.org/web/20220130211601/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=448502"),

	READING_SURAH_MARYAM(3019L,
			"Reading from the Surah Maryam",
			"https://web.archive.org/web/20220125203921/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=468143"),

	READING_SURAH_TAHA(3020L,
			"Reading from the Surah Ta-Ha",
			"https://web.archive.org/web/20220126212234/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=480576"),

	READING_SURAH_ALANBIYA(3021L,
			"Reading from the Surah Al-Anbiya",
			"https://web.archive.org/web/20210504115904/http://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=498061"),

	READING_SURAH_ALHAJJ(3022L,
			"Reading from the Surah Al-Hajj",
			"https://web.archive.org/web/20210811105040/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=512697"),

	READING_SURAH_ALMUMINUN(3023L,
			"Reading from the Surah Al-Muminun",
			"https://web.archive.org/web/20210427084055/http://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=527949"),

	READING_SURAH_ANNUR(3024L,
			"Reading from the Surah An-Nur",
			"https://web.archive.org/web/20210427083633/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=541486"),

	READING_SURAH_ALFURQAN(3025L,
			"Reading from the Surah Al-Furqan",
			"https://web.archive.org/web/20210223151511/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=556622"),

	READING_SURAH_ASHSHUARA(3026L,
			"Reading from the Surah Ash-Shuara",
			"https://web.archive.org/web/20220128224526/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=568126"),

	READING_SURAH_ANNAML(3027L,
			"Reading from the Surah An-Naml",
			"https://web.archive.org/web/20220128222308/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=586505"),

	READING_SURAH_ALQASAS(3028L,
			"Reading from the Surah Al-Qasas",
			"https://web.archive.org/web/20210129122849/http://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=600517"),

	READING_SURAH_ALANKABUT(3029L,
			"Reading from the Surah Al-Ankabut",
			"https://web.archive.org/web/20210504125339/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=617270"),

	READING_SURAH_ARRUM(3030L,
			"Reading from the Surah Ar-Rum",
			"https://web.archive.org/web/20210226204929/http://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=629383"),

	READING_SURAH_LUQMAN(3031L,
			"Reading from the Surah Luqman",
			"https://web.archive.org/web/20170720175353/http://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=639398"),

	READING_SURAH_ASSAJDAH(3032L,
			"Reading from the Surah As-Sajdah",
			"https://web.archive.org/web/20210508103507/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?byte=645764&type=DIV0"),

	READING_SURAH_ALAHZAB(3033L,
			"Reading from the Surah Al-Ahzab",
			"https://web.archive.org/web/20210505041909/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=650389"),

	READING_SURAH_SABA(3034L,
			"Reading from the Surah Saba",
			"https://web.archive.org/web/20170720174742/http://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=666054"),

	READING_SURAH_FATIR(3035L,
			"Reading from the Surah Fatir",
			"https://web.archive.org/web/20210227053634/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=676450"),

	READING_SURAH_YASIN(3036L,
			"Reading from the Surah Ya-Sin",
			"https://web.archive.org/web/20220125203933/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=685832"),

	READING_SURAH_ASSAFAT(3037L,
			"Reading from the Surah As-Safat",
			"https://web.archive.org/web/20210422004719/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=695195"),

	READING_SURAH_SAAD(3038L,
			"Reading from the Surah Saad",
			"https://web.archive.org/web/20210504134139/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=708759"),

	READING_SURAH_AZZUMAR(3039L,
			"Reading from the Surah Az-Zumar",
			"https://web.archive.org/web/20210223181549/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=718752"),

	READING_SURAH_GHAFIR(3040L,
			"Reading from the Surah Ghafir",
			"https://web.archive.org/web/20170720175358/http://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=733153"),

	READING_SURAH_FUSILAT(3041L,
			"Reading from the Surah Fusilat",
			"https://web.archive.org/web/20220129005234/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=747753"),

	READING_SURAH_ASH_SHORA(3042L,
			"Reading from the Surah Ash-Shora",
			"https://web.archive.org/web/20220129013405/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=757449"),

	READING_SURAH_AZZUKHRUF(3043L,
			"Reading from the Surah Az-Zukhruf",
			"https://web.archive.org/web/20220129015230/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=767330"),

	READING_SURAH_ADDUKHAN(3044L,
			"Reading from the Surah Ad-Dukhan",
			"https://web.archive.org/web/20220129080752/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=778686"),

	READING_SURAH_ALJATHIYAH(3045L,
			"Reading from the Surah Al-Jathiyah",
			"https://web.archive.org/web/20170720181618/http://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=783591"),

	READING_SURAH_ALAHQAF(3046L,
			"Reading from the Surah Al-Ahqaf",
			"https://web.archive.org/web/20210223223152/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=789696"),

	READING_SURAH_MUHAMMAD(3047L,
			"Reading from the Surah Muhammad",
			"https://web.archive.org/web/20210811105043/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=797085"),

	READING_SURAH_ALFATH(3048L,
			"Reading from the Surah Al-Fath",
			"https://web.archive.org/web/20210126204305/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=804189"),

	READING_SURAH_ALHUJURAT(3049L,
			"Reading from the Surah Al-Hujurat",
			"https://web.archive.org/web/20210421202937/http://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=811284"),

	READING_SURAH_QAAF(3050L,
			"Reading from the Surah Qaaf",
			"https://web.archive.org/web/20220125175754/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=815316"),

	READING_SURAH_ADHDHAARIYAAT(3051L,
			"Reading from the Surah Adh-Dhariyat",
			"https://web.archive.org/web/20170720181635/http://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=820181"),

	READING_SURAH_ATTOR(3052L,
			"Reading from the Surah At-Tor",
			"https://web.archive.org/web/20220128223218/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=825412"),

	READING_SURAH_ANNAJM(3053L,
			"Reading from the Surah An-Najm",
			"https://web.archive.org/web/20220128230030/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=829917"),

	READING_SURAH_ALQAMAR(3054L,
			"Reading from the Surah Al-Qamar",
			"https://web.archive.org/web/20220126212223/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=834984"),

	READING_SURAH_ARRAHMAN(3055L,
			"Reading from the Surah Ar-Rahman",
			"https://web.archive.org/web/20220128221131/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=840187"),

	READING_SURAH_ALWAQIA(3056L,
			"Reading from the Surah Al-Waqia",
			"https://web.archive.org/web/20170722021838/http://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=845981"),

	READING_SURAH_ALHADID(3057L,
			"Reading from the Surah Al-Hadid",
			"https://web.archive.org/web/20210214005237/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=852181"),

	READING_SURAH_ALMUJADILA(3058L,
			"Reading from the Surah Al-Mujadila",
			"https://web.archive.org/web/20210421180757/http://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=859334"),

	READING_SURAH_ALHASHR(3059L,
			"Reading from the Surah Al-Hashr",
			"https://web.archive.org/web/20220125203947/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=865085"),

	READING_SURAH_ALMUMTAHANAH(3060L,
			"Reading from the Surah Al-Mumtahanah",
			"https://web.archive.org/web/20220128222538/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=870663"),

	READING_SURAH_ASSAF(3061L,
			"Reading from the Surah As-Saf",
			"https://web.archive.org/web/20220128223630/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=874756"),

	READING_SURAH_ALJUMUAH(3062L,
			"Reading from the Surah Al-Jumuah",
			"https://web.archive.org/web/20210314165836/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=877378"),

	READING_SURAH_ALMUNAFIQUN(3063L,
			"Reading from the Surah Al-Munafiqun",
			"https://web.archive.org/web/20170720175424/http://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=879457"),

	READING_SURAH_ATTAGHABUN(3064L,
			"Reading from the Surah At-Taghabun",
			"https://web.archive.org/web/20170720174848/http://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=881746"),

	READING_SURAH_ATTALAQ(3065L,
			"Reading from the Surah At-Talaq",
			"https://web.archive.org/web/20210410225802/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=884985"),

	READING_SURAH_ATTAHRIM(3066L,
			"Reading from the Surah At-Tahrim",
			"https://web.archive.org/web/20220129014544/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=888547"),

	READING_SURAH_ALMULK(3067L,
			"Reading from the Surah Al-Mulk",
			"https://web.archive.org/web/20220129012756/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=891510"),

	READING_SURAH_ALQALAM(3068L,
			"Reading from the Surah Al-Qalam",
			"https://web.archive.org/web/20220130161730/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=895665"),

	READING_SURAH_ALHAQQA(3069L,
			"Reading from the Surah Al-Haqqa",
			"https://web.archive.org/web/20220129011830/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=900200"),

	READING_SURAH_ALMARIJ(3070L,
			"Reading from the Surah Al-Marij",
			"https://web.archive.org/web/20220129020446/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=904156"),

	READING_SURAH_NUH(3071L,
			"Reading from the Surah Nuh",
			"https://web.archive.org/web/20170720175434/http://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=907657"),

	READING_SURAH_ALJINN(3072L,
			"Reading from the Surah Al-Jinn",
			"https://web.archive.org/web/20200114062615/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=910644"),

	READING_SURAH_ALMUZAMIL(3073L,
			"Reading from the Surah Al-Muzamil",
			"https://web.archive.org/web/20210125135325/http://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=914195"),

	READING_SURAH_ALMUDDATHIR(3074L,
			"Reading from the Surah Al-Muddathir",
			"https://web.archive.org/web/20170720181655/http://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=916843"),

	READING_SURAH_ALQIYAMAH(3075L,
			"Reading from the Surah Al-Qiyamah",
			"https://web.archive.org/web/20191027093145/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=920701"),

	READING_SURAH_ALINSAN(3076L,
			"Reading from the Surah Al-Insan",
			"https://web.archive.org/web/20210323120628/http://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=923384"),

	READING_SURAH_ALMURSALAT(3077L,
			"Reading from the Surah Al-Mursalat",
			"https://web.archive.org/web/20170720174902/http://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=926854"),

	READING_SURAH_ANNABA(3078L,
			"Reading from the Surah An-Naba",
			"https://web.archive.org/web/20210126021352/http://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=930065"),

	READING_SURAH_ANNAZIAT(3079L,
			"Reading from the Surah An-Naziat",
			"https://web.archive.org/web/20210422215236/http://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=932913"),

	READING_SURAH_ABASA(3080L,
			"Reading from the Surah Abasa",
			"https://web.archive.org/web/20170720175439/http://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=936021"),

	READING_SURAH_ATTAKWIR(3081L,
			"Reading from the Surah At-Takwir",
			"https://web.archive.org/web/20210515132508/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=938467"),

	READING_SURAH_ALINFITAR(3082L,
			"Reading from the Surah Al-Infitar",
			"https://web.archive.org/web/20191027093149/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=940263"),

	READING_SURAH_ALMUTAFFIFIN(3083L,
			"Reading from the Surah Al-Mutaffifin",
			"https://web.archive.org/web/20220128222448/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=941648"),

	READING_SURAH_ALINSHIQAAQ(3084L,
			"Reading from the Surah Al-Inshiqaq",
			"https://web.archive.org/web/20220128220135/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=944316"),

	READING_SURAH_ALBURUJ(3085L,
			"Reading from the Surah Al-Buruj",
			"https://web.archive.org/web/20220128230350/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=946037"),

	READING_SURAH_ATTARIQ(3086L,
			"Reading from the Surah At-Tariq",
			"https://web.archive.org/web/20220129204804/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=947757"),

	READING_SURAH_ALALA(3087L,
			"Reading from the Surah Al-Ala",
			"https://web.archive.org/web/20170720174918/http://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=948861"),

	READING_SURAH_ALGHASHIYAH(3088L,
			"Reading from the Surah Al-Ghashiyah",
			"https://web.archive.org/web/20210422215230/http://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=950159"),

	READING_SURAH_ALFAJR(3089L,
			"Reading from the Surah Al-Fajr",
			"https://web.archive.org/web/20210323204802/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=951740"),

	READING_SURAH_ALBALAD(3090L,
			"Reading from the Surah Al-Balad",
			"https://web.archive.org/web/20210223162435/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=953976"),

	READING_SURAH_ASHSHAMS(3091L,
			"Reading from the Surah Ash-Shams",
			"https://web.archive.org/web/20170720175454/http://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=955347"),

	READING_SURAH_ALLAYL(3092L,
			"Reading from the Surah Al-Layl",
			"https://web.archive.org/web/20170720175459/http://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=956492"),

	READING_SURAH_ADDHUHA(3093L,
			"Reading from the Surah Ad-Dhuha",
			"https://web.archive.org/web/20220128235006/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=957972"),

	READING_SURAH_ASHSHARH(3094L,
			"Reading from the Surah Ash-Sharh",
			"https://web.archive.org/web/20170720174928/http://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=958893"),

	READING_SURAH_ATTIN(3095L,
			"Reading from the Surah At-Tin",
			"https://web.archive.org/web/20220128231004/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=959429"),

	READING_SURAH_ALALAQ(3096L,
			"Reading from the Surah Al-Alaq",
			"https://web.archive.org/web/20220128224339/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=960024"),

	READING_SURAH_ALQADR(3097L,
			"Reading from the Surah Al-Qadr",
			"https://web.archive.org/web/20220129013816/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=961213"),

	READING_SURAH_ALBAYYINAH(3098L,
			"Reading from the Surah Al-Bayyinah",
			"https://web.archive.org/web/20220129010139/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=961695"),

	READING_SURAH_AZZALZALAH(3099L,
			"Reading from the Surah Az-Zalzalah",
			"https://web.archive.org/web/20220129011413/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=962954"),

	READING_SURAH_ALADIYAT(3100L,
			"Reading from the Surah Al-Adiyat",
			"https://web.archive.org/web/20180817144659/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=963610"),

	READING_SURAH_ALQARIAH(3101L,
			"Reading from the Surah Al-Qariah",
			"https://web.archive.org/web/20180817144654/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=964405"),

	READING_SURAH_ATTAKATHUR(3102L,
			"Reading from the Surah At-Takathur",
			"https://web.archive.org/web/20170720174938/http://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=965151"),

	READING_SURAH_ALASR(3103L,
			"Reading from the Surah Al-Asr",
			"https://web.archive.org/web/20210304085552/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=965757"),

	READING_SURAH_ALHUMAZAH(3104L,
			"Reading from the Surah Al-Humazah",
			"https://web.archive.org/web/20210421203545/http://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=966084"),

	READING_SURAH_ALFIL(3105L,
			"Reading from the Surah Al-Fil",
			"https://web.archive.org/web/20210224124933/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=966765"),

	READING_SURAH_QURAYSH(3106L,
			"Reading from the Surah Quraysh",
			"https://web.archive.org/web/20170720174948/http://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=967239"),

	READING_SURAH_ALMAUN(3107L,
			"Reading from the Surah Al-Maun",
			"https://web.archive.org/web/20170720181735/http://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=967658"),

	READING_SURAH_ALKAWTHAR(3108L,
			"Reading from the Surah Al-Kawthar",
			"https://web.archive.org/web/20170720174954/http://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=968172"),

	READING_SURAH_ALKAFIRUN(3109L,
			"Reading from the Surah Al-Kafirun",
			"https://web.archive.org/web/20210411024339/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=968503"),

	READING_SURAH_ANNASR(3110L,
			"Reading from the Surah An-Nasr",
			"https://web.archive.org/web/20210422074842/http://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=968975"),

	READING_SURAH_ALMASAD(3111L,
			"Reading from the Surah Al-Masad",
			"https://web.archive.org/web/20210226173404/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=969367"),

	READING_SURAH_ALIKHLAS(3112L,
			"Reading from the Surah Al-Ikhlas",
			"https://web.archive.org/web/20210427205213/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=969810"),

	READING_SURAH_ALFALAQ(3113L,
			"Reading from the Surah Al-Falaq",
			"https://web.archive.org/web/20210421221956/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=970113"),

	READING_SURAH_ANNAS(3114L,
			"Reading from the Surah An-Nas",
			"https://web.archive.org/web/20210616093700/https://quod.lib.umich.edu/cgi/k/koran/koran-idx?type=DIV0&byte=970554"),

	READING_SAHIH_ALBUKHARI(3115L,
			"Reading from the Sahih Al-Bukhari",
			"https://web.archive.org/web/20220212125318/https://sunnah.com/bukhari"),

	READING_SAHIH_MUSLIM(3116L,
			"Reading from the Sahih Muslim",
			"https://web.archive.org/web/20220212125310/https://sunnah.com/muslim"),

	READING_SUNAN_ABI_DAWUD(3117L,
			"Reading from the Sunan Abi Dawud",
			"https://web.archive.org/web/20220212125244/https://sunnah.com/abudawud"),

	READING_SUNAN_ATTIRMIDHI(3118L,
			"Reading from the Sunan At-Tirmidhi",
			"https://web.archive.org/web/20220212125224/https://sunnah.com/tirmidhi"),

	READING_SUNAN_ANNASAI(3119L,
			"Reading from the Sunan An-Nasai",
			"https://web.archive.org/web/20220212125246/https://sunnah.com/nasai"),

	READING_SUNAN_IBN_MAJAH(3120L,
			"Reading from the Sunan ibn Majah",
			"https://web.archive.org/web/20220212125245/https://sunnah.com/ibnmajah"),

	READING_ADAB_ALMUFRAD(3121L,
			"Reading from the Adab Al-Mufrad",
			"https://web.archive.org/web/20220212125318/https://sunnah.com/adab"),

	READING_KITAB_ALIRSHAD(3122L,
			"Reading from the Kitab Al-Irshad",
			null),

/**
 * Christian Reading Titles begin here
 */

	READING_GOSPEL_MATTHEW(2001L,
			"Reading from the Gospel according to Saint Matthew",
			"https://web.archive.org/web/20211005235515/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=4380943"),

	READING_GOSPEL_MARK(2002L,
			"Reading from the Gospel according to Saint Mark",
			"https://web.archive.org/web/20210126064804/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=4520748"),

	READING_GOSPEL_LUKE(2003L,
			"Reading from the Gospel according to Saint Luke",
			"https://web.archive.org/web/20210506141352/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=4609530"),

	READING_GOSPEL_JOHN(2004L,
			"Reading from the Gospel according to Saint John",
			"https://web.archive.org/web/20210505112557/http://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=4760421"),

	READING_ACTS(2005L,
			"Reading from the Acts of the Apostles",
			"https://web.archive.org/web/20220105105410/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=4870983"),

	READING_EPISTLE_ROMANS(2006L,
			"Reading from the Epistle of Saint Paul to the Romans",
			"https://web.archive.org/web/20220122023218/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=5015363"),

	READING_EPISTLE_CORINTHIANS_1(2007L,
			"Reading from the First Epistle of Saint Paul to the Corinthians",
			"https://web.archive.org/web/20210817104359/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=5072031"),

	READING_EPISTLE_CORINTHIANS_2(2008L,
			"Reading from the Second Epistle of Saint Paul to the Corinthians",
			"https://web.archive.org/web/20190331083940/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=5127486"),

	READING_EPISTLE_GALATIANS(2009L,
			"Reading from the Epistle of Saint Paul to the Galatians",
			"https://web.archive.org/web/20210422004822/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=5163525"),

	READING_EPISTLE_EPHESIANS(2010L,
			"Reading from the Epistle of Saint Paul to the Ephesians",
			"https://web.archive.org/web/20190331103923/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=5182024"),

	READING_EPISTLE_PHILIPPIANS(2011L,
			"Reading from the Epistle of Saint Paul to the Philippians",
			"https://web.archive.org/web/20190331113115/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=5200653"),

	READING_EPISTLE_COLOSSIANS(2012L,
			"Reading from the Epistle of Saint Paul to the Colossians",
			"https://web.archive.org/web/20210814201205/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=5213809"),

	READING_EPISTLE_THESSALONIANS_1(2013L,
			"Reading from the First Epistle of Saint Paul to the Thessalonians",
			"https://web.archive.org/web/20210421014125/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=5226003"),

	READING_EPISTLE_THESSALONIANS_2(2014L,
			"Reading from the Second Epistle of Saint Paul to the Thessalonians",
			"https://web.archive.org/web/20201208184901/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=5237131"),

	READING_EPISTLE_TIMOTHY_1(2015L,
			"Reading from the First Epistle of Saint Paul to Saint Timothy",
			"https://web.archive.org/web/20210421030751/http://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=5243389"),

	READING_EPISTLE_TIMOTHY_2(2016L,
			"Reading from the Second Epistle of Saint Paul to Saint Timothy",
			"https://web.archive.org/web/20210426202615/http://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=5257901"),

	READING_EPISTLE_TITUS(2017L,
			"Reading from the Epistle of Saint Paul to Saint Titus",
			"https://web.archive.org/web/20190331073448/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=5268415"),

	READING_EPISTLE_PHILEMON(2018L,
			"Reading from the Epistle of Saint Paul to Saint Philemon",
			"https://web.archive.org/web/20190331110029/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=5274334"),

	READING_EPISTLE_HEBREWS(2019L,
			"Reading from the Epistle of Saint Paul to the Hebrews",
			"https://web.archive.org/web/20210415163119/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=5277078"),

	READING_EPISTLE_JAMES(2020L,
			"Reading from the Epistle of Saint James",
			"https://web.archive.org/web/20210421075642/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=5318893"),

	READING_EPISTLE_PETER_1(2021L,
			"Reading from the First Epistle of Saint Peter",
			"https://web.archive.org/web/20210427140744/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=5332719"),

	READING_EPISTLE_PETER_2(2022L,
			"Reading from the Second Epistle of Saint Peter",
			"https://web.archive.org/web/20210421100602/http://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=5347843"),

	READING_EPISTLE_JOHN_1(2023L,
			"Reading from the First Epistle of Saint John",
			"https://web.archive.org/web/20210501023255/http://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=5357535"),

	READING_EPISTLE_JOHN_2(2024L,
			"Reading from the Second Epistle of Saint John",
			"https://web.archive.org/web/20210418061135/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=5371967"),

	READING_EPISTLE_JOHN_3(2025L,
			"Reading from the Third Epistle of Saint John",
			"https://web.archive.org/web/20190331024157/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=5373793"),

	READING_EPISTLE_JUDE(2026L,
			"Reading from the Epistle of Saint Jude",
			"https://web.archive.org/web/20210421100607/http://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=5375671"),

	READING_APOCALYPSE(2027L,
			"Reading from the Apocalypse of Saint John",
			"https://web.archive.org/web/20220207022233/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=5379618"),

/**
 * Abrahamic Reading Titles begin here
 */

	READING_TANAKH_GENESIS(1001L,
			"Reading from the Book of Genesis",
			"https://web.archive.org/web/20220209084814/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=1477"),

	READING_TANAKH_EXODUS(1002L,
			"Reading from the Book of Exodus",
			"https://web.archive.org/web/20210702231946/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=220736"),

	READING_TANAKH_LEVITICUS(1003L,
			"Reading from the Book of Leviticus",
			"https://web.archive.org/web/20201207050626/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=407964"),

	READING_TANAKH_NUMBERS(1004L,
			"Reading from the Book of Numbers",
			"https://web.archive.org/web/20190331103406/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=547346"),

	READING_TANAKH_DEUTERONOMY(1005L,
			"Reading from the Book of Deuteronomy",
			"https://web.archive.org/web/20210727061937/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=741530"),

	READING_TANAKH_JOSHUA(1006L,
			"Reading from the Book of Joshua",
			"https://web.archive.org/web/20210212004851/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=902208"),

	READING_TANAKH_JUDGES(1007L,
			"Reading from the Book of Judges",
			"https://web.archive.org/web/20210502204141/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=1012257"),

	READING_TANAKH_RUTH(1008L,
			"Reading from the Book of Ruth",
			"https://web.archive.org/web/20210428120836/http://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=1120102"),

	READING_TANAKH_SAMUEL_1(1009L,
			"Reading from the First Book of Samuel",
			"https://web.archive.org/web/20210305151821/http://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=1134457"),

	READING_TANAKH_SAMUEL_2(1010L,
			"Reading from the Second Book of Samuel",
			"https://web.archive.org/web/20211227131502/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=1275889"),

	READING_TANAKH_KINGS_1(1011L,
			"Reading from the First Book of Kings",
			"https://web.archive.org/web/20210713100613/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=1392613"),

	READING_TANAKH_KINGS_2(1012L,
			"Reading from the Second Book of Kings",
			"https://web.archive.org/web/20210126125223/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=1531400"),

	READING_TANAKH_CHRONICLES_1(1013L,
			"Reading from the First Book of Chronicles",
			"https://web.archive.org/web/20190331044036/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=1662819"),

	READING_TANAKH_CHRONICLES_2(1014L,
			"Reading from the Second Book of Chronicles",
			"https://web.archive.org/web/20210812021244/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=1787641"),

	READING_TANAKH_EZRA(1015L,
			"Reading from the Book of Ezra",
			"https://web.archive.org/web/20190331112522/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=1939275"),

	READING_TANAKH_NEHEMIAH(1016L,
			"Reading from the Book of Nehemiah",
			"https://web.archive.org/web/20190331105539/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=1983856"),

	READING_TANAKH_ESTHER(1017L,
			"Reading from the Book of Esther",
			"https://web.archive.org/web/20190331113519/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=2046938"),

	READING_TANAKH_JOB(1018L,
			"Reading from the Book of Job",
			"https://web.archive.org/web/20210813100227/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=2079883"),

	READING_TANAKH_PSALMS(1019L,
			"Reading from the Book of Psalms",
			"https://web.archive.org/web/20220201042258/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=2190116"),

	READING_TANAKH_PROVERBS(1020L,
			"Reading from the Book of Proverbs",
			"https://web.archive.org/web/20190331103654/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=2453471"),

	READING_TANAKH_ECCLESIASTES(1021L,
			"Reading from the Book of Ecclesiastes",
			"https://web.archive.org/web/20211024034716/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=2546945"),

	READING_TANAKH_SOLOMON(1022L,
			"Reading from the Song of Solomon",
			"https://web.archive.org/web/20220128142808/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=2578814"),

	READING_TANAKH_ISAIAH(1023L,
			"Reading from the Book of Isaiah",
			"https://web.archive.org/web/20210227095743/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=2594466"),

	READING_TANAKH_JEREMIAH(1024L,
			"Reading from the Book of Jeremiah",
			"https://web.archive.org/web/20210727061936/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=2808522"),

	READING_TANAKH_LAMENTATIONS(1025L,
			"Reading from the Book of Lamentations",
			"https://web.archive.org/web/20190331144235/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=3052580"),

	READING_TANAKH_EZEKIEL(1026L,
			"Reading from the Book of Ezekiel",
			"https://web.archive.org/web/20210323213826/http://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=3072952"),

	READING_TANAKH_DANIEL(1027L,
			"Reading from the Book of Daniel",
			"https://web.archive.org/web/20190331105007/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=3297390"),

	READING_TANAKH_HOSEA(1028L,
			"Reading from the Book of Hosea",
			"https://web.archive.org/web/20210422025732/http://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=3364539"),

	READING_TANAKH_JOEL(1029L,
			"Reading from the Book of Joel",
			"https://web.archive.org/web/20210503105320/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=3394946"),

	READING_TANAKH_AMOS(1030L,
			"Reading from the Book of Amos",
			"https://web.archive.org/web/20190331074408/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=3406809"),

	READING_TANAKH_OBADIAH(1031L,
			"Reading from the Book of Obadiah",
			"https://web.archive.org/web/20210430204848/http://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=3431061"),

	READING_TANAKH_JONAH(1032L,
			"Reading from the Book of Jonah",
			"https://web.archive.org/web/20220217075223/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=3435013"),

	READING_TANAKH_MICAH(1033L,
			"Reading from the Book of Micah",
			"https://web.archive.org/web/20190331103248/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=3442521"),

	READING_TANAKH_NAHUM(1034L,
			"Reading from the Book of Nahum",
			"https://web.archive.org/web/20210421065037/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=3460595"),

	READING_TANAKH_HABAKKUK(1035L,
			"Reading from the Book of Habakkuk",
			"https://web.archive.org/web/20190331130812/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=3468307"),

	READING_TANAKH_ZEPHANIAH(1036L,
			"Reading from the Book of Zephaniah",
			"https://web.archive.org/web/20210504035527/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=3477149"),

	READING_TANAKH_HAGGAI(1037L,
			"Reading from the Book of Haggai",
			"https://web.archive.org/web/20190331082834/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=3486535"),

	READING_TANAKH_ZECHARIAH(1038L,
			"Reading from the Book of Zechariah",
			"https://web.archive.org/web/20210410212226/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=3492908"),

	READING_TANAKH_MALACHI(1039L,
			"Reading from the Book of Malachi",
			"https://web.archive.org/web/20211006093618/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=3529350"),

	READING_TANAKH_TOBIT(1040L,
			"Reading from the Book of Tobit",
			"https://web.archive.org/web/20200503002412/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=3729381"),

	READING_TANAKH_JUDITH(1041L,
			"Reading from the Book of Judith",
			"https://web.archive.org/web/20190331135813/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=3769041"),

	READING_TANAKH_BARUCH(1042L,
			"Reading from the Book of Baruch",
			"https://web.archive.org/web/20211228042744/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=4087835"),

	READING_TANAKH_SIRACH(1043L,
			"Reading from the Book of Sirach",
			"https://web.archive.org/web/20190331114810/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=3917619"),

	READING_TANAKH_MACCABEES_1(1044L,
			"Reading from the First Book of Maccabees",
			"https://web.archive.org/web/20190331115956/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=4143194"),

	READING_TANAKH_MACCABEES_2(1045L,
			"Reading from the Second Book of Maccabees",
			"https://web.archive.org/web/20190331033959/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=4282674"),

	READING_TANAKH_WISDOM(1046L,
			"Reading from the Book of Wisdom",
			"https://web.archive.org/web/20211228042741/https://quod.lib.umich.edu/cgi/k/kjv/kjv-idx?type=DIV1&byte=3849598"),

	HYMN(9999L,
			"Hymn",
			null);

	private final Long rank;

	private final String title;

	private final Long chapters;

	private final String hyperlink;

	ReadingTitleEnum(Long rank, String title, String hyperlink) {
		this.rank = rank;
		this.title = title;
		this.chapters = null;
		this.hyperlink = hyperlink;
	}

	ReadingTitleEnum(Long rank, String title, Long chapters, String hyperlink) {
		this.rank = rank;
		this.title = title;
		this.chapters = chapters;
		this.hyperlink = hyperlink;
	}

	public Long getRank() {
		return rank;
	}

	public String getTitle() {
		return this.title;
	}

	public Long getChapters() {
		return chapters;
	}

	public String getHyperlink() {
		return this.hyperlink;
	}

	private static final Map<String, ReadingTitleEnum> ALL_TITLES_MAP;

	static {
		ALL_TITLES_MAP = new LinkedHashMap<>();
		for (ReadingTitleEnum entry : ReadingTitleEnum.values()) {
			ALL_TITLES_MAP.put(entry.title, entry);
		}
	}

	public static String getHyperlinkByName(String name) {
		if (name != null) {
			final ReadingTitleEnum matchedEntry = ALL_TITLES_MAP.get(name);
			return (matchedEntry != null) 
					? Optional.ofNullable(matchedEntry.getHyperlink()).orElse(null) 
					: null;
		} else {
			return null;
		}
	}

	public static final Map<String, Map<Long, String>> getAllReadingTitles() {
		final HashMap<String, Map<Long, String>> map = new LinkedHashMap<>();
		map.putAll(getChristianReadingTitles()); // Includes Jewish entries
		map.putAll(getIslamicReadingTitles());
		return map;
	}

	public static final Map<String, Map<Long, String>> getIslamicReadingTitles() {
		final HashMap<String, Map<Long, String>> map = new LinkedHashMap<>();
		map.put(HYMN.getTitle(),  Collections.singletonMap(HYMN.getRank(),  HYMN.getHyperlink()));
		map.put(READING_SURAH_ALFATIHAH.getTitle(), Collections.singletonMap(READING_SURAH_ALFATIHAH.getRank(), READING_SURAH_ALFATIHAH.getHyperlink()));
		map.put(READING_SURAH_ALBAQARAH.getTitle(), Collections.singletonMap(READING_SURAH_ALBAQARAH.getRank(), READING_SURAH_ALBAQARAH.getHyperlink()));
		map.put(READING_SURAH_ALIIMRAN.getTitle(), Collections.singletonMap(READING_SURAH_ALIIMRAN.getRank(), READING_SURAH_ALIIMRAN.getHyperlink()));
		map.put(READING_SURAH_ANNISA.getTitle(), Collections.singletonMap(READING_SURAH_ANNISA.getRank(), READING_SURAH_ANNISA.getHyperlink()));
		map.put(READING_SURAH_ALMAIDAH.getTitle(), Collections.singletonMap(READING_SURAH_ALMAIDAH.getRank(), READING_SURAH_ALMAIDAH.getHyperlink()));
		map.put(READING_SURAH_ALANAM.getTitle(), Collections.singletonMap(READING_SURAH_ALANAM.getRank(), READING_SURAH_ALANAM.getHyperlink()));
		map.put(READING_SURAH_ALARAF.getTitle(), Collections.singletonMap(READING_SURAH_ALARAF.getRank(), READING_SURAH_ALARAF.getHyperlink()));
		map.put(READING_SURAH_ALANFAL.getTitle(), Collections.singletonMap(READING_SURAH_ALANFAL.getRank(), READING_SURAH_ALANFAL.getHyperlink()));
		map.put(READING_SURAH_ATTAWBAH.getTitle(), Collections.singletonMap(READING_SURAH_ATTAWBAH.getRank(), READING_SURAH_ATTAWBAH.getHyperlink()));
		map.put(READING_SURAH_YUNUS.getTitle(), Collections.singletonMap(READING_SURAH_YUNUS.getRank(), READING_SURAH_YUNUS.getHyperlink()));
		map.put(READING_SURAH_HUD.getTitle(), Collections.singletonMap(READING_SURAH_HUD.getRank(), READING_SURAH_HUD.getHyperlink()));
		map.put(READING_SURAH_YUSUF.getTitle(), Collections.singletonMap(READING_SURAH_YUSUF.getRank(), READING_SURAH_YUSUF.getHyperlink()));
		map.put(READING_SURAH_ARRAD.getTitle(), Collections.singletonMap(READING_SURAH_ARRAD.getRank(), READING_SURAH_ARRAD.getHyperlink()));
		map.put(READING_SURAH_IBRAHIM.getTitle(), Collections.singletonMap(READING_SURAH_IBRAHIM.getRank(), READING_SURAH_IBRAHIM.getHyperlink()));
		map.put(READING_SURAH_ALHIJR.getTitle(), Collections.singletonMap(READING_SURAH_ALHIJR.getRank(), READING_SURAH_ALHIJR.getHyperlink()));
		map.put(READING_SURAH_ANNAHL.getTitle(), Collections.singletonMap(READING_SURAH_ANNAHL.getRank(), READING_SURAH_ANNAHL.getHyperlink()));
		map.put(READING_SURAH_ALISRA.getTitle(), Collections.singletonMap(READING_SURAH_ALISRA.getRank(), READING_SURAH_ALISRA.getHyperlink()));
		map.put(READING_SURAH_ALKAHF.getTitle(), Collections.singletonMap(READING_SURAH_ALKAHF.getRank(), READING_SURAH_ALKAHF.getHyperlink()));
		map.put(READING_SURAH_MARYAM.getTitle(), Collections.singletonMap(READING_SURAH_MARYAM.getRank(), READING_SURAH_MARYAM.getHyperlink()));
		map.put(READING_SURAH_TAHA.getTitle(), Collections.singletonMap(READING_SURAH_TAHA.getRank(), READING_SURAH_TAHA.getHyperlink()));
		map.put(READING_SURAH_ALANBIYA.getTitle(), Collections.singletonMap(READING_SURAH_ALANBIYA.getRank(), READING_SURAH_ALANBIYA.getHyperlink()));
		map.put(READING_SURAH_ALHAJJ.getTitle(), Collections.singletonMap(READING_SURAH_ALHAJJ.getRank(), READING_SURAH_ALHAJJ.getHyperlink()));
		map.put(READING_SURAH_ALMUMINUN.getTitle(), Collections.singletonMap(READING_SURAH_ALMUMINUN.getRank(), READING_SURAH_ALMUMINUN.getHyperlink()));
		map.put(READING_SURAH_ANNUR.getTitle(), Collections.singletonMap(READING_SURAH_ANNUR.getRank(), READING_SURAH_ANNUR.getHyperlink()));
		map.put(READING_SURAH_ALFURQAN.getTitle(), Collections.singletonMap(READING_SURAH_ALFURQAN.getRank(), READING_SURAH_ALFURQAN.getHyperlink()));
		map.put(READING_SURAH_ASHSHUARA.getTitle(), Collections.singletonMap(READING_SURAH_ASHSHUARA.getRank(), READING_SURAH_ASHSHUARA.getHyperlink()));
		map.put(READING_SURAH_ANNAML.getTitle(), Collections.singletonMap(READING_SURAH_ANNAML.getRank(), READING_SURAH_ANNAML.getHyperlink()));
		map.put(READING_SURAH_ALQASAS.getTitle(), Collections.singletonMap(READING_SURAH_ALQASAS.getRank(), READING_SURAH_ALQASAS.getHyperlink()));
		map.put(READING_SURAH_ALANKABUT.getTitle(), Collections.singletonMap(READING_SURAH_ALANKABUT.getRank(), READING_SURAH_ALANKABUT.getHyperlink()));
		map.put(READING_SURAH_ARRUM.getTitle(), Collections.singletonMap(READING_SURAH_ARRUM.getRank(), READING_SURAH_ARRUM.getHyperlink()));
		map.put(READING_SURAH_LUQMAN.getTitle(), Collections.singletonMap(READING_SURAH_LUQMAN.getRank(), READING_SURAH_LUQMAN.getHyperlink()));
		map.put(READING_SURAH_ASSAJDAH.getTitle(), Collections.singletonMap(READING_SURAH_ASSAJDAH.getRank(), READING_SURAH_ASSAJDAH.getHyperlink()));
		map.put(READING_SURAH_ALAHZAB.getTitle(), Collections.singletonMap(READING_SURAH_ALAHZAB.getRank(), READING_SURAH_ALAHZAB.getHyperlink()));
		map.put(READING_SURAH_SABA.getTitle(), Collections.singletonMap(READING_SURAH_SABA.getRank(), READING_SURAH_SABA.getHyperlink()));
		map.put(READING_SURAH_FATIR.getTitle(), Collections.singletonMap(READING_SURAH_FATIR.getRank(), READING_SURAH_FATIR.getHyperlink()));
		map.put(READING_SURAH_YASIN.getTitle(), Collections.singletonMap(READING_SURAH_YASIN.getRank(), READING_SURAH_YASIN.getHyperlink()));
		map.put(READING_SURAH_ASSAFAT.getTitle(), Collections.singletonMap(READING_SURAH_ASSAFAT.getRank(), READING_SURAH_ASSAFAT.getHyperlink()));
		map.put(READING_SURAH_SAAD.getTitle(), Collections.singletonMap(READING_SURAH_SAAD.getRank(), READING_SURAH_SAAD.getHyperlink()));
		map.put(READING_SURAH_AZZUMAR.getTitle(), Collections.singletonMap(READING_SURAH_AZZUMAR.getRank(), READING_SURAH_AZZUMAR.getHyperlink()));
		map.put(READING_SURAH_GHAFIR.getTitle(), Collections.singletonMap(READING_SURAH_GHAFIR.getRank(), READING_SURAH_GHAFIR.getHyperlink()));
		map.put(READING_SURAH_FUSILAT.getTitle(), Collections.singletonMap(READING_SURAH_FUSILAT.getRank(), READING_SURAH_FUSILAT.getHyperlink()));
		map.put(READING_SURAH_ASH_SHORA.getTitle(), Collections.singletonMap(READING_SURAH_ASH_SHORA.getRank(), READING_SURAH_ASH_SHORA.getHyperlink()));
		map.put(READING_SURAH_AZZUKHRUF.getTitle(), Collections.singletonMap(READING_SURAH_AZZUKHRUF.getRank(), READING_SURAH_AZZUKHRUF.getHyperlink()));
		map.put(READING_SURAH_ADDUKHAN.getTitle(), Collections.singletonMap(READING_SURAH_ADDUKHAN.getRank(), READING_SURAH_ADDUKHAN.getHyperlink()));
		map.put(READING_SURAH_ALJATHIYAH.getTitle(), Collections.singletonMap(READING_SURAH_ALJATHIYAH.getRank(), READING_SURAH_ALJATHIYAH.getHyperlink()));
		map.put(READING_SURAH_ALAHQAF.getTitle(), Collections.singletonMap(READING_SURAH_ALAHQAF.getRank(), READING_SURAH_ALAHQAF.getHyperlink()));
		map.put(READING_SURAH_MUHAMMAD.getTitle(), Collections.singletonMap(READING_SURAH_MUHAMMAD.getRank(), READING_SURAH_MUHAMMAD.getHyperlink()));
		map.put(READING_SURAH_ALFATH.getTitle(), Collections.singletonMap(READING_SURAH_ALFATH.getRank(), READING_SURAH_ALFATH.getHyperlink()));
		map.put(READING_SURAH_ALHUJURAT.getTitle(), Collections.singletonMap(READING_SURAH_ALHUJURAT.getRank(), READING_SURAH_ALHUJURAT.getHyperlink()));
		map.put(READING_SURAH_QAAF.getTitle(), Collections.singletonMap(READING_SURAH_QAAF.getRank(), READING_SURAH_QAAF.getHyperlink()));
		map.put(READING_SURAH_ADHDHAARIYAAT.getTitle(), Collections.singletonMap(READING_SURAH_ADHDHAARIYAAT.getRank(), READING_SURAH_ADHDHAARIYAAT.getHyperlink()));
		map.put(READING_SURAH_ATTOR.getTitle(), Collections.singletonMap(READING_SURAH_ATTOR.getRank(), READING_SURAH_ATTOR.getHyperlink()));
		map.put(READING_SURAH_ANNAJM.getTitle(), Collections.singletonMap(READING_SURAH_ANNAJM.getRank(), READING_SURAH_ANNAJM.getHyperlink()));
		map.put(READING_SURAH_ALQAMAR.getTitle(), Collections.singletonMap(READING_SURAH_ALQAMAR.getRank(), READING_SURAH_ALQAMAR.getHyperlink()));
		map.put(READING_SURAH_ARRAHMAN.getTitle(), Collections.singletonMap(READING_SURAH_ARRAHMAN.getRank(), READING_SURAH_ARRAHMAN.getHyperlink()));
		map.put(READING_SURAH_ALWAQIA.getTitle(), Collections.singletonMap(READING_SURAH_ALWAQIA.getRank(), READING_SURAH_ALWAQIA.getHyperlink()));
		map.put(READING_SURAH_ALHADID.getTitle(), Collections.singletonMap(READING_SURAH_ALHADID.getRank(), READING_SURAH_ALHADID.getHyperlink()));
		map.put(READING_SURAH_ALMUJADILA.getTitle(), Collections.singletonMap(READING_SURAH_ALMUJADILA.getRank(), READING_SURAH_ALMUJADILA.getHyperlink()));
		map.put(READING_SURAH_ALHASHR.getTitle(), Collections.singletonMap(READING_SURAH_ALHASHR.getRank(), READING_SURAH_ALHASHR.getHyperlink()));
		map.put(READING_SURAH_ALMUMTAHANAH.getTitle(), Collections.singletonMap(READING_SURAH_ALMUMTAHANAH.getRank(), READING_SURAH_ALMUMTAHANAH.getHyperlink()));
		map.put(READING_SURAH_ASSAF.getTitle(), Collections.singletonMap(READING_SURAH_ASSAF.getRank(), READING_SURAH_ASSAF.getHyperlink()));
		map.put(READING_SURAH_ALJUMUAH.getTitle(), Collections.singletonMap(READING_SURAH_ALJUMUAH.getRank(), READING_SURAH_ALJUMUAH.getHyperlink()));
		map.put(READING_SURAH_ALMUNAFIQUN.getTitle(), Collections.singletonMap(READING_SURAH_ALMUNAFIQUN.getRank(), READING_SURAH_ALMUNAFIQUN.getHyperlink()));
		map.put(READING_SURAH_ATTAGHABUN.getTitle(), Collections.singletonMap(READING_SURAH_ATTAGHABUN.getRank(), READING_SURAH_ATTAGHABUN.getHyperlink()));
		map.put(READING_SURAH_ATTALAQ.getTitle(), Collections.singletonMap(READING_SURAH_ATTALAQ.getRank(), READING_SURAH_ATTALAQ.getHyperlink()));
		map.put(READING_SURAH_ATTAHRIM.getTitle(), Collections.singletonMap(READING_SURAH_ATTAHRIM.getRank(), READING_SURAH_ATTAHRIM.getHyperlink()));
		map.put(READING_SURAH_ALMULK.getTitle(), Collections.singletonMap(READING_SURAH_ALMULK.getRank(), READING_SURAH_ALMULK.getHyperlink()));
		map.put(READING_SURAH_ALQALAM.getTitle(), Collections.singletonMap(READING_SURAH_ALQALAM.getRank(), READING_SURAH_ALQALAM.getHyperlink()));
		map.put(READING_SURAH_ALHAQQA.getTitle(), Collections.singletonMap(READING_SURAH_ALHAQQA.getRank(), READING_SURAH_ALHAQQA.getHyperlink()));
		map.put(READING_SURAH_ALMARIJ.getTitle(), Collections.singletonMap(READING_SURAH_ALMARIJ.getRank(), READING_SURAH_ALMARIJ.getHyperlink()));
		map.put(READING_SURAH_NUH.getTitle(), Collections.singletonMap(READING_SURAH_NUH.getRank(), READING_SURAH_NUH.getHyperlink()));
		map.put(READING_SURAH_ALJINN.getTitle(), Collections.singletonMap(READING_SURAH_ALJINN.getRank(), READING_SURAH_ALJINN.getHyperlink()));
		map.put(READING_SURAH_ALMUZAMIL.getTitle(), Collections.singletonMap(READING_SURAH_ALMUZAMIL.getRank(), READING_SURAH_ALMUZAMIL.getHyperlink()));
		map.put(READING_SURAH_ALMUDDATHIR.getTitle(), Collections.singletonMap(READING_SURAH_ALMUDDATHIR.getRank(), READING_SURAH_ALMUDDATHIR.getHyperlink()));
		map.put(READING_SURAH_ALQIYAMAH.getTitle(), Collections.singletonMap(READING_SURAH_ALQIYAMAH.getRank(), READING_SURAH_ALQIYAMAH.getHyperlink()));
		map.put(READING_SURAH_ALINSAN.getTitle(), Collections.singletonMap(READING_SURAH_ALINSAN.getRank(), READING_SURAH_ALINSAN.getHyperlink()));
		map.put(READING_SURAH_ALMURSALAT.getTitle(), Collections.singletonMap(READING_SURAH_ALMURSALAT.getRank(), READING_SURAH_ALMURSALAT.getHyperlink()));
		map.put(READING_SURAH_ANNABA.getTitle(), Collections.singletonMap(READING_SURAH_ANNABA.getRank(), READING_SURAH_ANNABA.getHyperlink()));
		map.put(READING_SURAH_ANNAZIAT.getTitle(), Collections.singletonMap(READING_SURAH_ANNAZIAT.getRank(), READING_SURAH_ANNAZIAT.getHyperlink()));
		map.put(READING_SURAH_ABASA.getTitle(), Collections.singletonMap(READING_SURAH_ABASA.getRank(), READING_SURAH_ABASA.getHyperlink()));
		map.put(READING_SURAH_ATTAKWIR.getTitle(), Collections.singletonMap(READING_SURAH_ATTAKWIR.getRank(), READING_SURAH_ATTAKWIR.getHyperlink()));
		map.put(READING_SURAH_ALINFITAR.getTitle(), Collections.singletonMap(READING_SURAH_ALINFITAR.getRank(), READING_SURAH_ALINFITAR.getHyperlink()));
		map.put(READING_SURAH_ALMUTAFFIFIN.getTitle(), Collections.singletonMap(READING_SURAH_ALMUTAFFIFIN.getRank(), READING_SURAH_ALMUTAFFIFIN.getHyperlink()));
		map.put(READING_SURAH_ALINSHIQAAQ.getTitle(), Collections.singletonMap(READING_SURAH_ALINSHIQAAQ.getRank(), READING_SURAH_ALINSHIQAAQ.getHyperlink()));
		map.put(READING_SURAH_ALBURUJ.getTitle(), Collections.singletonMap(READING_SURAH_ALBURUJ.getRank(), READING_SURAH_ALBURUJ.getHyperlink()));
		map.put(READING_SURAH_ATTARIQ.getTitle(), Collections.singletonMap(READING_SURAH_ATTARIQ.getRank(), READING_SURAH_ATTARIQ.getHyperlink()));
		map.put(READING_SURAH_ALALA.getTitle(), Collections.singletonMap(READING_SURAH_ALALA.getRank(), READING_SURAH_ALALA.getHyperlink()));
		map.put(READING_SURAH_ALGHASHIYAH.getTitle(), Collections.singletonMap(READING_SURAH_ALGHASHIYAH.getRank(), READING_SURAH_ALGHASHIYAH.getHyperlink()));
		map.put(READING_SURAH_ALFAJR.getTitle(), Collections.singletonMap(READING_SURAH_ALFAJR.getRank(), READING_SURAH_ALFAJR.getHyperlink()));
		map.put(READING_SURAH_ALBALAD.getTitle(), Collections.singletonMap(READING_SURAH_ALBALAD.getRank(), READING_SURAH_ALBALAD.getHyperlink()));
		map.put(READING_SURAH_ASHSHAMS.getTitle(), Collections.singletonMap(READING_SURAH_ASHSHAMS.getRank(), READING_SURAH_ASHSHAMS.getHyperlink()));
		map.put(READING_SURAH_ALLAYL.getTitle(), Collections.singletonMap(READING_SURAH_ALLAYL.getRank(), READING_SURAH_ALLAYL.getHyperlink()));
		map.put(READING_SURAH_ADDHUHA.getTitle(), Collections.singletonMap(READING_SURAH_ADDHUHA.getRank(), READING_SURAH_ADDHUHA.getHyperlink()));
		map.put(READING_SURAH_ASHSHARH.getTitle(), Collections.singletonMap(READING_SURAH_ASHSHARH.getRank(), READING_SURAH_ASHSHARH.getHyperlink()));
		map.put(READING_SURAH_ATTIN.getTitle(), Collections.singletonMap(READING_SURAH_ATTIN.getRank(), READING_SURAH_ATTIN.getHyperlink()));
		map.put(READING_SURAH_ALALAQ.getTitle(), Collections.singletonMap(READING_SURAH_ALALAQ.getRank(), READING_SURAH_ALALAQ.getHyperlink()));
		map.put(READING_SURAH_ALQADR.getTitle(), Collections.singletonMap(READING_SURAH_ALQADR.getRank(), READING_SURAH_ALQADR.getHyperlink()));
		map.put(READING_SURAH_ALBAYYINAH.getTitle(), Collections.singletonMap(READING_SURAH_ALBAYYINAH.getRank(), READING_SURAH_ALBAYYINAH.getHyperlink()));
		map.put(READING_SURAH_AZZALZALAH.getTitle(), Collections.singletonMap(READING_SURAH_AZZALZALAH.getRank(), READING_SURAH_AZZALZALAH.getHyperlink()));
		map.put(READING_SURAH_ALADIYAT.getTitle(), Collections.singletonMap(READING_SURAH_ALADIYAT.getRank(), READING_SURAH_ALADIYAT.getHyperlink()));
		map.put(READING_SURAH_ALQARIAH.getTitle(), Collections.singletonMap(READING_SURAH_ALQARIAH.getRank(), READING_SURAH_ALQARIAH.getHyperlink()));
		map.put(READING_SURAH_ATTAKATHUR.getTitle(), Collections.singletonMap(READING_SURAH_ATTAKATHUR.getRank(), READING_SURAH_ATTAKATHUR.getHyperlink()));
		map.put(READING_SURAH_ALASR.getTitle(), Collections.singletonMap(READING_SURAH_ALASR.getRank(), READING_SURAH_ALASR.getHyperlink()));
		map.put(READING_SURAH_ALHUMAZAH.getTitle(), Collections.singletonMap(READING_SURAH_ALHUMAZAH.getRank(), READING_SURAH_ALHUMAZAH.getHyperlink()));
		map.put(READING_SURAH_ALFIL.getTitle(), Collections.singletonMap(READING_SURAH_ALFIL.getRank(), READING_SURAH_ALFIL.getHyperlink()));
		map.put(READING_SURAH_QURAYSH.getTitle(), Collections.singletonMap(READING_SURAH_QURAYSH.getRank(), READING_SURAH_QURAYSH.getHyperlink()));
		map.put(READING_SURAH_ALMAUN.getTitle(), Collections.singletonMap(READING_SURAH_ALMAUN.getRank(), READING_SURAH_ALMAUN.getHyperlink()));
		map.put(READING_SURAH_ALKAWTHAR.getTitle(), Collections.singletonMap(READING_SURAH_ALKAWTHAR.getRank(), READING_SURAH_ALKAWTHAR.getHyperlink()));
		map.put(READING_SURAH_ALKAFIRUN.getTitle(), Collections.singletonMap(READING_SURAH_ALKAFIRUN.getRank(), READING_SURAH_ALKAFIRUN.getHyperlink()));
		map.put(READING_SURAH_ANNASR.getTitle(), Collections.singletonMap(READING_SURAH_ANNASR.getRank(), READING_SURAH_ANNASR.getHyperlink()));
		map.put(READING_SURAH_ALMASAD.getTitle(), Collections.singletonMap(READING_SURAH_ALMASAD.getRank(), READING_SURAH_ALMASAD.getHyperlink()));
		map.put(READING_SURAH_ALIKHLAS.getTitle(), Collections.singletonMap(READING_SURAH_ALIKHLAS.getRank(), READING_SURAH_ALIKHLAS.getHyperlink()));
		map.put(READING_SURAH_ALFALAQ.getTitle(), Collections.singletonMap(READING_SURAH_ALFALAQ.getRank(), READING_SURAH_ALFALAQ.getHyperlink()));
		map.put(READING_SURAH_ANNAS.getTitle(), Collections.singletonMap(READING_SURAH_ANNAS.getRank(), READING_SURAH_ANNAS.getHyperlink()));
		map.put(READING_ADAB_ALMUFRAD.getTitle(), Collections.singletonMap(READING_ADAB_ALMUFRAD.getRank(), READING_ADAB_ALMUFRAD.getHyperlink()));
		map.put(READING_SAHIH_ALBUKHARI.getTitle(), Collections.singletonMap(READING_SAHIH_ALBUKHARI.getRank(), READING_SAHIH_ALBUKHARI.getHyperlink()));
		map.put(READING_SAHIH_MUSLIM.getTitle(), Collections.singletonMap(READING_SAHIH_MUSLIM.getRank(), READING_SAHIH_MUSLIM.getHyperlink()));
		map.put(READING_SUNAN_ABI_DAWUD.getTitle(), Collections.singletonMap(READING_SUNAN_ABI_DAWUD.getRank(), READING_SUNAN_ABI_DAWUD.getHyperlink()));
		map.put(READING_SUNAN_ANNASAI.getTitle(), Collections.singletonMap(READING_SUNAN_ANNASAI.getRank(), READING_SUNAN_ANNASAI.getHyperlink()));
		map.put(READING_SUNAN_ATTIRMIDHI.getTitle(), Collections.singletonMap(READING_SUNAN_ATTIRMIDHI.getRank(), READING_SUNAN_ATTIRMIDHI.getHyperlink()));
		map.put(READING_SUNAN_IBN_MAJAH.getTitle(), Collections.singletonMap(READING_SUNAN_IBN_MAJAH.getRank(), READING_SUNAN_IBN_MAJAH.getHyperlink()));
		map.put(READING_KITAB_ALIRSHAD.getTitle(), Collections.singletonMap(READING_KITAB_ALIRSHAD.getRank(), READING_KITAB_ALIRSHAD.getHyperlink()));
		return map;
	}

	public static final Map<String, Map<Long, String>> getChristianReadingTitles() {
		final HashMap<String, Map<Long, String>> map = new LinkedHashMap<>();
		map.putAll(getJewishReadingTitles());
		map.put(READING_GOSPEL_MATTHEW.getTitle(), Collections.singletonMap(READING_GOSPEL_MATTHEW.getRank(), READING_GOSPEL_MATTHEW.getHyperlink()));
		map.put(READING_GOSPEL_MARK.getTitle(), Collections.singletonMap(READING_GOSPEL_MARK.getRank(), READING_GOSPEL_MARK.getHyperlink()));
		map.put(READING_GOSPEL_LUKE.getTitle(), Collections.singletonMap(READING_GOSPEL_LUKE.getRank(), READING_GOSPEL_LUKE.getHyperlink()));
		map.put(READING_GOSPEL_JOHN.getTitle(), Collections.singletonMap(READING_GOSPEL_JOHN.getRank(), READING_GOSPEL_JOHN.getHyperlink()));
		map.put(READING_ACTS.getTitle(), Collections.singletonMap(READING_ACTS.getRank(), READING_ACTS.getHyperlink()));
		map.put(READING_EPISTLE_ROMANS.getTitle(), Collections.singletonMap(READING_EPISTLE_ROMANS.getRank(), READING_EPISTLE_ROMANS.getHyperlink()));
		map.put(READING_EPISTLE_CORINTHIANS_1.getTitle(), Collections.singletonMap(READING_EPISTLE_CORINTHIANS_1.getRank(), READING_EPISTLE_CORINTHIANS_1.getHyperlink()));
		map.put(READING_EPISTLE_CORINTHIANS_2.getTitle(), Collections.singletonMap(READING_EPISTLE_CORINTHIANS_2.getRank(), READING_EPISTLE_CORINTHIANS_2.getHyperlink()));
		map.put(READING_EPISTLE_GALATIANS.getTitle(), Collections.singletonMap(READING_EPISTLE_GALATIANS.getRank(), READING_EPISTLE_GALATIANS.getHyperlink()));
		map.put(READING_EPISTLE_EPHESIANS.getTitle(), Collections.singletonMap(READING_EPISTLE_EPHESIANS.getRank(), READING_EPISTLE_EPHESIANS.getHyperlink()));
		map.put(READING_EPISTLE_PHILIPPIANS.getTitle(), Collections.singletonMap(READING_EPISTLE_PHILIPPIANS.getRank(), READING_EPISTLE_PHILIPPIANS.getHyperlink()));
		map.put(READING_EPISTLE_COLOSSIANS.getTitle(), Collections.singletonMap(READING_EPISTLE_COLOSSIANS.getRank(), READING_EPISTLE_COLOSSIANS.getHyperlink()));
		map.put(READING_EPISTLE_THESSALONIANS_1.getTitle(), Collections.singletonMap(READING_EPISTLE_THESSALONIANS_1.getRank(), READING_EPISTLE_THESSALONIANS_1.getHyperlink()));
		map.put(READING_EPISTLE_THESSALONIANS_2.getTitle(), Collections.singletonMap(READING_EPISTLE_THESSALONIANS_2.getRank(), READING_EPISTLE_THESSALONIANS_2.getHyperlink()));
		map.put(READING_EPISTLE_TIMOTHY_1.getTitle(), Collections.singletonMap(READING_EPISTLE_TIMOTHY_1.getRank(), READING_EPISTLE_TIMOTHY_1.getHyperlink()));
		map.put(READING_EPISTLE_TIMOTHY_2.getTitle(), Collections.singletonMap(READING_EPISTLE_TIMOTHY_2.getRank(), READING_EPISTLE_TIMOTHY_2.getHyperlink()));
		map.put(READING_EPISTLE_TITUS.getTitle(), Collections.singletonMap(READING_EPISTLE_TITUS.getRank(), READING_EPISTLE_TITUS.getHyperlink()));
		map.put(READING_EPISTLE_PHILEMON.getTitle(), Collections.singletonMap(READING_EPISTLE_PHILEMON.getRank(), READING_EPISTLE_PHILEMON.getHyperlink()));
		map.put(READING_EPISTLE_HEBREWS.getTitle(), Collections.singletonMap(READING_EPISTLE_HEBREWS.getRank(), READING_EPISTLE_HEBREWS.getHyperlink()));
		map.put(READING_EPISTLE_JAMES.getTitle(), Collections.singletonMap(READING_EPISTLE_JAMES.getRank(), READING_EPISTLE_JAMES.getHyperlink()));
		map.put(READING_EPISTLE_PETER_1.getTitle(), Collections.singletonMap(READING_EPISTLE_PETER_1.getRank(), READING_EPISTLE_PETER_1.getHyperlink()));
		map.put(READING_EPISTLE_PETER_2.getTitle(), Collections.singletonMap(READING_EPISTLE_PETER_2.getRank(), READING_EPISTLE_PETER_2.getHyperlink()));
		map.put(READING_EPISTLE_JOHN_1.getTitle(), Collections.singletonMap(READING_EPISTLE_JOHN_1.getRank(), READING_EPISTLE_JOHN_1.getHyperlink()));
		map.put(READING_EPISTLE_JOHN_2.getTitle(), Collections.singletonMap(READING_EPISTLE_JOHN_2.getRank(), READING_EPISTLE_JOHN_2.getHyperlink()));
		map.put(READING_EPISTLE_JOHN_3.getTitle(), Collections.singletonMap(READING_EPISTLE_JOHN_3.getRank(), READING_EPISTLE_JOHN_3.getHyperlink()));
		map.put(READING_EPISTLE_JUDE.getTitle(), Collections.singletonMap(READING_EPISTLE_JUDE.getRank(), READING_EPISTLE_JUDE.getHyperlink()));
		map.put(READING_APOCALYPSE.getTitle(), Collections.singletonMap(READING_APOCALYPSE.getRank(), READING_APOCALYPSE.getHyperlink()));
		return map;
	}

	public static final Map<String, Map<Long, String>> getJewishReadingTitles() {
		final HashMap<String, Map<Long, String>> map = new LinkedHashMap<>();
		map.put(HYMN.getTitle(), Collections.singletonMap(HYMN.getRank(), HYMN.getHyperlink()));
		map.put(READING_TANAKH_GENESIS.getTitle(), Collections.singletonMap(READING_TANAKH_GENESIS.getRank(), READING_TANAKH_GENESIS.getHyperlink()));
		map.put(READING_TANAKH_EXODUS.getTitle(), Collections.singletonMap(READING_TANAKH_EXODUS.getRank(), READING_TANAKH_EXODUS.getHyperlink()));
		map.put(READING_TANAKH_LEVITICUS.getTitle(), Collections.singletonMap(READING_TANAKH_LEVITICUS.getRank(), READING_TANAKH_LEVITICUS.getHyperlink()));
		map.put(READING_TANAKH_NUMBERS.getTitle(), Collections.singletonMap(READING_TANAKH_NUMBERS.getRank(), READING_TANAKH_NUMBERS.getHyperlink()));
		map.put(READING_TANAKH_DEUTERONOMY.getTitle(), Collections.singletonMap(READING_TANAKH_DEUTERONOMY.getRank(), READING_TANAKH_DEUTERONOMY.getHyperlink()));
		map.put(READING_TANAKH_JOSHUA.getTitle(), Collections.singletonMap(READING_TANAKH_JOSHUA.getRank(), READING_TANAKH_JOSHUA.getHyperlink()));
		map.put(READING_TANAKH_JUDGES.getTitle(), Collections.singletonMap(READING_TANAKH_JUDGES.getRank(), READING_TANAKH_JUDGES.getHyperlink()));
		map.put(READING_TANAKH_RUTH.getTitle(), Collections.singletonMap(READING_TANAKH_RUTH.getRank(), READING_TANAKH_RUTH.getHyperlink()));
		map.put(READING_TANAKH_SAMUEL_1.getTitle(), Collections.singletonMap(READING_TANAKH_SAMUEL_1.getRank(), READING_TANAKH_SAMUEL_1.getHyperlink()));
		map.put(READING_TANAKH_SAMUEL_2.getTitle(), Collections.singletonMap(READING_TANAKH_SAMUEL_2.getRank(), READING_TANAKH_SAMUEL_2.getHyperlink()));
		map.put(READING_TANAKH_KINGS_1.getTitle(), Collections.singletonMap(READING_TANAKH_KINGS_1.getRank(), READING_TANAKH_KINGS_1.getHyperlink()));
		map.put(READING_TANAKH_KINGS_2.getTitle(), Collections.singletonMap(READING_TANAKH_KINGS_2.getRank(), READING_TANAKH_KINGS_2.getHyperlink()));
		map.put(READING_TANAKH_CHRONICLES_1.getTitle(), Collections.singletonMap(READING_TANAKH_CHRONICLES_1.getRank(), READING_TANAKH_CHRONICLES_1.getHyperlink()));
		map.put(READING_TANAKH_CHRONICLES_2.getTitle(), Collections.singletonMap(READING_TANAKH_CHRONICLES_2.getRank(), READING_TANAKH_CHRONICLES_2.getHyperlink()));
		map.put(READING_TANAKH_EZRA.getTitle(), Collections.singletonMap(READING_TANAKH_EZRA.getRank(), READING_TANAKH_EZRA.getHyperlink()));
		map.put(READING_TANAKH_NEHEMIAH.getTitle(), Collections.singletonMap(READING_TANAKH_NEHEMIAH.getRank(), READING_TANAKH_NEHEMIAH.getHyperlink()));
		map.put(READING_TANAKH_ESTHER.getTitle(), Collections.singletonMap(READING_TANAKH_ESTHER.getRank(), READING_TANAKH_ESTHER.getHyperlink()));
		map.put(READING_TANAKH_JOB.getTitle(), Collections.singletonMap(READING_TANAKH_JOB.getRank(), READING_TANAKH_JOB.getHyperlink()));
		map.put(READING_TANAKH_PROVERBS.getTitle(), Collections.singletonMap(READING_TANAKH_PROVERBS.getRank(), READING_TANAKH_PROVERBS.getHyperlink()));
		map.put(READING_TANAKH_ECCLESIASTES.getTitle(), Collections.singletonMap(READING_TANAKH_ECCLESIASTES.getRank(), READING_TANAKH_ECCLESIASTES.getHyperlink()));
		map.put(READING_TANAKH_SOLOMON.getTitle(), Collections.singletonMap(READING_TANAKH_SOLOMON.getRank(), READING_TANAKH_SOLOMON.getHyperlink()));
		map.put(READING_TANAKH_ISAIAH.getTitle(), Collections.singletonMap(READING_TANAKH_ISAIAH.getRank(), READING_TANAKH_ISAIAH.getHyperlink()));
		map.put(READING_TANAKH_JEREMIAH.getTitle(), Collections.singletonMap(READING_TANAKH_JEREMIAH.getRank(), READING_TANAKH_JEREMIAH.getHyperlink()));
		map.put(READING_TANAKH_LAMENTATIONS.getTitle(), Collections.singletonMap(READING_TANAKH_LAMENTATIONS.getRank(), READING_TANAKH_LAMENTATIONS.getHyperlink()));
		map.put(READING_TANAKH_EZEKIEL.getTitle(), Collections.singletonMap(READING_TANAKH_EZEKIEL.getRank(), READING_TANAKH_EZEKIEL.getHyperlink()));
		map.put(READING_TANAKH_DANIEL.getTitle(), Collections.singletonMap(READING_TANAKH_DANIEL.getRank(), READING_TANAKH_DANIEL.getHyperlink()));
		map.put(READING_TANAKH_HOSEA.getTitle(), Collections.singletonMap(READING_TANAKH_HOSEA.getRank(), READING_TANAKH_HOSEA.getHyperlink()));
		map.put(READING_TANAKH_JOEL.getTitle(), Collections.singletonMap(READING_TANAKH_JOEL.getRank(), READING_TANAKH_JOEL.getHyperlink()));
		map.put(READING_TANAKH_AMOS.getTitle(), Collections.singletonMap(READING_TANAKH_AMOS.getRank(), READING_TANAKH_AMOS.getHyperlink()));
		map.put(READING_TANAKH_OBADIAH.getTitle(), Collections.singletonMap(READING_TANAKH_OBADIAH.getRank(), READING_TANAKH_OBADIAH.getHyperlink()));
		map.put(READING_TANAKH_JONAH.getTitle(), Collections.singletonMap(READING_TANAKH_JONAH.getRank(), READING_TANAKH_JONAH.getHyperlink()));
		map.put(READING_TANAKH_MICAH.getTitle(), Collections.singletonMap(READING_TANAKH_MICAH.getRank(), READING_TANAKH_MICAH.getHyperlink()));
		map.put(READING_TANAKH_NAHUM.getTitle(), Collections.singletonMap(READING_TANAKH_NAHUM.getRank(), READING_TANAKH_NAHUM.getHyperlink()));
		map.put(READING_TANAKH_HABAKKUK.getTitle(), Collections.singletonMap(READING_TANAKH_HABAKKUK.getRank(), READING_TANAKH_HABAKKUK.getHyperlink()));
		map.put(READING_TANAKH_ZEPHANIAH.getTitle(), Collections.singletonMap(READING_TANAKH_ZEPHANIAH.getRank(), READING_TANAKH_ZEPHANIAH.getHyperlink()));
		map.put(READING_TANAKH_HAGGAI.getTitle(), Collections.singletonMap(READING_TANAKH_HAGGAI.getRank(), READING_TANAKH_HAGGAI.getHyperlink()));
		map.put(READING_TANAKH_ZECHARIAH.getTitle(), Collections.singletonMap(READING_TANAKH_ZECHARIAH.getRank(), READING_TANAKH_ZECHARIAH.getHyperlink()));
		map.put(READING_TANAKH_MALACHI.getTitle(), Collections.singletonMap(READING_TANAKH_MALACHI.getRank(), READING_TANAKH_MALACHI.getHyperlink()));
		map.put(READING_TANAKH_TOBIT.getTitle(), Collections.singletonMap(READING_TANAKH_TOBIT.getRank(), READING_TANAKH_TOBIT.getHyperlink()));
		map.put(READING_TANAKH_JUDITH.getTitle(), Collections.singletonMap(READING_TANAKH_JUDITH.getRank(), READING_TANAKH_JUDITH.getHyperlink()));
		map.put(READING_TANAKH_BARUCH.getTitle(), Collections.singletonMap(READING_TANAKH_BARUCH.getRank(), READING_TANAKH_BARUCH.getHyperlink()));
		map.put(READING_TANAKH_SIRACH.getTitle(), Collections.singletonMap(READING_TANAKH_SIRACH.getRank(), READING_TANAKH_SIRACH.getHyperlink()));
		map.put(READING_TANAKH_MACCABEES_1.getTitle(), Collections.singletonMap(READING_TANAKH_MACCABEES_1.getRank(), READING_TANAKH_MACCABEES_1.getHyperlink()));
		map.put(READING_TANAKH_MACCABEES_2.getTitle(), Collections.singletonMap(READING_TANAKH_MACCABEES_2.getRank(), READING_TANAKH_MACCABEES_2.getHyperlink()));
		map.put(READING_TANAKH_WISDOM.getTitle(), Collections.singletonMap(READING_TANAKH_WISDOM.getRank(), READING_TANAKH_WISDOM.getHyperlink()));
		return map;
	}

}
