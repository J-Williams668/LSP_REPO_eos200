package org.howard.edu.lsp.finale.question1;

/**
 * Singleton point of access for PasswordGenerator.
 */
public class PasswordGeneratorService {
    private static final PasswordGeneratorService INSTANCE = new PasswordGeneratorService();
    private final PasswordGenerator generator = new PasswordGenerator();

    private PasswordGeneratorService(){}

    /**
     * Returns a singleton instance of the PasswordGeneratorService class
     * @return singleton instance of PasswordGeneratorService class
     */
    public static PasswordGeneratorService getInstance(){
        return INSTANCE;
    }

    /**
     * Sets the password algorithm by calling the setAlgorithm() method from
     * PasswordGenerator
     * @param name name of a supported password algorithm
     */
    public void setAlgorithm(String name){
        generator.setAlgorithm(name);
    }

    /**
     * Generates a random password by calling the generate() method from
     * PasswordGenerator
     * @param length length of the generated password
     * @return a randomly generated password of the selected algorithm
     */
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