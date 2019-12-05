package layout;


//tempresult[] ���� �µ��� ����. weatresult[] ���� ������ ����.
//�迭�� 0���� 15���� ¦������ ������ ������, Ȧ������ ������ ������ ����ִ�.
//�ٸ� ����,����,�� 3���� ������ ������ ������ ��������� ������(null�� �������), ��� ������ ������ ������ ���糯��, ���ϰ� �𷡴� ������ �����̴�.
//��ü�� �ϳ� �����ϰ�, ��ü��.tempresult[i] �ϴ� ������ ȣ���ϴ� ������ ����ϴ� ���� ���� �� �ϴ�.

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
					
			Elements weatherT = doc1.select("table.btab tbody"); //����
			Elements weather = doc.select("div.matb tbody tr"); //����
			String wetext = weather.text();
			wetext = wetext.replaceAll(" ����", "");
			wetext = wetext.replaceAll(" ����", "");
			wetext = wetext.replaceAll(" ����", "");

			// 3�� �� ���� 7�� �� ������ ������ ��Ƴ���.
			// tempresult[6] ���� [15] ���� ���� ���� ������ �µ����ڿ��� ����.
			// weatresult[6] ���� [15] ���� ���� ���� ������ �������ڿ��� ����.
			// 
			for(int i=6; i<16; i++) {
			
			k = wetext.indexOf("��", k+1);
			tempresult[i] =wetext.substring(k-2, k).trim();
				if(i%2==0) {
					l = wetext.indexOf("����", l+1);
					weatresult[i] = wetext.substring(l+2, k-2).trim();
				}else if(i%2==1) {
					m = wetext.indexOf("����", m+1);
					n = wetext.indexOf("(", m+1);
					weatresult[i] = wetext.substring(m+2, n-2).trim();
				}
			}
						
			int b =  new Date().getDate();
			String midweather[] = new String[3];
			
			
			//����, ����, ���� ������ ��´�. ������ ���糯��, ���ϰ� �𷡴� ������ �����̴�.
			for (int i = 0; i < 3; i++) {
				String wT = weatherT.get(i).text();
				midweather[i] = b+"�� "+wT;
				b++;
				tempresult[i*2]=(midweather[i].substring(midweather[i].indexOf("��")-2,midweather[i].indexOf("��")).trim());
				weatresult[i*2]=(midweather[i].substring(midweather[i].indexOf("��")+1,midweather[i].indexOf("��")-2).trim());
				}
						
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
