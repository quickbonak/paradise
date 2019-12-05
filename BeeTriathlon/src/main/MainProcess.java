package main;

import java.util.Random;

public class MainProcess {
	int turn;
	MainBean bean;
	Random random = new Random();
	int[] specie, status, passedTurn, harvestTurn, tempResult,
			waterResult, optimalTemp, waterContent,
			waterReduction, dieTemp, dieWater, fertilized;
	int farmNum, seedNum;

	public MainBean doIt(MainBean tempBean, String behavior) {
		
		
		//밭의 행동들만, 8자로 제한해야 함!
		if(behavior.length()==8) {
			farmNum=Integer.parseInt(behavior.substring(6, 7));
			seedNum=Integer.parseInt(behavior.substring(7, 8));
			behavior=behavior.substring(0, 6);
		}
		bean = tempBean;
		specie = bean.getSpecie();
		status = bean.getStatus();
		passedTurn = bean.getPassedTurn();
		harvestTurn = bean.getHarvestTurn();
		tempResult = bean.getTempResult();
		waterResult = bean.getWaterResult();
		optimalTemp = bean.getOptimalTemp();
		fertilized = bean.getFertilized();
		waterContent = bean.getWaterContent();
		waterReduction = bean.getWaterReduction();
		dieTemp = bean.getDieTemp();
		dieWater = bean.getDieWater();
		
		turn = bean.getTurn()+1;
		bean.setTurn(turn);
		
		switch(bean.getTurn()) {
			
		case 34 :
			bean.setCharStatus(2);
			break;
		
		case 67 :
			if(random.nextInt(100)<50) {
				bean.setCharStatus(3);
			}else {
				bean.setCharStatus(4);
			}
			break;
			
		case 101 :
			break;
		
		}
		
		if(bean.getFood()-1==-1) {
			bean.setFatigue(bean.getFatigue()+20);
			bean.setFood(0);
			bean.setLifeCount(bean.getLifeCount()-2);
		}else {
			bean.setFood(bean.getFood()-1);
		}
		
		//나중에 스위치문으로 바꿔보자
		if(bean.getTemperature()<-15) {
			bean.setFirewood(bean.getFirewood()-3);
		}else if(bean.getTemperature()<-5) {
			bean.setFirewood(bean.getFirewood()-2);
		}else if(bean.getTemperature()<5) {
			bean.setFirewood(bean.getFirewood()-1);
		}
		
		if(bean.getFirewood()<0) {
			bean.setLifeCount(bean.getLifeCount()+bean.getFirewood()*2);
			bean.setFatigue(bean.getFatigue()-bean.getFirewood()*10);
			bean.setFirewood(0);
		}
		
		//농사 진행
		for (int i=0; i<3; i++) {
			if(status[i]!=0&&status[i]!=4) {
				
				passedTurn[i]+=10;
				waterContent[i]-=waterReduction[i];
				if(bean.getIsRain()==1) {
					waterContent[i]+=20;
				}
				
				
				if(bean.getTemperature()>optimalTemp[i]+12+fertilized[i]||bean.getTemperature()<optimalTemp[i]-12+fertilized[i]) {
					dieTemp[i] --;
					harvestTurn[i]+=6;
					if(dieTemp[i]==0) {
						status[i]=0;
						fertilized[i]=0;
						dieTemp[i]=3;
						dieWater[i]=3;
					}
				}else if(bean.getTemperature()>optimalTemp[i]+9+fertilized[i]||bean.getTemperature()<optimalTemp[i]-9+fertilized[i]) {
					tempResult[i]+=4;
					harvestTurn[i]+=3;
				}else if(bean.getTemperature()>optimalTemp[i]+6+fertilized[i]||bean.getTemperature()<optimalTemp[i]-6+fertilized[i]) {
					tempResult[i]+=6;
					dieTemp[i]+=1;
					if(dieTemp[i]>3) {
					dieTemp[i]=3;
					}
				}else if(bean.getTemperature()>optimalTemp[i]+3+fertilized[i]||bean.getTemperature()<optimalTemp[i]-3+fertilized[i]) {
					tempResult[i]+=8;
					harvestTurn[i]-=3;
					dieTemp[i]+=2;
					if(dieTemp[i]>3) {
					dieTemp[i]=3;
					}
				}else{
					tempResult[i]+=10;
					harvestTurn[i]-=6;
					dieTemp[i]=3;
				}
				
				
				if(waterContent[i]>=100||waterContent[i]<=0) {
					dieWater[i]--;
					harvestTurn[i]+=6;
					if(dieWater[i]<=0) {
						status[i]=0;
						fertilized[i]=0;
						dieTemp[i]=3;
						dieWater[i]=3;
					}
				}else if(waterContent[i]>80||waterContent[i]<20) {
					waterResult[i]+=4;
					harvestTurn[i]+=3;
				}else if(waterContent[i]>70||waterContent[i]<30) {
					waterResult[i]+=6;
					dieWater[i]+=1;
					if(dieWater[i]>3) {
					dieWater[i]=3;
					}
				}else if(waterContent[i]>60||waterContent[i]<40) {
					waterResult[i]+=8;
					harvestTurn[i]-=3;
					dieWater[i]+=2;
					if(dieWater[i]>3) {
					dieWater[i]=3;
					}
				}else {
					waterResult[i]+=10;
					harvestTurn[i]-=6;
					dieWater[i]=3;
				}
				
				
				
				if(harvestTurn[i]<=passedTurn[i]) {
					status[i]=4;
				}else if(harvestTurn[i]*2<=passedTurn[i]*3) {
					status[i]=3;
				}else if(harvestTurn[i]*1<=passedTurn[i]*3) {
					status[i]=2;
				}
			}
		}
		
		switch(behavior) {
		
			case "" :
				break;
		
			case "sowing" :
				this.sowing(farmNum, seedNum);
				break;
				
			case "fertil" :
				this.fertil(farmNum, seedNum);
				break;
				
			case "sprayi" :
				this.sprayi(farmNum, seedNum);
				break;
				
			case "draini" :
				this.draini(farmNum, seedNum);
				break;
				
			case "harves" :
				this.harves(farmNum, seedNum);
				break;
			
			case "woodchopping" :
				this.woodchopping();
				break;
			
			case "lumberjacking" :
				this.lumberjacking();
				break;
			
			case "beekeeping" :
				this.beekeeping();
				break;
				
			case "fishing" :
				this.fishing();
				break;
				
			case "homesleeping" :
				this.homesleeping();
				break;
				
			case "chinup" :
				this.chinup();
				break;
			
			case "swim" :
				this.swim();
				break;
			
			case "hike" :
				this.hike();
				break;
				
		}
		
		bean.setSpecie(specie);
		bean.setStatus(status);
		bean.setPassedTurn(passedTurn);
		bean.setHarvestTurn(harvestTurn);
		bean.setOptimalTemp(optimalTemp);
		bean.setFertilized(fertilized);
		bean.setWaterContent(waterContent);
		bean.setWaterReduction(waterReduction);
		bean.setTempResult(tempResult);
		bean.setWaterResult(waterResult);
		bean.setDieTemp(dieTemp);
		bean.setDieWater(dieWater);
		
		//끝내기전 연산
		
		bean.setHoneyPollution(bean.getHoneyPollution()-1);
		if(bean.getHoneyPollution()<0) {
			bean.setHoneyPollution(0);
		}
		bean.setFishPollution(bean.getFishPollution()-1);
		if(bean.getFishPollution()<0) {
			bean.setFishPollution(0);
		}
		
		if(bean.getFatigue()>100) {
			bean.setLifeCount(bean.getLifeCount()-2);
			bean.setFatigue(100);
		}else if(bean.getFatigue()<0){
			bean.setFatigue(0);
		}
		
		
		if(bean.getLifeCount()<=0) {
			bean.setCharStatus(0);
		}
		
		// 모든 연산이 끝나면 생명력 1을 회복
		if(bean.getLifeCount()<10) {
		bean.setLifeCount(bean.getLifeCount()+1);
		}
		
		if(bean.getTurn()==100) {
			bean.setCharStatus(8);
		}
		
		return bean;
	}
	
