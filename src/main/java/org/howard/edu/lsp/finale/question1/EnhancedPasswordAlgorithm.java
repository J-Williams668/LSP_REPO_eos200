package org.howard.edu.lsp.finale.question1;

import java.security.SecureRandom;

/**
 * Class that implements the "enhanced" password algorithm
 *
 * Generated passwords can contain digits, lowercase and uppercase letters.
 * java.security.SecureRandom is used for RNG.
 */
public class EnhancedPasswordAlgorithm implements PasswordAlgorithm {
    private final String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "abcdefghijklmnopqrstuvwxyz" +
            "0123456789";
    private final SecureRandom secureRand = new SecureRandom();

    /**
     * Randomly generates a password consisting of digits and letters using java.security.SecureRandom
     * @param length the length of the generated password
     * @return a randomly generated password of requested length containing digits and letters
     */
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
