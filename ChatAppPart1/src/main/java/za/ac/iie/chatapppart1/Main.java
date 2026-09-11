/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.iie.chatapppart1;

import java.util.Scanner;


public class Main {


    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Login login = new Login();

            System.out.println("--- REGISTRATION ---");

            // Input First Name and Last Name for welcome message display
            System.out.print("Enter First Name: ");
            String firstName = scanner.nextLine();

            System.out.print("Enter Last Name: ");
            String lastName = scanner.nextLine();

            // Username Input and Validation
            String username;
            while (true) {
                System.out.print("Enter Username: ");
                username = scanner.nextLine();
                if (login.checkUserName(username)) {
                    System.out.println("Username successfully captured.");
                    break;
                } else {
                    System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
                }
            }

            // Password Input and Validation
            String password;
            while (true) {
                System.out.print("Enter Password: ");
                password = scanner.nextLine();
                if (login.checkPasswordComplexity(password)) {
                    System.out.println("Password successfully captured.");
                    break;
                } else {
                    System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
                }
            }

            // Cell Phone Number Input and Validation
            String cellPhoneNumber;
            while (true) {
                System.out.print("Enter Cell Phone Number: ");
                cellPhoneNumber = scanner.nextLine();
                if (login.checkCellPhoneNumber(cellPhoneNumber)) {
                    System.out.println("Cell phone number successfully added.");
                    break;
                } else {
                    System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
                }
            }

            // Register User
            String registrationStatus = login.registerUser(username, password, cellPhoneNumber, firstName, lastName);
            System.out.println("\n" + registrationStatus + "\n");

            // LOGIN PROCESS
            System.out.println("--- LOGIN ---");
            System.out.print("Enter Username: ");
            String loginUser = scanner.nextLine();

            System.out.print("Enter Password: ");
            String loginPass = scanner.nextLine();

            boolean isSuccess = login.loginUser(loginUser, loginPass);
            String loginStatus = login.returnLoginStatus(isSuccess);
            System.out.println(loginStatus);
        } //Closes try (Scanner...)
       
    } //Closes public static void main
} //Closes public class Main