	public void woodchopping() {
		bean.setLog(bean.getLog()-1);
		bean.setFirewood(bean.getFirewood()+4);
		bean.setFatigue(bean.getFatigue()+10);
	}
	
	public void lumberjacking() {
		bean.setLog(bean.getLog()+random.nextInt(3)+1);
		
		if(10>random.nextInt(100)) {
			bean.setEvent("bear");
		}
		
		bean.setFatigue(bean.getFatigue()+10);
	}
	
	public void beekeeping() {
		
		int tempProduced = random.nextInt(5)-bean.getHoneyPollution();
		if(tempProduced<0) {
			tempProduced=0;
		}
		bean.setFood(bean.getFood()+tempProduced+1);
		bean.setHoneyPollution(bean.getHoneyPollution()+4);
		
		if(10>random.nextInt(100)) {
			bean.setEvent("fungus");
		}
		
		bean.setFatigue(bean.getFatigue()+10);
	}
	
	public void fishing() {
		
		int tempProduced = random.nextInt(5)-bean.getHoneyPollution();
		if(tempProduced<0) {
			tempProduced=0;
		}
		bean.setFood(bean.getFood()+tempProduced+1);
		bean.setFishPollution(bean.getFishPollution()+4);
		
		if(10>random.nextInt(100)) {
			bean.setEvent("chestbox");
		}
		
		bean.setFatigue(bean.getFatigue()+10);
		
	}
	
