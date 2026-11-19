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
    public void testRegistrationAndLoginSuccess() {
        Login login = new Login("Ahlumile", "Canda");
        String regMsg = login.registerUser("kyl_1", "Ch&&&sec@ke99!", "+27838969876");
        assertEquals("User registered successfully. Password successfully captured.", regMsg);
        assertTrue(login.loginUser("kyl_1", "Ch&&&sec@ke99!"));
        assertEquals("Welcome Ahlumile Canda it is great to see you again.", login.returnLoginStatus());
    }

    @Test
    public void testLoginFailureAndUnregisteredLogin() {
        Login freshLogin = new Login();
        assertFalse(freshLogin.loginUser("kyl_1", "Ch&&&sec@ke99!"));

        freshLogin.registerUser("kyl_1", "Ch&&&sec@ke99!", "+27838969876");
        assertFalse(freshLogin.loginUser("wrong", "wrong"));
        assertEquals("Username or password incorrect, please try again.", freshLogin.returnLoginStatus());
    }

    @Test
    public void testMessageLengthValidAndExceeding() {
        Message validMsg = new Message("+27818693002", "Hi Mike, can you join us for dinner tonight?", "Sent", 1);
        assertEquals("Message ready to send.", validMsg.checkMessageLength());

        String longText = "A".repeat(260);
        Message longMsg = new Message("+27818693002", longText, "Sent", 2);
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.", longMsg.checkMessageLength());
    }

    @Test
    public void testMessageHashAndActions() {
        Message msg = new Message("+27818693002", "Hi Mike, can you join us for dinner tonight?", "Sent", 1);
        assertNotNull(msg.getMessageHash());
        assertTrue(msg.getMessageHash().endsWith(":1:HITONIGHT"));
        assertTrue(msg.checkRecipientCell());

        assertEquals("Message successfully sent.", msg.sentMessage(1));
        assertEquals("Sent", msg.getFlag());
        assertEquals("Press 0 to delete the message.", msg.sentMessage(2));
        assertEquals("Disregard", msg.getFlag());
        assertEquals("Message successfully stored.", msg.sentMessage(3));
        assertEquals("Stored", msg.getFlag());
    }
}