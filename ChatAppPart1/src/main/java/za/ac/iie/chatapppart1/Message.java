/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.iie.chatapppart1;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher; 
import java.util.regex.Pattern;

/**
 *
 * @author Student
 */
public class Message {
 
   

    private String messageID;
    private int messageNumber;
    private String sender;
    private String recipient;
    private String messageText;
    private String messageHash;
    private String flag; // "Sent", "Stored", "Disregard"

    public Message(String recipient, String messageText, String flag, int messageNumber) {
        this(null, recipient, messageText, flag, messageNumber);
    }

    public Message(String sender, String recipient, String messageText, String flag, int messageNumber) {
        this.sender = sender;
        this.recipient = recipient;
        this.messageText = messageText;
        this.flag = flag;
        this.messageNumber = messageNumber;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    private String generateMessageID() {
        Random rand = new Random();
        long id = 1000000000L + (long) (rand.nextDouble() * 9000000000L);
        return String.valueOf(id);
    }

    // Message ID is not more than 10 characters
    public boolean checkMessageID() {
        return this.messageID != null && this.messageID.length() <= 10;
    }

    // Recipient cell number starts with '+' followed by valid digits (up to 12 chars total)
    public boolean checkRecipientCell() {
        if (this.recipient == null) {
            return false;
        }
        return Pattern.matches("^\\+[1-9][0-9]{1,11}$", this.recipient);
    }

    // First 2 chars of ID : messageNumber : FIRSTwordLASTword in ALL CAPS
    public String createMessageHash() {
        if (this.messageID == null || this.messageID.length() < 2) {
            return "";
        }
        String prefix = this.messageID.substring(0, 2);
        String firstWord = "";
        String lastWord = "";

        if (this.messageText != null && !this.messageText.trim().isEmpty()) {
            String[] words = this.messageText.trim().split("\\s+");
            firstWord = words[0].replaceAll("[^a-zA-Z0-9]", "");
            lastWord = words[words.length - 1].replaceAll("[^a-zA-Z0-9]", "");
        }

        return (prefix + ":" + this.messageNumber + ":" + firstWord + lastWord).toUpperCase();
    }

    // Checks message length (max 250 characters)
    public String checkMessageLength() {
        if (this.messageText != null && this.messageText.length() > 250) {
            int excess = this.messageText.length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
        return "Message ready to send.";
    }

    // Handles Send / Disregard / Store action and returns exact status string
    public String sentMessage(int choice) {
        if (choice == 1) {
            this.flag = "Sent";
            return "Message successfully sent.";
        } else if (choice == 2) {
            this.flag = "Disregard";
            return "Press 0 to delete the message.";
        } else if (choice == 3) {
            this.flag = "Stored";
            return "Message successfully stored.";
        }
        return "Invalid option selected.";
    }

    public static String printMessages(List<Message> sentList) {
        if (sentList == null || sentList.isEmpty()) {
            return "No messages sent yet.";
        }
        StringBuilder sb = new StringBuilder();
        for (Message m : sentList) {
            sb.append("Message ID: ").append(m.getMessageID())
              .append(" | Message Hash: ").append(m.getMessageHash())
              .append(" | Recipient: ").append(m.getRecipient())
              .append(" | Message: ").append(m.getMessageText())
              .append("\n");
        }
        return sb.toString().trim();
    }

    public static int returnTotalMessages(List<Message> sentList) {
        return sentList == null ? 0 : sentList.size();
    }

    // Stores messages in a JSON file
    public static void storeMessage(List<Message> messages, String filePath) {
        List<Message> safeList = (messages == null) ? List.of() : messages;
        StringBuilder json = new StringBuilder();
        json.append("[\n");
        for (int i = 0; i < safeList.size(); i++) {
            Message m = safeList.get(i);
            json.append("  {\n");
            json.append("    \"messageID\": \"").append(escapeJson(m.getMessageID())).append("\",\n");
            json.append("    \"sender\": \"").append(escapeJson(m.getSender())).append("\",\n");
            json.append("    \"recipient\": \"").append(escapeJson(m.getRecipient())).append("\",\n");
            json.append("    \"messageText\": \"").append(escapeJson(m.getMessageText())).append("\",\n");
            json.append("    \"messageHash\": \"").append(escapeJson(m.getMessageHash())).append("\",\n");
            json.append("    \"flag\": \"").append(escapeJson(m.getFlag())).append("\"\n");
            json.append("  }");
            if (i < safeList.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }
        json.append("]");

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(json.toString());
        } catch (IOException e) {
            System.out.println("Error storing messages to JSON: " + e.getMessage());
        }
    }

    // Reads stored messages from a JSON file into an array of Message objects
    public static Message[] readMessagesFromJsonArray(String filePath) {
       try {
           Path path = Path.of(filePath);
           if (!Files.exists(path)) {
               return new Message[0];
           }
           String content = Files.readString(path);
           String[] objects = content.split("\\}\\s*,\\s*\\{");
           List<Message> loaded = new ArrayList<>();
           int index = 1;
           for (String rawObj : objects) {
               String sender = extractJsonField(rawObj, "sender");
               String recipient = extractJsonField(rawObj, "recipient");
               String text = extractJsonField(rawObj, "messageText");
               String flag = extractJsonField(rawObj, "flag");
               String id = extractJsonField(rawObj, "messageID");
               String hash = extractJsonField(rawObj, "messageHash");
               
               if (!text.isEmpty() || !recipient.isEmpty()) {
                   Message msg = new Message(sender, recipient, text, flag.isEmpty() ? "Stored" : flag, index++);
                   if (!id.isEmpty()) {
                       msg.messageID = id;
                   }
                   if (!hash.isEmpty()) {
                       msg.messageHash = hash;
                   }
                   loaded.add(msg);
               }
           }
           return loaded.toArray(Message[]::new);
       } catch (IOException ex) {
           System.getLogger(Message.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
       }
        return null;
    }

    private static String extractJsonField(String source, String fieldName) {
        Matcher matcher = Pattern.compile("\"" + Pattern.quote(fieldName) + "\"\\s*:\\s*\"(.*?)\"", Pattern.DOTALL)
                .matcher(source);
        if (matcher.find()) {
            return unescapeJson(matcher.group(1));
        }
        return "";
    }

    private static String escapeJson(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private static String unescapeJson(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("\\\"", "\"").replace("\\\\", "\\");
    }

    public String getMessageID() {
        return messageID;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}