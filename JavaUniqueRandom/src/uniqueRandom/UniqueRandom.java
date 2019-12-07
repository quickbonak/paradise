package uniqueRandom;

import java.util.Random;

public class UniqueRandom {

	Random random = new Random();
	
	// 랜덤으로 출력될 수의 범위를 지정해 주어야 한다.
	// 수의 경계를 명확히 지정할 것
	public int[] nextIntArray(int from, int to, int arrayLength) {
		
		int range = to-from;
		int calibration = from;
		
		if(range < 0) {
			range = -range;
			calibration = to;
		}
		
		int result = random.nextInt(range) + calibration;
		
		return null;
		
	}
	
}