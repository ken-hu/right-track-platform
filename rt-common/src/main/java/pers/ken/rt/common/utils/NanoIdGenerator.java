package pers.ken.rt.common.utils;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

/**
 * @ClassName: NanoIdGenerator
 * @Created: 2024/12/23 11:01
 * @Author ken
 */
public class NanoIdGenerator {
    public static final char[] CUSTOM_ALPHABET =
            "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    private NanoIdGenerator() {
    }

    public static String generate() {
        return NanoIdUtils.randomNanoId(
                NanoIdUtils.DEFAULT_NUMBER_GENERATOR,
                CUSTOM_ALPHABET,
                NanoIdUtils.DEFAULT_SIZE);
    }
}
