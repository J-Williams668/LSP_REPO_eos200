package org.howard.edu.lsp.finale.question1;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PasswordGeneratorServiceTest {

    private PasswordGeneratorService service;

    @BeforeEach
    public void setup() {
        service = PasswordGeneratorService.getInstance();
    }

    @Test
    public void checkInstanceNotNull() {
        // verify that 'service' is not null
        assertNotNull(service, "PasswordGeneratorService instance should not be null");
    }

    @Test
    public void checkSingleInstanceBehavior() {
        PasswordGeneratorService second = PasswordGeneratorService.getInstance();

        assertSame(service, second,
                "getInstance() must always return the same singleton instance");
    }

    @Test
    public void generateWithoutSettingAlgorithmThrowsException() {
        PasswordGeneratorService s = PasswordGeneratorService.getInstance();

        assertThrows(IllegalStateException.class,
                () -> s.generatePassword(10),
                "Generating a password without setting an algorithm should throw IllegalStateException");
    }

    @Test
    public void basicAlgorithmGeneratesCorrectLengthAndDigitsOnly() {
        service.setAlgorithm("basic");
        String p = service.generatePassword(10);

        assertNotNull(p, "Generated password should not be null");
        assertEquals(10, p.length(), "Password length must match requested length");

        assertTrue(p.chars().allMatch(Character::isDigit),
                "Basic algorithm should generate digits only");
    }

    @Test
    public void enhancedAlgorithmGeneratesCorrectCharactersAndLength() {
        service.setAlgorithm("enhanced");
        String p = service.generatePassword(12);

        assertNotNull(p, "Generated password should not be null");
        assertEquals(12, p.length(), "Password length must match requested length");

        assertTrue(p.chars().allMatch(Character::isLetterOrDigit), "Enhanced password can contain letters and digits");
    }

    @Test
    public void lettersAlgorithmGeneratesLettersOnly() {
        service.setAlgorithm("letters");
        String p = service.generatePassword(8);

        assertNotNull(p, "Generated password should not be null");
        assertTrue(p.chars().allMatch(Character::isLetter),
                "Letters algorithm should generate letters only");
    }

    @Test
    public void switchingAlgorithmsChangesBehavior() {
        service.setAlgorithm("basic");
        String p1 = service.generatePassword(10);

        service.setAlgorithm("letters");
        String p2 = service.generatePassword(10);

        service.setAlgorithm("enhanced");
        String p3 = service.generatePassword(10);

        // Check basic: digits only
        assertEquals(10, p1.length(), "Basic password should have requested length");
        assertTrue(p1.chars().allMatch(Character::isDigit),
                "Basic algorithm should generate digits only");

        // Check letters: letters only
        assertEquals(10, p2.length(), "Letters password should have requested length");
        assertTrue(p2.chars().allMatch(Character::isLetter),
                "Letters algorithm should generate letters only");

        // Check enhanced: at least one digit and at least one letter
        assertEquals(10, p3.length(), "Enhanced password should have requested length");
        assertTrue(p3.chars().allMatch(Character::isLetterOrDigit), "Enhanced password can have any letter and digit");
    }
}
