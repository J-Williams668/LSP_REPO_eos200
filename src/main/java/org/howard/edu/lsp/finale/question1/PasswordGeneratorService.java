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

/**
 * DESIGN PATTERNS USED
 *
 * STRATEGY:
 * The varying behaviors of the basic, enhanced, and letters algorithms
 * were encapsulated and coded to the PasswordAlgorithm interface instead
 * of subclassing a PasswordAlgorithm super class. An interface is better than
 * inheritance since each password algorithm has its own behaviour for password
 * generation. Interfaces also allow PasswordGenerator to easily swap between algorithms
 * and support new algorithms that implement the PasswordAlgorithm interface.
 *
 * FACTORY METHOD:
 * PasswordAlgorithmFactory decides which algorithm to instantiate based on its name.
 * This allows the algorithm to easily be switched at runtime via setAlgorithm().
 *
 * SINGLETON:
 * Only a single instance of PasswordGeneratorService will exist, with a single
 * shared access point via getInstance().
 */