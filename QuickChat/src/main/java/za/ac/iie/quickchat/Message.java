package za.ac.iie.quickchat;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Student
 */
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;



public class Message {

    private String messageID;
    private String recipientCell;
    private String messageContent;
    private String messageHash;
    private static int totalMessagesSent = 0;

    public Message(String recipientCell, String messageContent) {
        this.recipientCell = recipientCell;
        this.messageContent = messageContent;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    private String generateMessageID() {
        Random random = new Random();
        long id = Math.abs(random.nextLong()) % 10000000000L;
        return String.valueOf(id);
    }

    public boolean checkMessageID() {
        return this.messageID != null && this.messageID.length() <= 10;
    }

    public String checkRecipientCell() {
        if (recipientCell != null && recipientCell.length() <= 10 && recipientCell.startsWith("+")) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    public String createMessageHash() {
        if (messageContent == null || messageContent.isEmpty()) {
            return "";
        }
        String formattedContent = messageContent.replaceAll("\\s+", "").toUpperCase();
        return "00:0:" + formattedContent;
    }

    public String SentMessage(int choice) {
        switch (choice) {
            case 1:
                totalMessagesSent++;
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete the message.";
            case 3:
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid option selected.";
        }
    }

    public void storeMessage() {
        String jsonString = "{\n" +
                "  \"messageID\": \"" + messageID + "\",\n" +
                "  \"recipient\": \"" + recipientCell + "\",\n" +
                "  \"message\": \"" + messageContent + "\",\n" +
                "  \"messageHash\": \"" + messageHash + "\"\n" +
                "}";

        try (FileWriter writer = new FileWriter("messages.json", true)) {
            writer.write(jsonString + "\n");
        } catch (IOException e) {
            System.out.println("Error saving message to JSON: " + e.getMessage());
        }
    }

    public String printMessages() {
        return "Message ID: " + messageID + "\n" +
               "Message Hash: " + messageHash + "\n" +
               "Recipient: " + recipientCell + "\n" +
               "Message: " + messageContent;
    }

    public static int returnTotalMessagess() {
        return totalMessagesSent;
    }

    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
}

