package layout;


public class DayConvert {
	
	
	
	
	
	// sunToMoon  ���->����
	// ����� 8�ڸ� ���ڿ��� ȣ�� ex) 20170701  -->  ��� 2017�� 07�� 01��
	// ������ 9�ڸ� ���ڿ��� �����ϴµ�, ������ ���ڸ��� ������ ���Ѵ�. ex) 201705081 --> ���� 2017�� 05�� 08�� ������ ����.
	// ������ ��� 1�� ���   ����-5�� �ϴ� ������ �ҷ��� �Ѵ�.
	// ex) new DayConverting().SunToMoon("20170701")   ->> 201705081
	public static String sunToMoon(String sunstr) {
				
		int tempYear =Integer.parseInt(sunstr.substring(0, 4));
		int tempMonth =Integer.parseInt(sunstr.substring(4, 6));
		int tempDay =Integer.parseInt(sunstr.substring(6, 8));
						
		String tempResult = new LunarCheck().countToDateForLunar(new LunarCheck().countSolarDay(tempYear, tempMonth, tempDay));
			
    	return tempResult;
    }
	
	
	// moonToSun ���� -> ���
	// ������ 9�ڸ� ���ڿ��� ȣ���ϴµ� ������ ���ڸ��� ������ ���Ѵ�. ex) 201705081 --> ���� 2017�� 05�� 08�� ������ ����.
	// ����� 8�ڸ� ���ڿ��� �����Ѵ�   ex) 20170701  --> ��� 2017�� 07�� 01��
	// ex) new DayConverting().MoonToSun("201705081")  ->> 20170701
	public static String moonToSun(String moonstr) {
		
		int tempYear = Integer.parseInt(moonstr.substring(0, 4));
		int tempMonth = Integer.parseInt(moonstr.substring(4, 6));
		int tempDay = Integer.parseInt(moonstr.substring(6, 8));
		int tempNum = Integer.parseInt(moonstr.substring(8, 9));
		boolean tempLeap = false;
		
		if(tempNum==1){
			tempLeap = true;
			String tempResult = new LunarCheck().countToDateForSolar(new LunarCheck().countLunarDay(tempYear, tempMonth, tempDay, tempLeap));
			return tempResult;
		}else if(tempNum==0) {
			tempLeap = false;
			String tempResult = new LunarCheck().countToDateForSolar(new LunarCheck().countLunarDay(tempYear, tempMonth, tempDay, tempLeap));
			return tempResult;
		}		
		
    	return "20000101";
    }
	
}
