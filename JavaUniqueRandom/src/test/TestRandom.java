package test;

import com.github.quickbonak.paradise.UniqueRandom;


public class TestRandom {

	public static void main(String[] args) {
		
		UniqueRandom uRandom = new UniqueRandom();
		
		int resu[] = new int[10];
		for (int i = 0; i < resu.length; i++) {
			resu[i] = uRandom.nextIntOnce(1, 10);
			System.out.println(resu[i]);
		}
		uRandom.resetNextIntOnce();
		for (int i = 0; i < resu.length; i++) {
			resu[i] = uRandom.nextIntOnce(1, 10);
			System.out.println(resu[i]);
		}
		uRandom.resetNextIntOnce();
		for (int i = 0; i < resu.length; i++) {
			resu[i] = uRandom.nextIntOnce(1, 10);
			System.out.println(resu[i]);
		}
		
	}

}
