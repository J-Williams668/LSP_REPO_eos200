package org.howard.edu.lsp.finale.question1;

import java.util.Random;

/**
 * Class that implements the "letters" password algorithm
 *
 * Generated passwords can contain lowercase and uppercase letters.
 * java.util.Random is used for RNG.
 */
public class LetterPasswordAlgorithm implements PasswordAlgorithm {
    private final String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "abcdefghijklmnopqrstuvwxyz";
    private final Random rand = new Random();

    /**
     * Randomly generates a password consisting of only letters using java.util.Random
     * @param length the length of the generated password
     * @return a randomly generated password of requested length containing only letters
     */
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
