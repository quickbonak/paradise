package com.zhongpei.utils;

import java.util.HashSet;


/**
 * Created by zhongpei on 2016/10/16.
 */
public class Test {

    private final static int RANGE_START = 1000;
    private final static int RANGE_END = 100000000;
    private final static int NUMBER_OF_ELEMENTS = 10000;
    private final static int NUMBER_OF_SEQUENCES = 10000000;


    public static void main(String args[]) {


        //Test unique string sequences
        Test.uniqueSequenceTest();

        //Test the runtime of generating random numbers.
        Test.timeTest();


        //Test if the numbers are unique
        Test.uniqueTest();


        //Test runtime memory
        Test.memoryTest();


        //Test range
        Test.rangeTest();

    }

    private static void uniqueSequenceTest() {

        sequenceGenerator sG = new sequenceGenerator();
        HashSet<String> set = new HashSet<>();

        System.out.println("Testing duplicates...");

        String[] res = sG.generateNoDup(NUMBER_OF_SEQUENCES);

        for (int i = 0; i < NUMBER_OF_SEQUENCES; i++) {
            if (!set.add(res[i])) {
                System.out.println("No Duplicates : false");
                return;
            }
        }

        System.out.println("No Duplicates : true");
    }

    private static void timeTest() {

        numberGenerator nG = new numberGenerator();

        System.out.println("Testing runtime...");
        System.out.println("Generating " + NUMBER_OF_ELEMENTS + " random numbers from 0 to " + RANGE_END + " ...");

        long startTime = System.currentTimeMillis();
        nG.generateNoDup(RANGE_END, NUMBER_OF_ELEMENTS);
        long endTime = System.currentTimeMillis();

        System.out.print("The time is ");
        System.out.print(endTime - startTime);
        System.out.println("ms");
    }

    private static void uniqueTest() {
        numberGenerator nG = new numberGenerator();
        HashSet<Integer> hashSet = new HashSet<>();

        System.out.println("Testing duplicates...");

        int[] random = nG.generateNoDup(RANGE_END, NUMBER_OF_ELEMENTS);

        boolean flag = true;
        for (int i = 0; i < random.length; i++) {
            if (!hashSet.add(random[i])) flag = false;
        }

        System.out.println("No duplicates :" + flag);
    }

    private static void memoryTest() {
        Runtime.getRuntime().gc();

        numberGenerator nG = new numberGenerator();

        nG.generateNoDup(RANGE_END, NUMBER_OF_ELEMENTS);

        System.out.println((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024L * 1024L) + "MB");

        Runtime.getRuntime().gc();
    }

    private static void rangeTest() {

        numberGenerator nG = new numberGenerator();

        System.out.println("Testing Random numbers in the range from " + RANGE_START + " to " + RANGE_END);

        int[] array = nG.generateNoDup(RANGE_START, RANGE_END, NUMBER_OF_ELEMENTS);

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < array.length; i++) {
            min = Math.min(min, array[i]);
            max = Math.max(max, array[i]);
        }

        boolean res = (min >= 0) && (max <= RANGE_END);
        System.out.println("Random numbers in the range:" + res);
    }
}
