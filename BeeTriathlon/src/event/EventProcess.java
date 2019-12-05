package event;

import java.util.Random;

import main.MainBean;

public class EventProcess {
	MainBean bean = null;
	Random random = new Random();
	
	public MainBean doEvent(MainBean tempBean, String eventName, int eventNum, int eventSelected) {
		bean = tempBean;
		
		switch(eventName) {
		case "bear" :
			this.bear(eventNum, eventSelected);
			break;
		case "chestbox" :
			this.chestbox(eventNum, eventSelected);
			break;
		case "fungus" :
			this.fungus(eventNum, eventSelected);
			break;	
		}
		
		return bean;
	}
	
	private void bear(int eventNum, int eventSelected) {
		int cc = random.nextInt(100);
		switch(eventNum) {
		case 0 :
			switch(eventSelected) {
			case 1 :
				if(50>cc) {
					bean.setLog(bean.getLog()+10);
					bean.setEvent("�����̸� �Ѵ� ������ �׸� ������� ��� ������ ���־���. <br> ������ �����̸� �����Կ� ��å���� ��������, �볪�� 10���� ȹ���Ͽ���!");
				}else if(20>cc) {
					bean.setFood(bean.getFood()+20);
					bean.setEvent("�����̿��� ġ���� ���� ���� �����̴� ����⸦ ������ ��������. <br> �������� ������ ���ڴ°�? �ķ� 20���� ȹ���ߴ�.");
				}else {
					bean.setCharStatus(0);
					bean.setEvent("�����̿� ����� �� ������ ġ����� �Ծ� �ǽ��� ���� �������...... <br> ����..... �ƺ�..... �������� ��ٸ��Կ�......");
				}
				break;
			case 2 :
				if(5>cc) {
					bean.setEvent("�����̰� �ٰ��� ůů �Ÿ��� ������ �þҴ�.<br> �������� �帣�� ������ �̾����� ���, �����̴� �ڷε��� �ָ� �������.");
				}else {
					bean.setCharStatus(0);
					bean.setEvent("�����̰� �ٰ��� ůů �Ÿ��� ������ �þҴ�.<br> �������� �帣�� ������ �̾����� ���, ���� �������� ��Ʈ����Ʈ ��ġ!! <br> ��ī�ο� �̻��� �巯���� �����̸� �ڷ��ϸ� �ǽ��� �������..... ����..... �ƺ�......");
				}
				break;
			}
			break;
		case 1 :
			switch(eventSelected) {
			case 1 :
				if(50>cc) {
					bean.setLog(bean.getLog()+5);
					bean.setEvent("�������� ���� �����̴��� ������ ������ �ָ��� ������!! <br> �� �ѹ濡 ���� �ټ��׷簡 �������°� ���δ�!");
				}else {
					bean.setEvent("�������� ���� ���� �谡 ���°�����. ������ �� ����� ���������.....<br> ��ģ���� �پ� �����̸� �������µ� �����ߴ�!");
				}
				break;
			case 2 :
				bean.setPower(bean.getPower()+3);
				bean.setEvent("���� �������� ���� ���س� ���� ������. <br> �����̿� �������̺긦 �ϰ�� ������ ���ƿԴ�. <br> �Ŀ��� 3 �����ߴ�! ");
				break;
			}
			break;
		}
		
	}
	
