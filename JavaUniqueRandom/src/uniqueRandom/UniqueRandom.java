package uniqueRandom;

import java.util.Random;

public class UniqueRandom {

	Random random = new Random();
	
	// �������� ��µ� ���� ������ ������ �־�� �Ѵ�.
	// ���� ��踦 ��Ȯ�� ������ ��
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