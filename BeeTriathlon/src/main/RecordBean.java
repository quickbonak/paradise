package main;

public class RecordBean {

	int[] playerIdx = new int[10];
	String[] name = new String[10];
	int[][] hikeRecords = new int[10][3];
	int[][] chinupRecords = new int[10][3];
	int[][] swimRecords = new int[10][3];
	int[][] allRecords = new int[10][9];

	public int[] getPlayerIdx() {
		return playerIdx;
	}

	public void setPlayerIdx(int[] playerIdx) {
		this.playerIdx = playerIdx;
	}

	public String[] getName() {
		return name;
	}

	public void setName(String[] name) {
		this.name = name;
	}

	public int[][] getHikeRecords() {
		return hikeRecords;
	}

	public void setHikeRecords(int[][] hikeRecords) {
		this.hikeRecords = hikeRecords;
	}

	public int[][] getChinupRecords() {
		return chinupRecords;
	}

	public void setChinupRecords(int[][] chinupRecords) {
		this.chinupRecords = chinupRecords;
	}

	public int[][] getSwimRecords() {
		return swimRecords;
	}

	public void setSwimRecords(int[][] swimRecords) {
		this.swimRecords = swimRecords;
	}

	public int[][] getAllRecords() {
		return allRecords;
	}

	public void setAllRecords(int[][] allRecords) {
		this.allRecords = allRecords;
	}
	
}
