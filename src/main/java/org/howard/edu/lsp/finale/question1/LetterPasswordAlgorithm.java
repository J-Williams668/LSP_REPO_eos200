package org.howard.edu.lsp.finale.question1;

import java.util.Random;

public class LetterPasswordAlgorithm implements PasswordAlgorithm {
    private final String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "abcdefghijklmnopqrstuvwxyz";
    private final Random rand = new Random();

    @Override
    public String makePassword(int length){
        StringBuilder passwordBuilder =  new StringBuilder(length);
        for (int i = 0; i < length; i++){
            int index = rand.nextInt(allowedChars.length());
            passwordBuilder.append(allowedChars.charAt(index));
        }
        return passwordBuilder.toString();
    }
}
