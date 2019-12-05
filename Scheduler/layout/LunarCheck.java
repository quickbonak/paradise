/* CalendarTest.java */
package layout;

import java.text.NumberFormat;

public class LunarCheck
{
	int DayOfMonth[] = {31,28,31,30,31,30,31,31,30,31,30,31};	// 양력 월별 일수
	int LunarDataNumberDay[] = {29,30,58,59,59,60}; 			//인덱스 숫자의 합

	//  1 : 작(29일), 2 : 큰(30일), 3 : 작작(윤달 - 29일,29일), 4 : 작큰(윤달 - 29일,30일), 5 : 큰작(윤달 - 30일,29일), 6 : 큰큰(윤달 - 30일,30일)

	int LunarData[][] = {
/* 1900 ~ 1910 까지*/
		{1,2,1,1,2,1,2,5,2,2,1,2,384},
		{1,2,1,1,2,1,2,1,2,2,2,1,354},{2,1,2,1,1,2,1,2,1,2,2,2,355},
		{1,2,1,2,1,3,2,1,1,2,2,1,383},{2,2,1,2,1,1,2,1,1,2,2,1,354},
		{2,2,1,2,2,1,1,2,1,2,1,2,355},{1,2,2,4,1,2,1,2,1,2,1,2,384},
		{1,2,1,2,1,2,2,1,2,1,2,1,354},{2,1,1,2,2,1,2,1,2,2,1,2,355},
		{1,5,1,2,1,2,1,2,2,2,1,2,384},{1,2,1,1,2,1,2,1,2,2,2,1,354},
/* 1911 ~ 1920 까지*/
		{2,1,2,1,1,5,1,2,2,1,2,2,384},{2,1,2,1,1,2,1,1,2,2,1,2,354},
		{2,2,1,2,1,1,2,1,1,2,1,2,354},{2,2,1,2,5,1,2,1,2,1,1,2,384},
		{2,1,2,2,1,2,1,2,1,2,1,2,355},{1,2,1,2,1,2,2,1,2,1,2,1,354},
		{2,3,2,1,2,2,1,2,2,1,2,1,384},{2,1,1,2,1,2,1,2,2,2,1,2,355},
		{1,2,1,1,2,1,5,2,2,1,2,2,384},{1,2,1,1,2,1,1,2,2,1,2,2,354},
/* 1921 ~ 1930 까지*/
		{2,1,2,1,1,2,1,1,2,1,2,2,354},{2,1,2,2,3,2,1,1,2,1,2,2,384},
		{1,2,2,1,2,1,2,1,2,1,1,2,354},{2,1,2,1,2,2,1,2,1,2,1,1,354},
		{2,1,2,5,2,1,2,2,1,2,1,2,385},{1,1,2,1,2,1,2,2,1,2,2,1,354},
		{2,1,1,2,1,2,1,2,2,1,2,2,355},{1,5,1,2,1,1,2,2,1,2,2,2,384},
		{1,2,1,1,2,1,1,2,1,2,2,2,354},{1,2,2,1,1,5,1,2,1,2,2,1,383},
/* 1931 ~ 1940 까지*/
		{2,2,2,1,1,2,1,1,2,1,2,1,354},{2,2,2,1,2,1,2,1,1,2,1,2,355},
		{1,2,2,1,6,1,2,1,2,1,1,2,384},{1,2,1,2,2,1,2,2,1,2,1,2,355},
		{1,1,2,1,2,1,2,2,1,2,2,1,354},{2,1,4,1,2,1,2,1,2,2,2,1,384},
		{2,1,1,2,1,1,2,1,2,2,2,1,354},{2,2,1,1,2,1,4,1,2,2,2,1,384},
		{2,2,1,1,2,1,1,2,1,2,1,2,354},{2,2,1,2,1,2,1,1,2,1,2,1,354},
/* 1941 ~ 1950 까지*/
		{2,2,1,2,2,4,1,1,2,1,2,1,384},{2,1,2,2,1,2,2,1,2,1,1,2,355},
		{1,2,1,2,1,2,2,1,2,2,1,2,355},{1,1,2,4,1,2,1,2,2,1,2,2,384},
		{1,1,2,1,1,2,1,2,2,2,1,2,354},{2,1,1,2,1,1,2,1,2,2,1,2,354},
		{2,5,1,2,1,1,2,1,2,1,2,2,384},{2,1,2,1,2,1,1,2,1,2,1,2,354},
		{2,2,1,2,1,2,3,2,1,2,1,2,384},{2,1,2,2,1,2,1,1,2,1,2,1,354},
/* 1951 ~ 1960 까지*/
		{2,1,2,2,1,2,1,2,1,2,1,2,355},{1,2,1,2,4,2,1,2,1,2,1,2,384},
		{1,2,1,1,2,2,1,2,2,1,2,2,355},{1,1,2,1,1,2,1,2,2,1,2,2,354},
		{2,1,4,1,1,2,1,2,1,2,2,2,384},{1,2,1,2,1,1,2,1,2,1,2,2,354},
		{2,1,2,1,2,1,1,5,2,1,2,2,384},{1,2,2,1,2,1,1,2,1,2,1,2,354},
		{1,2,2,1,2,1,2,1,2,1,2,1,354},{2,1,2,1,2,5,2,1,2,1,2,1,384},
/* 1961 ~ 1970 까지*/
		{2,1,2,1,2,1,2,2,1,2,1,2,355},{1,2,1,1,2,1,2,2,1,2,2,1,354},
		{2,1,2,3,2,1,2,1,2,2,2,1,384},{2,1,2,1,1,2,1,2,1,2,2,2,355},
		{1,2,1,2,1,1,2,1,1,2,2,1,353},{2,2,5,2,1,1,2,1,1,2,2,1,384},
		{2,2,1,2,2,1,1,2,1,2,1,2,355},{1,2,2,1,2,1,5,2,1,2,1,2,384},
		{1,2,1,2,1,2,2,1,2,1,2,1,354},{2,1,1,2,2,1,2,1,2,2,1,2,355},
/* 1971 ~ 1980 까지*/
		{1,2,1,1,5,2,1,2,2,2,1,2,384},{1,2,1,1,2,1,2,1,2,2,2,1,354},
		{2,1,2,1,1,2,1,1,2,2,2,1,354},{2,2,1,5,1,2,1,1,2,2,1,2,384},
		{2,2,1,2,1,1,2,1,1,2,1,2,354},{2,2,1,2,1,2,1,5,2,1,1,2,384},
		{2,1,2,2,1,2,1,2,1,2,1,1,354},{2,2,1,2,1,2,2,1,2,1,2,1,355},
		{2,1,1,2,1,6,1,2,2,1,2,1,384},{2,1,1,2,1,2,1,2,2,1,2,2,355},
/* 1981 ~ 1990 까지*/
		{1,2,1,1,2,1,1,2,2,1,2,2,354},{2,1,2,3,2,1,1,2,2,1,2,2,384},
		{2,1,2,1,1,2,1,1,2,1,2,2,354},{2,1,2,2,1,1,2,1,1,5,2,2,384},
		{1,2,2,1,2,1,2,1,1,2,1,2,354},{1,2,2,1,2,2,1,2,1,2,1,1,354},
		{2,1,2,2,1,5,2,2,1,2,1,2,385},{1,1,2,1,2,1,2,2,1,2,2,1,354},
		{2,1,1,2,1,1,2,2,1,2,2,2,355},{1,2,1,1,5,1,2,1,2,2,2,2,384},
/* 1991 ~ 2000 까지*/
		{1,2,1,1,2,1,1,2,1,2,2,2,354},{1,2,2,1,1,2,1,1,2,1,2,2,354},
		{1,2,5,2,1,2,1,1,2,1,2,1,383},{2,2,2,1,2,1,2,1,1,2,1,2,355},
		{1,2,2,1,2,2,1,5,2,1,1,2,384},{1,2,1,2,2,1,2,1,2,2,1,2,355},
		{1,1,2,1,2,1,2,2,1,2,2,1,354},{2,1,1,2,3,2,2,1,2,2,2,1,384},
		{2,1,1,2,1,1,2,1,2,2,2,1,354},{2,2,1,1,2,1,1,2,1,2,2,1,354},
/* 2001 ~ 2010 까지*/
		{2,2,2,3,2,1,1,2,1,2,1,2,384},{2,2,1,2,1,2,1,1,2,1,2,1,354},
		{2,2,1,2,2,1,2,1,1,2,1,2,355},{1,5,2,2,1,2,1,2,1,2,1,2,384},
		{1,2,1,2,1,2,2,1,2,2,1,1,354},{2,1,2,1,2,1,5,2,2,1,2,2,385},
		{1,1,2,1,1,2,1,2,2,2,1,2,354},{2,1,1,2,1,1,2,1,2,2,1,2,354},
		{2,2,1,1,5,1,2,1,2,1,2,2,384},{2,1,2,1,2,1,1,2,1,2,1,2,354},
/* 2011 ~ 2020 까지*/
		{2,1,2,2,1,2,1,1,2,1,2,1,354},{2,1,6,2,1,2,1,1,2,1,2,1,384},
		{2,1,2,2,1,2,1,2,1,2,1,2,355},{1,2,1,2,1,2,1,2,5,2,1,2,384},
		{1,2,1,1,2,1,2,2,2,1,2,2,355},{1,1,2,1,1,2,1,2,2,1,2,2,354},
		{2,1,1,2,3,2,1,2,1,2,2,2,384},{1,2,1,2,1,1,2,1,2,1,2,2,354},
		{2,1,2,1,2,1,1,2,1,2,1,2,354},{2,1,2,5,2,1,1,2,1,2,1,2,384},
/* 2021 ~ 2030 까지*/
		{1,2,2,1,2,1,2,1,2,1,2,1,354},{2,1,2,1,2,2,1,2,1,2,1,2,355},
		{1,5,2,1,2,1,2,2,1,2,1,2,384},{1,2,1,1,2,1,2,2,1,2,2,1,354},
		{2,1,2,1,1,5,2,1,2,2,2,1,384},{2,1,2,1,1,2,1,2,1,2,2,2,355},
		{1,2,1,2,1,1,2,1,1,2,2,2,354},{1,2,2,1,5,1,2,1,1,2,2,1,383},
		{2,2,1,2,2,1,1,2,1,1,2,2,355},{1,2,1,2,2,1,2,1,2,1,2,1,354},
/* 2031 ~ 2040 까지*/
		{2,1,5,2,1,2,2,1,2,1,2,1,384},{2,1,1,2,1,2,2,1,2,2,1,2,355},
		{1,2,1,1,2,1,5,2,2,2,1,2,384},{1,2,1,1,2,1,2,1,2,2,2,1,354},
		{2,1,2,1,1,2,1,1,2,2,1,2,354},{2,2,1,2,1,4,1,1,2,1,2,2,384},
		{2,2,1,2,1,1,2,1,1,2,1,2,354},{2,2,1,2,1,2,1,2,1,1,2,1,354},
		{2,2,1,2,5,2,1,2,1,2,1,1,384},{2,1,2,2,1,2,2,1,2,1,2,1,355},
/* 2041 ~ 2050 까지*/
		{2,1,1,2,1,2,2,1,2,2,1,2,355},{1,5,1,2,1,2,1,2,2,1,2,2,384},
		{1,2,1,1,2,1,1,2,2,1,2,2,354},{2,1,2,1,1,2,3,2,1,2,2,2,384},
		{2,1,2,1,1,2,1,1,2,1,2,2,354},{2,1,2,2,1,1,2,1,1,2,1,2,354},
		{2,1,2,2,4,1,2,1,1,2,1,2,384},{1,2,2,1,2,2,1,2,1,1,1,1,353},
		{2,1,2,1,2,2,1,2,2,1,2,1,355},{2,1,4,1,2,1,2,2,1,2,2,1,384}
	};


