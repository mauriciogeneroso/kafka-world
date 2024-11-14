package com.generoso.kafka.sender;

import java.util.concurrent.ThreadLocalRandom;

public class RandomStringGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private final int maxStringLength;

    public RandomStringGenerator(int maxStringLength) {
        this.maxStringLength = maxStringLength;
    }

    public String generate(int keySize) {
        var bodySize = maxStringLength - keySize;
        var sb = new StringBuilder(bodySize);
        var random = ThreadLocalRandom.current();
        for (var i = 0; i < bodySize; i++) {
            var index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }
}
