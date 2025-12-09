package org.howard.edu.lsp.finale.question1;

import java.util.Random;

/**
 * Class that implements the "basic" password algorithm
 *
 * Generated passwords contain only digits and use java.util.Random
 * for RNG.
 */
public class BasicPasswordAlgorithm implements PasswordAlgorithm {
    private final String allowedChars = "0123456789";
    private final Random rand = new Random();

    /**
     * Randomly generates a password consisting of only digits using java.util.Random
     * @param length the length of the generated password
     * @return a randomly generated password of requested length containing only digits
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