	/* 특징 : 음력의 총날짜수를 돌려준다. 만약 돌려줄수 없는 날이라면 0을 돌려준다 */
	public long countLunarDay(int Year, int Month, int Day, boolean Leap)
	{
		long AllCount 		= 0;
		long ResultValue 	= 0;
		int i = 0;
		Year -= 1900;
		AllCount += countSolarDay(1900,1,30);

		if (Year >= 0) {
			for(i=0;i<=Year-1;i++) {
				AllCount += LunarData[i][12];
			}
			for (i=0;i<=Month-2;i++) {
				AllCount += LunarDataNumberDay[LunarData[Year][i]-1];
			}
			if (!Leap) {
				AllCount += Day;
			} else {
				if (LunarData[Year][Month-1]==1 || LunarData[Year][Month-1]==2) {
					AllCount += Day;
				} else if (LunarData[Year][Month-1]==3 || LunarData[Year][Month-1]==4) {
					AllCount += Day+29;
				} else if (LunarData[Year][Month-1]==5 || LunarData[Year][Month-1]==6) {
					AllCount += Day+30;
				}
			}
			ResultValue = AllCount;
		} else {
			ResultValue = 0;
		}
		return ResultValue;
	}


	/* 특징 : 양력의 총 날짜수를 돌려준다 */
	public long countSolarDay(int Year, int Month, int Day)
	{
		int i, j 		= 0;
		long AllCount 	= 366;
		for (i = 1 ; i <= Year - 1; i++) {
			if (checkYunYear(i)) {
				AllCount += 366;
			} else {
				AllCount += 365;
			}
		}
		for (j=1;j<=Month-1;j++) {
			if (j==2) {
				if (checkYunYear(Year)) {
					AllCount += 29;
				} else {
					AllCount += DayOfMonth[j-1];
				}
			} else {
				AllCount += DayOfMonth[j-1];
			}
		}
		AllCount += Day;
		return AllCount;
	}

