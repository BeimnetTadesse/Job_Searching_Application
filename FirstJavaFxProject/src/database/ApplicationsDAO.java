package database;

import model.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ApplicationsDAO {

    public static boolean hasApplied(int userId, int jobId) {
        String sql = "SELECT 1 FROM Applications WHERE user_id = ? AND job_id = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setInt(2, jobId);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // returns true if user already applied
            }

        } catch (Exception e) {
            System.out.println("Error checking application: " + e.getMessage());
            return false;
        }
    }

    public static boolean applyToJob(int userId, int jobId, String resumeLink) {
        if (hasApplied(userId, jobId)) return false;

        String sql = "INSERT INTO Applications (job_id, user_id, resume_link, applied_at) VALUES (?, ?, ?, ?)";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, jobId);
            pstmt.setInt(2, userId);
            pstmt.setString(3, resumeLink);

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            pstmt.setString(4, timestamp);

            pstmt.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("Error applying to job: " + e.getMessage());
            return false;
        }
    }

    // New method to fetch applicants for a given jobId
    public static List<User> getApplicantsForJob(int jobId) {
        List<User> applicants = new ArrayList<>();
        String sql = "SELECT u.user_id, u.name, u.email, u.password, u.role, u.resume_link " +
                     "FROM Applications a " +
                     "JOIN User u ON a.user_id = u.user_id " +
                     "WHERE a.job_id = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, jobId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String role = rs.getString("role");
                String resumeLink = rs.getString("resume_link");

                User user = new User(userId, name, email, password, role, resumeLink);
                applicants.add(user);
            }

        } catch (Exception e) {
            System.out.println("Error fetching applicants: " + e.getMessage());
        }

        return applicants;
    }
}
