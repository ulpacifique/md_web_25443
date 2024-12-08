package org.mindrot.jbcrypt;

public class BCryptTest {
    public static void main(String[] args) {
        String plainPassword = "alain123";
        String storedHash = "$2a$10$nsCjw1ythLfbbd2CbySzwOYockZnZomMHXtMlDXAX/kbUA37qwhZy";

        if (BCrypt.checkpw(plainPassword, storedHash)) {
            System.out.println("Password matched!");
        } else {
            System.out.println("Password did not match.");
        }
    }
}