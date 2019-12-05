package event;

import java.util.Random;

public class EventStart {
	EventBean eBean = new EventBean();
	Random random = new Random();
	
	public EventBean eventSelect(String eventName) {
		eBean.setName(eventName);
		
		
		switch(eventName) {
		case "bear" :
			this.bear();
			break;
		case "chestbox" :
			this.chestbox();
			break;
		case "fungus" :
			this.fungus();
			break;
		}
		
		return eBean;
		
	}
	
	private void bear() {
		String[] eventContext = {
				"������ ������ �������� ������ �޾Ҵ�!! ��� ����?",
				"��ȭ�ο� �����̸� ������! ���� �̾߱⸦ �ұ�?"
		};
		String[] eventS1 = {
				"�����̿� ������ �� ����� �Ѵ�.",
				"������ �����! ������ �ּ���!"
		};
		String[] eventT1 = {
				"���� 10ȹ�� 50%, �ķ� 20ȹ�� 30%, ���� 20%",
				"���� 5 ȹ�� 50%, �ƹ��� ���� 50%"
		};
		String[] eventS2 = {
				"�ڴ� ô �Ѵ�.",
				"���� �������� ���Ͻðڽ��ϱ�?"
		};
		String[] eventT2 = {
				"�ƹ��Ͼ��� 95%, ���� 5%",
				"�� 3 ȹ��"
		};
		
		int num = random.nextInt(eventContext.length);
		eBean.setNumber(num);
		eBean.setContext(eventContext[num]);
		eBean.setS1(eventS1[num]);
		eBean.setT1(eventT1[num]);
		eBean.setS2(eventS2[num]);
		eBean.setT2(eventT2[num]);
	}
	
	private void chestbox() {
		String[] eventContext = {
				"���ø� �ϴ� �ɷ��� ���� ����ִ� ���� �ƴϾ���.",
				"�����߿� ���ڸ� ���Ҵ�, ��� ����?"
		};
		String[] eventS1 = {
				"�ν�����!",
				"������ �����ϰ� �����!"
		};
		String[] eventT1 = {
				"���� 6 ȹ�� 50%, ���� 10%",
				"�Ŀ� 5 ȹ��! 90%, ���� 10%"
		};
		String[] eventS2 = {
				"�¿�����!",
				"����ִ� ���� �״�� �δ� ���� ����!"
		};
		String[] eventT2 = {
				"�ķ� 6 ȹ�� 50%, ���� 10%",
				"�ƹ��� ����"
		};
		
		int num = random.nextInt(eventContext.length);
		eBean.setNumber(num);
		eBean.setContext(eventContext[num]);
		eBean.setS1(eventS1[num]);
		eBean.setT1(eventT1[num]);
		eBean.setS2(eventS2[num]);
		eBean.setT2(eventT2[num]);
	}
	
	private void fungus() {
		String[] eventContext = {
				"������ ���̸鼭�� ���������� ������ �ܶ� �Ǿ���!",
				"�������� ������ �� ���ӿ�, ���� ���� ���δ�."
		};
		String[] eventS1 = {
				"��ƿ��!",
				"�������� ������ �Դ´�."
		};
		String[] eventT1 = {
				"�Ŀ� 5 ȹ�� 40%, ���ǵ� 5 ȹ�� 50%, ���� 10%",
				"���ǵ� 7 ȹ�� 85%, ���� 15%"
		};
		String[] eventS2 = {
				"�������� ����.",
				"���� ���� ����."
		};
		String[] eventT2 = {
				"���� 4 ȹ��",
				"�ķ��� ���� 5ȹ��"
		};
		
		int num = random.nextInt(eventContext.length);
		eBean.setNumber(num);
		eBean.setContext(eventContext[num]);
		eBean.setS1(eventS1[num]);
		eBean.setT1(eventT1[num]);
		eBean.setS2(eventS2[num]);
		eBean.setT2(eventT2[num]);
	}

}
