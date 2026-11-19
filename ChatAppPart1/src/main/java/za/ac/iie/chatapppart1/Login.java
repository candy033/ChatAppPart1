/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.iie.chatapppart1;
import java.util.regex.Pattern;
/**
 *
 * @author Student
 */


public class Login {
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String cellPhoneNumber;

    private String registeredUserName;
    private String registeredPassword;

    public Login() {
    }

    public Login(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Username contains '_' and is no more than 5 characters long
    public boolean checkUserName() {
        if (this.userName == null) {
            return false;
        }
        return this.userName.contains("_") && this.userName.length() <= 5;
    }

    public boolean checkUserName(String candidateUserName) {
        this.userName = candidateUserName;
        return checkUserName();
    }

    // Password: at least 8 chars, capital letter, number, and special character
    public boolean checkPasswordComplexity() {
        if (this.password == null || this.password.length() < 8) {
            return false;
        }
        boolean hasUpper = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char ch : this.password.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                hasUpper = true;
            } else if (Character.isDigit(ch)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(ch)) {
                hasSpecial = true;
            }
        }
        return hasUpper && hasDigit && hasSpecial;
    }

    public boolean checkPasswordComplexity(String candidatePassword) {
        this.password = candidatePassword;
        return checkPasswordComplexity();
    }

    // Regex cell phone check: international code '+' followed by digits (up to 10 digits after '+')
    // Reference: Standard international phone regex validation using java.util.regex.Pattern
    public boolean checkCellPhoneNumber() {
        if (this.cellPhoneNumber == null) {
            return false;
        }
        return Pattern.matches("^\\+[1-9][0-9]{1,11}$", this.cellPhoneNumber);
    }

    public boolean checkCellPhoneNumber(String candidateCell) {
        this.cellPhoneNumber = candidateCell;
        return checkCellPhoneNumber();
    }

    public String registerUser() {
        if (!checkUserName()) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber()) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }

        this.registeredUserName = this.userName;
        this.registeredPassword = this.password;
        return "User registered successfully. Password successfully captured.";
    }

    public String registerUser(String userName, String password, String cellPhoneNumber) {
        this.userName = userName;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
        return registerUser();
    }

    public boolean loginUser() {
        if (this.userName == null || this.password == null){
             return false; 
        } 
            if (this. registeredUserName != null && this.registeredPassword != null){
                return this.userName.equals(this.registeredUserName)
                && this.password.equals(this.registeredPassword);
            }
            return checkUserName() && checkPasswordComplexity(); 
    }

    public boolean loginUser(String userName, String password) {
        this.userName = userName;
        this.password = password;
        return loginUser();
    }

    public String returnLoginStatus() {
        if (loginUser()) {
            String fName = (firstName != null && !firstName.isEmpty()) ? firstName : "User";
            String lName = (lastName != null && !lastName.isEmpty()) ? lastName : "";
            String fullName = lName.isEmpty() ? fName : fName + " " + lName;
            return "Welcome " + fullName + " it is great to see you again.";
        }
        return "Username or password incorrect, please try again.";
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }
}

   