	/* 해당 연도가 윤년인지 여부를 체크한다. */
	public boolean checkYunYear (int Year)
	{
	    if (((Year%4)==0) && ((Year%100!=0) || (Year%400==0))) {
        	return true;
		} else {
			return false;
		}
	}

	public String countToDateForLunar(long AllCountDay)
	{
		long AllCount = 0;
		int Year,Month,Day = 0;
		// {LDNC : Lunar Data Number Count}
		boolean RepeatStop;
		boolean LeapValue;
		boolean Leap;
		String dateOfLunar = "";

		Year = 0;
		Month = 1;
		Day = 0;
		LeapValue = false;
		RepeatStop = false;

		AllCount = AllCountDay;
		AllCount -= countSolarDay(1900,1,30);

	    do {
			if (AllCount > LunarData[Year][12]) {
				AllCount -= LunarData[Year][12];
				Year += 1;							// 년 계산
			} else {
				if (AllCount > LunarDataNumberDay[LunarData[Year][Month-1]-1]) {
	           		//월계산
					AllCount-=LunarDataNumberDay[LunarData[Year][Month-1]-1];
					Month += 1;
				} else {
					if (LunarData[Year][Month-1]==1 || LunarData[Year][Month-1]==2) {
						Day = Integer.parseInt(Long.toString(AllCount));
					} else if (LunarData[Year][Month-1]==3 || LunarData[Year][Month-1]==4) {
						if (AllCount <= 29) {
							Day = Integer.parseInt(Long.toString(AllCount));
						} else {
							Day = Integer.parseInt(Long.toString(AllCount))-29;
							LeapValue = true;
						}
					} else if (LunarData[Year][Month-1]==5 || LunarData[Year][Month-1]==6) {
						if (AllCount <= 30) {
							Day = Integer.parseInt(Long.toString(AllCount));
						} else {
							Day = Integer.parseInt(Long.toString(AllCount))-30;
							LeapValue = true;
						}
					}
//				System.out.println("3-2 : " + Year + "/" + Month + "-" +  LunarDataNumberDay[LunarData[Year][Month-1]-1] + " : " + AllCount);
					RepeatStop = true;
				}
			}
		} while (!RepeatStop);

		NumberFormat nf 	= NumberFormat.getNumberInstance();
		nf.setMinimumIntegerDigits(2);

		Leap = LeapValue;

		String returnLunar = Long.toString(Year+1900) + "" + nf.format(Integer.parseInt(Long.toString(Month))) + "" + nf.format(Integer.parseInt(Long.toString(Day)));
//		System.out.println("음력일자 : " + returnLunar);
		
		
		// 여기부터는 양식을 맞추기 위한 임의 코드.
		int templeap = 0;
		if(Leap==true)
			templeap=1;
		returnLunar = returnLunar + templeap;
		return returnLunar;
	}

