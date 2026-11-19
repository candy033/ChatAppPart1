/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.iie.chatapppart1;
   
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String JSON_FILE_PATH = "stored_messages.json";

    private static final List<Message> sentMessages = new ArrayList<>();
    private static final List<Message> disregardedMessages = new ArrayList<>();
    private static final List<Message> storedMessages = new ArrayList<>();
    private static final List<String> messageHashes = new ArrayList<>();
    private static final List<String> messageIDs = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login loginSystem = new Login();

        // Part 1: Registration
        System.out.println("=== QuickChat Registration ===");
        System.out.print("Enter First Name: ");
        loginSystem.setFirstName(scanner.nextLine().trim());

        System.out.print("Enter Last Name: ");
        loginSystem.setLastName(scanner.nextLine().trim());

        String regResult;
        while (true) {
            System.out.print("Enter Username (max 5 chars, contains '_'): ");
            String uName = scanner.nextLine().trim();

            System.out.print("Enter Password (min 8 chars, uppercase, number, special char): ");
            String pWord = scanner.nextLine();

            System.out.print("Enter Cell Phone Number (e.g. +27838968976): ");
            String cell = scanner.nextLine().trim();

            regResult = loginSystem.registerUser(uName, pWord, cell);
            System.out.println(regResult);

            if (regResult.startsWith("User registered successfully")) {
                break;
            }
        }

        // Part 1: Login
        System.out.println("\n=== QuickChat Login ===");
        boolean loggedIn = false;
        while (!loggedIn) {
            System.out.print("Enter Username: ");
            String loginUser = scanner.nextLine().trim();

            System.out.print("Enter Password: ");
            String loginPass = scanner.nextLine();

            loggedIn = loginSystem.loginUser(loginUser, loginPass);
            System.out.println(loginSystem.returnLoginStatus());
        }

        // Load previously stored messages from JSON into storedMessages array
        loadStoredMessagesFromJson();

        // Part 2 & Part 3: Main Application Loop
        System.out.println("\nWelcome to QuickChat.");
        int messageCounter = 0;
        boolean running = true;

        while (running) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Stored Messages");
            System.out.println("4) Quit");
            System.out.print("Choose an option: ");

            int choice = readInt(scanner);

            switch (choice) {
                case 1:
                    System.out.print("How many messages would you like to enter? ");
                    int limit = readInt(scanner);

                    for (int i = 0; i < limit; i++) {
                        System.out.println("\nEntering message " + (i + 1) + " of " + limit);
                        System.out.print("Enter Recipient Cell Number: ");
                        String recipient = scanner.nextLine().trim();

                        System.out.print("Enter Message (max 250 characters): ");
                        String text = scanner.nextLine();

                        Message msg = new Message(loginSystem.getUserName(), recipient, text, "Sent", messageCounter);
                        String lengthStatus = msg.checkMessageLength();
                        System.out.println(lengthStatus);

                        if (!lengthStatus.equals("Message ready to send.")) {
                            continue;
                        }

                        System.out.println("Choose action: 1) Send Message | 2) Disregard Message | 3) Store Message");
                        int action = readInt(scanner);
                        String actionResult = msg.sentMessage(action);
                        System.out.println(actionResult);

                        recordMessageInArrays(msg, action, scanner);

                        if (action == 1) {
                            messageCounter++;
                            System.out.println("Message ID: " + msg.getMessageID());
                            System.out.println("Message Hash: " + msg.getMessageHash());
                            System.out.println("Recipient: " + msg.getRecipient());
                            System.out.println("Message: " + msg.getMessageText());
                        }
                    }

                    System.out.println("Total messages sent: " + Message.returnTotalMessagess(sentMessages));
                    break;

                case 2:
                    System.out.println("Coming Soon.");
                    break;

                case 3:
                    handleStoredMessagesMenu(scanner);
                    break;

                case 4:
                    running = false;
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Please choose 1, 2, 3, or 4.");
            }
        }

        scanner.close();
    }

    private static void recordMessageInArrays(Message msg, int action, Scanner scanner) {
        messageIDs.add(msg.getMessageID());
        messageHashes.add(msg.getMessageHash());

        if (action == 1) {
            sentMessages.add(msg);
        } else if (action == 2) {
            disregardedMessages.add(msg);
            System.out.print("Press 0 to confirm deleting the disregarded message: ");
            int confirmDelete = readInt(scanner);
            if (confirmDelete == 0) {
                System.out.println("Disregarded message deleted.");
            }
        } else if (action == 3) {
            storedMessages.add(msg);
            Message.storeMessage(storedMessages, JSON_FILE_PATH);
        }
    }

    // Part 3 Menu (options a - f)
    private static void handleStoredMessagesMenu(Scanner scanner) {
        boolean backToMain = false;
        while (!backToMain) {
            System.out.println("\n=== Stored Messages Menu ===");
            System.out.println("a) Display sender and recipient of all stored messages");
            System.out.println("b) Display the longest stored message");
            System.out.println("c) Search for a message ID and display recipient and message");
            System.out.println("d) Search for all messages stored for a particular recipient");
            System.out.println("e) Delete a message using the message hash");
            System.out.println("f) Display report of all stored messages");
            System.out.println("g) Back to Main Menu");
            System.out.print("Select option: ");

            String subChoice = scanner.nextLine().trim().toLowerCase();

            switch (subChoice) {
                case "a":
                    System.out.println(displaySenderAndRecipient(storedMessages));
                    break;

                case "b":
                    System.out.println(displayLongestStoredMessage(storedMessages));
                    break;

                case "c":
                    System.out.print("Enter Message ID to search: ");
                    String searchId = scanner.nextLine().trim();
                    System.out.println(searchByMessageID(searchId));
                    break;

                case "d":
                    System.out.print("Enter recipient cell number: ");
                    String targetRecipient = scanner.nextLine().trim();
                    System.out.println(searchMessagesByRecipient(targetRecipient));
                    break;

                case "e":
                    System.out.print("Enter Message Hash to delete: ");
                    String targetHash = scanner.nextLine().trim();
                    System.out.println(deleteMessageByHash(targetHash));
                    break;

                case "f":
                    System.out.println(displayStoredMessagesReport(storedMessages));
                    break;

                case "g":
                    backToMain = true;
                    break;

                default:
                    System.out.println("Invalid selection.");
            }
        }
    }

    public static String displaySenderAndRecipient(List<Message> list) {
        if (list.isEmpty()) {
            return "No stored messages found.";
        }
        StringBuilder sb = new StringBuilder();
        for (Message m : list) {
            sb.append("Sender: ").append(m.getSender())
              .append(" | Recipient: ").append(m.getRecipient()).append("\n");
        }
        return sb.toString().trim();
    }

    public static String displayLongestStoredMessage(List<Message> list) {
        if (list.isEmpty()) {
            return "No stored messages available.";
        }
        Message longest = list.get(0);
        for (Message m : list) {
            if (m.getMessageText().length() > longest.getMessageText().length()) {
                longest = m;
            }
        }
        return longest.getMessageText();
    }

    public static String searchByMessageID(String id) {
        List<Message> all = getAllTrackedMessages();
        for (Message m : all) {
            if (m.getMessageID().equals(id)) {
                return "Recipient: " + m.getRecipient() + " | Message: " + m.getMessageText();
            }
        }
        return "Message ID not found.";
    }

    public static String searchMessagesByRecipient(String recipient) {
        List<Message> all = getAllTrackedMessages();
        StringBuilder sb = new StringBuilder();
        for (Message m : all) {
            if (m.getRecipient().equals(recipient)) {
                sb.append(m.getMessageText()).append("\n");
            }
        }
        return sb.length() == 0 ? "No messages found for recipient " + recipient : sb.toString().trim();
    }

    public static String deleteMessageByHash(String hash) {
        for (int i = 0; i < storedMessages.size(); i++) {
            Message m = storedMessages.get(i);
            if (m.getMessageHash().equalsIgnoreCase(hash)) {
                String text = m.getMessageText();
                storedMessages.remove(i);
                Message.storeMessage(storedMessages, JSON_FILE_PATH);
                return "Message: \"" + text + "\" successfully deleted.";
            }
        }
        return "Message hash not found in stored messages.";
    }

    public static String displayStoredMessagesReport(List<Message> list) {
        if (list.isEmpty()) {
            return "No stored messages to report.";
        }
        StringBuilder sb = new StringBuilder("=== Stored Messages Report ===\n");
        for (Message m : list) {
            sb.append("Message ID: ").append(m.getMessageID())
              .append(" | Message Hash: ").append(m.getMessageHash())
              .append(" | Sender: ").append(m.getSender())
              .append(" | Recipient: ").append(m.getRecipient())
              .append(" | Message: ").append(m.getMessageText())
              .append(" | Flag: ").append(m.getFlag())
              .append("\n");
        }
        return sb.toString().trim();
    }

    private static List<Message> getAllTrackedMessages() {
        List<Message> combined = new ArrayList<>();
        combined.addAll(sentMessages);
        combined.addAll(storedMessages);
        combined.addAll(disregardedMessages);
        return combined;
    }

    private static void loadStoredMessagesFromJson() {
        File file = new File(JSON_FILE_PATH);
        if (!file.exists()) {
            return;
        }
        try {
            String content = new String(Files.readAllBytes(Paths.get(JSON_FILE_PATH)));
            String[] objects = content.split("\\{\\s*");
            int idx = 0;
            for (String obj : objects) {
                if (!obj.contains("\"messageText\"")) {
                    continue;
                }
                String sender = extractJsonString(obj, "sender");
                String recipient = extractJsonString(obj, "recipient");
                String text = extractJsonString(obj, "messageText");
                String flag = extractJsonString(obj, "flag");
                Message loaded = new Message(sender, recipient, text, flag.isEmpty() ? "Stored" : flag, idx++);
                storedMessages.add(loaded);
            }
        } catch (IOException e) {
            System.out.println("Unable to load stored messages JSON: " + e.getMessage());
        }
    }

    private static String extractJsonString(String jsonObject, String key) {
        String searchKey = "\"" + key + "\": \"";
        int start = jsonObject.indexOf(searchKey);
        if (start == -1) {
            return "";
        }
        start += searchKey.length();
        int end = jsonObject.indexOf("\"", start);
        if (end == -1) {
            return "";
        }
        return jsonObject.substring(start, end).replace("\\\"", "\"").replace("\\\\", "\\");
    }

    private static int readInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.print("Please enter a valid number: ");
        }
        int val = scanner.nextInt();
        scanner.nextLine();
        return val;
    }
} 
    
        