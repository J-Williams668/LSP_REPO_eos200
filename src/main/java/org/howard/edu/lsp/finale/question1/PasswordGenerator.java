package org.howard.edu.lsp.finale.question1;

/**
 * Stores the current password algorithm being used and handles
 * password generation and algorithm switching logic.
 */
public class PasswordGenerator {
    private PasswordAlgorithm algorithm;

    /**
     * Constructor for the PasswordGenerator class
     */
    public PasswordGenerator(){}

    /**
     * Sets the algorithm to an instance of a PasswordAlgorithm object
     * corresponding to the name. Object instantiation is handled by
     * PasswordAlgorithmFactory.
     * @param name name of a password algorithm
     */
    public void setAlgorithm(String name){
        this.algorithm = PasswordAlgorithmFactory.fromString(name);
    }

    /**
     * Generates a password using the current algorithm
     * @param length length of the password
     * @return A randomly generated password of the selected algorithm
     * @throws IllegalStateException when no algorithm type is set
     */
    public String generate(int length) throws IllegalStateException{
        if (this.algorithm == null){
            throw new IllegalStateException("No password algorithm has been set.");
        }
        return algorithm.makePassword(length);
    }
}
