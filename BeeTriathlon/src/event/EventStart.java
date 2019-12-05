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
				"나무를 베던중 곰순이의 습격을 받았다!! 어떻게 하지?",
				"평화로운 곰순이를 만났다! 무슨 이야기를 할까?"
		};
		String[] eventS1 = {
				"곰순이와 생명을 건 대결을 한다.",
				"나무가 없어요! 나무좀 주세요!"
		};
		String[] eventT1 = {
				"나무 10획득 50%, 식량 20획득 30%, 죽음 20%",
				"나무 5 획득 50%, 아무일 없음 50%"
		};
		String[] eventS2 = {
				"자는 척 한다.",
				"저랑 씨름한판 안하시겠습니까?"
		};
		String[] eventT2 = {
				"아무일없음 95%, 죽음 5%",
				"힘 3 획득"
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
				"낚시를 하다 걸려온 것은 살아있는 것이 아니었다.",
				"낚시중에 상자를 낚았다, 어떻게 하지?"
		};
		String[] eventS1 = {
				"부숴보자!",
				"위험을 감수하고 열어본다!"
		};
		String[] eventT1 = {
				"장작 6 획득 50%, 죽음 10%",
				"파워 5 획득! 90%, 죽음 10%"
		};
		String[] eventS2 = {
				"태워보자!",
				"잠겨있는 것은 그대로 두는 것이 낫다!"
		};
		String[] eventT2 = {
				"식량 6 획득 50%, 죽음 10%",
				"아무일 없음"
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
				"위험해 보이면서도 먹음직스런 버섯이 잔뜩 피었다!",
				"무지개빛 버섯이 핀 숲속에, 나무 집이 보인다."
		};
		String[] eventS1 = {
				"우걱우걱!",
				"무지개빛 버섯을 먹는다."
		};
		String[] eventT1 = {
				"파워 5 획득 40%, 스피드 5 획득 50%, 죽음 10%",
				"스피드 7 획득 85%, 죽음 15%"
		};
		String[] eventS2 = {
				"장작으로 쓴다.",
				"나무 집에 들어간다."
		};
		String[] eventT2 = {
				"장작 4 획득",
				"식량과 장작 5획득"
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
