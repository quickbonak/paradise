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
					bean.setEvent("곰순이를 한대 때리자 그만 때리라며 대신 나무를 해주었다. <br> 무고한 곰순이를 폭행함에 죄책감을 느꼈지만, 통나무 10개를 획득하였다!");
				}else if(20>cc) {
					bean.setFood(bean.getFood()+20);
					bean.setEvent("곰순이와의 치열한 격전 끝에 곰순이는 곰고기를 떨구고 도망갔다. <br> 이정도면 열흘은 나겠는걸? 식량 20개를 획득했다.");
				}else {
					bean.setCharStatus(0);
					bean.setEvent("곰순이와 목숨을 건 사투중 치명상을 입어 의식이 점점 흐려진다...... <br> 엄마..... 아빠..... 먼저가서 기다릴게요......");
				}
				break;
			case 2 :
				if(5>cc) {
					bean.setEvent("곰순이가 다가와 킁킁 거리며 냄새를 맡았다.<br> 식은땀이 흐르며 정적이 이어지는 가운데, 곰순이는 뒤로돌아 멀리 사라졌다.");
				}else {
					bean.setCharStatus(0);
					bean.setEvent("곰순이가 다가와 킁킁 거리며 냄새를 맡았다.<br> 식은땀이 흐르며 정적이 이어지는 가운데, 돌연 곰순이의 스트레이트 펀치!! <br> 날카로운 이빨을 드러내는 곰순이를 뒤로하며 의식이 흐려진다..... 엄마..... 아빠......");
				}
				break;
			}
			break;
		case 1 :
			switch(eventSelected) {
			case 1 :
				if(50>cc) {
					bean.setLog(bean.getLog()+5);
					bean.setEvent("곰순이의 눈이 번뜩이더니 눈앞의 나무에 주먹을 날린다!! <br> 그 한방에 나무 다섯그루가 쓰러지는게 보인다!");
				}else {
					bean.setEvent("곰순이의 눈을 보니 배가 고픈가보다. 나무를 벨 기력은 없어보이지만.....<br> 미친듯이 뛰어 곰순이를 따돌리는데 성공했다!");
				}
				break;
			case 2 :
				bean.setPower(bean.getPower()+3);
				bean.setEvent("역시 곰순이의 힘은 당해낼 수가 없었다. <br> 곰순이와 하이파이브를 하고는 집으로 돌아왔다. <br> 파워가 3 증가했다! ");
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
					bean.setEvent("든것은 없지만, 상자가 부서져 된 장작은 유용할 것이다. <br> 장작이 6 증가했다.");
				}else if(40>cc) {
					bean.setCharStatus(0);
					bean.setEvent("상자에서 알수없는 가스가 나오며 의식이 흐려지고 있다. <br> 너무나 편안하고 졸려서 다시는 깨어나지 못할 것 처럼......");
				}else {
					bean.setEvent("아무일도 일어나지 않았다.");
				}
				break;
			case 2 :
				if(50>cc) {
					bean.setFood(bean.getFood()+6);
					bean.setEvent("상자안에 알수없는 고기가 들어있어 구운 고기가 완성되었다! <br> 식량이 6 증가했다.");
				}else if(40>cc) {
					bean.setCharStatus(0);
					bean.setEvent("상자안에 들어있던 것은 화약이었다. <br> 상자가 터지며 그의 존재는 다시금 우주곳곳으로 흩어졌다.");
				}else {
					bean.setEvent("아무일도 일어나지 않았다.");
				}
				break;
			}
			break;
		case 1 :
			switch(eventSelected) {
			case 1 :
				if(10>cc) {
					bean.setPower(bean.getPower()+5);
					bean.setEvent("상자를 차차 깡! 소리가 났다! 힘차게 깨부쉈지만 안은 비어있었다. <br> 파워가 5 증가했다.");
				}else {
					bean.setCharStatus(0);
					bean.setEvent("상자가 부숴지자 칼을 든 꽃게가 튀어나왔다. <br> 해물탕을 해먹으려 했지만 당하고 말았다..... <br> 주마등이 보인다..... 엄마..... 아빠.......");
				}
				break;
			case 2 :
				bean.setEvent("상자를 다시 물에 던지고 집으로 향했다.");
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
					bean.setEvent("아니 이 맛은? 미미!(아름다운 맛 이라는 뜻) <br> 파워가 5 증가했다!");
				}if(10>cc) {
					bean.setSpeed(bean.getSpeed()+5);
					bean.setEvent("아니 이 오묘한 맛은? 부모님이 해주신 그맛이야.... <br> 스피드가 5 증가했다!");
				}else {
					bean.setCharStatus(0);
					bean.setEvent("버섯을 먹자 곧 심한 구역감과 함께 어지러움이 찾아왔다. <br> 엄마 아빠를 다시만나는 환각을 보며 깊은 잠에 들었다.");
				}
				break;
			case 2 :
				bean.setFirewood(bean.getFirewood()+4);
				bean.setEvent("위험한지 알수 없는걸 먹을 수는 없지! <br> 태워서 장작으로 쓰자! <br> 장작이 4 증가했다.");
				break;
			}
			break;
		case 1 :
			switch(eventSelected) {
			case 1 :
				if(85>cc) {
					bean.setSpeed(bean.getSpeed()+7);
					bean.setEvent("뭐지 이 오묘한 빛깔은? 뭐지 이 오묘한 기운은? <br> 스피드가 7 증가했다!");
					}else {
					bean.setCharStatus(0);
					bean.setEvent("버섯을 먹자 곧 심한 구역감과 함께 어지러움이 찾아왔다. <br> 엄마 아빠를 다시만나는 환각을 보며 깊은 잠에 들었다.");
				}
				break;
			case 2 :
				bean.setFirewood(bean.getFirewood()+5);
				bean.setFood(bean.getFood()+5);
				bean.setEvent("마치 마녀의 것으로 보이는 화로와 알수 없는 스프가 놓여있었다. <br> 마녀가 갑자기 집에 난입했지만 쓸만해 보이는걸 들고 도망쳤다! <br> 식량과 장작이 5씩 증가했다. ");
				break;
			}
			break;
		
		}
	}
}
