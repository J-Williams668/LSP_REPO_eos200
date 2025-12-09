package org.howard.edu.lsp.finale.question1;

import java.security.SecureRandom;

public class EnhancedPasswordAlgorithm implements PasswordAlgorithm {
    private final String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "abcdefghijklmnopqrstuvwxyz" +
            "0123456789";
    private final SecureRandom secureRand = new SecureRandom();

    @Override
    public String makePassword(int length){
        StringBuilder passwordBuilder =  new StringBuilder(length);
        for (int i = 0; i < length; i++){
            int index = secureRand.nextInt(allowedChars.length());
            passwordBuilder.append(allowedChars.charAt(index));
        }
        return passwordBuilder.toString();
    }
}
