package com.zhongpei.utils;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by zhongpei on 2016/10/16.
 */
public class numberGenerator {
    private Random random;

    public numberGenerator() {
        random = new Random();
    }


    /**
     * Generate k random integers from 1 to n.
     *
     * @param n the range of random number
     * @param k the number of elements generated
     * @return A k-length array
     */
    public int[] generateNoDup(int n, int k) {
        int[] array = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            array[i] = i;
        }
        for (int i = 0; i <= n; i++) {
            int index = random.nextInt(n + 1);
            int tmp = array[index];
            array[index] = array[i];
            array[i] = tmp;
        }
        return Arrays.copyOfRange(array, 0, k + 1);
    }


    /**
     * Generate k random integers from start(inclusive) to end(inclusive) without duplicates.
     * @param start the start index
     * @param end the end index
     * @param k the number of elements generated
     * @return A k-length array
     */
    public int[] generateNoDup(int start, int end, int k) {
        int length = end - start + 1;
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            array[i] = start + i;
        }
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(length);
            int tmp = array[index];
            array[index] = array[i];
            array[i] = tmp;
        }
        return Arrays.copyOfRange(array, 0, k + 1);
    }


}
