package main;

public class ResultBean {

	String name;
	int speed, power, hike, hikerank, chinup, chinuprank, swim, swimrank, totalpoints, finalrank;
	int[] playeridx = new int[9];

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public int getHike() {
		return hike;
	}

	public void setHike(int hike) {
		this.hike = hike;
	}

	public int getHikerank() {
		return hikerank;
	}

	public void setHikerank(int hikerank) {
		this.hikerank = hikerank;
	}

	public int getChinup() {
		return chinup;
	}

	public void setChinup(int chinup) {
		this.chinup = chinup;
	}

	public int getChinuprank() {
		return chinuprank;
	}

	public void setChinuprank(int chinuprank) {
		this.chinuprank = chinuprank;
	}

	public int getSwim() {
		return swim;
	}

	public void setSwim(int swim) {
		this.swim = swim;
	}

	public int getSwimrank() {
		return swimrank;
	}

	public void setSwimrank(int swimrank) {
		this.swimrank = swimrank;
	}

	public int getTotalpoints() {
		return totalpoints;
	}

	public void setTotalpoints(int totalpoints) {
		this.totalpoints = totalpoints;
	}

	public int getFinalrank() {
		return finalrank;
	}

	public void setFinalrank(int finalrank) {
		this.finalrank = finalrank;
	}

	public int[] getPlayeridx() {
		return playeridx;
	}

	public void setPlayeridx(int[] playeridx) {
		this.playeridx = playeridx;
	}

}
