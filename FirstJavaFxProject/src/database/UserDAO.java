package database;

import java.security.MessageDigest;
import java.sql.*;
import model.User;

public class UserDAO {
    public static boolean registerUser(User user) {
        String sql = "INSERT INTO \"User\" (name, email, password, role, resume_link) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getRole());
            pstmt.setString(5, user.getResumeLink());

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error during registration: " + e.getMessage());
            return false;
        }
    }

    public static User loginUser(String email, String password) {
        String sql = "SELECT * FROM \"User\" WHERE email = ?";
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedHashedPassword = rs.getString("password");
                String inputHashed = sha256(password);
                if (storedHashedPassword.equals(inputHashed)) {
                    User user = new User(
                        rs.getString("name"),
                        email,
                        storedHashedPassword,
                        rs.getString("role"),
                        rs.getString("resume_link")
                    );
                    user.setUserId(rs.getInt("user_id"));
                    return user;
                }
            }

        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
        }

        return null;
    }

    public static User getUserByEmail(String email) {
        String sql = "SELECT * FROM \"User\" WHERE email = ?";
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                User user = new User(
                    rs.getString("name"),
                    email,
                    rs.getString("password"),
                    rs.getString("role"),
                    rs.getString("resume_link")
                );
                user.setUserId(rs.getInt("user_id"));
                return user;
            }

        } catch (Exception e) {
            System.out.println("Get user by email error: " + e.getMessage());
        }
        return null;
    }

    public static boolean updatePassword(String email, String newPassword) {
        String sql = "UPDATE \"User\" SET password = ? WHERE email = ?";
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, sha256(newPassword));
            pstmt.setString(2, email);

            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Password update error: " + e.getMessage());
            return false;
        }
    }

    public static boolean updateResumeLink(String email, String newLink) {
        String sql = "UPDATE \"User\" SET resume_link = ? WHERE email = ?";
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newLink);
            pstmt.setString(2, email);

            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Resume update error: " + e.getMessage());
            return false;
        }
    }

    private static String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
