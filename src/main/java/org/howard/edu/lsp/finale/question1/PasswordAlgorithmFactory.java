package org.howard.edu.lsp.finale.question1;

public class PasswordAlgorithmFactory {
    public static PasswordAlgorithm fromString(String name) throws IllegalArgumentException{
        return switch(name.toLowerCase()){
            case "basic" -> new BasicPasswordAlgorithm();
            case "enhanced" -> new EnhancedPasswordAlgorithm();
            case "letters" -> new LetterPasswordAlgorithm();
            default -> throw new IllegalArgumentException("Invalid algorithm type selected: " + name);
        };
    }
}
