package test;

import com.github.quickbonak.paradise.UniqueRandom;
import com.zhongpei.utils.numberGenerator;

public class TestRandom {

	public static void main(String[] args) {
		/*
		numberGenerator nRandom = new numberGenerator();
		int[] abc = nRandom.generateNoDup(1, 1000000000, 10);
		*/
		UniqueRandom uRandom = new UniqueRandom();
		int[] test = uRandom.nextIntArray(-2047483647, 2047483646, 10);
	}

}
