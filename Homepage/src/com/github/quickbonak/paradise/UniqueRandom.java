package com.github.quickbonak.paradise;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <b>Generate unique random int numbers.</b>
 * <p>
 * <b>nextIntOnce</b> - One by one in Int<br>
 * <b>resetNextIntOnce</b> - Reset generated numbers field<br>
 * <b>nextIntList</b> - Numbers result in List<br>
 * <b>nextIntArray</b> - Numbers result in Array<br>
 * <p>
 * @author quickbonak (Nakwon Sung)
 * @since Dec/09/2019
 * @version 1.0
 */
public class UniqueRandom {
	/**
	 * If you want to use common {@code java.util.Random}
	 */
	public Random random = new Random();
	
	// to generate correct range of numbers from A to B.
	private int range = 0;
	private int calibration = 0;
	
	// below 4 variables tie with nextIntOnce method.
	private int arrayPiece = 1;
	private int arrayLength = arrayPiece * 1023;
	// store generated unique numbers by nextIntOnce method.
	// reset needed when you want to make another unique random procedure.
	private int[] uniqueField = new int[arrayLength];
	private int targetThisTime = 0;

	// initializing range and calibration values with from, to variables.
	private void initialization(int from, int to) {
		this.range = to-from+1;
		if(this.range < 0) {
			this.range = -this.range;
			this.calibration = to;
		}else {
			this.calibration = from;
		}
	}

	/* Substantive generator of a unique random number.
	 * list : number of result numbers with void spaces.
	 * targetIndex = numberOfResultNumbers + 1 (target array index at this time.)
	 */
	private int uniqueIntFactory(int[] list, int targetIndex) {
		int result = this.random.nextInt(this.range - targetIndex) + this.calibration;
		int dupliCount = 0;
		int lastCount = 0;
		boolean[] counted = new boolean[targetIndex];
		boolean checking = true;
		while(checking) {
			for(int i = 0; i < targetIndex; i++) {
				if(!counted[i]) {
					if(result+dupliCount >= list[i]) {
						dupliCount++;
						counted[i] = true;
					}
				}
			}
			if(lastCount == dupliCount)
				checking = false;
			lastCount = dupliCount;
		}
		result += dupliCount;
		return result;
	}
	
	/**
	 * Returns a generated unique random number.<p>
	 * If you want to clear history cache,<br>
	 * (it store numbers to find out duplication)<br>
	 * and new unique random procedure again.<br>
	 * Use <b>resetNextIntOnce</b> method.<br>
	 * <p>
	 * @param from Lowest random number you want.
	 * @param to Highest random number you want.
	 * @return A generated unique random <i>int</i>.
	 */
	public int nextIntOnce(int from, int to) {
		int result = 0;
		this.initialization(from, to);
		if(this.targetThisTime == this.arrayLength) {
			int[] tempField = this.uniqueField;
			this.arrayPiece++;
			this.arrayLength = this.arrayPiece * 1023;
			this.uniqueField = new int[this.arrayLength];
			for (int i = 0; i < tempField.length; i++)
				this.uniqueField[i] = tempField[i];
		}
		result = this.uniqueIntFactory(this.uniqueField, this.targetThisTime);
		this.uniqueField[this.targetThisTime] = result;
		this.targetThisTime++;
		return result;
	}
	
	/**
	 * Reset history of generated numbers to <b>nextIntOnce</b>.<br>
	 * And initialize fields for new <b>nextIntOnce</b> procedure.<br>
	 */
	public void resetNextIntOnce() {
		this.arrayPiece = 1;
		this.arrayLength = this.arrayPiece * 1023;
		this.uniqueField = new int[this.arrayLength];
		this.targetThisTime = 0;
	}
	
	/**
	 * Returns a array of generated unique random int numbers.<br>
	 * <p>
	 * @param from Lowest random number you want.
	 * @param to Highest random number you want.
	 * @param arrayLength Number of unique random numbers you want.
	 * @return Generated unique random numbers <i>array</i>.
	 */
	public int[] nextIntArray(int from, int to, int arrayLength) {
		int[] result = new int[arrayLength];
		this.initialization(from, to);
		for (int i = 0; i < arrayLength; i++)
			result[i] = this.uniqueIntFactory(result, i);
		return result;
	}
	
	/**
	 * Returns a list of generated unique random int numbers.<br>
	 * <p>
	 * @param from Lowest random number you want.
	 * @param to Highest random number you want.
	 * @param listSize Number of unique random numbers you want.
	 * @return Generated unique random numbers <i>list</i>.
	 */
	public List<Integer> nextIntegerList(int from, int to, int listSize) {
		List<Integer> result = new ArrayList<Integer>();
		int[] arrayResult = this.nextIntArray(from, to, listSize);
		for (int i = 0; i < listSize; i++)
			result.add(arrayResult[i]);
		return result;
	}
}