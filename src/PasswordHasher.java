public class PasswordHasher {
    public static void main(String[] args) {
        String password = "password123"; // Replace with your desired password
        String hashedPassword = UserAuth.hashPassword(password);
        System.out.println("Hashed Password: " + hashedPassword);
    }
}
