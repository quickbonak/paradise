package layout;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

class AppendHoliday {
	Hashtable<LocalDate, String> ht = new Hashtable<>();
	int year;

	
	public AppendHoliday(int year) {
		this.year = year;

		
		LocalDate new_year = LocalDate.of(year, 1, 1);// 신정 -양력 1월 1일
		LocalDate march_1st = LocalDate.of(year, 3, 1);// 3.1절 -양력 3월 1일
		LocalDate children_day = LocalDate.of(year, 5, 5);
		LocalDate memorial_day = LocalDate.of(year, 6, 6);
		LocalDate independence_day = LocalDate.of(year, 8, 15);
		LocalDate foundation_day = LocalDate.of(year, 10, 3);
		LocalDate hangeul = LocalDate.of(year, 10, 9);
		LocalDate x_mas = LocalDate.of(year, 12, 25);

		String korean_new_year = DayConvert.moonToSun(year + "01010");
		LocalDate seollal = LocalDate.parse(korean_new_year, DateTimeFormatter.BASIC_ISO_DATE);
	
		LocalDate seollalb = seollal.minusDays(1);
		LocalDate seollala = seollal.plusDays(1);

		String buddha = DayConvert.moonToSun(year + "04080");
		LocalDate vesak = LocalDate.parse(buddha, DateTimeFormatter.BASIC_ISO_DATE);
		
		String chuseok = DayConvert.moonToSun(year + "08150");
		LocalDate thanksgiving = LocalDate.parse(chuseok, DateTimeFormatter.BASIC_ISO_DATE);
		LocalDate thanksgivingb = thanksgiving.minusDays(1);
		LocalDate thanksgivinga = thanksgiving.plusDays(1);

		ht.put(new_year, "신정");
		ht.put(seollalb, "설연휴");
		ht.put(seollal, "설날");
		ht.put(seollala, "설연휴");
		ht.put(march_1st, "삼일절");
		ht.put(vesak, "석가탄신일");
		ht.put(children_day, "어린이날");
		ht.put(memorial_day, "현충일");
		ht.put(independence_day, "광복절");
		ht.put(thanksgiving, "추석");
		ht.put(thanksgivingb, "추석연휴");
		ht.put(thanksgivinga, "추석연휴");
		ht.put(foundation_day, "개천절");
		ht.put(hangeul, "한글날");
		ht.put(x_mas, "성탄절");

	}
}
