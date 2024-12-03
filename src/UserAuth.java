import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class UserAuth {
    private static final String USERS_FILE = "users.txt";

    // Load users from file
    public static Map<String, String[]> loadUsers() {
        Map<String, String[]> users = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String username = parts[0];
                    String passwordHash = parts[1];
                    String role = parts[2];
                    users.put(username, new String[]{passwordHash, role});
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
        return users;
    }

    // Authenticate user
    public static String authenticate(String username, String password) {
        Map<String, String[]> users = loadUsers();
        if (users.containsKey(username)) {
            String[] details = users.get(username);
            String passwordHash = details[0];
            String role = details[1];
            if (hashPassword(password).equals(passwordHash)) {
                return role; // Return role if authentication is successful
            }
        }
        return null; // Authentication failed
    }

    // Hash password using SHA-256
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
