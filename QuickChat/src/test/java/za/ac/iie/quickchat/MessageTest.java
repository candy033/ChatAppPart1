/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package za.ac.iie.quickchat;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    public void testCheckRecipientCellSuccess() {
        Message msg = new Message("+278312345", "Hi");
        assertEquals("Cell phone number successfully captured.", msg.checkRecipientCell());
    }

    @Test
    public void testCheckRecipientCellFailure() {
        Message msg = new Message("083123456789", "Hi");
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", msg.checkRecipientCell());
    }

    @Test
    public void testMessageHashFormat() {
        Message msg = new Message("+278312345", "HI TONIGHT");
        assertEquals("00:0:HITONIGHT", msg.getMessageHash());
    }

    @Test
    public void testCheckMessageID() {
        Message msg = new Message("+278312345", "Test Message");
        assertTrue(msg.checkMessageID());
    }

    @Test
    public void testSentMessageOptions() {
        Message msg = new Message("+278312345", "Test Message");
        assertEquals("Message successfully sent.", msg.SentMessage(1));
        assertEquals("Press 0 to delete the message.", msg.SentMessage(2));
        assertEquals("Message successfully stored.", msg.SentMessage(3));
    }
}