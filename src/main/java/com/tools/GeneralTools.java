package com.tools;

import java.util.Random;

public class GeneralTools {
    public static String creatVerifyCode(String type, int targetStringLength) {
        int leftLimit = 0;
        int rightLimit = 0;
        switch (type) {
            case "number":
                leftLimit = 49;// letter '1'
                rightLimit = 57;// letter '9'
                break;
            case "lowercase":
                leftLimit = 97;
                rightLimit = 122;
                break;
            case "uppercase":
                leftLimit = 65;
                rightLimit = 90;
                break;
            default:
                break;
        }

        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();
        return generatedString;
    }
}
