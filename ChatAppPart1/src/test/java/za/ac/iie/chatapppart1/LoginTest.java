/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package za.ac.iie.chatapppart1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
 *
 * @author Student
 */
public class LoginTest {
    

    private final Login login = new Login();

    @Test
    public void testValidUsername() {
        assertTrue(login.checkUserName("kyl_1"));
    }

    @Test
    public void testInvalidUsername() {
        assertFalse(login.checkUserName("kyle!!!!!!"));
    }

    @Test
    public void testValidPassword() {
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    public void testInvalidPassword() {
        assertFalse(login.checkPasswordComplexity("password"));
    }

    @Test
    public void testValidCellNumber() {
        assertTrue(login.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    public void testInvalidCellNumber() {
        assertFalse(login.checkCellPhoneNumber("08966553"));
    }

    @Test
    public void testSuccessfulLogin() {
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976", "John", "Smith");
        boolean status = login.loginUser("kyl_1", "Ch&&sec@ke99!");
        assertTrue(status);
        assertEquals("Welcome John, Smith it is great to see you again.", login.returnLoginStatus(status));
    }

    @Test
    public void testFailedLogin() {
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976", "John", "Smith");
        boolean status = login.loginUser("kyl_1", "wrongPassword");
        assertFalse(status);
        assertEquals("Username or password incorrect, please try again.", login.returnLoginStatus(status));
    }
}

