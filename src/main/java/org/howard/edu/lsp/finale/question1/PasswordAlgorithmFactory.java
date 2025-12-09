package org.howard.edu.lsp.finale.question1;

/**
 * Instantiates a PasswordAlgorithm object based on the name given from
 * user input.
 */
public class PasswordAlgorithmFactory {

    /**
     * Instantiates a PasswordAlgorithm object based on the name given from
     * user input.
     * @param name the name corresponding to a PasswordAlgorithm class
     * @return a PasswordAlgorithm object corresponding to the name parameter
     * @throws IllegalArgumentException if an unsupported algorithm type is given
     */
    public static PasswordAlgorithm fromString(String name) throws IllegalArgumentException{
        return switch(name.toLowerCase()){
            case "basic" -> new BasicPasswordAlgorithm();
            case "enhanced" -> new EnhancedPasswordAlgorithm();
            case "letters" -> new LetterPasswordAlgorithm();
            default -> throw new IllegalArgumentException("Invalid algorithm type selected: " + name);
        };
    }
}
