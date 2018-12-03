package ua.mainacad.maintest.maintest;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class UtilsTest {

    @Test
    public void isEmailValid_WhenInputIsEmpty() {
        // Arrange
        String email = "";
        // Act
        boolean isValid = Utils.isEmailValid(email);
        // Assert
        assertFalse(isValid);
    }

    @Test(expected = IllegalArgumentException.class)
    public void isEmailValid_WhenInputIsNull() {
        // Act
        Utils.isEmailValid(null);
    }

    @Test
    public void isEmailValid_WhenInputHasTwoAts() {
        // Arrange
        String email = "user@google@com";
        // Act
        boolean isValid = Utils.isEmailValid(email);
        // Assert
        assertFalse(isValid);
    }

    @Test
    public void isEmailValid_WhenInputHasNoAts() {
        // Arrange
        String email = "user.google.com";
        // Act
        boolean isValid = Utils.isEmailValid(email);
        // Assert
        assertFalse(isValid);
    }

    @Test
    public void isEmailValid_WhenInputHasWrongDomain() {
        // Arrange
        String email = "user@google.com";
        // Act
        boolean isValid = Utils.isEmailValid(email);
        // Assert
        assertFalse(isValid);
    }

    @Test
    public void isEmailValid_WhenInputIsCorrect() {
        // Arrange
        String email = "user@mydomain.com";
        // Act
        boolean isValid = Utils.isEmailValid(email);
        // Assert
        assertTrue(isValid);
    }
}