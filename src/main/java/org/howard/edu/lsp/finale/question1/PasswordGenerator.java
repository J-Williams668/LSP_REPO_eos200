package org.howard.edu.lsp.finale.question1;

public class PasswordGenerator {
    private PasswordAlgorithm algorithm;

    public PasswordGenerator(){}

    public PasswordGenerator(PasswordAlgorithm algorithm){
        this.algorithm = algorithm;
    }

    public void setAlgorithm(String name){
        this.algorithm = PasswordAlgorithmFactory.fromString(name);
    }

    public String generate(int length) throws IllegalStateException{
        if (this.algorithm == null){
            throw new IllegalStateException("No password algorithm has been set.");
        }
        return algorithm.makePassword(length);
    }
}
