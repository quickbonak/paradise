package test;

import java.util.List;

import com.github.quickbonak.paradise.UniqueRandom;


public class TestRandom {

	public static void main(String[] args) {
		
		UniqueRandom uRandom = new UniqueRandom();
		
		List<Integer> aa = uRandom.nextIntegerList(0, 9, 10);
		
		for (int i = 0; i < aa.size(); i++) {
			System.out.println(aa.get(i));
		}
		
	}

}
