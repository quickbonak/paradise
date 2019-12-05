package main;

public class MainBean {

	private String name;
	private int charStatus;
	private int turn;
	private int speed;
	private int power;
	private int fatigue;
	private int lifeCount;
	private int food;
	private int log;
	private int firewood;
	private String event;
	private int honeyPollution;
	private int fishPollution;

	// 농사에 쓰이는
	private int specie[], status[], passedTurn[], harvestTurn[], tempResult[], waterResult[], optimalTemp[],
			fertilized[], waterContent[], waterReduction[], dieTemp[], dieWater[];

	private int temperature;
	private int isRain;
	private int isWinter;
	private int dispRain;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCharStatus() {
		return charStatus;
	}

	public void setCharStatus(int charStatus) {
		this.charStatus = charStatus;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int getFatigue() {
		return fatigue;
	}

	public void setFatigue(int fatigue) {
		this.fatigue = fatigue;
	}

	public int getLifeCount() {
		return lifeCount;
	}

	public void setLifeCount(int lifeCount) {
		this.lifeCount = lifeCount;
	}

	public int getFood() {
		return food;
	}

	public void setFood(int food) {
		this.food = food;
	}

	public int getLog() {
		return log;
	}

	public void setLog(int log) {
		this.log = log;
	}

	public int getFirewood() {
		return firewood;
	}

	public void setFirewood(int firewood) {
		this.firewood = firewood;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public int getHoneyPollution() {
		return honeyPollution;
	}

	public void setHoneyPollution(int honeyPollution) {
		this.honeyPollution = honeyPollution;
	}

	public int getFishPollution() {
		return fishPollution;
	}

	public void setFishPollution(int fishPollution) {
		this.fishPollution = fishPollution;
	}

	public int[] getSpecie() {
		return specie;
	}

	public void setSpecie(int[] specie) {
		this.specie = specie;
	}

	public int[] getStatus() {
		return status;
	}

	public void setStatus(int[] status) {
		this.status = status;
	}

	public int[] getPassedTurn() {
		return passedTurn;
	}

	public void setPassedTurn(int[] passedTurn) {
		this.passedTurn = passedTurn;
	}

	public int[] getHarvestTurn() {
		return harvestTurn;
	}

	public void setHarvestTurn(int[] harvestTurn) {
		this.harvestTurn = harvestTurn;
	}

	public int[] getTempResult() {
		return tempResult;
	}

	public void setTempResult(int[] tempResult) {
		this.tempResult = tempResult;
	}

	public int[] getWaterResult() {
		return waterResult;
	}

	public void setWaterResult(int[] waterResult) {
		this.waterResult = waterResult;
	}

	public int[] getOptimalTemp() {
		return optimalTemp;
	}

	public void setOptimalTemp(int[] optimalTemp) {
		this.optimalTemp = optimalTemp;
	}

	public int[] getFertilized() {
		return fertilized;
	}

	public void setFertilized(int[] fertilized) {
		this.fertilized = fertilized;
	}

	public int[] getWaterContent() {
		return waterContent;
	}

	public void setWaterContent(int[] waterContent) {
		this.waterContent = waterContent;
	}

	public int[] getWaterReduction() {
		return waterReduction;
	}

	public void setWaterReduction(int[] waterReduction) {
		this.waterReduction = waterReduction;
	}

	public int[] getDieTemp() {
		return dieTemp;
	}

	public void setDieTemp(int[] dieTemp) {
		this.dieTemp = dieTemp;
	}

	public int[] getDieWater() {
		return dieWater;
	}

	public void setDieWater(int[] dieWater) {
		this.dieWater = dieWater;
	}

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	public int getIsRain() {
		return isRain;
	}

	public void setIsRain(int isRain) {
		this.isRain = isRain;
	}

	public int getIsWinter() {
		return isWinter;
	}

	public void setIsWinter(int isWinter) {
		this.isWinter = isWinter;
	}

	public int getDispRain() {
		return dispRain;
	}

	public void setDispRain(int dispRain) {
		this.dispRain = dispRain;
	}

}