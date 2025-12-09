package org.howard.edu.lsp.finale.question1;

public class PasswordGeneratorService {
    private static final PasswordGeneratorService INSTANCE = new PasswordGeneratorService();
    private final PasswordGenerator generator = new PasswordGenerator();

    private PasswordGeneratorService(){}

    public static PasswordGeneratorService getInstance(){
        return INSTANCE;
    }

    public void setAlgorithm(String name){
        generator.setAlgorithm(name);
    }

    public String generatePassword(int length){
        return generator.generate(length);
    }
}