	private void chestbox(int eventNum, int eventSelected) {
		int cc = random.nextInt(100);
		switch(eventNum) {
		case 0 :
			switch(eventSelected) {
			case 1 :
				if(50>cc) {
					bean.setFirewood(bean.getFirewood()+6);
					bean.setEvent("����� ������, ���ڰ� �μ��� �� ������ ������ ���̴�. <br> ������ 6 �����ߴ�.");
				}else if(40>cc) {
					bean.setCharStatus(0);
					bean.setEvent("���ڿ��� �˼����� ������ ������ �ǽ��� ������� �ִ�. <br> �ʹ��� ����ϰ� ������ �ٽô� ����� ���� �� ó��......");
				}else {
					bean.setEvent("�ƹ��ϵ� �Ͼ�� �ʾҴ�.");
				}
				break;
			case 2 :
				if(50>cc) {
					bean.setFood(bean.getFood()+6);
					bean.setEvent("���ھȿ� �˼����� ��Ⱑ ����־� ���� ��Ⱑ �ϼ��Ǿ���! <br> �ķ��� 6 �����ߴ�.");
				}else if(40>cc) {
					bean.setCharStatus(0);
					bean.setEvent("���ھȿ� ����ִ� ���� ȭ���̾���. <br> ���ڰ� ������ ���� ����� �ٽñ� ���ְ������� �������.");
				}else {
					bean.setEvent("�ƹ��ϵ� �Ͼ�� �ʾҴ�.");
				}
				break;
			}
			break;
		case 1 :
			switch(eventSelected) {
			case 1 :
				if(10>cc) {
					bean.setPower(bean.getPower()+5);
					bean.setEvent("���ڸ� ���� ��! �Ҹ��� ����! ������ ���ν����� ���� ����־���. <br> �Ŀ��� 5 �����ߴ�.");
				}else {
					bean.setCharStatus(0);
					bean.setEvent("���ڰ� �ν����� Į�� �� �ɰ԰� Ƣ��Դ�. <br> �ع����� �ظ����� ������ ���ϰ� ���Ҵ�..... <br> �ָ����� ���δ�..... ����..... �ƺ�.......");
				}
				break;
			case 2 :
				bean.setEvent("���ڸ� �ٽ� ���� ������ ������ ���ߴ�.");
				break;
			}
			break;
		}
	}
	
	private void fungus(int eventNum, int eventSelected) {
		int cc = random.nextInt(100);
		switch(eventNum) {
		case 0 :
			switch(eventSelected) {
			case 1 :
				if(60>cc) {
					bean.setPower(bean.getPower()+5);
					bean.setEvent("�ƴ� �� ����? �̹�!(�Ƹ��ٿ� �� �̶�� ��) <br> �Ŀ��� 5 �����ߴ�!");
				}if(10>cc) {
					bean.setSpeed(bean.getSpeed()+5);
					bean.setEvent("�ƴ� �� ������ ����? �θ���� ���ֽ� �׸��̾�.... <br> ���ǵ尡 5 �����ߴ�!");
				}else {
					bean.setCharStatus(0);
					bean.setEvent("������ ���� �� ���� �������� �Բ� ���������� ã�ƿԴ�. <br> ���� �ƺ��� �ٽø����� ȯ���� ���� ���� �ῡ �����.");
				}
				break;
			case 2 :
				bean.setFirewood(bean.getFirewood()+4);
				bean.setEvent("�������� �˼� ���°� ���� ���� ����! <br> �¿��� �������� ����! <br> ������ 4 �����ߴ�.");
				break;
			}
			break;
		case 1 :
			switch(eventSelected) {
			case 1 :
				if(85>cc) {
					bean.setSpeed(bean.getSpeed()+7);
					bean.setEvent("���� �� ������ ������? ���� �� ������ �����? <br> ���ǵ尡 7 �����ߴ�!");
					}else {
					bean.setCharStatus(0);
					bean.setEvent("������ ���� �� ���� �������� �Բ� ���������� ã�ƿԴ�. <br> ���� �ƺ��� �ٽø����� ȯ���� ���� ���� �ῡ �����.");
				}
				break;
			case 2 :
				bean.setFirewood(bean.getFirewood()+5);
				bean.setFood(bean.getFood()+5);
				bean.setEvent("��ġ ������ ������ ���̴� ȭ�ο� �˼� ���� ������ �����־���. <br> ���డ ���ڱ� ���� ���������� ������ ���̴°� ��� �����ƴ�! <br> �ķ��� ������ 5�� �����ߴ�. ");
				break;
			}
			break;
		
		}
	}
}
