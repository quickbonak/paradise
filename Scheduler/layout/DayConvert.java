package layout;


public class DayConvert {
	
	
	
	
	
	// sunToMoon  양력->음력
	// 양력을 8자리 문자열로 호출 ex) 20170701  -->  양력 2017년 07월 01일
	// 음력을 9자리 문자열로 리턴하는데, 마지막 한자리는 윤달을 뜻한다. ex) 201705081 --> 음력 2017년 05월 08일 윤달을 뜻함.
	// 윤달의 경우 1일 경우   윤달-5월 하는 식으로 불러야 한다.
	// ex) new DayConverting().SunToMoon("20170701")   ->> 201705081
	public static String sunToMoon(String sunstr) {
				
		int tempYear =Integer.parseInt(sunstr.substring(0, 4));
		int tempMonth =Integer.parseInt(sunstr.substring(4, 6));
		int tempDay =Integer.parseInt(sunstr.substring(6, 8));
						
		String tempResult = new LunarCheck().countToDateForLunar(new LunarCheck().countSolarDay(tempYear, tempMonth, tempDay));
			
    	return tempResult;
    }
	
	
	// moonToSun 음력 -> 양력
	// 음력을 9자리 문자열로 호출하는데 마지막 한자리는 윤달을 뜻한다. ex) 201705081 --> 음력 2017년 05월 08일 윤달을 뜻함.
	// 양력을 8자리 문자열로 리턴한다   ex) 20170701  --> 양력 2017년 07월 01일
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
