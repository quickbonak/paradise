package com.github.quickbonak.paradise;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UniqueRandom {


	Random random = new Random();
	private List<Integer> uniqueField = new ArrayList<Integer>();
	
	private int range=0;
	private int calibration=0;
	
	private void initialization(int from, int to) {
		this.range = to-from+1;
		if(this.range < 0) {
			this.range = -this.range;
			this.calibration = to;
		}else {
			this.calibration = from;
		}
	}

	//타겟 인덱스는 랜덤 도출을 위해 계산될 후보의 수  + 1, 즉 새로운 랜덤이 생성될 인덱스
	private int uniqueIntFactory(int[] list, int targetIndex) {
		int result = this.random.nextInt(this.range-targetIndex) + this.calibration;
		
		return result;
	}
	
	public int nextIntUnit(int from, int to) {

		return 0;
	}
	
	public void resetUniqueField() {
		this.uniqueField.clear();
	}
	
	
	public int[] nextIntArray(int from, int to, int arrayLength) {
		
		int[] result = new int[arrayLength];
		int range = to-from+1;
		int calibration = from;
		
		if(range < 0) {
			range = -range;
			calibration = to;
		}
		
		Random random = new Random();
		for(int i=0; i<arrayLength; i++) {
			
			result[i] = random.nextInt(range-i) + calibration;

			int dupleCount = 0;
			int lastCount = 0;
			boolean[] lookIn = new boolean[arrayLength];
			boolean goLoop = true;
			while(goLoop) {
				for(int j=0; j<i; j++) {
					if(!lookIn[j]) {
						if(result[i]+dupleCount>=result[j]) {
							dupleCount++;
							lookIn[j] = true;
						}
					}
				}
				if(lastCount==dupleCount)goLoop=false;
				lastCount=dupleCount;
			}
			result[i] += lastCount;
		}
		
		return result;
	}
	
	
	public List<Integer> nextIntegerList(int from, int to, int listLength) {

		List<Integer> result = new ArrayList<Integer>();
		int[] arrayResult = this.nextIntArray(from, to, listLength);
		
		for (int i : arrayResult) {
			result.add(arrayResult[i]);
		}
		
		return result;
	}
	
	
	
}