package com.zhongpei.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Created by zhongpei on 2016/10/17.
 */
public class sequenceGenerator {

    private Random random;
    private String encryptAlgorithm;

    public sequenceGenerator() {
        random = new Random();
        encryptAlgorithm = "md5";
    }

    public sequenceGenerator(String encryptAlgorithm) {
        random = new Random();
        this.encryptAlgorithm = encryptAlgorithm;
    }

    /**
     * Generate k random string sequences with no duplicates
     *
     * @param k number of random strings generated
     * @return an array of random strings
     */
    public String[] generateNoDup(int k) {
        String[] res = new String[k];
        for (int i = 0; i < k; i++) {
            long data = System.currentTimeMillis() + random.nextLong();
            res[i] = hashFunction(String.valueOf(data));
        }
        return res;
    }


    private String hashFunction(String data) {
        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest md5 = MessageDigest.getInstance(encryptAlgorithm);
            md5.update(data.getBytes());
            for (byte encryptByte : md5.digest()) {
                sb.append(String.format("%02X", encryptByte));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
