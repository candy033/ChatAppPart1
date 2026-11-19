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
    

    @Test
    public void testUserNameValid() {
        Login login = new Login();
        assertTrue(login.checkUserName("kyl_1"));
    }

    @Test
    public void testUserNameInvalid() {
        Login login = new Login();
        assertFalse(login.checkUserName("kyle!!!!!!"));
    }

    @Test
    public void testPasswordComplexityValid() {
        Login login = new Login();
        assertTrue(login.checkPasswordComplexity("Ch&&&sec@ke99!"));
    }

    @Test
    public void testPasswordComplexityInvalid() {
        Login login = new Login();
        assertFalse(login.checkPasswordComplexity("password"));
    }

    @Test
    public void testCellPhoneValid() {
        Login login = new Login();
        assertTrue(login.checkCellPhoneNumber("+27838969876"));
    }

    @Test
    public void testCellPhoneInvalid() {
        Login login = new Login();
        assertFalse(login.checkCellPhoneNumber("08966553"));
    }

    @Test
    public void testLoginSuccess() {
        Login login = new Login("Ahlumile", "Canda");
        login.registerUser("kyl_1", "Ch&&&sec@ke99!", "+27838969876");
        assertTrue(login.loginUser("kyl_1", "Ch&&&sec@ke99!"));
    }

    @Test
    public void testLoginFailure() {
        Login login = new Login();
        login.registerUser("kyl_1", "Ch&&&sec@ke99!", "+27838969876");
        assertFalse(login.loginUser("wrong", "wrong"));
    }

    @Test
    public void testMessageLengthValid() {
        Message msg = new Message("+27818693002", "Hi Mike, can you join us for dinner tonight?", "Sent", 1);
        assertEquals("Message ready to send.", msg.checkMessageLength());
    }

    @Test
    public void testMessageHashCreation() {
        Message msg = new Message("+27818693002", "Hi Mike, can you join us for dinner tonight?", "Sent", 1);
        // Hash depends on the generated MessageID's first 2 chars, counter 1, and first/last words (HI, TONIGHT)
        assertNotNull(msg.getMessageHash());
        assertTrue(msg.getMessageHash().contains("HI"));
    }
}
