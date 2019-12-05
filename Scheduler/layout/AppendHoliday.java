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

		
		LocalDate new_year = LocalDate.of(year, 1, 1);// ���� -��� 1�� 1��
		LocalDate march_1st = LocalDate.of(year, 3, 1);// 3.1�� -��� 3�� 1��
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

		ht.put(new_year, "����");
		ht.put(seollalb, "������");
		ht.put(seollal, "����");
		ht.put(seollala, "������");
		ht.put(march_1st, "������");
		ht.put(vesak, "����ź����");
		ht.put(children_day, "��̳�");
		ht.put(memorial_day, "������");
		ht.put(independence_day, "������");
		ht.put(thanksgiving, "�߼�");
		ht.put(thanksgivingb, "�߼�����");
		ht.put(thanksgivinga, "�߼�����");
		ht.put(foundation_day, "��õ��");
		ht.put(hangeul, "�ѱ۳�");
		ht.put(x_mas, "��ź��");

	}
}
