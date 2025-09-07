package com.business.basiclogics;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VulnerableCodeExamples {
    
    // 1. Hardcoded credentials - Security vulnerability
    private static final String DB_PASSWORD = "admin123";
    private static final String API_KEY = "sk_test_1234567890abcdefghijklmnopqrstuvwxyz";
    
    // 2. Null pointer dereference - Bug
    public String getUserName(User user) {
        return user.getName().toUpperCase(); // Potential NPE if user or name is null
    }
    
    // 3. SQL Injection vulnerability
    public List<User> findUsers(String searchTerm) {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users WHERE name = '" + searchTerm + "'";
        // Execute query without prepared statement
        return users;
    }
    
    // 4. Unclosed resource - Bug
    public void readFile(String filePath) {
        try {
            FileReader reader = new FileReader(filePath);
            int data = reader.read();
            while (data != -1) {
                System.out.print((char) data);
                data = reader.read();
            }
            // Forgot to close the reader
        } catch (IOException e) {
            // 5. Ignored exception - Bug
        }
    }
    
    // 6. Insecure hashing - Security vulnerability
    public String hashPassword(String password) {
        // Using MD5 which is cryptographically broken
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(password.getBytes());
            return new String(array);
        } catch (java.security.NoSuchAlgorithmException e) {
            return null;
        }
    }
    
    // 7. Command injection - Security vulnerability
    public void pingHost(String ipAddress) {
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("ping " + ipAddress); // Command injection risk
            // Process the output
        } catch (IOException e) {
            Logger.getLogger(VulnerableCodeExamples.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    // 8. Insecure random number - Security vulnerability
    public String generatePasswordResetToken() {
        Random random = new Random(); // Insecure random
        return "token_" + random.nextInt();
    }
    
    // 9. Hardcoded file path - Bug
    public void logError(String error) {
        try (FileWriter fw = new FileWriter("C:\\logs\\error.log", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(error);
        } catch (IOException e) {
            // Ignored
        }
    }
    
    // 10. Insecure object deserialization - Security vulnerability
    public Object deserializeObject(byte[] data) throws Exception {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(in);
        return ois.readObject();
    }
    
    // 11. XSS vulnerability (if used in web context)
    public String displayUserInput(String userInput) {
        return "<div>" + userInput + "</div>"; // XSS risk
    }
    
    // 12. Array index out of bounds - Bug
    public int getElement(int[] array, int index) {
        return array[index]; // No bounds checking
    }
    
    // 13. Infinite recursion - Bug
    public int calculateFactorial(int n) {
        return n * calculateFactorial(n - 1); // Missing base case
    }
    
    // 14. Insecure comparison - Bug
    public boolean isValidUser(String username, String password) {
        // Using == instead of .equals() for string comparison
        return (username == "admin" && password == "admin123");
    }
    
    // 15. Logging sensitive information - Security vulnerability
    public void processCreditCard(String cardNumber) {
        // Logging sensitive information
        System.out.println("Processing card: " + cardNumber);
        // Process the card...
    }
}