	public void sowing(int farmNum, int seedNum) {
		int ot[] = {10, -5, -5, 25, 25};
		int wr[] = {4, 6, 2, 6, 2};
		
		specie[farmNum] = seedNum;
		status[farmNum] = 1;
		passedTurn[farmNum] = 1;
		harvestTurn[farmNum] = 100;
		optimalTemp[farmNum] = ot[seedNum];
		fertilized[farmNum] = 0;
		waterContent[farmNum] = 50;
		waterReduction[farmNum] = wr[seedNum];
		tempResult[farmNum] = 0;
		waterResult[farmNum] = 0;
		dieTemp[farmNum] = 3;
		dieWater[farmNum] = 3;
		bean.setFatigue(bean.getFatigue()+10);
	}
	
	public void fertil(int farmNum, int seedNum) {
		fertilized[farmNum] = 3;
		bean.setFatigue(bean.getFatigue()+10);
	}
	
	public void sprayi(int farmNum, int seedNum) {
		waterContent[farmNum]+=20;
		bean.setFatigue(bean.getFatigue()+10);
	}
	
	public void draini(int farmNum, int seedNum) {
		waterContent[farmNum]-=20;
		bean.setFatigue(bean.getFatigue()+10);
	}
	
	public void harves(int farmNum, int seedNum) {
		
		bean.setFood(bean.getFood()+tempResult[farmNum]*waterResult[farmNum]*40/harvestTurn[farmNum]/harvestTurn[farmNum]);
		status[farmNum]=0;
		bean.setFatigue(bean.getFatigue()+10);

	}
	
	public void homesleeping() {
		bean.setFatigue(bean.getFatigue()-40);
	}
	
	public void chinup() {
		bean.setPower(bean.getPower()+random.nextInt(6)+1);
		bean.setFatigue(bean.getFatigue()+10);
	}
	
	public void swim() {
		bean.setPower(bean.getPower()+random.nextInt(3)+1);
		bean.setSpeed(bean.getSpeed()+random.nextInt(3)+1);
		bean.setFatigue(bean.getFatigue()+10);
		
	}
	
	public void hike() {
		bean.setSpeed(bean.getSpeed()+random.nextInt(6)+1);
		bean.setFatigue(bean.getFatigue()+10);
	}
	
}