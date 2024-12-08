package org.mindrot.jbcrypt;

import org.mindrot.jbcrypt.BCrypt;
import java.util.Scanner;

public class PasswordHashingDemo {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Step 1: Get the password from the user
        System.out.print("Enter a password to hash: alain123");
        String password = scanner.nextLine();

        // Step 2: Generate a salt
        String salt = BCrypt.gensalt();
        System.out.println("Generated Salt: " + salt);

        // Step 3: Hash the password with the salt
        String hashedPassword = BCrypt.hashpw(password, salt);
        System.out.println("Hashed Password: " + hashedPassword);

        // Store hashedPassword securely in the database (this is just for demonstration)
        
        // Step 4: Verify the password
        System.out.print("Enter the password again to verify: ");
        String passwordToVerify = scanner.nextLine();

        // Step 5: Use BCrypt to verify the password
        boolean isMatch = BCrypt.checkpw(passwordToVerify, hashedPassword);
        if (isMatch) {
            System.out.println("Password verification successful! Password matches.");
        } else {
            System.out.println("Password verification failed! Password does not match.");
        }

        scanner.close();
    }
}