	public String countToDateForSolar(long AllCountDay)
	{
		int					Year,Month = 0;
		boolean 			YearRepeatStop,MonthRepeatStop;
		YearRepeatStop 		= false;
		MonthRepeatStop 	= false;
		Year 				= 0;
		Month 				= 1;
		do
		{
		   if (checkYunYear(Year)) {
				if (AllCountDay > 366) {
					AllCountDay-=366;
					Year+=1;
				} else {
					YearRepeatStop = true;
				}
			} else {
				if (AllCountDay > 365) {
					AllCountDay-=365;
					Year += 1;
				} else {
					YearRepeatStop = true;
				}
			}
// System.out.println(Year + " : " + AllCountDay + " - " + YearRepeatStop);
		} while (!YearRepeatStop);

		do
		{
           if (Month == 2) {
				if (checkYunYear(Year)) {
					if (AllCountDay > 29) {
						AllCountDay-=29;
						Month+=1;
					} else {
						MonthRepeatStop = true;
					}
				} else {
					if (AllCountDay > 28){
						AllCountDay-=28;
						Month+=1;
					} else {
						MonthRepeatStop = true;
					}
				}
			} else {
				if (AllCountDay > DayOfMonth[Month-1]) {
					AllCountDay-=DayOfMonth[Month-1];
					Month+=1;
				} else {
					MonthRepeatStop = true;
				}
			}
//System.out.println(Month + " : " + AllCountDay + " - " + MonthRepeatStop);
		} while (!MonthRepeatStop);

		NumberFormat nf 	= NumberFormat.getNumberInstance();
		nf.setMinimumIntegerDigits(2);

		String returnLunar = Long.toString(Year) + "" + nf.format(Integer.parseInt(Long.toString(Month))) + "" + nf.format(Integer.parseInt(Long.toString(AllCountDay)));
//		System.out.println("음력일자 : " + returnLunar);
		return returnLunar;
	}

}