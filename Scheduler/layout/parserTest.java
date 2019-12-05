package layout;


//tempresult[] 에는 온도가 담긴다. weatresult[] 에는 날씨가 담긴다.
//배열은 0에서 15까지 짝수에는 오전의 날씨가, 홀수에는 오후의 날씨가 담겨있다.
//다만 오늘,내일,모래 3일의 날씨는 오후의 날씨가 담겨있지는 않으며(null이 담겨있음), 담긴 날씨의 기준은 당일은 현재날씨, 내일과 모래는 자정의 날씨이다.
//객체를 하나 생성하고, 객체명.tempresult[i] 하는 식으로 호출하는 식으로 사용하는 것이 좋을 듯 하다.

import java.io.IOException;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class parserTest {
	
	static String weatresult[]= new String[16];
	static String tempresult[] = new String[16];
	int k = 0;
	int l = 0;
	int m = 0;
	int n = 0;
	
	public parserTest() {
		Document doc;
		Document doc1;
		try {
			doc = Jsoup.connect("http://m.kma.go.kr/m/forecast/forecast_02.jsp?area=0&zone=0").get();
			doc1 = Jsoup.connect("http://m.kma.go.kr/m/forecast/forecast_01.jsp?area=0&zone=0").get();
					
			Elements weatherT = doc1.select("table.btab tbody"); //날씨
			Elements weather = doc.select("div.matb tbody tr"); //날씨
			String wetext = weather.text();
			wetext = wetext.replaceAll(" 보통", "");
			wetext = wetext.replaceAll(" 낮음", "");
			wetext = wetext.replaceAll(" 높음", "");

			// 3일 뒤 부터 7일 뒤 까지의 날씨를 담아낸다.
			// tempresult[6] 부터 [15] 까지 오전 오후 순으로 온도문자열이 담긴다.
			// weatresult[6] 부터 [15] 까지 오전 오후 순으로 날씨문자열이 담긴다.
			// 
			for(int i=6; i<16; i++) {
			
			k = wetext.indexOf("℃", k+1);
			tempresult[i] =wetext.substring(k-2, k).trim();
				if(i%2==0) {
					l = wetext.indexOf("오전", l+1);
					weatresult[i] = wetext.substring(l+2, k-2).trim();
				}else if(i%2==1) {
					m = wetext.indexOf("오후", m+1);
					n = wetext.indexOf("(", m+1);
					weatresult[i] = wetext.substring(m+2, n-2).trim();
				}
			}
						
			int b =  new Date().getDate();
			String midweather[] = new String[3];
			
			
			//오늘, 내일, 모래의 날씨를 담는다. 오늘은 현재날씨, 내일과 모래는 자정의 날씨이다.
			for (int i = 0; i < 3; i++) {
				String wT = weatherT.get(i).text();
				midweather[i] = b+"일 "+wT;
				b++;
				tempresult[i*2]=(midweather[i].substring(midweather[i].indexOf("℃")-2,midweather[i].indexOf("℃")).trim());
				weatresult[i*2]=(midweather[i].substring(midweather[i].indexOf("일")+1,midweather[i].indexOf("℃")-2).trim());
				}
						
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
