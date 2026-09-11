/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.iie.chatapppart1;

/**
 *
 * @author Student
 */
public class Login {

    public static void main(String[] args){
        
    }
    private String username;
    private String password;
    private String phoneNumber;
    private String firstName;
    private String lastName;

    /**
     * Checks if username contains an underscore and is at most 5 characters long.
     */
    public boolean checkUserName(String username) {
        if (username == null) return false;
        return username.contains("_") && username.length() <= 5;
    }

    /**
     * Checks if password contains >= 8 characters, 1 uppercase, 1 digit, and 1 special character.
     */
    public boolean checkPasswordComplexity(String password) {
        if (password == null) return false;

        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) {
                hasCapital = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecial = true;
            }
        }

        return password.length() >= 8 && hasCapital && hasNumber && hasSpecial;
    }

    /**
     * Checks if phone number starts with +27 and is no more than 12 characters long.
     */
    public boolean checkCellPhoneNumber(String phone) {
        if (phone == null) return false;
        return phone.startsWith("+27") && phone.length() <= 12;
    }

    /**
     * Validates credentials and registers user details.
     */
    public String registerUser(String username, String password, String phoneNumber, String firstName, String lastName) {
        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber(phoneNumber)) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }

        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;

        return "Username successfully captured.\nPassword successfully captured.\nCell phone number successfully added.";
    }

    /**
     * Checks if entered username and password match stored credentials.
     */
    public boolean loginUser(String username, String password) {
        if (this.username == null || this.password == null) {
            return false;
        }
        return this.username.equals(username) && this.password.equals(password);
    }

    /**
     * Returns login success/failure message including first and last name.
     */
    public String returnLoginStatus(boolean success) {
        if (success) {
            return "Welcome " + firstName + " " + lastName + ", it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}