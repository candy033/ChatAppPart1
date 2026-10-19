/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package za.ac.iie.quickchat;

import java.util.Scanner;

/**
 *
 * @author Student
 */
public class QuickChat {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Requirement 1 & 2: Welcome message (Assumes successful login prior)
        System.out.println("Welcome to QuickChat.");

        boolean running = true;

        while (running) {
            // Requirement 3: Numeric Menu
            System.out.println("\nSelect an option:");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Enter choice: ");

            int menuChoice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (menuChoice) {
                case 1:
                    // Requirement 5: Set total messages to process
                    System.out.print("How many messages would you like to enter? ");
                    int totalToEnter = scanner.nextInt();
                    scanner.nextLine();

                    for (int i = 0; i < totalToEnter; i++) {
                        System.out.println("\n--- Entering Message " + (i + 1) + " of " + totalToEnter + " ---");
                        
                        System.out.print("Enter recipient cell number (e.g. +278212345): ");
                        String recipient = scanner.nextLine();

                        System.out.print("Enter message content: ");
                        String body = scanner.nextLine();

                        Message msg = new Message(recipient, body);

                        // Validate cell number
                        String cellValidation = msg.checkRecipientCell();
                        System.out.println(cellValidation);

                        if (!cellValidation.startsWith("Cell phone number successfully captured.")) {
                            i--; // Retry this iteration
                            continue;
                        }

                        // Message Action Sub-Menu
                        System.out.println("\nChoose action:");
                        System.out.println("1) Send Message");
                        System.out.println("2) Disregard Message");
                        System.out.println("3) Store Message to send later");
                        System.out.print("Choice: ");
                        int action = scanner.nextInt();
                        scanner.nextLine();

                        String actionResult = msg.SentMessage(action);
                        System.out.println(actionResult);

                        // Requirement 7: Display details if sent
                        if (action == 1) {
                            System.out.println("\n--- Message Details ---");
                            System.out.println(msg.printMessages());
                        }
                    }

                    // Requirement 6: Display total messages sent
                    System.out.println("\nTotal messages sent in this session: " + Message.returnTotalMessagess());
                    break;

                case 2:
                    // Requirement 3b: Feature in development
                    System.out.println("Coming Soon.");
                    break;

                case 3:
                    // Requirement 4: Quit application
                    System.out.println("Exiting QuickChat. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option. Please enter 1, 2, or 3.");
            }
        }
        scanner.close();
    }
 
    
    }
