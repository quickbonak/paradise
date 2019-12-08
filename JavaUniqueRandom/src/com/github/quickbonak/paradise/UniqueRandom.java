package com.github.quickbonak.paradise;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UniqueRandom {


	public Random random = new Random();
	
	private int range = 0;
	private int calibration = 0;
	
	// for nextIntUnit
	private int arrayPiece = 1;
	private int arrayLength = arrayPiece * 1023;
	private int[] uniqueField = new int[arrayLength];
	private int targetThisTime = 0;
	
	// end
	private void initialization(int from, int to) {
		this.range = to-from+1;
		if(this.range < 0) {
			this.range = -this.range;
			this.calibration = to;
		}else {
			this.calibration = from;
		}
	}

	// end
	// 타겟 인덱스는 랜덤 도출을 위해 계산될 후보의 수  + 1, 즉 새로운 랜덤이 생성될 인덱스
	private int uniqueIntFactory(int[] list, int targetIndex) {
		
		int result = this.random.nextInt(this.range - targetIndex) + this.calibration;
		
		int dupliCount = 0;
		int lastCount = 0;
		boolean[] lookIn = new boolean[targetIndex];
		boolean goLoop = true;
		
		while(goLoop) {
			
			for(int i = 0; i < targetIndex; i++) {
				
				if(!lookIn[i]) {
					
					if(result+dupliCount >= list[i]) {
						dupliCount++;
						lookIn[i] = true;
					}
					
				}
				
			}
			
			if(lastCount == dupliCount) {
				goLoop = false;
			}
			
			lastCount = dupliCount;
			
		}
		
		result += dupliCount;
		
		return result;
		
	}
	
	// end
	public int nextIntOnce(int from, int to) {
		
		int result = 0;
		
		this.initialization(from, to);
		
		if(this.targetThisTime == this.arrayLength) {
			int[] tempField = this.uniqueField;
			this.arrayPiece++;
			this.arrayLength = this.arrayPiece * 1023;
			this.uniqueField = new int[arrayLength];
			
			for (int i = 0; i < tempField.length; i++) {
				this.uniqueField[i] = tempField[i];
			}
		}
		
		result = this.uniqueIntFactory(uniqueField, targetThisTime);
		this.uniqueField[targetThisTime] = result;
		this.targetThisTime++;
		
		return result;
		
	}
	
	// end
	public void resetNextIntOnce() {
		
		this.arrayPiece = 1;
		this.arrayLength = this.arrayPiece * 1023;
		this.uniqueField = new int[arrayLength];
		this.targetThisTime = 0;
		
	}
	
	// end
	public int[] nextIntArray(int from, int to, int arrayLength) {
		
		int[] result = new int[arrayLength];
		
		this.initialization(from, to);
		
		for (int i = 0; i < arrayLength; i++) {
			result[i] = this.uniqueIntFactory(result, i);
		}
		
		return result;
		
	}
	
	// end
	public List<Integer> nextIntegerList(int from, int to, int listSize) {

		List<Integer> result = new ArrayList<Integer>();
		int[] arrayResult = this.nextIntArray(from, to, listSize);
		
		for (int i = 0; i < listSize; i++) {
			result.add(arrayResult[i]);
		}
		
		return result;
		
	}
	
	
	
}